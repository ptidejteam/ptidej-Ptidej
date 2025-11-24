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

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Vector;

import util.FilteredIterator;
import diagram.DiagramModel;

/**
 * @class Context
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * A Context contains a set of information about the MetaObjects created
 * from a DiagramModel. A single Context object can be passed to many different
 * CodeBuilders to incrementially perform different work in interpretting and
 * organizing the contents of a DiagramModel as meta data.
 */
public class Context {

  private final static MetaClassComparator comparator = new MetaClassComparator();

  private DiagramModel model;

  private Vector warnings = new Vector();
  private Vector errors = new Vector();
  private Vector classes = new Vector();
  private boolean wantArrays = false;

  /**
   * Create a new Context
   *
   * @param DiagramModel
   */
  public Context(DiagramModel model) {
    this.model = model;
  }

  /**
   * Get the DiagramModel this Context has been created for.
   *
   * @return DiagramModel
   */ 
  public DiagramModel getModel() {
    return model;
  }


  /**
   * Add a class to this context. Checks for duplicates.
   *
   * @param MetaClass
   */
  public void addClass(MetaClass c) {
      
    if(classes.contains(c))
      throw new SemanticException("Duplicate for '" + c.getProperName() + "'");

    classes.add(c);
    
    // MetaClasses are naturally sorted by name
    Collections.sort(classes);

  }

  /**
   * Get the MetaClass for the given name 
   *
   * @param class name
   * @return MetaClass or null
   */
  public MetaClass getMetaClass(String name) {

    int index = Collections.binarySearch(classes, name, comparator);
    if(index < 0)
      return null;

    return (MetaClass)classes.get(index);
    
  }
  
  /**
   * Add a warning to the builder Context.
   *
   * @param Warning
   */
  public void addWarning(String warning) {
    warnings.add(warning);
  }

  /**
   * Add an error to the builder Context.
   *
   * @param Error
   */
  public void addError(String error) {
    errors.add(error);
  }

  public void enableArrays(boolean flag) {
    wantArrays = flag;
  }

  public boolean isArraysEnabled() {
    return wantArrays;
  }

  /**
   * Test the Context for warnings.
   *
   * @return boolean
   */
  public boolean hasWarnings() {
    return !warnings.isEmpty();
  }

  /**
   * Test the Context for errors.
   *
   * @return boolean
   */
  public boolean hasErrors() {
    return !errors.isEmpty();
  }


  /**
   * Get an iterator over all MetaClasses previously mapped into this Context
   *
   * @return Iterator
   */
  public Iterator getClasses() {
    return new FilteredIterator(classes.iterator(), new TypeFilter("class"));
  }


  /**
   * Get an iterator over all MetaInterfaces previously mapped into this Context
   *
   * @return Iterator
   */
  public Iterator getInterfaces() {
    return new FilteredIterator(classes.iterator(), new TypeFilter("interface"));
  }

  /**
   * Get an iterator over all warnings placed into this Context
   *
   * @return Iterator
   */
  public Iterator getWarnings() {
    return warnings.iterator();
  }

  /**
   * Get an iterator over all errors placed into this Context
   *
   * @return Iterator
   */
  public Iterator getErrors() {
    return errors.iterator();
  }

  /**
   * Test for an item in this Context
   */
  public boolean contains(Object o) {
    return classes.contains(o);
  }

  /**
   * @class TypeFilter
   *
   * Implement a simple filter for iterating over various types of MetaClasses
   */
  protected static class TypeFilter implements Comparable {
    
    private String type;

    /**
     * Create a new TypeFilter
     */
    public TypeFilter(String type) {

      if(type == null)
        throw new RuntimeException("Invalid type");

      this.type = type;

    }

    /**
     * Compare MetaClass by type to the type string
     */
    public int compareTo(Object o) {

      if(o instanceof MetaClass) {
        o = ((MetaClass)o).getType();
        return type.compareTo(o);
      }

      return -1;

    }
    
  }

  /**
   * @class MetaClassComparator
   *
   * Implement a comparator that will match String to MetaClasses by class name
   */
  protected static class MetaClassComparator implements Comparator {

    public int compare(Object o1, Object o2) {

      // Unwrap the name 
      if(o1 instanceof MetaClass)
        o1 = ((MetaClass)o1).getName();

      if(o2 instanceof MetaClass)
        o2 = ((MetaClass)o2).getName();

      // Do the comparison by name
      if(o1 instanceof String)
        return ((String)o1).compareTo(o2);

      if(o2 instanceof String)
        return ((String)o2).compareTo(o1);

      return -1;

    }

  }

}
