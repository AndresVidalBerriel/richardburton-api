package br.edu.ifrs.canoas.richardburton.books;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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

        } catch (ConstraintViolationException e) {

            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@DefaultValue("-1") @QueryParam("after-id") Long afterId,
            @DefaultValue("15") @QueryParam("page-size") int pageSize,
            @DefaultValue("*:*") @QueryParam("search") String search,
            @QueryParam("use-default-fields") boolean useDefaultFields) {

        List<TranslatedBook> translations =
                translatedBookService.search(afterId, pageSize, search, useDefaultFields);

        if (translations != null) {

            return Response.ok(translations).build();
        } else {

            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieve(@PathParam("id") Long id) {

        TranslatedBook translation = translatedBookService.retrieve(id);

        if (translation != null) {

            return Response.ok(translation).build();

        } else {

            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
