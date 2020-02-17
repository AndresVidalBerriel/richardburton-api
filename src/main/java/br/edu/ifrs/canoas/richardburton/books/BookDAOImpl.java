package br.edu.ifrs.canoas.richardburton.books;

import br.edu.ifrs.canoas.richardburton.DAOImpl;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import javax.inject.Inject;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class BookDAOImpl<E extends Book> extends DAOImpl<E, Long> implements BookDAO<E> {

    @Inject
    private PublicationDAO publicationDAO;

    @Override
    @SuppressWarnings("unchecked")
    public List<E> retrieve(String title) {

        String titlesSubqueryString = "(SELECT publication.title FROM Publication publication WHERE publication.book = book)";
        String booksQueryString = "SELECT book FROM Book book WHERE (:title) IN " + titlesSubqueryString;
        Query query = em.createQuery(booksQueryString);
        query.setParameter("title", title);

        return (List<E>) query.getResultList();
    }

    @Override
    public E create(E book) {

        em.persist(book);

        for (Publication publication : book.getPublications()) {

            publication.setBook(book);
            publicationDAO.create(publication);
        }

        return book;
    }

    @Override
    public E update(E book) {

        Set<Publication> publications = new HashSet<>();

        for (Publication publication : book.getPublications()) {

            publication.setBook(book);

            if (publication.getId() != null) {

                publication = publicationDAO.update(publication);

            } else {

                publicationDAO.create(publication);
            }

            publications.add(publication);
        }
        book.setPublications(publications);

        return em.merge(book);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<E> search(Long afterId, int pageSize, String queryString, String[] onFields) {

        FullTextEntityManager fem = Search.getFullTextEntityManager(em);
        QueryBuilder qb = fem.getSearchFactory().buildQueryBuilder().forEntity(getEntityClass()).get();

        try {

            Analyzer analyzer = new StandardAnalyzer();
            QueryParser queryParser;


            if (onFields == null) {

                queryParser = new QueryParser("publications.title", analyzer);

            } else {

                queryParser = new MultiFieldQueryParser(onFields, analyzer);
            }

            org.apache.lucene.search.Query query = queryParser.parse(queryString);
            org.apache.lucene.search.Query filter = qb.range().onField("id_num").above(afterId).excludeLimit().createQuery();
            org.apache.lucene.search.Query filteredQuery = qb.bool().must(filter).must(query).createQuery();
            FullTextQuery ftq = fem.createFullTextQuery(filteredQuery, getEntityClass());

            ftq.setMaxResults(pageSize);
            ftq.setSort(qb.sort().byField("id_num").asc().createSort());

            return ftq.getResultList();

        } catch (ParseException e) {

            e.printStackTrace();

            return null;
        }
    }

}
