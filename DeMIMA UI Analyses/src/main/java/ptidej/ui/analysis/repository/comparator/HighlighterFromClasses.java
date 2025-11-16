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
package ptidej.ui.analysis.repository.comparator;

import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.lang3.NotImplementedException;
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
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGetter;
import padl.kernel.IGhost;
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
import padl.visitor.IWalker;
import util.io.ProxyConsole;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/12/20
 */
public class HighlighterFromClasses implements IWalker {
	private IAbstractModel abstractModel;
	private final Properties differences;

	public HighlighterFromClasses(final Properties someDifferences) {
		this.differences = someDifferences;
		Manager.getUniqueInstance().reset();
	}

	public void close(final IAbstractModel anAbstractModel) {
		final Iterator iterator = this.differences.values().iterator();
		while (iterator.hasNext()) {
			final String entityID = (String) iterator.next();
			if (!anAbstractModel.doesContainConstituentWithName(entityID
				.toCharArray())) {

				// Yann 2004/12/23: Comprehension!
				// The entity named "entityName" does not exist in
				// the current model but exists in the other (from
				// which the differences have been computed). So,
				// I add a new entity to the current model to reflect
				// that "entityName" does exist in the other model.
				Manager.getUniqueInstance().handleAddedEntity(
					this.abstractModel,
					entityID);
			}
		}
	}
	public void close(final IClass aClass) {
	}
	public void close(final IConstructor aConstructor) {
	}
	public void close(final IDelegatingMethod aDelegatingMethod) {
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
		return "Highligther from classes";
	}
	public Object getResult() {
		return this.abstractModel;
	}
	public void open(final IAbstractModel anAbstractModel) {
		this.abstractModel = anAbstractModel;
	}
	public void open(final IClass aClass) {
		this.open((IFirstClassEntity) aClass);
	}
	public void open(final IConstructor aConstructor) {
	}
	public void open(final IDelegatingMethod aDelegatingMethod) {
	}
	private void open(final IFirstClassEntity anEntity) {
		final String entityName = anEntity.getDisplayName();
		if (this.differences.containsValue(entityName)) {
			final Iterator iterator = this.differences.keySet().iterator();
			while (iterator.hasNext()) {
				final String key = (String) iterator.next();
				if (this.differences.get(key).equals(entityName)
						&& key.indexOf("Removed") > 0) {

					// Yann 2004/12/23: Comprehension!
					// The entity "anEntity" exist in the current model
					// but not in the other (from which the differences
					// have been computed). So, "anEntity" has been
					// "removed" from the other model.
					Manager.getUniqueInstance().handleRemovedEntity(
						this.abstractModel,
						anEntity);
				}
			}
		}
	}
	public void open(final IGetter aGetter) {
	}
	public void open(final IGhost aGhost) {
		this.open((IFirstClassEntity) aGhost);
	}
	public void open(final IInterface anInterface) {
		this.open((IFirstClassEntity) anInterface);
	}
	public void open(final IMemberClass aMemberClass) {
	}
	public void open(final IMemberGhost aMemberGhost) {
	}
	public void open(final IMemberInterface aMemberInterface) {
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

	public void traverse(Iterator iterator) {
		throw new NotImplementedException();
	}
}
