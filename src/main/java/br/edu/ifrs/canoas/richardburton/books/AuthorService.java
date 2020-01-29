package br.edu.ifrs.canoas.richardburton.books;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class AuthorService {

    @Inject
    private AuthorDAO authorDAO;

    public List<Author> search(Long afterId, int pageSize, String queryString) {

        return authorDAO.search(afterId, pageSize, queryString);
    }

}