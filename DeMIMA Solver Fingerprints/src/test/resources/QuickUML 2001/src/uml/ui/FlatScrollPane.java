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

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.plaf.basic.BasicScrollPaneUI;

/**
 * @class FlatScrollPane
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * This is a flat-style scroll pane
 */
public class FlatScrollPane extends JScrollPane {

  public FlatScrollPane() {
    this(null, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
  }

  public FlatScrollPane(Component view) {
    this(view, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
  }

  public FlatScrollPane(int vsbPolicy, int hsbPolicy) {
    this(null, vsbPolicy, hsbPolicy);
  }

  public FlatScrollPane(Component view, int vsbPolicy, int hsbPolicy) {
    super(view, vsbPolicy, hsbPolicy);
  }

  public void updateUI() {
    setUI(new FlatScrollPaneUI());
  }

  /**
   * Get the background color of the view component or null
   */
  public static Color getViewBackground(JScrollPane pane) {

    JViewport viewport = pane.getViewport();
    Component view = null;
    if(viewport != null && (view = viewport.getView()) != null)
      return view.getBackground();

    return null;

  }

  public JScrollBar createHorizontalScrollBar() {
    return new FlatScrollBar(JScrollBar.HORIZONTAL);
  }

  public JScrollBar createVerticalScrollBar() {
    return new FlatScrollBar(JScrollBar.VERTICAL);
  }

  /**
   * @class FlatScrollPaneUI
   * @author Eric Crahen
   */
  protected static class FlatScrollPaneUI extends BasicScrollPaneUI {

    public void installUI(JComponent x) {

      super.installUI(x);

      // Add a transparent corner between the scrollbars
      final JScrollPane thisPane = scrollpane;
      scrollpane.setCorner(getScrollBarCorner(), new Component() {

        public void paint(Graphics g) {

          int w = getSize().width;
          int h = getSize().height;

          Color c = getViewBackground(thisPane);
          if(c != null)
            g.setColor(c);

          g.fillRect(0, 0, w, h);

        }

      });

    }

    public void uninstallUI(JComponent c) {
      scrollpane.setCorner(getScrollBarCorner(), null);
      super.uninstallUI(c);
    }

    protected String getScrollBarCorner() {
      return LOWER_RIGHT_CORNER;
    }

  }

}
