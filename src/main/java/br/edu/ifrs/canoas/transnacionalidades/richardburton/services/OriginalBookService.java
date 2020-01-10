package br.edu.ifrs.canoas.transnacionalidades.richardburton.services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.edu.ifrs.canoas.transnacionalidades.richardburton.dao.OriginalBookDAO;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.entities.OriginalBook;

@Stateless
public class OriginalBookService {

    @Inject
    private OriginalBookDAO originalBookDAO;

    public OriginalBook create(OriginalBook original) {

        return originalBookDAO.create(original);
    }

}