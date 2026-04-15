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

import java.awt.Component;

import diagram.DefaultFigureEditor;
import diagram.Diagram;
import diagram.Figure;


/**
 * @class InterfaceEditor
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 */
public class InterfaceEditor extends DefaultFigureEditor {

  protected InterfaceRenderer renderer = new InterfaceRenderer();
  protected InterfaceComponent interfaceComponent;

  protected InterfaceItem lastValue;

  /**
   * This method is the first invoked when the editing process begins.
   *
   * @param Diagram surface that is being rendered upon
   * @param Figure item to edit
   *
   * @return suitable A Component that can render the given item is returned. 
   *
   * @post the Component returned will most likey be changed, in that it will
   * have been reparented & its bounds will be reset. Each time this Component
   * is used this will happen, so generally it is not neccessary for the 
   * returned item to bother setting its own size.
   */
  public Component getFigureEditorComponent(Diagram diagram, Figure figure, boolean isSelected) {

    // Reuse the value object
    lastValue = (InterfaceItem)diagram.getModel().getValue(figure);
  
    // Get the rendering component
    Component editorComponent = renderer.getRendererComponent(diagram, figure, isSelected);
    interfaceComponent = (InterfaceComponent)renderer.getUserComponent();
    
    return editorComponent;

  }

  /**
   *
   */
  public Object getCellEditorValue() {

    if(lastValue == null)
      lastValue = new InterfaceItem();

    lastValue.setName(interfaceComponent.getTitle());
    lastValue.setDescription(interfaceComponent.getMembers());

    return lastValue;
    
  }

 
}
