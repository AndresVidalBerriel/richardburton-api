package br.edu.ifrs.canoas.richardburton.books;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/translations")
@Stateless
public class TranslatedBookResource {

    @Inject
    private TranslatedBookService translatedBookService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(TranslatedBook translation) {

        try {

            translation = translatedBookService.create(translation);
            return Response.ok(translation).build();

        } catch (NoNewDataException e) {

            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        }

    }

}