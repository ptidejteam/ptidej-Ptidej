package org.tigris.giant;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import org.tigris.gef.base.CmdCreateNode;
import org.tigris.gef.base.CmdDeleteFromModel;
import org.tigris.gef.base.CmdSetMode;
import org.tigris.gef.base.Globals;
import org.tigris.gef.base.ModeCreatePolyEdge;
import org.tigris.gef.base.ModeSelect;
import org.tigris.gef.base.SelectionManager;
import org.tigris.gef.event.ModeChangeEvent;
import org.tigris.gef.event.ModeChangeListener;
import org.tigris.gef.graph.presentation.JGraph;
import org.tigris.gef.graph.presentation.JSplitGraphPane;
import org.tigris.gef.ui.PaletteFig;
import org.tigris.gef.ui.ToolBar;

import org.tigris.giant.gefworkbench.ZoomSliderButton;
import org.tigris.giant.model.DependsEdge;
import org.tigris.giant.model.TargetNode;

/**
 * A panel containing the toolbar and split diagram
 * @author Bob Tarling
 * @since 16-Feb-04
 */
public class DiagramPanel extends JPanel implements ModeChangeListener {
    /** The graph pane (shown in middle of window). */
    private JGraph graph;
    private ToolBar toolbar;

    public DiagramPanel() {
        super(new BorderLayout());
        graph = new JGraph();
        add(makeToolBar(), BorderLayout.NORTH);
        add(new JSplitGraphPane(graph), BorderLayout.CENTER);
        graph.addModeChangeListener(this);
        SelectionManager sm = graph.getEditor().getSelectionManager();
        sm.addGraphSelectionListener(new PropSheetListener());
        
        // make the delete key remove elements from the underlying GraphModel
        graph.bindKey(new CmdDeleteFromModel(), KeyEvent.VK_DELETE, 0);
    }
    
    private ToolBar makeToolBar() {
        toolbar = new PaletteFig();
        toolbar.add(new CmdCreateNode(TargetNode.class, "NodeTarget"));
        toolbar.add(new CmdSetMode(ModeCreatePolyEdge.class, "edgeClass", DependsEdge.class, "EdgeDependency"));
        toolbar.add(new ZoomSliderButton());
        
        return toolbar;
    }

    /* (non-Javadoc)
     * @see org.tigris.gef.event.ModeChangeListener#modeChange(org.tigris.gef.event.ModeChangeEvent)
     */
    public void modeChange(ModeChangeEvent mce) {
        if (!Globals.getSticky() && Globals.mode() instanceof ModeSelect)
            toolbar.unpressAllButtons();
    }
}
