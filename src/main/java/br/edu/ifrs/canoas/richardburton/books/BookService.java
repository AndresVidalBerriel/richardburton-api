package br.edu.ifrs.canoas.richardburton.books;

import br.edu.ifrs.canoas.richardburton.EntityService;

import javax.ejb.Local;
import java.util.List;

@Local
public interface BookService<E extends Book> extends EntityService<E, Long> {

    E retrieve(E book);

    List<E> search(Long afterId, int pageSize, String queryString, boolean useDefaultFields);
}
