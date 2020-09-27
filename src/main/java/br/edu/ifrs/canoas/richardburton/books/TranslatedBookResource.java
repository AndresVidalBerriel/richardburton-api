package br.edu.ifrs.canoas.richardburton.books;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/translations")
public interface TranslatedBookResource extends BookResource<TranslatedBook> {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response create(TranslatedBook translation);

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response retrieve(@PathParam("id") Long id);
}
