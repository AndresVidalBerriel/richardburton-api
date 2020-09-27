package br.edu.ifrs.canoas.richardburton.session;

import br.edu.ifrs.canoas.richardburton.users.User;
import br.edu.ifrs.canoas.richardburton.util.ServiceEntity;

class Session extends ServiceEntity {

    private String token;

    private User user;

    public Session(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}