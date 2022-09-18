/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

package infovis;

import infovis.metadata.Constants;

import java.util.Map;

/**
 * Metadata interface and constants for qualifying columns and tables.
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public interface Metadata extends Constants {
    public Map getMetadata();
    public Map getClientPropery();
}
