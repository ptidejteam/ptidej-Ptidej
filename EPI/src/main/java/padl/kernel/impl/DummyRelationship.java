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

import org.apache.commons.lang3.ArrayUtils;
import padl.event.IEvent;
import padl.kernel.IElement;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IRelationship;
import padl.path.IConstants;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2005/04/08
 */
public class DummyRelationship extends Constituent implements IRelationship {
	private static final long serialVersionUID = 8246146842271467729L;
	private static char[] DEFAULT_ID = "dm".toCharArray();
	private static int UniqueID = 0;

	private final IFirstClassEntity targetEntity;

	public DummyRelationship(final IFirstClassEntity aTargetEntity) {
		super(ArrayUtils.addAll(
			DummyRelationship.DEFAULT_ID,
			Integer.toString(DummyRelationship.UniqueID++).toCharArray()));
		this.targetEntity = aTargetEntity;
	}
	public void attachTo(final IElement anElement) {
	}
	public void detach() {
	}
	public void fireModelChange(final String anEventType, final IEvent anEvent) {
	}
	public IElement getAttachedElement() {
		return null;
	}
	public int getCardinality() {
		return 0;
	}
	protected char getPathSymbol() {
		return IConstants.ELEMENT_SYMBOL;
	}
	public IFirstClassEntity getTargetEntity() {
		return this.targetEntity;
	}
	public void setTargetEntity(final IFirstClassEntity anEntity) {
	}
	public void setCardinality(final int aCardinality) {
	}
}
