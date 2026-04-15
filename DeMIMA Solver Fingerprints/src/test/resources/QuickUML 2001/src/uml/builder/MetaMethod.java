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
import java.util.Vector;

import util.WrappedIterator;

/**
 * @class MetaMethod
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * This class represents a methods contract - its name, its parameters, return type
 * and its exceptions.
 *
 */
public class MetaMethod extends MetaAttribute {

  private NamingComponent parameterNamingComponent = new NamingComponent();
  private Vector params;
  private Vector exceptions;


  public MetaMethod(String description) {

    parseMethod(description);
    if(getName() == null)
      throw new SyntaxException("Invalid method descriptor");

  }

  protected void parseMethod(String description) {

    // Find the parameters
    int m = -1, n = description.indexOf('(');
    if(n > 0)
      m = description.indexOf(')', n);

    if(n < 1 || m < n)
      throw new SyntaxException("Invalid method descriptor");

    // Get the access modifiers, type & name
    parseAttribute(description.substring(0,n));

    // Get the parameters
    if((m - n) > 1)
      parseParameters(description.substring(n+1, m));

    // Check for a throws clause, skip over leading whitespace
    int len = description.length();
    while((m+1) < len && Character.isWhitespace(description.charAt(++m)));

    // Check for the throws keyword
    char[] z = {'t', 'h', 'r', 'o', 'w', 's', ' '};
    int x=0;
    while(m < len && x < z.length)
      if(z[x++] != Character.toLowerCase(description.charAt(m++)))
        break;

    // Found the keyoword, look at the final clause
    if(x == z.length)
      parseExceptions(description.substring(m));

    // Last chance to validate the name
    checkMethod();

  }

  /**
   * Last changes to the parse name can be made here. 
   *
   */
  protected void checkMethod() {
    
    if(getName() == null) {
      setName(getType());
      setType("void");
    }

  }

  protected void parseParameters(String description) {

    StringTokenizer tok = new StringTokenizer(description, ",\f");
    while(tok.hasMoreTokens())
      addParameter(new MetaParameter(tok.nextToken()));

  }

  /**
   * Add a parameter to this MetaMethod
   *
   * @param MetaParameter
   */
  public void addParameter(MetaParameter param) {

    if(params == null)
      params = new Vector();

    params.add(param);
    getNamingComponent().nameComponent(param);

  }

  /**
   * Get a component that can be used to create names for the collection
   * of parameters.
   *
   * @return NamingComponent
   */
  protected NamingComponent getNamingComponent() {
    return parameterNamingComponent;
  }

  /**
   * Parse the exception list, compress the list of exceptions to eliminate duplicates
   */
  protected void parseExceptions(String description) {

    StringTokenizer tok = new StringTokenizer(description, ", \f\t\r\n");
    while(tok.hasMoreTokens()) {

      String s = tok.nextToken();
      if(exceptions != null && exceptions.contains(s))
        continue;

      if(exceptions == null)
        exceptions = new Vector();

      exceptions.add(s);

    }

  }

  public boolean hasExceptions() {
    return exceptions != null;
  }


  public Iterator getExceptions() {
    return getExceptions(true);
  }

  protected Iterator getExceptions(boolean readOnly) {
    return new WrappedIterator(exceptions == null ? null : exceptions.iterator(), readOnly);
  }

  public boolean hasParameters() {
    return params != null;
  }


  public Iterator getParameters() {
    return getParameters(true);
  }

  protected Iterator getParameters(boolean readOnly) {
    return new WrappedIterator(params == null ? null : params.iterator(), readOnly);
  }

  /**
   * Compare by name & parameter list
   */
  public int compareTo(Object o) {

    int result = -1;

    if(o instanceof MetaMethod) {

      MetaMethod m = (MetaMethod)o;

      // Name must match, null names count
      if((result = m.getName().compareTo(getName())) != 0)
          return result;

      // Parameter count must match
      int n = params != null ? params.size() : 0;
      if(n == (m.params != null ? m.params.size() : 0)) {

        // Compare parameters
        while(--n >= 0 && result == 0) {

          MetaParameter p1 = (MetaParameter)params.elementAt(n);
          MetaParameter p2 = (MetaParameter)m.params.elementAt(n);

          result = p1.compareTo(p2);

        }

      } else
        result = -1;
    }

    return result;

  }


  public String toString() {

    StringBuffer buf = new StringBuffer(super.toString());
    buf.append('(');

    // Build the parameter list
    if(hasParameters()) {
      int n = 0;
      for(Iterator i = getParameters(); i.hasNext();) {

        if(n++ > 0) buf.append(", ");
        buf.append(i.next());

      }
    }

    buf.append(')');

    // Build the exception list
    if(hasExceptions()) {

      int n = 0;
      for(Iterator i = getExceptions(); i.hasNext();)
        buf.append((n++ > 0) ? ", " : " throws ").append(i.next());

    }

    return buf.toString();

  }


}
