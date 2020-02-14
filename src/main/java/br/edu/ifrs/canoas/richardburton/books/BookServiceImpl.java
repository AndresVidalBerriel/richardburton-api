package br.edu.ifrs.canoas.richardburton.books;

import java.util.List;

public abstract class BookServiceImpl<E extends Book> implements BookService<E> {

    protected abstract BookDAO<E> getDAO();

    protected abstract String[] getDefaultSearchFields();

    public List<E> search(Long afterId, int pageSize, String queryString, boolean useDefaultFields) {

        return getDAO().search(afterId, pageSize, queryString, useDefaultFields ? getDefaultSearchFields() : null);
    }
}
