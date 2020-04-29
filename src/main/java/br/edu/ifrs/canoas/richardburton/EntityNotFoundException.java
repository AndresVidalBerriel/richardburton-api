package br.edu.ifrs.canoas.richardburton;

public abstract class EntityNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public EntityNotFoundException(String message) {
        super(message);
    }
}
