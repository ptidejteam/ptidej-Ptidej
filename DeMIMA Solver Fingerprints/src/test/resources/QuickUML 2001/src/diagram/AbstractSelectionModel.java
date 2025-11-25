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

package diagram;

import util.WeakList;

/**
 * @class AbstractSelectionModel
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 */
public abstract class AbstractSelectionModel implements SelectionModel {

  private WeakList listeners = new WeakList();

  /**
   * Add a new listener to this model.
   *
   * @param DiagramSelectionListener
   */
  public void addSelectionListener(DiagramSelectionListener l) {
    listeners.add(l);
  }

  /**
   * Remove a listener to this model.
   *
   * @param DiagramSelectionListener
   */
  public void removeSelectionListener(DiagramSelectionListener l) {
    listeners.remove(l);
  }


  /**
   * Fire the figure selected event.
   *
   * @param Figure 
   */
  protected void fireFigureAdded(Figure f) {

    for(int i = 0; i < listeners.size(); i++) {

      DiagramSelectionListener l = (DiagramSelectionListener)listeners.get(i);
      if(l != null)
        l.figureAdded(this, f);

    }

  }

  /**
   * Fire the figure removed event.
   *
   * @param Figure 
   */
  protected void fireFigureRemoved(Figure f) {

    for(int i = 0; i < listeners.size(); i++) {

      DiagramSelectionListener l = (DiagramSelectionListener)listeners.get(i);
      if(l != null)
        l.figureRemoved(this, f);

    }

  }

}
