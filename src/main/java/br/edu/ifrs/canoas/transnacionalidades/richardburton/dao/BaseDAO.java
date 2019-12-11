package br.edu.ifrs.canoas.transnacionalidades.richardburton.dao;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@Stateless
public abstract class BaseDAO<E, ID> implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    protected EntityManager em;

    @SuppressWarnings("rawtypes")
    private Class entity;

    @SuppressWarnings("rawtypes")
    public Class getEntityClass() {
        return entity != null ? entity
                : (entity = (Class) ((ParameterizedType) getClass().getGenericSuperclass())
                        .getActualTypeArguments()[0]);
    }

    public E create(E e) {
        em.persist(e);
        return e;
    }

    @SuppressWarnings("unchecked")
    public E retrieve(ID id) {
        return (E) em.find(getEntityClass(), id);
    }

    public E update(E e) {
        return em.merge(e);
    }

    public void delete(ID id) {
        @SuppressWarnings("unchecked")
        Object ref = em.getReference(getEntityClass(), id);
        em.remove(ref);
    }

    @SuppressWarnings("unchecked")
    public List<E> retrieveAll() {
        @SuppressWarnings("rawtypes")
        Class entityClass = getEntityClass();
        String queryString = "Select e FROM " + entityClass.getSimpleName() + " e";
        Query query = em.createQuery(queryString, entityClass);
        return (List<E>) query.getResultList();
    }
}