package br.edu.ifrs.canoas.richardburton.books;

import br.edu.ifrs.canoas.richardburton.DAO;

import javax.ejb.Local;
import java.util.Set;

@Local
public interface PublicationDAO extends DAO<Publication, Long> {

    Publication retrieve(Publication publication);

    Set<Publication> create(Set<Publication> publications);
}
