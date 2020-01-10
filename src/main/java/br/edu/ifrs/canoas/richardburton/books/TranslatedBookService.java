package br.edu.ifrs.canoas.richardburton.books;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class TranslatedBookService {

    @Inject
    private TranslatedBookDAO translatedBookDAO;

    public TranslatedBook create(TranslatedBook translation) {

        return translatedBookDAO.create(translation);
    }

}