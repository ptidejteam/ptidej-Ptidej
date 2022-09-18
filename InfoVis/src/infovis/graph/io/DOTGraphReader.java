/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.graph.io;

import infovis.Graph;
import infovis.io.AbstractTableReader;
import infovis.io.WrongFormatException;

import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.IOException;

import antlr.RecognitionException;
import antlr.TokenStreamException;

/**
 * Class DOTGraphReader
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class DOTGraphReader extends AbstractTableReader {
    protected DOTLexer lexer;
    protected DOTParser parser;
    protected Graph graph;
    protected boolean updating; 
    public static final String DOT_POS_COLUMN = "#dot_pos";
    public static final String DOT_WIDTH_COLUMN = "#dot_width";
    public static final String DOT_HEIGHT_COLUMN = "#dot_height";

    public DOTGraphReader(
        BufferedReader in,
        String name,
        Graph graph) {
        super(in, name, graph.getEdgeTable());
        this.graph = graph;
    }

    public boolean isUpdating() {
        return this.updating;
    }

    public void setUpdating(boolean set) {
        this.updating = set;
    }
    
    public Rectangle2D.Float getBbox() {
        return this.parser.getBbox();
    }

    public boolean load() throws WrongFormatException {
        this.lexer = new DOTLexer(this.in);
        this.parser = new DOTParser(this.lexer);
        this.parser.setGraph(this.graph);
        this.parser.setUpdating(this.updating);
        try {
            this.parser.graph();
        }
        catch (TokenStreamException e) {
            e.printStackTrace();
            return false;
        }
        catch (RecognitionException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            try {
                this.in.close();
            }
            catch (IOException e) {
            }
        }
        return true;
    }

}
