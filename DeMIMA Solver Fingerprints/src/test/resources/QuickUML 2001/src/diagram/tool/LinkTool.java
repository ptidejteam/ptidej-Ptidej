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

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.event.MouseInputAdapter;

import diagram.Diagram;
import diagram.DiagramUI;
import diagram.Figure;
import diagram.Link;
import diagram.figures.PointFigure;
import diagram.figures.PolyLink;

/**
 * @class LinkTool 
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * This tool allows a Link to be added to a Diagram model. The Link must
 * be anchored in a (non-Link) Figure and its sink will be at the current
 * mouse position until it can be placed into another (non-Link) Figure
 */
public class LinkTool extends AbstractTool {

  private final static double POINT_SIZE = 5;

  private Diagram diagram;

  private Figure figSource;
  private Figure figSink;

  private PointFigure ptSink = new PointFigure(POINT_SIZE);
  private Point2D ptCenter;

  private Link link;

  private MouseHandler mouseHandler = new MouseHandler();

  /**
   * Install support for something in the given Diagram
   *
   * @param Diagram
   */
  public void install(Diagram diagram) {


    diagram.addMouseListener(mouseHandler);
    diagram.addMouseMotionListener(mouseHandler);

  }

  /**
   * Remove support for something that was previously installed.
   *
   * @param Diagram
   */
  public void uninstall(Diagram diagram) {

    diagram.removeMouseListener(mouseHandler);
    diagram.removeMouseMotionListener(mouseHandler);

    reset();

  }
  

  protected class MouseHandler extends MouseInputAdapter {


    /**
     * Mouse pressed.
     *
     * @param MouseEvent 
     */
    public void mousePressed(MouseEvent e) {
      
      // Start the drag
      Object o = e.getSource();
      
      if(!e.isConsumed() && e.getClickCount() == 1 && (o instanceof Diagram)) {
        
        diagram = (Diagram)o;
        
        // Find the figure clicked on
        Point2D pt = e.getPoint();
        if(((figSource = (Figure)diagram.findFigure(pt)) == null) || (figSource instanceof Link)) {
          reset();
          return;
        }
        
        e.consume();
        fireToolStarted();

        
        // Create an end point to drag the line with for now...
        ptSink.setLocation(pt.getX(), pt.getY());
        link = createLink(figSource, ptSink);
        
        // Display the link
        diagram.getModel().add(link);
        
        // The moving endpoint is not considered related
        DiagramUI ui = (DiagramUI)diagram.getUI();
        ui.removeConnection(ptSink, link);    
        
      }
      
    }
    
    
    /**
     * Mouse dragged. 
     *
     * @param MouseEvent
     */
    public void mouseDragged(MouseEvent e) {
      
      if(diagram == null)
        return;
      
      Point2D pt = e.getPoint();   
      DiagramUI ui = (DiagramUI)diagram.getUI();
      
      // Damage the link that is moving
      ui.damageFigure(link);
      
      // Find the figure beneath the mouse, attach the link if possible
      // The figure is not another link, the figure is not the source figure
      // and the figure is not the sink fiure
      Figure sink = (Figure)diagram.findFigure(pt);
      
      if(sink != null && !(sink instanceof Link) && sink != figSource && sink != figSink) {
        
        ui.addConnection(sink, link);
        link.setSink(sink);
        
        figSink = sink;
        
        // Otherwise, attach to the sink point & move it
      } else if(sink != figSink) { 
        
        ptSink.setLocation(pt.getX(), pt.getY());
        
        // Attach the sink point if needed, remove the connection to the last figure
        if(figSink != ptSink) {
          ui.removeConnection(link.setSink(ptSink), link);    
          figSink = ptSink;
          
        }
        
      }
      
      ui.refreshFigure(link);
      
    }
    
    /**
     * Mouse released.
     *
     * @param MouseEvent
     */
    public void mouseReleased(MouseEvent e) {
      
      if(diagram == null)
        return;
      
      e.consume();

      DiagramUI ui = (DiagramUI)diagram.getUI();
      ui.damageFigure(link);
      
      // If the link was not anchored (or never moved), remove it
      if(figSink == null || figSink == ptSink)
        diagram.getModel().remove(link);
      
      else {
        
        // Replace the link if needed
        Link finalLink = finalizeLink(link);
        if(finalLink != link) {
          diagram.getModel().remove(link);
          diagram.getModel().add(finalLink);
        }
        
        ui.refreshFigure(finalLink);
        
      }
      
      fireToolFinished();
      reset();
      
    }
    
  } /* MouseHandler */


  /**
   * Cleanup
   */
  protected void reset() {

    diagram = null;
    figSink = null;
    figSource = null;
    link = null;

  }

  /**
   * Create the Figure for the link
   *
   * @param Figure source end
   * @param Figure sink end
   *
   * @return Link
   */
  protected Link createLink(Figure source, Figure sink) {
    return new PolyLink(source, sink);
  }

  /**
   * This method provides an opportunity to replace the link when the shapping is done.
   *
   * @param Link
   * @return Link
   */
  protected Link finalizeLink(Link link) {
    return link;
  }

}
