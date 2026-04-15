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

import util.WeakList;
import diagram.Diagram;

/**
 * @class Tool
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 */
public abstract class AbstractTool implements Tool {

  private WeakList listeners = new WeakList();
  
  /**
   * Add a new listener to this tool.
   *
   * @param ToolListener
   */
  public void addToolListener(ToolListener l) {
    listeners.add(l);
  }

  /**
   * Remove a listener to this tool.
   *
   * @param ToolListener
   */
  public void removeToolListener(ToolListener l) {
    listeners.remove(l);
  }


  /**
   * Install support for something in the given Diagram
   *
   * @param Diagram
   */
  abstract public void install(Diagram diagram);

  /**
   * Remove support for something that was previously installed.
   *
   * @param Diagram
   */
  abstract public void uninstall(Diagram diagram);

  /**
   * Fire the tool started event
   */
  protected void fireToolStarted() {

    try {

      for(int i = 0; i < listeners.size(); i++) {
        
        ToolListener l = (ToolListener)listeners.get(i);
        if(l != null)
          l.toolStarted(this);
        
      }

    } catch(Throwable t) { 
      handleError(t);
    }

  }

  /**
   * Fire the tool finished event
   */
  protected void fireToolFinished() {

    try {

      for(int i = 0; i < listeners.size(); i++) {
        
        ToolListener l = (ToolListener)listeners.get(i);
        if(l != null)
          l.toolFinished(this);
        
      }


    } catch(Throwable t) { 
      handleError(t);
    }

  }

  protected void handleError(Throwable t) {

    System.err.println("Error dispatching event");

    t.printStackTrace();
    System.exit(0);

  }

}
