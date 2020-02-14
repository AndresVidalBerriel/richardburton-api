package br.edu.ifrs.canoas.richardburton.books;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public interface BookResource<E extends Book> {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response search(
            @DefaultValue("-1") @QueryParam("after-id") Long afterId,
            @DefaultValue("15") @QueryParam("page-size") int pageSize,
            @DefaultValue("*:*") @QueryParam("search") String queryString,
            @QueryParam("use-default-fields") boolean useDefaultFields
    );
}
