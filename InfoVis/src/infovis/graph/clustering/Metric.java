/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.graph.clustering;

/**
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public interface Metric {
    public float distance(int[] row1, int[] row2);
}
