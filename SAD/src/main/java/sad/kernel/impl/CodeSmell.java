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
import padl.kernel.IClass;
import sad.codesmell.property.impl.ClassProperty;
import sad.kernel.ICodeSmell;

/*
 * Modification :
 * ---------------------------------------------------------
 * Naouel Moha: 2008/07/17
 * we need to identify explicitely the name of the main class
 * suspected of having an antipattern 
 * 
 * Add the field : protected boolean mainCodeSmell;
 */

/**
 * This class represents a code smell.
 * 
 * @author Moha N. & Huynh D.L.
 * @version 1.0
 * @since 2005/08/08
 */
public class CodeSmell implements ICodeSmell {

	protected String name;

	protected String definition;

	protected boolean mainCodeSmell;

	private ClassProperty targetEntity;

	public void setClassProperty(final ClassProperty prop) {
		this.targetEntity = prop;
	}

	public ClassProperty getClassProperty() {
		return this.targetEntity;
	}

	public String getName() {
		return this.name;
	}

	public void setCodeSmellName(final String name) {
		this.name = name;
	}

	public String getDefinition() {
		return this.definition;
	}

	public void setDefinition(final String definition) {
		this.definition = definition;
	}

	public boolean isMainCodeSmell() {
		return this.mainCodeSmell;
	}

	public void setMainCodeSmell(final boolean mainCodeSmell) {
		this.mainCodeSmell = mainCodeSmell;
	}

	public String getIClassID() {
		return this.targetEntity.getIDClass();
	}

	public IClass getIClass() {
		return this.targetEntity.getIClass();
	}

	public Set getIClasses() {
		final Set setOfClasses = new HashSet();
		setOfClasses.add(this.targetEntity.getIClass());
		return setOfClasses;
	}

	/**
	 * Compares the specified codesmell with this codesmell for equality. We
	 * consider that a code smell is equal to another code smell if they involve
	 * the same class. we do not compare their other attributes.
	 * 
	 * @param cs the code smell to be compared
	 * @return boolean : true if the specified code smell is the same as this
	 *         code smell
	 */
	public boolean equals(final ICodeSmell cs) {

		boolean isSame = false;

		if (cs instanceof CodeSmellComposite) {
			// Search for this CodeSmell 
			if (cs.equals(this))
				isSame = true;
			else {
				isSame = false;
			}
		}
		else {
			String csClassID = cs.getClassProperty().getIDClass();
			String currentClassID = this.targetEntity.getIDClass();
			if (currentClassID.equals(csClassID)) {
				isSame = true;
			}
		}

		return isSame;
	}

	/**
	 * Check if this code smell is contains in the specified set
	 * @param csSet the set of code smells
	 * @return boolean : true if the specified code smell is in the set
	 * 					of code smells
	 */
	public boolean contains(final Set csSet) {

		boolean isSame = false;

		final Iterator iter = csSet.iterator();
		while (iter.hasNext() && !isSame) {
			final ICodeSmell cs = (ICodeSmell) iter.next();
			isSame = this.equals(cs);
		}
		return isSame;
	}

	public boolean containsPartially(final String codeSmellName, final Set csSet) {

		boolean isSame = false;

		if (this.getName().equals(codeSmellName)) {

			final Iterator iter = csSet.iterator();
			while (iter.hasNext() && !isSame) {
				final ICodeSmell cs = (ICodeSmell) iter.next();

				if (cs.getName().equals(codeSmellName))
					isSame = this.equals(cs);
			}
		}
		return isSame;
	}

	public CodeSmell(String name, String definition, ClassProperty prop) {
		this.name = name;
		this.definition = definition;
		this.targetEntity = prop;
	}

	public CodeSmell(
		String name,
		String definition,
		boolean mainCodeSmell,
		ClassProperty prop) {
		this.name = name;
		this.definition = definition;
		this.setMainCodeSmell(mainCodeSmell);
		this.targetEntity = prop;
	}

	public String toString() {
		return toString(1);
	}

	public String toString(int count) {
		return toString(count, 0);
	}

	public String toString(int count, final int compositeCount) {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("\n#");
		buffer.append(this.name);

		if (this.targetEntity != null) {
			buffer.append(this.targetEntity.toString(
				count,
				compositeCount,
				this.name));
		}
		return buffer.toString();
	}

}
