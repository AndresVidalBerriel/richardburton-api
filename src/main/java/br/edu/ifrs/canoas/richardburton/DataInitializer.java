package br.edu.ifrs.canoas.richardburton;

import br.edu.ifrs.canoas.richardburton.books.TranslatedBookService;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class DataInitializer implements ServletContextListener {

    @Inject
    private TranslatedBookService translatedBookService;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        Data.initialize(translatedBookService);
    }

}
