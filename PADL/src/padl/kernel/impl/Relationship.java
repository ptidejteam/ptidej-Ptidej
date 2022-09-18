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
package padl.kernel.impl;

import padl.kernel.Constants;
import padl.kernel.IElementMarker;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IRelationship;
import padl.kernel.IUseRelationship;
import padl.kernel.exception.ModelDeclarationException;
import padl.util.Util;
import util.io.ProxyConsole;
import util.lang.Modifier;
import util.multilingual.MultilingualManager;

abstract class Relationship extends Element implements IElementMarker,
		IRelationship {

	private static final long serialVersionUID = -5857707891166836532L;
	private int cardinality;
	private IFirstClassEntity targetEntity;

	public Relationship(final char[] anID) {
		super(anID);
	}
	public int getCardinality() {
		return this.cardinality;
	}
	public IFirstClassEntity getTargetEntity() {
		return this.targetEntity;
	}
	public void performCloneSession() {
		super.performCloneSession();
		final IUseRelationship clone = (IUseRelationship) this.getClone();
		clone.setTargetEntity((IFirstClassEntity) this.targetEntity.getClone());
	}
	public void setCardinality(int aCardinality) {
		if (aCardinality < 1) {
			throw new ModelDeclarationException(MultilingualManager.getString(
				"CARDINALITY",
				IRelationship.class,
				new Object[] { Integer.valueOf(this.cardinality) }));
		}

		this.cardinality = aCardinality;
	}
	public void setTargetEntity(final IFirstClassEntity anEntity) {
		this.targetEntity = anEntity;
	}
	public String toString() {
		if (Constants.DEBUG) {
			ProxyConsole.getInstance().debugOutput().print("// ");
			ProxyConsole.getInstance().debugOutput().print(this.getClass());
			ProxyConsole.getInstance().debugOutput().println(".toString()");
		}
		return this.toString(0);
	}
	public String toString(final int tab) {
		final StringBuffer buffer = new StringBuffer();
		Util.addTabs(tab, buffer);
		buffer.append(this.getClass().getName());
		buffer.append("\nName: ");
		buffer.append(this.getName());
		buffer.append("\nWith: ");
		buffer.append(this.getTargetEntity().getName());
		buffer.append("\nVisibility: ");
		buffer.append(Modifier.toString(this.getVisibility()));
		buffer.append(", cadinality: ");
		buffer.append(this.getCardinality());
		buffer.append('\n');
		return buffer.toString();
	}
}
