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

package uml.diagram;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;

import javax.swing.UIManager;

import diagram.DefaultLinkRenderer;
import diagram.shape.ArrowHead;

/**
 * @class RealizationLinkRenderer
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0 
 *
 * Renderer for RealizationLink Figures
 */
public class RealizationLinkRenderer extends DefaultLinkRenderer {
  
  protected static final CustomUI realizationUI = new CustomUI("realization");

  protected final static BasicStroke normal = new BasicStroke(1);
  protected final static BasicStroke dashed = new BasicStroke(1.0f, 
                                                              BasicStroke.CAP_ROUND, 
                                                              BasicStroke.JOIN_MITER, 
                                                              10.0f, new float[] {5.0f}, 0.0f);

  static {
    UIManager.put("realization.foreground", Color.black);
    UIManager.put("realization.background", Color.white);
  }

  /**
   * Create a new Renderer
   */
  public RealizationLinkRenderer() {

    setUI(realizationUI);

  }

  /**
   * Paint this link with a dashed Stroke
   */
  public void paintComponent(Graphics g) {
    ((Graphics2D)g).setStroke(dashed);
    super.paintComponent(g);
  }
  
  /**
   * No decoration on the source endpoint
   */
  protected GeneralPath getSourceEndpoint(double x, double y, GeneralPath path) {
    return null;
  }

  /**
   * Create flat ArrowHead shape for the sink endpoint
   */
  protected GeneralPath getSinkEndpoint(double x, double y, GeneralPath path) {
    return ArrowHead.createArrowHead(13.0, ArrowHead.FLAT, x, y, path);
  }

  /**
   * Paint the sink endpoint with a solid stroke.
   */
  protected void paintSinkEndpoint(Graphics2D g2, AffineTransform at, GeneralPath path) {
    
    // Set the stroke & fill color
    g2.setStroke(normal);
    g2.setPaint(getBackground());

    super.paintSinkEndpoint(g2, at, path);

    // Restore the line color & dashed stroke
    g2.setPaint(getForeground());
    g2.draw(path);
    g2.setStroke(dashed);

  }


}
