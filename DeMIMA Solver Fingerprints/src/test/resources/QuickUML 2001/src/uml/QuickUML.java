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

package uml;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

import uml.ui.DiagramContainer;
import uml.ui.FlatMenuBar;
import uml.ui.ToolPalette;

/**
 * @class QuickUML
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 */
public class QuickUML extends JFrame {

  protected QuickUML() {

    super("UML Application");

    // Create the content area
    DiagramContainer container = new DiagramContainer();
    ToolPalette palette = new ToolPalette(container);

    // Create the menubar & initialize it
    FlatMenuBar menuBar = new FlatMenuBar(); 
    container.updateMenus(menuBar);
    palette.updateMenus(menuBar);
    updateMenus(menuBar);

    // Update the content
    Container content = getContentPane();
    content.setLayout(new BorderLayout());

    content.add(menuBar, BorderLayout.NORTH);
    content.add(container);
    content.add(palette, BorderLayout.WEST);

  }

  /**
   * Update the JMenuBar before its installed. Add exit option, etc.
   *
   * @param JMenuBar
   */
  public void updateMenus(FlatMenuBar menuBar) {

    JMenu menu = menuBar.getMenu("File");

    menu.add(new JSeparator(), -1);    
    menu.add(new JMenuItem(new QuitAction()), -1);

    menu = menuBar.getHelpMenu();    
    menu.add(new JMenuItem(new AboutAction()), -1);

  }

  /**
   * @class QuitAction
   */
  class QuitAction extends AbstractAction {

    QuitAction() {
      super("Quit");
    }

    public void actionPerformed(ActionEvent e) {
      System.exit(0);
    }

  }


  /**
   * @class AboutAction
   */
  class AboutAction extends AbstractAction {

    JComponent about = new JLabel("<HTML>Created By: <B>Eric Crahen</B><CENTER>Copyright <B>(c)</B> 2001<CENTER><HTML>", JLabel.CENTER);

    AboutAction() {
      super("About");
    }

    public void actionPerformed(ActionEvent e) {
      JOptionPane.showOptionDialog(null, about, "About", JOptionPane.OK_OPTION ,JOptionPane.PLAIN_MESSAGE, null, new Object[] {"OK"}, null );
    }

  }

  public static void main(String[] args) {

    try {

      QuickUML app = new QuickUML();

      // Fit to screen
      Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); 
      app.setBounds(dim.width/8, dim.height/8, dim.width*3/4, dim.height*3/4);

      app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      app.setVisible(true);

    } catch(Throwable t) {
      t.printStackTrace();
      System.exit(0);
    }

  }

}
