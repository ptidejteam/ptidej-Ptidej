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

import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * @class MetaParameter
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 */
public class MetaParameter extends MetaComponent {

  public MetaParameter(String description) {
    parseParameter(description);
  }

  protected void parseParameter(String description) {

    StringTokenizer tok = new StringTokenizer(description);

    if(tok.hasMoreTokens())
      setType(tok.nextToken());

    // Look for a name
    if(tok.hasMoreTokens())
      setName(tok.nextToken());

  }


  /**
   * Compare by name
   */
  public int compareTo(Object o) {

    if(o instanceof MetaParameter)
      return super.compareTo(o);

    return -1;

  }


  public String toString() {

    StringBuffer buf = new StringBuffer(getType());

    if(getName() != null)
      buf.append(' ').append(getName());

    return buf.toString();

  }

  public static String toString(MetaMethod method) {

    StringBuffer buf = new StringBuffer();

    int n = 0;
    for(Iterator i = method.getParameters(); i.hasNext();) {

      if(n++ > 0) buf.append(", ");
      buf.append(i.next());
      
    }

    return buf.toString();

  }

}
