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

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import uml.diagram.CompositionItem;
import uml.diagram.CompositionLink;
import diagram.Diagram;
import diagram.DiagramModel;
import diagram.DiagramUI;
import diagram.Figure;
import diagram.tool.AbstractTool;
import diagram.tool.LinkShappingTool;

/**
 * @class CardinalityTool
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 */
public class CardinalityTool extends AbstractTool {

  protected MouseHandler mouseHandler = new MouseHandler();
  protected Popup popup = new Popup();

  protected CompositionItem item;
  protected CompositionLink link;
  protected Diagram diagram;

  public void install(Diagram diagram) {
    diagram.addMouseListener(mouseHandler);
  }

  public void uninstall(Diagram diagram) {
    diagram.removeMouseListener(mouseHandler);
    reset();
  }

  /**
   * @class MouseHandler
   *
   */
  protected class MouseHandler extends MouseAdapter {

    /**
     * Called when the mouse is clicked.
     *
     * @param MouseEvent
     */
    public void mouseClicked(MouseEvent e) {

      // If the right button was used & nothing else has consumed the event
      // try to join two segments
      if(!e.isConsumed() && SwingUtilities.isRightMouseButton(e)) {
        
        diagram = (Diagram)e.getSource();
        Point pt = e.getPoint();
        Figure figure = diagram.findFigure(pt);
      
        // Find a link
        if(!(figure instanceof CompositionLink)) {
          reset();
          return;
        }

        DiagramModel model = diagram.getModel();
        if(model == null) {
          reset();
          return;
        }

        link = (CompositionLink)figure;
        // Don't activate if the linkshapping tool should really handle this right click
        if(link.pointFor(pt.x, pt.y, LinkShappingTool.CLICK_TOLERANCE*2.0) != -1) {
          reset();
          return;
        }

        e.consume();
        fireToolStarted();        

        // Get the value of the item
        item = (CompositionItem)model.getValue(link);
        if(item == null) {
          item = new CompositionItem();
          model.setValue(link, item);
        }

        startEditing(pt);

      }

    }

  }

  protected void startEditing(Point pt) {
    // Popup cardinality menu
    popup.show(pt);        

  }

  protected void stopEditing(String n) {

    item.setCardinality(n); 

    DiagramUI ui  = (DiagramUI)diagram.getUI();
    ui.refreshFigure(link);

  }

  /**
   * @class Popup
   */
  protected class Popup extends JPopupMenu {
    
    protected JTextField text = new JTextField();
    protected int n;

    public Popup() {

      super("Cardinality");

      JLabel lbl = new JLabel("Cardinality");
      lbl.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));

      add(lbl);
      add(text);

      lbl.setFont( getFont().deriveFont(Font.PLAIN, getFont().getSize()-1) );
      setDefaultLightWeightPopupEnabled(true);

      Border border = BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.black),
                        BorderFactory.createEmptyBorder(1,1,1,1));

      border = BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(1,1,1,1), border);

      text.setBorder(border);

    }

    public void show(Point pt) {

      text.setText(item.getCardinality());
      super.show(diagram, pt.x, pt.y);

    }
    
    protected void firePopupMenuWillBecomeInvisible() {

      try { 
        stopEditing(text.getText());

      } catch(Throwable t) {}

      fireToolFinished();        
      reset();

    }

  }

  protected void reset() {
    diagram = null;
    item = null;
    link = null;
  }

}
