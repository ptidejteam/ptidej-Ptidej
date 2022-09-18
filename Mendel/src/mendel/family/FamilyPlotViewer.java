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

import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public abstract class FamilyPlotViewer extends ApplicationFrame {

    public FamilyPlotViewer(String title, String filename) {
        super(title);
        
        final CategoryDataset dataset = buildDatasetFromCsv(filename);

        final CategoryAxis xAxis = new CategoryAxis("Family");
        xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
        final NumberAxis yAxis = new NumberAxis("Value");
        yAxis.setAutoRangeIncludesZero(true);
        final CategoryItemRenderer renderer = setupRenderer();
        final CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);
        final JFreeChart chart = new JFreeChart(
            filename,
            new Font("SansSerif", Font.BOLD, 14),
            plot,
            true
        );
        final ChartPanel chartPanel = new ChartPanel(chart);
//        chartPanel.setPreferredSize(new java.awt.Dimension(450, 270));
        setContentPane(chartPanel);
    }

	public abstract CategoryItemRenderer setupRenderer();

    public CategoryDataset buildDatasetFromCsv(String filename) {
        CategoryDataset dataset = createDataset();
		try {
			BufferedReader input = new BufferedReader( new FileReader(filename) );	
			String classLine = input.readLine();
			String dataLine = input.readLine();
						
			while (classLine!=null) {
				String[] classes = classLine.split(",");
				String[] data = dataLine.split(",");
				// csv data: one line = dataset for one family
				fillDataset(dataset, classes, data);
				classLine = input.readLine();
				dataLine = input.readLine();
			}
			
			input.close();
			
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}		        

        return dataset;
    }

	public abstract void fillDataset(CategoryDataset dataset, String[] classes, String[] data);
//	{
//		final List<Double> list = new ArrayList<Double>(data.length-1);
//		for (int k = 1; k < data.length; k++) {
//			list.add(Double.valueOf(data[k]));
//		}
//		dataset.add(list, data[0], classes[0]);
//	}

	public abstract CategoryDataset createDataset();
    
    public void go() {
		pack();
	    RefineryUtilities.centerFrameOnScreen(this);
	    setVisible(true);
	}

}
