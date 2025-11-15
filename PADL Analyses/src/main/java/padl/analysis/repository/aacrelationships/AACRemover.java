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
package padl.analysis.repository.aacrelationships;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

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
import padl.motif.IDesignMotifModel;
import padl.visitor.IWalker;
import util.io.ProxyConsole;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2005/08/04
 */
public final class AACRemover implements IWalker {
	private IAbstractModel abstractModel;
	private final List messageCache;
	// Yann 2006/02/24: Member entities!
	// I must stack lists of relations to remove because,
	// while visiting an entity, I amy enter another 
	// (member) entity.
	private Stack stackOfListOfRelationsToRemove = new Stack();

	public AACRemover() {
		this.messageCache = new ArrayList();
	}
	public void close(final IAbstractModel anAbstractModel) {
	}
	public void close(final IClass aClass) {
		this.close((IFirstClassEntity) aClass);
	}
	public void close(final IConstructor aConstructor) {
	}
	public void close(final IDelegatingMethod aDelegatingMethod) {
	}
	public void close(final IDesignMotifModel aPatternModel) {
	}
	private void close(final IFirstClassEntity anEntity) {
		final List listOfRelationsToRemove =
			(List) this.stackOfListOfRelationsToRemove.pop();

		final Iterator iterator = listOfRelationsToRemove.iterator();
		while (iterator.hasNext()) {
			final char[] elementID = (char[]) iterator.next();
			// TODO: Hack!
			if (anEntity.doesContainConstituentWithID(elementID)) {
				anEntity.removeConstituentFromID(elementID);
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
		this.close((IFirstClassEntity) aMemberClass);
	}
	public void close(final IMemberGhost aMemberGhost) {
		this.close((IFirstClassEntity) aMemberGhost);
	}
	public void close(final IMemberInterface aMemberInterface) {
		this.close((IFirstClassEntity) aMemberInterface);
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
		return "AAC Remover";
	}
	public Object getResult() {
		return this.abstractModel;
	}
	public void open(final IAbstractModel anAbstractModel) {
		this.abstractModel = anAbstractModel;
	}
	public void open(final IClass aClass) {
		this.stackOfListOfRelationsToRemove.push(new ArrayList());
	}
	public void open(final IConstructor aConstructor) {
	}
	public void open(final IDelegatingMethod aDelegatingMethod) {
	}
	public void open(final IDesignMotifModel aPatternModel) {
	}
	public void open(final IGetter aGetter) {
	}
	public void open(final IGhost aGhost) {
		this.stackOfListOfRelationsToRemove.push(new ArrayList());
	}
	public void open(final IInterface anInterface) {
		this.stackOfListOfRelationsToRemove.push(new ArrayList());
	}
	public void open(final IMemberClass aMemberClass) {
		this.stackOfListOfRelationsToRemove.push(new ArrayList());
	}
	public void open(final IMemberGhost aMemberGhost) {
		this.stackOfListOfRelationsToRemove.push(new ArrayList());
	}
	public void open(final IMemberInterface aMemberInterface) {
		this.stackOfListOfRelationsToRemove.push(new ArrayList());
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

		// Yann 2014/06/18: Don't repeat yourself!
		// I now store the message and only print new message...
		final String key = aCalledMethodName;
		if (!this.messageCache.contains(key)) {
			this.messageCache.add(key);

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
	}
	public void visit(final IAggregation anAggregation) {
		((List) this.stackOfListOfRelationsToRemove.peek()).add(anAggregation
			.getID());
	}
	public void visit(final IAssociation anAssociation) {
		((List) this.stackOfListOfRelationsToRemove.peek()).add(anAssociation
			.getID());
	}
	public void visit(final IComposition aComposition) {
		((List) this.stackOfListOfRelationsToRemove.peek()).add(aComposition
			.getID());
	}
	public void visit(final IContainerAggregation aContainerAggregation) {
		((List) this.stackOfListOfRelationsToRemove.peek())
			.add(aContainerAggregation.getID());
	}
	public void visit(final IContainerComposition aContainerComposition) {
		((List) this.stackOfListOfRelationsToRemove.peek())
			.add(aContainerComposition.getID());
	}
	public void visit(final ICreation aCreation) {
		((List) this.stackOfListOfRelationsToRemove.peek()).add(aCreation
			.getID());
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
		((List) this.stackOfListOfRelationsToRemove.peek()).add(aUse.getID());
	}

	public void traverse(Iterator iterator) {
		throw new NotImplementedException();
	}
}
