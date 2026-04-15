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

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.UIManager;

/**
 * @class FontTile
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 */
public class FontTile extends JButton {

  private String title;
  private String fontProperty;

  /**
   * Create a new ColorTilefor the given UIManager property.
   *
   * @param String property
   */
  public FontTile(String fontProperty) {
    this(fontProperty, "Choose a Color");
  }

  /**
   * Create a new ColorTilefor teh given UIManager property and the given title
   * on the font chooser.
   *
   * @param String property
   * @param String chooser title
   */
  public FontTile(String fontProperty, String title) {

    this.fontProperty = fontProperty;
    this.title = title;

    Font font = UIManager.getFont(fontProperty);
    if(font == null)
      font = UIManager.getFont("Label.font");

    if(font != null) {
      setFont(font);    
      setText(font.getName());
    }

    setHorizontalAlignment(JLabel.CENTER);

  }

  public Dimension getMinimumSize() {
    return getPreferredSize();
  }

  public Dimension getPreferredSize() {
    Dimension dim = super.getPreferredSize();
    dim.height = 35;
    return dim;
  }

  /**
   * Listen for press event
   */
  protected void fireActionPerformed(ActionEvent event) {

    Font font = JFontChooser.showDialog(this, title, UIManager.getFont(fontProperty));
    if(font != null) {

      UIManager.put(fontProperty, font);
      setText(font.getName());

    }

  }

  
}
