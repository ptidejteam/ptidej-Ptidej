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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;

/**
 * @class CustomUI
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * Implments a simple UI that will install a parituclar theme on a component and listen
 * to the UIManager for changes in that theme, updating the components that using the ui
 */
public class CustomUI extends ComponentUI 
  implements PropertyChangeListener {
  
  protected ArrayList components = new ArrayList();
  protected String prefix;

  /**
   *
   */
  public CustomUI(String prefix) {

    if(prefix == null)
      throw new IllegalArgumentException();

    this.prefix = prefix;
    UIManager.getDefaults().addPropertyChangeListener(this);   

  }


  /**
   *
   */
  public void installUI(JComponent c) {

    if(components.contains(c))
      return;

    components.add(c);

    Color fg = UIManager.getColor(getPropertyPrefix() + ".foreground");
    if(fg != null)
      c.setForeground(fg);

    Color bg = UIManager.getColor(getPropertyPrefix() + ".background");
    if(bg != null)
      c.setBackground(bg);

    Border border = UIManager.getBorder(getPropertyPrefix() + ".border");
    if(border != null)
      c.setBorder(border);
    
    Font font = UIManager.getFont(getPropertyPrefix() + ".font"); 
    if(font != null)
      c.setFont(font);

  }
  
  /**
   *
   */
  public void uninstallUI(JComponent c) {
    components.remove(c);
  }

  /**
   *
   */
  protected String getPropertyPrefix() {
    return prefix;
  }

  /**
   * Listen to the UIManager for any color, font or border adjustments
   *
   * @param PropertyChangeEvent
   */
  public void propertyChange(PropertyChangeEvent e) {
    
    String name = e.getPropertyName();

    if(name.equals(getPropertyPrefix() + ".foreground")) {

      Color fg = (Color)e.getNewValue();
      if(fg != null) {

        for(int i=0; i < components.size(); i++)
          ((JComponent)components.get(i)).setForeground(fg);

      }

    } else if(name.equals(getPropertyPrefix() + ".background")) {

      Color bg = (Color)e.getNewValue();     
      if(bg != null) {

        for(int i=0; i < components.size(); i++)
          ((JComponent)components.get(i)).setBackground(bg);
            
      }

    } else if(name.equals(getPropertyPrefix() + ".border")) {

      Border border = (Border)e.getNewValue();
      if(border != null) {

        for(int i=0; i < components.size(); i++)
          ((JComponent)components.get(i)).setBorder(border);

      }

    } else if(name.equals(getPropertyPrefix() + ".font")) {

      Font font = (Font)e.getNewValue();
      if(font != null) {

        for(int i=0; i < components.size(); i++)
          ((JComponent)components.get(i)).setFont(font);

      }

    }

  }


}
