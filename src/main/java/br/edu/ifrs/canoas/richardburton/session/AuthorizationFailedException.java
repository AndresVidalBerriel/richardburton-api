package br.edu.ifrs.canoas.richardburton.session;

public class AuthorizationFailedException extends Exception {

    private static final long serialVersionUID = 1L;

    public AuthorizationFailedException(String message) {
        super(message);
    }
}
