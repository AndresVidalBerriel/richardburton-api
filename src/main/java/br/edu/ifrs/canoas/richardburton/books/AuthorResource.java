package br.edu.ifrs.canoas.richardburton.books;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/authors")
public interface AuthorResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Author> search(
            @DefaultValue("-1") @QueryParam("after-id") Long afterId,
            @DefaultValue("15") @QueryParam("page-size") int pageSize,
            @DefaultValue("*:*") @QueryParam("search") String queryString
    );
}
