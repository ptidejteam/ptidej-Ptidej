/**
 *
    Java Diagram Package; An extremely flexible and fast multipurpose diagram 
    component for Swing.
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

package diagram.tool;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import diagram.Diagram;
import diagram.DiagramUI;
import diagram.Figure;
import diagram.SelectionModel;

/**
 * @class SelectionTool 
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * This tool allows a the selected figures in a Diagram to be modified.
 *
 * - Left mouse button selects an item.
 * - Shift clicking edits the current selection.
 * - Left clicking no where unselects the current selection.
 *
 */
public class SelectionTool extends AbstractTool {

  private Figure[] figures = new Figure[4]; 

  private MouseHandler mouseHandler = new MouseHandler();

  /**
   * Install support for something in the given Diagram
   *
   * @param Diagram
   */
  public void install(Diagram diagram) {

    diagram.addMouseListener(mouseHandler);

  }

    /**
   * Remove support for something that was previously installed.
   *
   * @param Diagram
   */
  public void uninstall(Diagram diagram) {

    diagram.removeMouseListener(mouseHandler);

  }

  /**
   *
   */
  protected class MouseHandler extends MouseAdapter {
  
    /**
     * When an unconsumed left mouse click has been detected update the
     * components SelectionModel.
     *
     * @param MouseEvent
     */
    public void mouseClicked(MouseEvent e) {
      
      // Check for left mouse button & a valid selection
      if(e.isConsumed() || !SwingUtilities.isLeftMouseButton(e) || e.getClickCount() > 1)
        return;
      
      
      // Find the node which was clicked on
      Diagram diagram = (Diagram)e.getSource();
      Figure node = (Figure)diagram.findFigure(e.getPoint());
      
      SelectionModel selectionModel = diagram.getSelectionModel();
      
      // If a node was shift-clicked, update it
      if(e.isShiftDown() && node != null) {
        
        if(selectionModel.contains(node))
          selectionModel.remove(node);
      else
        selectionModel.add(node);
        
        // Repaint the diagram
        DiagramUI ui = (DiagramUI)diagram.getUI();
        ui.refreshFigure(node);
        
        // If not shift-clicked, start by clearing the current selection
      } else if(!e.isShiftDown())
        deselectAll(diagram, selectionModel, node);

    }
    
    /**
     * When an unconsumed left mouse click has been detected update the
     * components SelectionModel.
     *
     * This event is not consumed, so it will get passed on to other 
     * support objects. This makes mouse motions like 1 click & drag seem
     * more natural.
     *
     * @param MouseEvent
     */
    public void mousePressed(MouseEvent e) {
      
      // Check for left mouse button , no shift & a valid selection
      if(e.isConsumed() || e.isShiftDown() || !SwingUtilities.isLeftMouseButton(e) || 
         e.getClickCount() > 1)
        return; 
      
      // Find the node which was clicked on
      Diagram diagram = (Diagram)e.getSource();
      Figure node = (Figure)diagram.findFigure(e.getPoint());
      
      SelectionModel selectionModel = diagram.getSelectionModel();
      
      // If the model has 1 item or less selected, add this item quickly
      if(selectionModel.size() <= 1 && node != null) 
        deselectAll(diagram, selectionModel, node);
      
    }

  } /* MouseHandler */

  /**
   * Deselect all items but the given Figure (if any)
   *
   * @param SelectionModel
   * @param Figure
   */ 
  private final void deselectAll(Diagram diagram, SelectionModel model, Figure except) {

    // Copy previously selected items
    Figure[] items = (Figure[])model.toArray((Object[])figures);
    DiagramUI ui = (DiagramUI)diagram.getUI();
    
    // Remove nodes that just were un-selected, but not the one
    // node still clicked on
    for(int i=0; i<items.length && items[i] != null; i++) {

      Figure node = items[i];

      // Damage the area & remove
      if(except == null || node != except) {
        
        ui.damageFigure(node);
        model.remove(node);

      }

    }
 
    // If a node was click and it was not selected, select it.
    if(except != null && !model.contains(except)) {

      // Damage & remove
      ui.damageFigure(except);
      model.add(except);

    }

    // Refresh an item, to force a repaint when the fast refresh is not enabled
    if(items.length > 0 && items[0] != null) 
      ui.refreshFigure(items[0]);
    else if(except != null)
      ui.refreshFigure(except);

    java.util.Arrays.fill(items, 0, items.length, null);
    
  }


}

