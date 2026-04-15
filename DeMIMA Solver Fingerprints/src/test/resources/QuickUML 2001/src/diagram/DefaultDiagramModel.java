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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @class DefaultDiagramModel
 *
 * @author Eric Crahen
 * @date 08-20-2001
 * @version 1.0
 *
 * The DefaultDiagramModel implements a working DiagramModel that is
 * capable of storing and removing various Figures, but not associating
 * data with those Figures. 
 *
 * Serialization of the default model is accoomplished by through a simple
 * format. A table of all Figures that are a part of this model is written, 
 * followed by a table of extra information for each Figure. This allows 
 * shared objects (such as Link endpoints and some compound Figures) to be 
 * handled correctly (no duplicates are created just because a Figure is
 * references several times). 
 *
 * Derivatives of this class should  override the writeExternal(Figure, ...) 
 * and readExternal(Figure, ...) methods to customize what extra 
 * information is store for each Figure.
 *
 *
 * TODO: Update the externalization methods to handle links to Figures not
 * members of the Model.
 */
public class DefaultDiagramModel extends AbstractDiagramModel 
  implements Serializable {

  private static FigureComparator comparator = new FigureComparator();

  private ArrayList figures = new ArrayList();
  private HashMap valueMap = new HashMap();

  /**
   *
   * @param Figure
   */
  public void add(Figure fig) {

    if(!figures.contains(fig)) {

      figures.add(fig);
      fireFigureAdded(fig);
   
    }

  }
  

  /**
   *
   * @param Figure
   */
  public void remove(Figure fig) {

    if(figures.contains(fig)) {

      figures.remove(fig);
      fireFigureRemoved(fig);

    }

  }

  /**
   * Remove all figures from the model
   */
  public void clear() {

    while(!figures.isEmpty())
      fireFigureRemoved((Figure)figures.remove(figures.size()-1));

    valueMap.clear();

  }

  /**
   * Get the number of Figures in this model
   */
  public int size() {
    return figures.size();
  }

  /**
   *
   * @return Iterator
   */
  public Iterator iterator() {
    return new RepeatingIterator(figures);
  }

  /**
   * Associate a value with a Figure 
   *
   * @param Figure 
   * @param Object
   */
  public void setValue(Figure figure, Object value) {
    valueMap.put(figure, value);
  }

  /**
   * Get value associated with a Figure 
   *
   * @param Figure 
   * @return Object
   */
  public Object getValue(Figure figure) {
    return valueMap.get(figure);
  }

  /**
   * @class RepeatingIterator
   * 
   * Iterator implementation that will walk over the elements of some
   * List. Once the end of the list is reached, and has been tested once
   * with hasNext() returning false the Iterator is reset. It can then 
   * walk over the set of Objects in the List once again.
   */
  protected class RepeatingIterator implements Iterator {

    private List list;
    private int index;

    public RepeatingIterator(List list) {
      this.list = list;
      this.index = -1;
    }

    public boolean hasNext() {

      if(++index == list.size()) {

        index = -1;
        return false;

      }

      return true;

    }

    public Object next() {
      return list.get(index);
    }

    public void remove() {}

  }

  /**
   * Get all selected items. The items returned will be pruned by
   * the arrays element class if any. Passing a Figure[] array would return
   * all Figure classes & subclasses in the selection model.
   *
   * @param Object[] - avoid allocating a new array
   *
   * @return Object[]
   */
  public Object[] toArray(Object[] a) {

    Class itemClass = (a == null) ? Object.class : a.getClass().getComponentType();
    if(itemClass == Object.class)
      return figures.toArray(a);

    // New array needed, pick a decent element class
    int len = figures.size();
    if(a.length < len) 
      a = (Object[])java.lang.reflect.Array.newInstance(itemClass, len);
    
    // Copy by class
    int n = 0;
    for(int i=0; i < len; i++) {
      
      Object o = figures.get(i);
      Class c = o.getClass();
      
      if(c == itemClass || itemClass.isAssignableFrom(c))
        a[n++] = o;

    }
    
    // Null terminate
    if(n < a.length) 
      java.util.Arrays.fill(a, n, a.length, null);
    
    return a;

  }

}
