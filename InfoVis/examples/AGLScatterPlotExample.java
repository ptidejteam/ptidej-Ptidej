import infovis.Table;
import infovis.io.AbstractReader;
import infovis.table.DefaultTable;
import infovis.table.io.CSVTableReader;
import infovis.table.io.TableReaderFactory;

import java.util.Locale;

import agile2d.AgileJFrame;


/**
 * Example of use of Agile2D with the infovis toolkit.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class AGLScatterPlotExample extends AgileJFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Syntax: AGLScatterPlotExample <file>");
            System.exit(1);
        }
        Locale.setDefault(Locale.US);

        Table          t = new DefaultTable();
        AbstractReader reader = TableReaderFactory.createReader(args[0], t);
        if (reader instanceof CSVTableReader) {
            CSVTableReader csvreader = (CSVTableReader)reader;
            csvreader.setSeparator(';');
            csvreader.setConsideringQuotes(false);
        }
        if (reader.load()) {
            AgileJFrame frame = new AgileJFrame("AGLScatterPlotExample");
            ScatterPlotExample.create(frame, t);
            frame.setVisible(true);
            frame.pack();
        } else {
            System.err.println("cannot load  " + args[0]);
        }
    }
}
