package br.edu.ifrs.canoas.richardburton.books;

import javax.ejb.Local;
import java.util.List;

@Local
public interface TranslatedBookService extends BookService<TranslatedBook> {

    TranslatedBook create(TranslatedBook translation) throws NoNewDataException;

    List<TranslatedBook> retrieveAll(Long afterId, int pageSize);

    TranslatedBook retrieve(Long id);

    List<TranslatedBook> search(Long afterId, int pageSize, String search, boolean useDefaultFields);
}
