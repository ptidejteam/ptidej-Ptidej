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
import padl.kernel.IClass;
import padl.kernel.ICreation;
import padl.kernel.IInterface;
import padl.kernel.IMethod;
import padl.kernel.IUseRelationship;
import padl.motif.IDesignMotifModel;
import padl.motif.models.CreationalMotifModel;
import util.multilingual.MultilingualManager;
import com.ibm.toad.cfparse.utils.Access;

/**
 * @author Foutse Khomh
 * @since  2007/03/01
 */
public class AbstractFactory extends CreationalMotifModel implements Cloneable,
		IDesignMotifModel {
	public static final char[] CREATOR = "Creator".toCharArray();
	public static final char[] PRODUCT = "Product".toCharArray();
	private static final char[] CLIENT = "Client".toCharArray();
	public static final char[] CONCRETE_CREATOR = "ConcreteCreator"
		.toCharArray();
	public static final char[] CONCRETE_PRODUCT = "ConcreteProduct"
		.toCharArray();
	private static final char[] KNOW = "know".toCharArray();
	private static final long serialVersionUID = -7771579033362195570L;
	private static final char[] USE = "use".toCharArray();

	public AbstractFactory() throws CloneNotSupportedException {
		super(AbstractFactory.CREATOR);

		final IClass Client =
			this.getFactory().createClass(
				AbstractFactory.CLIENT,
				AbstractFactory.CLIENT);
		final IInterface abstractFactory =
			this.getFactory().createInterface(
				AbstractFactory.CREATOR,
				AbstractFactory.CREATOR);
		final IClass concreteFactory =
			this.getFactory().createClass(
				AbstractFactory.CONCRETE_CREATOR,
				AbstractFactory.CONCRETE_CREATOR);
		concreteFactory.addInheritedEntity(abstractFactory);

		final IUseRelationship ClientuseRelationship =
			this.getFactory().createUseRelationship(
				AbstractFactory.USE,
				abstractFactory,
				Constants.CARDINALITY_ONE);
		Client.addConstituent(ClientuseRelationship);

		this.addConstituent(Client);

		this.addConstituent(abstractFactory);
		this.addConstituent(concreteFactory);

		this.addProduct(new char[][] { AbstractFactory.PRODUCT,
				AbstractFactory.CONCRETE_PRODUCT });
	}

	public void addProduct(final char[][] names) {
		// names[0] is abstractProductName
		// names[1] is concreteProductName
		final char[] abstractProductName = names[0];
		final char[] concreteProductName = names[1];
		final IClass Client =
			this.getFactory().createClass(
				AbstractFactory.CLIENT,
				AbstractFactory.CLIENT);
		final IInterface abstractFactory =
			(IInterface) this.getConstituentFromName(AbstractFactory.CREATOR);
		final IClass concreteFactory =
			(IClass) this
				.getConstituentFromName(AbstractFactory.CONCRETE_CREATOR);
		final IInterface abstractProduct =
			this.getFactory().createInterface(
				abstractProductName,
				abstractProductName);
		final IClass concreteProduct =
			this.getFactory().createClass(
				concreteProductName,
				concreteProductName);
		concreteProduct.addImplementedInterface(abstractProduct);

		final IUseRelationship useRelationship =
			this.getFactory().createUseRelationship(
				AbstractFactory.KNOW,
				abstractProduct,
				Constants.CARDINALITY_ONE);
		abstractFactory.addConstituent(useRelationship);

		final char[] nameAndID =
			("create" + String.valueOf(abstractProductName)).toCharArray();
		final IMethod abstractCreateMethod =
			this.getFactory().createMethod(nameAndID, nameAndID);
		abstractCreateMethod.setReturnType(abstractProductName);
		abstractCreateMethod.setVisibility(Access.ACC_ABSTRACT);
		abstractFactory.addConstituent(abstractCreateMethod);

		final IMethod concreteCreateMethod =
			this.getFactory().createMethod(nameAndID, nameAndID);
		concreteCreateMethod.setCodeLines("return new "
				+ String.valueOf(concreteProductName) + "();");
		concreteCreateMethod.attachTo(abstractCreateMethod);
		concreteFactory.addConstituent(concreteCreateMethod);

		final ICreation creationLink =
			this.getFactory().createCreationRelationship(
				"create".toCharArray(),
				concreteProduct,
				Constants.CARDINALITY_ONE);
		concreteFactory.addConstituent(creationLink);
		final IUseRelationship ClientuseRelationship =
			this.getFactory().createUseRelationship(
				AbstractFactory.USE,
				abstractProduct,
				Constants.CARDINALITY_ONE);
		Client.addConstituent(ClientuseRelationship);
		this.addConstituent(abstractProduct);
		this.addConstituent(concreteProduct);
	}

	public String getIntent() {
		return MultilingualManager.getString("INTENT", FactoryMethod.class);
	}

	public char[] getName() {
		return "AbstractFactory".toCharArray();
	}

	public void removeProduct(final String[] names) {
	}
}
