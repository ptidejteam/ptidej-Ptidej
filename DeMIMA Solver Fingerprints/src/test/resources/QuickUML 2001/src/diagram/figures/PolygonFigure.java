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

package diagram.figures;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import diagram.Figure;

/**
 * @class PolygonFigure
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 * 
 */
public class PolygonFigure extends Polygon
  implements Cloneable, Figure, Serializable {

  /**
   * Get the rectangular bounds of this figure.
   *
   * @param Rectangle2D, use to avoid allocating a new object
   * @return Rectangle2D
   */
  public Rectangle2D getBounds2D(Rectangle2D rc) {

    if(rc == null)
      return new Rectangle2D.Double(bounds.x, bounds.y, bounds.width, bounds.height);

    rc.setRect(bounds.x, bounds.y, bounds.width, bounds.height);
    return rc;

  }

  /**
   * Get the center of this figure.
   *
   * @param Point2D use to avoid allocating a new object
   * @return Point2D
   */
  public Point2D getCenter(Point2D pt) {

    double centerX = bounds.x + bounds.width/2;
    double centerY = bounds.y + bounds.height/2;
    
    if(pt == null)
      return new Point2D.Double(centerX, centerY);

    pt.setLocation(centerX, centerY);
    return pt;

  }


  /**
   * Translate this figure.
   *
   * @param double
   * @param double
   */
  public void translate(double x, double y) {
    super.translate((int)x, (int)y);
  }
  

  /**
   * Point on the boundary of this figure closest to some
   * point outside this figures.
   *
   * @param Point2D point outside this Figure to connect to
   * @param Point2D use to avoid allocating a new object
   *
   * @return Point2D
   */
  public Point2D getConnection(Point2D ptFrom, Point2D pt) {
    
    if(contains(ptFrom)) {

      if(pt == null)
        pt = new Point2D.Double();

      pt.setLocation(ptFrom);
      return pt;

    }
    
    // compute the intersection of the line segment passing through
    // (x0,y0) and (x1,y1) with the ray passing through
    // (xCenter, yCenter) and (px,py)
    double x0,x1,y0,y1;
    // Get the angle 
    
    double centerX = bounds.x + bounds.width/2;
    double centerY = bounds.y + bounds.height/2;
    
    double dy = (ptFrom.getY() - centerY);
    double dx = (ptFrom.getX() - centerX);
    
    double theta = Math.atan2(dy, dx);
    
    double px = centerX + Math.cos(theta);
    double py = centerY + Math.sin(theta);
    
    double A = 0, B = 0, max = 0;
    
    x1 = this.xpoints[this.npoints-1];
    y1 = this.ypoints[this.npoints-1];

    for(int i=0; i < this.npoints; i++) {
      
      x0 = x1;
      y0 = y1;
      x1 = this.xpoints[i];
      y1 = this.ypoints[i];
        
      double n = (x0-centerX)*(py-centerY) - (y0-centerY)*(px-centerX);
      double m = (y1-y0)*(px-centerX) - (x1-x0)*(py-centerY);
      double t = n/m;
      
      
      if(0 <= t && t <= 1) {
        
        double tx = x0 + (x1-x0)*t;
        double ty = y0 + (y1-y0)*t;
        
        boolean xGood = (tx >= centerX && px >= centerX)||(tx < centerX && px < centerX);
        boolean yGood = (ty >= centerY && py >= centerY)||(ty < centerY && py < centerY);
        
        
        if(xGood && yGood) {
          double r = (tx-centerX)*(tx-centerX) + (ty-centerY)*(ty-centerY);
          
          if(r > max) {
            A = tx;
            B = ty;
            max = r;
          }
        }
        
      }
      
    }

    if(pt == null)
      return new Point2D.Double(A, B);
    
    pt.setLocation(A, B);
    return pt;

  }

  /**
   * Get the anchor for the Figure. This is usually the center
   * of the Figure but does not always have to be.
   *
   * @param Point2D use to avoid allocating a new object
   *
   * @return Point2D
   */
  public Point2D getAnchor(Point2D pt) {
    return getCenter(pt);
  }

  /**
   * Reshape this figure.
   *
   * @param double
   * @param double
   * @param double
   * @param double
   */
  public void setBounds(double x, double y, double w, double h) {
    
  }

  public Object clone() {

    PolygonFigure fig = new PolygonFigure();

    fig.bounds = (Rectangle)bounds.clone();
    fig.npoints = npoints;

    fig.xpoints = new int[xpoints.length];
    fig.ypoints = new int[ypoints.length];

    System.arraycopy(xpoints, 0, fig.xpoints, 0, xpoints.length);
    System.arraycopy(ypoints, 0, fig.ypoints, 0, ypoints.length);

    return fig;

  }

  /**
   * Generate a hashcode that will not be affected by the bounds of the
   * Figure.
   */
  private static int generateLocalHash() {
    Class c = PolygonFigure.class;
    return (c.hashCode() + figureId++);
  }

  private int hash = generateLocalHash();
  private static int figureId = 0;

  public int hashCode() {
    return figureId;
  }

}
