package br.edu.ifrs.canoas.richardburton.books;

import br.edu.ifrs.canoas.richardburton.DAO;

import javax.ejb.Local;
import java.util.List;

@Local
public interface AuthorDAO extends DAO<Author, Long> {

    Author retrieve(String name);

    List<Author> search(Long afterId, int pageSize, String queryString);
}
