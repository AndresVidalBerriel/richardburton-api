package br.edu.ifrs.canoas.richardburton.session;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/session")
public interface SessionResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    Response signIn(@Context HttpHeaders headers);
}