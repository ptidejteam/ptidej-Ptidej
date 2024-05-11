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

import padl.kernel.IAggregation;
import padl.kernel.IElementMarker;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethod;

class Aggregation extends Association implements IElementMarker, IAggregation {
	private static final long serialVersionUID = 510083598466826486L;
	public static String getLogo() {
		return "[]-->";
	}
	public Aggregation(final Association pAssociation) {
		this(pAssociation.getID(), pAssociation.getTargetEntity(), pAssociation
			.getCardinality());
	}
	public Aggregation(
		final char[] anID,
		final IFirstClassEntity aTargetEntity,
		final int aCardinality) {

		super(anID, aTargetEntity, aCardinality);
	}
	public Aggregation(
		final char[] anID,
		final IFirstClassEntity aTargetEntity,
		final int aCardinality,
		final IField anOriginField,
		final IMethod anOriginGetterMethod,
		final IMethod anOriginSetterMethod) {

		super(anID, aTargetEntity, aCardinality);
	}
}
