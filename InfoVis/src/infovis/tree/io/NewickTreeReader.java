/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.tree.io;

import infovis.Tree;

import java.io.BufferedReader;
import java.io.IOException;

import antlr.RecognitionException;
import antlr.TokenStreamException;


/**
 * Read Trees in Newick format, usually for phylogenetic trees.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class NewickTreeReader extends AbstractTreeReader {
    /**
     * Constructor for NewickTreeReader.
     * @param in
     * @param name
     * @param tree
     */
    public NewickTreeReader(BufferedReader in, String name, Tree tree) {
        super(in, name, tree);
    }

    /**
     * @see infovis.io.AbstractTableReader#load()
     */
    public boolean load() {
        NewickLexer  lexer = new NewickLexer(this.in);
        NewickParser parser = new NewickParser(lexer);
        parser.setTree(this.tree);
        parser.setName(getName());
        try {
            parser.tree();
        } catch (TokenStreamException e) {
            e.printStackTrace();
            return false;
        } catch (RecognitionException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                this.in.close();
            } catch (IOException e) {
            }
        }
        return true;
    }

}
