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

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;

import javax.swing.UIManager;

import diagram.DefaultLinkRenderer;
import diagram.Diagram;
import diagram.Figure;
import diagram.shape.DiamondHead;

/**
 * @class CompositionLinkRenderer
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 */
public class CompositionLinkRenderer extends DefaultLinkRenderer {

  protected static final CustomUI compositionUI = new CustomUI("composition"); 

  private double angle, sourceX, sourceY;
  private String cardinality;
  private Point pt = new Point();

  static {
    UIManager.put("composition.foreground", Color.black);
    UIManager.put("composition.background", Color.white);
  }

  public CompositionLinkRenderer() {
    
    super(new SinkLabelRenderer());

    setUI(compositionUI);

  }

  public Component getRendererComponent(Diagram diagram, Figure figure, boolean isSelected) {

    CompositionItem item = (CompositionItem)diagram.getModel().getValue(figure);
    cardinality = item == null ? "" : item.getCardinality();

    return super.getRendererComponent(diagram, figure, isSelected);

  }

  public Rectangle2D getDecoratedBounds(Diagram diagram, Figure figure, Rectangle2D rcBounds) {

    rcBounds = super.getDecoratedBounds(diagram, figure, rcBounds);
    Font font = getFont();
    if(font == null)
      return rcBounds;
 
    FontMetrics metrics = getFontMetrics(font);    
    int expansion = (int)getExpansion();

    int h = Math.max(metrics.getHeight() + 4 - expansion, 0);
    int w = metrics.charsWidth(cardinality.toCharArray(), 0, cardinality.length());
    w = Math.max(w - expansion, 0);

    rcBounds.setFrame( rcBounds.getX() - w, 
                       rcBounds.getY() - h, 
                       rcBounds.getWidth() + w*2, 
                       rcBounds.getHeight() + h*2);

    return rcBounds; 

  }

  protected GeneralPath getSinkEndpoint(double x, double y, GeneralPath path) {
    // This is not painted, only returned so that the paintSinkEndpoint will be invoked
    return DiamondHead.createDiamondHead(x, y, 7.0, 6.0, path);
  }

  protected GeneralPath getSourceEndpoint(double x, double y, GeneralPath path) {
    return DiamondHead.createDiamondHead(x, y, 7.0, 6.0, path);
  }


  protected double getSinkAngle(double x1, double y1, double x2, double y2) {

    double angle = super.getSinkAngle(x1, y1, x2, y2); 

    FontMetrics metrics = getFontMetrics(getFont());

    int h = metrics.getHeight();
    int w = metrics.charsWidth(cardinality.toCharArray(), 0, cardinality.length());

    pt.x = (int)(x2 + 24.0);
    pt.y = (int)(y2 - 4.0);

    if(angle <= -Math.PI/4)
      pt.translate(w, 0);

    else if(angle > -Math.PI/4 && angle < Math.PI/4) 
      pt.translate(w, -h);
    
    else if(angle <= Math.PI*3/4) 
      pt.translate(0, -h);

    else if(angle <= Math.PI*5/4) 
      pt.translate(0, 0);

      
    return (this.angle = angle);

  }


  protected void paintSourceEndpoint(Graphics2D g2, AffineTransform at, GeneralPath path) {
    
    g2.setPaint(getBackground());
    super.paintSourceEndpoint(g2, at, path);

    g2.setPaint(getForeground());
    g2.draw(path);
  }

  protected void paintSinkEndpoint(Graphics2D g2, AffineTransform at, GeneralPath path) {

    at.transform(pt, pt);
    g2.drawString(cardinality, (float)pt.x, (float)pt.y);

  }

}
