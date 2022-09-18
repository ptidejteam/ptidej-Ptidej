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
package sad.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import padl.kernel.IEntity;

/**
 * This class represents a boxplot. We use the TUKEY definition of the boxplot
 * because it enables to detect extreme values.
 * 
 * @author Naouel Moha
 * @version 2.0
 * @since 2006/02/14
 */

public class BoxPlot {

	public final static double TUKEY = 1.5;

	final protected Map mapOfEntities;

	final protected double[] values;

	final protected int nbValues;

	final protected double median;

	final protected double lowerQuartile;

	final protected double upperQuartile;

	final protected double minBound;

	final protected double maxBound;

	final protected double interQuartileRange;

	final protected double fuzziness;

	public BoxPlot(final Map oldMapOfEntities, final double fuzziness) {
		this.mapOfEntities = oldMapOfEntities;
		this.values = this.sortEntities(this.mapOfEntities);
		this.nbValues = this.values.length;
		this.median = this.getMedian();
		this.lowerQuartile = this.getLowerQuartile();
		this.upperQuartile = this.getUpperQuartile();
		this.interQuartileRange = this.getInterQuartileRange();
		this.minBound = this.getMinBound();
		this.maxBound = this.getMaxBound();

		// Calculate fuzziness value
		//		final double minValue = this.values[0];
		//		final double maxValue = this.values[this.values.length - 1];

		//final double range = maxValue - minValue;
		final double range = this.maxBound - this.minBound;
		this.fuzziness = fuzziness * range / 100;
	}

	public String toString() {
		final StringBuffer s = new StringBuffer();
		s.append("# Results of the Boxplot ");
		s.append(" ###### ");
		s.append(this.nbValues + " values : \n");
		final Iterator iter = this.mapOfEntities.keySet().iterator();
		while (iter.hasNext()) {
			final Object obj = iter.next();
			s.append(obj.toString() + " \n");
			s.append(this.mapOfEntities.get(obj) + " \n");
		}
		s.append("\n ###### ");
		s.append("\n Median        : " + this.median + " ");
		s.append("\n LowerQuartile : " + this.lowerQuartile + " ");
		s.append("\n UpperQuartile : " + this.upperQuartile + " ");
		s.append("\n InterQuartile : " + this.interQuartileRange + " ");
		s.append("\n MinBound      : " + this.minBound + " ");
		s.append("\n MaxBound      : " + this.maxBound + " ");

		s.append("\n\n Fuzziness   : " + this.fuzziness + " ");

		return s.toString();
	}

	/**
	 * Find value of pth sample percentile; can use to get median(p = 0.5)
	 * 
	 * Defintion: A percentile is a value at or below which a given percentage
	 * or fraction of the variable values lie. For a set of measurements
	 * arranged in order of magnitude, the p-th percentile is the value that has
	 * p% of the measurements below it and (100-p)% above it.
	 * 
	 * Here the percentile is calculated according to SAS Method 4 For this
	 * method, we use (n+1) instead of n. In that case, the p-th percentile is
	 * defined by: y = (1-g)*x(j) +g*x(j+1), where (n+1)*p= j + g (and x(n+1) is
	 * taken to be x(n)).
	 */
	public double getPercentile(final double p) {

		final double percentile;

		final double a = (this.nbValues + 1) * p;

		final int j = (int) a;
		final double g = a - j;

		// Yann 2009/05/21: What if there is no values?
		// In this case, better return 0 directly.
		if (this.nbValues == 0) {
			return 0;
		}
		else if (j == this.nbValues) {
			percentile = (1 - g) * this.values[j - 1] + g * this.values[j - 1];
		}
		else if (j == 0) {
			percentile = (1 - g) * this.values[j] + g * this.values[j];
		}
		else {
			percentile = (1 - g) * this.values[j - 1] + g * this.values[j];
		}

		return percentile;
	}

	public double getMedian() {
		return this.getPercentile(0.5);
	}

