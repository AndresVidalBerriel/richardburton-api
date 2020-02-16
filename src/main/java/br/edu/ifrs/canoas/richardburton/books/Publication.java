package br.edu.ifrs.canoas.richardburton.books;

import br.edu.ifrs.canoas.richardburton.constraints.NullOrNotBlank;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.Year;

@Entity
@JsonInclude(Include.NON_NULL)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"book_id", "title", "publisher", "year", "country"}))
public class Publication {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @ContainedIn
    @ManyToOne
    @JsonBackReference
    private Book book;

    @Field
    @NullOrNotBlank
    private String publisher;

    @NotNull
    @Field(analyze = Analyze.NO)
    @FieldBridge(impl = YearFieldBridge.class)
    @PastOrPresent
    @Convert(converter = YearAttributeConverter.class)
    @JsonSerialize(using = YearSerializer.class)
    private Year year;

    @Field
    @NotBlank
    private String country;

    @Field
    @NotBlank
    private String title;

    @ISBN
    @Column(unique = true)
    private String isbn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public boolean matches(Publication other) {

        return country.equals(other.country) && title.equals(other.title) && year.equals(other.year);
    }

    public boolean merge(Publication other) {

        // Publications should just be merged if they match

        if(this.matches(other)) {

            // Check if there is no ISBN information to avoid inconsistencies

            if(isbn == null) {

                if (!this.equals(other)) {

                    // If the publications are not equal, check that the current one's publisher is missing.

                    if (this.publisher == null && other.publisher != null) {

                        // As the two publications identities only differ in the publisher,
                        // they should be merged.

                        this.publisher = other.publisher;

                    } else if (other.publisher != null) {

                        // If the current one publisher is not missing, but the other one
                        // contains a different publisher (as it's not null and 'this' and 'other' are not equal)
                        // merge must be aborted

                        return false;
                    }
                }

                // If merged was not aborted until this point, it's safe to add the other publication's
                // isbn information to this entity, as they may be the same.

                if (other.isbn != null) {

                    this.isbn = other.isbn;
                }

                return true;
            }
        }

        return false;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((book == null) ? 0 : book.hashCode());
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + ((publisher == null) ? 0 : publisher.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((year == null) ? 0 : year.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Publication other = (Publication) obj;
        if (book == null) {
            if (other.book != null)
                return false;
        } else if (!book.equals(other.book))
            return false;
        if (country == null) {
            if (other.country != null)
                return false;
        } else if (!country.equals(other.country))
            return false;
        if (publisher == null) {
            if (other.publisher != null)
                return false;
        } else if (!publisher.equals(other.publisher))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (year == null) {
            return other.year == null;
        } else
            return year.equals(other.year);
    }

    @Override
    public String toString() {
        return "Publication [country=" + country + ", id=" + id + ", isbn=" + isbn + ", publisher=" + publisher
                + ", title=" + title + ", year=" + year + "]";
    }

}
