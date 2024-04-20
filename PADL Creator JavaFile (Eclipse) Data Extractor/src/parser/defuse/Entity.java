/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc  and others, see in file; API and its implementation
 ******************************************************************************/
package parser.defuse;

import java.util.Iterator;
import java.util.Vector;

public abstract class Entity {

	// name of the element 
	private final char[] name;

	// parent of the entity
	private final char[] parent;

	// CD, ID: the whole line of class/interface declaration
	// MD, COD: the signature of method and constructor
	// PD,LVD, FD: the modifier and the type of parameter, local variable, or field declaration
	private final char[] signature;

	// types: CD,ID,MD, COD, LVD,PD
	private final char[] declarationType;

	// line number of first definition
	private final char[] lineNumber;

	//list of its modification and use , for MD, COD the list is empty 
	private final Vector<ModificationAndUseStatement> modificationList =
		new Vector<ModificationAndUseStatement>();

	// a unique id for each entity
	private final char[] id;

	public Entity(
		final char[] name,
		final char[] signature,
		final char[] declarationType,
		final char[] lineNumber,
		final char[] parent,
		final char[] id) {

		this.name = name;
		this.signature = signature;
		this.declarationType = declarationType;
		this.lineNumber = lineNumber;
		this.parent = parent;
		this.id = id;
	}

	public char[] getDeclarationType() {
		return this.declarationType;
	}

	public char[] getID() {
		return this.id;
	}

	public char[] getLineNumber() {
		return this.lineNumber;
	}

	public Vector<ModificationAndUseStatement> getModificationList() {
		return this.modificationList;
	}

	public char[] getName() {
		return this.name;
	}

	public char[] getParent() {
		return this.parent;
	}

	public char[] getSignature() {
		return this.signature;
	}

	public char[] getType() {
		return this.declarationType;
	}

	public String toString() {
		String result = "";
		result = result + "\t\t" + new String(this.getName()) + "\n";
		result =
			result + "\t\t\t" + "signature: " + new String(this.getSignature())
					+ "\n";
		result =
			result + "\t\t\t" + "parent: " + new String(this.getParent())
					+ "\n";
		result = result + "\t\t\t" + "modifications and use: " + "\n";
		final Iterator<ModificationAndUseStatement> modificationsIt =
			this.getModificationList().iterator();
		ModificationAndUseStatement currentModification;
		while (modificationsIt.hasNext()) {
			currentModification = modificationsIt.next();
			result =
				result + "\t\t" + "" + currentModification.toString() + "\n";
		}
		return result;
	}

}
