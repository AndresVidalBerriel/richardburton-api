package br.edu.ifrs.canoas.richardburton.books;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class TranslatedBookDAOImpl extends BookDAOImpl<TranslatedBook> implements TranslatedBookDAO {

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


}
