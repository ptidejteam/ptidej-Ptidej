/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.tree.visualization.treemap;

/**
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class Strip extends Squarified {

    /**
     * Constructor for Strip.
     */
    public Strip() {
    }
    
    /**
     * @see infovis.tree.visualization.treemap.Squarified#getName()
     */
    public String getName() {
        return "Strip";
    }

    /**
     * @see infovis.tree.visualization.treemap.Squarified#isVertical(float, float, float, float, int)
     */
    protected boolean isVertical(
        float xmin,
        float ymin,
        float xmax,
        float ymax,
        int node) {
        return false;
    }

}
