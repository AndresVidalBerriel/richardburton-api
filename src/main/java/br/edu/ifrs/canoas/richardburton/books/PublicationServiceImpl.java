package br.edu.ifrs.canoas.richardburton.books;

import br.edu.ifrs.canoas.richardburton.DAO;
import br.edu.ifrs.canoas.richardburton.DuplicateEntityException;
import br.edu.ifrs.canoas.richardburton.EntityValidationException;
import br.edu.ifrs.canoas.richardburton.ServiceImpl;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import java.util.HashSet;
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

    @Override
    public Set<Publication> merge(Set<Publication> mainSet, Set<Publication> otherSet) throws EntityValidationException, DuplicateEntityException {

        boolean newData = false;

        Set<Publication> mergedSet = new HashSet<>();

        for (Publication publication : otherSet) {

            boolean merged = false;
            boolean exists = false;

            for (Publication registeredPublication : mainSet) {

                merged = registeredPublication.merge(publication);

                if (merged) {

                    registeredPublication = update(registeredPublication);
                    mergedSet.add(registeredPublication);
                    newData = true;
                    break;
                }

                exists = publication.equals(registeredPublication);
                if (exists) break;
            }

            if (!exists && !merged) {

                publication = create(publication);
                mergedSet.add(publication);
                newData = true;
            }
        }

        if(!newData) throw new PublicationDuplicateException("All the provided publications are already registered.");

        mergedSet.addAll(mainSet);

        return mergedSet;
    }
}
