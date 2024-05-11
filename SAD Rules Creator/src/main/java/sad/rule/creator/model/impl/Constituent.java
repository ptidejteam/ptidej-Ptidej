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
package sad.rule.creator.model.impl;

import java.lang.reflect.Method;
import sad.rule.creator.model.IConstituent;
import sad.rule.creator.model.IVisitor;

/**
 * @author Pierre Leduc
 */
public class Constituent implements IConstituent {
	private final String id;

	public Constituent(final String anID) {
		this.id = anID;
	}

	public void accept(final IVisitor visitor) {
		this.accept(visitor, "visit");
	}

	protected void accept(final IVisitor visitor, final String methodName) {
		// I must match a class a the model to the 
		// corresponding interface: 
		//     rule.creator.model.impl.Association
		// ->
		//     rule.creator.model.IAssociation

		final String acceptMethodName =
			this.getClass().getName().replaceAll(".impl.", ".I");
		try {
			// Get the corresponding method by reflection
			final java.lang.Class[] argument =
				new java.lang.Class[] { java.lang.Class
					.forName(acceptMethodName) };
			final Method method =
				visitor.getClass().getMethod(methodName, argument);
			method.invoke(visitor, new Object[] { this });
		}
		catch (final Exception e) {
			// In case I add new constituent and forget to update
			// the method in the IVisitor interface, I foward such
			// exceptions (to fail the appropriate tests).
			System.err
				.print("Exception : The IVisitor class does not implement the visit method");
			throw new RuntimeException(e);
		}
	}

	public String getID() {
		return this.id;
	}
}
