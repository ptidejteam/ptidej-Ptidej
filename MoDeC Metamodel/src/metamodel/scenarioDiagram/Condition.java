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
package metamodel.scenarioDiagram;


public class Condition {

	/**
	 * @uml.property  name="clause"
	 */
	private String clause = "";

	/**
	 * Getter of the property <tt>clause</tt>
	 * @return  Returns the clause.
	 * @uml.property  name="clause"
	 */
	public String getClause() {
		return this.clause;
	}

	/**
	 * Setter of the property <tt>clause</tt>
	 * @param clause  The clause to set.
	 * @uml.property  name="clause"
	 */
	public void setClause(String clause) {
		this.clause = clause;
	}

	/**
	 * @uml.property  name="ofOperand"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="guard:metamodel.scenarioDiagram.Operand"
	 */
	private Operand ofOperand = new metamodel.scenarioDiagram.Operand();

	/**
	 * Getter of the property <tt>ofOperand</tt>
	 * @return  Returns the ofOperand.
	 * @uml.property  name="ofOperand"
	 */
	public Operand getOfOperand() {
		return this.ofOperand;
	}

	/**
	 * Setter of the property <tt>ofOperand</tt>
	 * @param ofOperand  The ofOperand to set.
	 * @uml.property  name="ofOperand"
	 */
	public void setOfOperand(Operand ofOperand) {
		this.ofOperand = ofOperand;
	}

}
