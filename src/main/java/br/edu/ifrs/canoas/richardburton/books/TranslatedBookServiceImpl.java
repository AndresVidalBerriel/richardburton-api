package br.edu.ifrs.canoas.richardburton.books;

import javax.inject.Inject;
import java.util.List;

public class TranslatedBookServiceImpl extends BookServiceImpl<TranslatedBook> implements TranslatedBookService {

    @Inject
    private TranslatedBookDAO translatedBookDAO;

    @Override
    protected BookDAO<TranslatedBook> getDAO() {
        return translatedBookDAO;
    }

    @Override
    protected String[] getDefaultSearchFields() {

        return new String[]{
                "authors.name",
                "publications.title",
                "original.publications.title",
                "original.authors.name",
                "publications.year"
        };
    }

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

    public List<TranslatedBook> retrieveAll(Long afterId, int pageSize) {

        return translatedBookDAO.retrieveAll(afterId, pageSize);
    }

    public TranslatedBook retrieve(Long id) {

        return translatedBookDAO.retrieve(id);
    }
}
