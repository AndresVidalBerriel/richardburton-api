package br.edu.ifrs.canoas.richardburton.books;

import br.edu.ifrs.canoas.richardburton.Service;

import javax.ejb.Local;
import java.util.List;

@Local
public interface BookService<E extends Book> extends Service<E, Long> {

    E retrieve(E book);

    List<E> search(Long afterId, int pageSize, String queryString, boolean useDefaultFields);
}
