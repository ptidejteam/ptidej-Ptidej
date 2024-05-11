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

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import util.io.ProxyConsole;
import epi.util.BitVector;

/**
 * @author OlivierK
 *
 */
public class BitVectorSolver extends EPISolver {

	public BitVectorSolver(
		final String programString,
		final String patternString,
		final String motifName,
		final Approximation approximation,
		final Hashtable domains) {

		super(programString, patternString, motifName, approximation, domains);
	}

	//	approximate list contains list of different relationships.  
	public Solution[] computeSolutions() {

		final Hashtable sparseBitSets =
			this.createSparseBitSets(this.programString);

		final String efficientStringRepresentation =
			this.createEfficientStringRepresentation(
				this.patternString,
				sparseBitSets);

		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("EF: " + efficientStringRepresentation);

		final StringTokenizer st =
			new StringTokenizer(efficientStringRepresentation);
		final int numberOfTokens = st.countTokens();

		if (numberOfTokens < 3) {
			/*error*/;
		}

		final Vector occurenceVector = new Vector();
		Solution patternOccurence = new Solution(this.motifName);
		String entity = st.nextToken();
		String relationship = st.nextToken();
		String targetEntity = st.nextToken();

		if (((List) this.approximationType.getApproximationList().get(
			relationship)).contains("null")) {
			patternOccurence = new Solution(this.motifName);
			patternOccurence.addComponent("null", "null");
			occurenceVector.add(patternOccurence);
		}

		final Vector relationshipVector = new Vector();

		this.createRelationshipVectors(
			sparseBitSets,
			(List) this.approximationType.getApproximationList().get(
				relationship),
			relationshipVector);

		final Iterator relationshipIter = relationshipVector.listIterator();
		while (relationshipIter.hasNext()) {
			final ArrayList tuple = (ArrayList) relationshipIter.next();
			if (this.isRoleCandidate(entity, tuple.get(0).toString())
					&& this.isRoleCandidate(targetEntity, tuple
						.get(1)
						.toString())) {
				patternOccurence = new Solution(this.motifName);
				patternOccurence.addComponent(entity, tuple.get(0));
				patternOccurence.addComponent(targetEntity, tuple.get(1));
				occurenceVector.add(patternOccurence);
			}
		}

		while (st.hasMoreTokens()) {
			entity = targetEntity;
			relationship = st.nextToken();
			targetEntity = st.nextToken();

			if (relationship.matches("dm")
					|| relationship.matches("dummyRelationship")) {
				continue;
			}
			final Vector removeOccurenceVector = new Vector();
			final Vector addOccurenceVector = new Vector();

			final Iterator occurenceIter = occurenceVector.listIterator();
			while (occurenceIter.hasNext()) {
				final Solution aPatternOccurence =
					(Solution) occurenceIter.next();

				if (aPatternOccurence.containsComponent(entity)
						&& aPatternOccurence.containsComponent(targetEntity)) {
					final ArrayList tokenList = new ArrayList();
					tokenList.add(aPatternOccurence.getComponent(entity));
					tokenList.add(relationship);
					tokenList.add(aPatternOccurence.getComponent(targetEntity));
					if (this.isRelationshipExist(sparseBitSets, tokenList)) {
						final Solution patternOccurenceTemp =
							(Solution) aPatternOccurence.clone();
						addOccurenceVector.add(patternOccurenceTemp);
						removeOccurenceVector.add(aPatternOccurence);
					}
					if (!((List) this.approximationType
						.getApproximationList()
						.get(relationship)).contains("null")) {
						removeOccurenceVector.add(aPatternOccurence);
					}
				} //optimisation: deux if peuvent etre jumeles			
				else if (aPatternOccurence.containsComponent(entity)) {
					final ArrayList tokenList = new ArrayList();
					tokenList.add(aPatternOccurence.getComponent(entity));
					tokenList.add(relationship);

					final ArrayList targetEntityList = new ArrayList();
					this.getAfterToken(
						sparseBitSets,
						tokenList,
						targetEntityList);

					final Iterator targetEntityIter =
						targetEntityList.listIterator();
					while (targetEntityIter.hasNext()) {
						final String targetEntityName =
							targetEntityIter.next().toString();
						if (this
							.isRoleCandidate(targetEntity, targetEntityName)) {
							final Solution patternOccurenceTemp =
								(Solution) aPatternOccurence.clone();
							patternOccurenceTemp.addComponent(
								targetEntity,
								targetEntityName);
							addOccurenceVector.add(patternOccurenceTemp);
						}
					}
					if (!((List) this.approximationType
						.getApproximationList()
						.get(relationship)).contains("null")) {
						removeOccurenceVector.add(aPatternOccurence);
					}
				}
				else if (aPatternOccurence.containsComponent(targetEntity)) {
					final ArrayList tokenList = new ArrayList();
					tokenList.add(relationship);
					tokenList.add(aPatternOccurence.getComponent(targetEntity));

					final ArrayList entityList = new ArrayList();
					this.getBeforeToken(sparseBitSets, tokenList, entityList);

					final Iterator entityIter = entityList.listIterator();
					while (entityIter.hasNext()) {
						final String entityName = entityIter.next().toString();
						if (this.isRoleCandidate(entity, entityName)) {
							final Solution patternOccurenceTemp =
								(Solution) aPatternOccurence.clone();
							patternOccurenceTemp.addComponent(
								entity,
								entityName);
							addOccurenceVector.add(patternOccurenceTemp);
						}
					}
					if (!((List) this.approximationType
						.getApproximationList()
						.get(relationship)).contains("null")) {
						removeOccurenceVector.add(aPatternOccurence);
					}
				}
				else {
					final Vector relationshipList = new Vector();
					this.createRelationshipVectors(
						sparseBitSets,
						(List) this.approximationType
							.getApproximationList()
							.get(relationship),
						relationshipList);

					final Iterator relationshipIter2 =
						relationshipList.iterator();
					while (relationshipIter2.hasNext()) {
						final ArrayList tuple =
							(ArrayList) relationshipIter2.next();
						if (this.isRoleCandidate(entity, tuple
							.get(0)
							.toString())
								&& this.isRoleCandidate(targetEntity, tuple
									.get(1)
									.toString())) {
							final Solution patternOccurenceTemp =
								(Solution) aPatternOccurence.clone();
							patternOccurenceTemp.addComponent(
								entity,
								tuple.get(0));
							patternOccurenceTemp.addComponent(
								targetEntity,
								tuple.get(1));
							addOccurenceVector.add(patternOccurenceTemp);
						}
					}
					if (!((List) this.approximationType
						.getApproximationList()
						.get(relationship)).contains("null")) {
						removeOccurenceVector.add(aPatternOccurence);
					}
				}
			}
			occurenceVector.removeAll(removeOccurenceVector);
			occurenceVector.addAll(addOccurenceVector);
		}

		return this
			.vectorToSolutions(this.removeNullComponent(occurenceVector));
	}

