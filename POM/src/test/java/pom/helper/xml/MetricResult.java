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
 * @author Jean-Yves Guyomarc'h
 *
 */
public class MetricResult {

	private String entityName;
	private double[] values;

	/**
	 * 
	 */
	public MetricResult() {
		this.entityName = null;
		this.values = null;
	}
	/**
	 * 
	 */
	public MetricResult(final String entityName, final double[] values) {
		this.entityName = entityName;
		this.values = values;
	}
	/**
	 * @return Returns the entityName.
	 */
	public String getEntityName() {
		return this.entityName;
	}
	/**
	 * @return Returns the values.
	 */
	public double[] getValues() {
		return this.values;
	}
	/**
	 * @param entityName The entityName to set.
	 */
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	/**
	 * @param values The values to set.
	 */
	public void setValues(double[] values) {
		this.values = values;
	}
}
