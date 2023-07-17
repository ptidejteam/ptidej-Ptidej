/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package padl.refactoring.exception;

/**
 * @author Bouden Saliha
 */
public class ModelDeclarationException extends Exception {
	private static final long serialVersionUID = 6540257599239622710L;

	public ModelDeclarationException(final String anException) {
		super(anException);
	}
	public void printStackTrace(String message) {
		System.out.println("Error message: " + message);
		
	}
}
