package br.edu.ifrs.canoas.richardburton.books;

import br.edu.ifrs.canoas.richardburton.EntityService;
import br.edu.ifrs.canoas.richardburton.util.ServiceResponse;

import java.util.Set;

public interface PublicationService extends EntityService<Publication, Long> {

    Publication retrieve(Publication publication);

    ServiceResponse merge(Set<Publication> mainSet, Set<Publication> otherSet);
}
