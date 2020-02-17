package br.edu.ifrs.canoas.richardburton.books;

import javax.ejb.Local;

@Local
public interface TranslatedBookDAO extends BookDAO<TranslatedBook> {
}
