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

import diagram.Figure;

/**
 * @class FigureBorder
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * Utility for dealing with a Figures border
 */
public class FigureBorder {

  /**
   * Test a point to see if it lies on the boundary of a Figure.
   *
   * @param Figure
   * @param Point2D
   *
   * @return boolean
   */
  public static boolean isBorderPoint(Figure figure, Point2D pt) {
    return isBorderPoint(figure, pt, 8.0); 
  }

  /**
   * Test a point to see if it lies on the boundary of a Figure.
   *
   * @param Figure
   * @param Point2D
   *
   * @return boolean
   */
  public static boolean isBorderPoint(Figure figure, Point2D pt, double tolerance) {

    double pressX = pt.getX();
    double pressY = pt.getY();
    
    return (!figure.contains(pressX + tolerance, pressY + tolerance) ||
            !figure.contains(pressX + tolerance, pressY - tolerance) ||
            !figure.contains(pressX - tolerance, pressY + tolerance) ||
            !figure.contains(pressX - tolerance, pressY - tolerance));

  }

}
