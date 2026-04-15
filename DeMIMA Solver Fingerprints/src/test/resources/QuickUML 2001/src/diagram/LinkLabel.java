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

import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JTextField;
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
public class LinkLabel extends JTextField 
  implements FigureRenderer {
  
  protected Point2D p1 = null;
  protected Point2D p2 = null;

  public Document createDefaultModel() {
    return new SimpleDocument();
  }
  
  protected void updateBounds() {

  }

  /**
   *
   */
  public Component getRendererComponent(Diagram diagram, Figure figure, boolean isSelected) {
    return this;
  }

  /**
   *
   */
  public Rectangle2D getDecoratedBounds(Diagram diagram, Figure figure, Rectangle2D rcBounds) {
    
    Object label = diagram.getModel().getValue(figure);
    setText(label != null ? label.toString() : "");

    if(rcBounds == null)
      rcBounds = new Rectangle2D.Double();

    return calculateLabelBounds((PolyLink)figure, getText(), rcBounds);

  }

  /**
   * Find the longest segment.
   *
   * @param PolyLink
   * @return int segment to draw label on
   */
  protected int calculateLabelSegment(PolyLink link) {

    double d, max = 0;
    int n = 0;

    // Find the longest segment
    p2 = link.getPN(1, p2);
    p1 = link.getSource().getConnection(p2, p1);
      
    max = p1.distance(p2);
    
    for(int i = 1; i < link.getSegmentCount() - 1; i++) {
        
      p1 = link.getPN(i, p1);
      p2 = link.getPN(i+1, p2);

      if((d = p1.distance(p2)) > max) {
        max = d;
        n = i;
      }

    }

    p2 = link.getPN(link.getPointCount() - 2, p2);
    p1 = link.getSink().getConnection(p2, p1);

    if((d = p1.distance(p2)) > max)
      n = link.getSegmentCount() - 1;

    return n;

  }

  /**
   * Calculate where the label should be placed
   *
   * @param PolyLink
   * @param Rectangle2D rectangle to fill with the bounds
   */
  public Rectangle2D calculateLabelBounds(PolyLink link, String text, Rectangle2D rc) {

    int seg = calculateLabelSegment(link);
    
    double dx = 0;//getX(); 
    double dy = 0;//getY();

    double x1 = link.getXN(seg) - dx; double x2 = link.getXN(seg+1) - dx;
    double y1 = link.getYN(seg) - dy; double y2 = link.getYN(seg+1) - dy;

    dx = (x1 > x2) ? (x1 - x2)*-1 : (x2 - x1);
    dy = (y1 > y2) ? (y1 - y2)*-1 : (y2 - y1);

    FontMetrics metrics = getFontMetrics( getFont() );
    
    int w = metrics.charsWidth(text.toCharArray(), 0, text.length());
    int h = metrics.getHeight();

    dx -= w;
    dy -= h;

    dx /= 2;
    dy /= 2;

    if(rc == null)
      return new Rectangle2D.Double((x1 + dx), (y1 + dy), w, h);

    rc.setFrame((x1 + dx), (y1 + dy), w, h);
    return rc;
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
