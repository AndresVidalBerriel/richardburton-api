package br.edu.ifrs.canoas.richardburton.books;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonInclude(Include.NON_NULL)
public abstract class Book {

    @Id
    @GeneratedValue
    @Field(name = "id_num", store = Store.YES)
    @NumericField(forField = "id_num")
    @SortableField(forField = "id_num")
    private Long id;

    @NotEmpty
    @IndexedEmbedded
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Author> authors = new HashSet<>();

    @NotEmpty
    @IndexedEmbedded
    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    private Set<Publication> publications = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<Publication> getPublications() {
        return publications;
    }

    public void setPublications(Set<Publication> publications) {
        this.publications = publications;
    }

    @Override
    public String toString() {
        return "Book [authors=" + authors.stream().map(Author::getName).collect(Collectors.joining(",")) + ", id=" + id
                + ",\n publications=\n" + publications.stream().map(Publication::toString).collect(Collectors.joining("\n"))
                + "]";
    }

}
