// $Id: DiagramActionBarContributor.java 2006/06/22 00:00:00 b00__1 $
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

package org.argouml.argoeclipse.internal.ui.editor;

import org.eclipse.ui.part.EditorActionBarContributor;

/**
 * This deals with the menus and toolbar available when the editor is selected.
 * 
 * @author Bogdan Pistol
 */
public class DiagramActionBarContributor extends EditorActionBarContributor {
//
//    /*
//     * @see org.eclipse.ui.part.EditorActionBarContributor#contributeToMenu(org.eclipse.jface.action.IMenuManager)
//     */
//    public void contributeToMenu(IMenuManager menuManager) {
//        super.contributeToMenu(menuManager);
//        Actions actionsModel = Actions.getInstance();
//
//        // File menu - integration points: menu M_FILE, group PRINT_EXT
//        IMenuManager fileMenu = menuManager
//                .findMenuUsingPath(IWorkbenchActionConstants.M_FILE);
//        fileMenu.prependToGroup(IWorkbenchActionConstants.PRINT_EXT,
//                actionsModel.getActionPageSetup());
//        fileMenu.insertAfter(ActionFactory.PROPERTIES.getId(), actionsModel
//                .getActionSaveConfiguration());
//
//        // Edit menu - integration points M_EDIT, groups EDIT_START, EDIT_END
//        IMenuManager editMenu = menuManager
//                .findMenuUsingPath(IWorkbenchActionConstants.M_EDIT);
//        Separator s = new Separator("argo-edit"); //$NON-NLS-1$
//        editMenu.prependToGroup(IWorkbenchActionConstants.EDIT_START, s);
//        IMenuManager selectMenu = new MenuManager(
//                Translator.localize("menu.select")); //$NON-NLS-1$
//        selectMenu.add(actionsModel.getActionSelectAll());
//        selectMenu.add(new Separator());
//        selectMenu.add(actionsModel.getActionNavigateBack());
//        selectMenu.add(actionsModel.getActionNavigateForward());
//        selectMenu.add(new Separator());
//        selectMenu.add(actionsModel.getActionInvertSelection());
//        editMenu.appendToGroup(s.getGroupName(), new Separator());
//        editMenu.appendToGroup(s.getGroupName(), selectMenu);
//        editMenu.appendToGroup(s.getGroupName(), new Separator());
//        editMenu.appendToGroup(s.getGroupName(), actionsModel
//                .getActionRemoveFromDiagram());
//        editMenu.appendToGroup(s.getGroupName(), actionsModel
//                .getActionDeleteFromModel());
//        editMenu.appendToGroup(IWorkbenchActionConstants.EDIT_END,
//                new Separator());
//        editMenu.appendToGroup(IWorkbenchActionConstants.EDIT_END, actionsModel
//                .getActionSettings());
//
//        Separator group = new Separator("argo-menus-top"); //$NON-NLS-1$
//        menuManager.insertAfter(IWorkbenchActionConstants.M_EDIT, group);
//
//        // View menu
//        IMenuManager viewMenu = new MenuManager(
//                Translator.localize("menu.view")); //$NON-NLS-1$
//        viewMenu.add(actionsModel.getActionGotoDiagram());
//        viewMenu.add(actionsModel.getActionFind());
//        viewMenu.add(new Separator());
//        IMenuManager zoomMenu = new MenuManager(
//                Translator.localize("menu.zoom")); //$NON-NLS-1$
//        zoomMenu.add(actionsModel.getActionZoomOut());
//        zoomMenu.add(actionsModel.getActionZoomReset());
//        zoomMenu.add(actionsModel.getActionZoomIn());
//        viewMenu.add(zoomMenu);
//        viewMenu.add(new Separator());
//        viewMenu.add(actionsModel.getActionAdjustGrid());
//        viewMenu.add(actionsModel.getActionGridSnap());
//        viewMenu.add(actionsModel.getActionPageBreaks());
//        viewMenu.add(new Separator());
//        IMenuManager notationMenu = new MenuManager(
//                Translator.localize("menu.notation")); //$NON-NLS-1$
//        Iterator it = actionsModel.getActionsNotations().iterator();
//        while (it.hasNext()) {
//            notationMenu.add((Action) it.next());
//        }
//        viewMenu.add(notationMenu);
//        viewMenu.add(new Separator());
//        viewMenu.add(actionsModel.getActionXMLDump());
//        menuManager.appendToGroup(group.getGroupName(), viewMenu);
//
//        // Create menu
//        IMenuManager createMenu = new MenuManager(
//                Translator.localize("menu.create-diagram")); //$NON-NLS-1$
//        createMenu.add(actionsModel.getActionNewUseCaseDiagram());
//        createMenu.add(actionsModel.getActionNewClassDiagram());
//        createMenu.add(actionsModel.getActionNewSequenceDiagram());
//        createMenu.add(actionsModel.getActionNewCollaborationDiagram());
//        createMenu.add(actionsModel.getActionNewStateChartDiagram());
//        createMenu.add(actionsModel.getActionNewActivityDiagram());
//        createMenu.add(actionsModel.getActionNewDeploymentDiagram());
//        menuManager.appendToGroup(group.getGroupName(), createMenu);
//
//        // Arrange menu
//        IMenuManager arrangeMenu = new MenuManager(
//                Translator.localize("menu.arrange")); //$NON-NLS-1$
//        IMenuManager alignMenu = new MenuManager(
//                Translator.localize("menu.align")); //$NON-NLS-1$
//        alignMenu.add(actionsModel.getActionAlignTops());
//        alignMenu.add(actionsModel.getActionAlignBottoms());
//        alignMenu.add(actionsModel.getActionAlignRights());
//        alignMenu.add(actionsModel.getActionAlignLefts());
//        alignMenu.add(actionsModel.getActionAlignHorizontalCenters());
//        alignMenu.add(actionsModel.getActionALignVerticalCenters());
//        alignMenu.add(actionsModel.getActionAlignToGridSnap());
//        IMenuManager distributeMenu = new MenuManager(
//                Translator.localize("menu.distribute")); //$NON-NLS-1$
//        distributeMenu.add(actionsModel.getActionDistributeHorizontalSpacing());
//        distributeMenu.add(actionsModel.getActionDistributeHorizontalCenters());
//        distributeMenu.add(actionsModel.getActionDistributeVerticalSpacing());
//        distributeMenu.add(actionsModel.getActionDistributeVerticalCenters());
//        IMenuManager nudgeMenu = new MenuManager(
//                Translator.localize("menu.nudge")); //$NON-NLS-1$
//        nudgeMenu.add(actionsModel.getActionNudgeLeft());
//        nudgeMenu.add(actionsModel.getActionNudgeRight());
//        nudgeMenu.add(actionsModel.getActionNudgeUp());
//        nudgeMenu.add(actionsModel.getActionNudgeDown());
//        arrangeMenu.add(alignMenu);
//        arrangeMenu.add(distributeMenu);
//        arrangeMenu.add(nudgeMenu);
//        arrangeMenu.add(actionsModel.getActionSetPreferredSize());
//        arrangeMenu.add(actionsModel.getActionToggleAutoresizing());
//        arrangeMenu.add(actionsModel.getActionLayout());
//        menuManager.appendToGroup(group.getGroupName(), arrangeMenu);
//
//        // Generation menu
//        IMenuManager generationMenu = new MenuManager(
//                Translator.localize("menu.generation")); //$NON-NLS-1$
//        generationMenu.add(actionsModel.getActionGenerateSelectedClasses());
//        generationMenu.add(actionsModel.getActionGenerateAllClasses());
//        generationMenu.add(new Separator());
//        generationMenu.add(actionsModel.getActionCodeForProject());
//        generationMenu.add(actionsModel
//                .getActionSettingsForGenerateForProject());
//        menuManager.appendToGroup(group.getGroupName(), generationMenu);
//
//        // Critique menu
//        IMenuManager critiqueMenu = new MenuManager(
//                Translator.localize("menu.critique")); //$NON-NLS-1$
//        critiqueMenu.add(actionsModel.getActionToggleAutoCritique());
//        critiqueMenu.add(new Separator());
//        critiqueMenu.add(actionsModel.getActionDesignIssues());
//        critiqueMenu.add(actionsModel.getActionDesignGoals());
//        critiqueMenu.add(actionsModel.getActionBrowseCritics());
//        menuManager.appendToGroup(group.getGroupName(), critiqueMenu);
//
//        // Help menu
//        IMenuManager helpMenu = menuManager
//                .findMenuUsingPath(IWorkbenchActionConstants.M_HELP);
//        helpMenu.insertBefore(ActionFactory.ABOUT.getId(), actionsModel
//                .getActionAbout());
//    }
//
//    /*
//     * @see org.eclipse.ui.part.EditorActionBarContributor#contributeToToolBar(org.eclipse.jface.action.IToolBarManager)
//     */
//    public void contributeToToolBar(IToolBarManager toolBarManager) {
//        super.contributeToToolBar(toolBarManager);
//        Separator argoToolbar = new Separator("argoToolbar"); //$NON-NLS-1$
//        toolBarManager.add(argoToolbar);
//        Actions actionsModel = Actions.getInstance();
//        toolBarManager.appendToGroup(argoToolbar.getGroupName(), actionsModel
//                .getActionNew());
//        toolBarManager.appendToGroup(argoToolbar.getGroupName(), actionsModel
//                .getActionOpen());
//        // toolBarManager.appendToGroup(argoToolbar.getGroupName(),
//        // actionsModel.getActionSave());
//        toolBarManager.appendToGroup(argoToolbar.getGroupName(), actionsModel
//                .getActionPrint());
//        toolBarManager.appendToGroup(argoToolbar.getGroupName(),
//                new Separator());
//        toolBarManager.appendToGroup(argoToolbar.getGroupName(), actionsModel
//                .getActionRemoveFromDiagram());
//        toolBarManager.appendToGroup(argoToolbar.getGroupName(), actionsModel
//                .getActionNavigateBack());
//        toolBarManager.appendToGroup(argoToolbar.getGroupName(), actionsModel
//                .getActionNavigateForward());
//        toolBarManager.appendToGroup(argoToolbar.getGroupName(),
//                new Separator());
//        toolBarManager.appendToGroup(argoToolbar.getGroupName(), actionsModel
//                .getActionFind());
//        toolBarManager.appendToGroup(argoToolbar.getGroupName(), new Action() {
//            private ZoomSlider slider;       
//            {
//                setImageDescriptor(ImageDescriptor.createFromURL(
//                        ResourceLoader.class.getResource(
//                                "/org/argouml/Images/plaf/javax/swing/plaf/"
//                                + "metal/MetalLookAndFeel/toolbarButtonGrap"
//                                + "hics/general/ZoomReset.gif")));
//                setToolTipText(
//                        Translator.localize("button.zoom")); //$NON-NLS-1$
//            }
//
//            public void run() {
//                super.run();
//                if (slider == null) {
//                    slider = new ZoomSlider(Display.getDefault()
//                            .getActiveShell());
//                }
//                slider.open();
//            }
//            
//        });
//        toolBarManager.appendToGroup(argoToolbar.getGroupName(),
//                new Separator());
//        toolBarManager.appendToGroup(argoToolbar.getGroupName(), actionsModel
//                .getActionNewUseCaseDiagram());
//        toolBarManager.appendToGroup(argoToolbar.getGroupName(), actionsModel
//                .getActionNewClassDiagram());
//        toolBarManager.appendToGroup(argoToolbar.getGroupName(), actionsModel
//                .getActionNewSequenceDiagram());
//        toolBarManager.appendToGroup(argoToolbar.getGroupName(), actionsModel
//                .getActionNewCollaborationDiagram());
//        toolBarManager.appendToGroup(argoToolbar.getGroupName(), actionsModel
//                .getActionNewStateChartDiagram());
//        toolBarManager.appendToGroup(argoToolbar.getGroupName(), actionsModel
//                .getActionNewActivityDiagram());
//        toolBarManager.appendToGroup(argoToolbar.getGroupName(), actionsModel
//                .getActionNewDeploymentDiagram());
//    }
//
//    /*
//     * @see org.eclipse.ui.part.EditorActionBarContributor#contributeToStatusLine(org.eclipse.jface.action.IStatusLineManager)
//     * Deals with the status bar. Note: when the status bar updates eclipse
//     * can't see it directly, we have to dispatch the event asynchronously with
//     * asyncExec().
//     */
//    public void contributeToStatusLine(
//            final IStatusLineManager statusLineManager) {
//        super.contributeToStatusLine(statusLineManager);
//        PluginModel pluginModel = PluginModel.getInstance();
//        pluginModel.addStatusBarListener(new ActionListener() {
//
//            public void actionPerformed(final ActionEvent e) {
//                Display.getDefault().asyncExec(new Runnable() {
//                    public void run() {
//                        statusLineManager.setMessage(e.getActionCommand());
//                    }
//                });
//            };
//
//        });
//    }

}
