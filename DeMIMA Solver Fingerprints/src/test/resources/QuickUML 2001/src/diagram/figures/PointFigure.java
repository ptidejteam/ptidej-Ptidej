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



/**
 * @class PointFigure
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * This figure implements a fixed size circular figure
 */
public class PointFigure extends CircularFigure {

  public PointFigure() {
    this(0);
  }

  public PointFigure(double diameter) {
    this(0, 0, diameter);
  }

  public PointFigure(double x, double y, double diameter) {
    super(x, y, diameter, diameter);
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
    super.setBounds(x, y, (w > h) ? w: h, (w > h) ? w: h);
  }

  /**
   * Move the figure
   * 
   * @param double
   * @param double
   */
  public void setLocation(double x, double y) {
    this.x = x;
    this.y = y;
  }

}
