// $Id: Select.java,v 0.1.1 2006/10/15 00:00:00 b00__1 Exp $
// Copyright (c) 2006 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies. This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason. IN NO EVENT SHALL THE
// UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
// SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
// ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
// THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
// PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT,
// UPDATES, ENHANCEMENTS, OR MODIFICATIONS.

package org.argouml.argoeclipse.internal.ui.actionSet;

import org.argouml.argoeclipse.internal.ui.model.Actions;
import org.argouml.i18n.Translator;


/**
 * Select menu from Edit menu
 * @author Bogdan Pistol
 */
public class Select extends PulldownMenu {
    
    public Select() {
        super("Ar&go " + Translator.localize("menu.select")); //$NON-NLS-1$ //$NON-NLS-2$
    }

    protected void initMenu() {
        addToMenu(getMenu(), Actions.getInstance().getActionSelectAll());
        addSeparator(getMenu());
        addToMenu(getMenu(), Actions.getInstance().getActionNavigateBack());
        addToMenu(getMenu(), Actions.getInstance().getActionNavigateForward());
        addSeparator(getMenu());
        addToMenu(getMenu(), Actions.getInstance().getActionInvertSelection());
    }

}
