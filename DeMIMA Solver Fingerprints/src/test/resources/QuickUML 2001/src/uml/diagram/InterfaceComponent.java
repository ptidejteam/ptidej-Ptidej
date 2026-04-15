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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import uml.ui.FlatTextArea;

/**
 * @class InterfaceComponent
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 */
public class InterfaceComponent extends CustomComponent {

  protected static final CustomUI interfaceUI = new CustomUI("interface");
  protected static final Insets margin = new Insets(1,1,1,1);

  protected JLabel label = new JLabel("<< interface >>", JLabel.CENTER);
  protected JTextField title = new JTextField();
  protected FlatTextArea members = new FlatTextArea(true);
    
  static { // Set up some default colors

    UIManager.put("interface.background", new Color(0xFF, 0xFF, 0xDD));
    UIManager.put("interface.foreground", Color.black);
    UIManager.put("interface.border", BorderFactory.createLineBorder(Color.black, 1));

  }

  /**
   * Create a new Component for painting interfaces
   */
  public InterfaceComponent() {

    // Layout the component
    this.setLayout(null);
      
    label.setOpaque(true);
    this.add(label);

    title.setOpaque(true);
    title.setHorizontalAlignment(JTextField.CENTER);
    title.setMargin(margin);
    title.setBorder(null);

    this.add(title);
      
    members.setBorder(BorderFactory.createLineBorder(Color.black, 1));    
    members.setMargin(margin);
    this.add(members);

    // Trigger the label to reset its font
    setUI(interfaceUI);
    setFont( title.getFont() );

  }

  public void setTitle(String s) {
    title.setText(s);
  }

  public String getTitle() {
    return title.getText();
  }

  public void setMembers(String s) {
    members.setText(s);
  }

  public String getMembers() {
    return members.getText();
  }

  public void setFont(Font font) {

    super.setFont(font);

    font = font.deriveFont(Font.ITALIC|Font.PLAIN, font.getSize() - 2.0f);
    label.setFont(font);

  }

  public void doLayout() {

    Insets insets = this.getInsets();

    int w = this.getWidth() - (insets.left + insets.right);
    int h = this.getHeight() - (insets.top + insets.bottom);
    
    int x = insets.left;
    int y = insets.top;

    // Layout the title across the top
    int componentHeight = label.getPreferredSize().height+2;
    label.reshape(x+1, y+1, w-2, componentHeight);

    // Shift down, insert room for a border
    y += componentHeight+1;
    h -= componentHeight+1;

    // Layout the title across the top
    componentHeight = title.getPreferredSize().height+2;
    title.setBounds(x+1, y, w-2, componentHeight);

    // Shift down, insert room for a border
    y += componentHeight+1;
    h -= componentHeight+1;

    // Layout the members at the bottom 
    componentHeight = h;
    members.setBounds(x, y, w, componentHeight);

  }

  /**
   * Paint the normal border, and the border around the label & the text field
   */
  public void paintBorder(Graphics g) {

    super.paintBorder(g);

    Insets insets = this.getInsets();

    int x = insets.left;
    int y = insets.top;
    int w = label.getWidth() + 1;
    int h = label.getHeight() + title.getHeight() + 1;

    g.setColor(Color.black);
    g.drawRect(x, y, w, h);

  }

}
