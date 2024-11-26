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

import padl.kernel.IConstituent;
import padl.kernel.IEntity;
import padl.kernel.IParameter;
import padl.kernel.exception.ModelDeclarationException;
import padl.util.Util;

public class Parameter extends Element implements IParameter {
	private static final long serialVersionUID = -1688444809285895471L;
	private int cardinality = 1;
	private IEntity type;
	private int dimension;
	private boolean hasDimension = false;

	public Parameter(
		final IEntity anEntity,
		final char[] aName,
		final int aCardinality) {

		this(anEntity, aCardinality);
		this.setName(aName);
	}
	public Parameter(final IEntity aType, final int aCardinality) {
		super("Parameter".toCharArray());

		this.setType(aType);
		this.setNameFromType(aType);
		this.cardinality = aCardinality;
	}
	public Parameter(
		final IEntity aType,
		final char[] aName,
		final int aCardinality,
		final int aDimension) {
		
		this(aType, aCardinality);
		this.setName(aName);
		this.dimension = aDimension;
		this.hasDimension = true;
	}
	//	public Parameter(final String aName, final String aType) {
	//		super("Parameter");
	//
	//		this.setName(aName);
	//		this.setType(aType);
	//	}
	// Yann 2006/03/28: Need?
	// Do I need to have this method now that
	// I inherit from Consituent?
	//	public void accept(final IVisitor visitor) {
	//		visitor.visit(this);
	//	}
	//	private String clean(final String aString, final boolean computeCardinality) {
	//		final String cleanString;
	//		int index = aString.indexOf('[');
	//		if (index > -1) {
	//			cleanString = aString.substring(0, index);
	//
	//			// Yann 2004/08/10: Cardinality.
	//			// I compute the cardinality by counting the
	//			// number of opening brackets.
	//			if (computeCardinality) {
	//				this.cardinality++;
	//				while ((index = aString.indexOf('[', index + 1)) > -1) {
	//					this.cardinality++;
	//				}
	//			}
	//		}
	//		else {
	//			cleanString = aString;
	//		}
	//
	//		return cleanString;
	//	}
	
	/**
	 * Returns the cardinality (i.e. one or many)
	 */
	public int getCardinality() {
		return this.cardinality;
	}
	/**
	 * Returns the "true" dimension.
	 * For instance:
	 * int 		has dimension 1
	 * int[] 	has dimension 2
	 * int[][]	has dimension 3...
	 */
	public int getDimension() {
		if (this.hasDimension) {
			return this.dimension;
		}
		throw new ModelDeclarationException(
			"Parameter " +
			this.getDisplayName() +
			" has no declared dimensions");
	}
	public String getDisplayTypeName() {
		return String.valueOf(this.getTypeName());
	}
	public IEntity getType() {
		return this.type;
	}
	public char[] getTypeName() {
		return this.type.getID();
	}
	public void setNameFromType(final IConstituent aType) {
		final char[] beautyName =
			Util.capitalizeFirstLetter(Util.stripAndCapQualifiedName(this
				.getTypeName()));
		final char[] name = new char[1 + beautyName.length];
		name[0] = 'a';
		System.arraycopy(beautyName, 0, name, 1, beautyName.length);

		this.setName(name);
	}
	public void setType(final IEntity aType) {
		this.type = aType;
	}
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append(this.getTypeName());
		if (hasDimension) {
			for (int i = 1; i < this.dimension; i++) {
				buffer.append("[]");
			}
		}
		buffer.append(' ');
		buffer.append(this.getName());
		return buffer.toString();
	}
}
