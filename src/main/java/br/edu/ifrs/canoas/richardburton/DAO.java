package br.edu.ifrs.canoas.richardburton;

import javax.ejb.Local;
import java.io.Serializable;
import java.util.List;

@Local
public interface DAO<E, ID> extends Serializable {

    E create(E e);

    E retrieve(ID id);

    E update(E e);

    void delete(ID id);

    List<E> retrieve();

    List<E> retrieve(ID afterId, int pageSize);
}