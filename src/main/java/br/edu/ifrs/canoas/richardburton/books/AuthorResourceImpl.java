package br.edu.ifrs.canoas.richardburton.books;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class AuthorResourceImpl implements AuthorResource {

    @Inject
    private AuthorService authorService;

    public List<Author> search(Long afterId, int pageSize, String queryString) {

        return authorService.search(afterId, pageSize, queryString);
    }
}
