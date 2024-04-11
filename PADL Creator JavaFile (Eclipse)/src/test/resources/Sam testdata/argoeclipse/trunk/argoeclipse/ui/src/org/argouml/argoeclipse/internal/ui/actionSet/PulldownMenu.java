// $Id: PulldownMenu.java,v 0.1.1 2006/10/15 00:00:00 b00__1 Exp $
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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowPulldownDelegate2;

/**
 * This class is the base class for all the dynamic pulldown menus in 
 * the actionSet that activates when a diagram editor is opened.
 * @author Bogdan Pistol
 */
public abstract class PulldownMenu implements IWorkbenchWindowPulldownDelegate2 {
    
    /**
     * The menu created by this action
     */
    private Menu menu;
    
    /**
     * The action used to render this delegate.
     */
    private IAction action;
    
    private static List instances = new ArrayList();
    
    private String menuName = ""; //$NON-NLS-1$
    
    protected IAction getActionDelegate() {
        return action;
    }
    
    protected Menu getMenu() {
        return menu;
    }
    
    public PulldownMenu(String name) {
        menuName = name;
        instances.add(this);
    }

    public Menu getMenu(Menu parent) {
        setMenu(new Menu(parent));
        return menu;
    }

    public Menu getMenu(Control parent) {
        setMenu(new Menu(parent));
        return menu;
    }
    
    private void setMenu(Menu m) {
        if (menu != null) {
            menu.dispose();
        }
        menu = m;
    }
    
    /**
     * Adds the given action to the specified menu.
     * @param menu the menu to add the action to
     * @param action the action to add
     */
    protected void addToMenu(Menu menu, IAction action) {
        ActionContributionItem item = new ActionContributionItem(action);
        item.fill(menu, -1);
    }
    
    /**
     * Adds a separator to the given menu
     * @param m the menu
     */
    protected void addSeparator(Menu m) {
        new MenuItem(m, SWT.SEPARATOR);
    }
    
    /**
     * Initialize the menus. This should be done after ArgoEclipse plugin is
     * loaded.
     */
    public static void initMenus() {
        for (int i = 0; i < instances.size(); i++) {
            ((PulldownMenu) instances.get(i)).initMenu();
        }
    }
    
    /**
     * Init this menu.
     */
    protected abstract void initMenu();

    public void dispose() {
        setMenu(null);
        instances.remove(this);
    }

    public void init(IWorkbenchWindow window) {
    }

    public void run(IAction action) {
    }

    public void selectionChanged(IAction newAction, ISelection selection) {
        action = newAction;
        action.setText(menuName);
    }

}
