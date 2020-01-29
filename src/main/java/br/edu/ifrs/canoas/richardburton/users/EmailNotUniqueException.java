package br.edu.ifrs.canoas.richardburton.users;

public class EmailNotUniqueException extends Exception {

    private static final long serialVersionUID = 1L;

    public EmailNotUniqueException(String message) {
        super(message);
    }

}