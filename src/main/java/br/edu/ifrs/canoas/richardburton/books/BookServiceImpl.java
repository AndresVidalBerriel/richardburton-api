package br.edu.ifrs.canoas.richardburton.books;

import br.edu.ifrs.canoas.richardburton.DuplicateEntityException;
import br.edu.ifrs.canoas.richardburton.EntityValidationException;
import br.edu.ifrs.canoas.richardburton.ServiceImpl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class BookServiceImpl<E extends Book> extends ServiceImpl<E, Long> implements BookService<E> {

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

    protected abstract void throwDuplicateException() throws DuplicateEntityException;

    @Override
    public List<E> search(Long afterId, int pageSize, String queryString, boolean useDefaultFields) {

        return bookDAO.search(afterId, pageSize, queryString, useDefaultFields ? getDefaultSearchFields() : null);
    }


    @Override
    public E create(E book) throws EntityValidationException, DuplicateEntityException {

        E registered = retrieve(book);

        if (registered == null) {

            Set<Author> authors = new HashSet<>();
            for (Author author : book.getAuthors()) {

                author = authorService.create(author);
                authors.add(author);
            }
            book.setAuthors(authors);

            for (Publication publication : book.getPublications()) {

                publicationService.validate(publication);
                publication.setBook(book);
            }

            return super.create(book);

        } else {

            ArrayList<Publication> registeredPublications = new ArrayList<>(registered.getPublications());


            // Validate and attach publications to the already registered book

            for(Publication publication : book.getPublications()) {

                publicationService.validate(publication);
                publication.setBook(registered);
            }


            // Add just the new publications to the already registered book

            boolean newData = false;

            for (Publication publication : book.getPublications()) {

                int index = registeredPublications.indexOf(publication);

                if (index == -1) {

                    // If the publication is not registered, mark it to be registered

                    registeredPublications.add(publication);
                    newData = true;

                }
            }

            registered.setPublications(new HashSet<>(registeredPublications));

            if (!newData) {

                throwDuplicateException();
                return registered;
            }

            return update(registered);
        }
    }

    @Override
    public E retrieve(E book) {

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

        return alreadyRegistered;
    }
}
