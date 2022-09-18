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
/**
 * 
 */
package padl.aspectj.kernel.exception;

import padl.kernel.exception.CreationException;

/**
 * @author Jean-Yves Guyomarc'h
 *
 */
public class AspectCreationException extends CreationException {
	private static final long serialVersionUID = -5764234940384545596L;
	public static final String BASE = "Aspect Creator Exception";

	public AspectCreationException() {
		this("No details.");
	}
	public AspectCreationException(final String message) {
		super(AspectCreationException.BASE + ": " + message);
	}
}
