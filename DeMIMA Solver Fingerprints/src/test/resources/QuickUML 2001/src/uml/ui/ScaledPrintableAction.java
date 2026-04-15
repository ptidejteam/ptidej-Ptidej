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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;

import javax.swing.Icon;
import javax.swing.RepaintManager;


/**
 * @class ScaledPrintableAction
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 * 
 * Action that provides some generic component printing capabilites
 */
public abstract class ScaledPrintableAction extends PrintableAction {

  /**
   * Create a new Action
   */
  public ScaledPrintableAction(String name, Icon icon) {
    super(name, icon);
  }


  /**
   * Implement Printable
   */
  public int print(Graphics g, PageFormat pf, int pageIndex) {

    if(pageIndex > 0)
      return Printable.NO_SUCH_PAGE;

    Component component = getComponent();

    // Disable buffered for a quick paint into the canvas
    RepaintManager mgr = RepaintManager.currentManager(component);
    boolean isBuffered = mgr.isDoubleBufferingEnabled();
    mgr.setDoubleBufferingEnabled(false);  

    
    // Transform & Paint 
    Graphics2D g2 = (Graphics2D)g;
   
    double sx = (double)pf.getImageableWidth() / (double)component.getWidth();
    double sy = (double)pf.getImageableHeight() / (double)component.getHeight();

    double scale = (sx < sy) ? sx : sy;
    g2.scale(scale, scale);

    double x = (double)pf.getImageableX() - (double)component.getX();
    double y = (double)pf.getImageableY() - (double)component.getY();

    g2.translate(x, y);

    component.paint(g2);

    // Reset buffering
    mgr.setDoubleBufferingEnabled(isBuffered);  

    return Printable.PAGE_EXISTS;		
					
  }

}
