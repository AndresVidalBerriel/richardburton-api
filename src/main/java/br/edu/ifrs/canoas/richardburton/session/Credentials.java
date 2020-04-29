package br.edu.ifrs.canoas.richardburton.session;

public class Credentials {

    private String id;
    private String authenticationString;

    public Credentials(String id, String authenticationString) {
        this.id = id;
        this.authenticationString = authenticationString;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthenticationString() {
        return authenticationString;
    }

    public void setAuthenticationString(String authenticationString) {
        this.authenticationString = authenticationString;
    }
}