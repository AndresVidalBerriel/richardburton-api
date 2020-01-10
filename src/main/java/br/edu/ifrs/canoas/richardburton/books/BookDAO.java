package br.edu.ifrs.canoas.richardburton.books;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import br.edu.ifrs.canoas.richardburton.util.BaseDAO;

@Stateless
public class BookDAO<E extends Book> extends BaseDAO<E, Long> {

    private static final long serialVersionUID = 1L;

    @Inject
    private AuthorDAO authorDAO;

    @Inject
    private PublicationDAO publicationDAO;

    @SuppressWarnings("unchecked")
    public List<E> getByTitle(String title) {

        String titlesSubqueryString = "(SELECT publication.title FROM Publication publication WHERE publication.book = book)";
        String booksQueryString = "SELECT book FROM Book book WHERE (:title) IN " + titlesSubqueryString;
        Query query = em.createQuery(booksQueryString);
        query.setParameter("title", title);

        return (List<E>) query.getResultList();
    }

    public E retrieve(E book) {

        if (book.getId() != null)
            return retrieve(book.getId());

        Stream<Publication> publicationStream = book.getPublications().stream();
        Stream<String> titleStream = publicationStream.map(publication -> publication.getTitle());
        Stream<List<E>> resultStream = titleStream.map(title -> getByTitle(title));
        List<E> coincidences = resultStream.flatMap(list -> list.stream()).collect(Collectors.toList());

        E alreadyRegistered = null;

        for (E coincidence : coincidences) {

            boolean sameAuthors = book.getAuthors().equals(coincidence.getAuthors());

            publicationStream = book.getPublications().stream();
            titleStream = publicationStream.map(publication -> publication.getTitle());

            if (sameAuthors) {
                alreadyRegistered = coincidence;
                break;
            }
        }

        return alreadyRegistered;
    }

    @Override
    public E create(E book) {

        E alreadyRegistered = retrieve(book);

        if (alreadyRegistered == null) {

            book.getAuthors().stream().forEach(author -> author.addBook(book));
            book.setAuthors(authorDAO.create(book.getAuthors()));
            book.getPublications().stream().forEach(publication -> publication.setBook(book));
            book.setPublications(publicationDAO.create(book.getPublications()));

            return super.create(book);

        } else {

            List<Publication> registeredPublications = alreadyRegistered.getPublications();

            for (Publication publication : book.getPublications()) {

                publication.setBook(alreadyRegistered);
                int index = registeredPublications.indexOf(publication);

                if (index == -1) {

                    registeredPublications.add(publication);

                } else {

                    Publication registeredPublication = registeredPublications.get(index);

                    if (registeredPublication.getIsbn() == null) {

                        registeredPublication.setIsbn(publication.getIsbn());
                    }
                }
            }

            return update(alreadyRegistered);
        }
    }

}