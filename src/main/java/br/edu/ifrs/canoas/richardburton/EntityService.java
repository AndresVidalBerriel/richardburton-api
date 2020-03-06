package br.edu.ifrs.canoas.richardburton;

import javax.ejb.Local;
import java.util.List;
import java.util.Set;

@Local
public interface EntityService<E, ID> {

    E create(E e) throws EntityValidationException, DuplicateEntityException;

    E retrieve(ID id);

    E update(E e) throws EntityValidationException;

    void delete(ID id);

    List<E> create(List<E> es) throws EntityValidationException, DuplicateEntityException;

    Set<E> create(Set<E> es) throws EntityValidationException, DuplicateEntityException;

    List<E> retrieve();

    void validate(E e) throws EntityValidationException;
}
