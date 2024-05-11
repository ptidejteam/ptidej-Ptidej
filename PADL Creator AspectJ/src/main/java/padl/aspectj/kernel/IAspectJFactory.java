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
package padl.aspectj.kernel;

import padl.kernel.IFactory;

/**
 * @author Jean-Yves Guyomarc'h
 * @since  2004/08/29
 */
public interface IAspectJFactory extends IFactory {
	IAdvice createAdvice(final char[] aName);
	IAspect createAspect(final char[] aName);
	IInterTypeConstructor createInterTypeConstructor(final char[] aName);
	IInterTypeDeclareParents createInterTypeDeclareParents(final char[] aName);
	IInterTypeField createInterTypeField(final char[] aName, final char[] type);
	IInterTypeMethod createInterTypeMethode(final char[] aName);
	IPointcut createPointcut(final char[] aName);
}
