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
package padl.kernel;

import java.util.Iterator;
import java.util.List;

/**
 * @author Farouk Zaidi
 * @since  2004/04/02
 */
public interface IMethodInvocation extends IConstituentOfOperation {
	// Yann 2004/07/31: Field access.
	// Method invocation are currently used to describe
	// field access as well. I need a UNKNWOWN value
	// for such cases.
	// TODO: Method invocation should describe methods only!
	int CLASS_CLASS = 1;
	int CLASS_CLASS_FROM_FIELD = 2;
	int CLASS_INSTANCE = 3;
	int CLASS_INSTANCE_FROM_FIELD = 4;
	int INSTANCE_CLASS = 5;
	int INSTANCE_CLASS_FROM_FIELD = 6;
	int INSTANCE_CREATION = 9;
	int INSTANCE_INSTANCE = 7;
	int INSTANCE_INSTANCE_FROM_FIELD = 8;
	int OTHER = 10;

	IOperation getCalledMethod();
	Iterator getIteratorOnCallingFields();
	int getCardinality();
	IFirstClassEntity getFieldDeclaringEntity();
	IField getFirstCallingField();
	IFirstClassEntity getTargetEntity();
	int getType();

	// TODO: Should be removed
	void setCalledMethod(final IOperation calledMethod);
	void setCallingField(final List callingFields);
	void addCallingField(final IField field);
}
