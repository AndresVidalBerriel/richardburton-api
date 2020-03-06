package br.edu.ifrs.canoas.richardburton.books;

import br.edu.ifrs.canoas.richardburton.DuplicateEntityException;
import br.edu.ifrs.canoas.richardburton.EntityValidationException;
import br.edu.ifrs.canoas.richardburton.EntityServiceImpl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

@Stateless
public class AuthorServiceImpl extends EntityServiceImpl<Author, Long> implements AuthorService {

    @Inject
    private AuthorDAO authorDAO;

    @Override
    protected AuthorDAO getDAO() {
        return authorDAO;
    }

    @Override
    protected void throwValidationException(Set<ConstraintViolation<Author>> violations) throws AuthorValidationException {

        throw new AuthorValidationException(violations);
    }

    @Override
    public Author create(Author author) throws EntityValidationException, DuplicateEntityException {

        Author registered = retrieve(author.getName());
        return registered != null ? registered : super.create(author);
    }

    @Override
    public Author retrieve(String name) {

        return authorDAO.retrieve(name);
    }

    @Override
    public List<Author> search(Long afterId, int pageSize, String queryString) {

        return authorDAO.search(afterId, pageSize, queryString);
    }
}