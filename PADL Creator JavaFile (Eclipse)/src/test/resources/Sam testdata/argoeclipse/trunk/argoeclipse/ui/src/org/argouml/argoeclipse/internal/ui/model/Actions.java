// $Id: Actions.java 2006/07/22 00:00:00 b00__1 $
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

package org.argouml.argoeclipse.internal.ui.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.action.Action;

import org.tigris.gef.base.AlignAction;
import org.tigris.gef.base.CmdAdjustGrid;
import org.tigris.gef.base.CmdAdjustGuide;
import org.tigris.gef.base.CmdAdjustPageBreaks;
import org.tigris.gef.base.CmdSelectAll;
import org.tigris.gef.base.CmdSelectInvert;
import org.tigris.gef.base.CmdZoom;
import org.tigris.gef.base.DistributeAction;

import org.argouml.argoeclipse.internal.ui.actions.ActionFind;
import org.argouml.argoeclipse.internal.ui.actions.ActionOpenCritics;
import org.argouml.argoeclipse.internal.ui.actions.ActionOpenDecisions;
import org.argouml.argoeclipse.internal.ui.actions.ActionOpenGoals;
import org.argouml.argoeclipse.internal.ui.actions.ActionPageSetup;
import org.argouml.argoeclipse.internal.ui.actions.ActionPrint;
import org.argouml.argoeclipse.internal.ui.actions.NavigateTargetBackAction;
import org.argouml.argoeclipse.internal.ui.actions.NavigateTargetForwardAction;
import org.argouml.argoeclipse.internal.ui.util.HybridAction;
import org.argouml.notation.Notation;
import org.argouml.notation.NotationName;
import org.argouml.ui.ActionExportXMI;
import org.argouml.ui.ActionImportXMI;
import org.argouml.ui.ActionProjectSettings;
import org.argouml.ui.ProjectBrowser;
import org.argouml.ui.cmd.ActionAboutArgoUML;
import org.argouml.ui.cmd.ActionAutoCritique;
import org.argouml.ui.cmd.ActionGotoDiagram;
import org.argouml.ui.cmd.ActionNew;
import org.argouml.ui.cmd.ActionShowXMLDump;
import org.argouml.ui.cmd.ActionSystemInfo;
import org.argouml.ui.cmd.CmdSetPreferredSize;
import org.argouml.ui.explorer.ActionPerspectiveConfig;
import org.argouml.ui.targetmanager.TargetManager;
import org.argouml.uml.ui.ActionActivityDiagram;
import org.argouml.uml.ui.ActionClassDiagram;
import org.argouml.uml.ui.ActionCollaborationDiagram;
import org.argouml.uml.ui.ActionDeploymentDiagram;
import org.argouml.uml.ui.ActionGenerateAll;
import org.argouml.uml.ui.ActionGenerateOne;
import org.argouml.uml.ui.ActionGenerateProjectCode;
import org.argouml.uml.ui.ActionGenerationSettings;
import org.argouml.uml.ui.ActionImportFromSources;
import org.argouml.uml.ui.ActionLayout;
import org.argouml.uml.ui.ActionOpenProject;
import org.argouml.uml.ui.ActionRevertToSaved;
import org.argouml.uml.ui.ActionSaveAllGraphics;
import org.argouml.uml.ui.ActionSaveGraphics;
import org.argouml.uml.ui.ActionSaveProjectAs;
import org.argouml.uml.ui.ActionSequenceDiagram;
import org.argouml.uml.ui.ActionStateDiagram;
import org.argouml.uml.ui.ActionUseCaseDiagram;

/**
 * This class is a model and is responsible for supplying actions from ArgoUML
 * to the plugin.
 * 
 * @author Bogdan Pistol
 */
public class Actions {
    /**
     * The zoom factor from ArgoUML. 
     * (Or should be made public the ZOOM_FACTOR from 
     * org.argouml.ui.cmd.GenericArgoMenuBar.ZOOM_FACTOR ?)
     */
    static final double ZOOM_FACTOR = 0.9;
    
    /**
     * The singleton instance.
     */
    private static Actions instance;
    
    /**
     * The hidden JFrame instance from ArgoUML.
     */
    private ProjectBrowser projectBrowser;
    
