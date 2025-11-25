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

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;

/**
 * @class DefaultFigureEditor
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * 
 */
public class DefaultFigureEditor extends DefaultCellEditor 
  implements FigureEditor {

  /**
   *
   */
  public DefaultFigureEditor() {
    this(new JTextField());
  }

  /**
   *
   */
  public DefaultFigureEditor(JTextField comp) {
    super(comp);
    setClickCountToStart(2);
  }

  /**
   * This method is the first invoked when the editing process begins.
   *
   * @param Diagram surface that is being rendered upon
   * @param Figure item to edit
   * @param boolean
   *
   * @return suitable A Component that can render the given item is returned. 
   *
   * @post the Component returned will most likey be changed, in that it will
   * have been reparented & its bounds will be reset. Each time this Component
   * is used this will happen, so generally it is not neccessary for the 
   * returned item to bother setting its own size.
   */
  public Component getFigureEditorComponent(Diagram diagram, Figure figure, boolean isSelected) {

    JTextField comp = ((JTextField)getComponent());

    Object label = diagram.getModel().getValue(figure);
    comp.setText(label != null ? label.toString() : "");

    comp.setOpaque(true);
    comp.setForeground(diagram.getForeground());
    comp.setBorder(BorderFactory.createLineBorder(diagram.getBackground().darker()));

    return comp;

  }

  public Object getCellEditorValue() {
    return ((JTextField)getComponent()).getText();
  }


  /**
   * Get the extended bounds for a Figure, these are bounds that a component should use
   * to include a small area for decoration, such as different borders, or arrow heads
   * that fall just outside the Figures normal bounds.
   *
   * This allows editors to occupy a space different from the figure when neccessary.
   *
   * @param Diagram
   * @param Figure
   * @param Rectangle2D reuse a rectangle
   * 
   * @return Rectangle2D
   */
  public Rectangle2D getDecoratedBounds(Diagram diagram, Figure figure, Rectangle2D rcBounds) {

    FigureRenderer renderer = diagram.getFigureRenderer(figure.getClass());
    return renderer.getDecoratedBounds(diagram, figure, rcBounds);

  }

}
