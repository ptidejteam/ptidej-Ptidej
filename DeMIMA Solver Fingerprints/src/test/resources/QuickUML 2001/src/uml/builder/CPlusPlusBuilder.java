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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

import util.FilteredIterator;

/**
 * @class CPlusPlusBuilder
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 */
public class CPlusPlusBuilder extends ObjectBuilder {

  protected static final MetaAccessComparator attributeComparator = new MetaAccessComparator();
  protected String prefix;

  /**
   *
   */
  public CPlusPlusBuilder(String prefix) {
    this.prefix = prefix;
  }   

  /**
   *
   */
  public void build(Context ctx) 
    throws BuilderException {

    super.build(ctx);

    try {

      // Create the code for the headers first
      for(Iterator i = ctx.getInterfaces(); i.hasNext();) {
        
        MetaClass metaClass = (MetaClass)i.next();
        PrintWriter out = getWriter(metaClass, "h");
        
        writeHeader(metaClass, out);

        out.flush();

      }

      for(Iterator i = ctx.getClasses(); i.hasNext();) {

        MetaClass metaClass = (MetaClass)i.next();
        PrintWriter out = getWriter(metaClass, "h");

        writeHeader(metaClass, out);
        writeSource(metaClass, getWriter(metaClass, "cc"));

        out.flush();

      }

    } catch(IOException e) { throw new BuilderException("I/O Error: " + e.getMessage()); }   

  }


  /**
   * Get an output stream for a class
   */
  protected PrintWriter getWriter(MetaClass meta, String ext) 
    throws IOException {

    return new PrintWriter(new FileOutputStream(prefix + "/" + meta.getName() + "." + ext));

  }

 
  /**
   * Write the header
   */
  protected void writeHeader(MetaClass metaClass, PrintWriter out)
    throws IOException {

    // Write the ifndefs
    String def = "_" + metaClass.getName().toUpperCase() + "_H_";

    out.println("#ifndef " + def);
    out.println("#define " + def + "\n");
    

    // Collect all inheritance infor
    Vector v = new Vector();
    MetaClass superClass = metaClass.getSuperClass();
    if(superClass != null) 
      v.add(superClass.getName());

    for(Iterator i = metaClass.getInterfaces(); i.hasNext();)
      v.add(((MetaClass)i.next()).getName());

    // Write the includes needed
    for(int j=0; j < v.size(); j++) 
      out.println("#include \"" + v.get(j) + ".h\"");
    
    Vector used = new Vector();
    for(Iterator i = new FilteredIterator(metaClass.getAttributes(), MetaAssociation.class); i.hasNext();) {
      MetaAttribute attr = (MetaAttribute)i.next();
      String name = attr.getType();
      if(!v.contains(name) && !used.contains(name)) {
        out.println("#include \"" + name + ".h\"");
        used.add(name);
      }
    }

      // Write declaration
    out.print("\nclass " + metaClass.getName());
    int sz = v.size();
    if(sz > 0) {

      out.print(" : public " + v.get(0));
     
      for(int j=1; j < sz; j++) 
        out.print(", public " + v.get(j));     
     
    }

    out.print(" {\n");
    
    // Write the attributes if this is a class
    if(!metaClass.isInterface())
      writeHeaderAttributes(metaClass, out);

    // Write the methods
    writeHeaderMethods(metaClass, out);


    out.println("};\n");
    out.println("#endif // " + def);

  }

  /**
   * Write the attributes
   */
  protected void writeHeaderAttributes(MetaClass metaClass, PrintWriter out)
    throws IOException {

    // Collect & sort all the attributes
    Vector v = new Vector();
    for(Iterator i = metaClass.getAttributes(); i.hasNext();) 
      v.add(i.next());
        
    Collections.sort(v, attributeComparator);

    int n, last = 0;
    for(int j=0; j < v.size(); j++) {

      MetaAttribute attr = (MetaAttribute)v.get(j);

      // C's default acces is private, so clarify it in the header
      n = (attr.getAccess() & ~(MetaAccess.ABSTRACT | MetaAccess.STATIC));
      if(n == 0)
        n = MetaAccess.PRIVATE;

      if(last != n) {
        last = n;
        out.println(MetaAccess.toString(n) + ":\n");
      }
        
      out.println("\t" + attr.getType() + " " + attr.getName() + ";");

    }
        
    out.print("\n");

  }

