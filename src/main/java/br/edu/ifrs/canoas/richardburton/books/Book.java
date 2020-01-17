package br.edu.ifrs.canoas.richardburton.books;

import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.NumericField;
import org.hibernate.search.annotations.SortableField;
import org.hibernate.search.annotations.Store;

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
    private Set<Author> authors;

    @NotNull
    @IndexedEmbedded
    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Publication> publications;

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
        return "Book [authors="
                + authors.stream().map(a -> a.getName()).collect(Collectors.joining(",")) + ", id="
                + id + ",\n publications=\n"
                + publications.stream().map(p -> p.toString()).collect(Collectors.joining("\n"))
                + "]";
    }


}
