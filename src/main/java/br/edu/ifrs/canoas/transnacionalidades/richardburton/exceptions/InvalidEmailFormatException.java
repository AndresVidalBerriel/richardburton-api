package br.edu.ifrs.canoas.transnacionalidades.richardburton.exceptions;

public class InvalidEmailFormatException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidEmailFormatException(String errorMessage) {
        super(errorMessage);
    }

}