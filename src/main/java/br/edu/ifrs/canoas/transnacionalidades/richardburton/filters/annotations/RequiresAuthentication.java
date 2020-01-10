package br.edu.ifrs.canoas.transnacionalidades.richardburton.filters.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.ws.rs.NameBinding;

@NameBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface RequiresAuthentication {

    public enum Privileges {
        REGULAR, ADMIN
    }

    public Privileges privileges() default Privileges.REGULAR;
}