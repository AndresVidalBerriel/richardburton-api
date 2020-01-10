package br.edu.ifrs.canoas.transnacionalidades.richardburton.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonInclude(Include.NON_NULL)
public abstract class Book {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    @ElementCollection
    @OrderColumn
    private List<String> authors;

    @NotNull
    @OneToMany(mappedBy = "book")
    private List<Publication> publications;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public List<Publication> getPublications() {
        return publications;
    }

    public void setPublications(List<Publication> publications) {
        this.publications = publications;
    }
}