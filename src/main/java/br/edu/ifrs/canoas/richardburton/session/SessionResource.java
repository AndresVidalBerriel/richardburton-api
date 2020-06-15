package br.edu.ifrs.canoas.richardburton.session;

import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/session")
public interface SessionResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    Response create(@HeaderParam(HttpHeaders.AUTHORIZATION) String auth);

    @GET
    Response refresh(@HeaderParam(HttpHeaders.AUTHORIZATION) String auth);

    @HEAD
    Response verify(@HeaderParam(HttpHeaders.AUTHORIZATION) String auth);

}