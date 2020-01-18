package br.edu.ifrs.canoas.richardburton.books;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/originals")
@Stateless
public class OriginalBookResource {

    @Inject
    private OriginalBookService originalBookService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@DefaultValue("-1") @QueryParam("after-id") Long afterId,
            @DefaultValue("15") @QueryParam("page-size") int pageSize,
            @DefaultValue("*:*") @QueryParam("search") String queryString) {

        List<OriginalBook> originals = originalBookService.search(afterId, pageSize, queryString);
        return Response.ok(originals).build();
    }


}
