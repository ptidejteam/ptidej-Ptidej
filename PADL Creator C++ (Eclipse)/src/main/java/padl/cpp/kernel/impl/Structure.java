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
package padl.cpp.kernel.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ibm.toad.cfparse.utils.Access;

import padl.cpp.kernel.IStructure;
import padl.kernel.IEntityMarker;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IOperation;
import padl.kernel.impl.FirstClassEntity;

class Structure extends FirstClassEntity implements IEntityMarker, IStructure {
	private static final long serialVersionUID = -6073309025082506778L;

	private boolean forceAbstract = false;
	private List<IFirstClassEntity> friendClassList = new ArrayList<IFirstClassEntity>();
	private List<IOperation> friendFunctionList = new ArrayList<IOperation>();
	private final List<?> listOfSuperInterfaces = new ArrayList();

	public Structure(final char[] anID) {
		super(anID);
	}

	public void addFriendClass(final IFirstClassEntity aClass) {
		if (aClass != null) {
			this.friendClassList.add(aClass);
		}
	}

	public void addFriendFunction(final IOperation aFunction) {
		if (aFunction != null) {
			this.friendFunctionList.add(aFunction);
		}
	}

	public boolean isForceAbstract() {
		return this.forceAbstract;
	}

	public List<?> listOfImplementedEntities() {
		return this.listOfSuperInterfaces;
	}

	public void setAbstract(final boolean aBoolean) {
		this.forceAbstract = aBoolean;
		super.setAbstract(aBoolean);
	}

	public void setVisibility(final int visibility) {
		super.setVisibility(
				this.isForceAbstract() ? visibility | Access.ACC_ABSTRACT
						: visibility);
	}

	public String toString() {
		final StringBuffer codeEq = new StringBuffer();
		codeEq.append(super.toString());

		codeEq.append("\n\tFriends with functions:");
		final Iterator<IOperation> iteratorOnFriendFunctions = this.friendFunctionList
				.iterator();
		while (iteratorOnFriendFunctions.hasNext()) {
			final IOperation iOperation = iteratorOnFriendFunctions.next();
			codeEq.append('\t');
			codeEq.append(iOperation.getName());
			codeEq.append('\n');
		}

		codeEq.append("\n\tFriends with first-class entities:");
		final Iterator<IFirstClassEntity> iteratorOnFriendClasses = this.friendClassList
				.iterator();
		while (iteratorOnFriendClasses.hasNext()) {
			final IFirstClassEntity iOperation = iteratorOnFriendClasses.next();
			codeEq.append("\t\t");
			codeEq.append(iOperation.getName());
			codeEq.append('\n');
		}

		return codeEq.toString();
	}
}
