package org.opensails.spyglass;

/**
 * Thrown when a failure occurs using Spyglass.
 * 
 * @author aiwilliams
 */
public class Crack extends RuntimeException {
	private static final long serialVersionUID = -7037958944024955155L;

	public Crack() {
		super();
	}

	public Crack(String message) {
		super(message);
	}

	public Crack(String message, Throwable cause) {
		super(message, cause);
	}

	public Crack(Throwable cause) {
		super(cause);
	}

}
