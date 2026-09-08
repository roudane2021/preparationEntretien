package com.roudane.preparationentretien.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, Object identifier) {
        super(resourceName + " introuvable avec l'identifiant : " + identifier);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
