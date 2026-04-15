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
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * @class ColorAction
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 */
public class ColorAction extends AbstractAction {
  
  private JComponent comp;
  private ColorPanel panel;
  
  public ColorAction(JComponent comp, String[] properties) {
    
    super("Colors ...");
    this.comp = comp;
    this.panel = new ColorPanel(properties);
    
  }
  
  public void actionPerformed(ActionEvent e) {
    
    JOptionPane.showMessageDialog(comp, panel, "Change colors",
                                  JOptionPane.PLAIN_MESSAGE);
    
    // Force repaint to update the color changes.
    comp.paintImmediately(0, 0, comp.getWidth(), comp.getHeight());
    
  }
  

  /**
   * @class ColorPanel
   *
   * Panel for displaying a set of Color that are mapped to UIManager settings.
   */
  public class ColorPanel extends JPanel 
    implements PropertyChangeListener {
    
    protected HashMap tiles = new HashMap();
    
    /**
     * Create a panel for editing the given set of UIManager color
     * properties.
     *
     * @param String[]
     */
    protected ColorPanel(String[] colorProperties) {
      
      UIManager.getDefaults().addPropertyChangeListener(this);
      
      GridBagLayout gridbag = new GridBagLayout();
      GridBagConstraints gc = new GridBagConstraints();
      
      setLayout(gridbag);
      
      Font font = getFont().deriveFont(Font.PLAIN);
      gc.insets = new Insets(1,1,1,1);
      
      for(int i=0; i<colorProperties.length; i++) {
        
        String color = colorProperties[i];
        JLabel lbl = new JLabel(color);
        lbl.setHorizontalTextPosition(JLabel.LEFT);
        lbl.setFont(font);
        
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;
        gc.gridwidth = 1;
        
        gridbag.setConstraints(lbl, gc);
        add(lbl);
        
        ColorTile c = new ColorTile(color);
        gc.gridwidth = GridBagConstraints.REMAINDER;
        
        gridbag.setConstraints(c, gc);
        add(c);
        
        tiles.put(color, c);
        
      }
      
      
    }
    
    /**
     * Listen for color selections
     */
    public void propertyChange(PropertyChangeEvent e) {
      
      ColorTile c = (ColorTile)tiles.get(e.getPropertyName());
      if(c != null)
        c.setBackground((Color)e.getNewValue());
      
    }
    
    
  }
  

}
