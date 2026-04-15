/**
 *
    QuickUML; A simple UML tool that demonstrates one use of the 
    Java Diagram Package 

    Copyright (C) 2001  Eric Crahen <crahen@cse.buffalo.edu>

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

 */


package uml.ui;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import uml.diagram.ClassFigure;
import uml.diagram.InterfaceFigure;
import uml.diagram.NoteFigure;
import diagram.Diagram;
import diagram.tool.ClipboardTool;
import diagram.tool.CompositeTool;
import diagram.tool.EditingTool;
import diagram.tool.FigureDraggingTool;
import diagram.tool.FigureShappingTool;
import diagram.tool.FigureTool;
import diagram.tool.LinkShappingTool;
import diagram.tool.SelectionTool;
import diagram.tool.Tool;
import diagram.tool.ToolListener;


/**
 * @class ToolPalette
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 */
public class ToolPalette extends JToolBar 
  implements PropertyChangeListener, ToolListener {

  private ToolButton pointerButton;
  private ButtonGroup buttonGroup = new ButtonGroup();
  private DiagramContainer container;

  private Tool currentTool;
  private boolean revertPointer = true;

  /**
   * Create a new MenuBar
   */
  public ToolPalette(DiagramContainer container) {

    super("Drawing Palette", VERTICAL);

    setFloatable(false);
    addSeparator();

    CompositeTool arrowTool = new CompositeTool();
    arrowTool.add(new SelectionTool());
    arrowTool.add(new FigureDraggingTool());
    arrowTool.add(new EditingTool());
    arrowTool.add(new FigureShappingTool());
    arrowTool.add(new LinkShappingTool());
    arrowTool.add(new ClipboardTool());
    arrowTool.add(new CardinalityTool());

    pointerButton = createButton(arrowTool, "images/Arrow.gif", "Pointer");
    add(pointerButton);

    addSeparator();

    Tool tool = new FigureTool(new ClassFigure());
    add( createButton(tool, "images/Class.gif", "Class") );

    tool = new FigureTool(new InterfaceFigure());
    add( createButton(tool, "images/Interface.gif", "Interface") );

    tool = new FigureTool(new NoteFigure());
    add( createButton(tool, "images/Note.gif", "Note") );
    
    addSeparator();

    tool = new GeneralizationTool();
    add( createButton(tool, "images/Generalization.gif", "Generalization") );

    tool = new RealizationTool();
    add( createButton(tool, "images/Realization.gif", "Realization") );

    addSeparator();

    tool = new CompositionTool();
    add( createButton(tool, "images/Composition.gif", "Composition") );

    tool = new AssociationTool();
    add( createButton(tool, "images/Association.gif", "Association"));

    tool = new DependencyTool();
    add( createButton(tool, "images/Dependency.gif", "Dependency"));

    container.addPropertyChangeListener(this);
    this.container = container;

    pointerButton.doClick();

  }

  /**
   * Add a button to the palette
   */
  protected ToolButton createButton(Tool tool, String iconResource, String toolTip) {
    return createButton(tool, 
                        IconManager.getInstance().getIconResource(this, iconResource), toolTip); 
  }

  /**
   * Add a button to the palette
   */
  protected ToolButton createButton(Tool tool, Icon icon, String toolTip) {
    
    ToolButton button = new ToolButton(tool, icon, toolTip);
    tool.addToolListener(this);

    return button;

  }

  /**
   * Update the menu bar. Add toggle option, etc.
   *
   * @param FlatMenuBar
   */
  public void updateMenus(FlatMenuBar menuBar) {

    // Append the option to the end of the Options menu
    JMenu menu = menuBar.getMenu("Options");
    menu.add(new JSeparator(), -1);    
    
    JCheckBoxMenuItem item = new JCheckBoxMenuItem(new ToggleRevertAction());
    item.setState(revertPointer);
    menu.add(item, -1);
    
  }

  /**
   * Listen for the property to changes
   */
  public void propertyChange(PropertyChangeEvent e) {

    if(e.getPropertyName().equals("diagram.container")) {

      Diagram diagram = (Diagram)e.getNewValue();
      Diagram oldDiagram = (Diagram)e.getOldValue();

      if(currentTool != null) {

        if(oldDiagram != null)
          currentTool.uninstall(oldDiagram);
        if(diagram != null)
          currentTool.install(diagram);

      }
      
    }

  }

  /**
   * Called when a tool has reacted to an event and has started doing its job
   */
  public void toolStarted(Tool tool) {

  }

  /**
   * Called when a tool has completed its work
   */ 
  public void toolFinished(Tool tool) {

    if(revertPointer && pointerButton.getTool() != tool)
      pointerButton.doClick();

  }

  /**
   * @class ToolButton
   *
   */
  protected class ToolButton extends JToggleButton {

    protected Tool tool;

    public ToolButton(Tool tool, Icon icon, String toolTip) {

      super(icon);

      this.setToolTipText(toolTip);
      this.tool = tool;

      buttonGroup.add(this);

    }

    public Tool getTool() {
      return tool;
    }

    protected void fireActionPerformed(ActionEvent e) {

      Diagram diagram = container.getView();
      if(diagram != null) {
          
        if(currentTool != null)
          currentTool.uninstall(diagram);

        tool.install(diagram);
        currentTool = tool;
        
      }
 
      super.fireActionPerformed(e);

    }
    
  } /* ToggleButton */

  
  /**
   * @class ToggleRevertAction
   */
  protected class ToggleRevertAction extends AbstractAction {

    public ToggleRevertAction() {
      super("Revert to pointer");
    }

    public void actionPerformed(ActionEvent e) {
      revertPointer = !revertPointer;
    }

  }

}
