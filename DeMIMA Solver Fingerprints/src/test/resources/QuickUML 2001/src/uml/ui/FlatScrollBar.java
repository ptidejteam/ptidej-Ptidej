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
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * @class FlatScrollBar
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * This is a flat-style scroll bar.
 */
public class FlatScrollBar extends JScrollBar {

  public FlatScrollBar() {
    this(VERTICAL);
  }

  public FlatScrollBar(int orientation) {
    super(orientation);
  }

  public void updateUI() {
    setUI(new FlatScrollBarUI());
  }

  /**
   * @class FlatScrollBarUI
   * @author Eric Crahen
   */
  public static class FlatScrollBarUI extends BasicScrollBarUI {

    protected void installDefaults() {
      super.installDefaults();
      scrollbar.setBorder(null);
    }

    protected ModelListener createModelListener() {
    	return new ModelListener() {

       	public void stateChanged(ChangeEvent e) {

          super.stateChanged(e);

          BoundedRangeModel mdl = (BoundedRangeModel)e.getSource();

          showDecrementButton(mdl.getValue() != 0);
          showIncrementButton((mdl.getValue() + mdl.getExtent()) != mdl.getMaximum());

        }
     };
    }

    public void paint(Graphics g, JComponent comp) {

      int w = scrollbar.getSize().width;
      int h = scrollbar.getSize().height;

      Color c = FlatScrollPane.getViewBackground((JScrollPane)comp.getParent());
      if(c != null)
        g.setColor(c);

      g.fillRect(0, 0, w, h);

    }

    public Dimension getPreferredSize(JComponent c) {
    	return (scrollbar.getOrientation() == JScrollBar.VERTICAL) ? new Dimension(10, 48) : new Dimension(48, 10);
    }


    protected void paintDecreaseHighlight(Graphics g) {
    }

    protected void paintIncreaseHighlight(Graphics g) {
    }

    protected JButton createDecreaseButton(int orientation)  {
      return new FlatArrowButton(orientation);
    }

    protected JButton createIncreaseButton(int orientation)  {
      return new FlatArrowButton(orientation);
    }

    private final void showIncrementButton(boolean flag) {
      incrButton.setVisible(flag);
    }

    private final void showDecrementButton(boolean flag) {
      decrButton.setVisible(flag);
    }

  }

}
