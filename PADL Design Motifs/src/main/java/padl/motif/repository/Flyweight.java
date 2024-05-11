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

import padl.kernel.Constants;
import padl.kernel.IAssociation;
import padl.kernel.IClass;
import padl.kernel.IContainerComposition;
import padl.kernel.IDelegatingMethod;
import padl.kernel.IInterface;
import padl.kernel.IMethod;
import padl.motif.IDesignMotifModel;
import padl.motif.models.StructuralMotifModel;
import util.multilingual.MultilingualManager;

/**
 * @author Foutse Khomh
 * @since  2007/03/01
 */
public class Flyweight extends StructuralMotifModel implements Cloneable,
		IDesignMotifModel {
	private static final char[] CLIENT = "Client".toCharArray();
	private static final char[] CONCRETE_FLYWEIGHT = "ConcreteFlyweight"
		.toCharArray();
	private static final char[] FLYWEIGHT = "Flyweight".toCharArray();
	private static final char[] FLYWEIGHT_FACTORY = "FlyweightFactory"
		.toCharArray();
	private static final char[] FLYWEIGHTS = "flyweights".toCharArray();
	private static final char[] FOO1 = "foo1".toCharArray();
	private static final char[] FOO2 = "foo2".toCharArray();
	private static final char[] FOO3 = "foo3".toCharArray();
	private static final char[] GET_FLYWEIGHT = "GetFlyweight".toCharArray();
	private static final char[] OPERATION = "operation".toCharArray();
	private static final long serialVersionUID = 8660578974635370543L;
	private static final char[] UNSHARED_CONCRETE_FLYWEIGHT =
		"UnsharedConcreteFlyweight".toCharArray();

	public Flyweight() {
		super(Flyweight.FLYWEIGHT);

		final IClass client =
			this.getFactory().createClass(Flyweight.CLIENT, Flyweight.CLIENT);
		final IClass flyweightFactory =
			this.getFactory().createClass(
				Flyweight.FLYWEIGHT_FACTORY,
				Flyweight.FLYWEIGHT_FACTORY);
		final IMethod getFlyweightMethod =
			this.getFactory().createMethod(
				Flyweight.GET_FLYWEIGHT,
				Flyweight.GET_FLYWEIGHT);
		flyweightFactory.addConstituent(getFlyweightMethod);

		final IInterface flyweight =
			this.getFactory().createInterface(
				Flyweight.FLYWEIGHT,
				Flyweight.FLYWEIGHT);
		final IMethod mOperation =
			this.getFactory().createMethod(
				Flyweight.OPERATION,
				Flyweight.OPERATION);
		flyweight.addConstituent(mOperation);
		final IContainerComposition aComposition =
			this.getFactory().createContainerCompositionRelationship(
				Flyweight.FLYWEIGHTS,
				flyweight,
				Constants.CARDINALITY_MANY);
		flyweightFactory.addConstituent(aComposition);

		final IClass concreteFlyweight =
			this.getFactory().createClass(
				Flyweight.CONCRETE_FLYWEIGHT,
				Flyweight.CONCRETE_FLYWEIGHT);
		final IClass unsharedConcreteFlyweight =
			this.getFactory().createClass(
				Flyweight.UNSHARED_CONCRETE_FLYWEIGHT,
				Flyweight.UNSHARED_CONCRETE_FLYWEIGHT);
		concreteFlyweight.addImplementedInterface(flyweight);
		unsharedConcreteFlyweight.addImplementedInterface(flyweight);
		concreteFlyweight.assumeAllInterfaces();
		unsharedConcreteFlyweight.assumeAllInterfaces();

		final IAssociation associationClientFlyweightFactory =
			this.getFactory().createAssociationRelationship(
				Flyweight.GET_FLYWEIGHT,
				flyweightFactory,
				1);
		client.addConstituent(associationClientFlyweightFactory);
		final IDelegatingMethod method1 =
			this.getFactory().createDelegatingMethod(
				Flyweight.FOO1,
				associationClientFlyweightFactory,
				getFlyweightMethod);
		client.addConstituent(method1);

		final IAssociation associationClientConcreteFlyweight =
			this.getFactory().createAssociationRelationship(
				Flyweight.OPERATION,
				concreteFlyweight,
				1);
		client.addConstituent(associationClientConcreteFlyweight);
		final IDelegatingMethod method2 =
			this.getFactory().createDelegatingMethod(
				Flyweight.FOO2,
				associationClientConcreteFlyweight,
				mOperation);
		client.addConstituent(method2);
		final IAssociation associationClientUnsharedConcreteFlyweight =
			this.getFactory().createAssociationRelationship(
				Flyweight.OPERATION,
				unsharedConcreteFlyweight,
				1);
		client.addConstituent(associationClientUnsharedConcreteFlyweight);
		final IDelegatingMethod method3 =
			this.getFactory().createDelegatingMethod(
				Flyweight.FOO3,
				associationClientUnsharedConcreteFlyweight,
				mOperation);
		client.addConstituent(method3);

		this.addConstituent(client);
		this.addConstituent(flyweightFactory);
		this.addConstituent(flyweight);
		this.addConstituent(concreteFlyweight);
		this.addConstituent(unsharedConcreteFlyweight);
	}

	public String getIntent() {
		return MultilingualManager.getString("INTENT", Flyweight.class);
	}

	public char[] getName() {
		return Flyweight.FLYWEIGHT;
	}
}