    // File menu
    private Action actionNew;
    
    private Action actionOpen;
    
    private Action actionSave;
    
    private Action actionSaveAs;
    
    private Action actionRevertToSaved;
    
    private Action actionImportXMI;
    
    private Action actionExportXMI;
    
    private Action actionImportSources;
    
    private Action actionPageSetup;
    
    private Action actionPrint;
    
    private Action actionExportGraphics;
    
    private Action actionExportAllGraphics;
    
    private Action actionProperties;
    
    //  Edit menu
    private Action actionSelectAll;

    private Action actionNavigateBack;

    private Action actionNavigateForward;

    private Action actionInvertSelection;

    private Action actionRemoveFromDiagram;

    private Action actionDeleteFromModel;

    private Action actionConfigurePerspectives;


    // View menu
    private Action actionGotoDiagram;

    private Action actionFind;

    private Action actionZoomOut;

    private Action actionZoomReset;

    private Action actionZoomIn;
    
    /**
     * Toolbar item.
     */
    private Action actionAdjustGrid;

    private Action actionGridSnap;

    private Action actionPageBreaks;

    /**
     * All the notations.
     */
    private List actionsNotations;    

    private Action actionXMLDump;

    // Create menu
    private Action actionNewUseCaseDiagram;

    private Action actionNewClassDiagram;

    private Action actionNewSequenceDiagram;

    private Action actionNewCollaborationDiagram;

    private Action actionNewStateChartDiagram;

    private Action actionNewActivityDiagram;

    private Action actionNewDeploymentDiagram;

    // Arange menu
    private Action actionAlignTops;

    private Action actionAlignBottoms;

    private Action actionAlignRights;

    private Action actionAlignLefts;

    private Action actionAlignHorizontalCenters;

    private Action actionAlignVerticalCenters;

    private Action actionAlignToGridSnap;

    private Action actionDistributeHorizontalSpacing;

    private Action actionDistributeHorizontalCenters;

    private Action actionDistributeVerticalSpacing;

    private Action actionDistributeVerticalCenters;

    private Action actionReorderForward;

    private Action actionReorderBackward;

    private Action actionReorderToFront;

    private Action actionReorderToBack;

    private Action actionSetPreferredSize;

    private Action actionLayout;

    // Generation menu
    private Action actionGenerateSelectedClasses;

    private Action actionGenerateAllClasses;

    private Action actionCodeForProject;

    private Action actionSettingsForGenerateForProject;

    // Critique menu
    private Action actionToggleAutoCritique;

    private Action actionDesignIssues;

    private Action actionDesignGoals;

    private Action actionBrowseCritics;

    // Tools menu

    // Help menu
    private Action actionSystemInformation;

    private Action actionAbout;
    
    /**
     * Initialization.     
     */
    private Actions() {
        projectBrowser = ProjectBrowser.getInstance();
        initFileActions();
        initEditActions();
        initViewActions();
        initCreateActions();
        initArrangeActions();
        initGenerationActions();
        initCritiqueActions();
        initHelpActions();
    }
    
    /**
     * Getter for the instance.
     * @return the instance
     */
    public static Actions getInstance() {
        if (instance == null) {                        
            instance = new Actions();            
        }             
        return instance;
    }
    
    //  File menu
    public Action getActionNew() {
        return actionNew;
    }
    
    public Action getActionOpen() {
        return actionOpen;
    }
    
    public Action getActionSave() {
        return actionSave;
    }
    
    public Action getActionSaveAs() {
        return actionSaveAs;
    }
    
    public Action getActionRevertToSaved() {
        return actionRevertToSaved;
    }

    public Action getActionImportXMI() {
        return actionImportXMI;
    }

    public Action getActionExportXMI() {
        return actionExportXMI;
    }

    public Action getActionImportSources() {
        return actionImportSources;
    }
    
    public Action getActionPageSetup() {
        return actionPageSetup;
    }
    
    public Action getActionPrint() {
        return actionPrint;
    }
    
    public Action getActionExportGraphics() {
        return actionExportGraphics;
    }

    public Action getActionExportAllGraphics() {
        return actionExportAllGraphics;
    }

    public Action getActionProperties() {
        return actionProperties;
    }
    
