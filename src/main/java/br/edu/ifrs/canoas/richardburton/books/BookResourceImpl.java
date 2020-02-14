package br.edu.ifrs.canoas.richardburton.books;

import javax.ws.rs.core.Response;
import java.util.List;

public abstract class BookResourceImpl<E extends Book> implements BookResource<E> {

    protected abstract BookService<E> getService();

    public Response search(Long afterId, int pageSize, String search, boolean useDefaultFields) {

        List<E> books = getService().search(afterId, pageSize, search, useDefaultFields);

        if (books != null) {

            return Response.ok(books).build();

        } else {

            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
