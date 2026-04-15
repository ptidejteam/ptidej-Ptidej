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

package diagram;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.BorderFactory;
import javax.swing.UIManager;

import diagram.figures.PolyLink;
import diagram.shape.ArrowHead;

/**
 * @class DefaultLinkRenderer
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * Default renderer for links
 */
public class DefaultLinkRenderer extends DefaultFigureRenderer {

  protected AffineTransform at = new AffineTransform();
  protected GeneralPath path = null;
  
  protected Point2D p1;
  protected Point2D p2;

  protected FontMetrics font;
  
  private Rectangle labelBounds = new Rectangle();
  private DefaultLabelRenderer labelRenderer; 
  private Component labelComponent;

  static {
    UIManager.put("link.foreground", Color.black);
    UIManager.put("link.background", Color.white);
  }


  /**
   * Create a new renderer for SimpleLink figures
   */
  public DefaultLinkRenderer() {
    this(new DefaultLabelRenderer());
  }

  /**
   * Create a new renderer for SimpleLink figures
   */
  public DefaultLinkRenderer(DefaultLabelRenderer labelRenderer) {
    
    setBackground(UIManager.getColor("link.background"));
    setForeground(UIManager.getColor("link.foreground"));

    labelRenderer.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
    labelRenderer.setOpaque(false);

    this.labelRenderer = labelRenderer;

  }

  /**
   * Make sure the decorated bounds are wide enough or tall enough for the label.
   */
  public Rectangle2D getDecoratedBounds(Diagram diagram, Figure figure, Rectangle2D rcBounds) {
    
    rcBounds = super.getDecoratedBounds(diagram, figure, rcBounds);
    
    // Force the label renderer to update
    labelComponent = labelRenderer.getRendererComponent(diagram, figure, false);
    if(labelRenderer.getText() != null) {

      // Update the label bounds
      labelBounds = (Rectangle)labelRenderer.getDecoratedBounds(diagram, figure, labelBounds);

      double x = Math.min(rcBounds.getX(), labelBounds.x);
      double y = Math.min(rcBounds.getY(), labelBounds.y);
      double w = Math.max(rcBounds.getWidth(), (labelBounds.x - x) + labelBounds.width);
      double h = Math.max(rcBounds.getHeight(), (labelBounds.y - y) + labelBounds.height);
     
      rcBounds.setFrame(x, y, w, h);
      
    } else 
      labelComponent = null;

    return rcBounds;  

  }
  
  public void paintComponent(Graphics g) {
    
    PolyLink link = (PolyLink)getFigure();
    
    Graphics2D g2 = (Graphics2D)g;
    GeneralPath path;
    
    double x1, x2, y1, y2;
    double angle;
    
    if(link != null) {
      
      // Get the offsets to start drawing this link
      double dx = getX(); 
      double dy = getY();
      
      g2.setPaint(getForeground());
      
      p2 = link.getPN(1, p2);
      p1 = link.getSource().getConnection(p2, p1);

      x1 = p1.getX() - dx; x2 = p2.getX() - dx;
      y1 = p1.getY() - dy; y2 = p2.getY() - dy;

      // Draw 1st line segment
      g2.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
      
      // Draw source   
      angle = getSourceAngle(x2, y2, x1, y1);

      
      if((path = getSourceEndpoint(x1, y1, this.path)) != null) {
        
        at.setToRotation(angle+Math.PI, x1, y1);
        paintSourceEndpoint(g2, at, path);
        
      }
      
      p2 = link.getPN(link.getPointCount() - 2, p2);
      p1 = link.getSink().getConnection(p2 , p1);
      
      x1 = p1.getX() - dx; x2 = p2.getX() - dx;
      y1 = p1.getY() - dy; y2 = p2.getY() - dy;
      
      angle = getSinkAngle(x2, y2, x1, y1);
      
      // Draw 2nd line segment
      if(link.getSegmentCount() > 1)
        g2.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
      
      // Draw sink
      if((path = getSinkEndpoint(x1, y1, this.path)) != null) {
        
        at.setToRotation(angle+Math.PI, x1, y1);
        paintSinkEndpoint(g2, at, path);
        
      }
      

      // Draw any odd segments left over
      for(int i = 1; i < link.getSegmentCount() - 1; i++)
        g2.drawLine((int)(link.getXN(i)-dx), (int)(link.getYN(i)-dy), 
                    (int)(link.getXN(i+1)-dx), (int)(link.getYN(i+1)-dy));


                       
      // Find the longest link, paint a label there if there is one
      if(labelComponent != null) {
        
        dx = labelBounds.x-getX();
        dy = labelBounds.y-getY();

        g2.translate(dx, dy);
        labelComponent.setBounds(labelBounds.x, labelBounds.y, 
                                 labelBounds.width, labelBounds.height);

        labelComponent.paint(g);
        g2.translate(-dx, -dy);
        
      }
      
    }
    
  }
  

  /**
   * Paint the source endpoint.
   *
   * @param Graphics2D
   * @param AffineTransform recommended transformation to rotate the endpoint correctly
   * @param GeneralPath recommended path to use when rendering the endpoint.
   */
  protected void paintSourceEndpoint(Graphics2D g2, AffineTransform at, GeneralPath path) {
    path.transform(at);
    g2.fill(path);
  }
  
  /**
   * Paint the sink endpoint.
   *
   * @param Graphics2D
   * @param AffineTransform recommended transformation to rotate the endpoint correctly
   * @param GeneralPath recommended path to use when rendering the endpoint.
   */
  protected void paintSinkEndpoint(Graphics2D g2, AffineTransform at, GeneralPath path) {
    path.transform(at);
    g2.fill(path);
  }
  
  
  /**
   * x1, y1 will be source connection coords
   */ 
  protected double getSourceAngle(double x1, double y1, double x2, double y2) {
    return getAngle(x1, y1, x2, y2); 
  }

  /**
   * x1, y1 will be sink connection coords
   */ 
  protected double getSinkAngle(double x1, double y1, double x2, double y2) {
    return getAngle(x1, y1, x2, y2); 
  } 

  /**
   * Calculate the angle between two points.
   *
   * @param double
   * @param double
   * @param double
   * @param double
   *
   * @return double angle in radians
   */
  final private double getAngle(double x1, double y1, double x2, double y2) {
    
    double angle;
    
    if((x2 - x1) == 0) {
      
      angle = Math.PI/2;
      return (y2 < y1) ? (angle + Math.PI) : angle; 
      
    }
    
    angle = Math.atan((y1-y2)/(x1-x2));
    return (x2 < x1) ? (angle + Math.PI) : angle; 
    
  }
  
  
  /**
   * Create the GeneralPath for a figure to be used as the source endpoint.
   * 
   * @param double x coordinate
   * @param double y coordinate
   * @param GeneralPath reuse a path
   *
   * @return GeneralPath
   */
  protected GeneralPath getSourceEndpoint(double x, double y, GeneralPath path) {
    return ArrowHead.createArrowHead(7.0, ArrowHead.FLAT, x, y, path);
  }
  
  /**
   * Create the GeneralPath for a figure to be used as the sink endpoint.
   * 
   * @param double x coordinate
   * @param double y coordinate
   * @param GeneralPath reuse a path
   *
   * @return GeneralPath
   */
  protected GeneralPath getSinkEndpoint(double x, double y, GeneralPath path) {
    return ArrowHead.createArrowHead(7.0, ArrowHead.FLAT, x, y, path);
  }
  
}
