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
package modec.metamodel;

import java.util.List;

public class Loop extends CombinedFragment {

	/**
	 * @uml.property  name="min"
	 */
	private String min = "";

	/**
	 * Getter of the property <tt>min</tt>
	 * @return  Returns the min.
	 * @uml.property  name="min"
	 */
	public String getMin() {
		return this.min;
	}

	/**
	 * Setter of the property <tt>min</tt>
	 * @param min  The min to set.
	 * @uml.property  name="min"
	 */
	public void setMin(String min) {
		this.min = min;
	}

	// ========================================================================

	private Operand operand;
	private int id;

	/**
	 * Getter of the property <tt>min</tt>
	 * @return  Returns the min.
	 * @uml.property  name="min"
	 */
	public Operand getOperand() {
		return this.operand;
	}

	/**
	 * Setter of the property <tt>min</tt>
	 * @param min  The min to set.
	 * @uml.property  name="min"
	 */
	public void setOperand(Operand operand) {
		this.operand = operand;
	}

	public int getID() {
		return this.id;
	}

	public Loop(ScenarioDiagram scenarioDiagram, int header, int id) {
		super(scenarioDiagram, header);
		this.id = id;
	}

	public Loop(int id) {
		this.id = id;
		this.operand = new Operand();
		addOperands(this.operand);
	}

	public void addComponentToOperand(int index, Component c) {
		this.operand.addComponents(index, c);
	}

	public void addComponentToOperand(Component c) {
		this.operand.addComponents(c);
	}

	public void addLastComponentToOperand(Component c) {
		this.operand.addLastComponents(c);
	}

	public void addFirstComponentToOperand(Component c) {
		this.operand.addFirstComponents(c);
	}

	public String toString() {
		String tab = "";
		for (int i = 0; i < this.level; i++)
			tab += "\t";
		//tab = "{level " + level + "} " + tab;		
		return tab /*+ this.getIndex() + " " */
		+"<LOOP> " + this.id + "\n" + this.operand;
	}

	public List visitLoopMessages() {
		return this.operand.visitOperandMessages();
	}

}
