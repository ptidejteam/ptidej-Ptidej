/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.visualization;

/**
 * Interface Orientable
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public interface Orientable {
    /** Orientation towards north */
    public static final int ORIENTATION_NORTH = 0;
    /** Orientation towards south*/
    public static final int ORIENTATION_SOUTH = 1;
    /** Orientation towards east */
    public static final int ORIENTATION_EAST = 2;
    /** Orientation towards west */
    public static final int ORIENTATION_WEST = 3;
    
    public int getOrientation();
    public void setOrientation(int orientation);

}
