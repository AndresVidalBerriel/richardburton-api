package br.edu.ifrs.canoas.richardburton.books;

import br.edu.ifrs.canoas.richardburton.DuplicateEntityException;
import br.edu.ifrs.canoas.richardburton.EntityValidationException;
import br.edu.ifrs.canoas.richardburton.EntityService;

import java.util.Set;

public interface PublicationService extends EntityService<Publication, Long> {

    Publication retrieve(Publication publication);

    Set<Publication> merge(Set<Publication> mainSet, Set<Publication> otherSet) throws EntityValidationException, DuplicateEntityException;
}
