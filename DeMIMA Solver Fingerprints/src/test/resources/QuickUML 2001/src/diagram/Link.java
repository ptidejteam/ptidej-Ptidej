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

import java.awt.geom.Point2D;

/**
 * @interface Link
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * A Link is shape, usually a Line, that exists between two Figures.
 */
public interface Link extends Figure {
  
  /**
   * Get the Figure for the source end of this 
   * Link.
   *
   * @return Figure
   */
  public Figure getSource();

  /**
   * Set the Figure for the source end of this 
   * Link.
   *
   * @param Figure
   * @return Figure old Figure
   */
  public Figure setSource(Figure figure);

  /**
   * Get the Point the source Figure can use an anchor.
   *
   * @param Point2D
   * @return Point2D
   */
  public Point2D getSourceAnchor(Point2D pt);

  /**
   * Get the Figure for the sink end of this 
   * Link.
   *
   * @return Figure
   */
  public Figure getSink();

  /**
   * Set the Figure for the sink end of this 
   * Link.
   *
   * @param Figure
   * @return Figure old Figure
   */
  public Figure setSink(Figure figure);

  /**
   * Get the Point the sink Figure can use an anchor.
   *
   * @param Point2D
   * @return Point2D
   */
  public Point2D getSinkAnchor(Point2D pt);

  /**
   * Test for an intersection w/ some point.
   *
   * @param Point2D
   * @return boolean
   */
  //  public boolean contains(Point2D pt);

  /**
   * Test for an intersection w/ some Rectangle
   *
   * @param Point2D
   * @return boolean
   */
  //  public boolean intersects(Rectangle2D rc);


}


