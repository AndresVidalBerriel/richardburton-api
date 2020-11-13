package br.edu.ifrs.canoas.richardburton.users;

import br.edu.ifrs.canoas.richardburton.constraints.NullOrNotBlank;
import br.edu.ifrs.canoas.richardburton.util.ServiceEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@NamedQueries({
  @NamedQuery(
    name = "User.getEmailById",
    query = "SELECT email FROM User u WHERE u.id = :id"
  ),
  @NamedQuery(
    name = "User.retrieveByEmail",
    query = "SELECT u FROM User u WHERE u.email = :email"
  )
})

@Entity
@JsonInclude(Include.NON_NULL)
@Table(name = "_User")
public class User extends ServiceEntity {

    public static final String EMAIL_FORMAT = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    @NotNull
    @NotBlank
    @Email(regexp = EMAIL_FORMAT)
    private String email;

    @NotNull
    @NotBlank
    private String firstName;

    @NotNull
    @NotBlank
    private String lastName;

    @NullOrNotBlank
    private String nationality;

    @NullOrNotBlank
    private String affiliation;

    @NullOrNotBlank
    private String occupation;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}