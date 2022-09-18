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
package padl.aspectj.kernel.impl;

import padl.aspectj.kernel.IAdvice;
import padl.aspectj.kernel.IAspect;
import padl.aspectj.kernel.IAspectJFactory;
import padl.aspectj.kernel.IInterTypeConstructor;
import padl.aspectj.kernel.IInterTypeDeclareParents;
import padl.aspectj.kernel.IInterTypeField;
import padl.aspectj.kernel.IInterTypeMethod;
import padl.aspectj.kernel.IPointcut;
import padl.kernel.IFactory;
import padl.kernel.impl.Factory;

/**
 * @author Jean-Yves Guyomarc'h
 */
public class AspectJFactory extends Factory implements IAspectJFactory {
	private static final long serialVersionUID = -3321920211167680442L;

	private static IFactory UniqueInstance;
	public static IFactory getInstance() {
		if (AspectJFactory.UniqueInstance == null) {
			AspectJFactory.UniqueInstance = new AspectJFactory();
		}
		return AspectJFactory.UniqueInstance;
	}

	private AspectJFactory() {
	}
	public IAdvice createAdvice(final char[] aName) {
		return new Advice(aName);
	}
	public IAspect createAspect(final char[] aName) {
		return new Aspect(aName);
	}
	public IInterTypeConstructor createInterTypeConstructor(final char[] aName) {
		return new InterTypeConstructor(aName);
	}
	public IInterTypeDeclareParents createInterTypeDeclareParents(
		final char[] aName) {

		return new InterTypeDeclareParents(aName);
	}
	public IInterTypeField createInterTypeField(
		final char[] aName,
		final char[] type) {

		return new InterTypeField(aName, type, 1);
	}
	public IInterTypeMethod createInterTypeMethode(final char[] aName) {
		return new InterTypeMethod(aName);
	}
	public IPointcut createPointcut(final char[] aName) {
		return new Pointcut(aName);
	}
}
