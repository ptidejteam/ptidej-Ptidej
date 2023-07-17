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
/**
 * 
 */
package sad.rule.creator.ui.metaModel;

/**
 * @author Family
 *
 */
public final class Relationship {
	private String type;
	private String name;
	private String from;
	private String to;
	private String fromCardinality;
	private String toCardinality;
	public Relationship(
		final String aType,
		final String aName,
		final String aFrom,
		final String aTo,
		final String aFromCardinality,
		final String aToCardinality) {
		this.type = aType;
		this.name = aName;
		this.from = aFrom;
		this.to = aTo;
		this.fromCardinality = aFromCardinality;
		this.toCardinality = aToCardinality;
	}
	/**
	 * @return Returns the from.
	 */
	public String getFrom() {
		return this.from;
	}
	/**
	 * @return Returns the fromCardinality.
	 */
	public String getFromCardinality() {
		return this.fromCardinality;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * @return Returns the to.
	 */
	public String getTo() {
		return this.to;
	}
	/**
	 * @return Returns the toCardinality.
	 */
	public String getToCardinality() {
		return this.toCardinality;
	}
	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return this.type;
	}
	/**
	 * @param from The from to set.
	 */
	public void setFrom(final String from) {
		this.from = from;
	}
	/**
	 * @param fromCardinality The fromCardinality to set.
	 */
	public void setFromCardinality(final String fromCardinality) {
		this.fromCardinality = fromCardinality;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(final String name) {
		this.name = name;
	}
	/**
	 * @param to The to to set.
	 */
	public void setTo(final String to) {
		this.to = to;
	}
	/**
	 * @param toCardinality The toCardinality to set.
	 */
	public void setToCardinality(final String toCardinality) {
		this.toCardinality = toCardinality;
	}
	/**
	 * @param type The type to set.
	 */
	public void setType(final String type) {
		this.type = type;
	}

}
