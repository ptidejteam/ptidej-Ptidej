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
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;
import util.io.ProxyConsole;

/**
 * @author OlivierK
 *
 */
public class OptimisedBitVectorSolver extends EPISolver {
	public OptimisedBitVectorSolver(
		final String programString,
		final String patternString,
		final String motifName,
		final Approximation approximation) {

		super(programString, patternString, motifName, approximation, null);
	}
	public OptimisedBitVectorSolver(
		final String programString,
		final String patternString,
		final String motifName,
		final Approximation approximation,
		final Hashtable domains) {

		super(programString, patternString, motifName, approximation, domains);
	}
	public Solution[] computeSolutions() {
		final Hashtable sparseBitSets =
			this.createSparseBitSets(this.programString);
		final String efficientStringRepresentation = this.patternString;

		ProxyConsole
			.getInstance()
			.debugOutput()
			.println(efficientStringRepresentation);
		if (efficientStringRepresentation.equals("")) {
			return this.vectorToSolutions(new Vector());
		}

		final Hashtable programRelationshipTable =
			this.getRelationshipTable(sparseBitSets);
		final ArrayList ignoranceConstraint = new ArrayList();

		final StringTokenizer st =
			new StringTokenizer(efficientStringRepresentation);

		final Vector occurenceVector = new Vector();
		Solution patternOccurence = new Solution(this.motifName);
		String entity = st.nextToken();
		String relationship = st.nextToken();
		String targetEntity = st.nextToken();

		final boolean pathInteritanceConstraint =
			((List) this.approximationType.getApproximationList().get(
				"inheritance")).contains("pathInheritance");

		// First triplet... WARNING! The first relation should NOT be "dummyRelationship".
		if (relationship.equals("ignorance")) {
			final ArrayList tuple = new ArrayList();
			tuple.add(entity);
			tuple.add(targetEntity);

			ignoranceConstraint.add(tuple);
		}
		else {
			final List list =
				(List) this.approximationType.getApproximationList().get(
					relationship);
			if (list.contains("null")) {
				patternOccurence = new Solution(this.motifName);
				patternOccurence.addComponent("null", "null");
				occurenceVector.add(patternOccurence);
			}

			final Vector relationshipVector1 =
				this.getRelationshipVector(
					relationship,
					entity,
					targetEntity,
					programRelationshipTable);

			final ListIterator relationshipVector1Iter =
				relationshipVector1.listIterator();
			while (relationshipVector1Iter.hasNext()) {
				final ArrayList tuple =
					(ArrayList) relationshipVector1Iter.next();
				if ((relationship.equals("inheritance") || relationship
					.equals("inheritance2")) && pathInteritanceConstraint) {

					final Vector children =
						this.getChildren(
							tuple.get(0).toString(),
							relationshipVector1);
					final Iterator childrenIterator = children.iterator();
					while (childrenIterator.hasNext()) {
						final ArrayList tuple1 = new ArrayList();
						tuple1.add(tuple.get(0));
						tuple1.add(childrenIterator.next());
						if (!relationshipVector1.contains(tuple1)) {
							relationshipVector1Iter.add(tuple1);
							relationshipVector1Iter.previous();
						}
					}
				}
				patternOccurence = new Solution(this.motifName);
				patternOccurence.addComponent(entity, tuple.get(0));
				patternOccurence.addComponent(targetEntity, tuple.get(1));
				occurenceVector.add(patternOccurence);
			}
		}

		// Next triplets...
		while (st.hasMoreTokens()) {
			final Vector removeOccurenceVector = new Vector();
			final Vector addOccurenceVector = new Vector();

			entity = targetEntity;
			relationship = st.nextToken();
			targetEntity = st.nextToken();

			if (relationship.equals("dm")
					|| relationship.equals("dummyRelationship")) {

				continue;
			}
			else if (relationship.equals("ignorance")) {
				final ArrayList tuple = new ArrayList();
				tuple.add(entity);
				tuple.add(targetEntity);
				ignoranceConstraint.add(tuple);
				continue;
			}

			final Vector relationshipVector =
				(Vector) this.getRelationshipVector(
					relationship,
					entity,
					targetEntity,
					programRelationshipTable).clone();

			final ListIterator occurenceIter = occurenceVector.listIterator();
			while (occurenceIter.hasNext()) {
				final Solution aPatternOccurence =
					(Solution) occurenceIter.next();

				if (aPatternOccurence.containsComponent(entity)
						&& aPatternOccurence.containsComponent(targetEntity)) {
					final ArrayList temp = new ArrayList();
					temp.add(aPatternOccurence.getComponent(entity));
					temp.add(aPatternOccurence.getComponent(targetEntity));
					if (relationshipVector.contains(temp)
							|| (relationship.equals("inheritance") || relationship
								.equals("inheritance2"))
							&& pathInteritanceConstraint
							&& this.getChildren(
								aPatternOccurence
									.getComponent(entity)
									.toString(),
								relationshipVector).contains(
								aPatternOccurence.getComponent(targetEntity))) {
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
					final ListIterator relationshipVectorIter =
						relationshipVector.listIterator();
					while (relationshipVectorIter.hasNext()) {
						final ArrayList aRelation =
							(ArrayList) relationshipVectorIter.next();

						if ((relationship.equals("inheritance") || relationship
							.equals("inheritance2"))
								&& pathInteritanceConstraint) {
							final Vector children =
								this.getChildren(
									aRelation.get(0).toString(),
									relationshipVector);
							final Iterator childrenIterator =
								children.iterator();
							while (childrenIterator.hasNext()) {
								final ArrayList tuple = new ArrayList();
								tuple.add(aRelation.get(0));
								tuple.add(childrenIterator.next());
								if (!relationshipVector.contains(tuple)) {
									relationshipVectorIter.add(tuple);
									relationshipVectorIter.previous();
								}
							}
						}
						if (aRelation.get(0) == aPatternOccurence
							.getComponent(entity)) {
							final Solution patternOccurenceTemp =
								(Solution) aPatternOccurence.clone();
							patternOccurenceTemp.addComponent(
								targetEntity,
								aRelation.get(1).toString());
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
					final ListIterator relationshipVectorIter =
						relationshipVector.listIterator();
					while (relationshipVectorIter.hasNext()) {
						final ArrayList aRelation =
							(ArrayList) relationshipVectorIter.next();
						if ((relationship.equals("inheritance") || relationship
							.equals("inheritance2"))
								&& pathInteritanceConstraint) {
							final Vector parent =
								this.getParent(
									aRelation.get(1).toString(),
									relationshipVector);
							final Iterator parentIterator = parent.iterator();
							while (parentIterator.hasNext()) {
								final ArrayList tuple = new ArrayList();
								tuple.add(parentIterator.next());
								tuple.add(aRelation.get(1));
								if (!relationshipVector.contains(tuple)) {
									relationshipVectorIter.add(tuple);
									relationshipVectorIter.previous();
								}
							}
						}
						if (aRelation.get(1) == aPatternOccurence
							.getComponent(targetEntity)) {
							final Solution patternOccurenceTemp =
								(Solution) aPatternOccurence.clone();
							patternOccurenceTemp.addComponent(entity, aRelation
								.get(0)
								.toString());
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
					//a ajouter toutes relations --> peut arriver si dm
					final ListIterator relationshipVectorIter =
						relationshipVector.listIterator();
					while (relationshipVectorIter.hasNext()) {
						final ArrayList aRelation =
							(ArrayList) relationshipVectorIter.next();
						if ((relationship.equals("inheritance") || relationship
							.equals("inheritance2"))
								&& pathInteritanceConstraint) {
							final Vector children =
								this.getChildren(
									aRelation.get(0).toString(),
									relationshipVector);
							final Iterator childrenIterator =
								children.iterator();
							while (childrenIterator.hasNext()) {
								final ArrayList tuple = new ArrayList();
								tuple.add(aRelation.get(0));
								tuple.add(childrenIterator.next());
								if (!relationshipVector.contains(tuple)) {
									relationshipVectorIter.add(tuple);
									relationshipVectorIter.previous();
								}
							}
						}
						final Solution patternOccurenceTemp =
							(Solution) aPatternOccurence.clone();
						patternOccurenceTemp.addComponent(entity, aRelation
							.get(0)
							.toString());
						patternOccurenceTemp.addComponent(
							targetEntity,
							aRelation.get(1).toString());
						addOccurenceVector.add(patternOccurenceTemp);
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

		// TODO: Commented out ignorance relationship because 
		// we don't have any in our strings...
		// Remove occurrences that doesn't respect the "ignorance" contraints
		if (!ignoranceConstraint.isEmpty()) {
			final Vector removeOccurenceVector = new Vector();
			final ListIterator occurenceIter = occurenceVector.listIterator();
			while (occurenceIter.hasNext()) {
				final Solution aPatternOccurence =
					(Solution) occurenceIter.next();

				final Iterator ignoranceIter = ignoranceConstraint.iterator();
				while (ignoranceIter.hasNext()) {
					final ArrayList tuple = (ArrayList) ignoranceIter.next();
					final Object token1 = tuple.get(0);
					final Object token2 = tuple.get(1);

					if (aPatternOccurence.containsComponent(token1)
							&& aPatternOccurence.containsComponent(token2)) {

						if (this.doesARelationshipExist(
							sparseBitSets,
							aPatternOccurence.getComponent(token1),
							aPatternOccurence.getComponent(token2))) {
							removeOccurenceVector.add(aPatternOccurence);
						}
					}
				}
			}
			occurenceVector.removeAll(removeOccurenceVector);
		}

		return this
			.vectorToSolutions(this.removeNullComponent(occurenceVector));
	}

	private Hashtable getRelationshipTable(final Hashtable sparseBitSets) {
		final Hashtable programRelationshipTable = new Hashtable();
		final Hashtable approximationList =
			this.approximationType.getApproximationList();
		final Enumeration approximateEnum = approximationList.keys();
		while (approximateEnum.hasMoreElements()) {
			final String key = (String) approximateEnum.nextElement();
			if (this.patternString.indexOf(key) != -1) {
				final List relationships = (List) approximationList.get(key);
				final Vector programRelationshipList = new Vector();
				this.createRelationshipVectors(
					sparseBitSets,
					relationships,
					programRelationshipList);

				programRelationshipTable.put(key, programRelationshipList);
			}
		}
		return programRelationshipTable;
	}

	private Vector getRelationshipVector(
		final String relationship,
		final String entity,
		final String targetEntity,
		final Hashtable relationshipTable) {

		final Vector relationshipVector =
			(Vector) ((Vector) relationshipTable.get(relationship)).clone();

		if (this.domains != null) {
			final Vector relationToRemove = new Vector();
			final Iterator relationshipVectorIter =
				relationshipVector.iterator();
			while (relationshipVectorIter.hasNext()) {
				final ArrayList tuple =
					(ArrayList) relationshipVectorIter.next();
				if (this.domains.containsKey(entity.toString().toLowerCase())
						&& !((List) this.domains.get(entity
							.toString()
							.toLowerCase())).contains(tuple.get(0).toString())
						|| this.domains.containsKey(targetEntity
							.toString()
							.toLowerCase())
						&& !((List) this.domains.get(targetEntity
							.toString()
							.toLowerCase())).contains(tuple.get(1).toString())) {
					relationToRemove.add(tuple);
				}
			}
			relationshipVector.removeAll(relationToRemove);
		}
		if (entity.startsWith("EPI_Abstract_")) {
			final Vector relationToRemove = new Vector();
			final Iterator relationshipVectorIter =
				relationshipVector.iterator();
			while (relationshipVectorIter.hasNext()) {
				final ArrayList tuple =
					(ArrayList) relationshipVectorIter.next();
				if (!tuple.get(0).toString().startsWith("EPI_Abstract_")) {
					relationToRemove.add(tuple);
				}
			}
			relationshipVector.removeAll(relationToRemove);
		}
		if (targetEntity.startsWith("EPI_Abstract_")) {
			final Vector relationToRemove = new Vector();
			final Iterator relationshipVectorIter =
				relationshipVector.iterator();
			while (relationshipVectorIter.hasNext()) {
				final ArrayList tuple =
					(ArrayList) relationshipVectorIter.next();
				if (!tuple.get(1).toString().startsWith("EPI_Abstract_")) {
					relationToRemove.add(tuple);
				}
			}
			relationshipVector.removeAll(relationToRemove);
		}
		return relationshipVector;
	}

	public int getSolverType() {
		return EPISolver.WITH_CONSTRAINTS;
	}
}
