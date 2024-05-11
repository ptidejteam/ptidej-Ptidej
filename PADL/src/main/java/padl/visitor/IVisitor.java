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
package padl.visitor;

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

public interface IVisitor {
	void close(final IAbstractModel anAbstractModel);
	void close(final IClass aClass);
	void close(final IConstructor aConstructor);
	void close(final IDelegatingMethod aDelegatingMethod);
	void close(final IGetter aGetter);
	void close(final IGhost aGhost);
	void close(final IInterface anInterface);
	void close(final IMemberClass aMemberClass);
	void close(final IMemberGhost aMemberGhost);
	void close(final IMemberInterface aMemberInterface);
	void close(final IMethod aMethod);
	void close(final IPackage aPackage);
	void close(final IPackageDefault aPackage);
	// TODO: Add close(final IPackageGhost aPackageGhost);
	// void close(final IPackageGhost aPackageGhost);
	void close(final ISetter aSetter);
	String getName();
	void open(final IAbstractModel anAbstractModel);
	void open(final IClass aClass);
	void open(final IConstructor aConstructor);
	void open(final IDelegatingMethod aDelegatingMethod);
	void open(final IGetter aGetter);
	void open(final IGhost aGhost);
	void open(final IInterface anInterface);
	void open(final IMemberClass aMemberClass);
	void open(final IMemberGhost aMemberGhost);
	void open(final IMemberInterface aMemberInterface);
	void open(final IMethod aMethod);
	void open(final IPackage aPackage);
	void open(final IPackageDefault aPackage);
	// TODO: Add open(final IPackageGhost aPackageGhost);
	// void open(final IPackageGhost aPackageGhost);
	void open(final ISetter aSetter);
	void reset();
	void unknownConstituentHandler(
		final String aCalledMethodName,
		final IConstituent aConstituent);
	void visit(final IAggregation anAggregation);
	void visit(final IAssociation anAssociation);
	void visit(final IComposition aComposition);
	void visit(final IContainerAggregation aContainerAggregation);
	void visit(final IContainerComposition aContainerComposition);
	void visit(final ICreation aCreation);
	void visit(final IField aField);
	void visit(final IMethodInvocation aMethodInvocation);
	void visit(final IParameter aParameter);
	// Yann 2013/07/13: Added new visit method!
	// When adding primitive entities, I forgot to add the corresponding
	// visit() method that, in most cases, does nothing but in some cases
	// must: like when generating models for MADMatch! 
	void visit(final IPrimitiveEntity aPrimitiveEntity);
	void visit(final IUseRelationship aUse);
}
