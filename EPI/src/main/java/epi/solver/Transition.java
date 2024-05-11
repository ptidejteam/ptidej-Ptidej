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
public class Transition {
	static String loopTransition = "loop";
	private State destination;
	private String token;

	public Transition(final String token, final State dest) {
		this.token = token;
		this.destination = dest;
	}

	public Transition(final Transition transition) {
		if (transition.token != null) {
			this.token = transition.token;
		}
		if (transition.destination != null) {
			this.destination = transition.destination;
		}
	}

	public State getDestinationState() {
		return this.destination;
	}

	public String getToken() {
		return this.token;
	}

	public String toString() {
		final StringBuffer result = new StringBuffer();

		result.append("(");
		if (this.token != null) {
			result.append(this.token);
		}
		else {
			result.append("null");
		}

		if (this.destination != null) {
			result.append("," + this.destination.getNumber());
		}
		else {
			result.append(", null");
		}
		result.append(")");
		return result.toString();
	}
}
