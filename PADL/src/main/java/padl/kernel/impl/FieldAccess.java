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

import padl.kernel.Constants;
import padl.kernel.IField;
import padl.kernel.IFieldAccess;
import padl.kernel.IFirstClassEntity;
import padl.path.IConstants;

/**
 * @author 	Yann-Gal Guhneuc
 * @since	2002/08/19
 */
class FieldAccess extends Constituent implements IFieldAccess {
	private static final long serialVersionUID = 5541766100314762093L;

	private int cardinality;
	private IFirstClassEntity entityDeclaringField;
	private IField field;

	public FieldAccess(
		final int cardinality,
		final int visibility,
		final IField field,
		final IFirstClassEntity entityDeclaringField) {

		super(Constants.DEFAULT_METHODINVOCATION_ID);

		this.cardinality = cardinality;
		this.setVisibility(visibility);
		this.field = field;
		this.entityDeclaringField = entityDeclaringField;
	}
	public boolean equals(final IFieldAccess anotherFieldAccess) {
		return this.getField().equals(anotherFieldAccess.getField())
				&& this.getFieldDeclaringEntity().equals(
					anotherFieldAccess.getFieldDeclaringEntity());
	}
	public boolean equals(final Object object) {
		if (object instanceof IFieldAccess) {
			return this.equals((IFieldAccess) object);
		}
		return super.equals(object);
	}
	public int getCardinality() {
		return this.cardinality;
	}
	public IField getField() {
		return this.field;
	}
	public IFirstClassEntity getFieldDeclaringEntity() {
		return this.entityDeclaringField;
	}
	public char[] getName() {
		return this.toString().toCharArray();
	}
	protected char getPathSymbol() {
		// Yann 2009/04/28: Unification with PADL Statement
		// This method should not be here but in Statement.
		// This class should be a subclass of Statement too!
		return IConstants.STATEMENT_SYMBOL;
	}
	public int hashCode() {
		int hashCode = this.field.hashCode();
		hashCode += 31 * this.entityDeclaringField.hashCode();
		return hashCode;
	}
	public void performCloneSession() {
		final FieldAccess clonedFieldAccess = (FieldAccess) this.getClone();

		clonedFieldAccess.entityDeclaringField =
			(IFirstClassEntity) this.entityDeclaringField.getClone();
		clonedFieldAccess.field = (IField) this.field.getClone();
	}
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append(this.entityDeclaringField.getName());
		buffer.append('.');
		buffer.append(this.field.getName());
		return buffer.toString();
	}
}
