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
package sad.kernel.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import sad.kernel.IDesignSmell;
import sad.kernel.ICodeSmell;

/*
 * Modification :
 * ---------------------------------------------------------
 * Naouel Moha: 2006/01/29
 * 
 * Modification :
 * ---------------------------------------------------------
 * Naouel Moha: 2008/07/17
 * we need to identify explicitely the name of the main class
 * suspected of having an antipattern 
 */

/**
 * This class represents an Antipattern. An antipattern is defined by an name, a
 * definition, and a set of code smells.
 * 
 * @author Moha N. & Huynh D.L.
 * @version 1.0
 * @since 2005/08/08
 */
public class DesignSmell implements IDesignSmell {

	protected String name;
	protected String definition;
	protected Set setOfCodeSmells;
	protected String mainClassName;

	public DesignSmell(final ICodeSmell aCodeSmell) {
		HashSet set = new HashSet();
		set.add(aCodeSmell);
		this.setOfCodeSmells = set;
		if (aCodeSmell instanceof CodeSmell) {
			if (aCodeSmell.isMainCodeSmell()) {
				setMainClassName(aCodeSmell.getIClassID());
			}
		}
		else if (aCodeSmell instanceof CodeSmellComposite) {
			setMainClassName(aCodeSmell.getIClassID());
		}
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getMainClassName() {
		return this.mainClassName;
	}

	public void setMainClassName(final String className) {
		this.mainClassName = className;
	}

	public String getDefinition() {
		return this.definition;
	}

	public void setDefinition(final String definition) {
		this.definition = definition;
	}

	public Set listOfCodeSmells() {
		return this.setOfCodeSmells;
	}

	public void setSetOfCodeSmells(final Set codeSmells) {
		this.setOfCodeSmells = codeSmells;
	}

	public String toString() {
		String s = "\n####" + this.name;

		final Set setFrom = this.listOfCodeSmells();

		final Iterator iter = setFrom.iterator();
		while (iter.hasNext()) {
			final ICodeSmell codeSmell = (ICodeSmell) iter.next();
			s += codeSmell.toString();
		}
		return s;

	}

	/**
	 * Compares the specified antipattern with this antipattern for equality
	 * 
	 * @param ant The antipattern to be compared
	 * @return boolean : true if the specified antipattern is the same as this
	 *         antipattern
	 */
	public boolean equals(final IDesignSmell ant) {

		boolean isSame = true;

		final Set setFrom = this.listOfCodeSmells();
		// TODO : verify the number of elements and then one by one

		final Iterator iter = ant.listOfCodeSmells().iterator();
		while (iter.hasNext() && isSame == true) {
			final ICodeSmell codeSmell = (ICodeSmell) iter.next();
			if (!codeSmell.contains(setFrom)) {
				isSame = false;
			}
		}
		return isSame;
	}

	/**
	 * Check if this antipattern is contained in the specified set of
	 * antipatterns
	 * 
	 * @param setAnt The set of antipatterns
	 * @return boolean : true if this antipattern is contained in the specified
	 *         set
	 * TODO : remove
	 */
	public boolean contains(final Set setAnt) {

		boolean isIn = false;

		final Iterator iter = setAnt.iterator();
		while (iter.hasNext()) {
			final IDesignSmell ant = (IDesignSmell) iter.next();
			if (this.equals(ant)) {
				isIn = true;
			}
		}
		return isIn;
	}

	/**
	 * Compares the specified antipattern with this antipattern for equality
	 * 
	 * @param ant The antipattern to be compared
	 * @return boolean : true if the specified antipattern is the same as this
	 *         antipattern
	 */
	public boolean equalsPartially(
		final String codeSmellName,
		final IDesignSmell ant) {

		boolean isSame = true;

		final Set setFrom = this.listOfCodeSmells();

		final Iterator iter = ant.listOfCodeSmells().iterator();
		while (iter.hasNext() && isSame == true) {
			final ICodeSmell codeSmell = (ICodeSmell) iter.next();

			if (!codeSmell.containsPartially(codeSmellName, setFrom)) {
				isSame = false;
			}
		}
		return isSame;
	}

	/**
	 * Check if this antipattern is contained in the specified set of
	 * antipatterns
	 * 
	 * @param setAnt The set of antipatterns
	 * @return boolean : true if this antipattern is contained in the specified
	 *         set
	 */
	public boolean containsPartially(
		final String identicalCodeSmellName,
		final Set setAnt) {

		boolean isIn = false;

		final Iterator iter = setAnt.iterator();
		while (iter.hasNext()) {
			final IDesignSmell ant = (IDesignSmell) iter.next();
			if (this.equalsPartially(identicalCodeSmellName, ant)) {
				isIn = true;
			}
		}
		return isIn;
	}

	public DesignSmell() {
		this.name = null;
		this.definition = null;
	}

	public DesignSmell(final String name, final String definition) {
		this.name = name;
		this.definition = definition;
	}

	public DesignSmell(
		final String name,
		final String definition,
		final Set setOfCodeSmells) {
		this.name = name;
		this.definition = definition;
		this.setOfCodeSmells = setOfCodeSmells;
	}
}