    // Edit menu
    public Action getActionSelectAll() {
        return actionSelectAll;
    }

    public Action getActionNavigateBack() {
        return actionNavigateBack;
    }

    public Action getActionNavigateForward() {
        return actionNavigateForward;
    }

    public Action getActionInvertSelection() {
        return actionInvertSelection;
    }

    public Action getActionRemoveFromDiagram() {
        return actionRemoveFromDiagram;
    }

    public Action getActionDeleteFromModel() {
        return actionDeleteFromModel;
    }

    public Action getActionConfigurePerspectives() {
        return actionConfigurePerspectives;
    }

    // View menu
    public Action getActionGotoDiagram() {
        return actionGotoDiagram;
    }

    public Action getActionFind() {
        return actionFind;
    }

    public Action getActionZoomOut() {
        return actionZoomOut;
    }

    public Action getActionZoomReset() {
        return actionZoomReset;
    }

    public Action getActionZoomIn() {
        return actionZoomIn;
    }
    
    public Action getActionAdjustGrid() {
        return actionAdjustGrid;
    }

    public Action getActionGridSnap() {
        return actionGridSnap;
    }

    public Action getActionPageBreaks() {
        return actionPageBreaks;
    }

    public List getActionsNotations() {
        return actionsNotations;
    }    

    public Action getActionXMLDump() {
        return actionXMLDump;
    }

    // Create menu
    public Action getActionNewUseCaseDiagram() {
        return actionNewUseCaseDiagram;
    }

    public Action getActionNewClassDiagram() {
        return actionNewClassDiagram;
    }

    public Action getActionNewSequenceDiagram() {
        return actionNewSequenceDiagram;
    }

    public Action getActionNewCollaborationDiagram() {
        return actionNewCollaborationDiagram;
    }

    public Action getActionNewStateChartDiagram() {
        return actionNewStateChartDiagram;
    }

    public Action getActionNewActivityDiagram() {
        return actionNewActivityDiagram;
    }

    public Action getActionNewDeploymentDiagram() {
        return actionNewDeploymentDiagram;
    }

    // Arrange menu
    public Action getActionAlignTops() {
        return actionAlignTops;
    }

    public Action getActionAlignBottoms() {
        return actionAlignBottoms;
    }

    public Action getActionAlignRights() {
        return actionAlignRights;
    }

    public Action getActionAlignLefts() {
        return actionAlignLefts;
    }

    public Action getActionAlignHorizontalCenters() {
        return actionAlignHorizontalCenters;
    }

    public Action getActionALignVerticalCenters() {
        return actionAlignVerticalCenters;
    }

    public Action getActionAlignToGridSnap() {
        return actionAlignToGridSnap;
    }

    public Action getActionDistributeHorizontalSpacing() {
        return actionDistributeHorizontalSpacing;
    }

    public Action getActionDistributeHorizontalCenters() {
        return actionDistributeHorizontalCenters;
    }

    public Action getActionDistributeVerticalSpacing() {
        return actionDistributeVerticalSpacing;
    }

    public Action getActionDistributeVerticalCenters() {
        return actionDistributeVerticalCenters;
    }

    public Action getActionReorderForward() {
        return actionReorderForward;
    }

    public Action getActionReorderBackward() {
        return actionReorderBackward;
    }

    public Action getActionReorderToFront() {
        return actionReorderToFront;
    }

    public Action getActionReorderToBack() {
        return actionReorderToBack;
    }

    public Action getActionSetPreferredSize() {
        return actionSetPreferredSize;
    }

    public Action getActionLayout() {
        return actionLayout;
    }

    // Generation menu
    public Action getActionGenerateSelectedClasses() {
        return actionGenerateSelectedClasses;
    }

    public Action getActionGenerateAllClasses() {
        return actionGenerateAllClasses;
    }

    public Action getActionCodeForProject() {
        return actionCodeForProject;
    }

    public Action getActionSettingsForGenerateForProject() {
        return actionSettingsForGenerateForProject;
    }

    // Critique menu
    public Action getActionToggleAutoCritique() {
        return actionToggleAutoCritique;
    }

    public Action getActionDesignIssues() {
        return actionDesignIssues;
    }

