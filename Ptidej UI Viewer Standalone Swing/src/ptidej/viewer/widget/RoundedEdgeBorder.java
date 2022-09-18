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
package ptidej.viewer.widget;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.Border;

public class RoundedEdgeBorder implements Border {

  public static final int ALL_EDGES         = 0;
  public static final int TOP_LEFT_EDGE     = 1;
  public static final int TOP_RIGHT_EDGE    = 2;
  public static final int TOP_EDGES         = 3;
  public static final int BOTTOM_LEFT_EDGE  = 4;
  public static final int BOTTOM_RIGHT_EDGE = 5;
  public static final int BOTTOM_EDGES      = 6;
  public static final int LEFT_EDGES        = 7;
  public static final int RIGHT_EDGES       = 8;

  protected int w = 6;
  protected int h = 6;
//  protected int w = 100;
//  protected int h = 100;

  protected int defaultInset = 1;
  protected Color borderColor/* = Color.BLACK*/;
  protected int edgesConstraints = ALL_EDGES;


  public RoundedEdgeBorder() {
  }

  public RoundedEdgeBorder(int w, int h) {
    this.w = w;
    this.h = h;
  }

  public RoundedEdgeBorder(Color borderColor) {
    this.borderColor = borderColor;
  }

  public RoundedEdgeBorder(int edgesConstraints) {
    this.edgesConstraints = edgesConstraints;
  }

  public RoundedEdgeBorder(int w, int h, Color borderColor) {
    this.w = w;
    this.h = h;
    this.borderColor = borderColor;
  }

  public RoundedEdgeBorder(int w, int h, int edgesConstraints) {
    this.w = w;
    this.h = h;
    this.edgesConstraints = edgesConstraints;
  }

  public RoundedEdgeBorder(Color borderColor, int edgesConstraints) {
    this.borderColor = borderColor;
    this.edgesConstraints = edgesConstraints;
  }

  public RoundedEdgeBorder(int w, int h, Color borderColor, int edgesConstraints) {
    this.w = w;
    this.h = h;
    this.borderColor = borderColor;
    this.edgesConstraints = edgesConstraints;
  }

  public int getArcW() {
    return this.w;
  }

  public void setArcW(int w) {
    this.w = w;
  }

  public int getArcH() {
    return this.h;
  }

  public void setArcH(int h) {
    this.h = h;
  }

  public int getDefaultInset() {
    return this.defaultInset;
  }

  public void setDefaultInset(int defaultInset) {
    this.defaultInset = defaultInset;
  }

  public Color getBorderColor() {
    return this.borderColor;
  }

  public void setBorderColor(Color borderColor) {
    this.borderColor = borderColor;
  }

  public Insets getBorderInsets(Component c) {
    Insets insets = null;
    switch(this.edgesConstraints) {
      case TOP_LEFT_EDGE: // TOP_LEFT_EDGE
        insets = new Insets(this.defaultInset, this.w, this.defaultInset, this.defaultInset);
        break;
      case TOP_RIGHT_EDGE: // TOP_RIGHT_EDGE
        insets = new Insets(this.defaultInset, this.defaultInset, this.defaultInset, this.w);
        break;
      case TOP_EDGES: // TOP_EDGES
        insets = new Insets(this.defaultInset, this.w, this.defaultInset, this.w);
        break;
      case BOTTOM_LEFT_EDGE: // BOTTOM_LEFT_EDGE
        insets = new Insets(this.defaultInset, this.w, this.defaultInset, this.defaultInset);
        break;
      case BOTTOM_RIGHT_EDGE: // BOTTOM_RIGHT_EDGE
        insets = new Insets(this.defaultInset, this.defaultInset, this.defaultInset, this.w);
        break;
      case BOTTOM_EDGES: // BOTTOM_EDGES
        insets = new Insets(this.defaultInset, this.w, this.defaultInset, this.w);
        break;
      case LEFT_EDGES: // LEFT_EDGES
        insets = new Insets(this.defaultInset, this.w, this.defaultInset, this.defaultInset);
        break;
      case RIGHT_EDGES: // RIGHT_EDGES
        insets = new Insets(this.defaultInset, this.defaultInset, this.defaultInset, this.w);
        break;
      default: // ALL_EDGES
        insets = new Insets(this.defaultInset, this.w, this.defaultInset, this.w);
    }
    return insets;/*new Insets(this.h, this.w, this.h, this.w);*/
  }

  public boolean isBorderOpaque() { return true; }

