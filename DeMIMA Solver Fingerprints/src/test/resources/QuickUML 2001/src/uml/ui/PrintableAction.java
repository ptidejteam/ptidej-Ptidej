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
import java.awt.event.ActionEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.RepaintManager;

/**
 * @class PrintableAction
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 * 
 * Action that provides some generic component printing capabilites
 */
public abstract class PrintableAction extends AbstractAction 
  implements Printable {

  /**
   * Create a new Action
   */
  public PrintableAction(String name, Icon icon) {
    super(name, icon);
  }

  /**
   * Create a new Action
   */
  public PrintableAction(String name) {
    super(name);
  }

  /**
   * Get the component to print
   */
  abstract public Component getComponent();

  /**
   * Implement Printable
   */
  public int print(Graphics g, PageFormat pf, int pageIndex) {

    Component component = getComponent();

    // Create a canvas to paint on
    Graphics2D g2 = (Graphics2D)g;
    // double x, y;
    double x;

    // Check the width of the page being printed
    if(pf.getOrientation() != PageFormat.LANDSCAPE)
      x = pageIndex*pf.getImageableWidth();
    else 
      x = pageIndex*pf.getImageableHeight();

    // If the coords would be out of bounds, there is no page here 
    // TODO, shift back left and down to print the tall pages
    if(x > component.getWidth())
      return Printable.NO_SUCH_PAGE;
    
    x += pf.getImageableX();
    g2.translate(x, pf.getImageableY()); 

    // Disable buffered for a quick paint into the canvas
    RepaintManager mgr = RepaintManager.currentManager(component);
    boolean isBuffered = mgr.isDoubleBufferingEnabled();
    mgr.setDoubleBufferingEnabled(false);  
    
    // Paint 
    component.paint(g2);

    // Reset buffering
    mgr.setDoubleBufferingEnabled(isBuffered);  
    return Printable.PAGE_EXISTS;		
					
  }

  /**
   * Display a print dialog
   */
  public void print()
    throws PrinterException {

    PrinterJob job = PrinterJob.getPrinterJob();
    job.setPrintable(this);
    if(job.printDialog()) 
      job.print();
      
  }

  public void actionPerformed(ActionEvent e) {

    try {
      print();
    } catch(Throwable t) { 
      JOptionPane.showMessageDialog(getComponent(), t.getMessage(), "Print Error", JOptionPane.ERROR_MESSAGE);
    }

  }

}
