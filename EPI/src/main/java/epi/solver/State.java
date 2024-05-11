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
package epi.solver;

/**
 * @author OlivierK
 */
public class State {
	private int number = -1;
	private boolean isFinal = false;
	public Transition[] transitions = new Transition[2];

	public State(final int number) {
		this.number = number;
	}

	public State(final State state) {
		if (state.number != -1) {
			this.number = state.number;
		}
		if (state.transitions[0] != null) {
			this.transitions[0] = new Transition(state.transitions[0]);
		}
		if (state.transitions[1] != null) {
			this.transitions[1] = new Transition(state.transitions[1]);
		}
	}

	public int getNumber() {
		return this.number;
	}

	public boolean isFinal() {
		return this.isFinal;
	}
	public void setFinal(final boolean isFinal) {
		this.isFinal = isFinal;
	}
	public void setNumber(final int number) {
		this.number = number;
	}
	public String toString() {
		final StringBuffer result = new StringBuffer();
		if (this.number != -1) {
			result.append(this.number + ":");
		}
		if (this.transitions[0] != null) {
			result.append(this.transitions[0].toString());
		}
		if (this.transitions[1] != null) {
			result.append(this.transitions[1].toString());
		}
		if (this.isFinal == true) {
			result.append("final state");
		}
		return result.toString();
	}
}
