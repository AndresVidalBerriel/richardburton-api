package br.edu.ifrs.canoas.transnacionalidades.richardburton;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Year;
import java.util.Arrays;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import br.edu.ifrs.canoas.transnacionalidades.richardburton.entities.OriginalBook;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.entities.Publication;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.entities.TranslatedBook;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.services.TranslatedBookService;

@WebListener
public class DataInitializer implements ServletContextListener {

    @Inject
    private TranslatedBookService translatedBookService;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        String pathToData = "/opt/richardburton/data.csv";
        String line = "";

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(pathToData), "ISO-8859-1"))) {

            while ((line = br.readLine()) != null) {

                String[] data = line.split("(;)(?=(?:[^\"]|\"[^\"]*\")*$)");

                OriginalBook original = new OriginalBook();
                original.setAuthors(Arrays.asList(data[0].split(" and ")));

                Publication originalPublication = new Publication();
                originalPublication.setBook(original);
                originalPublication.setTitle(data[3]);
                originalPublication.setCountry("BR");
                originalPublication.setYear(Year.of(0));
                original.setPublications(Arrays.asList(originalPublication));

                TranslatedBook translation = new TranslatedBook();
                translation.setAuthors(Arrays.asList(data[5].split(" and ")));
                translation.setOriginal(original);

                Publication translationPublication = new Publication();
                translationPublication.setBook(translation);
                translationPublication.setTitle(data[4]);
                translationPublication.setCountry(data[2]);
                translationPublication.setPublisher(data[6]);
                translationPublication.setYear(Year.of(Integer.parseInt(data[1])));
                translation.setPublications(Arrays.asList(translationPublication));

                original.setTranslations(Arrays.asList(translation));

                translatedBookService.create(translation);
            }

        } catch (IOException e) {

            e.printStackTrace();
        }

    }

}