  /**
   * Write the attributes section, with a little space in between top and bottom
   */
  protected void writeHeaderMethods(MetaClass metaClass, PrintWriter out)
    throws IOException {


    // Collect & sort all the methods
    int last = MetaAccess.PUBLIC;
    out.println("public: ");

    Vector v = new Vector();
    for(Iterator i = metaClass.getMethods(); i.hasNext();) {
      Object o = i.next();
      if(o instanceof MetaConstructor)
        writeHeaderConstructor(metaClass, (MetaConstructor)o, out);
      
      else
        v.add(o);
    }

    // Write a descrutor
    writeHeaderDestructor(metaClass, out);

    int n;
    for(int j=0; j < v.size(); j++) {

      MetaMethod meth = (MetaMethod)v.get(j);

      // C's default acces is private, so clarify it in the header
      n = (meth.getAccess() & ~(MetaAccess.ABSTRACT | MetaAccess.STATIC));
      if(n == 0)
        n = MetaAccess.PRIVATE;

      if(last != n) {
        last = n;
        out.println("\n" + MetaAccess.toString(n) + ":\n");
      }

      String signature = meth.getType() + " " + meth.getName() + "(" + MetaParameter.toString(meth) + ")";
      if(meth.hasExceptions()) 
        signature += " throw(" + MetaException.toString(meth) + ")";

      if(metaClass.isInterface())
        out.println("\tvirtual " + signature + " = 0;");
      else {

        if(MetaAccess.isAbstract(meth.getAccess())) 
          signature = "virtual " + signature + " = 0";
        else if(MetaAccess.isStatic(meth.getAccess())) 
          signature = "static " + signature;

        out.println("\t" + signature + ";");

      }

    }
        
    out.print("\n");

  }

  /**
   */
  protected void writeHeaderDestructor(MetaClass metaClass, PrintWriter out)
    throws IOException {

    out.print("\tvirtual ~" + metaClass.getName() + "()");
    out.println(metaClass.isInterface() ? " {};" : ";");

  }

  /**
   */
  protected void writeHeaderConstructor(MetaClass metaClass, MetaConstructor cons, PrintWriter out)
    throws IOException {
 
    String signature = metaClass.getName() + "(" + MetaParameter.toString(cons) + ")";
    if(cons.hasExceptions()) 
      signature += " throw(" + MetaException.toString(cons) + ")";

    out.print("\t" + signature);
    out.println(metaClass.isInterface() ? " {};" : ";");

  }

  /**
   */
  protected void writeSource(MetaClass metaClass, PrintWriter out)
    throws IOException {
    
    out.println("#include \"" + metaClass.getName() + ".h\"\n");
    
    boolean writtenDestructor = false;
    for(Iterator i = metaClass.getMethods(); i.hasNext();) {

      MetaMethod method = (MetaMethod)i.next();
      if(method instanceof MetaConstructor) 
        writeSourceConstructor(metaClass, (MetaConstructor)method, out);

      else {

        if(!writtenDestructor) {
          writtenDestructor = true;
          writeSourceDestructor(metaClass, out);
        }

        out.print(method.getType() + " " + metaClass.getName() + "::" + method.getName());
        out.print("(" + MetaParameter.toString(method) + ")");

        if(method.hasExceptions()) 
          out.print(" throw(" + MetaException.toString(method) + ")");
                  
        out.print(" { \n\n");
        
        out.print("}\n\n");

      }

    }

    out.flush();

  }

  /**
   */
  protected void writeSourceConstructor(MetaClass metaClass, MetaConstructor cons, PrintWriter out)
    throws IOException {

    out.print(metaClass.getName() + "::" + metaClass.getName());
    out.print("(" + MetaParameter.toString(cons) + ")");
    
    if(cons.hasExceptions()) 
      out.print(" throw(" + MetaException.toString(cons) + ")");
    
    // Write an initializations
    int n = 0;
    for(Iterator i = cons.getAssociations(), j = cons.getParameters(); i.hasNext();) {
      
      MetaAttribute attr = (MetaAttribute)i.next();
      MetaParameter param = (MetaParameter)j.next();

      if(n++ == 0)
        out.print("\n\t: ");
      else
        out.print(", ");

      out.print(attr.getName());
      out.print("(" + param.getName() + ")");
      
    }

    // Write the constructor body
    out.println(" {\n");

    for(Iterator i = cons.getCompositions(); i.hasNext();) {
      
      MetaAttribute attr = (MetaAttribute)i.next();
      out.println("\t" + attr.getName() + " = new " + attr.getType() + "();");
      
    }
        
    out.println("\n}\n");
      
  }

  /**
   */
  protected void writeSourceDestructor(MetaClass metaClass, PrintWriter out)
    throws IOException {

    out.println(metaClass.getName() + "::~" + metaClass.getName() + "{\n");

    for(Iterator i = new FilteredIterator(metaClass.getAttributes(), MetaComposition.class); i.hasNext();) {
      
      MetaAttribute attr = (MetaAttribute)i.next();
      out.println("\tdelete " + attr.getName() + ";");
      
    }

    out.println("\n}\n");

  }
    
}
