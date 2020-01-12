package br.edu.ifrs.canoas.richardburton.books;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.management.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

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

        /*
         * CriteriaBuilder cb = em.getCriteriaBuilder(); CriteriaQuery<TranslatedBook>
         * criteria = cb.createQuery(TranslatedBook.class); Root<TranslatedBook>
         * translatedBook = criteria.from(TranslatedBook.class);
         * ParameterExpression<Long> id = cb.parameter(Long.class);
         * criteria.select(translatedBook).where(cb.gt(translatedBook.get("id"), id));
         * 
         * TypedQuery<TranslatedBook> query = em.createQuery(criteria);
         */

        String queryString = "SELECT book FROM TranslatedBook book WHERE book.id > :afterId";
        TypedQuery<TranslatedBook> query = em.createQuery(queryString, TranslatedBook.class);
        query.setParameter("afterId", afterId);
        query.setMaxResults(pageSize);

        List<TranslatedBook> translations = query.getResultList();
        return translations;
    }
}