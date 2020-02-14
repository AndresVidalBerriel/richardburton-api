package br.edu.ifrs.canoas.richardburton.books;

import br.edu.ifrs.canoas.richardburton.DAO;

import javax.ejb.Local;
import java.util.List;
import java.util.Set;

@Local
public interface AuthorDAO extends DAO<Author, Long> {

    Author retrieve(String name);

    @Override
    Author create(Author author);

    Set<Author> create(Set<Author> authors);

    List<Author> search(Long afterId, int pageSize, String queryString);
}
