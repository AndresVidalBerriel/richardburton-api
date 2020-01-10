package br.edu.ifrs.canoas.richardburton.books;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.edu.ifrs.canoas.richardburton.util.BaseDAO;

@Stateless
public class PublicationDAO extends BaseDAO<Publication, Long> {

    private static final long serialVersionUID = 1L;

    public Publication retrieve(Publication publication) {

        try {

            String queryString = "SELECT publication FROM Publication publication WHERE publication.title = :title AND publication.year = :year AND publication.country = :country AND publication.publisher = :publisher AND publication.book = :book";
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

    public List<Publication> create(List<Publication> publications) {

        Stream<Publication> publicationStream = publications.stream();
        publicationStream = publicationStream.map(publication -> create(publication));
        return publicationStream.collect(Collectors.toList());
    }

}