	// Do not remove!
	// Another method
	// Minitab method 
	// cf. http://mathworld.wolfram.com/Quartile.html
	// TODO: What is the above comment about?  I really don't know, ask Naouel.. :)
	public double getLowerQuartile() {
		int lowerQuartile;

		if (this.nbValues == 0) {
			return 0;
		}
		else if (this.nbValues % 2 == 0) {
			lowerQuartile = (this.nbValues + 1) / 4;
		}
		else {
			lowerQuartile = (this.nbValues + 1) / 4;
		}
		return this.values[lowerQuartile];
	}

	// Do not remove!
	// Another method
	// Minitab method 
	// cf. http://mathworld.wolfram.com/Quartile.html
	public double getUpperQuartile() {
		int upperQuartile;

		// TODO: This computations do not work
		// in the case where there are only
		// two classes... For example, using
		// APSEC Example1.ptidej
		if (this.nbValues == 0) {
			return 0;
		}
		else if (this.nbValues % 2 == 0) {
			upperQuartile = (3 * this.nbValues + 3) / 4;
		}
		else {
			upperQuartile = (3 * this.nbValues + 3) / 4;
		}
		// Yann 2008/12/06: Fix!
		// I make sure that if there are only two values,
		// we return the greater of these values...
		return this.values[Math.min(upperQuartile, this.values.length - 1)];
	}

	public double getInterQuartileRange() {
		return this.getUpperQuartile() - this.getLowerQuartile();
	}

	public double getMinBound() {
		double min =
			this.lowerQuartile - BoxPlot.TUKEY * this.interQuartileRange;

		// exception : if min is lower than the minimal value tolerated (here 0)
		// than min = minimal value tolerated 
		if (min < 0) {
			min = 0;
		}
		return min;
	}

	public double getMaxBound() {
		return this.upperQuartile + BoxPlot.TUKEY * this.interQuartileRange;
	}

	/**
	 * Get high outliers (> max bound)
	 */
	public Map getHighOutliers() {

		final Map map = new HashMap();

		final double max = this.getMaxBound();

		final Iterator iter = this.mapOfEntities.keySet().iterator();
		while (iter.hasNext()) {
			final IEntity entity = (IEntity) iter.next();
			if (((Double[]) this.mapOfEntities.get(entity))[0].doubleValue() > max
					- this.fuzziness) {

				if (((Double[]) this.mapOfEntities.get(entity))[0]
					.doubleValue() < max) {
					((Double[]) this.mapOfEntities.get(entity))[1] =
						Double.valueOf((max - ((Double[]) this.mapOfEntities
							.get(entity))[0].doubleValue())
								* 100 / this.fuzziness);
				}
				else {
					((Double[]) this.mapOfEntities.get(entity))[1] =
						Double.valueOf(100);
				}

				map.put(entity, this.mapOfEntities.get(entity));
			}
		}

		return map;
	}

	/**
	 * Get high values (> 75th percentile or upper quartile) (no high outliers)
	 */
	public Map getHighValues() {

		final Map map = new HashMap();

		final Iterator iter = this.mapOfEntities.keySet().iterator();
		while (iter.hasNext()) {
			final Object entity = iter.next();
			if (((Double[]) this.mapOfEntities.get(entity))[0].doubleValue() > this.upperQuartile
					- this.fuzziness
					&& ((Double[]) this.mapOfEntities.get(entity))[0]
						.doubleValue() < this.maxBound + this.fuzziness) {

				map.put(entity, this.mapOfEntities.get(entity));
			}
		}

		return map;
	}

