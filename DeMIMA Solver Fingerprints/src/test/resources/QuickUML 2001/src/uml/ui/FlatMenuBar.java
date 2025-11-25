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
import java.awt.Insets;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

/**
 * @class FlatMenuBar
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * This menu bar creates several menus for itself based on the 
 * ActionMap from the DiagramContainer.
 */
public class FlatMenuBar extends JMenuBar {

  private static Insets noInsets = new Insets(0,0,0,0);
  private int nextInsert = 0;

  static { 
    
    Font menuFont = UIManager.getFont("MenuItem.font");
    UIManager.put("MenuItem.font", menuFont.deriveFont(Font.PLAIN));
    UIManager.put("MenuItem.checkIcon", "No Icon");
    UIManager.put("CheckBoxMenuItem.font", menuFont.deriveFont(Font.PLAIN));

  }

  /**
   * Get the a certain menu in the toolbar. If it is not in the menu
   * then create & add it.
   */
  public JMenu getMenu(String menuTitle) {

    JMenu menu = null;
    int index = 0;

    for(int n=0; n < this.getMenuCount(); n++) {
      
      // Append the option to the end of the Options menu
      menu = this.getMenu(n);
      if(menu != null) {

        // Find a mnemonic that will fit
        while(index < menuTitle.length() && menu.getMnemonic() == menuTitle.charAt(index)) 
          index++;
        
        if(menu.getText().compareTo(menuTitle) == 0) 
          return menu;

      }
    }
     
    // Create the new menu and set the mnemonic
    menu = new Menu(menuTitle);
    if(index < menuTitle.length())
      menu.setMnemonic(menuTitle.charAt(index));
    
    int insertAt = this.getMenuCount() - nextInsert;
    super.add(menu, insertAt);

    // Insert the glue, update the insert point
    if(menuTitle.toLowerCase().equals("help")) {
      super.add(Box.createHorizontalGlue(), insertAt);
      nextInsert -= 2;
    } 

    return menu;
    
  }

  /**
   * This should be used after all other menus have been added
   */
  public JMenu getHelpMenu() {
    return getMenu("Help");
  }

  /**
   * @class Menu
   *
   * Create a customized JMenu with a flat look
   */
  protected class Menu extends JMenu {

    /**
     * Create a JMenu with a flat border
     */
    public Menu(String title) {

      super(title);

      this.setBorder(BorderFactory.createEmptyBorder(1,1,2,1));
      getPopupMenu().setBorder(BorderFactory.createLineBorder(Color.black));

    }
    
    /**
     * Configure any Actions added do that the accelerator is picked up correctly
     */
    public JMenuItem add(Action action) {

      JMenuItem item = new JMenuItem(action);

      item.setMargin(noInsets);
      item.setAccelerator((KeyStroke)action.getValue(Action.ACCELERATOR_KEY));

      return super.add(item);
    }

  }

}
