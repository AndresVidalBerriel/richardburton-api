package br.edu.ifrs.canoas.richardburton.books;

import br.edu.ifrs.canoas.richardburton.DAOImpl;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Stateless
public class AuthorDAOImpl extends DAOImpl<Author, Long> implements AuthorDAO {

    private static final long serialVersionUID = 1L;

    public Author retrieve(String name) {

        try {

            String queryString = "SELECT author FROM Author author WHERE author.name = :name";
            TypedQuery<Author> query = em.createQuery(queryString, Author.class);
            query.setParameter("name", name);
            return query.getSingleResult();

        } catch (NoResultException e) {

            return null;
        }
    }

    @Override
    public Author create(Author author) {

        Author alreadyRegistered = retrieve(author.getName());
        return alreadyRegistered == null ? super.create(author) : alreadyRegistered;
    }

    public Set<Author> create(Set<Author> authors) {

        Stream<Author> authorStream = authors.stream();
        authorStream = authorStream.map(this::create);
        return authorStream.collect(Collectors.toSet());
    }

    @SuppressWarnings("unchecked")
    public List<Author> search(Long afterId, int pageSize, String queryString) {

        FullTextEntityManager fem = Search.getFullTextEntityManager(em);
        QueryBuilder qb = fem.getSearchFactory().buildQueryBuilder().forEntity(Author.class).get();

        try {
            Analyzer analyzer = new StandardAnalyzer();
            QueryParser queryParser = new QueryParser("name", analyzer);
            Query query = queryParser.parse(queryString);
            Query filter = qb.range().onField("id_num").above(afterId).excludeLimit().createQuery();
            Query filteredQuery = qb.bool().must(filter).must(query).createQuery();
            FullTextQuery ftq = fem.createFullTextQuery(filteredQuery, Author.class);

            ftq.setMaxResults(pageSize);
            ftq.setSort(qb.sort().byField("id_num").asc().createSort());

            return ftq.getResultList();

        } catch (ParseException e) {

            e.printStackTrace();
            return null;
        }
    }
}
