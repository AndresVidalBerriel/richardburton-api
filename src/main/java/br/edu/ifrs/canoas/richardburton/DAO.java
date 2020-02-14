package br.edu.ifrs.canoas.richardburton;

import javax.ejb.Stateless;
import java.io.Serializable;
import java.util.List;

@Stateless
public interface DAO<E, ID> extends Serializable {

    E create(E e);

    E retrieve(ID id);

    E update(E e);

    void delete(ID id);

    List<E> retrieveAll();
}