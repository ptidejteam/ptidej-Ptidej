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
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import uml.ui.FlatSplitPane;
import uml.ui.FlatTextArea;

/**
 * @class ClassComponent
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 */
public class ClassEditorComponent extends CustomComponent {

  protected static final CustomUI classUI = new CustomUI("class");
  protected static final Insets margin = new Insets(1,1,1,1);

  protected JTextField title = new JTextField();
  protected FlatSplitPane pane;
  protected FlatTextArea fields = new FlatTextArea(true);
  protected FlatTextArea members = new FlatTextArea(true);

  /**
   * Create a new Component for painting classes
   */
  public ClassEditorComponent() {

    // Layout the component
    this.setLayout(null);
      
    // Title area
    title.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    title.setOpaque(true);
    title.setMargin(margin);
    title.setHorizontalAlignment(JTextField.CENTER);

    this.add(title);

    // Field text area
    fields.setBorder(null);
    fields.setMargin(margin);

    // Member text area
    members.setBorder(null);
    members.setMargin(margin);

    pane = new FlatSplitPane(fields, members);
    pane.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    pane.setDividerSize(2);
    
    this.add(pane);

    setUI(classUI);
    setBorder(BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(new diagram.SelectionBorder(), BorderFactory.createEmptyBorder(2,2,2,2)),getBorder()));

  }

  public void setEnabled(boolean enabled) {
    super.setEnabled(enabled);
    pane.setEnabled(enabled);
  }

  public int getDividerLocation() {
    return pane.getDividerLocation();
  }

  public void setDividerLocation(int lastDividerLocation) {
    pane.setDividerLocation(lastDividerLocation);
  }


  public void setTitle(String s) {
    title.setText(s);
  }

  public String getTitle() {
    return title.getText();
  }

  public void setFields(String s) {
    fields.setText(s);
  }

  public String getFields() {
    return fields.getText();
  }

  public void setMembers(String s) {
    members.setText(s);
  }

  public String getMembers() {
    return members.getText();
  }
  
  /**
   * Create a built in layout, there seems to be a bug with current LayoutManagers
   * placing TextAreas in scroll panes with borders in the same component correctly.
   * They leave an extra pixel at the bottom with the hieght would be an odd number.
   *
   * This will garuntee the component will be laid out as expected.
   */
  public void doLayout() {

    Insets insets = this.getInsets();

    int w = this.getWidth() - (insets.left + insets.right);
    int h = this.getHeight() - (insets.top + insets.bottom);
    
    int x = insets.left;
    int y = insets.top;

    // Layout the title across the top
    int componentHeight = title.getPreferredSize().height + 2;

    title.setBounds(x, y, w, componentHeight);

    // Shift down
    y += componentHeight;
    h -= componentHeight;
    /*
    // Layout the fields in the middle
    componentHeight = (int)((double)h*(3.0/8.0));
    fields.setBounds(x, y, w, componentHeight);
    
    // Shift down
    y += componentHeight;
    h -= componentHeight;

    // Layout the members at the bottom 
    componentHeight = h;
    members.setBounds(x, y, w, componentHeight);
    */
    pane.setBounds(x, y, w, h);
  }

}
