package br.edu.ifrs.canoas.richardburton.books;

public class NoNewDataException extends Exception {

    private static final long serialVersionUID = 1L;

    public NoNewDataException(String message) {
        super(message);
    }

}