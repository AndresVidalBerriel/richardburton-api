package br.edu.ifrs.canoas.richardburton;

public abstract class DuplicateEntityException extends Exception {

    private static final long serialVersionUID = 1L;

    public DuplicateEntityException(String message) {
        super(message);
    }
}