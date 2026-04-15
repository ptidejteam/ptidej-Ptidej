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

package uml.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.UIManager;

/**
 * @class ColorTile
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * Colored button that will display the color of given UIManager property and
 * display a JColorChooser that will edit that color when it is pressed.
 */
public class ColorTile extends JButton {

  private String title;
  private String colorProperty;

  /**
   * Create a new ColorTilefor teh given UIManager property.
   *
   * @param String property
   */
  public ColorTile(String colorProperty) {
    this(colorProperty, "Choose a Color");
  }

  /**
   * Create a new ColorTilefor teh given UIManager property and the given title
   * on the color chooser.
   *
   * @param String property
   * @param String chooser title
   */
  public ColorTile(String colorProperty, String title) {

    this.colorProperty = colorProperty;
    this.title = title;

    Dimension sz = new Dimension(32,32);

    setMinimumSize(sz);
    setMaximumSize(sz);

    setBackground(UIManager.getColor(colorProperty));

  }

  /**
   * Listen for press event
   */
  protected void fireActionPerformed(ActionEvent event) {

    Color c = JColorChooser.showDialog(this, title, UIManager.getColor(colorProperty));
    if(c != null)
      UIManager.put(colorProperty, c);

  }

}
