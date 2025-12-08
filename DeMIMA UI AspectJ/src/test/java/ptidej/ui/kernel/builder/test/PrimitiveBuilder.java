/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc  and others, see in file; API and its implementation
 ******************************************************************************/
package ptidej.ui.kernel.builder.test;

import padl.creator.aspectj.test.AspectJPrimitive;
import padl.kernel.exception.CreationException;
import ptidej.ui.awt.primitive.PrimitiveFactory;
import ptidej.ui.kernel.builder.AspectJBuilder;
import ptidej.ui.kernel.builder.Builder;
import ptidej.ui.primitive.IPrimitiveFactory;

/**
 * @author Jean-Yves Guyomarc'h
 * @since 2005-08-16
 */
public abstract class PrimitiveBuilder extends AspectJPrimitive {
	private Builder ajBuilder;

	public PrimitiveBuilder(final String aName) {
		super(aName);
	}

	protected void setUp() throws CreationException {
		super.setUp();

		final IPrimitiveFactory primitiveFactory = PrimitiveFactory
				.getInstance();
		this.ajBuilder = AspectJBuilder.getCurrentBuilder(primitiveFactory);
	}

	public Builder getBuilder() {
		return this.ajBuilder;
	}
}
