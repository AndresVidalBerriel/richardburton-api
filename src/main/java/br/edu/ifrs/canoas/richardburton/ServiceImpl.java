package br.edu.ifrs.canoas.richardburton;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class ServiceImpl<E, ID> implements Service<E, ID> {

    private DAO<E, ID> dao;

    protected abstract DAO<E, ID> getDAO();

    protected abstract void throwValidationException(Set<ConstraintViolation<E>> violations) throws EntityValidationException;

    @PostConstruct
    private void init() {

        dao = getDAO();
    }

    @Override
    public void validate(E e) throws EntityValidationException {

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<E>> violations = validator.validate(e);
        if (violations.size() > 0) throwValidationException(violations);
    }

    @Override
    public E create(E e) throws EntityValidationException, DuplicateEntityException {

        validate(e);
        return dao.create(e);
    }

    @Override
    public E update(E e) throws EntityValidationException {

        validate(e);
        return dao.update(e);
    }

    @Override
    public E retrieve(ID id) {

        return dao.retrieve(id);
    }

    @Override
    public void delete(ID id) {

        dao.delete(id);
    }

    @Override
    public List<E> create(List<E> es) throws EntityValidationException, DuplicateEntityException {

        List<E> created = new ArrayList<>();
        for (E e : es) created.add(create(e));
        return created;
    }

    @Override
    public Set<E> create(Set<E> es) throws EntityValidationException, DuplicateEntityException {

        Set<E> created = new HashSet<>();
        for (E e : es) created.add(create(e));
        return created;
    }

    @Override
    public List<E> retrieve() {

        return dao.retrieve();
    }
}
