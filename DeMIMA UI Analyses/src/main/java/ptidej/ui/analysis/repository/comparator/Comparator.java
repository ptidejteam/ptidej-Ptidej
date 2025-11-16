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
import padl.kernel.IElement;
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
 * @since  2004/12/12
 */
public final class Comparator implements IWalker {
	private IAbstractModel anAbstractModel;
	private IFirstClassEntity anEntity;
	private IAbstractModel anotherAbstractModel;
	private IFirstClassEntity anotherEntity;

	public Comparator(final IAbstractModel anAbstractModel) {
		this.anotherAbstractModel = anAbstractModel;
		Manager.getUniqueInstance().reset();
	}
	public void close(final IAbstractModel anAbstractModel) {
		// Now that I have compared existing constituents with
		// constituents from the other model, I check if the
		// other model does not define new consituents.
		final Iterator iterator =
			this.anotherAbstractModel.getIteratorOnTopLevelEntities();
		while (iterator.hasNext()) {
			// Yann 2005/10/07: Packages!
			// A model may now contrain entities and packages.
			// Yann 2005/10/12: Iterator!
			// I have now an iterator able to iterate over a
			// specified type of constituent of a list.
			final IFirstClassEntity firstClassEntity =
				(IFirstClassEntity) iterator.next();
			if (this.anAbstractModel.getTopLevelEntityFromID(firstClassEntity
				.getID()) == null) {

				Manager.getUniqueInstance().handleAddedEntity(
					this.anAbstractModel,
					firstClassEntity);
			}
		}
	}
	public void close(final IClass aClass) {
		this.close((IFirstClassEntity) aClass);
	}
	public void close(final IConstructor aConstructor) {
	}
	public void close(final IDelegatingMethod aDelegatingMethod) {
	}
	private void close(final IFirstClassEntity anEntity) {
		if (this.anotherEntity != null) {
			// Now that I have compared existing constituents with
			// constituents from the other model, I check if the
			// other model does not define new consituents.
			final Iterator iterator =
				this.anotherEntity.getIteratorOnConstituents();
			while (iterator.hasNext()) {
				final IElement element = (IElement) iterator.next();
				// Yann 2004/12/19: Name and ID.
				// I cannot use the actor ID because this might vary
				// across idiom-level model. I must look for a way
				// to compute a unique actor ID!
				if (this.anEntity.getConstituentFromName(element.getName()) == null) {

					Manager.getUniqueInstance().handleRemovedElement(
						this.anAbstractModel,
						this.anEntity,
						element);
				}
			}
		}
	}
	public void close(final IGetter aGetter) {
	}
	public void close(final IGhost aGhost) {
		this.close((IFirstClassEntity) aGhost);
	}
	public void close(final IInterface anInterface) {
		this.close((IFirstClassEntity) anInterface);
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
		return "Comparator";
	}
	public Object getResult() {
		return this.anAbstractModel;
	}
	public void open(final IAbstractModel anAbstractModel) {
		this.anAbstractModel = anAbstractModel;
	}
	public void open(final IClass aClass) {
		this.open((IFirstClassEntity) aClass);
	}
	public void open(final IConstructor aConstructor) {
		this.visit((IElement) aConstructor);
	}
	public void open(final IDelegatingMethod aDelegatingMethod) {
		this.visit((IElement) aDelegatingMethod);
	}
	private void open(final IFirstClassEntity anEntity) {
		this.anEntity = anEntity;
		this.anotherEntity =
			(IFirstClassEntity) this.anotherAbstractModel
				.getTopLevelEntityFromID(anEntity.getID());

		if (this.anotherEntity != null) {
			if (!anEntity.getClass().isInstance(this.anotherEntity)) {
				anEntity.setDisplayName("<<modified type>> "
						+ anEntity.getDisplayName());
			}
		}
		else {
			Manager.getUniqueInstance().handleAddedEntity(
				this.anAbstractModel,
				anEntity);
		}
	}
	public void open(final IGetter aGetter) {
		this.visit((IElement) aGetter);
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
		this.visit((IElement) aMethod);
	}
	public void open(final IPackage aPackage) {
	}
	public void open(final IPackageDefault aPackage) {
	}
	public void open(final ISetter aSetter) {
		this.visit((IElement) aSetter);
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
		this.visit((IElement) anAggregation);
	}
	public void visit(final IAssociation anAssociation) {
		this.visit((IElement) anAssociation);
	}
	public void visit(final IComposition aComposition) {
		this.visit((IElement) aComposition);
	}
	public void visit(final IContainerAggregation aContainerAggregation) {
		this.visit((IElement) aContainerAggregation);
	}
	public void visit(final IContainerComposition aContainerComposition) {
		this.visit((IElement) aContainerComposition);
	}
	public void visit(final ICreation aCreation) {
		this.visit((IElement) aCreation);
	}
	private void visit(final IElement anElement) {
		if (this.anotherEntity != null) {
			final IElement anotherElement =
				(IElement) this.anotherEntity.getConstituentFromName(anElement
					.getName());

			if (anotherElement != null) {
				if (!anElement.getClass().isInstance(anotherElement)) {
					anElement.setDisplayName("<<modified type>> "
							+ anElement.getDisplayName());
				}
			}
			else {
				Manager.getUniqueInstance().handleAddedElement(
					this.anAbstractModel,
					this.anEntity,
					anElement);
			}
		}
	}
	public void visit(final IField aField) {
		this.visit((IElement) aField);
	}
	public void visit(final IMethodInvocation aMethodInvocation) {
		this.visit((IElement) aMethodInvocation);
	}
	public void visit(final IParameter aParameter) {
		this.visit((IElement) aParameter);
	}
	public void visit(final IPrimitiveEntity aPrimitiveEntity) {
		// Do nothing for uninteresting primitive types.
	}
	public void visit(final IUseRelationship aUse) {
		this.visit((IElement) aUse);
	}

	public void traverse(Iterator iterator) {
		throw new NotImplementedException();
	}
}
