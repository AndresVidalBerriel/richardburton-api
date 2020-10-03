package br.edu.ifrs.canoas.richardburton.auth;

import br.edu.ifrs.canoas.richardburton.util.ServiceEntity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
public class CredentialsGroup extends ServiceEntity implements Serializable {

    @Id
    private String name;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Permissions> permissions;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Credentials> members;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Permissions> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permissions> permissions) {
        this.permissions = permissions;
    }

    public Set<Credentials> getMembers() {
        return members;
    }

    public void setMembers(Set<Credentials> members) {
        this.members = members;
    }

    public void add(Credentials member) {
        members.add(member);
    }

    public void remove(Credentials member) { members.remove(member);}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CredentialsGroup that = (CredentialsGroup) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
