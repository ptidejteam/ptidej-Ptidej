/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

import infovis.Visualization;
import infovis.panel.ControlPanel;
import infovis.table.DefaultTable;
import infovis.table.io.TQDTableReader;
import infovis.table.visualization.TimeSeriesVisualization;

import java.io.File;

import javax.swing.JFrame;

/**
 * Example of time series visualization.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */

public class TimeSeriesExample {
    public static void create(JFrame frame, DefaultTable t) {
	TimeSeriesVisualization visualization = new TimeSeriesVisualization(t);
		
        visualization.setVisualColumn(Visualization.VISUAL_LABEL, t.getColumnAt(0));
	ControlPanel control = (ControlPanel)visualization.createDefaultControls();
		
	frame.getContentPane().add(control);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
	
    public static void main(String args[]) {
	if (args.length != 1) {
	    System.err.println("Syntax: TimeSeriesExample <file>");
	    System.exit(1);
	}
	File file = new File(args[0]);
	if (! file.exists()) {
	    System.err.println("file "+args[0]+" doesn't exist");
	    System.exit(1);
	}
	DefaultTable t = new DefaultTable();
	TQDTableReader.load(file, t);
	JFrame frame = new JFrame("TimeSeriesExample");
	create(frame, t);
		
	frame.setVisible(true);
	frame.pack();
    }

}
