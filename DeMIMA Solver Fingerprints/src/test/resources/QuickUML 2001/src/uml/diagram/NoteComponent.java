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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;

import javax.swing.UIManager;

import uml.ui.FlatTextArea;

/**
 * @class NoteComponent
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * Component used to draw the Note on a Diagram
 */
public class NoteComponent extends CustomComponent {

  protected static final CustomUI noteUI = new CustomUI("note");
  protected static final Insets margin = new Insets(1,1,1,1);
  
  protected FlatTextArea text = new FlatTextArea(true);

  static { // Set up some default colors

    UIManager.put("note.background", new Color(0xFF, 0xFF, 0xEE));
    UIManager.put("note.foreground", Color.black);
    UIManager.put("note.border", new NoteBorder());

  }

  /**
   * Create a new Component for painting Notes
   */
  public NoteComponent() {

    // Layout the component
    setLayout(new BorderLayout());

    text.setBorder(null);
    text.setMargin(margin);
    add(text);

    setUI(noteUI);

  }

  public void setText(String s) {
    text.setText(s);
  }
  
  public String getText() {
    return text.getText();
  }

 
}
