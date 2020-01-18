package br.edu.ifrs.canoas.richardburton.books;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.miscellaneous.TrimFilterFactory;
import org.apache.lucene.analysis.standard.StandardFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

@AnalyzerDef(name = "originalsAnalyzer",
        tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
        filters = {@TokenFilterDef(factory = StandardFilterFactory.class),
                @TokenFilterDef(factory = LowerCaseFilterFactory.class),
                @TokenFilterDef(factory = TrimFilterFactory.class)})


@Entity
@Indexed
@Analyzer(definition = "originalsAnalyzer")
@JsonInclude(Include.NON_NULL)
public class OriginalBook extends Book {

    @NotNull
    @OneToMany(mappedBy = "original")
    @ContainedIn
    @JsonBackReference
    private List<TranslatedBook> translations;

    public List<TranslatedBook> getTranslations() {
        return translations;
    }

    public void setTranslations(List<TranslatedBook> translations) {
        this.translations = translations;
    }

}
