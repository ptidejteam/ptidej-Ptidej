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
package ptidej.viewer.extension.repository.callgraph.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/09/24
 */
public class Method {
	// Yann 2007/10/06: Recursion
	// This table makes sure that we visit
	// each method in a call graph once
	// and only once.
	private static final List ListOfVisitedMethods = new ArrayList();

	private final boolean isClass;
	private final boolean isEntryMethod;
	private final List listOfCalls;
	private final String name;
	private final String type;
	private final String uniqueName;

	//	public Method(
	//		final String aTypeName,
	//		final boolean isClass,
	//		final String aMethodName) {
	//
	//		this(aTypeName, isClass, aMethodName, false);
	//	}
	public Method(
		final String aTypeName,
		final boolean isClass,
		final String aMethodName,
		final boolean isEntryMethod) {

		this.isClass = isClass;
		this.isEntryMethod = isEntryMethod;
		this.listOfCalls = new ArrayList();
		this.name = aMethodName;
		this.type = aTypeName;

		final StringBuffer buffer = new StringBuffer();
		buffer.append(this.type);
		buffer.append('.');
		buffer.append(this.name);
		this.uniqueName = buffer.toString();
	}
	public void accept(final IVisitor aVisitor) {
		if (this.isEntryMethod) {
			Method.ListOfVisitedMethods.clear();
		}

		aVisitor.enter(this);
		if (!ListOfVisitedMethods.contains(this.uniqueName)) {
			ListOfVisitedMethods.add(this.uniqueName);
			final Iterator iterator = this.listOfCalls.iterator();
			while (iterator.hasNext()) {
				final Call call = (Call) iterator.next();
				call.accept(aVisitor);
			}
		}
		aVisitor.exit(this);
	}
	public void addCall(final Call aCall) {
		this.listOfCalls.add(aCall);
	}
	public String getName() {
		return this.name;
	}
	public String getType() {
		return this.type;
	}
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append(this.name);
		buffer.append("   [in");
		if (this.isClass) {
			buffer.append(" class ");
		}
		else {
			buffer.append(" interface ");
		}
		buffer.append(this.type);
		buffer.append(']');
		return buffer.toString();
	}
}
