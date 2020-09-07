package br.edu.ifrs.canoas.richardburton.books;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/export")
public interface ExportBookData extends OriginalBookResource{

    @GET
    @Path("/dataCSV")
    @Produces("text/plain")    
    Response retrieveCSV();

    @GET
    @Path("/dataPDF")
    @Produces("application/pdf") 
    Response retrievePDF();    
    
    @GET
    @Path("/dataPDF2")
    @Produces("application/pdf") 
    Response retrievePDFSearch(
            @DefaultValue("-1") @QueryParam("after-id") Long afterId,
            @DefaultValue("15") @QueryParam("page-size") int pageSize,
            @DefaultValue("*:*") @QueryParam("search") String queryString,
            @QueryParam("use-default-fields") boolean useDefaultFields
    );

}