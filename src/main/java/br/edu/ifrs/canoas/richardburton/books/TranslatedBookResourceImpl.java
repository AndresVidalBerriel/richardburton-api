package br.edu.ifrs.canoas.richardburton.books;

import br.edu.ifrs.canoas.richardburton.DuplicateEntityException;
import br.edu.ifrs.canoas.richardburton.EntityValidationException;

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

        try {

            translation = translatedBookService.create(translation);
            return Response.ok(translation).build();

        } catch (DuplicateEntityException e) {

            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();

        } catch (EntityValidationException e) {

            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

    }

    @Override
    public Response retrieve(Long id) {

        TranslatedBook translation = translatedBookService.retrieve(id);

        if (translation != null) {

            return Response.ok(translation).build();

        } else {

            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
