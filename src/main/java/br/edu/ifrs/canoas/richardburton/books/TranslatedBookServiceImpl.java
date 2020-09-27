package br.edu.ifrs.canoas.richardburton.books;

import br.edu.ifrs.canoas.richardburton.util.ServiceResponse;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import java.util.Set;

public class TranslatedBookServiceImpl extends BookServiceImpl<TranslatedBook> implements TranslatedBookService {

    @Inject
    private OriginalBookService originalBookService;

    @Inject
    private TranslatedBookDAO translatedBookDAO;

    @Override
    protected TranslatedBookDAO getDAO() {
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

    @Override
    public ServiceResponse create(TranslatedBook translation) {

        OriginalBook original = translation.getOriginal();

        ServiceResponse response = originalBookService.create(original);
        if(!response.ok()) return response;
        original = (OriginalBook) response;

        translation.setOriginal(original);

        return super.create(translation);
    }
}
