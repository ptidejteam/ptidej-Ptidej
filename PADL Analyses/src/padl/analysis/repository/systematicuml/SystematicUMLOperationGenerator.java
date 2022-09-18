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
package padl.analysis.repository.systematicuml;

import java.util.Arrays;
import java.util.Iterator;
import padl.analysis.repository.SystematicUMLAnalysis;
import padl.kernel.IAbstractModel;
import padl.kernel.IAggregation;
import padl.kernel.IAssociation;
import padl.kernel.IClass;
import padl.kernel.IComposition;
import padl.kernel.IConstituent;
import padl.kernel.IConstructor;
import padl.kernel.IContainerAggregation;
import padl.kernel.IContainerComposition;
import padl.kernel.ICreation;
import padl.kernel.IDelegatingMethod;
import padl.kernel.IFactory;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGetter;
import padl.kernel.IGhost;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.IInterface;
import padl.kernel.IMemberClass;
import padl.kernel.IMemberGhost;
import padl.kernel.IMemberInterface;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.IPackage;
import padl.kernel.IPackageDefault;
import padl.kernel.IParameter;
import padl.kernel.IPrimitiveEntity;
import padl.kernel.ISetter;
import padl.kernel.IUseRelationship;
import padl.motif.IDesignMotifModel;
import padl.visitor.IWalker;
import util.io.ProxyConsole;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/05/21
 */
public class SystematicUMLOperationGenerator implements IWalker {
	private IFirstClassEntity currentEntity;
	private final IIdiomLevelModel currentIdiomLevelModel;
	private final SystematicUMLStatistics statistics;

	public SystematicUMLOperationGenerator(
		final IFactory aFactory,
		final IIdiomLevelModel anIdiomLevelModel,
		final SystematicUMLStatistics aStatistics) {

		this.currentIdiomLevelModel = anIdiomLevelModel;
		this.statistics = aStatistics;
	}

	public void close(final IAbstractModel anAbstractModel) {
	}
	public void close(final IClass aClass) {
	}
	public void close(final IConstructor aConstructor) {
	}
	public void close(final IDelegatingMethod aDelegatingMethod) {
	}
	public void close(final IDesignMotifModel aPatternModel) {
	}
	public void close(final IGetter aGetter) {
	}
	public void close(final IGhost aGhost) {
	}
	public void close(final IInterface anInterface) {
	}
	public void close(final IMemberClass aMemberClass) {
	}
	public void close(final IMemberGhost aMemberGhost) {
	}
	public void close(final IMemberInterface aMemberInterface) {
	}
	public void close(final IMethod aMethod) {
	}
	public void close(final IPackage aPackage) {
	}
	public void close(final IPackageDefault aPackage) {
	}
	public void close(final ISetter aSetter) {
	}

