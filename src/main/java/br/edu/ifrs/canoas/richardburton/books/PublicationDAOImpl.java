package br.edu.ifrs.canoas.richardburton.books;

import br.edu.ifrs.canoas.richardburton.DAOImpl;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Stateless
public class PublicationDAOImpl extends DAOImpl<Publication, Long> implements PublicationDAO {

    @Override
    public Publication create(Publication publication) {

        Book book = publication.getBook();
        book = em.getReference(Book.class, book.getId());
        publication.setBook(book);
        return super.create(publication);
    }

    @Override
    public Publication retrieve(Publication publication) {

        try {

            String queryString = "" +
                    "SELECT publication FROM Publication publication " +
                    "WHERE publication.title = :title " +
                    "AND publication.year = :year " +
                    "AND publication.country = :country " +
                    "AND publication.publisher = :publisher " +
                    "AND publication.book = :book";

            TypedQuery<Publication> query = em.createQuery(queryString, Publication.class);
            query.setParameter("title", publication.getTitle());
            query.setParameter("year", publication.getYear());
            query.setParameter("country", publication.getCountry());
            query.setParameter("publisher", publication.getPublisher());
            query.setParameter("book", publication.getBook());
            return query.getSingleResult();

        } catch (NoResultException e) {

            return null;
        }
    }
}
