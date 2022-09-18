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

import org.jfree.chart.labels.BoxAndWhiskerToolTipGenerator;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;

public class BoxPlotViewer extends FamilyPlotViewer {

    public BoxPlotViewer(String title, String filename) {
        super(title, filename);
    }

    public static void main(final String[] args) {
        BoxPlotViewer viewer = new BoxPlotViewer("Family Boxplots", args[0]);
        viewer.go();
    }

	/* (non-Javadoc)
	 * @see mendel.family.FamilyPlotViewer#createDataset()
	 */
	@Override
	public CategoryDataset createDataset() {
		return new DefaultBoxAndWhiskerCategoryDataset();
	}

	/* (non-Javadoc)
	 * @see mendel.family.FamilyPlotViewer#fillDataset(org.jfree.data.category.CategoryDataset, java.lang.String[], java.lang.String[])
	 */
	@Override
	public void fillDataset(CategoryDataset dataset, String[] classes,
			String[] data) {
		DefaultBoxAndWhiskerCategoryDataset set = (DefaultBoxAndWhiskerCategoryDataset) dataset;
		final List<Double> list = new ArrayList<Double>(data.length-1);
		for (int k = 1; k < data.length; k++) {
			list.add(Double.valueOf(data[k]));
		}
		set.add(list, data[0], classes[0]);		
	}

	/* (non-Javadoc)
	 * @see mendel.family.FamilyPlotViewer#setupRenderer()
	 */
	@Override
	public CategoryItemRenderer setupRenderer() {
        final BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
        renderer.setFillBox(true);
        renderer.setBaseToolTipGenerator(new BoxAndWhiskerToolTipGenerator());
        return renderer;
	}

}
