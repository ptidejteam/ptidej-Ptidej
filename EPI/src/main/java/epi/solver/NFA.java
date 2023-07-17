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

import java.util.StringTokenizer;
import util.io.ProxyConsole;

/**
 * @author OlivierK
 */
public class NFA implements Cloneable {

	public static void main(final String[] args) {
		final NFA nfa =
			new NFA(
				"Component inheritance Leaf dummyRelationship Component inheritance Composite containerComposition Component");
		ProxyConsole.getInstance().debugOutput().println(nfa.toString());
		//Vector test = nfa.match("A inheritance B association A inheritance B containerComposition A inheritance D", nfa.getInitalState(), null, new Approximation());
		//System.out.println(test);
	}

	public State[] states;

	private NFA(final int size) {
		this.states = new State[size];
	}

	private NFA(final NFA nfa) {
		this(nfa.states.length);

		for (int i = 0; i < nfa.states.length; i++) {
			this.states[i] = new State(nfa.states[i]);
		}
	}

	public NFA(final String string) {
		final StringTokenizer st = new StringTokenizer(string);
		this.states = new State[st.countTokens() / 2 + 1];

		String entity = st.nextToken();
		String relation = st.nextToken();
		String targetEntity = st.nextToken();

		this.states[0] = new State(0);
		this.states[1] = new State(1);
		this.states[0].transitions[0] =
			new Transition(Transition.loopTransition, this.states[0]);
		this.states[0].transitions[1] =
			new Transition(
				entity + " " + relation + " " + targetEntity,
				this.states[1]);

		int i;
		for (i = 2; st.hasMoreTokens(); i++) {
			entity = targetEntity;
			relation = st.nextToken();
			targetEntity = st.nextToken();

			//		    if(relation.equals("dummyRelationship") || relation.equals("dm")){
			//		    	i--;
			//		       	continue;
			//		    }

			this.states[i] = new State(i);
			this.states[i - 1].transitions[0] =
				new Transition(Transition.loopTransition, this.states[i - 1]);
			this.states[i - 1].transitions[1] =
				new Transition(
					entity + " " + relation + " " + targetEntity,
					this.states[i]);
		}
		this.states[i - 1].setFinal(true);
	}

	public Object clone() {
		return new NFA(this);
	}

	public State getFinalState() {
		return this.states[this.states.length - 1];
	}

	public State getInitalState() {
		return this.states[0];
	}

	public String toString() {
		final StringBuffer result = new StringBuffer();
		for (int i = 0; i < this.states.length; i++) {
			result.append(this.states[i].toString()
					+ System.getProperty("line.separator"));
		}
		return result.toString();
	}
}
