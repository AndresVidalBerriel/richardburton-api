package br.edu.ifrs.canoas.richardburton.books;

import br.edu.ifrs.canoas.richardburton.DuplicateEntityException;
import br.edu.ifrs.canoas.richardburton.EntityValidationException;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import java.util.Set;

public class TranslatedBookServiceImpl extends BookServiceImpl<TranslatedBook> implements TranslatedBookService {

    @Inject
    private OriginalBookService originalBookService;

    @Inject
    private TranslatedBookDAO translatedBookDAO;

    @Override
    protected BookDAO<TranslatedBook> getDAO() {
        return translatedBookDAO;
    }

    @Override
    protected void throwValidationException(Set<ConstraintViolation<TranslatedBook>> violations) throws TranslatedBookValidationException {

        throw new TranslatedBookValidationException(violations);
    }

    @Override
    protected void throwDuplicateException() throws TranslatedBookDuplicateException {

        throw new TranslatedBookDuplicateException("A Translated Book containing the provided data is already registered.");
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

    @Override
    public TranslatedBook create(TranslatedBook translation) throws EntityValidationException, DuplicateEntityException {

        OriginalBook original = translation.getOriginal();
        original = originalBookService.create(original);
        translation.setOriginal(original);

        return super.create(translation);
    }

    @Override
    public TranslatedBook retrieve(Long id) {

        return translatedBookDAO.retrieve(id);
    }
}
