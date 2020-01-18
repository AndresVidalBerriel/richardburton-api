package br.edu.ifrs.canoas.richardburton.books;

import java.util.List;
import javax.ejb.Stateless;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

@Stateless
public class OriginalBookDAO extends BookDAO<OriginalBook> {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unchecked")
    public List<OriginalBook> search(Long afterId, int pageSize, String queryString) {

        FullTextEntityManager fem = Search.getFullTextEntityManager(em);
        QueryBuilder qb =
                fem.getSearchFactory().buildQueryBuilder().forEntity(OriginalBook.class).get();

        try {
            Analyzer analyzer = new StandardAnalyzer();
            QueryParser queryParser = new QueryParser("publications.title", analyzer);
            Query query = queryParser.parse(queryString);
            Query filter = qb.range().onField("id_num").above(afterId).excludeLimit().createQuery();
            Query filteredQuery = qb.bool().must(filter).must(query).createQuery();
            FullTextQuery ftq = fem.createFullTextQuery(filteredQuery, OriginalBook.class);

            ftq.setMaxResults(pageSize);
            ftq.setSort(qb.sort().byField("id_num").asc().createSort());

            return ftq.getResultList();

        } catch (ParseException e) {

            e.printStackTrace();
            return null;
        }
    }

}
