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

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

import diagram.Diagram;
import diagram.DiagramUI;
import diagram.Figure;
import diagram.figures.PolyLink;

/**
 * @class LinkShapping
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * This tool allows PolyLink figures to be dragged or edited. Left-clicking and
 * dragging allows new points to be inserted into the PolyLink, or existing points
 * to be moved. Right-clicking allows existing points to be deleted. The two end
 * points are immune to these operations.
 */
public class LinkShappingTool extends AbstractTool {

  // Margin of error for clicking on a line, and for detecting a drag
  public static final double CLICK_TOLERANCE = 6.0;
  public static final double DRAG_TOLERANCE = 6.0;
  // Drag cursor
  private static final Cursor HD_CURSOR = new Cursor(Cursor.HAND_CURSOR);

  // Orginal diagram cursor
  private Cursor originalCursor = null;

  // Diagram
  private Diagram diagram;

  // Mouse dragged from
  private Point2D dragPoint; 

  // Link getting shapped
  private PolyLink polyLink;
  
  // Point first pressed
  private Point2D pressPoint;

  private MouseHandler mouseHandler = new MouseHandler();

  /**
   * Install the support in the specified Diagram
   *
   * @param Diagram
   */
  public void install(Diagram diagram) {

    if(diagram.getSelectionModel() == null)
      throw new RuntimeException("LinkShapping requires SelectionModel support");

    diagram.addMouseListener(mouseHandler);
    diagram.addMouseMotionListener(mouseHandler);

  }

  /**
   * Uninstall the support from the specified Diagram
   *
   * @param Diagram
   */
  public void uninstall(Diagram diagram) {

    diagram.removeMouseListener(mouseHandler);
    diagram.removeMouseMotionListener(mouseHandler);

    // Clean up
    reset();

  }

  /**
   * Called when a link has been pressed
   */
  protected void linkPressed(PolyLink link, MouseEvent event) { }

  /**
   * Called when a link has been clicked
   */
  protected void linkClicked(PolyLink link, MouseEvent event) { }

  /**
   * Called when a link has been pressed
   */
  protected void linkReleased(PolyLink link, MouseEvent event) { }

  /**
   * @class MouseHandler
   */
  protected class MouseHandler extends MouseInputAdapter {

    /**
     * Called when the mouse is pressed.
     *
     * @param MouseEvent
     */
    public void mousePressed(MouseEvent e) {
      
      // Skip processing this event when some modifier is being used, a popup might
      // start or something has already consumed the event
      if(e.isConsumed() || e.isShiftDown() || e.isControlDown() || e.isAltDown())
        return;
      
      // Get the diagram and the point
      diagram = (Diagram)e.getSource();
      Point2D pt = e.getPoint();
      
      Diagram diagram = (Diagram)e.getSource();
      Figure figure = diagram.findFigure(pt);
      
      // Find a link
      if(!(figure instanceof PolyLink)) {
        reset();
        return;
      }
      
      polyLink = (PolyLink)figure;

      // Start a drag if this is a single click, do not consume this event. This
      // might just be someone sloppily double clicking
      if(SwingUtilities.isLeftMouseButton(e))
        pressPoint = pt;
      
      linkPressed(polyLink, e);

    }
    

    /**
     * Called when the mouse is clicked.
     *
     * @param MouseEvent
     */
    public void mouseClicked(MouseEvent e) {
     
      // If the right button was used & nothing else has consumed the event
      // try to join two segments
      if(!e.isConsumed() && SwingUtilities.isRightMouseButton(e)) {

        Diagram diagram = (Diagram)e.getSource();
        Point pt = e.getPoint();

        // Find a link
        Figure figure = diagram.findFigure(pt);
        if(!(figure instanceof PolyLink))
          return;
        
        PolyLink polyLink = (PolyLink)figure;
        DiagramUI ui = (DiagramUI)diagram.getUI();
        
        // Make sure the place clicked was a point that can be removed
        int point = polyLink.pointFor(pt.getX(), pt.getY(), CLICK_TOLERANCE*2.0);
        if(point > 0 && point < (polyLink.getPointCount() - 1)) {
          
          e.consume();
          fireToolStarted();        
          
          // Damage & refresh
          ui.damageFigure(polyLink);
          polyLink.join(point);
          ui.refreshFigure(polyLink);
            

        }
        
        linkClicked(polyLink, e);

      }  
    
    }

    /**
     * Called when the dragging takes place. 
     *
     * @param MouseEvent
     */
    public void mouseDragged(MouseEvent e) {
      
      // Skip if the wrong button was used or something else has consumed the event
      if(e.isConsumed() || !SwingUtilities.isLeftMouseButton(e)) 
        return;
      
      // Start a new operation if the link has been clicked and is just waiting to
      // be dragged. This allows the ui to handle sloppy clicks better
      if(pressPoint != null) {
        
        // Check to see if this drag exceed the tolerance
        if(pressPoint.distance(e.getPoint()) >= DRAG_TOLERANCE) {
          
          Point2D pt = pressPoint;
          pressPoint = null;
          
          // Find the closest segment
          int segment = polyLink.segmentFor(pt, CLICK_TOLERANCE);
          if(segment == -1)
            return;

          fireToolStarted();
          
          // Try to use an existing point first. If nothing was close enough
          // split the segment and use the new point
          int point = polyLink.pointFor(pt.getX(), pt.getY(), CLICK_TOLERANCE*2.0);
          if(point > 0 && point < (polyLink.getPointCount() - 1))
            dragPoint = polyLink.getPN(point);
          else 
            dragPoint = polyLink.split(segment, pt);
          
          // Change the cursor on glass pane
          originalCursor = diagram.getCursor();
          diagram.setCursor(HD_CURSOR);
          
        }
        
      }
      
      // If a drag has been started, update the point
      if(dragPoint != null) {
        
        e.consume();    
        
        DiagramUI ui = (DiagramUI)diagram.getUI();
        
        // Damage & refresh
        ui.damageFigure(polyLink);
        dragPoint.setLocation(e.getPoint());
        ui.refreshFigure(polyLink);
        
      }
      
    }

    /**
     * Called when the dragging to stops. 
     *
     * @param MouseEvent
     */
    public void mouseReleased(MouseEvent e) {
      
      // If there was a drag behing handled by this tool cleanup
      if(dragPoint != null) {
        
        e.consume();
        // Force a repaint when appropriate
        ((DiagramUI)diagram.getUI()).refreshRegion(null);
       

        linkReleased(polyLink, e);
        
        fireToolFinished();
        reset();
        
      }
      
      // Clear the press point once the mouse is released
      // it may have been set, even if a drag was not started  
      
    }

  } /* MouseHandler */


  /**
   * Clean up
   */
  protected void reset() {

    // Reset cursor
    if(diagram != null && originalCursor != null)
      diagram.setCursor(originalCursor);

    // Clear variables
    diagram = null;
    dragPoint = null;
    originalCursor = null;
    polyLink = null;    
    pressPoint = null;

  }

}
