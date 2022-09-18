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
import java.util.HashMap;
import java.util.Map;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBubbleRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.statistics.Statistics;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class XYBubbleViewer extends ApplicationFrame {

	public static void main(final String[] args) {
        XYBubbleViewer viewer = new XYBubbleViewer("Family XY ScatterPoints", args[0]);
        viewer.go();
    }

	public XYBubbleViewer(String title, String filename) {
        super(title);
        
        final DefaultXYZDataset dataset = buildDatasetFromCsv(filename);

        final NumberAxis yAxis = new NumberAxis("Family Deviation");
        final NumberAxis xAxis = new NumberAxis("Value");
        yAxis.setAutoRangeIncludesZero(true);
        xAxis.setAutoRangeIncludesZero(true);
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

    public DefaultXYZDataset buildDatasetFromCsv(String filename) {
        DefaultXYZDataset dataset = createDataset();
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
	public DefaultXYZDataset createDataset() {
		return new DefaultXYZDataset();
	}

	/* (non-Javadoc)
	 * @see mendel.family.FamilyPlotViewer#fillDataset(org.jfree.data.category.CategoryDataset, java.lang.String[], java.lang.String[])
	 */
	public void fillDataset(DefaultXYZDataset dataset, String[] classes,
			String[] data) {
		Map<Double, Integer> count = new HashMap<Double, Integer>();
		Double[] distances = new Double[data.length -1];
		for (int k = 0; k < data.length-1 ; k++) {
			distances[k] = Double.valueOf(data[k+1]);
			inc(count, distances[k]);
		}
		int size = count.size();
		double[][] list = new double[3][size];
		int i = 0;
		for (Double index : count.keySet()) {
			list[0][i] = index;
			list[2][i] = count.get(index) / 100.0;
			i++;
		}
		Arrays.fill(list[1], Statistics.getStdDev(distances));
		dataset.addSeries(classes[0], list);
	}

	/**
	 * @param count
	 * @param double1
	 */
	public void inc(Map<Double, Integer> count, Double double1) {
		if( !count.containsKey(double1) )
			count.put(double1, 0);
		count.put(double1, count.get(double1) + 1);
	}

	/* (non-Javadoc)
	 * @see mendel.family.FamilyPlotViewer#setupRenderer()
	 */
	public XYBubbleRenderer setupRenderer() {
		XYBubbleRenderer renderer = new XYBubbleRenderer(XYBubbleRenderer.SCALE_ON_RANGE_AXIS);
//        renderer.setBaseShapesFilled(false);
//        renderer.setDrawOutlines(true);
		renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());
        return renderer;
	}

}
