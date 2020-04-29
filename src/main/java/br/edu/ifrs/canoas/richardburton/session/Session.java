package br.edu.ifrs.canoas.richardburton.session;

import br.edu.ifrs.canoas.richardburton.users.User;
import org.jboss.resteasy.util.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Session {

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

    public static String digest(String authenticationString) {

        try {

            MessageDigest md = MessageDigest.getInstance("SHA3-512");
            byte[] bytes = Hex.decodeHex(authenticationString);
            byte[] hashed = md.digest(bytes);
            return Hex.encodeHex(hashed);

        } catch (NoSuchAlgorithmException e) {

            throw new RuntimeException(e);
        }

    }
}