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

import java.util.Iterator;

/**
 * @interface SelectionModel
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * This is a simpled implementation of a model that keeps track
 * of selected Figures.
 *
 * The selection model needs to keep track of a set of selected Figures.
 * - Adding figures
 * - Removing figures
 * - Testing for figures
 */
public interface SelectionModel {

  /**
   * Test an item to see if it is a member of the current
   * selection.
   *
   * @param Figure
   * @return boolean
   */
  public boolean contains(Figure figure);

  /**
   * Test an item to see if it is a member of the current
   * selection.
   *
   * @param Link
   * @return boolean
   */
  public boolean contains(Link link);

  /**
   * Add one item to the current selection
   *
   * @param Figure
   */
  public void add(Figure figure);

  /**
   * Add one item to the current selection
   *
   * @param Figure
   * @param boolean clear flag
   */
  public void add(Figure f, boolean reset);

  /**
   * Remove one item from the current selection
   *
   * @param Figure
   */
  public void remove(Figure figure);

  /**
   * Remove all items from the current selection
   */
  public void clear();


  /**
   * Get the size of the current selection.
   *
   * @return int
   */
  public int size();


  /**
   * Get an Iterator over the currently selected items of a
   * certain Class hierarchy.
   *
   * @return Iterator
   */
  public Iterator iterator();


  /**
   * Get all selected items. The items returned will be pruned by
   * the arryas element class if any. Passing a Figure[] array would return
   * all Figure classes & subclasses in the selection model.
   *
   * @param Object[] - avoid allocating a new array
   *
   * @return Object[]
   */
  public Object[] toArray(Object[] array);



  /**
   * Add a new listener to this model.
   *
   * @param DiagramSelectionListener
   */
  public void addSelectionListener(DiagramSelectionListener listener);

  /**
   * Remove a listener to this model.
   *
   * @param DiagramSelectionListener
   */
  public void removeSelectionListener(DiagramSelectionListener listener);

}
