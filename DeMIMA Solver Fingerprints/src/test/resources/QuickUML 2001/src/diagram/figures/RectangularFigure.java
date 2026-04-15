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

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import diagram.Figure;

/**
 * @class RectangularFigure
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 */
public class RectangularFigure extends Rectangle2D.Double 
  implements Cloneable, Figure, Serializable {
  
  public RectangularFigure() { 
    this(0, 0, 0, 0);
  }

  public RectangularFigure(double w, double h) { 
    this(0, 0, w, h);
  }

  public RectangularFigure(double x, double y, double w, double h) { 
    setRect(x, y, w, h);
  }

  /**
   * Get the rectangular bounds of this figure.
   *
   * @param Rectangle2D, use to avoid allocating a new object
   * @return Rectangle2D
   */
  public Rectangle2D getBounds2D(Rectangle2D rc) {

    if(rc == null)
      return getBounds2D();

    rc.setRect(x, y, width, height);
    return rc;

  }

 
  /**
   * Get the center of this figure.
   *
   * @param Point2D, use to avoid allocating a new object
   * @return Point2D
   */
  public Point2D getCenter(Point2D pt) {
    
    if(pt == null)
      return new Point2D.Double(getCenterX(), getCenterY());

    pt.setLocation(getCenterX(), getCenterY());
    return pt;

  }



  /**
   * Translate this figure.
   *
   * @param double
   * @param double
   */
  public void translate(double x, double y) {

    this.x += x;
    this.y += y;

  }


  /**
   * Point on the boundary of this figure closest to some
   * point outside this figures.
   *
   * @param Point2D point outside this Figure to connect to
   * @param Point2D use to avoid allocating a new object
   * @return Point2D
   */
  public Point2D getConnection(Point2D ptFrom, Point2D pt) {
    
    if(contains(ptFrom)) {

      if(pt == null)
        pt = new Point2D.Double();

      pt.setLocation(ptFrom);
      return pt;

    }

    double right  = this.x + this.width - 1;
    double bottom = this.y + this.height - 1;
    
    double centerX = getCenterX();
    double centerY = getCenterY();
    
    double s, t;
    
    double dx = (ptFrom.getX() - centerX);
    double dy = (ptFrom.getY() - centerY);
    
    if(dx > 0)
      s = (right - centerX) / dx;
    else 
      s = (dx < 0) ? ((this.x - centerX) / dx) : java.lang.Double.POSITIVE_INFINITY;
    
    if(dy < 0)
      t = (this.y - centerY) / dy;
    else
      t = (dy > 0) ? ((bottom - centerY) / dy) : java.lang.Double.POSITIVE_INFINITY;
    
    double A = (s < t) ? (centerX + s * dx) : (centerX + t * dx);
    double B = (s < t) ? (centerY + s * dy) : (centerY + t * dy);
    
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

    this.x = x;
    this.y = y;
    this.width = w;
    this.height = h;

  }
 
  /**
   * Generate a hashcode that will not be affected by the bounds of the
   * Figure.
   */
  private static int generateLocalHash() {
    Class c = RectangularFigure.class;
    return (c.hashCode() + figureId++);
  }
  private static int figureId = 0;

  private int hash = generateLocalHash();

  public int hashCode() {
    return figureId;
  }



  private void writeObject(java.io.ObjectOutputStream out)
    throws java.io.IOException {
    
    out.writeDouble(x);
    out.writeDouble(y);
    out.writeDouble(width);
    out.writeDouble(height);

  }

  private void readObject(java.io.ObjectInputStream in)
    throws java.io.IOException, ClassNotFoundException {

    x = in.readDouble();
    y = in.readDouble();
    width = in.readDouble();
    height = in.readDouble();

  }

 
}
