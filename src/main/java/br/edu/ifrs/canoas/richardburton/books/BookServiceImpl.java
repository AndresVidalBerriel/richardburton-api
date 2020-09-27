package br.edu.ifrs.canoas.richardburton.books;

import br.edu.ifrs.canoas.richardburton.EntityServiceImpl;
import br.edu.ifrs.canoas.richardburton.util.ServiceResponse;
import br.edu.ifrs.canoas.richardburton.util.ServiceSet;
import br.edu.ifrs.canoas.richardburton.util.ServiceStatus;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class BookServiceImpl<E extends Book> extends EntityServiceImpl<E, Long> implements BookService<E> {

    @Inject
    private PublicationService publicationService;

    @Inject
    private AuthorService authorService;

    private BookDAO<E> bookDAO;

    @PostConstruct
    private void init() {

        bookDAO = getDAO();
    }

    @Override
    protected abstract BookDAO<E> getDAO();

    protected abstract String[] getDefaultSearchFields();

    @Override
    public List<E> search(Long afterId, int pageSize, String queryString, boolean useDefaultFields) {

        return bookDAO.search(afterId, pageSize, queryString, useDefaultFields ? getDefaultSearchFields() : null);
    }


    @Override
    public ServiceResponse create(E book) {

        ServiceResponse response = retrieve(book);

        if (response.status() == ServiceStatus.NOT_FOUND) {

            // Create authors

            for (Author author : book.getAuthors()) author.addBook(book);
            response = authorService.create(book.getAuthors());
            if(!response.ok()) return response;
            Set<Author> authors = ((ServiceSet<Author>) response).unwrap();

            // Create book

            book.setAuthors(authors);

            response = super.create(book);
            if(!response.ok()) return response;
            book = (E) response;

            // Create publications

            for (Publication publication : book.getPublications()) publication.setBook(book);
            response = publicationService.create(book.getPublications());
            if(!response.ok()) return response;
            Set<Publication> publications = ((ServiceSet<Publication>) response).unwrap();

            book.setPublications(publications);

            return book;

        } else {

            E registered = (E) response;

            for (Publication publication : book.getPublications()) publication.setBook(registered);

            response = publicationService.merge(registered.getPublications(), book.getPublications());

            if(response.ok()) {

                Set<Publication> publications = ((ServiceSet<Publication>) response).unwrap();
                registered.setPublications(publications);
                return update(registered);

            } else if(response.status() == ServiceStatus.CONFLICT) {

                if(registered instanceof OriginalBook) return registered;

            }
            return response;
        }
    }

    @Override
    public ServiceResponse retrieve(E book) {

        if (book.getId() != null) {

            return retrieve(book.getId());
        }

        Stream<Publication> publicationStream = book.getPublications().stream();
        Stream<String> titleStream = publicationStream.map(Publication::getTitle);
        Stream<List<E>> resultStream = titleStream.map(bookDAO::retrieve);
        List<E> coincidences = resultStream.flatMap(Collection::stream).collect(Collectors.toList());

        E alreadyRegistered = null;

        for (E coincidence : coincidences) {

            boolean sameAuthors = book.getAuthors().equals(coincidence.getAuthors());

            if (sameAuthors) {
                alreadyRegistered = coincidence;
                break;
            }
        }

        return alreadyRegistered == null
          ? ServiceStatus.NOT_FOUND
          : alreadyRegistered;
    }
}
