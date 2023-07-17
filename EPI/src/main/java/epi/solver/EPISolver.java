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
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import epi.util.BitVector;
import epi.util.ConstraintComparator;

/**
 * @author OlivierK
 *
 */
public abstract class EPISolver {

	protected Approximation approximationType;
	protected String patternString;
	protected String programString;
	protected String motifName;
	protected Hashtable domains;

	// Type of solver
	public static final int WITH_CONSTRAINTS = 1;
	public static final int WITHOUT_CONSTRAINT = 2;
	public static final int AUTOMATON_SOLVER = 3;

	protected EPISolver(
		final String programRepresentation,
		final String motifRepresentation) {

		this(
			programRepresentation,
			motifRepresentation,
			"unknown pattern",
			new Approximation(),
			null);
	}

	protected EPISolver(
		final String programRepresentation,
		final String motifRepresentation,
		final Approximation approximation) {

		this(
			programRepresentation,
			motifRepresentation,
			"unknown pattern",
			approximation,
			null);
	}

	protected EPISolver(
		final String programRepresentation,
		final String motifRepresentation,
		final String motifName) {

		this(
			programRepresentation,
			motifRepresentation,
			motifName,
			new Approximation(),
			null);
	}

	protected EPISolver(
		final String programRepresentation,
		final String motifRepresentation,
		final String motifName,
		final Approximation approximation,
		final Hashtable domains) {

		this.programString = programRepresentation;
		this.patternString = motifRepresentation;
		this.motifName = motifName;
		this.approximationType = approximation;
		this.domains = domains;
	}

	public abstract Solution[] computeSolutions();

	protected String createEfficientStringRepresentation(
		final String aStringRepresentation,
		final Hashtable bitSets) {
		final StringBuffer buffer = new StringBuffer();

		final StringTokenizer st = new StringTokenizer(aStringRepresentation);
		final ArrayList constraintTriplet = new ArrayList();
		final ArrayList ignoranceTriplet = new ArrayList();

		String first = st.nextToken();
		String second = st.nextToken();
		String third = st.nextToken();
		if (second.equals("ignorance")) {
			ignoranceTriplet.add(first + " " + second + " " + third);
		}
		else if (!second.equals("dummyRelationship") && !second.equals("dm")) {
			constraintTriplet.add(first + " " + second + " " + third);
		}
		while (st.hasMoreElements()) {
			first = third;
			second = st.nextToken();
			third = st.nextToken();
			if (second.equals("ignorance")) {
				ignoranceTriplet.add(first + " " + second + " " + third);
			}
			else if (!second.equals("dummyRelationship")
					&& !second.equals("dm")) {
				constraintTriplet.add(first + " " + second + " " + third);
			}
		}
		Collections.sort(constraintTriplet, new ConstraintComparator(this
			.getConstraintPriority(constraintTriplet, bitSets)));

		constraintTriplet.addAll(ignoranceTriplet);
		final Iterator arrayIter = constraintTriplet.iterator();
		while (arrayIter.hasNext()) {
			buffer.append(arrayIter.next());
			buffer.append(' ');
			buffer.append("dm");
			buffer.append(' ');
		}

		// I complete the output with the first vertex which is also the last.
		final String efficientStringRepresentation = buffer.toString();
		final StringTokenizer st2 =
			new StringTokenizer(efficientStringRepresentation);
		if (st2.hasMoreTokens()) {
			buffer.append(st2.nextToken());
		}
		return buffer.toString();
	}