	/**
	 * Get values higher than val
	 */
	public Map getValuesHigherThan(final double val) {

		final Map map = new HashMap();

		final Iterator iter = this.mapOfEntities.keySet().iterator();
		while (iter.hasNext()) {
			final Object entity = iter.next();
			if (((Double[]) this.mapOfEntities.get(entity))[0].doubleValue() > val
					- this.fuzziness) {

				// Naouel and Yann 2007/03/11: Fuzzyness and clone.
				// We modify this method to take in account the fuzzyness.
				// However, this method is a clear clone of getHigherOutliers()
				// thus should be called by this method!
				// TODO: Remove the clone...
				if (((Double[]) this.mapOfEntities.get(entity))[0]
					.doubleValue() < val) {
					((Double[]) this.mapOfEntities.get(entity))[1] =
						Double.valueOf((val - ((Double[]) this.mapOfEntities
							.get(entity))[0].doubleValue())
								* 100 / this.fuzziness);
				}
				else {
					((Double[]) this.mapOfEntities.get(entity))[1] =
						Double.valueOf(100);
				}

				map.put(entity, this.mapOfEntities.get(entity));
			}
		}

		return map;
	}

	/**
	 * Get low outliers (< min bound)
	 */
	public Map getLowOutliers() {

		final Map map = new HashMap();

		final double min = this.getMinBound();

		final Iterator iter = this.mapOfEntities.keySet().iterator();
		while (iter.hasNext()) {
			final Object entity = iter.next();
			if (((Double[]) this.mapOfEntities.get(entity))[0].doubleValue() < min
					+ this.fuzziness) {
				map.put(entity, this.mapOfEntities.get(entity));
			}
		}

		return map;
	}

	/**
	 * Get low outliers (< 25th percentile or lower quartile) (no low outliers)
	 */
	public Map getLowValues() {

		final Map map = new HashMap();

		final Iterator iter = this.mapOfEntities.keySet().iterator();
		while (iter.hasNext()) {
			final Object entity = iter.next();
			if (((Double[]) this.mapOfEntities.get(entity))[0].doubleValue() < this.lowerQuartile
					+ this.fuzziness
					&& ((Double[]) this.mapOfEntities.get(entity))[0]
						.doubleValue() > this.minBound - this.fuzziness) {

				map.put(entity, this.mapOfEntities.get(entity));
			}
		}

		return map;
	}

	/**
	 * Get normal values (> 25th and < 75th percentile of lower quartile) + low outliers
	 */
	public Map getNormalValues() {
		final Map map = new HashMap();

		final Iterator iter = this.mapOfEntities.keySet().iterator();
		while (iter.hasNext()) {
			final Object entity = iter.next();
			if (((Double[]) this.mapOfEntities.get(entity))[0].doubleValue() >= this
				.getLowerQuartile()
					- this.fuzziness
					&& ((Double[]) this.mapOfEntities.get(entity))[0]
						.doubleValue() <= this.getUpperQuartile()
							+ this.fuzziness) {

				map.put(entity, this.mapOfEntities.get(entity));
			}
		}

		return map;
	}

	/**
	 * Get data without outliers. It is different
	 * from the normal values which are localized in the box.
	 */
	public Map getOutliersRemoved() {
		final Map map = new HashMap();

		final double min = this.getMinBound();
		final double max = this.getMaxBound();

		final Iterator iter = this.mapOfEntities.keySet().iterator();
		while (iter.hasNext()) {
			final Object entity = iter.next();
			if (((Double[]) this.mapOfEntities.get(entity))[0].doubleValue() >= min
					&& ((Double[]) this.mapOfEntities.get(entity))[0]
						.doubleValue() <= max) {
				map.put(entity, this.mapOfEntities.get(entity));
			}
		}

		return map;
	}

	/**
	 * Sort the values of the map and return a array of double
	 */
	private double[] sortEntities(final Map mapOfEntities) {

		final Object[] orderedValues = mapOfEntities.values().toArray();
		final double[] tab = new double[orderedValues.length];

		for (int i = 0; i < orderedValues.length; i++) {
			tab[i] = ((Double[]) orderedValues[i])[0].doubleValue();
		}
		Arrays.sort(tab);

		return tab;
	}
}
