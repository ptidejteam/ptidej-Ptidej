import infovis.Graph;
import infovis.graph.DefaultGraph;
import infovis.graph.io.DOTGraphReader;
import infovis.graph.io.GraphMLWriter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

/**
 * Class DOTGraphReaderExample
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.2 $
 */
public class DOTGraphReaderExample {

    /**
     * DOCUMENT ME!
     *
     * @param args DOCUMENT ME!
     */
    public static void main(String[] args) {
        String fname = args[0];
        try {
            FileReader fin = new FileReader(fname);
            BufferedReader in = new BufferedReader(fin);
            Graph graph = new DefaultGraph();
            int offset1 = fname.lastIndexOf('/') + 1;
            int offset2 = fname.indexOf('.', offset1);
            String name;
            if (offset2 == -1)
                name = fname.substring(offset1);
            else {
                if ((offset2 - offset1) > 4)
                    offset1 += 4;

                name = fname.substring(offset1, offset2).toLowerCase();
            }
            DOTGraphReader reader = new DOTGraphReader(in, name, graph);
            if (reader.load()) {
                Writer out;
                if (args.length == 2) {
                    try {
                        FileWriter fout = new FileWriter(args[1]);

                        out = new BufferedWriter(fout);
                    }
                    catch (IOException e) {
                        out = new OutputStreamWriter(System.out);
                    }
                }
                else {
                    out = new OutputStreamWriter(System.out);
                }
                GraphMLWriter writer = new GraphMLWriter(out, graph);
                writer.write();
            }
        }
        catch (FileNotFoundException e) {
        }
    }
}
