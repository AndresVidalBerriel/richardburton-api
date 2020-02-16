package br.edu.ifrs.canoas.richardburton.books;

import br.edu.ifrs.canoas.richardburton.DAO;

import javax.ejb.Local;
import java.util.List;

@Local
public interface BookDAO<E extends Book> extends DAO<E, Long> {

    List<E> retrieve(String title);

    List<E> search(
            Long afterId,
            int pageSize,
            String queryString,
            String[] onFields
    );

}
