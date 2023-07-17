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
package ptidej.viewer.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;



/**
 * VFlow layout is used to layout components in a panel. It will arrange
 * components left to right until no more buttons fit in the same row.
 * The constructor has full control over alignment and packing within the row.
 */
public abstract class TFlowLayout
	implements LayoutManager,
			   java.io.Serializable {
	/** Pack everything together at the left of the row or line up the left of a column. */
	public static final int LEFT = 0;

	/** Pack everything together in the middle of the row or line up the centre of a column. */
	public static final int HCENTER = 1;

	/** Pack everything together at the right of the row or line up the right of a column. */
	public static final int RIGHT = 2;

	/** Spread to even out the space between the components in the row or column. */
	public static final int SPREAD = 3;

	/** Pack everything together at the top of the column or line up the top of a row. */
	public static final int TOP = 0;

	/** Pack everything together in the centre of the column or line up the centre of a row. */
	public static final int VCENTER = 4;

	/** Pack everything together at the bottom of the column or line up the bottom of a row. */
	public static final int BOTTOM = 8;

	/** Expand everything to the full height of the row or full width of the column. */
	public static final int EXPAND = 12;
	int align;
	int hgap;
	int vgap;

	/*
	* JDK 1.1 serialVersionUID 
	*/
	private static final long serialVersionUID = -7262534975593282951L;

	/**
	 * Constructs a new TFlow Layout with the specified alignment and gap
	 * values.
	 * @param align the alignment value
	 * @param hgap the horizontal gap variable
	 * @param vgap the vertical gap variable
	 */
	public TFlowLayout(int align, int hgap, int vgap) {
		this.align = align;
		this.hgap  = hgap;
		this.vgap  = vgap;
	}

	/**
	 * Returns the alignment value for this layout, one of LEFT, HCENTER or RIGHT,
	 * possibly ORed with one of TOP, VCENTER, or BOTTOM.
	 */
	public int getAlignment() {
		return this.align;
	}

	/**
	 * Returns the horizontal gap between components.
	 */
	public int getHgap() {
		return this.hgap;
	}

	/**
	 * Returns the vertical gap between components.
	 */
	public int getVgap() {
		return this.vgap;
	}

	/**
	 * Adds the specified component to the layout. Not used by this class.
	 * @param name the name of the component
	 * @param comp the the component to be added
	 */
	public void addLayoutComponent(String name, Component comp) {
	}

	/**
	 * Lays out the container. This method will actually reshape the
	 * components in the target in order to satisfy the constraints of
	 * the TFlowLayout object. 
	 * @param target the specified component being laid out.
	 * @see IContainer
	 */
	public abstract void layoutContainer(Container target);

	/**
	 * Returns the minimum dimensions needed to layout the components
	 * contained in the specified target container.
	 * @param target the component which needs to be laid out 
	 * @see #preferredLayoutSize
	 */
	public abstract Dimension minimumLayoutSize(Container target);

	/**
	 * Returns the preferred dimensions for this layout given the components
	 * in the specified target container.
	 * @param target the component which needs to be laid out
	 * @see IContainer
	 * @see #minimumLayoutSize
	 */
	public abstract Dimension preferredLayoutSize(Container target);

	/**
	 * Sets the alignment value for this layout.
	 * @param align the alignment value, one of LEFT, HCENTER or RIGHT,
	 * possibly ORed with one of TOP, VCENTER, or BOTTOM.
	 */
	public void setAlignment(int align) {
		this.align = align;
	}

	/**
	 * Sets the horizontal gap between components.
	 * @param hgap the horizontal gap between components
	 */
	public void setHgap(int hgap) {
		this.hgap = hgap;
	}

	/**
	 * Sets the vertical gap between components.
	 * @param vgap the vertical gap between components
	 */
	public void setVgap(int vgap) {
		this.vgap = vgap;
	}

	/**
	 * Removes the specified component from the layout. Not used by
	 * this class.  
	 * @param comp the component to remove
	 */
	public void removeLayoutComponent(Component comp) {
	}

	/**
	 * Returns the String representation of this TFlowLayout's values.
	 */
	public String toString() {
		String str = "";
		switch (this.align & SPREAD) {
			case TOP:
				str = ",align=top";
				break;
			case VCENTER:
				str = ",align=center";
				break;
			case BOTTOM:
				str = ",align=bottom";
				break;
			case SPREAD:
				str = ",align=spread";
				break;
		}

		switch (this.align & EXPAND) {
			case LEFT:
				str += " left";
				break;
			case HCENTER:
				str += " center";
				break;
			case RIGHT:
				str += " right";
				break;
			case EXPAND:
				str += " expand";
				break;
		}

		return getClass()
				   .getName() + "[hgap=" + this.hgap + ",vgap=" + this.vgap
			   + str + "]";
	}
}
