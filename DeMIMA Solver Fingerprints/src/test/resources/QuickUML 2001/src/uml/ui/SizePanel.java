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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @class SizePanel
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * This is a simple Component for displaying and changing another Components
 * dimensions.
 */
public class SizePanel extends JPanel {

  protected Dimension size = new Dimension();

  protected JTextField txtWidth = new JTextField();
  protected JTextField txtHeight = new JTextField();

  /**
   * Create a panel that will edit the size of a given Component
   *
   * @param Component
   */
  public SizePanel(Component component) {
    this(component.getWidth(), component.getHeight());
  }

  /**
   * Create a panel that will edit the sizes given 
   *
   * @param int
   * @param int
   */
  public SizePanel(int width, int height) {

    size.width = width;
    size.height = height;

    GridBagLayout gridbag = new GridBagLayout();
    GridBagConstraints gc = new GridBagConstraints();

    setLayout(gridbag);
    

    Component comp = new JLabel("Width:");
    
    gc.anchor = GridBagConstraints.WEST;
    gc.weightx = 1;
    gc.gridwidth = 1;
    
    gridbag.setConstraints(comp, gc);
    add(comp);

    comp = txtWidth;
    txtWidth.setText(Integer.toString(size.width));

    gc.gridwidth = GridBagConstraints.REMAINDER;
    gc.fill = GridBagConstraints.HORIZONTAL;
    gridbag.setConstraints(comp, gc);
    add(comp);

    comp = new JLabel("Height:");
    
    gc.weightx = 1;
    gc.gridwidth = 1;
    gc.fill = GridBagConstraints.NONE;
    
    gridbag.setConstraints(comp, gc);
    add(comp);

    comp = txtHeight;
    txtHeight.setText(Integer.toString(size.height));

    gc.gridwidth = GridBagConstraints.REMAINDER;
    gc.fill = GridBagConstraints.HORIZONTAL;

    gridbag.setConstraints(comp, gc);
    add(comp);

  }


  public int getSelectedWidth() {

    try {
      size.width = Integer.parseInt(txtWidth.getText());
    } catch(Throwable t) {}

    return size.width;

  }

  public int getSelectedHeight() {

    try {
      size.height = Integer.parseInt(txtHeight.getText());
    } catch(Throwable t) {}

    return size.height;

  }


  /**
   * Get the chosen dimension
   */
  public Dimension getDimension(Dimension d) {

    if(d == null)
      d = new Dimension();

    d.width = getSelectedWidth();
    d.height = getSelectedHeight();
 
    return d;
  }

}
