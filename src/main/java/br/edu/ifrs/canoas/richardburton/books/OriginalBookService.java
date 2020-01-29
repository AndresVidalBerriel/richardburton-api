package br.edu.ifrs.canoas.richardburton.books;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class OriginalBookService {

    @Inject
    private OriginalBookDAO originalBookDAO;

    public OriginalBook create(OriginalBook original) {

        return originalBookDAO.create(original);
    }

    public List<OriginalBook> search(Long afterId, int pageSize, String queryString) {

        return originalBookDAO.search(afterId, pageSize, queryString);
    }

}
