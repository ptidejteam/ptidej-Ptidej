/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
import infovis.Table;
import infovis.Visualization;
import infovis.column.StringColumn;
import infovis.io.AbstractReader;
import infovis.panel.ControlPanel;
import infovis.panel.DynamicQueryPanel;
import infovis.panel.NumberColumnBoundedRangeModel;
import infovis.table.DefaultTable;
import infovis.table.io.TableReaderFactory;
import infovis.table.visualization.ScatterPlotVisualization;

import java.util.Locale;

import javax.swing.JFrame;


/**
 * Example of scatter plot visualization.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.2 $
 */
public class ScatterPlotExample {
    /**
     * Create a Scatter Plot BasicVisualization inside a specified JFrame
     * on the specified Table.
     *
     * @param frame the JFrame.
     * @param t the Table.
     */
    public static void create(JFrame frame, Table t) {
        ScatterPlotVisualization visualization = new ScatterPlotVisualization(t);
        visualization.setShowingLabel(false);

        visualization.setVisualColumn(Visualization.VISUAL_LABEL, getStringColumn(t, 0));

        ControlPanel control = (ControlPanel)visualization.createDefaultControls();

        // Connect dynamic query components to XAxis and YAxis components of
        // scatter plot to also control the scaling.
        DynamicQueryPanel jquery = control.getDynamicQueryPanel();

        // CHECK++
        visualization.setXAxisModel((NumberColumnBoundedRangeModel)jquery.getColumnDynamicQuery(visualization.getXAxisColumn()));
        visualization.setYAxisModel((NumberColumnBoundedRangeModel)jquery.getColumnDynamicQuery(visualization.getYAxisColumn()));

        frame.getContentPane().add(control);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Return the nth string column in the table.
     *
     * @param t the table.
     * @param index index of the desired string column.
     *
     * @return the nth string column in the table
     * or <code>null</code> if none exist.
     */
    public static StringColumn getStringColumn(Table t, int index) {
        StringColumn ret = null;
        for (int i = 0; i < t.getColumnCount(); i++) {
            ret = StringColumn.getColumn(t, i);
            if (ret != null && !ret.isInternal() && index-- == 0)
                return ret;
        }
        return null;
    }

    /**
     * Main example program for Scatter Plot BasicVisualization.
     *
     * @param args argument strings.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Syntax: ScatterPlotExample <name>");
            System.exit(1);
        }
        Locale.setDefault(Locale.US);

        Table          t = new DefaultTable();
        AbstractReader reader = TableReaderFactory.createReader(args[0], t);
//        if (reader instanceof CSVTableReader) {
//            CSVTableReader csvreader = (CSVTableReader) reader;
//            csvreader.setSeparator(';');
//            csvreader.setConsideringQuotes(false);
//        }
        if (reader.load()) {
            JFrame frame = new JFrame("ScatterPlotExample");
            create(frame, t);
            frame.setVisible(true);
            frame.pack();
        } else {
            System.err.println("cannot load  " + args[0]);
        }
    }
}