	protected void getAfterToken(
		final Hashtable sparseBitSets,
		final List token,
		final List after) {

		if (token.size() != 2) {
			; //error??
		}

		after.clear();

		if (sparseBitSets.containsKey(token.get(0))
		/*&& sparseBitSets.containsKey(token.get(1))*/) {
			final BitVector token0Set =
				(BitVector) ((BitVector) sparseBitSets.get(token.get(0)))
					.clone();

			final BitVector token1Set =
				this
					.getRelationshipBitSet((String) token.get(1), sparseBitSets);

			token0Set.shift(1);
			token0Set.and(token1Set);

			if (token0Set.isEmpty()) {
				return;
			}

			token0Set.shift(1);

			Object key = null;
			BitVector tempSet = null;
			BitVector classSet = null;

			final Enumeration e = sparseBitSets.keys();
			while (e.hasMoreElements()) {
				key = e.nextElement();
				tempSet = (BitVector) token0Set.clone();
				classSet = (BitVector) sparseBitSets.get(key);
				tempSet.and(classSet);

				if (!tempSet.isEmpty()) {
					after.add(key);
				}
			}
		}
	}

	protected void getBeforeToken(
		final Hashtable sparseBitSets,
		final List token,
		final List before) {

		if (token.size() != 2) {
			; //error??
		}

		before.clear();

		if (/*sparseBitSets.containsKey(token.get(0))
			&&*/sparseBitSets.containsKey(token.get(1))) {
			final BitVector token0Set =
				this
					.getRelationshipBitSet((String) token.get(0), sparseBitSets);
			final BitVector token1Set =
				(BitVector) ((BitVector) sparseBitSets.get(token.get(1)))
					.clone();
			token1Set.shift(-1);
			token1Set.and(token0Set);

			if (token1Set.isEmpty()) {
				return;
			}

			token1Set.shift(-1);

			Object key = null;
			BitVector tempSet = null;
			BitVector classSet = null;

			final Enumeration e = sparseBitSets.keys();
			while (e.hasMoreElements()) {
				key = e.nextElement();
				tempSet = (BitVector) token1Set.clone();
				classSet = (BitVector) sparseBitSets.get(key);
				tempSet.and(classSet);

				if (!tempSet.isEmpty()) {
					before.add(key);
				}
			}
		}
	}

	private BitVector getRelationshipBitSet(
		final String relationship,
		final Hashtable sparseBitSets) {

		final List approxList =
			(List) this.approximationType.getApproximationList().get(
				relationship);

		final BitVector sb = new BitVector(this.getMaxValueSize(sparseBitSets));
		final Iterator approxListIter = approxList.iterator();
		while (approxListIter.hasNext()) {
			final String rel = (String) approxListIter.next();
			if (sparseBitSets.containsKey(rel)) {
				sb.or((BitVector) sparseBitSets.get(rel));
			}
		}
		return sb;
	}

	public int getSolverType() {
		return EPISolver.WITHOUT_CONSTRAINT;
	}

	protected boolean isRelationshipExist(
		final Hashtable sparseBitSets,
		final List token) {

		if (token.size() != 3) {
			; //error??
		}

		if (sparseBitSets.containsKey(token.get(0))
		/*&& sparseBitSets.containsKey(token.get(1))*/) {
			final BitVector token0Set =
				(BitVector) ((BitVector) sparseBitSets.get(token.get(0)))
					.clone();

			final BitVector token1Set =
				this
					.getRelationshipBitSet((String) token.get(1), sparseBitSets);

			final BitVector token2Set =
				(BitVector) ((BitVector) sparseBitSets.get(token.get(2)))
					.clone();

			token0Set.shift(1);
			token2Set.shift(-1);

			token0Set.and(token1Set);
			if (!token0Set.isEmpty()) {
				token0Set.and(token2Set);
				if (!token0Set.isEmpty()) {
					return true;
				}
			}
		}
		return false;
	}
}
