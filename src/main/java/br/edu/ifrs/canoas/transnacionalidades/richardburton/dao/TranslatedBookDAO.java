package br.edu.ifrs.canoas.transnacionalidades.richardburton.dao;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.edu.ifrs.canoas.transnacionalidades.richardburton.entities.OriginalBook;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.entities.TranslatedBook;;

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