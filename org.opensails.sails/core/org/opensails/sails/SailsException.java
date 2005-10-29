/*
 * Created on Mar 1, 2005
 * 
 * (c) 2005 Adam Williams
 */
package org.opensails.sails;

/**
 * All intentionally thrown exceptions in a SailsApplication should be one
 * of these or derived from this. It makes debugging easier, that's for sure!
 */
public class SailsException extends RuntimeException {
    private static final long serialVersionUID = 3832616305156109108L;

    public SailsException(String message, Throwable cause) {
        super(message, cause);
    }

    public SailsException(String message) {
        super(message);
    }

    public SailsException(Throwable cause) {
        super(cause);
    }
}
