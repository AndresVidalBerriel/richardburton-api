package br.edu.ifrs.canoas.richardburton.books;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class TranslatedBookService {

    @Inject
    private TranslatedBookDAO translatedBookDAO;

    public TranslatedBook create(TranslatedBook translation) throws NoNewDataException {

        TranslatedBook alreadyRegistered = translatedBookDAO.retrieve(translation);
        if (alreadyRegistered != null) {

            translation.setId(alreadyRegistered.getId());

            for (Publication publication : translation.getPublications()) {

                publication.setBook(alreadyRegistered);
            }

            if (alreadyRegistered.getPublications().containsAll(translation.getPublications())) {

                throw new NoNewDataException("The provided data is already registered.");

            }
        }

        return translatedBookDAO.create(translation);
    }

}