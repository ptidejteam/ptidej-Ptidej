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

import javax.swing.JComponent;

/**
 * @class DefaultFigureRenderer
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 * 
 * The DefaultFigureRenderer provides a simple base for creating new renderers.
 * It will return a a transparent JComponent whose bounds can be set to the 
 * decorated bounds (getDecoratedBounds) and centered within it will be a user
 * supplied component set to the figure bounds.
 */
public class DefaultFigureRenderer extends JComponent
  implements FigureRenderer {

  private final static double DEFAULT_EXPANSION = 8.0;
  private final static SelectionBorder selectionBorder = new SelectionBorder();

  // Solid component to center within the transparent one
  private Component userComponent = null;

  private Figure lastFigure = null;
  private Diagram lastDiagram = null;
  
  /**
   * Create an empty renderer
   */
  public DefaultFigureRenderer() {
    this(null);
  }

  /**
   * Create a renderer that will use the given component to draw the solid
   * figure in the transparent frame provided by the DefaultRenderer
   *
   * @param Component
   */
  public DefaultFigureRenderer(Component userComponent) {

    setLayout(null);
    setUserComponent(userComponent);

  }

  /**
   * The getUserComponent() method is should be overridden to customize this 
   * renderer.
   *
   * @param Diagram surface that is being rendered upon
   * @param Figure item to draw
   * @param boolean is the item selected
   *
   * @return transparent Component contained user supplied Component
   */
  public Component getRendererComponent(Diagram diagram, Figure figure, boolean isSelected) {

    lastFigure = figure;
    lastDiagram = diagram;

    // Get the component to render within the bounds 
    Component component = getUserComponent();
    if(userComponent != component) 
      setUserComponent(component);

    setBorder( (isSelected) ? selectionBorder : null );

    return this;
    
  }

  /**
   * Get the Component to be fixed to the Figure bounds.
   */

  public void setUserComponent(Component component) {
    
    if(component != userComponent) {

      userComponent = component;
      removeAll();
      
      if(component != null)
        add(component);  

    }

  }

  /**
   * Get the Component to be fixed to the Figure bounds.
   */
  public Component getUserComponent() {
    return userComponent;
  }

  /**
   * Get the last Figure
   */
  public Figure getFigure() {
    return lastFigure;
  }

  /**
   * Get the last Diagram
   */
  public Diagram getDiagram() {
    return lastDiagram;
  }


  /**
   * Get the extended bounds for a Figure, these are bounds that a component should use
   * to include a small area for decoration, such as different borders, or arrow heads
   * that fall just outside the Figures normal bounds.
   *
   * @param Diagram
   * @param Figure
   * @param Rectangle2D reuse a rectangle
   * 
   * @return Rectangle2D
   */
  public Rectangle2D getDecoratedBounds(Diagram diagram, Figure figure, Rectangle2D rcBounds) {

    double expansion = getExpansion();

    rcBounds = figure.getBounds2D(rcBounds);
    rcBounds.setFrame( rcBounds.getX() - expansion, 
                       rcBounds.getY() - expansion, 
                       rcBounds.getWidth() + 2*expansion, 
                       rcBounds.getHeight() + 2*expansion);

    return rcBounds; 

  }

  /**
   * Get the amount the expand the Figures bounds, in order to determine the 
   * decorated bounds. Default is to expand the Figure bounds by 8 pixels.
   *
   * @return double
   */
  protected double getExpansion() { 
    return DEFAULT_EXPANSION; 
  }


  /**
   * Keep the user supplied Component centered within the JComponent used for this
   * Renderer.
   *
   * @param int
   * @param int
   * @param int
   * @param int
   */
  public void setBounds(int x, int y, int w, int h) {
    
    super.setBounds(x, y, w, h);

    int ex = (int)getExpansion();
    if(userComponent != null)
      userComponent.setBounds(ex, ex, w-2*ex, h-2*ex);

  }


}
