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
import padl.kernel.IClass;
import padl.kernel.IDelegatingMethod;
import padl.kernel.IMethod;
import padl.motif.IDesignMotifModel;
import padl.motif.models.BehaviouralMotifModel;
import util.multilingual.MultilingualManager;

public class State extends BehaviouralMotifModel implements Cloneable,
		IDesignMotifModel {
	private static final char[] CONCRETE_STATE = "concreteState".toCharArray();
	private static final char[] CONTEXT = "Context".toCharArray();
	private static final char[] HANDLE = "handle".toCharArray();
	private static final char[] REQUEST = "request".toCharArray();
	private static final long serialVersionUID = 1326720399480859308L;
	private static final char[] STATE = "State".toCharArray();
	private static final char[] STRING = "state".toCharArray();

	public State() {
		super(State.STATE);

		final IClass context =
			this.getFactory().createClass(State.CONTEXT, State.CONTEXT);
		// final IMethod RequestMethod =
		// this.getFactory().createMethod("request");
		// context.addConstituent(RequestMethod);

		final IClass state =
			this.getFactory().createClass(State.STATE, State.STATE);
		state.setAbstract(true);
		final IMethod HandleMethod =
			this.getFactory().createMethod(State.HANDLE, State.HANDLE);
		HandleMethod.setAbstract(true);
		state.addConstituent(HandleMethod);

		final IAggregation aComposition =
			this.getFactory().createAggregationRelationship(
				State.STRING,
				state,
				Constants.CARDINALITY_MANY);
		// context.addImplementedEntity(state);
		context.addConstituent(aComposition);

		// final IClass concreteState =
		// this.getFactory().createClass("ConcreteState");
		// concreteState.addInheritedEntity(state);

		/*
		 * final IAssociation associationConcreateState =
		 * this.getFactory().createAssociationRelationship( "concreateState",
		 * concreteState, 1);
		 */
		final IDelegatingMethod aPDelegatingMethod =
			this.getFactory().createDelegatingMethod(
				State.REQUEST,
				aComposition,
				HandleMethod);
		aPDelegatingMethod.attachTo(HandleMethod);
		context.addConstituent(aPDelegatingMethod);

		// concreateHandleMethod.attachTo(HandleMethod);
		// RequestMethod.attachTo(HandleMethod);

		this.addConstituent(context);
		this.addConstituent(state);
		this.addConcrateState(new char[][] { State.CONCRETE_STATE });
	}

	public void addConcrateState(final char[][] names) {
		final char[] concrateStateName = names[0];

		final IClass concreteState =
			this.getFactory().createClass(concrateStateName, concrateStateName);

		concreteState.addInheritedEntity((IClass) this
			.getConstituentFromName(State.STATE));
		final IMethod concreateHandleMethod =
			this.getFactory().createMethod(State.HANDLE, State.HANDLE);
		concreteState.addConstituent(concreateHandleMethod);

		this.addConstituent(concreteState);
	}

	public String getIntent() {
		return MultilingualManager.getString("INTENT", State.class);
	}

	public char[] getName() {
		return State.STATE;
	}
}
