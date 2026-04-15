/**
 *
    Java Diagram Package; An extremely flexible and fast multipurpose diagram 
    component for Swing.
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

package diagram.tool;

import java.util.Iterator;
import java.util.Vector;

import diagram.Diagram;

/**
 * @class CompositeTool
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * This tool allows composites of other tools to be formed. This is convient 
 * for tools that need to be able to drag AND select, or edit AND select, etc.
 */
public class CompositeTool implements Tool {

  private Vector tools = new Vector();

  /**
   * Add a new listener to this tool.
   *
   * @param ToolListener
   */
  public void addToolListener(ToolListener l) {

    for(Iterator i = tools.iterator(); i.hasNext();)
      ((Tool)i.next()).addToolListener(l);

  }

  /**
   * Remove a listener to this tool.
   *
   * @param ToolListener
   */
  public void removeToolListener(ToolListener l) {

    for(Iterator i = tools.iterator(); i.hasNext();)
      ((Tool)i.next()).removeToolListener(l);

  }

  public void install(Diagram diagram) {
    
    for(Iterator i = tools.iterator(); i.hasNext();)
      ((Tool)i.next()).install(diagram);

  }

  public void uninstall(Diagram diagram) {

    for(Iterator i = tools.iterator(); i.hasNext();)
      ((Tool)i.next()).uninstall(diagram);

  }

  public void add(Tool tool) {
    if(!tools.contains(tool))
      tools.add(tool);
  }

  public void remove(Tool tool) {
    tools.remove(tool);
  }

}
