package br.edu.ifrs.canoas.richardburton.books;

import javax.ejb.Local;
import java.util.List;

@Local
public interface BookService<E extends Book> {

    List<E> search(Long afterId, int pageSize, String queryString, boolean useDefaultFields);
}
