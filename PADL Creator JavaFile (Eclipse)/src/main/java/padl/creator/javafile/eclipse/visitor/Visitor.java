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
package padl.creator.javafile.eclipse.visitor;

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

import java.util.Iterator;

public class Visitor implements IWalker {

	@Override
	public void close(final IAbstractModel anAbstractModel) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(final IClass aClass) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(final IConstructor aConstructor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(final IDelegatingMethod aDelegatingMethod) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(final IGetter aGetter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(final IGhost aGhost) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(final IInterface anInterface) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(final IMemberClass aMemberClass) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(final IMemberGhost aMemberGhost) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(final IMemberInterface aMemberInterface) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(final IMethod aMethod) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(final IPackage aPackage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(final IPackageDefault aPackage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(final ISetter aSetter) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void open(final IAbstractModel anAbstractModel) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(final IClass aClass) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(final IConstructor aConstructor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(final IDelegatingMethod aDelegatingMethod) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(final IGetter aGetter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(final IGhost aGhost) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(final IInterface anInterface) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(final IMemberClass aMemberClass) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(final IMemberGhost aMemberGhost) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(final IMemberInterface aMemberInterface) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(final IMethod aMethod) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(final IPackage aPackage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(final IPackageDefault aPackage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(final ISetter aSetter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
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

	@Override
	public void visit(final IAggregation anAggregation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(final IAssociation anAssociation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(final IComposition aComposition) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(final IContainerAggregation aContainerAggregation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(final IContainerComposition aContainerComposition) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(final ICreation aCreation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(final IField aField) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(final IMethodInvocation aMethodInvocation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(final IParameter aParameter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(final IPrimitiveEntity aPrimitiveEntity) {
		// Do nothing for uninteresting primitive types.
	}

	@Override
	public void visit(final IUseRelationship aUse) {
		// TODO Auto-generated method stub

	}

	@Override
	public void traverse(Iterator iterator) {
		throw new NotImplementedException();
	}
}
