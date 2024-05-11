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
package ptidej.solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import padl.kernel.IAbstractModel;
import padl.kernel.IClass;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGhost;
import padl.kernel.IInterface;
import padl.motif.IDesignMotifModel;
import util.io.ProxyConsole;

public final class OccurrenceBuilder {
	private static OccurrenceBuilder UniqueInstance;
	public static OccurrenceBuilder getInstance() {
		if (OccurrenceBuilder.UniqueInstance == null) {
			OccurrenceBuilder.UniqueInstance = new OccurrenceBuilder();
		}
		return OccurrenceBuilder.UniqueInstance;
	}
	private static Occurrence[] getRawOccurrences(final Properties properties) {
		try {
			// I create the GroupSolution objects.
			final Map listOfSolutions = new HashMap();
			Iterator iterator = properties.keySet().iterator();
			for (int i = 0; i < properties.size(); i++) {
				String key = (String) iterator.next();
				int firstDotPos = key.indexOf('.');

				// If it's an interesting value
				//     of the form <solutionNumber>.<percentage>.<actorName> = <correspondingEntityName>
				//     and not of the form [<blabla>]
				if (firstDotPos > 0) {
					final int number =
						Integer.parseInt(key.substring(0, firstDotPos));
					final int percentage =
						Integer.parseInt(key.substring(
							++firstDotPos,
							key.indexOf('.', firstDotPos)));
					final char[] name =
						key
							.substring(
								key.indexOf('.', key.indexOf('.') + 1) + 1)
							.toCharArray();
					final char[] value =
						properties.getProperty(key).toCharArray();

					// The story goes like this: the keys are in random order.
					// I first need to build a DGroupSolution if a group solution
					// with this given solution number does not already exist.
					// If it already exists, I shall use the previously created one.
					final Integer solutionKey = Integer.valueOf(number);
					Occurrence solution;
					if (listOfSolutions.containsKey(solutionKey)) {
						solution =
							(Occurrence) listOfSolutions.get(solutionKey);
					}
					else {
						solution = new Occurrence(number, percentage);
						listOfSolutions.put(solutionKey, solution);
					}

					// Now that I have a GroupSolution in my hand,
					// I'm looking for the entity that correspond to
					// the role found in the key being analysed.
					// If such an entity does not exist, it means that
					// this solution is not valid for the given model
					// and should be removed from the list of solutions.

					// Yann 2002/08/05: Cannot happen anymore
					// The case in which the solution is not valid
					// for the given source model cannot happen
					// anymore. (I just don't want it to happen
					// and also I added a method to clear the
					// result files from the constraint system.)

					solution.addComponent(new OccurrenceComponent(name, value));
				}
			}

			Occurrence[] solutions = new Occurrence[listOfSolutions.size()];
			iterator = listOfSolutions.values().iterator();
			for (int i = 0; i < solutions.length; i++) {
				solutions[i] = (Occurrence) iterator.next();
			}

			return solutions;
		}
		catch (final Exception e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		return new Occurrence[0];
	}
	private static void sortOccurrencesByNames(final Occurrence[] solutions) {
		// I sort the solutions from the greatest percentage
		// to the smallest. Then, I sort the solutions in its
		// successive components.
		//	Arrays.sort(solutions, new Comparator() {
		//		public int compare(final Object o1, final Object o2) {
		//			final Solution s1 = (Solution) o1;
		//			final Solution s2 = (Solution) o2;
		//			if (s1.getPercentage() != s2.getPercentage()) {
		//				return s1.getPercentage() - s2.getPercentage();
		//			}
		//			else if (
		//				s1.getComponents().size()
		//					!= s2.getComponents().size()) {
		//				return s1.getComponents().size()
		//					- s2.getComponents().size();
		//			}
		//			else {
		//				// The components are already sorted
		//				// alphabetically (see Solution.addComponent()).
		//				final List cs1 = s1.getComponents();
		//				final List cs2 = s2.getComponents();
		//				for (int i = 0; i < cs1.size(); i++) {
		//					final SolutionComponent c1 =
		//						(SolutionComponent) cs1.get(i);
		//					final SolutionComponent c2 =
		//						(SolutionComponent) cs2.get(i);
		//					if (!c1.getName().equals(c2.getName())) {
		//						return c1.getName().compareTo(c2.getName());
		//					}
		//					else {
		//						return c1.getValue().compareTo(c2.getValue());
		//					}
		//				}
		//				return 0;
		//			}
		//		}
		//	});
		// Yann 2004/09/19: Order!
		// The previous order wasn't very stable... Now, I compute 
		// strings representing the solutions and compare these with
		// one another.
		Arrays.sort(solutions, new Comparator() {
			public int compare(final Object o1, final Object o2) {
				final String solution1 = this.toString((Occurrence) o1);
				final String solution2 = this.toString((Occurrence) o2);
				return solution1.compareTo(solution2);
			}
			private String toString(final Occurrence solution) {
				final StringBuffer buffer = new StringBuffer();
				buffer.append(solution.getConfidence());

				// The components are already sorted
				// alphabetically (see Solution.addComponent()).
				final Iterator iterator = solution.getComponents().iterator();
				while (iterator.hasNext()) {
					buffer.append(((OccurrenceComponent) iterator.next())
						.getValue());
				}

				return buffer.toString();
			}
		});
	}
	private static void sortOccurrencesByPercentages(
		final Occurrence[] solutions) {
		// I sort the solutions from the greatest percentage
		// to the smallest. Then, I sort the solutions in its
		// successive components.
		Arrays.sort(solutions, new Comparator() {
			public int compare(final Object o1, final Object o2) {
				final Occurrence s1 = (Occurrence) o1;
				final Occurrence s2 = (Occurrence) o2;
				if (s1.getConfidence() != s2.getConfidence()) {
					return s2.getConfidence() - s1.getConfidence();
				}
				else if (s1.getComponents().size() != s2.getComponents().size()) {
					return s2.getComponents().size()
							- s1.getComponents().size();
				}
				else {
					// The components are already sorted
					// alphabetically (see Solution.addComponent()).
					final List cs1 = s1.getComponents();
					final List cs2 = s2.getComponents();
					// Yann 2013/05/16: Weird ol'code
					// Test why this following piece of code only consider the first component.
					//	for (int i = 0; i < cs1.size(); i++) {
					//		final OccurrenceComponent c1 =
					//			(OccurrenceComponent) cs1.get(i);
					//		final OccurrenceComponent c2 =
					//			(OccurrenceComponent) cs2.get(i);
					//
					//		if (!Arrays.equals(c1.getName(), c2.getName())) {
					//			return c2.getDisplayName().compareTo(
					//				c1.getDisplayName());
					//		}
					//		else {
					//			return c2.getDisplayValue().compareTo(
					//				c1.getDisplayValue());
					//		}
					//	}
					//	return 0;
					final OccurrenceComponent c1 =
						(OccurrenceComponent) cs1.get(0);
					final OccurrenceComponent c2 =
						(OccurrenceComponent) cs2.get(0);

					if (!Arrays.equals(c1.getName(), c2.getName())) {
						return c2.getDisplayName().compareTo(
							c1.getDisplayName());
					}
					else {
						return c2.getDisplayValue().compareTo(
							c1.getDisplayValue());
					}
				}
			}
		});
	}

	private OccurrenceBuilder() {
	}
	private boolean belongsTo(
		final IFirstClassEntity anEntity,
		final IDesignMotifModel aMotifModel) {

		// Yann 2005/10/07: Packages!
		// A model may now contrain entities and packages.
		// Yann 2005/10/12: Iterator!
		// I have now an iterator able to iterate over a
		// specified type of constituent of a list.
		final Iterator iterator =
			aMotifModel.getIteratorOnConstituents(IFirstClassEntity.class);
		while (iterator.hasNext()) {
			final IFirstClassEntity firstClassEntity =
				(IFirstClassEntity) iterator.next();
			if (Arrays.equals(firstClassEntity.getID(), anEntity.getID())) {
				return true;
			}
		}
		return false;
	}
	public Occurrence[] getAllOccurrences(final Properties someProperties) {
		final Occurrence[] solutions =
			OccurrenceBuilder.getRawOccurrences(someProperties);
		OccurrenceBuilder.sortOccurrencesByNames(solutions);
		return solutions;
	}
	public Occurrence[] getCanonicalOccurrences(final Properties someProperties) {
		// I clean up the GroupSolution objects to keep only the interesting ones.
		// Yann 2001/07/19: Noisy!
		// Now that I have all the solutions, I must remove distorted solutions
		// that have the same actors as complete solutions: These distorted
		// solution are useless.

		// Yann 2001/11/19: Improved (?)
		// The method is now independent of the solution builder algorithm.
		// The algorithm has been tuned to keep only interesting solutions.
		Occurrence[] solutions =
			OccurrenceBuilder.getRawOccurrences(someProperties);
		OccurrenceBuilder.sortOccurrencesByPercentages(solutions);

		for (int i = solutions.length - 1; i > 0; i--) {
			final List currentSolutionComponents = solutions[i].getComponents();
			boolean keepSolution = true;

			for (int j = 0; j < i && keepSolution; j++) {
				final List otherSolutionComponents =
					solutions[j].getComponents();

				if (currentSolutionComponents.size() == otherSolutionComponents
					.size()) {

					boolean componentsEqual = true;
					for (int k = 0; k < currentSolutionComponents.size(); k++) {
						final OccurrenceComponent currentSolutionComponent =
							(OccurrenceComponent) currentSolutionComponents
								.get(k);
						final OccurrenceComponent otherSolutionComponent =
							(OccurrenceComponent) otherSolutionComponents
								.get(k);

						if (!Arrays.equals(
							currentSolutionComponent.getName(),
							Occurrence.NAME)
								&& !Arrays.equals(
									currentSolutionComponent.getName(),
									Occurrence.COMMAND)) {

							componentsEqual &=
								currentSolutionComponent
									.equals(otherSolutionComponent);
						}
					}

					keepSolution = !componentsEqual;
				}
				else {
					keepSolution = true;
				}
			}

			if (!keepSolution) {
				Occurrence[] temporarySolutions =
					new Occurrence[solutions.length - 1];
				System.arraycopy(solutions, 0, temporarySolutions, 0, i);
				System.arraycopy(
					solutions,
					i + 1,
					temporarySolutions,
					i,
					solutions.length - i - 1);
				solutions = temporarySolutions;
			}
		}

		OccurrenceBuilder.sortOccurrencesByNames(solutions);

		return solutions;
	}
	public MicroArchitectureModel getMicroArchitectureModel(
		final Occurrence solution,
		final IAbstractModel sourceCode) {

		final MicroArchitectureModel model =
			new MicroArchitectureModel(
				solution.getNumber(),
				solution.getConfidence());

		// Yann 2002/08/05: Clone protocol.
		// I must follow the clone protocol as defined in PatternsBox.
		Iterator iterator = solution.getComponents().iterator();
		while (iterator.hasNext()) {
			final OccurrenceComponent solutionComponent =
				(OccurrenceComponent) iterator.next();
			final char[] componentName = solutionComponent.getName();
			final char[] componentValue = solutionComponent.getValue();

			// Yann 2002/08/05: XCommand and duplicate
			// I must not consider the XCommand solution component.
			// Also, I must remove possible duplicate (when two
			// solution components have the same value).
			if (!Arrays.equals(componentName, Occurrence.NAME)
					&& !Arrays.equals(componentName, Occurrence.COMMAND)
					&& model.getConstituentFromID(componentValue) == null) {

				final IFirstClassEntity firstClassEntity =
					(IFirstClassEntity) sourceCode
						.getConstituentFromID(componentValue);
				if (firstClassEntity != null) {
					firstClassEntity.startCloneSession();
				}
			}
		}

		iterator = solution.getComponents().iterator();
		while (iterator.hasNext()) {
			final OccurrenceComponent solutionComponent =
				(OccurrenceComponent) iterator.next();
			final char[] componentName = solutionComponent.getName();
			final char[] componentValue = solutionComponent.getValue();

			if (!Arrays.equals(componentName, Occurrence.NAME)
					&& !Arrays.equals(componentName, Occurrence.COMMAND)
					&& model.getConstituentFromID(componentValue) == null) {

				final IFirstClassEntity firstClassEntity =
					(IFirstClassEntity) sourceCode
						.getConstituentFromID(componentValue);
				if (firstClassEntity != null) {
					firstClassEntity.performCloneSession();
					model.addConstituent((IFirstClassEntity) firstClassEntity
						.getClone());
				}
			}
		}

		iterator = solution.getComponents().iterator();
		while (iterator.hasNext()) {
			final OccurrenceComponent solutionComponent =
				(OccurrenceComponent) iterator.next();
			final char[] name = solutionComponent.getName();
			final char[] value = solutionComponent.getValue();

			if (!Arrays.equals(name, Occurrence.NAME)
					&& !Arrays.equals(name, Occurrence.COMMAND)
					&& model.getConstituentFromID(value) == null) {

				final IFirstClassEntity firstClassEntity =
					(IFirstClassEntity) sourceCode.getConstituentFromID(value);
				if (firstClassEntity != null) {
					firstClassEntity.endCloneSession();
				}
			}
		}

		// Yann 01/07/18: I must manage the case of A -|>- ... -|>- B,
		// Where A and B belong to the new subset pattern. That is, I
		// must change the superclass of A, to point straight to B.
		// (I must deal with superclasses and superinterfaces alike.)
		this.reConnectSuperEntities(model, sourceCode);

		return model;
	}
	private IFirstClassEntity reConnectSuperClass(
		final IClass aClass,
		final IDesignMotifModel aMotifModel,
		final IAbstractModel anAbstractModel) {

		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) anAbstractModel.getConstituentFromID(aClass
				.getID());
		if (firstClassEntity instanceof IClass) {
			final Iterator listOfInheritedEntities =
				((IClass) firstClassEntity).getIteratorOnInheritedEntities();

			if (!listOfInheritedEntities.hasNext()) {
				return null;
			}

			// Yann 2002/08/06: Ghost!
			// I must manage ghost entity.
			// Yann 2003/04/07: Pattern are different from Source!
			// An entity in a pattern might not have the same
			// superclass and superinterfaces as its counterpart
			// in the source code because the pattern only is a
			// subset of the source code! So, I must reconnect
			// superclass and superinterfaces according to the
			// entity in the source code!
			final IFirstClassEntity superclass =
				(IFirstClassEntity) listOfInheritedEntities.next();

			// No more superclass or the superclass
			// exists in the pattern model.
			if (superclass == null || this.belongsTo(superclass, aMotifModel)
					|| superclass instanceof IGhost) {

				// Yann 2014/05/09: Cloning is perfect!
				// Seriously, I should not add the inherited entity
				// if it has already been added thanks to the cloning.
				if (firstClassEntity.getInheritedEntityFromID(superclass
					.getID()) != null) {

					return null;
				}
				else {
					return superclass;
				}
			}

			final List listOfsuperinterfaces = new ArrayList();
			this.reConnectSuperInterfaces(
				(IClass) superclass,
				aMotifModel,
				anAbstractModel,
				listOfsuperinterfaces);
			final Iterator iterator = listOfsuperinterfaces.iterator();
			while (iterator.hasNext()) {
				aClass.addImplementedInterface((IInterface) iterator.next());
			}

			return this.reConnectSuperClass(
				(IClass) superclass,
				aMotifModel,
				anAbstractModel);
		}
		else {
			return null;
		}
	}
	private void reConnectSuperEntities(
		final IDesignMotifModel pattern,
		final IAbstractModel sourceCode) {

		// Yann 2005/10/07: Packages!
		// A model may now contrain entities and packages.
		// Yann 2005/10/12: Iterator!
		// I have now an iterator able to iterate over a
		// specified type of constituent of a list.
		final Iterator iterator =
			pattern.getIteratorOnConstituents(IFirstClassEntity.class);
		while (iterator.hasNext()) {
			final IFirstClassEntity firstClassEntity =
				(IFirstClassEntity) iterator.next();
			IFirstClassEntity superEntity;

			// The trick is that I do not remove invalid superEntities:
			// I must keep them, but I add new (and correct) ones.
			if (firstClassEntity instanceof IClass) {
				// First, I check the superInterfaces.
				final List listOfsuperinterfaces = new ArrayList();
				this.reConnectSuperInterfaces(
					(IClass) firstClassEntity,
					pattern,
					sourceCode,
					listOfsuperinterfaces);
				final Iterator iterator2 = listOfsuperinterfaces.iterator();
				while (iterator2.hasNext()) {
					// Yann 2003/04/07: Ghost!
					// A superinterface may be an interface or a ghost!
					superEntity = (IFirstClassEntity) iterator2.next();
					((IClass) firstClassEntity)
						.addImplementedInterface((IInterface) superEntity);
				}

				// Then, I check the superClasses.
				final IFirstClassEntity superClass =
					this.reConnectSuperClass(
						(IClass) firstClassEntity,
						pattern,
						sourceCode);
				if (superClass != null) {
					firstClassEntity.addInheritedEntity(superClass);
				}
			}
			else if (firstClassEntity instanceof IInterface) {
				// I check its superInterfaces.
				final List listOfsuperinterfaces = new ArrayList();
				this.reConnectSuperInterfaces(
					(IInterface) firstClassEntity,
					pattern,
					sourceCode,
					listOfsuperinterfaces);
				final Iterator iterator2 = listOfsuperinterfaces.iterator();
				while (iterator2.hasNext()) {
					// Yann 2003/04/07: Ghost!
					// A superinterface may be an interface or a ghost!
					superEntity = (IFirstClassEntity) iterator2.next();
					((IInterface) firstClassEntity)
						.addInheritedEntity(superEntity);
				}
			}
		}
	}
	private void reConnectSuperInterfaces(
		final IClass aClass,
		final IDesignMotifModel aMotifModel,
		final IAbstractModel anAbstractModel,
		final List aListOfSuperinterfaces) {

		// Yann 2002/08/06: Ghost!
		// I must manage ghost entities.
		// Yann 2003/04/07: Pattern are different from Source!
		// An entity in a pattern might not have the same
		// superclass and superinterfaces as its counterpart
		// in the source code because the pattern only is a
		// subset of the source code! So, I must reconnect
		// superclass and superinterfaces according to the
		// entity in the source code!
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) anAbstractModel.getConstituentFromID(aClass
				.getID());
		if (firstClassEntity instanceof IClass) {
			final Iterator iterator =
				((IClass) firstClassEntity)
					.getIteratorOnImplementedInterfaces();
			while (iterator.hasNext()) {
				final IFirstClassEntity superinterface =
					(IFirstClassEntity) iterator.next();
				if (this.belongsTo(superinterface, aMotifModel)
						|| superinterface instanceof IGhost) {

					// Yann 2014/05/09: Cloning is perfect!
					// Seriously, I should not add the implemented entity
					// if it has already been added thanks to the cloning.
					if (((IClass) firstClassEntity)
						.getImplementedInterface(superinterface.getID()) != null) {

						aListOfSuperinterfaces.add(superinterface);
					}
				}
				else {
					this.reConnectSuperInterfaces(
						(IInterface) superinterface,
						aMotifModel,
						anAbstractModel,
						aListOfSuperinterfaces);
				}
			}
		}
	}
	private void reConnectSuperInterfaces(
		final IInterface anInterface,
		final IDesignMotifModel aMotifModel,
		final IAbstractModel anAbstractModel,
		final List aListOfSuperinterfaces) {

		// Yann 2003/04/07: Pattern are different from Source!
		// An entity in a pattern might not have the same
		// superclass and superinterfaces as its counterpart
		// in the source code because the pattern only is a
		// subset of the source code! So, I must reconnect
		// superclass and superinterfaces according to the
		// entity in the source code!
		final Iterator iterator =
			((IInterface) anAbstractModel.getConstituentFromID(anInterface
				.getID())).getIteratorOnInheritedEntities();
		while (iterator.hasNext()) {
			final IFirstClassEntity superinterface =
				(IFirstClassEntity) iterator.next();
			if (this.belongsTo(superinterface, aMotifModel)
					|| superinterface instanceof IGhost) {

				// Yann 2014/05/06: Object is everywhere!
				// I should not forget that now, cleanly,
				// Object is the root of the class and (!)
				// interface hierarchy :-) So, it will
				// appear multiple times...
				if (!aListOfSuperinterfaces.contains(superinterface)) {
					aListOfSuperinterfaces.add(superinterface);
				}
			}
			else {
				this.reConnectSuperInterfaces(
					(IInterface) superinterface,
					aMotifModel,
					anAbstractModel,
					aListOfSuperinterfaces);
			}
		}
	}
}
