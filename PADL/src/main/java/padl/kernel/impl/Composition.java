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
package padl.kernel.impl;

import padl.kernel.IAssociation;
import padl.kernel.IComposition;
import padl.kernel.IElementMarker;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethod;

class Composition extends Aggregation implements IElementMarker, IComposition {
	private static final long serialVersionUID = 7121625505141622383L;

	public Composition(final IAssociation anAssociation) {
		this(
			anAssociation.getID(),
			anAssociation.getTargetEntity(),
			anAssociation.getCardinality());
	}
	public Composition(
		final char[] anID,
		final IFirstClassEntity aTargetEntity,
		final int cardinality) {

		super(anID, aTargetEntity, cardinality);
	}
	public Composition(
		final char[] anID,
		final IFirstClassEntity aTargetEntity,
		final int cardinality,
		final IField originField,
		final IMethod originGetterMethod,
		final IMethod originSetterMethod) {

		super(
			anID,
			aTargetEntity,
			cardinality,
			originField,
			originGetterMethod,
			originSetterMethod);
	}
}
