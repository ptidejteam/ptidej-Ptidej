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

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.io.Serializable;

/**
 * @class AbstractLayout
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 */
public abstract class AbstractLayout 
  implements LayoutManager2, Serializable {

  protected int hgap;
  protected int vgap;
  
  public AbstractLayout()   {
    this(0, 0);
  }
  
  public AbstractLayout(int hgap, int vgap) {
    this.hgap = hgap;
    this.vgap = vgap;
  }

  /**
   * Get the horizontal gap between components.
   */
  public int getHgap() {
    return hgap;
  }

  /**
   * Get the vertical gap between components.
   */
  public int getVgap() {
    return vgap; 
  }

  /**
   * Set the horizontal gap between components.
   * @param gap The horizontal gap to be set
   */
  public void setHgap(int hgap) {
    this.hgap = hgap;
  }
  
  /**
   * Set the vertical gap between components.
   * @param gap The vertical gap to be set
   */
  public void setVgap(int vgap) {
    this.vgap = vgap;
  }

  /**
   * Returns the maximum dimensions for this layout given
   * the component in the specified target container.
   * @param target The component which needs to be laid out
   */
  public Dimension maximumLayoutSize(Container target) {
    return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
  }

  /**
   * Adds the specified component with the specified name
   * to the layout. By default, we call the more recent
   * addLayoutComponent method with an object constraint
   * argument. The name is passed through directly.
   * @param name The name of the component
   * @param comp The component to be added
   */
  public void addLayoutComponent(String name, Component comp) {
    addLayoutComponent(comp, name);
  }
  
  /**
   * Use the parent containers mimimum size.
   */
  public Dimension minimumLayoutSize(Container parent) {
    return parent.getMinimumSize();
  }


  /**
   * Use the parent containers preferred size.
   */
  public Dimension preferredLayoutSize(Container parent) {
    return parent.getPreferredSize();
  }
  
  
  /**
   * Removes the specified component from the layout.
   * By default, we let the Container handle this directly.
   * @param comp the component to be removed
   */
  public void removeLayoutComponent(Component comp) {
    throw new UnsupportedOperationException();
  }

  /**
   * Returns the alignment along the y axis. This specifies how
   * the component would like to be aligned relative to other 
   * components. The value should be a number between 0 and 1
   * where 0 represents alignment along the origin, 1 is aligned
   * the furthest away from the origin, 0.5 is centered, etc.
   */
  public float getLayoutAlignmentY(Container target) {
    return 0.05f;
  }

  /**
   * Returns the alignment along the x axis. This specifies how
   * the component would like to be aligned relative to other 
   * components. The value should be a number between 0 and 1
   * where 0 represents alignment along the origin, 1 is aligned
   * the furthest away from the origin, 0.5 is centered, etc.
   */
  public float getLayoutAlignmentX(Container target) {
    return 0.05f;
  }

  /**
   * Return a string representation of the layout manager
   */
  public String toString() {

    StringBuffer buf = new StringBuffer();

    buf.append(getClass().getName());
    buf.append("[hgap=").append(hgap);
    buf.append(",vgap=").append(vgap).append("]");

    return buf.toString();

  }

}
