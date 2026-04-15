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

package uml.ui;

import java.awt.Component;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * @class FileAction
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * Action that provides a smarter JFileChooser
 */
public abstract class FileAction extends AbstractAction {

  private SmartChooser chooser = new SmartChooser();

  /**
   * Create a new action
   */
  public FileAction(String name, Icon icon) {
    super(name, icon);
  }

  /**
   * Create a new action
   */
  public FileAction(String name) {
    super(name);
  }

  
  /**
   * Get the JFileChooser shared among all FileActions
   */
  protected SmartChooser getChooser() {
    // Remove all the filters before letting another object reuse it
    chooser.resetChoosableFileFilters();
    return chooser;
  }


  /**
   * @class SmartChooser
   * 
   * File chooser that will remeber the users last selected directory
   */
  protected static class SmartChooser extends JFileChooser {
    
    protected static File lastDirectory = new File(System.getProperty("user.dir"));
    
    /** 
     * Remember last directory
     */
    public void approveSelection() {
      
      super.approveSelection();
      
      // Remember last directory
      lastDirectory = getSelectedFile();
      if(!lastDirectory.isDirectory())
        lastDirectory = lastDirectory.getParentFile();
      
    }
    
    /**
     * Switch to last used directory
     */
    public int showDialog(Component parent, String approveButtonText) {
      
      setCurrentDirectory(lastDirectory);
      return super.showDialog(parent, approveButtonText);
      
    }

    public String getLastDirectory() {
      return lastDirectory.getAbsolutePath();
    }

  } /* SmartChooser */


  /**
   * @class SimpleFilter
   *
   * Simple file filter
   */
  protected class SimpleFilter extends FileFilter {
    
    private String[] extensions;
    private String description;

    public SimpleFilter(String extension, String description) {
      this(new String[]{extension}, description);
    }

    public SimpleFilter(String[] extensions, String description) {
      this.extensions = extensions;
      this.description = description;
    }

    public boolean accept(File file) {
    
      if(file.isDirectory())
        return true;

      // Match an extension
      String name = file.getName().toLowerCase();
      for(int i=0; i<extensions.length; i++)
        if(name.endsWith("." + extensions[i]))
          return true;
      
      return false;

    }
  
    public String getDescription() {

      StringBuffer buf = new StringBuffer(description);

      buf.append('(');

      for(int i=0; i<extensions.length; i++) {

        buf.append("*.").append(extensions[i]);
        if(i < (extensions.length-1))
          buf.append(", ");

      }
      
      buf.append(')');

      return buf.toString();
    }

  } /* SimpleFilter */

}  
