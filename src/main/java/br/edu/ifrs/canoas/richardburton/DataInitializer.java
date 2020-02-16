package br.edu.ifrs.canoas.richardburton;

import br.edu.ifrs.canoas.richardburton.books.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Year;
import java.util.*;

@WebListener
public class DataInitializer implements ServletContextListener {

    @Inject
    private TranslatedBookService translatedBookService;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        Data.initialize(translatedBookService);

    }

}
