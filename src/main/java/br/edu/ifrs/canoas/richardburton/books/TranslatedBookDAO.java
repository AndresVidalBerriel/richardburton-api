package br.edu.ifrs.canoas.richardburton.books;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class TranslatedBookDAO extends BookDAO<TranslatedBook> {

    private static final long serialVersionUID = 1L;

    @Inject
    private OriginalBookDAO originalBookDAO;

    @Override
    public TranslatedBook create(TranslatedBook translation) {

        OriginalBook original = originalBookDAO.create(translation.getOriginal());
        translation.setOriginal(original);
        return super.create(translation);
    }
}