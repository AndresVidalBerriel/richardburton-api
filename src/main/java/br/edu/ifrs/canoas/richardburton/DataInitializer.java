package br.edu.ifrs.canoas.richardburton;

import br.edu.ifrs.canoas.richardburton.books.*;

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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@WebListener
public class DataInitializer implements ServletContextListener {

    @Inject
    private TranslatedBookService translatedBookService;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        String pathToData = "/Users/andres/richardburton/data.csv";
        String line;

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(pathToData), StandardCharsets.ISO_8859_1))) {

            while ((line = br.readLine()) != null) {

                String[] data = line.split("(;)(?=(?:[^\"]|\"[^\"]*\")*$)");

                OriginalBook original = new OriginalBook();

                HashSet<Author> originalAuthors = new HashSet<>();
                for (String name : data[0].split(" and ")) {

                    Author author = new Author();
                    author.setName(name);
                    originalAuthors.add(author);
                }
                original.setAuthors(originalAuthors);

                Publication originalPublication = new Publication();
                originalPublication.setBook(original);
                originalPublication.setTitle(data[3]);
                originalPublication.setCountry("BR");
                originalPublication.setYear(Year.of(0));
                original.setPublications(Set.of(originalPublication));

                TranslatedBook translation = new TranslatedBook();

                HashSet<Author> translationAuthors = new HashSet<>();
                for (String name : data[5].split(" and ")) {

                    Author author = new Author();
                    author.setName(name);
                    translationAuthors.add(author);
                }
                translation.setAuthors(translationAuthors);
                translation.setOriginal(original);

                Publication translationPublication = new Publication();
                translationPublication.setBook(translation);
                translationPublication.setTitle(data[4]);
                translationPublication.setCountry(data[2]);
                translationPublication.setPublisher(data[6]);
                translationPublication.setYear(Year.of(Integer.parseInt(data[1])));
                translation.setPublications(Set.of(translationPublication));

                original.setTranslations(Collections.singletonList(translation));

                try {

                    translatedBookService.create(translation);

                } catch (NoNewDataException e) {

                    e.printStackTrace();
                }
            }

        } catch (IOException e) {

            e.printStackTrace();
        }

    }

}
