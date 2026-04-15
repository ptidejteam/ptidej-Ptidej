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
import java.awt.Graphics;

import javax.swing.plaf.basic.BasicArrowButton;

/**
 * @class FlatArrowButton
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * This is a flat-style arrow button.
 *
 * TODO: Update to make a custom UI component for this
 */
public class FlatArrowButton extends BasicArrowButton {

  public FlatArrowButton(int orientation) {
    super(orientation);
  }
  
  public Dimension getPreferredSize() {
    return new Dimension(8, 8);
  }
  
  public void paint(Graphics g) {
    
    int w, h, size;

    w = getSize().width;
    h = getSize().height;

    // If there's no room to draw arrow, bail
    if(h >= 5 && w >= 5) {

      // Draw the arrow
      size = Math.min((h - 4) / 3, (w - 4) / 3);
      size = Math.max(size, 4);
	    paintTriangle(g, (w - size) / 2, (h - size) / 2, size, direction, isEnabled());

    }


  }

}
