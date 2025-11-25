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

import uml.diagram.ClassFigure;
import uml.diagram.ClassItem;
import uml.diagram.InterfaceFigure;
import uml.diagram.InterfaceItem;
import diagram.DiagramModel;
import diagram.ValueIterator;

/**
 * @class ClassBuilder
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * This code builder scans the DiagramModel in the Context to identify all classes 
 * (both regular and interface classes). Any errors and warning detected by the 
 * Context or by the MetaClasses that are created will be added to the context.
 */
public class ClassBuilder extends AbstractBuilder {

  /**
   * Assemble the code based on the information represented as a DiagramModel
   */
  public void build(Context ctx) 
    throws BuilderException {

    identifyClasses(ctx);
    identifyInterfaces(ctx);

    checkContext(ctx);

  }
  
  /**
   * Handle any errors that were detected
   */
  protected void checkContext(Context ctx) 
    throws BuilderException {

    if(ctx.hasErrors())
      throw new BuilderException("Errors were detected while identifying classes");

  }

  /**
   * Locate all the classes in the diagram. course-grained validation is performed.
   * Warning are are generated for obvious bad names and errors for duplicate classes.
   */
  protected void identifyClasses(Context ctx) {

    // Walk through the ClassFigures in the model and identify all the classes
    DiagramModel model = ctx.getModel();
    for(Iterator i = new ValueIterator(model, ClassFigure.class); i.hasNext();) {
      
      ClassItem item = (ClassItem)i.next();
      
      try { // Add the class to the context

        String className = (item == null) ? null : item.getName();
        MetaClass metaClass = new MetaClass(className, false);

        identifyAttributes(ctx, metaClass, item.getAttributes());
        identifyMethods(ctx, metaClass, item.getDescription());

        ctx.addClass(metaClass);

      } catch(SyntaxException e1) { 
        ctx.addWarning(e1.getMessage()); 
      } catch(SemanticException e2) { 
        ctx.addError(e2.getMessage()); 
      }
                       
    } 

  }

  /**
   * Locate all the interfaces in the diagram. course-grained validation is performed.
   * Warning are are generated for obvious bad names and errors for duplicate classes.
   */
  protected void identifyInterfaces(Context ctx) {

    // Walk through the InterfaceFigures in the model and identify all the interfaces
    DiagramModel model = ctx.getModel();
    for(Iterator i = new ValueIterator(model, InterfaceFigure.class); i.hasNext();) {
      
      InterfaceItem item = (InterfaceItem)i.next();
      
      try { // Add the class to the context

        String className = (item == null) ? null : item.getName();
        MetaClass metaClass = new MetaClass(className, true);

        identifyMethods(ctx, metaClass, item.getDescription());

        ctx.addClass(metaClass);

      } catch(SyntaxException e1) { 
        ctx.addWarning(e1.getMessage()); 
      } catch(SemanticException e2) { 
        ctx.addError(e2.getMessage()); 
      }
                       
    } 

  }

  protected void identifyAttributes(Context ctx, MetaClass metaClass, String attributes) {
   
    for(StringTokenizer tok = new StringTokenizer(attributes, "\f\n\r"); tok.hasMoreTokens();) {
      
      try {
        
        // Add all discover attributes and note any exceptions
        metaClass.addAttribute(new MetaAttribute(tok.nextToken()));
        
      } catch(SyntaxException e1) { 
        ctx.addWarning(e1.getMessage()); 
      } catch(SemanticException e2) { 
        ctx.addWarning(e2.getMessage()); 
      }
      
    }

  }

  protected void identifyMethods(Context ctx, MetaClass metaClass, String methods) {
   
    for(StringTokenizer tok = new StringTokenizer(methods, "\f\n\r"); tok.hasMoreTokens();) {
      
      try {
        
        // Add all discover attributes and note any exceptions
        metaClass.addMethod(new MetaMethod(tok.nextToken()));
        
      } catch(SyntaxException e1) { 
        ctx.addWarning(e1.getMessage()); 
      } catch(SemanticException e2) { 
        ctx.addWarning(e2.getMessage()); 
      }
      
    }

  }

}
