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
import diagram.Link;
import diagram.shape.PolyLine2D;

/**
 * @class PolyLink
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * Implements a Link between two Figures with many points in between
 */
public class PolyLink extends PolyLine2D.Double 
  implements Link, Serializable {

  protected Figure source;
  protected Figure sink;

  /**
   * Create a new simple link
   *
   * @param Figure
   * @param Figure
   */
  public PolyLink(Figure source, Figure sink) {

    if(source == null || sink == null)
      throw new IllegalArgumentException("null Figures not allowed");

    this.source = source;
    this.sink = sink;

  } 


  /**
   * Get the Figure for the source end of this 
   * Link.
   *
   * @return Figure
   */
  public Figure getSource() {
    return this.source;
  }

  /**
   * Get the Point the source Figure can use an anchor.
   *
   * @param Point2D
   * @return Point2D
   */
  public Point2D getSourceAnchor(Point2D pt) {
    return (pt = source.getAnchor(pt));
  }
  

  /**
   * Get the Figure for the sink end of this 
   * Link.
   *
   * @return Figure
   */
  public Figure getSink() {
    return this.sink;
  }

  /**
   * Get the Point the sink Figure can use an anchor.
   *
   * @param Point2D
   * @return Point2D
   */
  public Point2D getSinkAnchor(Point2D pt) {
    return (pt = sink.getAnchor(pt));
  }


  /**
   * Update the endpoints
   */
  private final void updateEndpoints() {

    getSourceAnchor(getP1());
    getSinkAnchor(getP2());

  }

  /**
   * Test for an intersection w/ some point.
   */
  public boolean contains(double x, double y, double tolerance) {
    updateEndpoints();
    return super.contains(x, y, tolerance);
  }
  
  /**
   * Get the rectangular bounds of this figure.
   *
   * @param Rectangle2D, use to avoid allocating a new object
   * @return Rectangle2D
   */ 
  public Rectangle2D getBounds2D(Rectangle2D rc) {
    updateEndpoints();
    return super.getBounds2D(rc);
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
    updateEndpoints();
    return getCenter(pt);
  }

  /**
   * Point on the boundary of this figure closest to some
   * point outside this figures.
   *
   * @param Point2D point outside this Figure to connect to
   * @param Point2D use to avoid allocating a new object
   *
   * @return Point2D get the closest non-endpoint
   */
  public Point2D getConnection(Point2D ptFrom, Point2D pt) {

    updateEndpoints();

    double dist = java.lang.Double.MAX_VALUE;

    for(int i=1; i < pointCount-1; i++) {
      
      double d = getPN(i).distance(ptFrom);
      if(d < dist) {

        dist = d;
        if(pt == null)
          pt = new Point2D.Double(getXN(i), getYN(i));
        else 
          pt.setLocation(getXN(i), getYN(i));

      }

    }

    return pt;

  }

  /**
   * Test for an intersection w/ some Rectangle
   */
  public boolean intersects(double x, double y, double w, double h) {
    updateEndpoints(); 
    return super.intersects(x, y, w, h);
  }

  /**
   *
   */
  public void setBounds(double x, double y, double w, double h) {
  }


  /**
   * Set the Figure for the source end of this 
   * Link.
   *
   * @param Figure
   * @return Figure
   */
  public Figure setSource(Figure figure) {

    Figure oldSource = source;

    source = figure;
    return oldSource;

  }

  /**
   * Set the Figure for the sink end of this 
   * Link.
   *
   * @param Figure
   * @return Figure
   */
  public Figure setSink(Figure figure) {

    Figure oldSink = sink;

    sink = figure;
    return oldSink;

  }
  
  /**
   * Hash on the links class.
   */
  public int hashCode() {
    return getClass().hashCode();
  }

}
