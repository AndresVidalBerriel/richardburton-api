package br.edu.ifrs.canoas.richardburton;

public abstract class EntityValidationException extends Exception {

    private static final long serialVersionUID = 1L;

    public EntityValidationException(String message) {
        super(message);
    }
}