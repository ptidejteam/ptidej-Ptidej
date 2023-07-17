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

import org.apache.commons.lang3.ArrayUtils;
import padl.kernel.Constants;
import padl.kernel.IAggregation;
import padl.kernel.IClass;
import padl.kernel.ICreation;
import padl.kernel.IMethod;
import padl.motif.IDesignMotifModel;
import padl.motif.models.BehaviouralMotifModel;
import util.multilingual.MultilingualManager;

/**
 * @author Foutse Khomh
 * @since  2007/03/01
 */
public class Memento extends BehaviouralMotifModel implements Cloneable,
		IDesignMotifModel {
	private static final char[] CARETAKER = "Caretaker".toCharArray();
	private static final char[] CREATE = "create".toCharArray();
	private static final char[] GET_STATE = "GetState".toCharArray();
	private static final char[] MEMENTO = "Memento".toCharArray();
	private static final char[] ORIGINATOR = "Originator".toCharArray();
	private static final long serialVersionUID = 3879779843304557078L;
	private static final char[] SET_STATE = "SetState".toCharArray();
	private static final char[] STRING = "memento".toCharArray();

	public Memento() {
		super(Memento.MEMENTO);

		final IClass originator =
			this.getFactory().createClass(
				Memento.ORIGINATOR,
				Memento.ORIGINATOR);
		final IClass memento =
			this.getFactory().createClass(Memento.MEMENTO, Memento.MEMENTO);
		final ICreation creationLink =
			this.getFactory().createCreationRelationship(
				Memento.CREATE,
				memento,
				Constants.CARDINALITY_ONE);
		originator.addConstituent(creationLink);

		final IMethod originatorCreateMethod =
			this.getFactory().createMethod(
				ArrayUtils.addAll(Memento.CREATE, Memento.MEMENTO),
				ArrayUtils.addAll(Memento.CREATE, Memento.MEMENTO));

		originatorCreateMethod.setCodeLines("return new "
				+ String.valueOf(Memento.MEMENTO) + "();");

		originator.addConstituent(originatorCreateMethod);

		final IMethod getState =
			this
				.getFactory()
				.createMethod(Memento.GET_STATE, Memento.GET_STATE);
		memento.addConstituent(getState);
		final IMethod setState =
			this
				.getFactory()
				.createMethod(Memento.SET_STATE, Memento.SET_STATE);
		memento.addConstituent(setState);

		final IClass caretaker =
			this.getFactory().createClass(Memento.CARETAKER, Memento.CARETAKER);
		final IAggregation aComposition =
			this.getFactory().createAggregationRelationship(
				Memento.STRING,
				memento,
				Constants.CARDINALITY_MANY);
		caretaker.addConstituent(aComposition);

		this.addConstituent(originator);
		this.addConstituent(memento);
		this.addConstituent(caretaker);

	}

	public String getIntent() {
		return MultilingualManager.getString("INTENT", Memento.class);
	}

	public char[] getName() {
		return Memento.MEMENTO;
	}
}
