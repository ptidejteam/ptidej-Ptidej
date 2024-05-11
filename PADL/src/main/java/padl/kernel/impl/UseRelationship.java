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

import padl.kernel.IElementMarker;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IUseRelationship;

class UseRelationship extends Relationship implements IElementMarker,
		IUseRelationship {

	private static final long serialVersionUID = -6218449758442734567L;

	public UseRelationship(
		final char[] anID,
		final IFirstClassEntity aTargetEntity,
		final int aCardinality) {

		super(anID);

		// The order of the following lines is very fragile!
		this.setTargetEntity(aTargetEntity);
		this.setCardinality(aCardinality);
		// Yann 2004/12/19: Name and ID.
		// I set the name of the relationship according to its
		// type, to its target entity, and to its cardinaly
		// because I want to distinguish similar relationships
		// across models (i.e., with different IDs).
		final StringBuffer buffer = new StringBuffer();
		buffer.append(this.getClass().getName());
		buffer.append(':');
		buffer.append(aTargetEntity.getID());
		buffer.append(':');
		buffer.append(aCardinality);

		// Yann 2009/04/28: Unique ID!
		// I don't forget to set the ID of the relationship 
		// to a unique name... its name...
		//	this.setID(buffer.toString());
		this.setName(buffer.toString().toCharArray());
	}
}
