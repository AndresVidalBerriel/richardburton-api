package br.edu.ifrs.canoas.richardburton;

import br.edu.ifrs.canoas.richardburton.books.*;
import br.edu.ifrs.canoas.richardburton.auth.JWT;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Year;
import java.util.*;

public class Data {

    public static void initialize(TranslatedBookService translatedBookService) {

        String pathToData="";

        try {

            InputStream propertiesStream = JWT.class.getClassLoader().getResourceAsStream("dev.properties");
            Properties properties = new Properties();

            assert propertiesStream != null;
            properties.load(propertiesStream);

            pathToData = properties.getProperty("INITIAL_DATA_PATH");

        } catch(IOException e) {

            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathToData), StandardCharsets.ISO_8859_1))) {

            String line;

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
                original.setPublications(new HashSet<>(Set.of(originalPublication)));

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
                translation.setPublications(new HashSet<>(Set.of(translationPublication)));

                original.setTranslations(new ArrayList<>(Collections.singletonList(translation)));

                try {

                    translatedBookService.create(translation);

                } catch (EntityValidationException | DuplicateEntityException e) {

                    e.printStackTrace();
                }
            }

        } catch (IOException e) {

            e.printStackTrace();
        }

    }
}
