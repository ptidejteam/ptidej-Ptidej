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
package pom.helper.xml;

/**
 * Define properties for Pom Metrics Vizualisation.
 * 
 * @author Jean-Yves Guyomarc'h
 * @since 2006/03/10
 *
 */
public class MetricProp {

	private String name;
	private String comment;
	private String type;
	private double max;
	private double min;

	public MetricProp(
		final String name,
		final String comment,
		final String type,
		final double max,
		final double min) {
		this.name = name;
		this.comment = comment;
		this.type = type;
		this.max = max;
		this.min = min;
	}

	public MetricProp(final String name, final String comment, final String type) {
		this(name, comment, type, -1.0, -1.0);
	}

	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @param type The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return Returns the comment.
	 */
	public String getComment() {
		return this.comment;
	}

	/**
	 * @return Returns the max, -1.0 if not set.
	 */
	public double getMax() {
		return this.max;
	}

	/**
	 * @return Returns the min, -1.0 if not set.
	 */
	public double getMin() {
		return this.min;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param comment The comment to set.
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @param max The max to set.
	 */
	public void setMax(double max) {
		this.max = max;
	}

	/**
	 * @param min The min to set.
	 */
	public void setMin(double min) {
		this.min = min;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

}
