package br.edu.ifrs.canoas.richardburton;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Year;
import java.util.Arrays;
import java.util.HashSet;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import br.edu.ifrs.canoas.richardburton.books.Author;
import br.edu.ifrs.canoas.richardburton.books.NoNewDataException;
import br.edu.ifrs.canoas.richardburton.books.OriginalBook;
import br.edu.ifrs.canoas.richardburton.books.Publication;
import br.edu.ifrs.canoas.richardburton.books.TranslatedBook;
import br.edu.ifrs.canoas.richardburton.books.TranslatedBookService;

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

                HashSet<Author> originalAuthors = new HashSet<Author>();
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
                original.setPublications(new HashSet<>(Arrays.asList(originalPublication)));

                TranslatedBook translation = new TranslatedBook();

                HashSet<Author> translationAuthors = new HashSet<Author>();
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
                translation.setPublications(new HashSet<>(Arrays.asList(translationPublication)));

                original.setTranslations(Arrays.asList(translation));

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
