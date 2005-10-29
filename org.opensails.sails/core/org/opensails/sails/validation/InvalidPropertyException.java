package org.opensails.sails.validation;

public class InvalidPropertyException extends RuntimeException {
    private static final long serialVersionUID = -5757123386517604516L;
    
    public InvalidPropertyException(String reason) {
        super(reason);
    }
}
