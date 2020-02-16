package br.edu.ifrs.canoas.richardburton.books;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.miscellaneous.TrimFilterFactory;
import org.apache.lucene.analysis.standard.StandardFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@AnalyzerDef(name = "authorsAnalyzer", tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class), filters = {
        @TokenFilterDef(factory = StandardFilterFactory.class), @TokenFilterDef(factory = LowerCaseFilterFactory.class),
        @TokenFilterDef(factory = TrimFilterFactory.class)})

@Entity
@Indexed
@Analyzer(definition = "authorsAnalyzer")
@JsonInclude(Include.NON_NULL)
public class Author {

    @Id
    @GeneratedValue
    @Field(name = "id_num", store = Store.YES)
    @NumericField(forField = "id_num")
    @SortableField(forField = "id_num")
    private Long id;

    @NotBlank
    @Field
    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.EAGER)
    @JsonBackReference
    @ContainedIn
    private List<Book> books = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        Author other = (Author) obj;
        if (name == null) {
            return other.name == null;
        } else return name.equals(other.name);
    }

    public void addBook(Book book) {

        this.books.add(book);
    }
}