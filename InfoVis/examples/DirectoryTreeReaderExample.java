import infovis.Tree;
import infovis.table.io.CSVTableWriter;
import infovis.tree.DefaultTree;
import infovis.tree.io.DirectoryTreeReader;

import java.io.File;
import java.io.OutputStreamWriter;

/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

/**
 * Class DirectoryTreeReaderExample
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class DirectoryTreeReaderExample {

    public static void main(String args[]) {
        if (args.length < 1 || args.length > 4) {
            System.err.println("Syntax: [-l] [-t] [-s=c] <file>");
            System.exit(1);
        }
        File file = new File(args[args.length - 1]);
        Tree table = new DefaultTree();
        DirectoryTreeReader reader;
        CSVTableWriter writer =
            new CSVTableWriter(
                new OutputStreamWriter(System.out),
                table);
        reader = new DirectoryTreeReader(file.getAbsolutePath(), table);

        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equals("-l")) {
                //reader.setLabelLinePresent(true);
                writer.setLabelLinePresent(true);
            }
            else if (args[i].equals("-t")) {
                //reader.setTypeLinePresent(true);
                writer.setTypeLinePresent(true);
            }
            else if (args[i].startsWith("-s=")) {
                //reader.setSeparator(args[i].charAt(3));
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
