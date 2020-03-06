package br.edu.ifrs.canoas.richardburton.books;

import br.edu.ifrs.canoas.richardburton.DuplicateEntityException;
import br.edu.ifrs.canoas.richardburton.EntityValidationException;
import br.edu.ifrs.canoas.richardburton.EntityServiceImpl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Set;
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

    protected abstract void throwDuplicateException() throws DuplicateEntityException;

    @Override
    public List<E> search(Long afterId, int pageSize, String queryString, boolean useDefaultFields) {

        return bookDAO.search(afterId, pageSize, queryString, useDefaultFields ? getDefaultSearchFields() : null);
    }


    @Override
    public E create(E book) throws EntityValidationException, DuplicateEntityException {

        E registered = retrieve(book);

        if (registered == null) {

            for (Author author : book.getAuthors()) author.addBook(book);
            Set<Author> authors = authorService.create(book.getAuthors());
            book.setAuthors(authors);

            book = super.create(book);

            for (Publication publication : book.getPublications()) publication.setBook(book);
            Set<Publication> publications = publicationService.create(book.getPublications());
            book.setPublications(publications);

            return book;

        } else {

            for (Publication publication : book.getPublications()) publication.setBook(registered);

            try {

                Set<Publication> publications = publicationService.merge(registered.getPublications(), book.getPublications());
                registered.setPublications(publications);
                return update(registered);

            } catch (PublicationDuplicateException e) {

                throwDuplicateException();
                return registered;
            }
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
