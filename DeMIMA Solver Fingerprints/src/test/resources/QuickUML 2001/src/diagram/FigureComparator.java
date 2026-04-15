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

import java.awt.Rectangle;
import java.util.Comparator;

/**
 * @class FigureComparator
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * Implement a comparator that can be used to compare various Figures to one
 * another. It considers both the figures class and its bounds when making a 
 * comparision
 */
public class FigureComparator implements Comparator {

  protected Rectangle r1 = new Rectangle();
  protected Rectangle r2 = new Rectangle();

  /**
   * Compare two figures to one another
   *
   * @param Object
   * @param Object
   *
   * @return int
   */
  public int compare(Object o1, Object o2) {

    Figure f1 = (Figure)o1;
    Figure f2 = (Figure)o2;

    // Check for equal reference
    if(f1 == f2 || o1.equals(f2))
      return 0;

    // Check for different classes
    Class c1 = f1.getClass();
    Class c2 = f2.getClass();

    if(c1 != c2) // Sort on class name 
      return c1.getName().compareTo(c2.getName());

    // Same class, sort on bounds
    r1 = (Rectangle)f1.getBounds2D(r1);
    r2 = (Rectangle)f2.getBounds2D(r2);

    //    if(r1.hashCode() == r2.hashCode())
    //  return 0;

    return (r1.hashCode() < r2.hashCode()) ? -1 : 1;

  }

}
