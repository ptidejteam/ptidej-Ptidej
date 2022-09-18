/*
 * Created on 10 juil. 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package fr.emn.oadymppac;

/**
 * @author Mohammad Ghoniem
 *
 * This exception is thrown when there is a problem
 * related to a constraint e.g. when a constraint is
 * manipulated without having been declared etc.
 */
public class ConstraintException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1875910085863625753L;

	/**
	 * @param message
	 */
	public ConstraintException(final String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ConstraintException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public ConstraintException(final Throwable cause) {
		super(cause);
	}

}
