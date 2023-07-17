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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import padl.kernel.IClass;
import padl.kernel.ICreation;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IUseRelationship;
import sad.kernel.ICodeSmell;
import sad.kernel.impl.CodeSmellComposite;

/**
 * @author Naouel Moha
 * @author Yann-Gaï¿½l Guï¿½hï¿½neuc
 * @since  2005/02/04
 *
 * The class Relationships allows to check the relationships
 * between code smells.
 */
public class Relationships {

	// Relation
	private final static int RELATION_ASSOC = 1;
	//	private final static int RELATION_AGGREG = 2;
	//	private final static int RELATION_COMPOS = 3;
	private final static int RELATION_INHERIT = 4;

	/**
	 * UniqueInstance is the unique possible Relationships object.
	 */
	private static Relationships UniqueInstance;
	/**
	 *  Returns an unique instance of the Relationships class
	 * @return the unique instance of a Singleton
	 */
	public static Relationships getInstance() {
		if (Relationships.UniqueInstance == null) {
			Relationships.UniqueInstance = new Relationships();
		}
		return Relationships.UniqueInstance;
	}

	private Relationships() {
	}

	/**
	 * Returns the subset of code smells that have an IUseRelationship with 
	 * the code smell given.
	 * 
	 * @param codeSmellRef : side ONE  
	 * @param setCodeSmells : side MANY
	 * @return the subset of code smells
	 */
	private Set doesUseRelationshipExist(
		final int typeOfRelationship,
		final ICodeSmell codeSmellRef,
		final Set setCodeSmells) {

		// Creation of the map (class, codesmell)
		final Map classes = new HashMap();

		final Iterator iterCodeSmell = setCodeSmells.iterator();
		while (iterCodeSmell.hasNext()) {
			final ICodeSmell aCodeSmell = (ICodeSmell) iterCodeSmell.next();
			classes.put(aCodeSmell.getIClass(), aCodeSmell);
		}

		// Creation of the subset of code smells related to the code smell of reference
		Set subset = null;
		if (typeOfRelationship == RELATION_ASSOC) {
			subset = createSubsetOfAssociation(codeSmellRef, classes);
		}
		else if (typeOfRelationship == RELATION_INHERIT) {

			subset = createSubsetOfInheritance(codeSmellRef, classes);
		}

		return subset;
	}

	private Set createSubsetOfInheritance(
		final ICodeSmell codeSmellRef,
		final Map classes) {
		Set subset = new HashSet();
		// TODO: How could the IClass be null???
		// The IClass is null if we are dealing with composite!!

		if (codeSmellRef.getIClass() != null) {
			final Iterator iterListChildren =
				codeSmellRef.getIClass().getIteratorOnInheritingEntities();
			while (iterListChildren.hasNext()) {
				final IFirstClassEntity anChild = (IFirstClassEntity) iterListChildren.next();
				if (anChild instanceof IClass) {
					// Get the codesmell related to the class
					final Object tmpCodeSmell = classes.get(anChild);
					if (tmpCodeSmell != null) {
						subset.add(tmpCodeSmell);
					}
				}
			}

		}

		return subset;

	}

	private Set createSubsetOfAssociation(
		final ICodeSmell codeSmellRef,
		final Map classes) {
		Set subset = new HashSet();
		// TODO: How could the IClass be null???
		// The IClass is null if we are dealing with composite!!
		if (codeSmellRef.getIClass() != null) {
			final Iterator iterRelations =
				codeSmellRef.getIClass().getConcurrentIteratorOnConstituents(
					IUseRelationship.class);
			while (iterRelations.hasNext()) {
				final IUseRelationship relationship =
					(IUseRelationship) iterRelations.next();
				final IFirstClassEntity target = relationship.getTargetEntity();

				// Yann 2007/03/08: Self-reference
				// An entity always knows itself so I prevent
				// self-reference to be used to distinguish
				// code smells.
				if (!(relationship instanceof ICreation)
						&& target instanceof IClass
						&& !target.equals(codeSmellRef.getIClass())) {

					// Get the codesmell related to the class
					final Object tmpCodeSmell = classes.get(target);
					if (tmpCodeSmell != null) {
						subset.add(tmpCodeSmell);
					}
				}
			}
		}
		return subset;
	}

