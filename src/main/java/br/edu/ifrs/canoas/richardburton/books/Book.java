package br.edu.ifrs.canoas.richardburton.books;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
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

    @IndexedEmbedded
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Author> authors = Collections.emptySet();

    @NotNull
    @IndexedEmbedded
    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Publication> publications = Collections.emptySet();

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
