package br.edu.ifrs.canoas.richardburton.books;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class OriginalBookService {

    @Inject
    private OriginalBookDAO originalBookDAO;

    public OriginalBook create(OriginalBook original) {

        return originalBookDAO.create(original);
    }

}