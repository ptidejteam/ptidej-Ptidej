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
import padl.kernel.IMethod;
import padl.motif.IDesignMotifModel;
import padl.motif.models.StructuralMotifModel;
import util.multilingual.MultilingualManager;

public class Adapter extends StructuralMotifModel implements Cloneable,
		IDesignMotifModel {
	private static final char[] ADAPTEE = "Adaptee".toCharArray();
	private static final char[] ADAPTER = "Adapter".toCharArray();
	private static final char[] CLIENT = "Client".toCharArray();
	private static final char[] FOO = "foo".toCharArray();
	private static final char[] REQUEST = "request".toCharArray();
	private static final long serialVersionUID = -8439597980811548922L;
	private static final char[] SPECIFIC_REQUEST = "specificRequest"
		.toCharArray();
	private static final char[] TARGET = "Target".toCharArray();

	public Adapter() {
		super(Adapter.ADAPTER);

		final IClass client =
			this.getFactory().createClass(Adapter.CLIENT, Adapter.CLIENT);
		final IClass target =
			this.getFactory().createClass(Adapter.TARGET, Adapter.TARGET);
		final IClass adapter =
			this.getFactory().createClass(Adapter.ADAPTER, Adapter.ADAPTER);
		final IClass adaptee =
			this.getFactory().createClass(Adapter.ADAPTEE, Adapter.ADAPTEE);

		final IMethod specificRequest =
			this.getFactory().createMethod(
				Adapter.SPECIFIC_REQUEST,
				Adapter.SPECIFIC_REQUEST);
		adaptee.addConstituent(specificRequest);

		final IAssociation associationAdapterAdaptee =
			this.getFactory().createAssociationRelationship(
				Adapter.REQUEST,
				adaptee,
				1);
		adapter.addConstituent(associationAdapterAdaptee);
		final IDelegatingMethod request =
			this.getFactory().createDelegatingMethod(
				Adapter.REQUEST,
				associationAdapterAdaptee,
				specificRequest);
		adapter.addConstituent(request);
		adapter.addInheritedEntity(target);

		final IMethod abstractRequest =
			this.getFactory().createMethod(Adapter.REQUEST, Adapter.REQUEST);
		abstractRequest.setAbstract(true);
		target.setAbstract(true);
		target.addConstituent(abstractRequest);

		final IAssociation associationClientTarget =
			this.getFactory().createAssociationRelationship(
				Adapter.REQUEST,
				target,
				1);
		client.addConstituent(associationClientTarget);
		final IDelegatingMethod method =
			this.getFactory().createDelegatingMethod(
				Adapter.FOO,
				associationClientTarget,
				abstractRequest);
		client.addConstituent(method);

		this.addConstituent(client);
		this.addConstituent(target);
		this.addConstituent(adapter);
		this.addConstituent(adaptee);
	}

	public String getIntent() {
		return MultilingualManager.getString("INTENT", Adapter.class);
	}

	public char[] getName() {
		return Adapter.ADAPTER;
	}
}