	//	The method finds all the classes before and after 1 or more relationships. The results are stored
	// in a vector of tuple (before, after)
	protected void createRelationshipVectors(
		final Hashtable sparseBitSets,
		final List relationships,
		final Vector afterBefore) {

		final int bitSetSize = this.getMaxValueSize(sparseBitSets);
		afterBefore.clear();

		final BitVector relationSet = new BitVector(bitSetSize);

		final Iterator relationshipsIter = relationships.iterator();
		while (relationshipsIter.hasNext()) {
			final String relationship = (String) relationshipsIter.next();
			if (sparseBitSets.containsKey(relationship)) {
				relationSet.or((BitVector) ((BitVector) sparseBitSets
					.get(relationship)).clone());
			}
		}

		if (relationSet.isEmpty()) {
			return;
		}

		BitVector tempSet = null;
		BitVector tempSet2 = null;
		BitVector classSet = null;

		Object key = null;
		Object key2 = null;

		// To found the classes before and after a relation, we first do a right-shift on the relation bitset.
		// If the intersection of this shifted bitset and the classes bitsets is not empty, we have found 
		// the classes after this relation. We then do a left-shift by 2 on the relation bitset and redo
		// the intersection.
		//
		// Ex: A in B co C
		// Relation "in" bitset: 0 1 0 0 0
		// Class "A" bitset:	 1 0 0 0 0 
		// Class "B" bitset:     0 0 1 0 0
		// The right shift on the relation bitset: 0 1 0 0 0 --> 0 0 1 0 0
		// The intersetion of this new bitset and the class "B" bitset: 0 0 1 0 0 --> B is after an "in" relation
		// The left-shift by 2 on the relation bitset: 0 0 1 0 0 --> 1 0 0 0 0
		// The intersection with the class "A" bitset: 1 0 0 0 0 --> A is before an "in" relation   
		relationSet.shift(1);

		final Enumeration e = sparseBitSets.keys();
		while (e.hasMoreElements()) {
			key = e.nextElement();
			tempSet = (BitVector) relationSet.clone();
			classSet = (BitVector) sparseBitSets.get(key);
			tempSet.and(classSet);

			if (!tempSet.isEmpty()) {
				tempSet.shift(-2);

				final Enumeration e2 = sparseBitSets.keys();
				while (e2.hasMoreElements()) {
					key2 = e2.nextElement();
					classSet = (BitVector) sparseBitSets.get(key2);

					tempSet2 = (BitVector) tempSet.clone();
					tempSet2.and(classSet);
					if (!tempSet2.isEmpty()) {
						final List test = new ArrayList();
						test.add(key2);
						test.add(key);
						afterBefore.add(test);
					}
				}
			}
		}
	}

	//	Creates a hashtable containing the SparseBitSets from a string representation
	protected Hashtable createSparseBitSets(final String aString) {
		final Hashtable sparseBitSets = new Hashtable();

		final StringTokenizer st = new StringTokenizer(aString);
		final int numberOfTokens = st.countTokens();

		String key;
		BitVector tokenBitSet = null;

		for (int index = 0; index < numberOfTokens; index++) {
			key = st.nextToken();

			if (sparseBitSets.containsKey(key)) {
				tokenBitSet = (BitVector) sparseBitSets.get(key);
			}
			else {
				tokenBitSet =
					new BitVector(this.getMaxValueSize(sparseBitSets));
			}
			tokenBitSet.set(index);
			sparseBitSets.put(key, tokenBitSet);
		}

		return sparseBitSets;
	}

	protected boolean doesARelationshipExist(
		final Hashtable sparseBitSets,
		final Object entity,
		final Object targetEntity) {

		if (sparseBitSets.containsKey(entity)
				&& sparseBitSets.containsKey(targetEntity)) {

			final BitVector token1Set =
				(BitVector) ((BitVector) sparseBitSets.get(entity)).clone();

			final BitVector token2Set =
				(BitVector) ((BitVector) sparseBitSets.get(targetEntity))
					.clone();

			BitVector token3Set =
				new BitVector(this.getMaxValueSize(sparseBitSets));
			if (sparseBitSets.containsKey("dm")) {
				token3Set =
					(BitVector) ((BitVector) sparseBitSets.get("dm")).clone();
			}
			BitVector token4Set =
				new BitVector(this.getMaxValueSize(sparseBitSets));
			if (sparseBitSets.containsKey("dummyRelationship")) {
				token4Set =
					(BitVector) ((BitVector) sparseBitSets
						.get("dummyRelationship")).clone();
			}
			token3Set.or(token4Set);

			// token1 rel token2
			token1Set.shift(1);
			token2Set.shift(-1);
			token1Set.and(token2Set);

			// token2 rel token1
			//	token1Set.shift(2, bitSetSize);
			//	token1Set.and(token2Set);

			token1Set.andNot(token3Set);
			if (!token1Set.isEmpty()) {
				return true;
			}
		}
		return false;
	}

	protected Vector getChildren(
		final String entity,
		final Vector inheritanceRelationship) {
		return this.getParentOrChildren(false, entity, inheritanceRelationship);
	}

