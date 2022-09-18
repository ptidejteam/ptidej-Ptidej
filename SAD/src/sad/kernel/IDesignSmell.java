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
/*
 * Modification :
 * ---------------------------------------------------------
 * Naouel Moha: 2008/07/17
 * we need to identify explicitely the name of the main class
 * suspected of having an antipattern 
 */

package sad.kernel;

import java.util.Set;

public interface IDesignSmell {

	/** 
	 * Get the name of the antipattern
	 */
	String getName();

	/** 
	 * Set the name of the antipattern
	 */
	void setName(final String name);

	/** 
	 * Get the name of the main class of the antipattern
	 */
	String getMainClassName();

	/** 
	 * Set the name of the main class of the antipattern
	 */
	void setMainClassName(final String mainClassName);

	/**
	 * Get the definition of the antipattern
	 */
	String getDefinition();

	/**
	 * Set the definition of the antipattern
	 */
	void setDefinition(final String definition);

	/**
	 * Get the set of code smells that constitute the antipattern
	 */
	Set listOfCodeSmells();

	/**
	 * Check if this antipattern is contained in the specified set of antipatterns
	 */
	boolean contains(final Set setAnt);

	/**
	 * Compares the specified antipattern with this antipattern for equality
	 */
	boolean equals(final IDesignSmell anAntiPattern);

	/**
	 * Check if this antipattern is contained in the specified set of antipatterns
	 */
	boolean containsPartially(
		final String identicalCodeSmellName,
		final Set setAnt);

	/**
	 * Compares the specified antipattern with this antipattern for equality
	 */
	boolean equalsPartially(
		final String identicalCodeSmellName,
		final IDesignSmell ant);

	/**
	 * Return a string that describes the antipattern
	 */
	String toString();
}