	public String getName() {
		return "SystematicUMLAnalisysOperationGenerator";
	}
	public Object getResult() {
		return this.currentIdiomLevelModel;
	}
	private void heuristic1(final IMethod aMethod) {
		// Heuristic 1: Any abstract method defines an operation
		// of the class. This is actually not true, because abstract
		// methods might be defined to implement the Template Method
		// design pattern. Thus, it is necessary to check that the
		// abstract method is not called from within another method
		// of the same declaring class.
		if (aMethod.isAbstract()) {
			boolean isTemplateMethod = false;
			final Iterator iteratorOnMethods =
				this.currentEntity.getIteratorOnConstituents(IMethod.class);
			while (iteratorOnMethods.hasNext() && !isTemplateMethod) {
				final IMethod method = (IMethod) iteratorOnMethods.next();
				final Iterator interatorOnMethodInvocations =
					method.getIteratorOnConstituents(IMethodInvocation.class);

				while (interatorOnMethodInvocations.hasNext()
						&& !isTemplateMethod) {

					final IMethodInvocation methodInvocation =
						(IMethodInvocation) interatorOnMethodInvocations.next();
					if (methodInvocation.getCalledMethod() != null
							&& methodInvocation.getCalledMethod().equals(
								aMethod)) {

						isTemplateMethod = true;
					}
				}
			}
			if (isTemplateMethod) {
				aMethod.setDisplayName(SystematicUMLAnalysis.TEMPLATE_METHOD
						+ aMethod.getDisplayName());
				this.statistics.operationRecognized(aMethod);
			}
			else {
				aMethod.setDisplayName(SystematicUMLAnalysis.OPERATION
						+ aMethod.getDisplayName());
				this.statistics.operationRecognized(aMethod);
			}
		}
	}
	private void heuristic2(final IMethod aMethod) {
		// Heuristic 2: Overloaded methods declared in a class
		// (including its super-class) define a service on a
		// set of classes (the types of their parameters).
		if (this.isMethodOverLoaded(aMethod, this.currentEntity)) {
			this.setMethodAsOperation(aMethod.getName(), this.currentEntity);
		}
	}
	private boolean isMethodOverLoaded(
		final IMethod aMethod,
		final IFirstClassEntity anEntity) {

		boolean isOverLoaded = false;
		final Iterator iteratorOnMethods =
			anEntity.getIteratorOnConstituents(IMethod.class);
		while (iteratorOnMethods.hasNext() && !isOverLoaded) {
			final IMethod method = (IMethod) iteratorOnMethods.next();

			// The methods must be different, their names equal.
			if (!method.equals(aMethod)
					&& Arrays.equals(method.getName(), aMethod.getName())) {

				// The lists of parameters must be different.
				final StringBuffer listOfParameters1 = new StringBuffer();
				Iterator iteratorOnParameters =
					method.getIteratorOnConstituents(IParameter.class);
				while (iteratorOnParameters.hasNext()) {
					listOfParameters1.append(((IParameter) iteratorOnParameters
						.next()).getTypeName());
				}

				final StringBuffer listOfParameters2 = new StringBuffer();
				iteratorOnParameters =
					aMethod.getIteratorOnConstituents(IParameter.class);
				while (iteratorOnParameters.hasNext()) {
					listOfParameters2.append(((IParameter) iteratorOnParameters
						.next()).getTypeName());
				}

				if (!listOfParameters1.equals(listOfParameters2)) {
					isOverLoaded = true;
				}
			}
		}

		final Iterator iteratorOnSuperEntities =
			anEntity.getIteratorOnInheritedEntities();
		while (iteratorOnSuperEntities.hasNext() && !isOverLoaded) {
			isOverLoaded =
				this.isMethodOverLoaded(
					aMethod,
					(IFirstClassEntity) iteratorOnSuperEntities.next());
		}

		return isOverLoaded;
	}
	public void open(final IAbstractModel anAbstractModel) {
	}
	public void open(final IClass aClass) {
		this.open((IFirstClassEntity) aClass);
	}
	public void open(final IConstructor aConstructor) {
	}
	public void open(final IDelegatingMethod aDelegatingMethod) {
		this.open((IMethod) aDelegatingMethod);
	}
	public void open(final IDesignMotifModel aPatternModel) {
	}
	private void open(final IFirstClassEntity anEntity) {
		this.currentEntity =
			(IFirstClassEntity) this.currentIdiomLevelModel
				.getConstituentFromID(anEntity.getID());
	}
	public void open(final IGetter aGetter) {
		this.open((IMethod) aGetter);
	}
	public void open(final IGhost aGhost) {
		this.open((IFirstClassEntity) aGhost);
	}
	public void open(final IInterface anInterface) {
		this.open((IFirstClassEntity) anInterface);
	}
	public void open(final IMemberClass aMemberClass) {
		this.open((IFirstClassEntity) aMemberClass);
	}
	public void open(final IMemberGhost aMemberGhost) {
		this.open((IFirstClassEntity) aMemberGhost);
	}
	public void open(final IMemberInterface aMemberInterface) {
		this.open((IFirstClassEntity) aMemberInterface);
	}
	public void open(final IMethod aMethod) {
		// Yann 2004/05/21: Heuristics.
		// These are heuristics to infer operations based on the
		// assumption that the implementation is not 100% clean
		// (i.e., developers did not use visibilities by the book).
		this.heuristic1(aMethod);
		this.heuristic2(aMethod);
	}
	public void open(final IPackage aPackage) {
	}
	public void open(final IPackageDefault aPackage) {
	}
	public void open(final ISetter aSetter) {
		this.open((IMethod) aSetter);
	}
	public void reset() {
	}
	private void setMethodAsOperation(
		final char[] aName,
		final IFirstClassEntity anEntity) {
		final Iterator iteratorOnMethods =
			anEntity.getIteratorOnConstituents(IMethod.class);
		while (iteratorOnMethods.hasNext()) {
			final IMethod method = (IMethod) iteratorOnMethods.next();

			// The methods must be different, their names equal.
			if (Arrays.equals(method.getName(), aName)) {
				method.setDisplayName(SystematicUMLAnalysis.OPERATION
						+ method.getDisplayName());
				this.statistics.operationRecognized(method);
			}
		}

		final Iterator iteratorOnSuperEntities =
			anEntity.getIteratorOnInheritedEntities();
		while (iteratorOnSuperEntities.hasNext()) {
			this.setMethodAsOperation(
				aName,
				(IFirstClassEntity) iteratorOnSuperEntities.next());
		}
	}
	public final void unknownConstituentHandler(
		final String aCalledMethodName,
		final IConstituent aConstituent) {

		ProxyConsole
			.getInstance()
			.debugOutput()
			.print(this.getClass().getName());
		ProxyConsole
			.getInstance()
			.debugOutput()
			.print(" does not know what to do for \"");
		ProxyConsole.getInstance().debugOutput().print(aCalledMethodName);
		ProxyConsole.getInstance().debugOutput().print("\" (");
		ProxyConsole
			.getInstance()
			.debugOutput()
			.print(aConstituent.getDisplayID());
		ProxyConsole.getInstance().debugOutput().println(')');
	}
	public void visit(final IAggregation anAggregation) {
	}
	public void visit(final IAssociation anAssociation) {
	}
	public void visit(final IComposition aComposition) {
	}
	public void visit(final IContainerAggregation aContainerAggregation) {
	}
	public void visit(final IContainerComposition aContainerComposition) {
	}
	public void visit(final ICreation aCreation) {
	}
	public void visit(final IField aField) {
	}
	public void visit(final IMethodInvocation aMethodInvocation) {
	}
	public void visit(final IParameter aParameter) {
	}
	public void visit(final IPrimitiveEntity aPrimitiveEntity) {
		// Do nothing for uninteresting primitive types.
	}
	public void visit(final IUseRelationship aUse) {
	}
}
