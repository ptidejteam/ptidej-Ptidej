/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.panel;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public abstract class DefaultAction extends AbstractAction {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor for DefaultAction.
     * @param name
     */
    public DefaultAction(String name, int mnemonic) {
        super(name);
        putValue(Action.MNEMONIC_KEY, Integer.valueOf(mnemonic));
    }


}
