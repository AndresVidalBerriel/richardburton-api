package br.edu.ifrs.canoas.richardburton.auth;

import br.edu.ifrs.canoas.richardburton.constraints.NullOrNotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(
                name="Credentials.getDirectPermissions",
                query="SELECT c.permissions " +
                        "FROM Credentials c " +
                        "WHERE c.identifier = :identifier"
        ),
        @NamedQuery(
                name="Credentials.getGroupPermissions",
                query="SELECT g.permissions " +
                        "FROM Credentials c JOIN c.groups g " +
                        "WHERE c.identifier = :identifier"
        )
})
public class Credentials implements Cloneable {

    @Id
    private String identifier;

    @NotNull
    @NotBlank
    private String secret;

    @NullOrNotBlank
    private String token;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private EnumSet<Permissions> permissions;

    @ManyToMany
    private Set<CredentialsGroup> groups;

    public Credentials(){}

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public EnumSet<Permissions> getPermissions() {
        return permissions;
    }

    public void setPermissions(EnumSet<Permissions> permissions) {
        this.permissions = permissions;
    }

    public Set<CredentialsGroup> getGroups() {
        return groups;
    }

    public void setGroups(Set<CredentialsGroup> groups) {
        this.groups = groups;
    }

    @Override
    protected Credentials clone() {
        try {

            return (Credentials) super.clone();

        } catch (CloneNotSupportedException e) {

            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credentials that = (Credentials) o;
        return Objects.equals(identifier, that.identifier) &&
                Objects.equals(secret, that.secret);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, secret);
    }
}
