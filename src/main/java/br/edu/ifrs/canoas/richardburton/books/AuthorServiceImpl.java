package br.edu.ifrs.canoas.richardburton.books;

import br.edu.ifrs.canoas.richardburton.EntityServiceImpl;
import br.edu.ifrs.canoas.richardburton.util.ServiceResponse;
import br.edu.ifrs.canoas.richardburton.util.ServiceStatus;

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
    public ServiceResponse create(Author author) {

        ServiceResponse response = retrieve(author.getName());
        return response.ok()
          ? response
          : super.create(author);
    }

    @Override
    public ServiceResponse retrieve(String name) {

        Author author = authorDAO.retrieve(name);
        return author == null
          ? ServiceStatus.NOT_FOUND
          : author;
    }

    @Override
    public List<Author> search(Long afterId, int pageSize, String queryString) {

        return authorDAO.search(afterId, pageSize, queryString);
    }
}