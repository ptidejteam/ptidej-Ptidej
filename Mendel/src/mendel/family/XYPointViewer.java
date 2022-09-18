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
import java.util.Arrays;
import java.util.Random;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.statistics.Statistics;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class XYPointViewer extends ApplicationFrame {

	private static final double JITTER = 0.1;

	public static void main(final String[] args) {
        XYPointViewer viewer = new XYPointViewer("Family XY ScatterPoints", args[0]);
        viewer.go();
    }

	public XYPointViewer(String title, String filename) {
        super(title);
        
        final DefaultXYDataset dataset = buildDatasetFromCsv(filename);

        final NumberAxis yAxis = new NumberAxis("Family Deviation");
        final NumberAxis xAxis = new NumberAxis("Value");
        xAxis.setAutoRangeIncludesZero(true);
        yAxis.setAutoRangeIncludesZero(true);
        final XYItemRenderer renderer = setupRenderer();
        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);
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

    public DefaultXYDataset buildDatasetFromCsv(String filename) {
        DefaultXYDataset dataset = createDataset();
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
    
    public void go() {
		pack();
	    RefineryUtilities.centerFrameOnScreen(this);
	    setVisible(true);
	}

	
	/* (non-Javadoc)
	 * @see mendel.family.FamilyPlotViewer#createDataset()
	 */
	public DefaultXYDataset createDataset() {
		return new DefaultXYDataset();
	}

	/* (non-Javadoc)
	 * @see mendel.family.FamilyPlotViewer#fillDataset(org.jfree.data.category.CategoryDataset, java.lang.String[], java.lang.String[])
	 */
	public void fillDataset(DefaultXYDataset dataset, String[] classes,
			String[] data) {
		Double[] distances = new Double[data.length -1];
		double[][] list = new double[2][data.length-1];
		Random random = new Random();
		for (int k = 0; k < data.length-1 ; k++) {
			distances[k] = Double.valueOf(data[k+1]);
			list[0][k] = distances[k] + JITTER * (random.nextDouble() - .5);
		}
		Arrays.fill(list[1], Statistics.getStdDev(distances));
		dataset.addSeries(classes[0], list);
	}

	/* (non-Javadoc)
	 * @see mendel.family.FamilyPlotViewer#setupRenderer()
	 */
	public XYItemRenderer setupRenderer() {
		// XYBubbleRenderer
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(false, true);
        renderer.setBaseShapesFilled(false);
//        renderer.setDrawOutlines(true);
		renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());
        return renderer;
	}

}
