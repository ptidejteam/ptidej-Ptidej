// $Id: ApplicationActionBarAdvisor.java 86 2006-07-22 21:46:41Z tfmorris $
// Copyright (c) 2006 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies.  This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason.  IN NO EVENT SHALL THE
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

package org.argouml.argoeclipse.internal.rcp;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

/**
 * Configures the action bars.
 * @author Tom Morris
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

    private IWorkbenchAction newAction;
    private IWorkbenchAction printAction;
    
    private IWorkbenchAction copyAction;
    private IWorkbenchAction cutAction;
    private IWorkbenchAction pasteAction;
    private IWorkbenchAction undoAction;

    private IWorkbenchAction perspectiveAction;
    private IWorkbenchAction viewAction;

    private IWorkbenchAction aboutAction;
    private IWorkbenchAction introAction;
    private IWorkbenchAction helpContentsAction;
    private IWorkbenchAction helpSearchAction;
    
    /**
     * Constructor
     * @param configurer special class to configure the base class
     */
    public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }

    /*
     * @see org.eclipse.ui.application.ActionBarAdvisor#makeActions(org.eclipse.ui.IWorkbenchWindow)
     */
    protected void makeActions(IWorkbenchWindow window) {
        newAction = ActionFactory.NEW.create(window);
        register(newAction);
        printAction = ActionFactory.PRINT.create(window);
        register(printAction);

        copyAction = ActionFactory.COPY.create(window);
        register(copyAction);
        cutAction = ActionFactory.CUT.create(window);
        register(cutAction);
        pasteAction = ActionFactory.PASTE.create(window);
        register(pasteAction);
        undoAction = ActionFactory.UNDO.create(window);
        register(undoAction);
        
        perspectiveAction = ActionFactory.OPEN_PERSPECTIVE_DIALOG
                .create(window);
        register(perspectiveAction);
        viewAction = ActionFactory.OPEN_NEW_WINDOW.create(window);
        register(viewAction);

        aboutAction = ActionFactory.ABOUT.create(window);
        register(aboutAction);
        introAction = ActionFactory.INTRO.create(window);
        register(introAction);
        helpContentsAction = ActionFactory.HELP_CONTENTS.create(window);
        register(helpContentsAction);
        helpSearchAction = ActionFactory.HELP_SEARCH.create(window);
        register(helpSearchAction);
    }

    /*
     * @see org.eclipse.ui.application.ActionBarAdvisor#fillMenuBar(org.eclipse.jface.action.IMenuManager)
     */
    protected void fillMenuBar(IMenuManager menuBar) {
        MenuManager fileMenu = new MenuManager(MessageBundle.fileMenu,
                IWorkbenchActionConstants.M_FILE);
        menuBar.add(fileMenu);

        fileMenu.add(newAction);
        fileMenu.add(new Separator(IWorkbenchActionConstants.PRINT_EXT));
        fileMenu.appendToGroup(IWorkbenchActionConstants.PRINT_EXT, 
                printAction);
        
        MenuManager editMenu = new MenuManager(MessageBundle.editMenu,
                IWorkbenchActionConstants.M_EDIT);
        menuBar.add(editMenu);
        editMenu.add(undoAction);
        editMenu.add(new Separator(IWorkbenchActionConstants.EDIT_START));
        editMenu.appendToGroup(IWorkbenchActionConstants.EDIT_START, 
                copyAction);
        editMenu.appendToGroup(IWorkbenchActionConstants.EDIT_START, cutAction);
        editMenu.appendToGroup(IWorkbenchActionConstants.EDIT_START, 
                pasteAction);
        editMenu.add(new Separator(IWorkbenchActionConstants.EDIT_END));
        
        MenuManager windowMenu = new MenuManager(MessageBundle.windowMenu,
                IWorkbenchActionConstants.M_WINDOW);
        menuBar.add(windowMenu);
        windowMenu.add(perspectiveAction);
        windowMenu.add(viewAction);

        // Help
        MenuManager helpMenu = new MenuManager(MessageBundle.helpMenu,
                IWorkbenchActionConstants.M_HELP);
        menuBar.add(helpMenu);
        helpMenu.add(introAction);
        helpMenu.add(new Separator());
        helpMenu.add(helpContentsAction);
        helpMenu.add(helpSearchAction);
        helpMenu.add(new Separator());
        helpMenu.add(aboutAction);
    }

}
