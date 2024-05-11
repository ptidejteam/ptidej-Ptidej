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
package sad.rule.creator.model;

/**
 * @author Pierre Leduc
 */
public interface IMetric extends IAttribute {

	public int getComparisonOperator();
	public double getFuzziness();
	public IMetric getMetric1();
	public IMetric getMetric2();
	public double getNumericValue();
	public int getOperator();
	public int getOrdinalValue();
	public void setComparisonOperator(final int value);

	public void setFuzziness(final double value);
	public void setNumericValue(final double value);
	public void setOrdinalValue(final int value);

}
