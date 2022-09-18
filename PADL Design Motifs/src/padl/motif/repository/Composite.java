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
package padl.motif.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import padl.event.IModelListener;
import padl.kernel.Constants;
import padl.kernel.IAbstractModel;
import padl.kernel.IAssociation;
import padl.kernel.IClass;
import padl.kernel.IContainerComposition;
import padl.kernel.IDelegatingMethod;
import padl.kernel.IElement;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IInterface;
import padl.kernel.IMethod;
import padl.motif.IDesignMotifModel;
import padl.motif.detector.repository.ReflectiveDetector;
import padl.motif.models.StructuralMotifModel;
import util.io.ProxyConsole;
import util.multilingual.MultilingualManager;

/**
 * @author Hervé Albin-Amiot
 * @author Yann-Gaël Guéhéneuc
 */

// The comments in French are used in my Ph.D. thesis
// (through the lgrind package for LaTeX).

// %%D\'eclaration de la m\'eta-entit\'e \ygg@code{Composite} :%%
public class Composite extends StructuralMotifModel implements Cloneable,
		IDesignMotifModel {
	public static final char[] COMPONENT = "Component".toCharArray();
	public static final char[] COMPOSITE = "Composite".toCharArray();
	private static final char[] DEFAULT_ID = Composite.COMPOSITE;
	public static final char[] LEAF = "Leaf".toCharArray();
	private static final long serialVersionUID = 5458890750756439522L;

	//	public static void main(final String[] args)
	//			throws CloneNotSupportedException, ModelDeclarationException {
	//
	//		final IModelListener patternListener = new ModelStatistics();
	//		final Composite composite = new Composite(patternListener);
	//
	//		// I generate the Java source code associated with this padl.pattern.
	//		final JavaGenerator javaGenerator = new JavaGenerator();
	//		// I generate the constraints associated with this padl.pattern.
	//		final PtidejSolverCustomConstraintGenerator constraintGenerator =
	//			new PtidejSolverCustomConstraintGenerator();
	//		// I generate this padl.pattern as a domain for the constraints.
	//		final PtidejSolver2AC4DomainGenerator domainGenerator =
	//			new PtidejSolver2AC4DomainGenerator();
	//
	//		ProxyConsole.getInstance().normalOutput().println(composite);
	//		ProxyConsole.getInstance().normalOutput().println("----");
	//		composite.generate(javaGenerator);
	//		ProxyConsole.getInstance().normalOutput().println(javaGenerator.getCode());
	//		ProxyConsole.getInstance().normalOutput().println("----");
	//		composite.generate(constraintGenerator);
	//		ProxyConsole
	//			.getInstance()
	//			.normalOutput()
	//			.println(constraintGenerator.getCode());
	//		ProxyConsole.getInstance().normalOutput().println("----");
	//		composite.walk(domainGenerator);
	//		ProxyConsole
	//			.getInstance()
	//			.normalOutput()
	//			.println(domainGenerator.getResult());
	//		ProxyConsole.getInstance().normalOutput().println("----");
	//		javaGenerator.reset();
	//		composite.generate(javaGenerator);
	//		ProxyConsole.getInstance().normalOutput().println(javaGenerator.getCode());
	//		ProxyConsole.getInstance().normalOutput().println("----");
	//		ProxyConsole.getInstance().normalOutput().println(patternListener);
	//	}

	// %%D\'eclaration du leitmotiv du motif \ygg@pattern{Composite},%%
	// %%faite par le constructeur de la classe \ygg@code{Composite},%%
	// %%sous-classe de la classe \ygg@code{Pattern} :%%
	public Composite() {
		this(null);
	}

	public Composite(final IModelListener patternListener) {
		super(Composite.DEFAULT_ID);

		this.addModelListener(patternListener);

		// Interface Component.
		// %%D\'eclaration de l'interface \ygg@code{Component} :%%
		final IInterface iComponent =
			this.getFactory().createInterface(
				Composite.COMPONENT,
				Composite.COMPONENT);
		// %%D\'eclaration de la m\'ethode \ygg@code{operation()},%%
		// %%d\'efinie dans l'interface \ygg@code{Component} :%%
		final IMethod mOperation =
			this.getFactory().createMethod(
				"operation".toCharArray(),
				"operation".toCharArray());
		iComponent.addConstituent(mOperation);
		// %%Ajout de l'interface \ygg@code{Component} au mod\`ele :%%
		this.addConstituent(iComponent);

		// Composition children.
		// %%Motif de composition ayant pour cible \ygg@code{Component} et de
		// cardinalit\'e $> 1$ :%%
		final IContainerComposition aComposition =
			this.getFactory().createContainerCompositionRelationship(
				"children".toCharArray(),
				iComponent,
				Constants.CARDINALITY_MANY);

		// Class Composite.
		// %%D\'eclaration de la classe \ygg@code{Composite} :%%
		final IClass cComposite =
			this.getFactory().createClass(
				Composite.COMPOSITE,
				Composite.COMPOSITE);
		// %%La classe \ygg@code{Composite} a pour interface
		// \ygg@code{Component} :%%
		cComposite.addImplementedInterface(iComponent);
		// %%La classe \ygg@code{Composite} et l'interface \ygg@code{Component}
		// sont li\'ees%%
		// %%par le motif de composition \ygg@code{children} :%%
		cComposite.addConstituent(aComposition);
		// %%La m\'ethode \ygg@code{operation()} d\'efinie dans la classe
		// \ygg@code{Composite} impl\'emente%%
		// %%la m\'ethode \ygg@code{operation()} de l'interface
		// \ygg@code{Component} et est li\'ee via le%%
		// %%motif de composition \ygg@code{aComposition} \`a cette interface
		// :%%
		final IDelegatingMethod mDelegation =
			this.getFactory().createDelegatingMethod(
				"operation".toCharArray(),
				aComposition,
				mOperation);
		mDelegation.attachTo(mOperation);
		cComposite.addConstituent(mDelegation);
		// %%Ajout de la classe \ygg@code{Composite} au mod\`ele :%%
		this.addConstituent(cComposite);

		// Class Leaf.
		// %%Ajout d'une classe \ygg@code{Leaf} :%%
		this.addLeaf(new char[][] { Composite.LEAF });
	}

	// %%D\'eclaration des services propres au mod\`ele.%%
	// %%Exemple de la m\'ethode \ygg@code{addLeaf()} :%%
	public void addLeaf(final char[][] names) {
		final char[] leafName = names[0];

		// %%D\'eclaration de la classe \ygg@code{Leaf} :%%
		final IClass aClass = this.getFactory().createClass(leafName, leafName);
		// %%La classe \ygg@code{Leaf} a pour interface \ygg@code{Component} :%%
		aClass.addImplementedInterface((IInterface) this
			.getConstituentFromID(Composite.COMPONENT));
		// %%L'interface publique de la classe \ygg@code{Leaf} est g\'en\'er\'ee
		// automatiquement%%
		// %%(cr\'eation d'une m\'ethode \ygg@code{operation()}) :%%
		aClass.assumeAllInterfaces();
		// %%Ajout de la classe \ygg@code{Leaf} au mod\`ele :%%
		this.addConstituent(aClass);
	}
	public String getIntent() {
		return MultilingualManager.getString("INTENT", Composite.class);
	}

	public char[] getName() {
		return Composite.DEFAULT_ID;
	}

	public List identify(final IAbstractModel anAbstractModel) {
		final List solutions = new ArrayList();

		if (this.getDetector() == null) {
			ProxyConsole
				.getInstance()
				.errorOutput()
				.println(
					MultilingualManager.getString(
						"Err_INIT_ALMD",
						Composite.class));
			// No solutions.
			return solutions;
		}

		this.getDetector().setPattern(this);
		Map partialSolutions =
			((ReflectiveDetector) this.getDetector())
				.buildPartialSolution(anAbstractModel);
		if (partialSolutions.size() > 0) {
			partialSolutions =
				((ReflectiveDetector) this.getDetector()).applyCriterias(
					partialSolutions,
					ReflectiveDetector.AssociationsCriteria);
			if (partialSolutions.size() > 0) {
				solutions.add(partialSolutions);
			}
		}

		if (solutions.size() != 1) {
			return solutions;
		}

		final Map matched = (Map) solutions.get(0);
		solutions.clear();
		final Iterator iterator =
			((List) matched.get(Composite.COMPONENT)).iterator();
		while (iterator.hasNext()) {
			final IClass currentPInterface = (IClass) iterator.next();
			final List tmpVector = new ArrayList();
			final List tmpVector2 = new ArrayList();
			tmpVector2.add(currentPInterface);
			final Map currentSolution = new HashMap();
			currentSolution.put(Composite.COMPONENT, tmpVector2);

			Iterator iterator2 =
				((List) matched.get(Composite.DEFAULT_ID)).iterator();
			while (iterator2.hasNext()) {
				final IClass currentClass = (IClass) iterator2.next();
				final Iterator iterator3 =
					currentClass.getIteratorOnConstituents();

				while (iterator3.hasNext()) {
					final IElement pElement = (IElement) iterator3.next();
					if (pElement instanceof IAssociation) {
						if (((IAssociation) pElement).getTargetEntity() == currentPInterface) {
							tmpVector.add(currentClass);
						}
					}
				}
			}

			if (tmpVector.size() > 0) {
				currentSolution.put(Composite.DEFAULT_ID, tmpVector);
			}

			tmpVector.clear();
			iterator2 = ((List) matched.get(Composite.LEAF)).iterator();
			while (iterator2.hasNext()) {
				final IClass currentClass = (IClass) iterator2.next();
				final Iterator iteratorOnInheritedEntities =
					currentClass.getIteratorOnInheritedEntities();
				while (iteratorOnInheritedEntities.hasNext()) {
					final IFirstClassEntity inheritedEntity =
						(IFirstClassEntity) iteratorOnInheritedEntities.next();
					if (inheritedEntity.equals(currentPInterface)
							&& !((List) currentSolution
								.get(Composite.DEFAULT_ID))
								.contains(currentClass)) {

						tmpVector.add(currentClass);
					}
				}
			}
			if (tmpVector.size() > 0) {
				currentSolution.put(Composite.LEAF, tmpVector);
			}
			if (currentSolution.size() == this.getNumberOfConstituents()) {
				solutions.add(currentSolution);
			}
		}
		return solutions;
	}

	public void removeLeaf(final char[][] names) {
		// If there is more than two entites, then there is a Leaf and a
		// Composite
		// (at least)
		// This hypothesis is only valid when there is no means to add
		// Composites
		// or Components.

		final char[] leafName = names[0];

		if (this.getNumberOfConstituents() > 3) {
			this.removeConstituentFromID(leafName);
		}
	}
}
