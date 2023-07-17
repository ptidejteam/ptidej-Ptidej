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
package padl.statement.kernel.impl;

import padl.kernel.IField;
import padl.kernel.IInterface;
import padl.kernel.IMethod;
import padl.kernel.impl.Statement;
import padl.statement.kernel.IIterator;

/**
 * @author tagmouty
 */
class Iterator extends Statement implements IIterator {
	private static final char[] ITERATOR = "Iterator".toCharArray();
	private static final long serialVersionUID = 4413591962431115345L;

	private IField field;
	private IInterface anInterface;
	private char[] condition;
	private IMethod method;
	private char[] returnTypeName;

	public Iterator(final IField aField, final char[] aCondition) {
		super(Iterator.ITERATOR);
		this.field = aField;
	}
	public Iterator(final IInterface anInterface, final char[] aCondition) {
		super(Iterator.ITERATOR);
		this.anInterface = anInterface;
	}
	public Iterator(final IMethod aMethod, final char[] aCondition) {
		super(Iterator.ITERATOR);

		this.method = aMethod;
		this.condition = aCondition;
	}
	public char[] getCondition() {
		return this.condition;
	}
	public IField getDeclaratingFiled() {
		return this.field;
	}
	public IMethod getDeclaringMethod() {
		return this.method;
	}
	public IInterface getDeclaratingInterface() {
		return this.anInterface;
	}
	public char[] getReturnTypeName() {
		/*if (this.getAttachedElement() == null) {
			return this.returnType;
		}
		return ((IConditionelS) this.getAttachedElement()).getReturn();*/
		return this.returnTypeName;
	}
	public void setReturnTypeName(final char[] aTypeName) {
		this.returnTypeName = aTypeName;
	}
}