	private ArrayList getConstraintPriority(
		final ArrayList constraintTriplets,
		final Hashtable bitSets) {

		final Set constraintPrioritySet = new TreeSet(new Comparator() {
			public int compare(final Object o1, final Object o2) {
				final Integer v1 = (Integer) ((Map.Entry) o1).getValue();
				final Integer v2 = (Integer) ((Map.Entry) o2).getValue();
				if (v1.intValue() == v2.intValue()) {
					return 1;
				}
				else {
					return v1.compareTo(v2);
				}
			}
		});

		final Map constraintPriority = new TreeMap();
		final Iterator arrayIter = constraintTriplets.iterator();
		while (arrayIter.hasNext()) {
			final StringTokenizer st =
				new StringTokenizer(arrayIter.next().toString());
			if (st.countTokens() == 3) {
				st.nextToken();
				final String relationship = st.nextToken();

				if (this.approximationType.getApproximationList().containsKey(
					relationship)) {
					final List relationships =
						this.approximationType
							.getApproximationList(relationship);
					int nbOfRel = 0;
					final Iterator relationshipsIter = relationships.iterator();
					while (relationshipsIter.hasNext()) {
						final BitVector temp =
							(BitVector) bitSets.get(relationshipsIter.next());
						if (temp != null) {
							nbOfRel += temp.size();
						}
					}
					if (!constraintPriority.containsKey(relationship)) {
						constraintPriority.put(relationship, Integer.valueOf(
							nbOfRel));
					}
				}
				else {
					constraintPriority.put(relationship, Integer.valueOf(
						Integer.MAX_VALUE));
				}
			}
			else {
				/*error*/
			}
		}

		constraintPrioritySet.addAll(constraintPriority.entrySet());
		final ArrayList priority = new ArrayList();

		final Iterator setIter = constraintPrioritySet.iterator();
		while (setIter.hasNext()) {
			priority.add(((Map.Entry) setIter.next()).getKey());
		}
		return priority;
	}

	// The method returns the length of the longest sparseBitSet in the hashtable
	protected int getMaxValueSize(final Hashtable hs) {
		int size = -1;
		final Enumeration e = hs.elements();
		while (e.hasMoreElements()) {
			final int length = ((BitVector) e.nextElement()).size();
			if (size < length) {
				size = length;
			}
		}
		// assert(position != -1);
		// if(position == -1)
		//	 throw new AssertionError();
		return size;
	}

	protected Vector getParent(
		final String entity,
		final Vector inheritanceRelationship) {
		return this.getParentOrChildren(true, entity, inheritanceRelationship);
	}
	private Vector getParentOrChildren(
		final boolean isParentQuery,
		final String entity,
		final Vector inheritanceRelationship) {
		final Vector parentOrChildrenVect = new Vector();

		final List inheritance = new ArrayList();

		if (entity != null) {
			final Iterator relationIter = inheritanceRelationship.iterator();

			if (isParentQuery) {
				while (relationIter.hasNext()) {
					final List tuple = (List) relationIter.next();
					if (entity.equals(tuple.get(1).toString())) {
						inheritance.add(tuple.get(0));
					}
				}
			}
			else {
				while (relationIter.hasNext()) {
					final List tuple = (List) relationIter.next();
					if (entity.equals(tuple.get(0).toString())) {
						inheritance.add(tuple.get(1));
					}
				}
			}
		}

		if (inheritance != null) {
			parentOrChildrenVect.addAll(inheritance);

			final Iterator iterator = inheritance.iterator();
			while (iterator.hasNext()) {
				final String key = (String) iterator.next();
				parentOrChildrenVect.addAll(this.getParentOrChildren(
					isParentQuery,
					key,
					inheritanceRelationship));
			}
		}
		return parentOrChildrenVect;
	}

	public String getSolverType(final int solverType) {
		if (solverType == EPISolver.WITH_CONSTRAINTS) {
			return "Optimised Bit-Vector Algorithm";
		}
		else if (solverType == EPISolver.WITHOUT_CONSTRAINT) {
			return "Bit-Vector Algorithm";
		}
		else if (solverType == EPISolver.AUTOMATON_SOLVER) {
			return "Automaton Algorithm";
		}
		else {
			return null;
		}

	}

	protected boolean isRoleCandidate(final String role, final String entity) {
		if (this.domains != null
				&& this.domains.containsKey(role.toLowerCase())) {
			return ((List) this.domains.get(role.toLowerCase()))
				.contains(entity);
		}
		else {
			return true;
		}
	}

	protected Vector removeNullComponent(final Vector occurenceVector) {
		final Vector occVect = new Vector();
		final Iterator iterator = occurenceVector.iterator();
		while (iterator.hasNext()) {
			final Solution sol = (Solution) iterator.next();
			if (sol.containsComponent("null")) {
				sol.removeComponent("null");
			}
			occVect.add(sol);
		}
		return occVect;
	}

	public void setApproximation(final Approximation approx) {
		this.approximationType = approx;
	}

	public void setMotifName(final String aMotif) {
		this.motifName = aMotif;
	}

	protected Solution[] vectorToSolutions(final Vector occurenceVector) {
		final Solution[] solutions = new Solution[occurenceVector.size()];
		final Iterator iterator = occurenceVector.iterator();
		for (int i = 0; i < solutions.length; i++) {
			solutions[i] = (Solution) iterator.next();
		}
		return solutions;
	}
}
