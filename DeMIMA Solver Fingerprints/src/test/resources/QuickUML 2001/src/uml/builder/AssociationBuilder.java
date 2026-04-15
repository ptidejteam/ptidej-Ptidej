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

import uml.diagram.AssociationLink;
import uml.diagram.ClassFigure;
import uml.diagram.InterfaceFigure;
import diagram.DiagramModel;
import diagram.Figure;
import diagram.FigureIterator;

/**
 * @class AssociationBuilder
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * Scan the DiagramModel in the Context for all AssociationLink figures. Using this
 * information, inheritance information will be added to all the MetaClasses that have
 * been previously place in the Contex.
 */
public class AssociationBuilder extends AbstractBuilder {


  /**
   * Assemble the code based on the information represented as a DiagramModel
   */
  public void build(Context ctx) 
    throws BuilderException {

    identifyAssociations(ctx);

    checkContext(ctx);
    
  }

  /**
   */
  protected void identifyAssociations(Context ctx) {

    // Walk through the AssociationLinks in the model and identify all the classes
    DiagramModel model = ctx.getModel();
    for(Iterator i = new FigureIterator(model, AssociationLink.class); i.hasNext();) {
      
      AssociationLink figure = (AssociationLink)i.next();

      Figure source = figure.getSource();
      Figure sink = figure.getSink();

      String sourceName = getName(ctx, source);
      String sinkName = getName(ctx, sink);

      if(compatibleFigures(ctx, source, sink)) {
      
        // Make sure the targets of the link are present in the context
        MetaClass sourceClass = ctx.getMetaClass(sourceName);
        MetaClass sinkClass = ctx.getMetaClass(sinkName);
        
        // Found a generalization between two compatible classes that are present in 
        // the current build context
        if(sourceClass != null && sinkClass != null) 
          buildAssociation(ctx, sourceClass, sinkClass);

        else
          ctx.addWarning("ignoring association '" + sourceName + " - " + sinkName + "'");
      
      } else {

        if(validFigure(source))
          ctx.addWarning("ignoring invalid association on '" + sourceName + "'");
        else if(validFigure(sink))
          ctx.addWarning("ignoring invalid association from '" + sinkName + "'");
        else
          ctx.addWarning("ignoring invalid association");

      }
      
    } 

  }

  
  /**
   * Check compatiblility
   */
  protected boolean compatibleFigures(Context ctx, Figure source, Figure sink) {
    
    // Check to see if the source & sink are compatible classes
    Class sourceClass = source.getClass();   
    Class sinkClass = sink.getClass();   

    return validFigure(source) && sourceClass == sinkClass;

  }

  protected boolean validFigure(Figure source) {
    Class sourceClass = source.getClass(); 
    return (sourceClass == ClassFigure.class || sourceClass == InterfaceFigure.class);
  }


  /**
   * Create the generalization if possible. Finer grain checks for cyclical inheritance
   * and for mulitple inheritance are performed.
   */
  protected void buildAssociation(Context ctx, MetaClass sourceClass, MetaClass sinkClass) {

    // Attempt to update inheritance on the meta class
    try {

      sourceClass.addAttribute(new MetaAssociation("private " + sinkClass.getName()));

    } catch(SyntaxException e1) {
      ctx.addWarning(e1.getMessage());
    } catch(SemanticException e2) {
      ctx.addError(e2.getMessage());
    }

  }
 

}
