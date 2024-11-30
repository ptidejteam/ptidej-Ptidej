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
package sad.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import sad.kernel.ICodeSmell;
import sad.kernel.impl.CodeSmellComposite;

/**
 * @author Naouel Moha
 * @author Yann-Gaël Guéhéneuc 
 * @since  2005/12/05
 *
 * The class OperatorsCodeSmells uses the Singleton pattern.
 * It allows operations on sets of code smells.
 * The class to describe a set is HashSet (from package java.util). 
 */
public final class OperatorsCodeSmells {
	/**
	 * UNIQUE_INSTANCE is the unique possible OperatorsCodeSmells object.
	 */
	private static OperatorsCodeSmells UniqueInstance;
	/**
	 *  Returns an unique instance of the OperatorsCodeSmells class
	 * @return the unique instance of a Singleton
	 */
	public static OperatorsCodeSmells getInstance() {
		if (OperatorsCodeSmells.UniqueInstance == null) {
			OperatorsCodeSmells.UniqueInstance = new OperatorsCodeSmells();
		}
		return OperatorsCodeSmells.UniqueInstance;
	}

	private OperatorsCodeSmells() {
	}

	/**
	 * Returns a set of code smells composite. A code smell composite
	 * is composed of an element of each of the specified sets that
	 * designed the same class. We perform here the union of all 
	 * specifed code smells.
	 * 
	 * @param setA : set of code smells
	 * @param setB : set of code smells
	 * @return a set of code smells composite
	 */
	public Set union(final Set setA, final Set setB) {
		final Set resultOfUnion = new HashSet();

		resultOfUnion.addAll(setA);
		resultOfUnion.addAll(setB);

		return resultOfUnion;
	}

	/**
	 * Returns a set of code smells composite. A code smell composite
	 * is composed of an element of each of the specified sets that
	 * designed the same class. We perform here the intersection of all 
	 * specifed code smells.
	 * 
	 * @param setA : set of code smells
	 * @param setB : set of code smells
	 * @return a set of code smells composite. If there is no intersection
	 * between the specified code smells, it returns a code smell composite
	 * empty
	 * @deprecated This method has to be removed because we could make the
	 * intersection of codeSmellComposite so the intersection should be done 
	 * on all 
	 */
	//	public Set intersection(final Set setA, final Set setB) {
	//
	//		// We construct a map<IClass, codeSmell> for the setA 
	//		final HashMap mapSetA = createMapClassCodeSmell(setA);
	//
	//		// We construct a map<IClass, codeSmell> for the setB 
	//		final HashMap mapSetB = createMapClassCodeSmell(setB);
	//
	//		// We do the intersection of the set of classes
	//		final Set resultOfIntersectionClasses = new HashSet(mapSetA.keySet());
	//		resultOfIntersectionClasses.retainAll(mapSetB.keySet());
	//
	//		final Set resultOfIntersection = new HashSet();
	//		if (!resultOfIntersectionClasses.isEmpty()) {
	//			// We get the code smells of the classes of the result of 
	//			// intersection of classes
	//
	//			final Iterator iter = resultOfIntersectionClasses.iterator();
	//			while (iter.hasNext()) {
	//				final CodeSmellComposite csc = new CodeSmellComposite();
	//				final IClass iClass = (IClass) iter.next();
	//				csc.addCodeSmell((ICodeSmell) mapSetA.get(iClass));
	//				csc.addCodeSmell((ICodeSmell) mapSetB.get(iClass));
	//				resultOfIntersection.add(csc);
	//			}
	//		} else {
	//			final CodeSmellComposite csc = new CodeSmellComposite();
	//			resultOfIntersection.add(csc);
	//		}
	//
	//		return resultOfIntersection;
	//	}
	@Deprecated
	public ICodeSmell intersectionCodeSmell(
		final ICodeSmell csA,
		final ICodeSmell csB) {
		if (csA.equals(csB)) {
			final CodeSmellComposite csc = new CodeSmellComposite();
			csc.addCodeSmell(csA);
			csc.addCodeSmell(csB);
			return csc;
		} else
			return null;
	}

	public Set intersection(final Set setA, final Set setB) {

		final Set resultOfIntersection = new HashSet();
		final Iterator iterSetA = setA.iterator();
		while (iterSetA.hasNext()) {
			final ICodeSmell csA = (ICodeSmell) iterSetA.next();
			final Iterator iterSetB = setB.iterator();
			while (iterSetB.hasNext()) {
				final ICodeSmell csB = (ICodeSmell) iterSetB.next();
				final ICodeSmell csInter = intersectionCodeSmell(csA, csB);
				if (csInter != null) {
					resultOfIntersection.add(csInter);
				}
			}
		}
		return resultOfIntersection;
	}

	/**
	 * Create a map<IClass, ICodeSmell> to memorize which class is related to
	 * which code smell.
	 * 
	 */
	//		private HashMap createMapClassCodeSmell(final Set setOfCS) {
	//			final HashMap mapClassCS = new HashMap();
	//			final Iterator iter = setOfCS.iterator();
	//			while (iter.hasNext()) {
	//				final ICodeSmell cs = (ICodeSmell) iter.next();
	//				mapClassCS.put(cs.getIClass(), cs);
	//			}
	//			return mapClassCS;
	//		}

	/**
	 * Returns the set of code smells setA without any
	 * code smells pointing on a class that has a code
	 * smell in setB.
	 * 
	 * @param setA : set of code smells
	 * @param setB : set of code smells
	 * @return setA \ setB
	 */
//	public Set substraction(final Set setA, final Set setB) {
//
//		// We construct a map<IClass, codeSmell> for the setA 
//		final HashMap mapSetA = createMapClassCodeSmell(setA);
//
//		// We construct a map<IClass, codeSmell> for the setB 
//		final HashMap mapSetB = createMapClassCodeSmell(setB);
//
//		// We do the substraction of the set of classes
//		final Set resultOfSubstractionClasses = new HashSet(mapSetA.keySet());
//		resultOfSubstractionClasses.removeAll(mapSetB.keySet());
//
//		final Set resultOfSubstraction = new HashSet();
//		final Iterator iterator = resultOfSubstractionClasses.iterator();
//		while (iterator.hasNext()) {
//			final IClass iClass = (IClass) iterator.next();
//			resultOfSubstraction.add(mapSetA.get(iClass));
//		}
//
//		return resultOfSubstraction;
//	}
}
