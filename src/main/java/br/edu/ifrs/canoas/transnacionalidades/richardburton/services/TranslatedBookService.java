package br.edu.ifrs.canoas.transnacionalidades.richardburton.services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.edu.ifrs.canoas.transnacionalidades.richardburton.dao.TranslatedBookDAO;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.entities.TranslatedBook;

@Stateless
public class TranslatedBookService {

    @Inject
    private TranslatedBookDAO translatedBookDAO;

    public TranslatedBook create(TranslatedBook translation) {

        return translatedBookDAO.create(translation);
    }

}