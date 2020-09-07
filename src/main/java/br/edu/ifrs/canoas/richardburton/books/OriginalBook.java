package br.edu.ifrs.canoas.richardburton.books;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.miscellaneous.TrimFilterFactory;
import org.apache.lucene.analysis.standard.StandardFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.stream.Collectors;

@AnalyzerDef(name = "originalsAnalyzer", tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class), filters = {
        @TokenFilterDef(factory = StandardFilterFactory.class), @TokenFilterDef(factory = LowerCaseFilterFactory.class),
        @TokenFilterDef(factory = TrimFilterFactory.class)})

@Entity
@Indexed
@Analyzer(definition = "originalsAnalyzer")
@JsonInclude(Include.NON_NULL)
public class OriginalBook extends Book {


    @ContainedIn
    @OneToMany(mappedBy = "original")
    @JsonBackReference
    private List<TranslatedBook> translations;

    public List<TranslatedBook> getTranslations() {
        return translations;
    }

    public void setTranslations(List<TranslatedBook> translations) {
        this.translations = translations;
    }

    public String getCSVString(){
        Publication[] original = this.getPublications().toArray(Publication[]::new);
        return "OR;"+this.getAuthors().stream().map(Author::getName).collect(Collectors.joining(","))
                + original[0].getCSVString();
    }

    public String getPDFString(){
        Publication[] original = this.getPublications().toArray(Publication[]::new);
        return original[0].getTitle()+ " - " + this.getAuthors().stream().map(Author::getName).collect(Collectors.joining(","));
    }

}
