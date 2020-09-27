package br.edu.ifrs.canoas.richardburton;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class DAOImpl<E, ID> implements DAO<E, ID> {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    protected EntityManager em;

    @SuppressWarnings("rawtypes")
    private Class entity;

    @SuppressWarnings("rawtypes")
    protected Class getEntityClass() {
        return entity != null ? entity
                : (entity = (Class) ((ParameterizedType) getClass().getGenericSuperclass())
                        .getActualTypeArguments()[0]);
    }

    @Override
    public E create(E e) {
        em.persist(e);
        return e;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E retrieve(ID id) {
        return (E) em.find(getEntityClass(), id);
    }

    @Override
    public E update(E e) {
        return em.merge(e);
    }

    @Override
    public void delete(ID id) {
        @SuppressWarnings("unchecked")
        Object ref = em.getReference(getEntityClass(), id);
        em.remove(ref);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<E> retrieve() {
        String queryString = "Select e FROM " + getEntityClass().getSimpleName() + " e";
        Query query = em.createQuery(queryString);
        return (List<E>) query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<E> retrieve(ID afterId, int pageSize) {

        String queryString = "SELECT book FROM " + getEntityClass().getSimpleName() + " book WHERE book.id > :afterId";
        Query query = em.createQuery(queryString);
        query.setParameter("afterId", afterId);
        query.setMaxResults(pageSize);

        return (List<E>) query.getResultList();
    }

    @Override
    public boolean exists(ID id) {
        return retrieve(id) != null;
    }
}
