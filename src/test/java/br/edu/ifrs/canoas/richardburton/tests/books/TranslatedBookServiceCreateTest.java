package br.edu.ifrs.canoas.richardburton.tests.books;

import br.edu.ifrs.canoas.richardburton.books.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Year;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TranslatedBookServiceCreateTest {

    @Mock
    private TranslatedBookDAO translatedBookDAO;

    @InjectMocks
    private TranslatedBookService translatedBookService = new TranslatedBookService();

    @Test
    public void successNewTranslation() throws NoNewDataException {

        TranslatedBook translation = new TranslatedBook();

        when(translatedBookDAO.retrieve(translation)).thenReturn(null);
        when(translatedBookDAO.create(translation)).thenReturn(translation);

        TranslatedBook created = translatedBookService.create(translation);
        assertEquals(translation, created);
    }

    @Test
    public void successAlreadyRegisteredButThereAreNewPublications() throws NoNewDataException {

        TranslatedBook translation = new TranslatedBook();

        Publication p1 = new Publication();
        p1.setTitle("Title");
        p1.setCountry("US");
        p1.setYear(Year.of(2020));
        p1.setPublisher("Publisher");
        p1.setBook(translation);

        Publication p2 = new Publication();
        p2.setTitle("Title");
        p2.setCountry("GB");
        p2.setYear(Year.of(2020));
        p2.setPublisher("Publisher");
        p2.setBook(translation);

        translation.setPublications(Set.of(p1, p2));

        TranslatedBook alreadyRegistered = new TranslatedBook();
        alreadyRegistered.setId(1L);

        Publication p3 = new Publication();
        p3.setTitle("Title");
        p3.setCountry("NZ");
        p3.setYear(Year.of(2020));
        p3.setPublisher("Publisher");
        p3.setBook(alreadyRegistered);

        alreadyRegistered.setPublications(Set.of(p3));

        when(translatedBookDAO.retrieve(translation)).thenReturn(alreadyRegistered);
        when(translatedBookDAO.create(translation)).thenReturn(translation);

        TranslatedBook created = translatedBookService.create(translation);

        for(Publication p : created.getPublications()) {

            assertEquals("New translation's publications' book must be the already registered one.", alreadyRegistered, p.getBook());

        }

        assertEquals("New translation's id must match with the already registered's one.", alreadyRegistered.getId(), translation.getId());
    }

    @Test(expected = NoNewDataException.class)
    public void failAlreadyRegisteredButThereAreNoNewPublications() throws NoNewDataException {

        TranslatedBook translation = new TranslatedBook();

        Publication p1 = new Publication();
        p1.setTitle("Title");
        p1.setCountry("US");
        p1.setYear(Year.of(2020));
        p1.setPublisher("Publisher");
        p1.setBook(translation);

        translation.setPublications(Set.of(p1));

        TranslatedBook alreadyRegistered = new TranslatedBook();
        alreadyRegistered.setId(1L);

        Publication p2 = new Publication();
        p2.setTitle("Title");
        p2.setCountry("GB");
        p2.setYear(Year.of(2020));
        p2.setPublisher("Publisher");
        p2.setBook(alreadyRegistered);

        Publication p3 = new Publication();
        p3.setTitle("Title");
        p3.setCountry("US");
        p3.setYear(Year.of(2020));
        p3.setPublisher("Publisher");
        p3.setBook(alreadyRegistered);

        alreadyRegistered.setPublications(Set.of(p2, p3));

        when(translatedBookDAO.retrieve(translation)).thenReturn(alreadyRegistered);

        translatedBookService.create(translation);
    }
}
