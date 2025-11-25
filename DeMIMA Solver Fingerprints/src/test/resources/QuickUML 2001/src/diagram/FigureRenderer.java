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

import java.awt.Component;
import java.awt.geom.Rectangle2D;

/**
 * @interface FigureRenderer
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * Objects implementing this interface are responsible for selecting and 
 * configuring a Component that can be used to paint a representation of 
 * a Figure on a Diagram. 
 *
 * FigureRenderers can also take into account that some representations 
 * (painting highlights, labels, etc) that might fall outside the normal 
 * bounds of the Figure.
 */
public interface FigureRenderer {

  /**
   * This method is invoked when a Component that can render the given item
   * is needed.
   *
   * @param Diagram surface that is being rendered upon
   * @param Figure item to draw
   * @param boolean is the item selected
   * 
   * @return suitable Component 
   *
   * @post the Component returned will most likey be changed, in that it will
   * have been reparented & its bounds will be reset. Each time this Component
   * is used this will happen, so generally it is not neccessary for the 
   * returned item to bother setting its own size.
   */
  public Component getRendererComponent(Diagram diagram, Figure figure, boolean isSelected);


  /**
   * Get the extended bounds for a Figure, these are bounds that a component should use
   * to include a small area for decoration, such as different borders, or arrow heads
   * that fall just outside the Figures normal bounds.
   *
   * This allows extra space on the display to be associated with a Figure but without
   * affecting the Figures actual bounds.
   *
   * @param Diagram
   * @param Figure
   * @param Rectangle2D reuse a rectangle
   * 
   * @return Rectangle2D
   *
   * @post the bounds returned should not be smaller than the normal Figure bounds
   */
  public Rectangle2D getDecoratedBounds(Diagram diagram, Figure figure, Rectangle2D rcBounds);

}
