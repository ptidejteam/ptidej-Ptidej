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
package ptidej.ui.kernel;

import padl.aspectj.kernel.IAspect;
import ptidej.ui.kernel.builder.Builder;
import ptidej.ui.primitive.IPrimitiveFactory;

/**
 * @author Jean-Yves Guyomarc'h
 * @since 2005/08/16
 */
public final class Aspect extends Entity {

	/**
	 * @param aPrimitiveFactory
	 * @param aBuilder
	 * @param anEntity
	 */
	public Aspect(
		final IPrimitiveFactory aPrimitiveFactory,
		final Builder aBuilder,
		final IAspect anAspect) {

		super(aPrimitiveFactory, aBuilder, anAspect);
	}
	protected String getStereotype() {
		return "<<Aspect>>";
	}
	protected String supplementalFieldText() {
		final Element[] elements = this.getElements();
		final StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < elements.length; i++) {
			final Element element = elements[i];
			if (element instanceof Pointcut
					|| element instanceof InterTypeDeclareParents
					|| element instanceof InterTypeField) {
				buffer.append(element.getName());
				buffer.append('\n');
			}
		}
		return buffer.toString();
	}
	protected String supplementalMethodText() {
		final Element[] elements = this.getElements();
		final StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < elements.length; i++) {
			final Element element = elements[i];
			if (element instanceof Advice || element instanceof InterTypeMethod
					|| element instanceof InterTypeConstructor) {
				buffer.append(element.getName());
				buffer.append('\n');
			}
		}
		return buffer.toString();
	}
}
