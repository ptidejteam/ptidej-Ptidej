/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

import infovis.table.DefaultTable;
import infovis.table.io.TQDTableReader;

import java.io.File;

import agile2d.AgileJFrame;

/**
 * Example of use of Agile2D with the infovis toolkit.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class AGLTimeSeriesExample extends AgileJFrame {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String args[]) {
	if (args.length != 1) {
	    System.err.println("Syntax: AGLTimeSeriesExample <file>");
	    System.exit(1);
	}
	File file = new File(args[0]);
	if (! file.exists()) {
	    System.err.println("file "+args[0]+" doesn't exist");
	    System.exit(1);
	}
	DefaultTable t = new DefaultTable();
	TQDTableReader.load(file, t);
	AgileJFrame frame = new AgileJFrame("AGLTimeSeriesExample");
	TimeSeriesExample.create(frame, t);
	frame.setVisible(true);
	frame.pack();
    }

}
