/**
 *
    Java Diagram Package; An extremely flexible and fast multipurpose diagram 
    component for Swing.
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

package diagram;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import diagram.figures.PolyLink;


/**
 * @class DefaultLabelRenderer
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * This render paints a PolyLink as its label in the correct position
 */
public class DefaultLabelRenderer extends JTextField 
  implements FigureRenderer {
  
  protected Insets insets = new Insets(0,0,0,0);

  protected Point p1 = new Point();
  protected Point p2 = new Point();
  
  private int lastWidth;
  private final static int MAXIMUM_WIDTH = 150;

  static {
    UIManager.put("label.foreground", Color.black);
    UIManager.put("label.background", Color.white);
  }

  public DefaultLabelRenderer() {

    setBackground(UIManager.getColor("label.background"));
    setForeground(UIManager.getColor("label.foreground"));

  }

  public Document createDefaultModel() {
    return new SimpleDocument();
  }
  
  /**
   *
   */
  public Component getRendererComponent(Diagram diagram, Figure figure, boolean isSelected) {

    Object label = diagram.getModel().getValue(figure);
    setText(label != null ? label.toString() : "");

    return this;

  }

  /**
   *
   */
  public Rectangle2D getDecoratedBounds(Diagram diagram, Figure figure, Rectangle2D rcBounds) {
    
    if(rcBounds == null)
      rcBounds = new Rectangle2D.Double();

    return calculateLabelBounds((PolyLink)figure, getText(), rcBounds);

  }

  /**
   * Find the longest segment. Update the p1 & p2 members to contain the 
   * segment to draw the label in.
   *
   * @param PolyLink
   */
  protected void calculateSegment(PolyLink link) {

    double d, max = 0;
    int n = 0;

    // Find the longest segment
    p2 = (Point)link.getPN(1, p2);
    p1 = (Point)link.getSource().getConnection(p2, p1);
      
    max = p1.distance(p2);
    
    for(int i = 1; i < link.getSegmentCount() - 1; i++) {
        
      p1 = (Point)link.getPN(i, p1);
      p2 = (Point)link.getPN(i+1, p2);

      if((d = p1.distance(p2)) > max) {
        max = d;
        n = i;
      }

    }

    p2 = (Point)link.getPN(link.getPointCount() - 2, p2);
    p1 = (Point)link.getSink().getConnection(p2, p1);

    if((d = p1.distance(p2)) > max)
      n = link.getSegmentCount() - 1;

    // Get the longest point
    p2 = (Point)link.getPN(n, p2);
    p1 = (Point)link.getPN(n+1, p1);

  }

  /**
   * Calculate where the label should be placed
   *
   * @param PolyLink
   * @param Rectangle2D rectangle to fill with the bounds
   */
  protected Rectangle2D calculateLabelBounds(PolyLink link, String text, Rectangle2D rc) {

    calculateSegment(link);
    
    double x1 = p1.x;
    double y1 = p1.y;
    double x2 = p2.x;
    double y2 = p2.y;
    
    double dx = (x1 > x2) ? (x1 - x2)*-1 : (x2 - x1);
    double dy = (y1 > y2) ? (y1 - y2)*-1 : (y2 - y1);

    FontMetrics metrics = getFontMetrics( getFont() );
    insets = getInsets(insets);
    
    int w = metrics.charsWidth(text.toCharArray(), 0, text.length());
    w += insets.left + insets.right;
    w = Math.min(MAXIMUM_WIDTH, w);

    int h = metrics.getHeight();
    h += insets.top + insets.bottom;

    dx -= w;
    dy -= h;

    dx /= 2;
    dy /= 2;


    y1 -= insets.top;
    x1 -= insets.left;

    lastWidth = w;

    if(rc == null)
      return new Rectangle2D.Double((x1 + dx), (y1 + dy), w, h);

    rc.setFrame((x1 + dx), (y1 + dy), w, h);
    return rc;
  }

  /**
   * Change the width to fit the text
   */
  protected void updateBounds() {

    String text = getText();

    FontMetrics metrics = getFontMetrics( getFont() );
    int w = metrics.charsWidth(text.toCharArray(), 0, text.length());
    w = Math.min(MAXIMUM_WIDTH, w);

    int dx = (w - lastWidth);
    lastWidth = w;

    setBounds(getX()-dx/2, getY(), Math.max(getWidth()+dx, 5), getHeight());

  }


  /**
   * Modified document that notifies the component of the text changing.
   */
  class SimpleDocument extends PlainDocument {
    
    public void insertString(int offs, String str, AttributeSet a) 
      throws BadLocationException {
      
      super.insertString(offs, str, a);
      updateBounds();
      
    }
    
    public void remove(int offs, int len)
      throws BadLocationException {
      
      super.remove(offs, len);
      updateBounds();
      
    }
    
  }


}
