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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
public class SystematicUMLDataTypeCleaner implements IWalker {
	private final IIdiomLevelModel newIdiomLevelModel;
	private final SystematicUMLStatistics statistics;

	public SystematicUMLDataTypeCleaner(
		final IFactory aFactory,
		final IIdiomLevelModel anIdiomLevelModel,
		final SystematicUMLStatistics aStatistics) {

		this.newIdiomLevelModel = anIdiomLevelModel;
		this.statistics = aStatistics;
	}

	private boolean areAllSuperEntitiesDataTypes(
		final IFirstClassEntity anEntity) {
		if (anEntity instanceof IGhost) {
			return true;
		}
		if (anEntity.getDisplayName().indexOf("datatype") == -1) {
			return false;
		}

		final List superEntities = new ArrayList();
		Iterator iterator = anEntity.getIteratorOnInheritedEntities();
		while (iterator.hasNext()) {
			final IFirstClassEntity firstClassEntity =
				(IFirstClassEntity) iterator.next();
			superEntities.add(firstClassEntity);
		}
		if (anEntity instanceof IClass) {
			iterator = ((IClass) anEntity).getIteratorOnImplementedInterfaces();
			while (iterator.hasNext()) {
				final IFirstClassEntity firstClassEntity =
					(IFirstClassEntity) iterator.next();
				superEntities.add(firstClassEntity);
			}
		}

		iterator = superEntities.iterator();
		while (iterator.hasNext()) {
			final IFirstClassEntity firstClassEntity =
				(IFirstClassEntity) iterator.next();
			if (!this.areAllSuperEntitiesDataTypes(firstClassEntity)) {
				return false;
			}
		}

		return true;
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
		return "SystematicUMLAnalisysDataTypeCleaner";
	}
	public Object getResult() {
		return this.newIdiomLevelModel;
	}
	public void open(final IAbstractModel anAbstractModel) {
	}
	public void open(final IClass aClass) {
		this.open((IFirstClassEntity) aClass);
	}
	public void open(final IConstructor aConstructor) {
	}
	public void open(final IDelegatingMethod aDelegatingMethod) {
	}
	public void open(final IDesignMotifModel aPatternModel) {
	}
	private void open(final IFirstClassEntity anEntity) {
		// If an entity is tagged as a datatype, then I check that
		// all its superentities are also tagged as datatypes, else
		// I untag this entity (because it does not make sens to tag,
		// an entity as datatype if it inherits everyting from its
		// superentities).
		if (anEntity.getDisplayName().indexOf("datatype") > 0) {
			if (!this.areAllSuperEntitiesDataTypes(anEntity)) {
				// If a entity is not abstract (has no abstract method,
				// i.e., does not define operations), then this entity
				// is an implementation class.
				if (!anEntity.isAbstract()) {
					final StringBuffer displayName = new StringBuffer();
					displayName.append("<<implementation>>\n");
					displayName.append(anEntity.getDisplayName());
					anEntity.setDisplayName(displayName.toString());

					this.statistics.dataTypeUnRecognized(anEntity);
					this.statistics.implementationClassRecognized(anEntity);
				}
			}
		}
	}
	public void open(final IGetter aGetter) {
	}
	public void open(final IGhost aGhost) {
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
	}
	public void open(final IPackage aPackage) {
	}
	public void open(final IPackageDefault aPackage) {
	}
	public void open(final ISetter aSetter) {
	}
	public void reset() {
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