    public Action getActionDesignGoals() {
        return actionDesignGoals;
    }

    public Action getActionBrowseCritics() {
        return actionBrowseCritics;
    }

    // Help menu
    public Action getActionSystemInformation() {
        return actionSystemInformation;
    }

    public Action getActionAbout() {
        return actionAbout;
    }    

    /**
     * Initialize actions for file menu.
     */
    private void initFileActions() {
        actionNew = new HybridAction(new ActionNew(), IconPaths.NEW_PATH);
        actionOpen = new HybridAction(new ActionOpenProject(), 
                IconPaths.OPEN_PATH);
        actionSave = new HybridAction(projectBrowser.getSaveAction(),
                IconPaths.SAVE_PATH);
        actionSaveAs = new HybridAction( new ActionSaveProjectAs(), 
                IconPaths.SAVE_AS_PATH);
        actionRevertToSaved = new HybridAction(new ActionRevertToSaved());
        actionImportXMI = new HybridAction(new ActionImportXMI());
        actionExportXMI = new HybridAction(new ActionExportXMI());
        actionImportSources = new HybridAction(
                ActionImportFromSources.getInstance(), 
                IconPaths.IMPORT_SOURCES_PATH);
        actionPageSetup = new ActionPageSetup();
        actionPrint = new ActionPrint();
        actionExportGraphics = new HybridAction(new ActionSaveGraphics());
        actionExportAllGraphics = new HybridAction(new ActionSaveAllGraphics());
        actionProperties = new HybridAction(new ActionProjectSettings());

    }
    
    /**
     * Initialize actions for edit menu.
     */
    private void initEditActions() {
        actionSelectAll = new HybridAction(new CmdSelectAll());
        actionNavigateBack = new NavigateTargetBackAction();
        actionNavigateForward = new NavigateTargetForwardAction();
        actionInvertSelection = new HybridAction(new CmdSelectInvert());
        actionRemoveFromDiagram = new HybridAction(projectBrowser
                .getRemoveFromDiagramAction(), 
                IconPaths.REMOVE_FROM_DIAGRAM_PATH);
        actionDeleteFromModel = new HybridAction(TargetManager.getInstance()
                .getDeleteAction(), IconPaths.DELETE_FROM_MODEL_PATH);
        actionConfigurePerspectives = new HybridAction(
                new ActionPerspectiveConfig());
    }

    /**
     * Initialize actions for view menu.
     */
    private void initViewActions() {
        actionGotoDiagram = new HybridAction(new ActionGotoDiagram());
        actionFind = new ActionFind();
        actionZoomOut = new HybridAction(new CmdZoom(ZOOM_FACTOR),
                IconPaths.ZOOM_OUT_PATH);
        actionZoomReset = new HybridAction(new CmdZoom(0.0), 
                IconPaths.ZOOM_RESET_PATH);
        actionZoomIn = new HybridAction(
                new CmdZoom((1.0) / ZOOM_FACTOR), 
                IconPaths.ZOOM_IN_PATH);
        
        actionAdjustGrid = new HybridAction(new CmdAdjustGrid());
        actionGridSnap = new HybridAction(new CmdAdjustGuide());
        actionPageBreaks = new HybridAction(new CmdAdjustPageBreaks());
        
        actionsNotations = getNotationsActions();
                    
        actionXMLDump = new HybridAction(new ActionShowXMLDump());
    }

