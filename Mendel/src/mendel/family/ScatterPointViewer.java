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
package mendel.family;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.ScatterRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.statistics.DefaultMultiValueCategoryDataset;

public class ScatterPointViewer extends FamilyPlotViewer {

	private static final double JITTER = 0.1;

	public static void main(final String[] args) {
        FamilyPlotViewer viewer = new ScatterPointViewer("Family ScatterPoints", args[0]);
        viewer.go();
    }

	public ScatterPointViewer(String title, String filename) {
		super(title, filename);
	}

	/* (non-Javadoc)
	 * @see mendel.family.FamilyPlotViewer#createDataset()
	 */
	@Override
	public CategoryDataset createDataset() {
		return new DefaultMultiValueCategoryDataset();
	}

	/* (non-Javadoc)
	 * @see mendel.family.FamilyPlotViewer#fillDataset(org.jfree.data.category.CategoryDataset, java.lang.String[], java.lang.String[])
	 */
	@Override
	public void fillDataset(CategoryDataset dataset, String[] classes,
			String[] data) {
		DefaultMultiValueCategoryDataset set = (DefaultMultiValueCategoryDataset) dataset;
		final List<Double> list = new ArrayList<Double>(data.length-1);
		Random random = new Random();
		for (int k = 1; k < data.length; k++) {
			list.add(Double.valueOf(data[k]) + JITTER * (random.nextDouble() - .5));
		}
		set.add(list, data[0], classes[0]);
	}

	/* (non-Javadoc)
	 * @see mendel.family.FamilyPlotViewer#setupRenderer()
	 */
	@Override
	public CategoryItemRenderer setupRenderer() {
        ScatterRenderer renderer = new ScatterRenderer();
        renderer.setBaseShapesFilled(false);
        renderer.setDrawOutlines(true);
        renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
        return renderer;
	}

}
