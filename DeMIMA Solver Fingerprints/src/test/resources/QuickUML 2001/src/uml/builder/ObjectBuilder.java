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
import java.util.Vector;

import util.FilteredIterator;

/**
 * @class ObjectBuilder
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * Fills the Context with a set of MetaClasses that contain the approriate 
 * relationships.
 */
public class ObjectBuilder implements CodeBuilder {

  protected static ClassBuilder classBuilder = new ClassBuilder();
  protected static InheritanceBuilder inheritanceBuilder = new InheritanceBuilder();
  protected static RealizationBuilder realizationBuilder = new RealizationBuilder();
  protected static CompositionBuilder compositionBuilder = new CompositionBuilder();
  protected static AssociationBuilder associationBuilder = new AssociationBuilder();
  
  /**
   */
  public void build(Context ctx) 
    throws BuilderException {

    classBuilder.build(ctx);
    inheritanceBuilder.build(ctx);
    realizationBuilder.build(ctx);
    compositionBuilder.build(ctx);
    associationBuilder.build(ctx);

    // Add the methods to the meta classes
    buildConstructors(ctx);

  }

  /**
   * Create constructors for the classes
   */
  protected void buildConstructors(Context ctx) {
    
    for(Iterator i = ctx.getClasses(); i.hasNext(); ) {

      MetaClass metaClass = (MetaClass)i.next();

      // Collect the associations for this class
      Vector attrs = new Vector();
      for(Iterator j = new FilteredIterator(metaClass.getAttributes(), MetaAssociation.class); j.hasNext();)
        attrs.add(j.next());
      
      // Collect the compositions for this class
      Vector comps = new Vector();
      for(Iterator j = new FilteredIterator(metaClass.getAttributes(), MetaComposition.class); j.hasNext();)
        comps.add(j.next());

      // no constructor needed?
      if(attrs.isEmpty() && comps.isEmpty())
        continue;

      // Create the constructor for the class
      MetaConstructor cons = new MetaConstructor((MetaAttribute[])attrs.toArray(new MetaAttribute[attrs.size()]), 
                                                 (MetaAttribute[])comps.toArray(new MetaAttribute[comps.size()]));
      
      metaClass.addMethod(cons);
    }

  }

}
