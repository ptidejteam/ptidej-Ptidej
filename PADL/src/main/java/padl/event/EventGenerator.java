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
package padl.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import padl.kernel.IAbstractModel;
import padl.kernel.IAggregation;
import padl.kernel.IAssociation;
import padl.kernel.IClass;
import padl.kernel.IComposition;
import padl.kernel.IConstituent;
import padl.kernel.IConstituentOfEntity;
import padl.kernel.IConstituentOfModel;
import padl.kernel.IConstituentOfOperation;
import padl.kernel.IConstructor;
import padl.kernel.IContainer;
import padl.kernel.IContainerAggregation;
import padl.kernel.IContainerComposition;
import padl.kernel.ICreation;
import padl.kernel.IDelegatingMethod;
import padl.kernel.IField;
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

public class EventGenerator implements IWalker, Serializable {
	private static final long serialVersionUID = -7086126993797987777L;
	private static EventGenerator UniqueInstance;
	public static EventGenerator getInstance() {
		if (EventGenerator.UniqueInstance == null) {
			EventGenerator.UniqueInstance = new EventGenerator();
		}
		return EventGenerator.UniqueInstance;
	}

	private IAbstractModel abstractModel;
	private final List messageCache;
	private final Stack stackOfEnclosingEntities;

	protected EventGenerator() {
		this.messageCache = new ArrayList();
		this.stackOfEnclosingEntities = new Stack();
	}
	public void close(final IAbstractModel anAbstractModel) {
		this.close((IContainer) anAbstractModel);
	}
	public final void close(final IClass aClass) {
		this.close((IContainer) aClass);
	}
	public final void close(final IConstructor aConstructor) {
	}
	protected final void close(final IContainer aContainer) {
		this.stackOfEnclosingEntities.pop();
	}
	public final void close(final IDelegatingMethod aDelegatingMethod) {
	}
	public final void close(final IGetter aGetter) {
	}
	public final void close(final IGhost aGhost) {
		this.close((IContainer) aGhost);
	}
	public final void close(final IInterface anInterface) {
		this.close((IContainer) anInterface);
	}
	public final void close(final IMemberClass aMemberClass) {
		this.close((IContainer) aMemberClass);
	}
	public final void close(final IMemberGhost aMemberGhost) {
		this.close((IContainer) aMemberGhost);
	}
	public final void close(final IMemberInterface aMemberInterface) {
		this.close((IContainer) aMemberInterface);
	}
	public final void close(final IMethod aMethod) {
	}
	public final void close(final IPackage aPackage) {
		this.close((IContainer) aPackage);
	}
	public final void close(final IPackageDefault aPackage) {
		this.close((IContainer) aPackage);
	}
	public final void close(final ISetter aSetter) {
	}
	protected final void genetateEventForElement(final IConstituentOfEntity anElement) {
		this.abstractModel.fireModelChange(
			IModelListener.ELEMENT_ADDED,
			new ElementEvent(this.getCurrentEnclosingEntity(), anElement));
	}
	protected final void genetateEventForElement(
		final IConstituentOfOperation anInvoke) {

		this.abstractModel.fireModelChange(
			IModelListener.INVOKE_ADDED,
			new InvokeEvent(this.getCurrentEnclosingEntity(), anInvoke));
	}
	protected final void genetateEventForEntity(final IConstituentOfModel anEntity) {
		this.abstractModel.fireModelChange(
			IModelListener.ENTITY_ADDED,
			new EntityEvent(this.getCurrentEnclosingEntity(), anEntity));
	}
	protected final IContainer getCurrentEnclosingEntity() {
		return (IContainer) this.stackOfEnclosingEntities.peek();
	}
	public String getName() {
		return "Event Generator";
	}
	public Object getResult() {
		return null;
	}
	public void open(final IAbstractModel anAbstractModel) {
		this.abstractModel = anAbstractModel;
		this.open((IContainer) anAbstractModel);
	}
	public final void open(final IClass aClass) {
		this.genetateEventForEntity(aClass);
		this.open((IContainer) aClass);
	}
	public final void open(final IConstructor aConstructor) {
		this.genetateEventForElement(aConstructor);
	}
	protected final void open(final IContainer aContainer) {
		this.stackOfEnclosingEntities.push(aContainer);
	}
	public final void open(final IDelegatingMethod aDelegatingMethod) {
		this.genetateEventForElement(aDelegatingMethod);
	}
	public final void open(final IGetter aGetter) {
		this.genetateEventForElement(aGetter);
	}
	public final void open(final IGhost aGhost) {
		this.genetateEventForEntity(aGhost);
		this.open((IContainer) aGhost);
	}
	public final void open(final IInterface anInterface) {
		this.genetateEventForEntity(anInterface);
		this.open((IContainer) anInterface);
	}
	public final void open(final IMemberClass aMemberClass) {
		this.genetateEventForEntity(aMemberClass);
		this.open((IContainer) aMemberClass);
	}
	public final void open(final IMemberGhost aMemberGhost) {
		this.genetateEventForEntity(aMemberGhost);
		this.open((IContainer) aMemberGhost);
	}
	public final void open(final IMemberInterface aMemberInterface) {
		this.genetateEventForEntity(aMemberInterface);
		this.open((IContainer) aMemberInterface);
	}
	public final void open(final IMethod aMethod) {
		this.genetateEventForElement(aMethod);
	}
	public final void open(final IPackage aPackage) {
		this.open((IContainer) aPackage);
	}
	public final void open(final IPackageDefault aPackage) {
		this.open((IContainer) aPackage);
	}
	public final void open(final ISetter aSetter) {
		this.genetateEventForElement(aSetter);
	}
	public final void reset() {
		this.stackOfEnclosingEntities.clear();
	}
	public final void unknownConstituentHandler(
		final String aCalledMethodName,
		final IConstituent aConstituent) {

		final String constituentClassName = this.getClass().getName();

		// Yann 2014/06/18: Don't repeat yourself!
		// I now store the message and only print new message...
		final StringBuffer buffer = new StringBuffer();
		buffer.append(aCalledMethodName);
		buffer.append(constituentClassName);
		final String key = buffer.toString();
		if (!this.messageCache.contains(key)) {
			this.messageCache.add(key);

			ProxyConsole
				.getInstance()
				.debugOutput()
				.print(constituentClassName);
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
	public final void visit(final IAggregation anAggregation) {
		this.genetateEventForElement(anAggregation);
	}
	public final void visit(final IAssociation anAssociation) {
		this.genetateEventForElement(anAssociation);
	}
	public final void visit(final IComposition aComposition) {
		this.genetateEventForElement(aComposition);
	}
	public final void visit(final IContainerAggregation aContainerAggregation) {
		this.genetateEventForElement(aContainerAggregation);
	}
	public final void visit(final IContainerComposition aContainerComposition) {
		this.genetateEventForElement(aContainerComposition);
	}
	public final void visit(final ICreation aCreation) {
		this.genetateEventForElement(aCreation);
	}
	public final void visit(final IField aField) {
		this.genetateEventForElement(aField);
	}
	public final void visit(final IMethodInvocation aMethodInvocation) {
		this.genetateEventForElement(aMethodInvocation);
	}
	public final void visit(final IParameter aParameter) {
		// Without the cast, the method is ambiguous
		// TODO fix the ambiguity without a cast! 
		this.genetateEventForElement((IConstituentOfOperation) aParameter);
	}
	public void visit(final IPrimitiveEntity aPrimitiveEntity) {
		this.genetateEventForEntity(aPrimitiveEntity);
	}
	public final void visit(final IUseRelationship aUse) {
		this.genetateEventForElement(aUse);
	}
}
