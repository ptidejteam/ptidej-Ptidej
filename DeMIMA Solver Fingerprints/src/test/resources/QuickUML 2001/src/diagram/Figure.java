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

import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * @interface Figure
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 */
public interface Figure extends Shape {

  /**
   * Get the anchor for the Figure. This is usually the center
   * of the Figure but does not always have to be.
   *
   * @param Point2D use to avoid allocating a new object
   *
   * @return Point2D
   */
  public Point2D getAnchor(Point2D pt);

  /**
   * Get the rectangular bounds of this figure.
   *
   * @param Rectangle2D, use to avoid allocating a new object
   * @return Rectangle2D
   */
  public Rectangle2D getBounds2D(Rectangle2D rc);

  /**
   * Get the center of this figure.
   *
   * @param Point2D use to avoid allocating a new object
   * @return Point2D
   */
  public Point2D getCenter(Point2D pt);

  /**
   * Point on the boundary of this figure closest to some
   * point outside this figures.
   *
   * @param Point2D point outside this Figure to connect to
   * @param Point2D use to avoid allocating a new object
   * @return Point2D
   */
  public Point2D getConnection(Point2D ptFrom, Point2D pt);

  
  /**
   * Change the bounds for this Figure
   * 
   * @param double
   * @param double
   * @param double
   * @param double
   */
  public void setBounds(double x, double y, double w, double h); 

  /**
   * Translate this figure.
   *
   * @param double
   * @param double
   */
  public void translate(double x, double y);

  /**
   * Clone support
   */
  public Object clone();


}
