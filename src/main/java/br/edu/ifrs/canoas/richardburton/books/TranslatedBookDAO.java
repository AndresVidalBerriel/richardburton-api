package br.edu.ifrs.canoas.richardburton.books;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

@Stateless
public class TranslatedBookDAO extends BookDAO<TranslatedBook> {

    private static final long serialVersionUID = 1L;

    @Inject
    private OriginalBookDAO originalBookDAO;

    @Override
    public TranslatedBook create(TranslatedBook translation) {

        OriginalBook original = originalBookDAO.create(translation.getOriginal());
        translation.setOriginal(original);
        return super.create(translation);
    }

    public List<TranslatedBook> retrieveAll(Long afterId, int pageSize) {


        String queryString = "SELECT book FROM TranslatedBook book WHERE book.id > :afterId";
        TypedQuery<TranslatedBook> query = em.createQuery(queryString, TranslatedBook.class);
        query.setParameter("afterId", afterId);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<TranslatedBook> search(Long afterId, int pageSize, String queryString,
            boolean useDefaultFields) {

        FullTextEntityManager fem = Search.getFullTextEntityManager(em);
        QueryBuilder qb =
                fem.getSearchFactory().buildQueryBuilder().forEntity(TranslatedBook.class).get();


        try {

            Analyzer analyzer = new StandardAnalyzer();
            QueryParser queryParser;

            if (useDefaultFields) {


                String[] fields =
                        {"authors.name", "publications.title", "original.publications.title",
                                "original.authors.name", "publications.year"};

                queryParser = new MultiFieldQueryParser(fields, analyzer);

            } else {

                queryParser = new QueryParser("publications.title", analyzer);
            }

            Query query = queryParser.parse(queryString);
            Query filter = qb.range().onField("id_num").above(afterId).excludeLimit().createQuery();
            Query filteredQuery = qb.bool().must(filter).must(query).createQuery();
            FullTextQuery ftq = fem.createFullTextQuery(filteredQuery, TranslatedBook.class);

            ftq.setMaxResults(pageSize);
            ftq.setSort(qb.sort().byField("id_num").asc().createSort());

            return ftq.getResultList();


        } catch (ParseException e) {

            e.printStackTrace();

            return null;
        }
    }
}
