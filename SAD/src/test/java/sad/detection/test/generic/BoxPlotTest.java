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
package sad.detection.test.generic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Assert;

import junit.framework.TestCase;
import padl.kernel.impl.Factory;
import sad.util.BoxPlot;

/**
 * Test for the BoxPlot
 * See rsc/BoxPlot Explained.pdf for the choice of 
 * dataset and the expected values tested.
 */
public final class BoxPlotTest extends TestCase {
	// 
	private static final Map Data1 = new HashMap();
	private static final Map Data2 = new HashMap();
	private static final double[] DoubleValues1 = new double[] { 11.0, 11.2,
			11.5, 11.6, 11.9, 12.0, 12.2, 12.8, 12.9, 12.9, 13.1, 13.3, 13.4,
			13.8, 13.9, 14.2, 14.5, 14.5, 14.6, 15.3, 15.5, 16.0 };
	private static final double[] DoubleValues2 = new double[] { 11.0, 11.2,
			11.5, 11.6, 11.9, 12.0, 12.2, 12.8, 12.9, 12.9, 13.1, 13.3, 13.4,
			13.8, 13.9, 14.2, 14.5, 14.5, 14.6, 15.3, 15.5, 19.0 };
	static {
		//	for (int i = 0; i < 10; i++) {
		//		Data.put(
		//			Factory.getUniqueInstance().createGhost(
		//				Integer.toString(i)),
		//			Integer.valueOf(i));
		//	}
		for (int i = 0; i < BoxPlotTest.DoubleValues1.length; i++) {
			final char[] id = (Double.toString(BoxPlotTest.DoubleValues1[i])
					+ " (" + i + ')').toCharArray();
			BoxPlotTest.Data1.put(Factory.getInstance().createGhost(id, id),
					new Double[] { Double.valueOf(BoxPlotTest.DoubleValues1[i]),
							Double.valueOf(0) });
		}
		for (int i = 0; i < BoxPlotTest.DoubleValues2.length; i++) {
			final char[] id = (Double.toString(BoxPlotTest.DoubleValues2[i])
					+ " (" + i + ')').toCharArray();
			BoxPlotTest.Data2.put(Factory.getInstance().createGhost(id, id),
					new Double[] { Double.valueOf(BoxPlotTest.DoubleValues2[i]),
							Double.valueOf(0) });
		}
	}

	private static double getHighestValue(final Map someData) {
		double maxValue = Double.MIN_VALUE;
		final Iterator iterator = someData.values().iterator();
		while (iterator.hasNext()) {
			final Double[] d = (Double[]) iterator.next();
			final double value = d[0].doubleValue();
			if (value > maxValue) {
				maxValue = value;
			}
		}
		return maxValue;
	}

	private static double getLowestValue(final Map someData) {
		double minValue = Double.MAX_VALUE;
		final Iterator iterator = someData.values().iterator();
		while (iterator.hasNext()) {
			final Double[] d = (Double[]) iterator.next();
			final double value = d[0].doubleValue();
			if (value < minValue) {
				minValue = value;
			}
		}
		return minValue;
	}

	public BoxPlotTest(final String name) {
		super(name);
	}

	protected void setUp() {
	}

	public void testCurrentHigherValue() {
		final BoxPlot boxPlot = new BoxPlot(BoxPlotTest.Data1, 0.0);
		Assert.assertEquals("Higher value", 16.0,
				BoxPlotTest.getHighestValue(boxPlot.getHighValues()), 0.0);
	}

	public void testCurrentHigherValue2() {
		final BoxPlot boxPlot = new BoxPlot(BoxPlotTest.Data2, 0.0);
		Assert.assertEquals("Higher value", 15.5,
				BoxPlotTest.getHighestValue(boxPlot.getHighValues()), 0.0);
	}

	public void testCurrentHighValues() {
		final BoxPlot boxPlot = new BoxPlot(BoxPlotTest.Data1, 0.0);
		Assert.assertEquals("Number of high values", 4,
				boxPlot.getHighValues().size());
	}

	public void testCurrentIQR() {
		final BoxPlot boxPlot = new BoxPlot(BoxPlotTest.Data1, 0.0);
		Assert.assertEquals("IQR", 2.5, boxPlot.getInterQuartileRange(), 0.0);
	}

	public void testCurrentLowerQuartile() {
		final BoxPlot boxPlot = new BoxPlot(BoxPlotTest.Data1, 0.0);
		Assert.assertEquals("Lower quartile", 12.0, boxPlot.getLowerQuartile(),
				0.0);
	}

	public void testCurrentLowerValue() {
		final BoxPlot boxPlot = new BoxPlot(BoxPlotTest.Data1, 0.0);
		Assert.assertEquals("Lower value", 11.0,
				BoxPlotTest.getLowestValue(boxPlot.getLowValues()), 0.0);
	}

	public void testCurrentLowValues() {
		final BoxPlot boxPlot = new BoxPlot(BoxPlotTest.Data1, 0.0);
		Assert.assertEquals("Number of low values", 5,
				boxPlot.getLowValues().size());
	}

	public void testCurrentMaxBound() {
		final BoxPlot boxPlot = new BoxPlot(BoxPlotTest.Data1, 0.0);
		Assert.assertEquals("Max bound", 18.25, boxPlot.getMaxBound(), 0.0);
	}

	public void testCurrentMedian() {
		final BoxPlot boxPlot = new BoxPlot(BoxPlotTest.Data1, 0.0);
		Assert.assertEquals("Median", 13.2, boxPlot.getMedian(), 0.0);
	}

	public void testCurrentMinBound() {
		final BoxPlot boxPlot = new BoxPlot(BoxPlotTest.Data1, 0.0);
		Assert.assertEquals("Min bound", 8.25, boxPlot.getMinBound(), 0.0);
	}

	public void testCurrentNormalValues() {
		final BoxPlot boxPlot = new BoxPlot(BoxPlotTest.Data1, 0.0);
		Assert.assertEquals("Number of number values", 13,
				boxPlot.getNormalValues().size());
	}

	public void testCurrentOutliers() {
		final BoxPlot boxPlot = new BoxPlot(BoxPlotTest.Data1, 0.0);
		Assert.assertEquals("Number of outliers", 0,
				boxPlot.getHighOutliers().size());
	}

	public void testCurrentOutliers2() {
		final BoxPlot boxPlot = new BoxPlot(BoxPlotTest.Data2, 0.0);
		Assert.assertEquals("Number of outliers", 1,
				boxPlot.getHighOutliers().size());
	}

	public void testCurrentUpperQuartile() {
		final BoxPlot boxPlot = new BoxPlot(BoxPlotTest.Data1, 0.0);
		Assert.assertEquals("Higher quartile", 14.5, boxPlot.getUpperQuartile(),
				0.0);
	}

	public void testEmptyData() {
		final BoxPlot boxPlot = new BoxPlot(new HashMap(), 0.0);
		Assert.assertEquals("Higher quartile", 0.0, boxPlot.getUpperQuartile(),
				0.0);
	}
}
