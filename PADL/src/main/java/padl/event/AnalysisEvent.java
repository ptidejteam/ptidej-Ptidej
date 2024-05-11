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
package padl.event;

import java.util.List;

public final class AnalysisEvent implements IEvent {
	private final char[] name;
	private final char[] elementType;

	/**
	 * @deprecated by AnalysisEvent(final char[])
	 */
	public AnalysisEvent(final String aName) {
		this.name = aName.toCharArray();
		this.elementType = new char[0];
	}
	/**
	 * @deprecated by AnalysisEvent(final char[], final char[])
	 */
	public AnalysisEvent(final char[] aName) {
		this.name = aName;
		this.elementType = new char[0];
	}
	public AnalysisEvent(final char[] aName, final char[] anElementType) {
		this.name = aName;
		this.elementType = anElementType;
	}
	public char[] getConstituentName() {
		return this.name;
	}
	public List getSubmittedConstituents() {
		return null;
	}
	public String toString() {
		final StringBuffer buffer =
			new StringBuffer(this.name.length + this.elementType.length + 1);
		buffer.append(this.name);
		buffer.append(';');
		buffer.append(this.elementType);
		return buffer.toString();
	}
}
