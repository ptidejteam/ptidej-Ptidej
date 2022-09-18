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
package padl.motif.detector.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import padl.kernel.Constants;
import padl.kernel.IAbstractModel;
import padl.kernel.IAssociation;
import padl.kernel.IClass;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IInterface;
import padl.motif.IDesignMotifModel;
import padl.motif.detector.IDetector;
import util.io.ProxyConsole;

public class ReflectiveDetector implements IDetector {
	private IDesignMotifModel currentPattern;

	// Criterias.
	public static final int InheritsCriteria = 1; // 3rd criteria.
	public static final int ImplementationsCriteria = 2; // 4th criteria.
	public static final int AssociationsCriteria = 4; // 5th criteria.
	public static final int AllCriterias = 7;

	public Map applyCriterias(final Map currentSolution, final int criterias) {
		final Map matchedCombination = new HashMap(currentSolution);
		boolean changed;

		if (matchedCombination.size() != this
			.getPattern()
			.getNumberOfConstituents()) {

			// No solutions.
			return new HashMap();
		}

		// Arc-consistency... AC1
		do {
			changed = false;
			// 3rd criteria, "inherits", must be implemented... 

			// 4th criteria, (subtype dependant) "implementation"...
			if ((criterias & ReflectiveDetector.ImplementationsCriteria) == ReflectiveDetector.ImplementationsCriteria) {

				final Iterator iterator =
					matchedCombination.keySet().iterator();
				while (iterator.hasNext()) {
					final String currentKey = (String) iterator.next();
					final Object currentElement =
						getPattern().getConstituentFromID(currentKey);

					if (currentElement instanceof IClass) {
						final IClass currentPClass = (IClass) currentElement;
						final Iterator iterator2 =
							currentPClass.getIteratorOnImplementedInterfaces();
						while (iterator2.hasNext()) {
							final IFirstClassEntity currentShouldImplement =
								(IFirstClassEntity) iterator2.next();
							if (matchedCombination
								.containsKey(currentShouldImplement.getID())) {
								final List tmpVectorImp = new ArrayList();
								final List tmpVectorInt = new ArrayList();
								final Iterator iterator3 =
									((List) matchedCombination.get(currentKey))
										.iterator();
								while (iterator3.hasNext()) {
									final IClass currentAnalyzed =
										(IClass) iterator3.next();
									final Iterator iterator4 =
										((List) matchedCombination
											.get(currentShouldImplement.getID()))
											.iterator();
									while (iterator4.hasNext()) {
										final IInterface currentImplemented =
											(IInterface) iterator4.next();
										final Iterator iteratorOnImplementedInterfaces =
											currentAnalyzed
												.getIteratorOnImplementedInterfaces();
										while (iteratorOnImplementedInterfaces
											.hasNext()) {
											final IFirstClassEntity implementedEntity =
												(IFirstClassEntity) iteratorOnImplementedInterfaces
													.next();
											if (implementedEntity
												.equals(currentImplemented)) {
												if (!tmpVectorImp
													.contains(currentAnalyzed)) {
													tmpVectorImp
														.add(currentAnalyzed);
												}
												if (!tmpVectorInt
													.contains(currentImplemented)) {
													tmpVectorInt
														.add(currentImplemented);
												}
											}
										}
									}
								}
								if (tmpVectorImp.size() == 0) {
									return new HashMap();
									// No actor for this role, exit...
								}

								// Remove not implemented PInterfaces... 
								matchedCombination.put(currentShouldImplement
									.getID(), tmpVectorInt);
								// Remove PClasses that do not implement, at least, one Interface of the "currentShouldImplement" actor... 
								matchedCombination
									.put(currentKey, tmpVectorImp);
							}
						}
					}
					else {
						// This is not a PClass, next...
					}
				}
			}
			if (Constants.DEBUG) {
				ProxyConsole
					.getInstance()
					.debugOutput()
					.println(
						"- 3 & 4 -\n"
								+ this.displayCombination(matchedCombination));
			}

			// 5th criteria (subtype dependant) "associations"...
			if ((criterias & ReflectiveDetector.AssociationsCriteria) == ReflectiveDetector.AssociationsCriteria) {
				final Iterator iterator =
					matchedCombination.keySet().iterator();
				while (iterator.hasNext()) {
					final String currentKey = (String) iterator.next();
					final IFirstClassEntity currentPEntity =
						(IFirstClassEntity) getPattern().getConstituentFromID(
							currentKey);
					// Yann 2005/10/12: Iterator!
					// I have now an iterator able to iterate over a
					// specified type of constituent of a list.
					final Iterator iterator2 =
						currentPEntity
							.getIteratorOnConstituents(IAssociation.class);
					while (iterator2.hasNext()) {
						final IAssociation association =
							(IAssociation) iterator2.next();
						if (matchedCombination.containsKey(association
							.getTargetEntity()
							.getID())) {
							final char[] currentTargetKey =
								association.getTargetEntity().getID();
							// Looking for a matched PAssoc in the generated pattern...
							final List tmpVectorVal = new ArrayList();
							// Values set.
							final List tmpVectorSupport = new ArrayList();
							// Supports set.
							final Iterator iterator3 =
								((List) matchedCombination.get(currentKey))
									.iterator();
							while (iterator3.hasNext()) {
								final IFirstClassEntity currentAnalyzedEntity =
									(IFirstClassEntity) iterator3.next();
								// Yann 2005/10/12: Iterator!
								// I have now an iterator able to iterate over a
								// specified type of constituent of a list.
								final Iterator iterator4 =
									currentAnalyzedEntity
										.getIteratorOnConstituents(IAssociation.class);
								while (iterator4.hasNext()) {
									final IAssociation anotherAssociation =
										(IAssociation) iterator4.next();

									// Verification of the cardinality.
									if (anotherAssociation.getCardinality() != association
										.getCardinality()) {
										continue;
									}
									final Iterator iterator5 =
										((List) matchedCombination
											.get(currentTargetKey)).iterator();
									while (iterator5.hasNext()) {
										final IFirstClassEntity support =
											(IFirstClassEntity) iterator5.next();
										if (anotherAssociation
											.getTargetEntity() == support) {
											// Shouldn't it be a call to method equals(...) ???
											if (!tmpVectorVal
												.contains(currentAnalyzedEntity)) {
												tmpVectorVal
													.add(currentAnalyzedEntity);
											}
											if (!tmpVectorSupport
												.contains(support)) {
												tmpVectorSupport.add(support);
											}
										}
									}
								}
							}
							if (tmpVectorVal.size() == 0
									|| tmpVectorSupport.size() == 0) {
								return new HashMap();
								// No values or supports, exit ...
							}
							if (tmpVectorVal.size() != ((List) matchedCombination
								.get(currentKey)).size()) {
								matchedCombination
									.put(currentKey, tmpVectorVal);
								changed = true;
							}
							if (tmpVectorSupport.size() != ((List) matchedCombination
								.get(currentTargetKey)).size()) {
								matchedCombination.put(
									currentTargetKey,
									tmpVectorSupport);
								changed = true;
							}
						}
					}
				}
			}
		}
		while (changed);

		// Others criterias are pattern dependant... (must be subclassed).
		if (Constants.DEBUG) {
			ProxyConsole.getInstance().debugOutput().println(
				"- 5 -\n" + this.displayCombination(matchedCombination));
		}
		return matchedCombination;
	}
	public Map buildPartialSolution(final IAbstractModel aModel) {
		final Map matchedCombination = new HashMap();
		if (this.getPattern() == null || aModel == null) {
			return matchedCombination;
		}

		// 1st criteria , size adequation...
		if (aModel.getNumberOfConstituents() < this
			.getPattern()
			.getNumberOfConstituents()) {
			return matchedCombination;
		}

		// Building partial solutions with the 2nd criteria... Type and (size) adequation...
		final Iterator iterator = this.getPattern().getIteratorOnConstituents();
		while (iterator.hasNext()) {
			final IFirstClassEntity currentPEntity = (IFirstClassEntity) iterator.next();
			final Iterator iterator2 = aModel.getIteratorOnConstituents();
			while (iterator2.hasNext()) {
				final IFirstClassEntity currentAnalyzed = (IFirstClassEntity) iterator2.next();
				if (currentPEntity.getClass() == currentAnalyzed.getClass()
						&& currentAnalyzed.getNumberOfConstituents() >= currentPEntity
							.getNumberOfConstituents()) {
					// 2nd criteria is verified...
					final List tmpVector;
					if (!matchedCombination.containsKey(currentPEntity.getID())) {
						tmpVector = new ArrayList();
						matchedCombination.put(
							currentPEntity.getID(),
							tmpVector);
					}
					else {
						tmpVector =
							(List) matchedCombination.get(currentPEntity
								.getID());
					}
					tmpVector.add(currentAnalyzed);
				}
			}
		}
		if (matchedCombination.size() != this
			.getPattern()
			.getNumberOfConstituents()) {

			return new HashMap(); // No solutions.
		}
		if (Constants.DEBUG) {
			ProxyConsole.getInstance().debugOutput().println(
				"- 1 & 2 -\n" + displayCombination(matchedCombination));
		}
		return matchedCombination;
	}
	public String displayCombination(final Map map) {
		if (map != null) {
			final StringBuffer buffer = new StringBuffer();
			final Iterator iterator1 = map.keySet().iterator();
			while (iterator1.hasNext()) {
				final Object o = iterator1.next();
				buffer.append(o);
				buffer.append(": {");
				final Iterator iterator2 = ((List) map.get(o)).iterator();
				while (iterator2.hasNext()) {
					buffer.append(((IFirstClassEntity) iterator2.next()).getName());
					if (iterator2.hasNext()) {
						buffer.append(", ");
					}
				}
				buffer.append(" }\n");
			}
			return buffer.toString();
		}
		return "";
	}
	public IDesignMotifModel getPattern() {
		return this.currentPattern;
	}
	public void setPattern(final IDesignMotifModel pattern) {
		this.currentPattern = pattern;
	}
}
