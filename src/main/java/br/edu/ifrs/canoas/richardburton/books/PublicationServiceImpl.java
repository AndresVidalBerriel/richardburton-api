package br.edu.ifrs.canoas.richardburton.books;

import br.edu.ifrs.canoas.richardburton.EntityServiceImpl;
import br.edu.ifrs.canoas.richardburton.util.ServiceResponse;
import br.edu.ifrs.canoas.richardburton.util.ServiceSet;
import br.edu.ifrs.canoas.richardburton.util.ServiceStatus;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

public class PublicationServiceImpl extends EntityServiceImpl<Publication, Long> implements PublicationService {

    @Inject
    private PublicationDAO publicationDAO;

    @Override
    protected PublicationDAO getDAO() {
        return publicationDAO;
    }

    @Override
    public Publication retrieve(Publication publication) {
        return publicationDAO.retrieve(publication);
    }

    @Override
    public ServiceResponse merge(Set<Publication> mainSet, Set<Publication> otherSet) {

        boolean newData = false;

        Set<Publication> mergedSet = new HashSet<>();

        for (Publication publication : otherSet) {

            boolean merged = false;
            boolean exists = false;

            for (Publication registeredPublication : mainSet) {

                merged = registeredPublication.merge(publication);

                if (merged) {

                    ServiceResponse response = update(registeredPublication);

                    if(response.ok()) {
                        mergedSet.add((Publication) response);
                        newData = true;
                        break;
                    } else return response;
                }

                exists = publication.equals(registeredPublication);
                if (exists) break;
            }

            if (!exists && !merged) {

                ServiceResponse response = create(publication);
                if(response.ok()) {
                    publication = (Publication) response;
                    mergedSet.add(publication);
                    newData = true;
                } else return response;
            }
        }

        if (!newData) return ServiceStatus.CONFLICT;
        mergedSet.addAll(mainSet);

        return new ServiceSet<>(mergedSet);
    }
}
