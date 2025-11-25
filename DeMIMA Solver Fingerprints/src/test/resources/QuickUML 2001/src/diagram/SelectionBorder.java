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

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.AbstractBorder;

/**
 * @class SelectionBorder
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * Draws a set of squarez along the border of a Component
 */
public class SelectionBorder extends AbstractBorder {

  protected int thickness;
  protected Color lineColor;


  /**
   * Create a SelectionBorder of thickness 2 and foldThickness 6.
   */
  public SelectionBorder() {
    this(6, Color.lightGray);
  }

  /**
   * Create a new SelectionBorder
   *
   * @param int
   * @param Color
   *
   * @post if the Color is null the darker color of the component background
   * will be used.
   */
  public SelectionBorder(int thickness, Color lineColor) {

    this.thickness = thickness;
    this.lineColor = lineColor;

  }

  /**
   * Returns the insets of the border.
   * @param c the component for which this border insets value applies
   */
  public Insets getBorderInsets(Component c)       {
    return new Insets(thickness,thickness,thickness,thickness);
  }

  /**
   * Reinitialize the insets parameter with this Border's current Insets.
   * @param c the component for which this border insets value applies
   * @param insets the object to be reinitialized
   */
  public Insets getBorderInsets(Component c, Insets insets) {

    insets.left = insets.bottom = thickness;
    insets.right = insets.top = thickness;

    return insets;

  }

  /**
   * Returns the color of the border.
   *
   * @return Color
   */
  public Color getLineColor() {
    return lineColor;
  }

  /**
   * Returns the thickness of the border.
   *
   * @return int
   */
  public int getThickness() {
    return thickness;
  }

  /**
   * Paint the components border.
   *
   * @param Component
   * @param Graphics
   * @param int
   * @param int
   * @param int
   * @param int
   */
  public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
      
    //    Graphics2D g2 = (Graphics2D)g;

    int dx = (x+w)-thickness-1;
    int dy = (y+h)-thickness-1;

    Color fillColor = c.getBackground();
    g.setColor((fillColor == null) ? lineColor : fillColor.darker());

    g.fillRect(x, y, thickness, thickness);
    g.fillRect(dx, y, thickness, thickness);

    g.fillRect(x, dy, thickness, thickness);
    g.fillRect(dx, dy, thickness, thickness);

    g.fillRect((w/2)-(thickness/2), y, thickness, thickness);
    g.fillRect((w/2)-(thickness/2), dy, thickness, thickness);

    g.fillRect(x, (h/2)-(thickness/2), thickness, thickness);
    g.fillRect(dx, (h/2)-(thickness/2), thickness, thickness);

    g.setColor((lineColor == null) ? c.getBackground().darker() : lineColor.darker());

    g.drawRect(x, y, thickness, thickness);
    g.drawRect(dx, y, thickness, thickness);

    g.drawRect(x, dy, thickness, thickness);
    g.drawRect(dx, dy, thickness, thickness);

    g.drawRect((w/2)-(thickness/2), y, thickness, thickness);
    g.drawRect((w/2)-(thickness/2), dy, thickness, thickness);

    g.drawRect(x, (h/2)-(thickness/2), thickness, thickness);
    g.drawRect(dx, (h/2)-(thickness/2), thickness, thickness);

  }

}
