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
import padl.kernel.IUseRelationship;
import padl.motif.IDesignMotifModel;
import padl.motif.models.BehaviouralMotifModel;
import util.multilingual.MultilingualManager;

/**
 * @author Foutse Khomh
 * @since  2007/03/01
 */
public class Mediator extends BehaviouralMotifModel implements Cloneable,
		IDesignMotifModel {
	public static final char[] CLIENT1 = "Client1".toCharArray();
	private static final char[] CLIENT1_MEDIATOR = "client1Mediator"
		.toCharArray();
	public static final char[] CLIENT2 = "Client2".toCharArray();
	private static final char[] CLIENT2_MEDIATOR = "client2Mediator"
		.toCharArray();
	public static final char[] MEDIATOR = "Mediator".toCharArray();
	private static final char[] MEDIATOR_CLIENT1 = "mediatorClient1"
		.toCharArray();
	private static final char[] MEDIATOR_CLIENT2 = "mediatorClient2"
		.toCharArray();
	private static final long serialVersionUID = 5608009702431565343L;

	public Mediator() {
		super(Mediator.MEDIATOR);

		final IClass client1 =
			this.getFactory().createClass(Mediator.CLIENT1, Mediator.CLIENT1);
		final IClass mediator =
			this.getFactory().createClass(Mediator.MEDIATOR, Mediator.MEDIATOR);
		final IClass client2 =
			this.getFactory().createClass(Mediator.CLIENT2, Mediator.CLIENT2);

		final IUseRelationship client1Mediator =
			this.getFactory().createUseRelationship(
				Mediator.CLIENT1_MEDIATOR,
				mediator,
				Constants.CARDINALITY_MANY);
		client1.addConstituent(client1Mediator);
		final IUseRelationship mediatorClient1 =
			this.getFactory().createUseRelationship(
				Mediator.MEDIATOR_CLIENT1,
				client1,
				Constants.CARDINALITY_MANY);
		mediator.addConstituent(mediatorClient1);

		final IUseRelationship client2Mediator =
			this.getFactory().createUseRelationship(
				Mediator.CLIENT2_MEDIATOR,
				mediator,
				Constants.CARDINALITY_MANY);
		client2.addConstituent(client2Mediator);
		final IUseRelationship mediatorClient2 =
			this.getFactory().createUseRelationship(
				Mediator.MEDIATOR_CLIENT2,
				client2,
				Constants.CARDINALITY_MANY);
		mediator.addConstituent(mediatorClient2);

		this.addConstituent(client1);
		this.addConstituent(mediator);
		this.addConstituent(client2);
	}

	public String getIntent() {
		return MultilingualManager.getString("INTENT", Mediator.class);
	}

	public char[] getName() {
		return Mediator.MEDIATOR;
	}
}
