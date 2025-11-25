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

import java.util.StringTokenizer;

/**
 * @class MetaAttribute
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 */
public class MetaAttribute extends MetaComponent {

  private int access;
  
  protected MetaAttribute() { }

  public MetaAttribute(String description) {
    parseAttribute(description);
  }
  
  
  protected void parseAttribute(String description) {

    StringTokenizer tok = new StringTokenizer(description);

    // Look for access words first
    for(int n=0; tok.hasMoreTokens();) {

      // Accumulate the access tokens
      String s = tok.nextToken();
      if((n = MetaAccess.parse(s)) != MetaAccess.NONE)
        access |= n;

      else { // Set the type
        setType(s);
        break;
      }

    }

    setAccess(access);

    // Look for a name
    if(tok.hasMoreTokens())
      setName(tok.nextToken());

  }

  public int getAccess() {
    return access;
  }

  public void setAccess(int access) {

    access = MetaAccess.compress(access);
    if(!MetaAccess.isValid(access))
      throw new SyntaxException("Invalid access modifiers");

    this.access = access;

  }
  
  public String getType() {

    String type =  super.getType();
    int n = type.indexOf('[');

    return (n < 0) ? type : type.substring(0,n);

  }

  public boolean isArray() {
    return super.getType().indexOf('[') > 0;
  }
  
  public int getArraySize() {

    String type =  super.getType(); 
    int n = type.indexOf('[');
    int m = type.indexOf(']');

    if(m > n && n > 0) {
      try { return Integer.parseInt(type.substring(n+1,m)); } catch(Throwable t) {}
    }
    
    return -1;
  }

  /**
   * Compare by name
   */
  public int compareTo(Object o) {

    int result = -1;
    if(o instanceof MetaAttribute && (result = super.compareTo(o)) == 0) {

      MetaAttribute m = (MetaAttribute)o;
      result = getType().compareTo(m.getType());

    }

    return result;

  }


  public String toString() {

    StringBuffer buf = new StringBuffer();

    if(access != MetaAccess.NONE)
      buf.append(MetaAccess.toString(access)).append(' ');

    buf.append(getType());

    if(isArray())
      buf.append("[]");

    if(getName() != null)
      buf.append(' ').append(getName());

    return buf.toString();

  }

}
