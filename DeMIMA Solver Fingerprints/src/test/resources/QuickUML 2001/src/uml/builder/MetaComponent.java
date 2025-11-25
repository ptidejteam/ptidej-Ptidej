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



/**
 * @class MetaComponent
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 */
public abstract class MetaComponent implements Comparable {

  private String type;
  private String name;

  public String getType() {
    return type;
  }

  public void setType(String type) {

    if(type == null || type.length() < 1 || MetaAccess.parse(type) != MetaAccess.NONE)
      throw new SyntaxException("Invalid type");

    this.type = type;

  }

  public String getName() {
    return name;
  }

  public void setName(String name) {

    if(name == null || MetaAccess.parse(name) != MetaAccess.NONE)
      throw new SyntaxException("Invalid name");

    this.name = name;

  }

  /**
   * Compare by name
   */
  public int compareTo(Object o) {

    if(o instanceof MetaComponent) {

      MetaComponent m = (MetaComponent)o;

      // Compare names
      if(getName() != null)
        return getName().compareTo(m.getName());

      return (m.getName() == null) ? -1 : 0;

    }

    return -1;

  }

  public boolean equals(Object o) {
    return compareTo(o) == 0;
  }

  public String toString() {

    StringBuffer buf = new StringBuffer(type);

    if(name != null)
      buf.append(' ').append(name);

    return buf.toString();

  }

}
