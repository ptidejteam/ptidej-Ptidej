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

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @class FilteredIterator
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * This is a simle wrapper for any Iterator. It will filter the results
 * reachable through this Iterator by skipping any values that are not
 * comparable (ie filter.compareTo(nextValue) != 0) to the given filter.
 */
public class FilteredIterator extends WrappedIterator {

  private Comparable filter;
  private Object nextValue;

  /**
   * Create a new FilteredIterator, filtered by a particular class of item
   *
   * @param Iterator
   * @param Class
   */
  public FilteredIterator(Iterator iter, Class filter) {
    this(iter, new ClassFilter(filter));
  }

  /**
   * Create a new FilteredIterator
   *
   * @param Iterator
   * @param Comparable
   */
  public FilteredIterator(Iterator iter, Comparable filter) {
    this(iter, filter, true);
  }

  /**
   * Create a new FilteredIterator
   *
   * @param Iterator
   * @param Comparable
   * @param boolean
   */
  public FilteredIterator(Iterator iter, Comparable filter, boolean readOnly) {

    super(iter, readOnly);
    this.filter = filter;
    
  }

  /**
   * Returns true if the iteration has more elements that are
   * Comparable to the filter.
   * 
   * @return boolean
   */
  public boolean hasNext() {
    
    if(nextValue != null)
      return true;

    while(super.hasNext()) {

      nextValue = super.next();
      if(filter.compareTo(nextValue) == 0)
        return true;
      
    }
    
    return false;

  }

  /**
   * Returns the next element in the interation.
   *
   * @return Object
   */
  public Object next() {

    if(nextValue == null)
      throw new NoSuchElementException();

    Object o = nextValue;
    nextValue = null;

    return o;

  }

}
