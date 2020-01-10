package br.edu.ifrs.canoas.transnacionalidades.richardburton.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@JsonInclude(Include.NON_NULL)
public class TranslatedBook extends Book {

    @NotNull
    @ManyToOne
    private OriginalBook original;

    public OriginalBook getOriginal() {
        return original;
    }

    public void setOriginal(OriginalBook original) {
        this.original = original;
    }

}
