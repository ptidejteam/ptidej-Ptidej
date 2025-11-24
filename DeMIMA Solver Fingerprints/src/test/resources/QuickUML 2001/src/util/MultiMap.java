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

import java.util.Collection;
import java.util.Map;

/**
 * @class MultiMap
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * MutliMaps are similar to regular maps except that a MutliMap
 * can have more than one entry mapped from a single key.
 */
public interface MultiMap extends Map {

  /**
   * Returns true if this map maps the key to this value.
   *
   * @param Object key
   * @param Object value
   *
   * @return boolean
   */
  public boolean contains(Object key, Object value);

  /**
   * Removes a particular mapping, or all mappings to the given
   * value if the key is null.
   *
   * @param Object key or all keys if null
   * @param Object value
   */
  public void remove(Object key, Object value);

  /**
   * Removes the all mappings for this key from this map
   *
   * @param Object key
   */
  public void removeAll(Object key);

  /**
   * Store this item in the map, uses an internal object as the key. This 
   * can be retrieved later using a null key.
   *
   * @param Object
   */
  public void put(Object value);

  /**
   * Create all of the mappings from the specified key to the elements in
   * the given Collection.
   *
   * @param Object
   * @param Collection
   */
  public void putAll(Object key, Collection c);


  /**
   * Create all of the mappings from the specified key to the elements in
   * the given Collection.
   *
   * @param Object
   * @param Object[]
   */
  public void putAll(Object key, Object[] o);

  /**
   * Get all the values mapped to the given key
   *
   * @param Object key or null for all values
   * @param Object[] avoid extra allocation
   */
  public Object[] getAll(Object key, Object[] array);

  /**
   * Returns a collection view of the values contained in this map
   * which map to a certain key.
   *
   * @return Collection
   */
  public Collection values(Object key);

  /**
   * @class Entry
   *
   * Simple Map.Entry
   */
  public static class Entry implements Map.Entry {

    public Object key;
    public Object val;

    public Entry() {
      this(null, null);
    }

    public Entry(Object key, Object val) {
      this.key = key;
      this.val = val;
    }

    public Object getKey() {
      return this.key;
    }

    public Object getValue() {
      return this.val;
    }

    public Object setValue(Object value) {
      throw new UnsupportedOperationException();
    }

    public String toString() {
      return "[" + key + "], [" + val + "]";
    }

  }

 

}
