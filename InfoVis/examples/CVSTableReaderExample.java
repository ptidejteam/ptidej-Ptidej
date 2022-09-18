import infovis.table.DefaultTable;
import infovis.table.io.CSVTableReader;
import infovis.table.io.CSVTableWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.OutputStreamWriter;

/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

/**
 * Class CVSTableReaderExample
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.2 $
 */
public class CVSTableReaderExample {

    public static void main(String args[]) {
        if (args.length < 1 || args.length > 4) {
            System.err.println("Syntax: [-l] [-t] [-s=c] <file>");
            System.exit(1);
        }
        File file = new File(args[args.length - 1]);
        DefaultTable table = new DefaultTable();
        CSVTableReader reader;
        CSVTableWriter writer =
            new CSVTableWriter(
                new OutputStreamWriter(System.out),
                table);
        try {
            reader =
                new CSVTableReader(
                    new BufferedReader(new FileReader(file)),
                    table);
        }
        catch (FileNotFoundException e) {
            System.err.println(
                "file " + args[args.length - 1] + " doesn't exist");
            System.exit(1);
            return;
        }

        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equals("-l")) {
                reader.setLabelLinePresent(true);
                writer.setLabelLinePresent(true);
            }
            else if (args[i].equals("-t")) {
                reader.setTypeLinePresent(true);
                writer.setTypeLinePresent(true);
            }
            else if (args[i].startsWith("-s=")) {
                reader.setSeparator(args[i].charAt(3));
                writer.setSeparator(args[i].charAt(3));
            }
            else {
                System.err.println("unkonwn option " + args[i]);
                System.err.println("Syntax: [-l] [-t] <file>");
            }
        }

        if (reader.load()) {
            System.out.println("DefaultTable loaded successfully");
        }
        else {
            System.out.println("DefaultTable not loaded successfully");
        }
        writer.write();

    }

}
