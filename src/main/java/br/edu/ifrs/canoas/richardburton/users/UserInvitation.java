package br.edu.ifrs.canoas.richardburton.users;

import br.edu.ifrs.canoas.richardburton.auth.CredentialsGroup;
import br.edu.ifrs.canoas.richardburton.auth.Permissions;
import br.edu.ifrs.canoas.richardburton.util.LocalDateTimeAttributeConverter;
import br.edu.ifrs.canoas.richardburton.util.ServiceEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class UserInvitation extends ServiceEntity {

    public enum Status {
        ISSUED,
        CONFIRMED,
        CANCELLED,
        EXPIRED,
    }

    @Id
    private String email;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    private User issuer;

    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private Set<Permissions> permissions;

    @ManyToMany
    private Set<CredentialsGroup> groups;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getIssuer() {
        return issuer;
    }

    public void setIssuer(User issuer) {
        this.issuer = issuer;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Permissions> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permissions> permissions) {
        this.permissions = permissions;
    }

    public Set<CredentialsGroup> getGroups() {
        return groups;
    }

    public void setGroups(Set<CredentialsGroup> groups) {
        this.groups = groups;
    }
}
