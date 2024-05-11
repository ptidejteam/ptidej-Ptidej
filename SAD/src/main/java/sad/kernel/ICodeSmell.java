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
package sad.kernel;

import java.util.Set;

import padl.kernel.IClass;
import sad.codesmell.property.impl.ClassProperty;

/*
 * Modification :
 * ---------------------------------------------------------
 * Naouel Moha: 2008/07/17
 * we need to identify explicitely the name of the main class
 * suspected of having an antipattern 
 * 
 * Add the field : protected boolean mainCodeSmell;
 */

public interface ICodeSmell {
	String getName();

	String getDefinition();
	
	boolean isMainCodeSmell();

	void setMainCodeSmell(final boolean mainCodeSmell);

	ClassProperty getClassProperty();

	IClass getIClass();
	
	Set getIClasses();

	String getIClassID();

	String toString(final int count);
	
	String toString(final int count, final int compositeCount);

	/**
	 * Compares the specified codesmell with this codesmell for equality. We
	 * consider that a code smell is equal to another code smell if they involve
	 * the same class. we do not compare their other attributes.
	 */
	boolean equals(final ICodeSmell cs);

	/**
	 * Check if this code smell is contains in the specified set
	 * TODO : to remove!
	 */
	boolean contains(final Set csSet);

	boolean containsPartially(
		final String codeSmellName,
		final Set csSet);
}
