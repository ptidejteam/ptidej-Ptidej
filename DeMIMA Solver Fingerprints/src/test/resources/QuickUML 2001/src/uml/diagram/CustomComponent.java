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
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;

import javax.swing.JComponent;


/**
 * @class CustomComponent 
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * Component that keeps its childrens colors & fonts in sync
 */
public class CustomComponent extends JComponent {


  public void setForeground(Color c) {

    super.setForeground(c);
    setForeground(this, c);

  }

  public void setBackground(Color c) {

    super.setBackground(c);
    setBackground(this, c);

  }

  public void setFont(Font font) {
    
    super.setFont(font);
    setFont(this, font);

  } 

  public static void setBackground(Container parent, Color c) {

    Component[] children = parent.getComponents();
    for(int i=0; i < children.length; i++) {

      Component comp = children[i];   
      comp.setBackground(c);
      
      if(comp instanceof Container)
        setBackground((Container)comp, c);
            
    }
    
  }


  public static void setForeground(Container parent, Color c) {

    Component[] children = parent.getComponents();
    for(int i=0; i < children.length; i++) {

      Component comp = children[i];   
      comp.setForeground(c);
      
      if(comp instanceof Container)
        setForeground((Container)comp, c);
            
    }
    
  }

  public static void setFont(Container parent, Font font) {

    Component[] children = parent.getComponents();
    for(int i=0; i < children.length; i++) {

      Component comp = children[i];   
      comp.setFont(font);
      
      if(comp instanceof Container)
        setFont((Container)comp, font);
            
    }
    
  }

}
