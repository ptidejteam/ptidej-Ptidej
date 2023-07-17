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

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class BevelRoundedEdgeBorder extends RoundedEdgeBorder {

  /** Raised border. */
  public static final int RAISED  = 1;
  /** Lowered border. */
  public static final int LOWERED = 2;

  protected Color highlightInnerColor;
  protected Color highlightOuterColor;
  protected Color shadowInnerColor;
  protected Color shadowOuterColor;
  // DELETE ME
//  protected Color highlightColor;
//  protected Color shadowColor;
//  protected Color topColor;
//  protected Color bottomColor;

  // END DELETE ME

  protected Color topInnerColor;
  protected Color topOuterColor;
  protected Color bottomInnerColor;
  protected Color bottomOuterColor;

  protected int bevelType;
  protected int defaultInset = 2;

  /**
   * Creates a bevel border with the specified type and whose
   * colors will be derived from the background color of the
   * component passed into the paintBorder method.
   * @param bevelType the type of bevel for the border
   */
  public BevelRoundedEdgeBorder(int bevelType) {
    this.setBevelType(bevelType);
    super.defaultInset = this.defaultInset;
  }

  public BevelRoundedEdgeBorder(int bevelType, int w, int h) {
    super(w, h);
    this.setBevelType(bevelType);
    super.defaultInset = this.defaultInset;
  }

  public BevelRoundedEdgeBorder(int bevelType, Color borderColor) {
    super(borderColor);
    this.setBevelType(bevelType);
    this.setColors();
    super.defaultInset = this.defaultInset;
  }

  public BevelRoundedEdgeBorder(int bevelType, int edgesConstraints) {
    super(edgesConstraints);
    this.setBevelType(bevelType);
    super.defaultInset = this.defaultInset;
  }

  public BevelRoundedEdgeBorder(int bevelType, int w, int h, Color borderColor) {
    super(w, h, borderColor);
    this.setBevelType(bevelType);
    this.setColors();
    super.defaultInset = this.defaultInset;
  }

  public BevelRoundedEdgeBorder(int bevelType, int w, int h, int edgesConstraints) {
    super(w, h, edgesConstraints);
    this.setBevelType(bevelType);
    super.defaultInset = this.defaultInset;
  }

  public BevelRoundedEdgeBorder(int bevelType, Color borderColor, int edgesConstraints) {
    super(borderColor, edgesConstraints);
    this.setBevelType(bevelType);
    this.setColors();
    super.defaultInset = this.defaultInset;
  }

  public BevelRoundedEdgeBorder(int bevelType, int w, int h, Color borderColor, int edgesConstraints) {
    super(w, h, borderColor, edgesConstraints);
    this.setBevelType(bevelType);
    this.setColors();
    super.defaultInset = this.defaultInset;
  }

  public void setColors() {
    this.highlightInnerColor = this.getBorderColor().brighter();
    this.highlightOuterColor = this.getBorderColor().brighter().brighter();
    this.shadowInnerColor    = this.getBorderColor().darker();
    this.shadowOuterColor    = this.getBorderColor().darker().darker();
  }


  public void setColors(Color borderColor) {
    this.setBorderColor(borderColor);
    this.highlightInnerColor = borderColor.brighter();
    this.highlightOuterColor = borderColor.brighter().brighter();
    this.shadowInnerColor    = borderColor.darker();
    this.shadowOuterColor    = borderColor.darker().darker();
  }

  public void setEffectiveColors(Color componentBackground) {
    if(this.getBorderColor() == null) {
      this.setColors(componentBackground);
    }
    if(this.isRaised()) {
      this.topInnerColor = this.highlightInnerColor;
      this.topOuterColor = this.highlightOuterColor;
      this.bottomInnerColor = this.shadowInnerColor;
      this.bottomOuterColor = this.shadowOuterColor;
    }
    else if (this.isLowered()) {
      this.topInnerColor = this.shadowInnerColor;
      this.topOuterColor = this.shadowOuterColor;
      this.bottomInnerColor = this.highlightInnerColor;
      this.bottomOuterColor = this.highlightOuterColor;
    }
  }

  public int getBevelType(){
    return this.bevelType;
  }

  public void setBevelType(int bevelType){
    this.bevelType = bevelType;
    if (!isLowered() && !isRaised()) {
      throw new IllegalArgumentException(
          "Bevel type must be either RAISED or LOWERED.\n" +
          "    Bad supplied value : " + this.getBevelType() +
          "\n");
    }
  }

  public boolean isRaised(){
    return this.getBevelType() == BevelRoundedEdgeBorder.RAISED;
  }

  public boolean isLowered(){
    return this.getBevelType() == BevelRoundedEdgeBorder.LOWERED;
  }

  public void paintBorder(
      Component c,
      Graphics g,
      int x, int y, int w, int h) {

    this.setEffectiveColors(c.getBackground());

    w-=1;
    h-=1;

    switch(this.edgesConstraints) {
      case TOP_LEFT_EDGE: // TOP_LEFT_EDGE
        g.setColor(this.topOuterColor);
        this.paintRoundedEdge(c, g, x, y, w, h, TOP_LEFT_EDGE);
        g.drawLine(x, y + h, x, y + this.h); // left
        g.drawLine(x + this.w, y, x + w, y); // top
        g.setColor(this.bottomOuterColor);
        g.drawLine(x + w, y, x + w, y + h); // right
        g.drawLine(x, y + h, x + w, y + h); // bottom
        break;
      case TOP_RIGHT_EDGE: // TOP_RIGHT_EDGE
        g.setColor(this.topOuterColor);
        this.paintRoundedEdge(c, g, x, y, w, h, TOP_RIGHT_EDGE);
        g.drawLine(x, y + h, x, y); // left
        g.drawLine(x, y, x + w - this.w, y); // top
        g.setColor(this.bottomOuterColor);
        g.drawLine(x + w, y + this.h, x + w, y + h); // right
        g.drawLine(x, y + h, x + w, y + h); // bottom
        break;
      case TOP_EDGES: // TOP_EDGES
        g.setColor(this.topOuterColor);
        this.paintRoundedEdge(c, g, x, y, w, h, TOP_LEFT_EDGE);
        this.paintRoundedEdge(c, g, x, y, w, h, TOP_RIGHT_EDGE);
        g.drawLine(x + this.w, y, x + w - this.w, y); // top
        g.drawLine(x, y + h, x, y + this.h); // left

        g.setColor(this.topInnerColor);
        this.h-=1; this.w-=1;
        this.paintRoundedEdge(c, g, x + 1, y + 1, w, h, TOP_LEFT_EDGE);
        this.paintRoundedEdge(c, g, x - 1, y + 1, w, h, TOP_RIGHT_EDGE);
        this.h+=1; this.w+=1;
        g.drawLine(x + this.w + 1, y + 1, x + w - this.w - 1, y + 1); // top
        g.drawLine(x + 1, y + h + 1, x + 1, y + this.h - 1); // left

        g.setColor(this.bottomInnerColor);
        g.drawLine(x + w - 1, y + this.h + 1, x + w - 1, y + h -1); // right
        g.drawLine(x + 1, y + h - 1, x + w + 1, y + h - 1); // bottom
        g.setColor(this.bottomOuterColor);
        g.drawLine(x + w, y + this.h, x + w, y + h); // right
        g.drawLine(x, y + h, x + w, y + h); // bottom

        break;
      case BOTTOM_LEFT_EDGE: // BOTTOM_LEFT_EDGE
        g.setColor(this.topOuterColor);
        g.drawLine(x, y + h - this.h, x, y); // left
        g.drawLine(x, y, x + w, y); // top
        g.setColor(this.bottomOuterColor);
        g.drawLine(x + w, y, x + w, y + h); // right
        g.drawLine(x + this.w, y + h, x + w, y + h); // bottom
        this.paintRoundedEdge(c, g, x, y, w, h, BOTTOM_LEFT_EDGE);
        break;
      case BOTTOM_RIGHT_EDGE: // BOTTOM_RIGHT_EDGE
        g.setColor(this.topOuterColor);
        g.drawLine(x, y + h, x, y); // left
        g.drawLine(x, y, x + w, y); // top
        g.setColor(this.bottomOuterColor);
        g.drawLine(x + w, y, x + w, y + h - this.h); // right
        g.drawLine(x, y + h, x + w - this.w, y + h); // bottom
        this.paintRoundedEdge(c, g, x, y, w, h, BOTTOM_RIGHT_EDGE);
        break;
      case BOTTOM_EDGES: // BOTTOM_EDGES
        g.setColor(this.topInnerColor);
        g.drawLine(x + 1, y + h - this.h + 1, x + 1, y - 1); // left
        g.drawLine(x + 1, y + 1, x + w - 1, y + 1); // top
        g.setColor(this.topOuterColor);
        g.drawLine(x, y + h - this.h, x, y); // left
        g.drawLine(x, y, x + w, y); // top
        g.setColor(this.bottomInnerColor);
        g.drawLine(x + w - 1, y + 1, x + w - 1, y + h - this.h - 1); // right
        g.drawLine(x + this.w + 1, y + h - 1, x + w - this.w - 1, y + h - 1); // bottom
        this.h-=1; this.w-=1;
        this.paintRoundedEdge(c, g, x - 1, y - 1, w, h, BOTTOM_RIGHT_EDGE);
        this.paintRoundedEdge(c, g, x + 1, y - 1, w, h, BOTTOM_LEFT_EDGE);
        this.h+=1; this.w+=1;
        g.setColor(this.bottomOuterColor);
        g.drawLine(x + w, y, x + w, y + h - this.h); // right
        g.drawLine(x + this.w, y + h, x + w - this.w, y + h); // bottom
        this.paintRoundedEdge(c, g, x, y, w, h, BOTTOM_RIGHT_EDGE);
        this.paintRoundedEdge(c, g, x, y, w, h, BOTTOM_LEFT_EDGE);
        break;
      case LEFT_EDGES: // LEFT_EDGES
        g.setColor(this.topOuterColor);
        this.paintRoundedEdge(c, g, x, y, w, h, TOP_LEFT_EDGE);
        g.drawLine(x, y + h - this.h, x, y + this.h); // left
        g.drawLine(x + this.w, y, x + w, y); // top
        g.setColor(this.bottomOuterColor);
        g.drawLine(x + w, y, x + w, y + h); // right
        g.drawLine(x + this.w, y + h, x + w, y + h); // bottom
        this.paintRoundedEdge(c, g, x, y, w, h, BOTTOM_LEFT_EDGE);
        break;
      case RIGHT_EDGES: // RIGHT_EDGES
        g.setColor(this.topOuterColor);
        this.paintRoundedEdge(c, g, x, y, w, h, TOP_RIGHT_EDGE);
        g.drawLine(x, y + h, x, y); // left
        g.drawLine(x, y, x + w - this.w, y); // top
        g.setColor(this.bottomOuterColor);
        g.drawLine(x + w, y + this.h, x + w, y + h - this.h); // right
        g.drawLine(x, y + h, x + w - this.w, y + h); // bottom
        this.paintRoundedEdge(c, g, x, y, w, h, BOTTOM_RIGHT_EDGE);
        break;
      default: // ALL_EDGES
        g.setColor(this.topInnerColor);
        g.drawLine(x + 1, y + h - this.h + 1, x + 1, y + this.h - 1); // left
        g.drawLine(x + this.w + 1, y + 1, x + w - this.w - 1, y + 1); // top
        this.h-=1; this.w-=1;
        this.paintRoundedEdge(c, g, x + 1, y + 1, w, h, TOP_LEFT_EDGE);
        this.paintRoundedEdge(c, g, x - 1, y + 1, w, h, TOP_RIGHT_EDGE);
        this.h+=1; this.w+=1;
        g.setColor(this.topOuterColor);
        g.drawLine(x, y + h - this.h, x, y + this.h); // left
        g.drawLine(x + this.w, y, x + w - this.w, y); // top
        this.paintRoundedEdge(c, g, x, y, w, h, TOP_LEFT_EDGE);
        this.paintRoundedEdge(c, g, x, y, w, h, TOP_RIGHT_EDGE);


        g.setColor(this.bottomInnerColor);
        g.drawLine(x + w - 1, y + this.h + 1, x + w - 1, y + h - this.h); // right
        g.drawLine(x + this.w, y + h - 1, x + w - this.w, y + h - 1); // bottom
        this.h-=1; this.w-=1;
        this.paintRoundedEdge(c, g, x - 1, y - 1, w, h, BOTTOM_RIGHT_EDGE);
        this.paintRoundedEdge(c, g, x + 1, y - 1, w, h, BOTTOM_LEFT_EDGE);
        this.h+=1; this.w+=1;
        g.setColor(this.bottomOuterColor);
        g.drawLine(x + w, y + this.h, x + w, y + h - this.h); // right
        g.drawLine(x + this.w, y + h, x + w - this.w, y + h); // bottom
        this.paintRoundedEdge(c, g, x, y, w, h, BOTTOM_RIGHT_EDGE);
        this.paintRoundedEdge(c, g, x, y, w, h, BOTTOM_LEFT_EDGE);
    }
  }

  public void paintRoundedEdge(
      Component c,
      Graphics g,
      int x, int y, int w, int h,
      int edgeID) {

    switch(edgeID) {
      case TOP_LEFT_EDGE:
        g.drawArc(x, y, 2 * this.w, 2 * this.h, 180, -90);
        break;
      case TOP_RIGHT_EDGE:
        g.drawArc(x + w - 2 * this.w, y, 2 * this.w, 2 * this.h, 90, -90);
        break;
      case BOTTOM_LEFT_EDGE:
        g.drawArc(x, y + h - 2 * this.h, 2 * this.w, 2 * this.h, -90, -90);
        break;
      case BOTTOM_RIGHT_EDGE:
        g.drawArc(x + w - 2 * this.w, y + h - 2 * this.h, 2 * this.w,
                  2 * this.h, 0, -90);
    }

  }



}
