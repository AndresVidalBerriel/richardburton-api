package br.edu.ifrs.canoas.richardburton.auth;

import br.edu.ifrs.canoas.richardburton.util.ServiceEntity;

import javax.persistence.*;
import java.io.Serializable;
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
}
