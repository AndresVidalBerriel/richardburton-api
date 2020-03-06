package br.edu.ifrs.canoas.richardburton.books;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import java.util.Set;

@Stateless
public class OriginalBookServiceImpl extends BookServiceImpl<OriginalBook> implements OriginalBookService {

    @Inject
    private OriginalBookDAO originalBookDAO;

    @Override
    protected OriginalBookDAO getDAO() {

        return originalBookDAO;
    }

    @Override
    protected void throwValidationException(Set<ConstraintViolation<OriginalBook>> violations) throws OriginalBookValidationException {

        throw new OriginalBookValidationException(violations);
    }

    @Override
    protected void throwDuplicateException() {
    }

    @Override
    protected String[] getDefaultSearchFields() {

        return new String[]{
                "authors.name",
                "publications.title",
                "publications.year"
        };
    }
}
