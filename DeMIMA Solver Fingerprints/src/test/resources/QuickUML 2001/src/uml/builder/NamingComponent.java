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

package uml.builder;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

/**
 * @class NamingComponent
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 */
public class NamingComponent {

  private Vector names = new Vector();

  /**
   * Name a single component. Names created with this method are remebered
   * in each instance used and considered when other MetaComponents are names
   * in future calls to this method.
   *
   * @param MetaComponent
   * @post the given MetaComponet will now have a unique name
   */
  public void nameComponent(MetaComponent meta) {
    nameComponent(meta, names);
  }

  /**
   * Walk through a list of MetaComponents and ensure they all have
   * unique names. Names generated with this method are not remebered
   * and not considered again after this method completes
   *
   * @param Collection
   * @post the given Collection of MetaComponets will now have unique names
   */
  public void nameComponents(Collection c) {

    Vector v = new Vector();
    for(Iterator i = c.iterator(); i.hasNext();) {

      Object o = i.next();
      if(!(o instanceof MetaComponent))
        throw new RuntimeException("Collection contains non-MetaComponents");

      nameComponent((MetaComponent)o, v);

    }

  }

  /**
   * Generate a unique name for a MetaComponent, considering all the names
   * contained in the given Collection.
   *
   * @param Collection
   * @param List
   * @post the given MetaComponet will now have a unique name
   */
  private final void nameComponent(MetaComponent meta, Collection names) {

    String name = meta.getName();
    String type = meta.getType();

    // Add the type to list of used names so it is not selected
    if(!names.contains(type))
      names.add(type);

    // If the name is currently invalid or in use, generate another name
    String baseName = name = getBaseName(meta);
    names.add(baseName);
    for(int n = 0; name.equals(type) || names.contains(name); n++)
      name = getName(baseName, n);

    // Change the name for the component
    meta.setName(name);
    names.add(name);

  }

  /**
   * Create the base name to generate new names.
   *
   * @param MetaComponent
   * @return String
   */
  protected String getBaseName(MetaComponent meta) {

    String name = meta.getName();
    String type = meta.getType();

    if(name == null || name.length() < 1)
      name = type;

    int n = name.indexOf('[');    
    StringBuffer buf = new StringBuffer(name);

    // Flatten array names
    if(n>0)
      buf.delete(n, name.length());

    // Remove leading underscores
    while(buf.length() > 0 && buf.charAt(0) == '_')
      buf.deleteCharAt(0);

    // Force first character to lower case
    if(buf.length() > 0)
      buf.setCharAt(0, Character.toLowerCase(buf.charAt(0)));

    // Strip trailing digits
    for(; (n = buf.length()) > 0 && Character.isDigit(buf.charAt(--n)); buf.deleteCharAt(n));

    
    return buf.toString();

  }

  /**
   * Create the n'th name using the given base name.
   *
   * @param String
   * @int String
   *
   * @return String
   */
  protected String getName(String name, int attempt) {
    return (attempt >= 0) ? name + Integer.toString(attempt) : name;
  }

}
