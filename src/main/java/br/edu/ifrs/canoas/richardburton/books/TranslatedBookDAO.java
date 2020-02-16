package br.edu.ifrs.canoas.richardburton.books;

import javax.ejb.Local;
import java.util.List;

@Local
public interface TranslatedBookDAO extends BookDAO<TranslatedBook> {

    TranslatedBook create(TranslatedBook translation);

    List<TranslatedBook> retrieve(Long afterId, int pageSize);

}
