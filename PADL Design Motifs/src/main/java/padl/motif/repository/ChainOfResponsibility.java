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

import padl.kernel.IAssociation;
import padl.kernel.IClass;
import padl.kernel.IDelegatingMethod;
import padl.kernel.IInterface;
import padl.kernel.IMethod;
import padl.motif.IDesignMotifModel;
import padl.motif.models.BehaviouralMotifModel;
import util.multilingual.MultilingualManager;

/**
 * @author Foutse Khomh
 * @since  2007/03/01
 */
public class ChainOfResponsibility extends BehaviouralMotifModel implements
		Cloneable, IDesignMotifModel {

	private static final char[] CHAIN_OF_RESPONSIBILITY =
		"ChainOfResponsibility".toCharArray();
	private static final char[] CLIENT = "Client".toCharArray();
	private static final char[] CONCRETE_HANDLER = "ConcreteHandler"
		.toCharArray();
	private static final char[] FOO = "foo".toCharArray();
	private static final char[] HANDLE_REQUEST = "HandleRequest".toCharArray();
	private static final char[] HANDLER = "Handler".toCharArray();
	private static final long serialVersionUID = -6201681806591176334L;
	private static final char[] SUCCESSOR = "successor".toCharArray();

	public ChainOfResponsibility() {
		super(ChainOfResponsibility.CHAIN_OF_RESPONSIBILITY);

		final IClass Client =
			this.getFactory().createClass(
				ChainOfResponsibility.CLIENT,
				ChainOfResponsibility.CLIENT);
		final IInterface Handler =
			this.getFactory().createInterface(
				ChainOfResponsibility.HANDLER,
				ChainOfResponsibility.HANDLER);
		final IMethod HandleRequest =
			this.getFactory().createMethod(
				ChainOfResponsibility.HANDLE_REQUEST,
				ChainOfResponsibility.HANDLE_REQUEST);
		Handler.addConstituent(HandleRequest);

		final IAssociation associationHandlerSuccessor =
			this.getFactory().createAssociationRelationship(
				ChainOfResponsibility.SUCCESSOR,
				Handler,
				1);
		Handler.addConstituent(associationHandlerSuccessor);

		final IClass concreteHandler =
			this.getFactory().createClass(
				ChainOfResponsibility.CONCRETE_HANDLER,
				ChainOfResponsibility.CONCRETE_HANDLER);
		concreteHandler.addImplementedInterface(Handler);
		concreteHandler.assumeAllInterfaces();

		final IAssociation associationClientReceiver =
			this.getFactory().createAssociationRelationship(
				ChainOfResponsibility.HANDLE_REQUEST,
				Handler,
				1);
		Client.addConstituent(associationClientReceiver);
		final IDelegatingMethod method =
			this.getFactory().createDelegatingMethod(
				ChainOfResponsibility.FOO,
				associationClientReceiver,
				HandleRequest);
		Client.addConstituent(method);

		this.addConstituent(Client);
		this.addConstituent(Handler);
		this.addConstituent(concreteHandler);

	}

	public String getIntent() {
		return MultilingualManager.getString(
			"INTENT",
			ChainOfResponsibility.class);
	}

	public char[] getName() {
		return ChainOfResponsibility.CHAIN_OF_RESPONSIBILITY;
	}
}
