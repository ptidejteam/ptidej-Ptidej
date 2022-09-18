/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package epi.test.quickuml;

import java.util.Iterator;
import java.util.Vector;

/**
 * @author guehene
 */
public class CompositeTool implements Tool {
  private Vector tools = new Vector();

  /**
   * Add a new listener to this tool.
   *
   * @param ToolListener
   */
  public void addToolListener(Tool l) {

    for(Iterator i = this.tools.iterator(); i.hasNext();)
      ((Tool)i.next()).removeToolListener(l);

  }

  /**
   * Remove a listener to this tool.
   *
   * @param ToolListener
   */
  public void removeToolListener(Tool l) {

    for(Iterator i = this.tools.iterator(); i.hasNext();)
      ((Tool)i.next()).removeToolListener(l);

  }

  public void install(Tool diagram) {
    
    for(Iterator i = this.tools.iterator(); i.hasNext();)
      ((Tool)i.next()).install(diagram);

  }

  public void uninstall(Tool diagram) {

    for(Iterator i = this.tools.iterator(); i.hasNext();)
      ((Tool)i.next()).uninstall(diagram);

  }

  public void add(Tool tool) {
    if(!this.tools.contains(tool))
      this.tools.add(tool);
  }

  public void remove(Tool tool) {
    this.tools.remove(tool);
  }

}