	/**
	 * Returns a map between a code smell from the first set with all the 
	 * code smells from the set 2 it has relationships with
	 * 
	 * @param setCodeSmellsFrom
	 * @param setCodeSmellsTo
	 * @return map<a code smell, a set of code smells>
	 */
	private Map doUseRelationshipsExist(
		final int typeOfRelationship,
		final Set setCodeSmellsFrom,
		final Set setCodeSmellsTo) {

		final Map map = new HashMap();
		final Iterator iterator = setCodeSmellsFrom.iterator();
		while (iterator.hasNext()) {
			final ICodeSmell csFrom = (ICodeSmell) iterator.next();
			final Set subset =
				this.doesUseRelationshipExist(
					typeOfRelationship,
					csFrom,
					setCodeSmellsTo);
			if (!subset.isEmpty()) {
				map.put(csFrom, subset);
			}
		}

		return map;
	}

	/**
	 * Returns a set of code smells composite of associations between ONE setCodeSmellsFrom 
	 * to MANY setCodeSmellsTo
	 * 
	 * @param setCodeSmellsFrom
	 * @param setCodeSmellsTo
	 * @return set<code smells composite>
	 */
	public Set checkAssociationOneToMany(
		final int typeOfRelationship,
		final Set setCodeSmellsFrom,
		final Set setCodeSmellsTo) {
		final Set result = new HashSet();

		// We retain only the subset of DataClasses for each LargeClass
		final Map useRelations =
			this.doUseRelationshipsExist(
				typeOfRelationship,
				setCodeSmellsFrom,
				setCodeSmellsTo);

		final Set setKeys = useRelations.keySet();
		final Iterator iter = setKeys.iterator();
		while (iter.hasNext()) {
			final CodeSmellComposite csc = new CodeSmellComposite();
			final ICodeSmell codeSmell = (ICodeSmell) iter.next();
			csc.addCodeSmell(codeSmell); // we add the main class
			// we add the set of codesmells that have a relationship with the main class
			final Set dataSet = (Set) useRelations.get(codeSmell);
			final Iterator iter2 = dataSet.iterator();
			while (iter2.hasNext()) {
				final ICodeSmell da = (ICodeSmell) iter2.next();
				csc.addCodeSmell(da);
			}
			result.add(csc);
		}
		return result;
	}

	/**
	 * Returns a set of code smells composite of associations between ONE setCodeSmellsFrom 
	 * to ONE setCodeSmellsTo
	 * 
	 * @param setCodeSmellsFrom
	 * @param setCodeSmellsTo
	 * @return set<code smells composite>
	 */
	public Set checkAssociationOneToOne(
		final int typeOfRelationship,
		final Set setCodeSmellsFrom,
		final Set setCodeSmellsTo) {
		final Set result = new HashSet();

		// We retain only the subset of DataClasses for each LargeClass
		final Map useRelations =
			this.doUseRelationshipsExist(
				typeOfRelationship,
				setCodeSmellsFrom,
				setCodeSmellsTo);

		final Set setKeys = useRelations.keySet();
		final Iterator iter = setKeys.iterator();
		while (iter.hasNext()) {
			// The difference with checkAssociationOneToMany is that we put in
			// separate sets 
			final ICodeSmell codeSmell = (ICodeSmell) iter.next();
			final Set dataSet = (Set) useRelations.get(codeSmell);
			final Iterator iter2 = dataSet.iterator();
			while (iter2.hasNext()) {
				final CodeSmellComposite csc = new CodeSmellComposite();
				csc.addCodeSmell(codeSmell); // we add the main class
				// we add the ONLY one codesmell that have a relationship with the main class
				final ICodeSmell da = (ICodeSmell) iter2.next();
				csc.addCodeSmell(da);
				result.add(csc);
			}
		}
		return result;
	}

}