    private List getNotationsActions() {
        List actions = new ArrayList();
        /*
         * It seems that we cannot have in eclipse a radioitem with an icon.
         */
        Iterator it = Notation.getAvailableNotations().iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof NotationName) {
                final NotationName nn = (NotationName) o;
                actions.add(new Action(nn.getTitle(),
                        Action.AS_RADIO_BUTTON) {
                    {
                        setChecked(Notation.getConfigueredNotation()
                                .sameNotationAs(nn));
                    }

                    public void run() {
                        super.run();
                        Notation.setDefaultNotation(nn);
                    }
                });
            }
        }
        return actions;
    }

    /**
     * Initialize actions for create menu.
     */
    private void initCreateActions() {
        actionNewUseCaseDiagram = new HybridAction(
                new ActionUseCaseDiagram(), IconPaths.USECASE_DIAGRAM_PATH);
        actionNewClassDiagram = new HybridAction(
                new ActionClassDiagram(), IconPaths.CLASS_DIAGRAM_PATH);
        actionNewSequenceDiagram = new HybridAction(
                new ActionSequenceDiagram(), IconPaths.SEQUENCE_DIAGRAM_PATH);
        actionNewCollaborationDiagram = new HybridAction(
                new ActionCollaborationDiagram(), 
                IconPaths.COLLABORATION_DIAGRAM_PATH);
        actionNewStateChartDiagram = new HybridAction(new ActionStateDiagram(),
                IconPaths.STATE_DIAGRAM_PATH);
        actionNewActivityDiagram = new HybridAction(
                new ActionActivityDiagram(), IconPaths.ACTIVITY_DIAGRAM_PATH);
        actionNewDeploymentDiagram = new HybridAction(
                new ActionDeploymentDiagram(), 
                IconPaths.DEPLOYMENT_DIAGRAM_PATH);
    }

    /**
     * Initialize actions for arrange menu.
     */
    private void initArrangeActions() {
        actionAlignTops = new HybridAction(new AlignAction(
                AlignAction.ALIGN_TOPS));
        actionAlignBottoms = new HybridAction(new AlignAction(
                AlignAction.ALIGN_BOTTOMS));
        actionAlignRights = new HybridAction(new AlignAction(
                AlignAction.ALIGN_RIGHTS));
        actionAlignLefts = new HybridAction(new AlignAction(
                AlignAction.ALIGN_LEFTS));
        actionAlignHorizontalCenters = new HybridAction(new AlignAction(
                AlignAction.ALIGN_H_CENTERS));
        actionAlignVerticalCenters = new HybridAction(new AlignAction(
                AlignAction.ALIGN_V_CENTERS));
        actionAlignToGridSnap = new HybridAction(new AlignAction(
                AlignAction.ALIGN_TO_GRID));
        actionDistributeHorizontalSpacing = new HybridAction(
                new DistributeAction(DistributeAction.H_SPACING),
                IconPaths.DISTRIBUTE_HORIZONTAL_SPACING_PATH);
        actionDistributeHorizontalCenters = new HybridAction(
                new DistributeAction(DistributeAction.H_CENTERS),
                IconPaths.DISTRIBUTE_HORIZONTAL_CENTERS_PATH);
        actionDistributeVerticalSpacing = new HybridAction(
                new DistributeAction(DistributeAction.V_SPACING),
                IconPaths.DISTRIBUTE_VERTICAL_SPACING_PATH);
        actionDistributeVerticalCenters = new HybridAction(
                new DistributeAction(DistributeAction.V_CENTERS),
                IconPaths.DISTRIBUTE_VERTICAL_CENTERS_PATH);
        actionSetPreferredSize = new HybridAction(new CmdSetPreferredSize(
                CmdSetPreferredSize.MINIMUM_SIZE));
        actionLayout = new HybridAction(new ActionLayout());
    }

    /**
     * Initialize actions for generate menu.
     */
    private void initGenerationActions() {
        actionGenerateSelectedClasses = 
            new HybridAction(new ActionGenerateOne());
        actionGenerateAllClasses = new HybridAction(new ActionGenerateAll());
        actionCodeForProject = 
            new HybridAction(new ActionGenerateProjectCode());
        actionSettingsForGenerateForProject = new HybridAction(
                new ActionGenerationSettings());
    }

    /**
     * Initialize actions for critique menu.
     */
    private void initCritiqueActions() {
        actionToggleAutoCritique = new HybridAction(new ActionAutoCritique(),
                null, Action.AS_CHECK_BOX, null);
        actionDesignIssues = new ActionOpenDecisions();
        actionDesignGoals = new ActionOpenGoals();
        actionBrowseCritics = new ActionOpenCritics();
    }
    
    /**
     * Initialize actions for help menu.
     */
    private void initHelpActions() {
        actionSystemInformation = new HybridAction(new ActionSystemInfo());
        // TODO: Make this a contribution to the Eclipse About box - tfm
        actionAbout = new HybridAction(new ActionAboutArgoUML(),
                IconPaths.ABOUT_PATH);
    }

}
