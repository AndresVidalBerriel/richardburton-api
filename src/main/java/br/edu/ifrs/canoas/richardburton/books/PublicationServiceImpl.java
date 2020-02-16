package br.edu.ifrs.canoas.richardburton.books;

import br.edu.ifrs.canoas.richardburton.DAO;
import br.edu.ifrs.canoas.richardburton.EntityValidationException;
import br.edu.ifrs.canoas.richardburton.ServiceImpl;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import java.util.Set;

public class PublicationServiceImpl extends ServiceImpl<Publication, Long> implements PublicationService {

    @Inject
    private PublicationDAO publicationDAO;

    @Override
    protected DAO<Publication, Long> getDAO() {
        return publicationDAO;
    }

    @Override
    protected void throwValidationException(Set<ConstraintViolation<Publication>> violations) throws EntityValidationException {

        throw new PublicationValidationException(violations);
    }

    @Override
    public Publication retrieve(Publication publication) {
        return publicationDAO.retrieve(publication);
    }
}
