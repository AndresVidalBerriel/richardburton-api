package br.edu.ifrs.canoas.richardburton.books;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class OriginalBookServiceImpl extends BookServiceImpl<OriginalBook> implements OriginalBookService {

    @Inject
    private OriginalBookDAO originalBookDAO;

    @Override
    protected BookDAO<OriginalBook> getDAO() {

        return originalBookDAO;
    }

    @Override
    protected String[] getDefaultSearchFields() {

        return new String[]{
                "authors.name",
                "publications.title",
                "publications.year"
        };
    }

    public OriginalBook create(OriginalBook original) {

        return originalBookDAO.create(original);
    }
}
