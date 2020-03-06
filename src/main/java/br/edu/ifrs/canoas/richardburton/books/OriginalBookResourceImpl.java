package br.edu.ifrs.canoas.richardburton.books;

import javax.ejb.Stateless;
import javax.inject.Inject;


@Stateless
public class OriginalBookResourceImpl extends BookResourceImpl<OriginalBook> implements OriginalBookResource {

    @Inject
    private OriginalBookService originalBookService;

    @Override
    protected OriginalBookService getService() {
        return originalBookService;
    }
}
