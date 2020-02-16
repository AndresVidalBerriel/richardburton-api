package br.edu.ifrs.canoas.richardburton.books;

import br.edu.ifrs.canoas.richardburton.Service;

public interface PublicationService extends Service<Publication, Long> {

    Publication retrieve(Publication publication);
}
