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
package epi.model;

import java.util.Iterator;
import padl.kernel.IAbstractLevelModel;
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
import padl.kernel.IMemberEntity;
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
 * @since  2005/04/06
 */
public class MatrixBuilder implements IWalker {
	private IFirstClassEntity keyFirstHalf;
	private final Matrix matrix;
	// private IAbstractModel model;

	public MatrixBuilder(final int aNumberOfEntities) {
		this.matrix = new Matrix(aNumberOfEntities);
	}
	public void close(final IAbstractLevelModel anAbstractLevelModel) {
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
		return "Adjacency matrix builder (integers)";
	}
	public Object getResult() {
		return this.matrix;
	}
	private void handleEntity(final IFirstClassEntity anEntity) {
		if (!(anEntity instanceof IMemberEntity)) {
			this.matrix.incrementValue(this.keyFirstHalf, anEntity);
		}
	}
	//	private void handleType(final String aType) {
	//		if (this.model.getConstituentFromName(aType) != null) {
	//			this.matrix.incrementValue(
	//				this.keyFirstHalf,
	//				(IEntity) this.model.getConstituentFromName(aType));
	//		}
	//	}
	public void open(final IAbstractLevelModel anAbstractLevelModel) {
		// this.model = anAbstractLevelModel;
	}
	public void open(final IClass aClass) {
		this.keyFirstHalf = aClass;
		this.handleEntity(aClass);
		final Iterator iterator = aClass.getIteratorOnInheritingEntities();
		while (iterator.hasNext()) {
			this.handleEntity((IFirstClassEntity) iterator.next());
		}
	}
	public void open(final IConstructor aConstructor) {
	}
	public void open(final IDelegatingMethod aDelegatingMethod) {
		this.open((IMethod) aDelegatingMethod);
	}
	public void open(final IDesignMotifModel aPatternModel) {
		// this.model = aPatternModel;
	}
	public void open(final IGetter aGetter) {
	}
	public void open(final IGhost aGhost) {
		this.keyFirstHalf = aGhost;
		this.handleEntity(aGhost);
		final Iterator iterator = aGhost.getIteratorOnInheritingEntities();
		while (iterator.hasNext()) {
			this.handleEntity((IFirstClassEntity) iterator.next());
		}
	}
	public void open(final IInterface anInterface) {
		this.keyFirstHalf = anInterface;
		this.handleEntity(anInterface);
		final Iterator iterator = anInterface.getIteratorOnInheritingEntities();
		while (iterator.hasNext()) {
			this.handleEntity((IFirstClassEntity) iterator.next());
		}
	}
	public void open(final IMemberClass aMemberClass) {
	}
	public void open(final IMemberGhost aMemberGhost) {
	}
	public void open(final IMemberInterface aMemberInterface) {
	}
	public void open(final IMethod aMethod) {
		//this.handleType(aMethod.getReturnType());
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
		this.handleEntity(anAggregation.getTargetEntity());
	}
	public void visit(final IAssociation anAssociation) {
		this.handleEntity(anAssociation.getTargetEntity());
	}
	public void visit(final IComposition aComposition) {
		this.handleEntity(aComposition.getTargetEntity());
	}
	public void visit(final IContainerAggregation aContainerAggregation) {
		this.handleEntity(aContainerAggregation.getTargetEntity());
	}
	public void visit(final IContainerComposition aContainerComposition) {
		this.handleEntity(aContainerComposition.getTargetEntity());
	}
	public void visit(final ICreation aCreation) {
		this.handleEntity(aCreation.getTargetEntity());
	}
	public void visit(final IField aField) {
		// this.handleType(aField.getType());
	}
	public void visit(final IMethodInvocation aMethodInvocation) {
	}
	public void visit(final IParameter aParameter) {
		// this.handleType(aParameter.getType());
	}
	public void visit(final IPrimitiveEntity aPrimitiveEntity) {
		// Do nothing for uninteresting primitive types.
	}
	public void visit(final IUseRelationship aUse) {
		this.handleEntity(aUse.getTargetEntity());
	}
	public void close(final IAbstractModel anAbstractModel) {
		// Do nothing.
	}
	public void open(final IAbstractModel anAbstractModel) {
		// Do nothing.
	}
}
