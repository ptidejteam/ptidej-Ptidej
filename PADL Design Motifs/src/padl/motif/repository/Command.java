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
import padl.kernel.IAggregation;
import padl.kernel.IAssociation;
import padl.kernel.IClass;
import padl.kernel.IDelegatingMethod;
import padl.kernel.IMethod;
import padl.motif.IDesignMotifModel;
import padl.motif.models.BehaviouralMotifModel;
import util.multilingual.MultilingualManager;

/**
 * @author Foutse Khomh
 * @since  2007/03/01
 */
public class Command extends BehaviouralMotifModel implements Cloneable,
		IDesignMotifModel {
	private static final char[] ACTION = "Action".toCharArray();
	private static final char[] CLIENT = "Client".toCharArray();
	private static final char[] COMMAND = "Command".toCharArray();
	private static final char[] CONCRETE_COMMAND = "ConcreteCommand"
		.toCharArray();
	private static final char[] EXECUTE = "Execute".toCharArray();
	private static final char[] FOO = "foo".toCharArray();
	private static final char[] INVOKER = "Invoker".toCharArray();
	private static final char[] RECEIVER = "Receiver".toCharArray();
	private static final long serialVersionUID = -1371421311301524058L;

	public Command() {
		super(Command.COMMAND);

		final IClass client =
			this.getFactory().createClass(Command.CLIENT, Command.CLIENT);
		final IClass command =
			this.getFactory().createClass(Command.COMMAND, Command.COMMAND);
		final IMethod executeMethod =
			this.getFactory().createMethod(Command.EXECUTE, Command.EXECUTE);
		executeMethod.setAbstract(true);
		command.setAbstract(true);
		command.addConstituent(executeMethod);

		final IClass Invoker =
			this.getFactory().createClass(Command.INVOKER, Command.INVOKER);
		final IAggregation aComposition =
			this.getFactory().createAggregationRelationship(
				Command.COMMAND,
				command,
				Constants.CARDINALITY_MANY);
		Invoker.addConstituent(aComposition);
		final IClass Receiver =
			this.getFactory().createClass(Command.RECEIVER, Command.RECEIVER);
		final IMethod ActionMethod =
			this.getFactory().createMethod(Command.ACTION, Command.ACTION);
		Receiver.addConstituent(ActionMethod);

		final IClass concreteCommand =
			this.getFactory().createClass(
				Command.CONCRETE_COMMAND,
				Command.CONCRETE_COMMAND);
		concreteCommand.addInheritedEntity(command);

		final IAssociation associationConcreateCommand =
			this.getFactory().createAssociationRelationship(
				Command.EXECUTE,
				Receiver,
				1);
		concreteCommand.addConstituent(associationConcreateCommand);
		final IDelegatingMethod aPDelegatingMethod =
			this.getFactory().createDelegatingMethod(
				Command.EXECUTE,
				associationConcreateCommand,
				ActionMethod);

		concreteCommand.addConstituent(aPDelegatingMethod);

		final IAssociation associationClientReceiver =
			this.getFactory().createAssociationRelationship(
				Command.ACTION,
				Receiver,
				1);
		client.addConstituent(associationClientReceiver);
		final IDelegatingMethod method =
			this.getFactory().createDelegatingMethod(
				Command.FOO,
				associationClientReceiver,
				ActionMethod);
		client.addConstituent(method);

		this.addConstituent(client);
		this.addConstituent(command);
		this.addConstituent(Invoker);
		this.addConstituent(Receiver);
		this.addConstituent(concreteCommand);
	}

	public String getIntent() {
		return MultilingualManager.getString("INTENT", Command.class);
	}

	public char[] getName() {
		return Command.COMMAND;
	}
}
