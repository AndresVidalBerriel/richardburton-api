package br.edu.ifrs.canoas.richardburton.books;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.miscellaneous.TrimFilterFactory;
import org.apache.lucene.analysis.standard.StandardFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.TokenizerDef;
import org.hibernate.search.annotations.TokenFilterDef;

@AnalyzerDef(name = "translationsAnalyzer",
        tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
        filters = {@TokenFilterDef(factory = StandardFilterFactory.class),
                @TokenFilterDef(factory = LowerCaseFilterFactory.class),
                @TokenFilterDef(factory = TrimFilterFactory.class)})


@Entity
@Indexed
@Analyzer(definition = "translationsAnalyzer")
@JsonInclude(Include.NON_NULL)
public class TranslatedBook extends Book {

    @NotNull
    @ManyToOne
    @IndexedEmbedded
    @JsonManagedReference
    private OriginalBook original;

    public OriginalBook getOriginal() {
        return original;
    }

    public void setOriginal(OriginalBook original) {
        this.original = original;
    }

}
