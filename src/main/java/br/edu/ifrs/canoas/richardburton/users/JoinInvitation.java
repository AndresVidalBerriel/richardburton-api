package br.edu.ifrs.canoas.richardburton.users;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class JoinInvitation {

    @Id
    @GeneratedValue
    private Long id;

    private User creator;
}