package br.edu.ifrs.canoas.richardburton;

import br.edu.ifrs.canoas.richardburton.util.*;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Id;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class EntityServiceImpl<E extends ServiceEntity, ID> implements EntityService<E, ID> {

    private DAO<E, ID> dao;

    protected abstract DAO<E, ID> getDAO();

    @PostConstruct
    private void init() {

        dao = getDAO();
    }

    @Override
    public ServiceResponse validate(E e) {

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<E>> violations = validator.validate(e);

        return violations.size() > 0
          ? new ServiceError(ServiceStatus.INVALID_ENTITY, violations)
          : ServiceStatus.OK;
    }

    @Override
    public ServiceResponse create(E e) {

        ServiceResponse response = validate(e);
        return response.ok()
          ? dao.create(e)
          : response;
    }

    @Override
    public ServiceResponse create(Set<E> es) {
        Set<E> set = new HashSet<>();
        for (E e : es) {
            ServiceResponse response = create(e);
            if (!response.ok()) return response;
            set.add((E) response);
        }
        return new ServiceSet<>(set);
    }

    @Override
    public ServiceResponse update(E e) {

        ServiceResponse response = validate(e);
        return response.ok()
          ? dao.update(e)
          : response;
    }

    @Override
    public ServiceResponse retrieve(ID id) {
        E e = dao.retrieve(id);
        return e == null ? ServiceStatus.NOT_FOUND : e;
    }

    @Override
    public ServiceResponse delete(ID id) {
        try {
            dao.delete(id);
            return ServiceStatus.OK;
        } catch (EntityNotFoundException e) {
            return ServiceStatus.NOT_FOUND;
        }
    }

    @Override
    public List<E> retrieve() {

        return dao.retrieve();
    }
}