  public void paintBorder(
      Component c,
      Graphics g,
      int x, int y, int w, int h) {
    if(this.getBorderColor() == null) this.setBorderColor(c.getBackground().darker().darker());
    g.setColor(this.getBorderColor());

    w-=1;
    h-=1;

    switch(this.edgesConstraints) {
      case TOP_LEFT_EDGE: // TOP_LEFT_EDGE
        g.drawArc(x, y, 2 * this.w, 2 * this.h, 180, -90); // top left arc
        g.drawLine(x, y + h, x, y + this.h); // left
        g.drawLine(x + this.w, y, x + w, y); // top
        g.drawLine(x + w, y, x + w, y + h); // right
        g.drawLine(x, y + h, x + w, y + h); // bottom
        break;
      case TOP_RIGHT_EDGE: // TOP_RIGHT_EDGE
        g.drawArc(x + w - 2 * this.w, y, 2 * this.w, 2 * this.h, 90, -90); // top right arc
        g.drawLine(x, y + h, x, y); // left
        g.drawLine(x, y, x + w - this.w, y); // top
        g.drawLine(x + w, y + this.h, x + w, y + h); // right
        g.drawLine(x, y + h, x + w, y + h); // bottom
        break;
      case TOP_EDGES: // TOP_EDGES
        g.drawArc(x, y, 2 * this.w, 2 * this.h, 180, -90); // top left arc
        g.drawArc(x + w - 2 * this.w, y, 2 * this.w, 2 * this.h, 90, -90); // top right arc
        g.drawLine(x, y + h, x, y + this.h); // left
        g.drawLine(x + this.w, y, x + w - this.w, y); // top
        g.drawLine(x + w, y + this.h, x + w, y + h); // right
        g.drawLine(x, y + h, x + w, y + h); // bottom

        break;
      case BOTTOM_LEFT_EDGE: // BOTTOM_LEFT_EDGE
        g.drawArc(x, y + h - 2 * this.h, 2 * this.w, 2 * this.h, -90, -90); // bottom left arc
        g.drawLine(x, y + h - this.h, x, y); // left
        g.drawLine(x, y, x + w, y); // top
        g.drawLine(x + w, y, x + w, y + h); // right
        g.drawLine(x + this.w, y + h, x + w, y + h); // bottom
        break;
      case BOTTOM_RIGHT_EDGE: // BOTTOM_RIGHT_EDGE
        g.drawArc(x + w - 2 * this.w, y + h - 2 * this.h, 2 * this.w,
                  2 * this.h, 0, -90); // bottom right arc
        g.drawLine(x, y + h, x, y); // left
        g.drawLine(x, y, x + w, y); // top
        g.drawLine(x + w, y, x + w, y + h - this.h); // right
        g.drawLine(x, y + h, x + w - this.w, y + h); // bottom
        break;
      case BOTTOM_EDGES: // BOTTOM_EDGES
        g.drawArc(x + w - 2 * this.w, y + h - 2 * this.h, 2 * this.w,
                  2 * this.h, 0, -90); // bottom right arc
        g.drawArc(x, y + h - 2 * this.h, 2 * this.w, 2 * this.h, -90, -90); // bottom left arc
        g.drawLine(x, y + h - this.h, x, y); // left
        g.drawLine(x, y, x + w, y); // top
        g.drawLine(x + w, y, x + w, y + h - this.h); // right
        g.drawLine(x + this.w, y + h, x + w - this.w, y + h); // bottom
        break;
      case LEFT_EDGES: // LEFT_EDGES
        g.drawArc(x, y, 2 * this.w, 2 * this.h, 180, -90); // top left arc
        g.drawArc(x, y + h - 2 * this.h, 2 * this.w, 2 * this.h, -90, -90); // bottom left arc
        g.drawLine(x, y + h - this.h, x, y + this.h); // left
        g.drawLine(x + this.w, y, x + w, y); // top
        g.drawLine(x + w, y, x + w, y + h); // right
        g.drawLine(x + this.w, y + h, x + w, y + h); // bottom
        break;
      case RIGHT_EDGES: // RIGHT_EDGES
        g.drawArc(x + w - 2 * this.w, y, 2 * this.w, 2 * this.h, 90, -90); // top right arc
        g.drawArc(x + w - 2 * this.w, y + h - 2 * this.h, 2 * this.w,
                  2 * this.h, 0, -90); // bottom right arc
        g.drawLine(x, y + h, x, y); // left
        g.drawLine(x, y, x + w - this.w, y); // top
        g.drawLine(x + w, y + this.h, x + w, y + h - this.h); // right
        g.drawLine(x, y + h, x + w - this.w, y + h); // bottom
        break;
      default: // ALL_EDGES
        g.drawLine(x, y + h - this.h, x, y + this.h); // left
        g.drawLine(x + this.w, y, x + w - this.w, y); // top
        g.drawLine(x + w, y + this.h, x + w, y + h - this.h); // right
        g.drawLine(x + this.w, y + h, x + w - this.w, y + h); // bottom

        g.drawArc(x, y, 2 * this.w, 2 * this.h, 180, -90); // top left arc
        g.drawArc(x + w - 2 * this.w, y, 2 * this.w, 2 * this.h, 90, -90); // top right arc
        g.drawArc(x + w - 2 * this.w, y + h - 2 * this.h, 2 * this.w,
                  2 * this.h, 0, -90); // bottom right arc
        g.drawArc(x, y + h - 2 * this.h, 2 * this.w, 2 * this.h, -90, -90); // bottom left arc
    }
  }

//  public static void main(String[] args) {
//    JFrame frame = new JFrame("Custom Border: OvalBorder");
//    JLabel label = new JLabel("OvalBorder");
//    JButton cmd  = new JButton("Test Button");
//    cmd.setBorder(new OvalBorder(5,5));
//    ((JPanel) frame.getContentPane()).setBorder(new CompoundBorder(new EmptyBorder(5,5,5,5), new OvalBorder(5,5)));
//    frame.getContentPane().add(label);
//    frame.getContentPane().add(cmd);
//    frame.setBounds(0,0,300,150);
//    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// 1.3
//    frame.setVisible(true);
//  }
}
