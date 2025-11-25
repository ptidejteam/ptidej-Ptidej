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

package util;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.AbstractList;
import java.util.Vector;

/**
 * @class WeakList
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * Simple WeakList implementation. Stores items in a List using 
 * WeakReferences. The list is pruned as empty WeakReferences 
 * are found. 
 *
 */
public class WeakList extends AbstractList {

  private ReferenceQueue queue = new ReferenceQueue();
  private Vector list = new Vector();


  /**
   * Wrap an object and add it to the list.
   *
   * @param Object
   * @return boolean
   */
  public boolean add(Object o) {

    list.addElement(new WeakReference(o, queue));
    return true;

  }

  /**
   * Get an object at the given index. 
   *
   * @param int
   * @return Object
   */
  public Object get(int index) {

    WeakReference r = (WeakReference)list.elementAt(index);
    Object o = null;

    // Unwrap the reference
    if(r == null || ((o = r.get()) == null))
      return null;

    return o;

  }
 
  /**
   * Find the index of a particular item.
   * 
   * @param Object
   * @return int
   */
  public int indexOf(Object o) {

    for(int i = 0; i < list.size(); i++) {

      // Look at each element
      WeakReference r = (WeakReference)list.elementAt(i);

      if(r != null && (r.get() == o))
        return i;

    }

    cleanUp();

    return -1;

  }


  /**
   * Find the index of a particular item.
   * 
   * @param Object
   * @return int
   */
  public boolean remove(Object o) {

    boolean found = false;

    for(int i = 0; !found && i<list.size(); i++) {

      // Look at each element
      WeakReference r = (WeakReference)list.elementAt(i);

      if(r != null && (r.get() == o)) {

        list.removeElement(r);
        found = true;

      }

    }

    cleanUp();

    return found;

  }

  /**
   * Get a decent estimate of the lists size. It could really change
   * any time since its only storing references to objects which might
   * be garbage collected after this method returns.
   */ 
  public int size() {

    cleanUp();
    return list.size();

  }

  /**
   * Eliminate references that are waiting to be cleaned up.
   */
  protected void cleanUp() {

    WeakReference r;

    while((r = (WeakReference)queue.poll()) != null)
      list.removeElement(r);

  }


}

