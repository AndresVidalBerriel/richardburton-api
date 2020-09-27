package br.edu.ifrs.canoas.richardburton.books;

import br.edu.ifrs.canoas.richardburton.util.ServiceError;
import br.edu.ifrs.canoas.richardburton.util.ServiceResponse;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

@Stateless
public class TranslatedBookResourceImpl extends BookResourceImpl<TranslatedBook> implements TranslatedBookResource {

    @Inject
    private TranslatedBookService translatedBookService;

    @Override
    protected TranslatedBookService getService() {
        return translatedBookService;
    }

    @Override
    public Response create(TranslatedBook translation) {

        ServiceResponse response = translatedBookService.create(translation);

        switch(response.status()) {

            case CONFLICT:
                return Response
                  .status(Response.Status.CONFLICT)
                  .build();

            case INVALID_ENTITY:
                return Response
                  .status(Response.Status.BAD_REQUEST)
                  .entity(response.descriptor())
                  .build();

            case OK:
                return Response
                  .ok(response.entity())
                  .build();

            default:
                return Response
                  .status(response.status().toHttpStatus())
                  .build();
        }
    }

    @Override
    public Response retrieve(Long id) {

        ServiceResponse response = translatedBookService.retrieve(id);

        switch(response.status()) {
            case NOT_FOUND:
                return Response
                  .status(Response.Status.NOT_FOUND)
                  .build();

            case OK:
                return Response
                  .ok(response.entity())
                  .build();

            default:
                return Response
                  .status(response.status().toHttpStatus())
                  .build();
        }

    }
}
