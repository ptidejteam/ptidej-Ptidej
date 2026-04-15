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
 * @class FontAction
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 */
public class FontAction extends AbstractAction {
  
  private JComponent comp;
  private FontPanel panel;
  
  public FontAction(JComponent comp, String[] properties) {
    
    super("Fonts ...");
    this.comp = comp;
    this.panel = new FontPanel(properties);
    
  }

  public void actionPerformed(ActionEvent e) {
    
    JOptionPane.showMessageDialog(comp, panel, "Change fonts",
                                  JOptionPane.PLAIN_MESSAGE);
    
    // Force repaint to update the font changes.
    comp.paintImmediately(0, 0, comp.getWidth(), comp.getHeight());
    
  }

  
  /**
   * @class FontPanel
   *
   * Panel for displaying a set of Font that are mapped to UIManager settings.
   */
  public class FontPanel extends JPanel 
    implements PropertyChangeListener {
    
    protected HashMap tiles = new HashMap();
    
    /**
     * Create a panel for editing the given set of UIManager font
     * properties.
     *
     * @param String[]
     */
    protected FontPanel(String[] fontProperties) {

      UIManager.getDefaults().addPropertyChangeListener(this);
      
      GridBagLayout gridbag = new GridBagLayout();
      GridBagConstraints gc = new GridBagConstraints();
      
      setLayout(gridbag);
      
      Font font = getFont().deriveFont(Font.PLAIN);
      gc.insets = new Insets(1,1,1,1);
      
      for(int i=0; i<fontProperties.length; i++) {
        
        String fontName = fontProperties[i];
        JLabel lbl = new JLabel(fontName);
        lbl.setHorizontalTextPosition(JLabel.LEFT);
        lbl.setFont(font);
        
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;
        gc.gridwidth = 1;
        
        gridbag.setConstraints(lbl, gc);
        add(lbl);

        
        FontTile c = new FontTile(fontName);
        gc.gridwidth = GridBagConstraints.REMAINDER;
        
        gridbag.setConstraints(c, gc);
        add(c);
        
        tiles.put(fontName, c);
        
      }
      
      
    }
    
    /**
     * Listen for font selections
     */
    public void propertyChange(PropertyChangeEvent e) {
      
      FontTile c = (FontTile)tiles.get(e.getPropertyName());
      if(c != null) 
        c.setFont((Font)e.getNewValue());


    }

    
  }
 

}
