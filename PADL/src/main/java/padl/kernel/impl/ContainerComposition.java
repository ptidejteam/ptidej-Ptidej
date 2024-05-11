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
import padl.kernel.IContainerComposition;
import padl.kernel.IElementMarker;
import padl.kernel.IFirstClassEntity;

class ContainerComposition extends ContainerAggregation implements
		IElementMarker, IContainerComposition, IPrivateModelObservable {

	private static final long serialVersionUID = 3675667508613729100L;
	public ContainerComposition(final IAssociation pAssociation) {
		this(pAssociation.getID(), pAssociation.getTargetEntity(), pAssociation
			.getCardinality());
	}
	public ContainerComposition(
		final char[] anID,
		final IFirstClassEntity aTargetEntity,
		final int cardinality) {

		super(anID, aTargetEntity, cardinality);
	}
}
