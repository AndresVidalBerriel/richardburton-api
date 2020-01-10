package br.edu.ifrs.canoas.transnacionalidades.richardburton.dao;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import br.edu.ifrs.canoas.transnacionalidades.richardburton.entities.Book;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.entities.Publication;

@Stateless
public class BookDAO<E extends Book> extends BaseDAO<E, Long> {

    private static final long serialVersionUID = 1L;

    @Inject
    private PublicationDAO publicationDAO;

    @SuppressWarnings("unchecked")
    public List<E> getByTitle(String title) {

        String titlesSubqueryString = "(SELECT publication.title FROM Publication publication WHERE publication.book = book)";
        String booksQueryString = "SELECT book FROM OriginalBook book WHERE (:title) IN " + titlesSubqueryString;
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

            for (Publication publication : book.getPublications()) {

                publicationDAO.create(publication);
            }
            return super.create(book);
        }

        else {

            List<Publication> publications = alreadyRegistered.getPublications();

            for (Publication publication : book.getPublications()) {

                publication.setBook(alreadyRegistered);
                if (!publications.contains(publication)) {
                    publications.add(publication);
                    publicationDAO.create(publication);
                }
            }

            return update(alreadyRegistered);
        }
    }

}