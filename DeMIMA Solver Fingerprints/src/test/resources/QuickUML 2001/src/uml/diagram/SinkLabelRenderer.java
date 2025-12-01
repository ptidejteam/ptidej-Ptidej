/**
 *
    QuickUML; A simple UML tool that demonstrates one use of the 
    Java Diagram Package 

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

package uml.diagram;

import java.awt.Point;

import diagram.DefaultLabelRenderer;
import diagram.figures.PolyLink;

/**
 * @class SinkLabelRenderer
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0 
 *
 * This label render will find its bounds along the last segment (closest to the sink)
 * and positions on the closest 1/4th of that segment
 */
public class SinkLabelRenderer extends DefaultLabelRenderer {

  /**
   * Get the segment closest to the sink
   *
   * @param PolyLink
   */
  protected void calculateSegment(PolyLink link) {

    p2 = (Point)link.getPN(link.getPointCount() - 2, p2);
    p1 = (Point)link.getSink().getConnection(p2, p1);

    // Move p2 closer to the sink
    p2.x += (p1.x - p2.x)/4;
    p2.y += (p1.y - p2.y)/4;

  }

}
