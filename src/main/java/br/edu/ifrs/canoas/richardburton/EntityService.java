package br.edu.ifrs.canoas.richardburton;

import br.edu.ifrs.canoas.richardburton.util.ServiceEntity;
import br.edu.ifrs.canoas.richardburton.util.ServiceResponse;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Local
public interface EntityService<E extends ServiceEntity, ID> {

    ServiceResponse create(E e);

    ServiceResponse create(Set<E> es);

    ServiceResponse retrieve(ID id);

    ServiceResponse update(E e);

    ServiceResponse delete(ID id);

    ServiceResponse validate(E e);

    List<E> retrieve();
}
