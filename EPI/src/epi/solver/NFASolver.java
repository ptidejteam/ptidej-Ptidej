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

import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import util.io.ProxyConsole;

/**
 * @author OlivierK
 */
public class NFASolver extends EPISolver {

	public NFASolver(
		final String programString,
		final String patternString,
		final String motifName,
		final Approximation approximation,
		final Hashtable domains) {

		super(programString, patternString, motifName, approximation, domains);
	}

	public Solution[] computeSolutions() {

		final NFA nfa = new NFA(this.patternString);
		ProxyConsole.getInstance().debugOutput().println(nfa.toString());
		final Vector test =
			this.match(this.programString, nfa.getInitalState(), null);
		//System.out.println(test);
		return this.vectorToSolutions(test);
	}

	public int getSolverType() {
		return EPISolver.AUTOMATON_SOLVER;
	}

	public Vector match(
		final String input,
		final State currentState,
		final Solution currentOccurrence) {

		final Vector occurrencesVector = new Vector();
		if (currentState.isFinal()) {
			occurrencesVector.add(currentOccurrence);
			return occurrencesVector;
		}
		final StringTokenizer st = new StringTokenizer(input);

		// A dummy relationship is considered like a epsilon transition
		if (currentState.transitions[1].getToken().indexOf("dummyRelationship") > 0
				|| currentState.transitions[1].getToken().indexOf("dm") > 0) {
			return this.match(
				input,
				currentState.transitions[1].getDestinationState(),
				currentOccurrence);
		}

		if (st.countTokens() >= 3) {
			final String entity = st.nextToken();
			final String relation = st.nextToken();
			final String targetEntity = st.nextToken();

			//for all edges emanating from this node
			for (int i = 0; i < currentState.transitions.length; i++) {
				final String token = currentState.transitions[i].getToken();
				if (token.equals("loop")) {
					occurrencesVector
						.addAll(this.match(
							input.substring(entity.length() + relation.length()
									+ 2),
							currentState.transitions[i].getDestinationState(),
							currentOccurrence));
				}
				else {
					final StringTokenizer stToken = new StringTokenizer(token);
					final String entityToken = stToken.nextToken();
					final String relationToken = stToken.nextToken();
					final String targetToken = stToken.nextToken();
					if (this.approximationType.getApproximationList(
						relationToken).contains("null")) {
						occurrencesVector.addAll(this.match(
							input.substring(entity.length() + relation.length()
									+ 2),
							currentState.transitions[i].getDestinationState(),
							currentOccurrence));
					}
					else if (this.approximationType.getApproximationList(
						relationToken).contains(relation)) {
						Solution newSolution;
						String newProgramString = input;
						if (currentOccurrence == null) {
							newSolution = new Solution();

							//programString is circular so we add the beginning of the string to the input
							String beforeTriplet =
								this.programString.substring(
									0,
									this.programString.indexOf(input));
							if (!beforeTriplet.equals("")) {
								final String firstToken =
									input.substring(
										0,
										beforeTriplet.indexOf(" "));
								beforeTriplet =
									beforeTriplet
										.substring(firstToken.length());
								newProgramString =
									newProgramString.concat(beforeTriplet
											+ firstToken);
							}
						}
						else {
							newSolution = (Solution) currentOccurrence.clone();
						}
						if (newSolution.containsComponent(entityToken)
								&& newSolution.containsComponent(targetToken)) {
							if (newSolution.getComponent(entityToken).equals(
								entity)
									&& newSolution
										.getComponent(targetToken)
										.equals(targetEntity)) {
								occurrencesVector.addAll(this.match(
									newProgramString.substring(entity.length()
											+ relation.length() + 2),
									currentState.transitions[i]
										.getDestinationState(),
									newSolution));
							}
						}
						else if (newSolution.containsComponent(entityToken)) {
							if (newSolution.getComponent(entityToken).equals(
								entity)
									&& this
										.isRoleCandidate(entityToken, entity)) {
								newSolution.addComponent(
									targetToken,
									targetEntity);
								occurrencesVector.addAll(this.match(
									newProgramString.substring(entity.length()
											+ relation.length() + 2),
									currentState.transitions[i]
										.getDestinationState(),
									newSolution));
							}
						}
						else if (newSolution.containsComponent(targetToken)) {
							if (newSolution.getComponent(targetToken).equals(
								targetEntity)
									&& this.isRoleCandidate(
										targetToken,
										targetEntity)) {
								newSolution.addComponent(entityToken, entity);
								occurrencesVector.addAll(this.match(
									newProgramString.substring(entity.length()
											+ relation.length() + 2),
									currentState.transitions[i]
										.getDestinationState(),
									newSolution));
							}
						}
						else if (this.isRoleCandidate(entityToken, entity)
								&& this.isRoleCandidate(
									targetToken,
									targetEntity)) {
							newSolution.addComponent(entityToken, entity);
							newSolution.addComponent(targetToken, targetEntity);
							occurrencesVector.addAll(this.match(
								newProgramString.substring(entity.length()
										+ relation.length() + 2),
								currentState.transitions[i]
									.getDestinationState(),
								newSolution));
						}
					}
				}
			}
		}

		//System.out.println("NOOOO");
		return occurrencesVector;
	}
}
