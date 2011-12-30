/**
 * 
 */
package org.hyperdata.beeps.system;

/**
 * @author danny
 *
 */
public class NotFoundException extends Exception {

	/**
	 * 
	 */
	public NotFoundException() {
	}

	/**
	 * @param message
	 */
	public NotFoundException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public NotFoundException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
