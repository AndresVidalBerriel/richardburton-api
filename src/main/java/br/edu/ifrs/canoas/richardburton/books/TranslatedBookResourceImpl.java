package br.edu.ifrs.canoas.richardburton.books;

import br.edu.ifrs.canoas.richardburton.Data;
import br.edu.ifrs.canoas.richardburton.DataInitializer;
import br.edu.ifrs.canoas.richardburton.DuplicateEntityException;
import br.edu.ifrs.canoas.richardburton.EntityValidationException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Stateless
public class TranslatedBookResourceImpl extends BookResourceImpl<TranslatedBook> implements TranslatedBookResource {

    @Inject
    private TranslatedBookService translatedBookService;

    @Override
    protected BookService<TranslatedBook> getService() {
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
