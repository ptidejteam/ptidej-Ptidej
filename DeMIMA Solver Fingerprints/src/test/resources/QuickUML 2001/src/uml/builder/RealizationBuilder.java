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

import uml.diagram.ClassFigure;
import uml.diagram.ClassItem;
import uml.diagram.InterfaceFigure;
import uml.diagram.InterfaceItem;
import uml.diagram.RealizationLink;
import diagram.DiagramModel;
import diagram.Figure;
import diagram.FigureIterator;

/**
 * @class RealizationBuilder
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * Scan the DiagramModel in the Context for all RealizationLink figures. Using this
 * information, inheritance information will be added to all the MetaClasses that have
 * been previously place in the Contex.
 */
public class RealizationBuilder implements CodeBuilder {

  /**
   * Assemble the code based on the information represented as a DiagramModel
   */
  public void build(Context ctx) 
    throws BuilderException {

    identifyRealizations(ctx);

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
   */
  protected void identifyRealizations(Context ctx) 
    throws BuilderException {

    // Walk through the RealizationLinks in the model and identify all the classes
    DiagramModel model = ctx.getModel();
    for(Iterator i = new FigureIterator(model, RealizationLink.class); i.hasNext();) {
      
      RealizationLink figure = (RealizationLink)i.next();

      Figure source = figure.getSource();
      Figure sink = figure.getSink();

      String sourceName = getName(ctx, source);
      String sinkName = getName(ctx, sink);
      
      // Make sure the link is somewhat valid before proceeding
      if(compatibleFigures(ctx, source, sink)) {
        
        // Make sure the targets of the link are present in the context
        MetaClass sourceClass = ctx.getMetaClass(sourceName);
        MetaClass sinkClass = ctx.getMetaClass(sinkName);
        
        // Found a generalization between two compatible classes that are present in 
        // the current build context
        if(sourceClass != null && sinkClass != null)
          buildRealization(ctx, sourceClass, sinkClass);

        else
          ctx.addWarning("skipping realization '" + sourceName + " - " + sinkName + "'");
        
      } else 
        ctx.addWarning("incompatible realization '" + sourceName + " - " + sinkName + "'");
    
              
    } 

  }

  
  /**
   * Course-grained check to determine if the two figures are compatible and valid
   * candidates for a realization
   */
  protected boolean compatibleFigures(Context ctx, Figure source, Figure sink) 
    throws BuilderException {
    
    // Check to see if the source & sink are compatible classes
    Class sourceClass = source.getClass();   
    Class sinkClass = sink.getClass();   

    return (sourceClass == ClassFigure.class && sinkClass == InterfaceFigure.class);

  }

  /**
   * Get the class name for a Figure in the diagram
   */
  protected String getName(Context ctx, Figure figure) {
    
    Object value = ctx.getModel().getValue(figure);
    String name = null;

    if(value instanceof ClassItem)
      name = ((ClassItem)value).getName();
    
    else if(value instanceof InterfaceItem)
      name = ((InterfaceItem)value).getName();
    
    // Remove invalid names
    return (name == null || name.length() < 0) ? "<no name>" : name;     
    
  }


  /**
   * Create the generalization if possible. Finer grain checks for cyclical inheritance
   * and for mulitple inheritance are performed.
   */
  protected void buildRealization(Context ctx, MetaClass sourceClass, MetaClass sinkClass) 
    throws BuilderException {

    // Attempt to update inheritance on the meta class
    try {

      sourceClass.addInterface(sinkClass, true);

    } catch(SyntaxException e1) {
      ctx.addWarning(e1.getMessage());
    } catch(SemanticException e2) {
      ctx.addError(e2.getMessage());
    }

  }
 

}
