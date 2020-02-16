package br.edu.ifrs.canoas.richardburton.books;

import javax.ejb.Local;

@Local
public interface TranslatedBookService extends BookService<TranslatedBook> {
}
