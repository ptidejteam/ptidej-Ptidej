/*
 * $Id: JVariableManager.java,v 1.2 2005/10/14 21:34:08 guehene Exp $
 *
 * Copyright (c) 2001-2002 Jean-Daniel Fekete, Mohammad Ghoniem and
 * Ecole des Mines de Nantes.  All rights reserved.
 *
 * This software is proprietary information of Jean-Daniel Fekete and
 * Ecole des Mines de Nantes.  You shall use it only in accordance
 * with the terms of the license agreement you accepted when
 * downloading this software.  The license is available in the file
 * licence.txt and at the following URL:
 * http://www.emn.fr/fekete/oadymppac/licence.html
 */
package fr.emn.oadymppac.widgets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import fr.emn.oadymppac.Variable;
import fr.emn.oadymppac.VariableManager;
import fr.emn.oadymppac.event.DomainChangedEvent;
import fr.emn.oadymppac.event.DomainChangedListener;

/**
 * A <code>JVariableManager</code> displays the domains maintained by
 * a <code>VariableManager</code> using a grid.
 *
 * @author Jean-Daniel Fekete
 * @author Mohammad Ghoniem
 * @version $Revision: 1.2 $
 */
public class JVariableManager extends JComponent implements JObjectLocator,
		MouseListener, MouseMotionListener, DomainChangedListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3779256969073849752L;
	VariableManager varManager;
	int columns;
	int labelSize = 30;
	Color withdrawnColor = Color.gray;
	Color hiliteColor;
	Variable picked;
	boolean noRepaintOnNotify = true;

	GlassPane glass;

	public JVariableManager(final VariableManager m) {
		m.addDomainChangedListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.varManager = m;
		this.setToolTipText("don't use this one");
	}

	/**
	 * Builds a <code>Shape</code> with the given parameters and returns it.
	 */
	Shape buildShape(final int x, final int y, final int w, final int h) {
		return new Rectangle(x, y, w, h);
	}
	// DomainChangedListener
	public void domainChanged(final DomainChangedEvent e) {
		this.update();
	}

	public int getColumns() {
		if (this.columns == 0) {
			synchronized (this.varManager) {
				final Iterator i = this.varManager.iterator();
				while (i.hasNext()) {
					final Variable v = (Variable) i.next();
					if (this.columns < v.getValues().length) {
						this.columns = v.getValues().length;
					}
				}
			}
		}
		return this.columns;
	}

	/**
	 * Get the value of hiliteColor.
	 * @return value of hiliteColor.
	 */
	public Color getHiliteColor() {
		return this.hiliteColor;
	}

	public Object getObjectAtCoords(final int px, final int py) {
		final int w = this.getWidth() - this.labelSize;
		final int h = this.getHeight();
		Variable v = null;

		synchronized (this.varManager) {
			int ch = 0;
			/**
			 * The rectangle associated to a value will be at least
			 * 2 pixels x 2 pixels big.
			 */
			if (this.varManager.getVariableCount() != 0) {
				ch = h / this.varManager.getVariableCount();
			}
			if (ch < 2) {
				ch = 2;
			}
			int cw = 0;
			if (this.getColumns() != 0) {
				cw = w / this.getColumns();
			}
			if (cw < 2) {
				cw = 2;
			}

			final Iterator i = this.varManager.iterator();
			int x = 0, y = 0;

			//	int varNum = 0;

			while (i.hasNext()) {
				v = (Variable) i.next();
				x += this.labelSize;
				for (int j = 0; j < v.getValueCount(); j++) {
					if (this.buildShape(x, y, cw - 1, ch - 1).contains(px, py)) {
						return v;
					}
					x += cw;
				}
				x = 0;
				y += ch;
				//	varNum++;
			}
		}
		return null;
	}

	/**
	 * This method returns the coordinates of the required variable. Since the
	 * graphic representation of one variable may encompass several regions,
	 * this method returns a vector of points.
	 */
	public Vector getPositions(final Object var, Vector positions) {
		if (positions == null) {
			positions = new Vector();
		}
		final Variable v = (Variable) var;
		final int w = this.getWidth() - this.labelSize;
		final int h = this.getHeight();

		synchronized (this.varManager) {
			int ch = 0;
			/**
			 * The rectangle associated to a value will be at least
			 * 2 pixels x 2 pixels big.
			 */
			if (this.varManager.getVariableCount() != 0) {
				ch = h / this.varManager.getVariableCount();
			}
			if (ch < 2) {
				ch = 2;
			}
			int cw = 0;
			if (this.getColumns() != 0) {
				cw = w / this.getColumns();
			}
			if (cw < 2) {
				cw = 2;
			}

			final Iterator i = this.varManager.iterator();
			//	int x = 0;
			int y = 0;

			//	int varNum = 0;
			while (i.hasNext()) {
				final Variable current = (Variable) i.next();
				if (current == v) {
					final Point p =
						new Point(this.labelSize / 2, y + (ch - 1) / 2);
					positions.addElement(SwingUtilities.convertPoint(
						this,
						p,
						this.glass));
					return positions;
				}
				//	else {
				//		x += cw;
				//	}
				//	x = 0;
				y += ch;
				//	varNum++;
			}
		}
		return positions;
	}

	/**
	 * This method returns the coordinates of the required variable. Since the
	 * graphic representation of one variable may encompass several regions,
	 * this method returns a vector of points.
	 */
	public Vector getPositions(final String varname, Vector positions) {
		if (positions == null) {
			positions = new Vector();
		}
		final Variable v = this.varManager.getVariable(varname);
		final int w = this.getWidth() - this.labelSize;
		final int h = this.getHeight();

		synchronized (this.varManager) {
			int ch = 0;
			/**
			 * The rectangle associated to a value will be at least
			 * 2 pixels x 2 pixels big.
			 */
			if (this.varManager.getVariableCount() != 0) {
				ch = h / this.varManager.getVariableCount();
			}
			if (ch < 2) {
				ch = 2;
			}
			int cw = 0;
			if (this.getColumns() != 0) {
				cw = w / this.getColumns();
			}
			if (cw < 2) {
				cw = 2;
			}

			final Iterator i = this.varManager.iterator();
			//	int x = 0;
			int y = 0;

			//	int varNum = 0;
			while (i.hasNext()) {
				final Variable current = (Variable) i.next();
				if (current == v) {
					final Point p =
						new Point(this.labelSize / 2, y + (ch - 1) / 2);
					positions.addElement(SwingUtilities.convertPoint(
						this,
						p,
						this.glass));
					return positions;
				}
				//	else {
				//		x += cw;
				//	}
				//	x = 0;
				y += ch;
				//	varNum++;
			}
		}

		return positions;
	}

	public Dimension getPreferredSize(final JComponent c) {
		return new Dimension(
			(this.getColumns() + 1) * this.labelSize,
			this.varManager.getVariableCount() * 10);
	}

	/**
	 * This method returns the shapes associated with the required variable.
	 * Since the graphic representation of one variable may encompass several
	 * regions, this method returns a vector of shapes.
	 */
	public Vector getShapes(final Object var, Vector shapes) {
		final Point tp = new Point(0, 0); // translation vector
		SwingUtilities.convertPointToScreen(tp, this);
		final AffineTransform at = new AffineTransform();
		at.setToTranslation(tp.getX(), tp.getY());

		if (shapes == null) {
			shapes = new Vector();
		}
		final Variable v = (Variable) var;
		final int w = this.getWidth() - this.labelSize;
		final int h = this.getHeight();

		synchronized (this.varManager) {
			int ch = 0;
			/**
			 * The rectangle associated to a value will be at least
			 * 2 pixels x 2 pixels big.
			 */
			if (this.varManager.getVariableCount() != 0) {
				ch = h / this.varManager.getVariableCount();
			}
			if (ch < 2) {
				ch = 2;
			}
			int cw = 0;
			if (this.getColumns() != 0) {
				cw = w / this.getColumns();
			}
			if (cw < 2) {
				cw = 2;
			}

			final Iterator i = this.varManager.iterator();
			int x = 0, y = 0;

			//	int varNum = 0;
			boolean varFound = false;
			final GeneralPath gp = new GeneralPath();
			while (i.hasNext() && !varFound) {
				final Variable current = (Variable) i.next();
				for (int j = 0; j < current.getValueCount(); j++) {
					if (current.getId() == v.getId()) {
						final Shape shape =
							this.buildShape(x, y, cw - 1, ch - 1);
						gp.append(shape, true);
						shapes.addElement(shape.getPathIterator(at));
						varFound = true;
					}
					x += cw;
				}
				x = 0;
				y += ch;
				//	varNum++;
			}
		}

		return shapes;
	}

	/**
	 * This method returns the shapes associated with the required variable.
	 * Since the graphic representation of one variable may encompass several
	 * regions, this method returns a vector of shapes.
	 */
	public Vector getShapes(final String varname, Vector shapes) {
		final Point tp = new Point(0, 0); // translation vector
		SwingUtilities.convertPointToScreen(tp, this);
		final AffineTransform at = new AffineTransform();
		at.setToTranslation(tp.getX(), tp.getY());

		if (shapes == null) {
			shapes = new Vector();
		}
		final Variable v = this.varManager.getVariable(varname);
		final int w = this.getWidth() - this.labelSize;
		final int h = this.getHeight();

		synchronized (this.varManager) {
			int ch = 0;
			/**
			 * The rectangle associated to a value will be at least
			 * 2 pixels x 2 pixels big.
			 */
			if (this.varManager.getVariableCount() != 0) {
				ch = h / this.varManager.getVariableCount();
			}
			if (ch < 2) {
				ch = 2;
			}
			int cw = 0;
			if (this.getColumns() != 0) {
				cw = w / this.getColumns();
			}
			if (cw < 2) {
				cw = 2;
			}

			final Iterator i = this.varManager.iterator();
			int x = 0, y = 0;

			//	int varNum = 0;
			boolean varFound = false;
			final GeneralPath gp = new GeneralPath();
			Variable current = new Variable();
			while (i.hasNext() && !varFound) {
				current = (Variable) i.next();
				for (int j = 0; j < current.getValueCount(); j++) {
					if (current.getId() == v.getId()) {
						final Shape shape =
							this.buildShape(x, y, cw - 1, ch - 1);
						gp.append(shape, true);
						shapes.addElement(shape.getPathIterator(at));
						varFound = true;
					}
					x += cw;
				}
				x = 0;
				y += ch;
				//	varNum++;
			}
		}

		return shapes;
	}

	public String getToolTipText(final MouseEvent event) {
		if (this.glass != null) {
			this.glass.repaint();
		}
		//return getToolTipText();
		final int w = this.getWidth() - this.labelSize;
		final int h = this.getHeight();
		final int x = event.getX();
		final int y = event.getY();

		synchronized (this.varManager) {
			int ch = 0;
			if (this.varManager.getVariableCount() != 0) {
				ch = h / this.varManager.getVariableCount();
			}
			if (ch < 2) {
				ch = 2;
			}
			int cw = 0;
			if (this.getColumns() != 0) {
				cw = w / this.getColumns();
			}
			if (cw < 2) {
				cw = 2;
			}

			final int varNum = y / ch;
			if (varNum < 0 || varNum >= this.varManager.getVariableCount()) {
				return null;
			}
			final Variable v = this.varManager.getVariableAt(varNum);
			final int valNum = (x - this.labelSize) / cw;
			if (valNum < 0 || valNum >= v.getValueCount()) {
				return v.getName();
			}
			else {
				return v.getName() + "=" + v.getValueAt(valNum);
			}
		}
	}

	public VariableManager getVariableManager() {
		return this.varManager;
	}

	/**
	 * Get the value of withdrawnColor.
	 * @return value of withdrawnColor.
	 */
	public Color getWithdrawnColor() {
		return this.withdrawnColor;
	}
	public void mouseClicked(final MouseEvent e) {
		/*        picked = (Variable)getObjectAtCoords(e.getX(), e.getY());
		        e = SwingUtilities.convertMouseEvent(this, e, glass);
		        glass.setOrigin(e.getX(), e.getY());
		        if (glass != null){
		            glass.getPositions().clear();
		            glass.setPositions(getVariableManager().getPositions(picked, glass.getPositions()));
		            System.out.println("Number of positions : "+glass.getPositions().size());
		//            glass.repaint();
		        }*/
	}

	public void mouseDragged(MouseEvent e) {
		this.picked = (Variable) this.getObjectAtCoords(e.getX(), e.getY());
		if (this.glass != null) {
			e = SwingUtilities.convertMouseEvent(this, e, this.glass);
			this.glass.setOrigin(e.getX(), e.getY());
			this.glass.getPositions().clear();
			this.glass.setPositions(this.getVariableManager().getPositions(
				this.picked,
				this.glass.getPositions()));
			System.out.println("Number of positions : "
					+ this.glass.getPositions().size());
			this.glass.repaint();
		}
	}

	public void mouseEntered(final MouseEvent e) {
	}

	public void mouseExited(final MouseEvent e) {
		if (this.glass != null) {
			this.glass.getPositions().clear();
		}
	}

	public void mouseMoved(final MouseEvent e) {
	}

	public void mousePressed(final MouseEvent e) {
	}

	public void mouseReleased(final MouseEvent e) {
		this.glass.getPositions().clear();
	}

	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		final int w = this.getWidth() - this.labelSize;
		final int h = this.getHeight();
		final VarColorManager mgr =
			VarColorManager.getVarColorManager(this.varManager.getSolver());

		synchronized (this.varManager) {
			int ch = 0;
			/**
			 * The rectangle associated to a value will be at least
			 * 2 pixels x 2 pixels big.
			 */
			if (this.varManager.getVariableCount() != 0) {
				ch = h / this.varManager.getVariableCount();
			}
			if (ch < 2) {
				ch = 2;
			}
			int cw = 0;
			if (this.getColumns() != 0) {
				cw = w / this.getColumns();
			}
			if (cw < 2) {
				cw = 2;
			}

			final Iterator i = this.varManager.iterator();
			int x = 0, y = 0;
			g.setFont(this.getFont());
			final FontMetrics fm = g.getFontMetrics();
			final Rectangle2D bounds = fm.getStringBounds("9", g);
			/**
			 * When rectangles are bif enough, the corresponding values are
			 * shown.
			 */
			final boolean showValues =
				(int) bounds.getWidth() < cw && (int) bounds.getHeight() < ch;

			//	int varNum = 0;
			while (i.hasNext()) {
				final Variable v = (Variable) i.next();
				final Shape s = g.getClip();
				g.clipRect(x, y, this.labelSize, ch);
				g.setColor(this.getForeground());
				g.drawString(v.getName(), x, y + ch / 2);
				g.setClip(s);
				x += this.labelSize;
				final Color varColor = mgr.getVarColor(v);
				/**
				 * Drawing of the grid.
				 */
				for (int j = 0; j < v.getValueCount(); j++) {
					/**
					 * If a value is withdrawn, it is grayed out. Otherwise, its
					 * rectangle is filled with the color associated to the
					 * variable.
					 */
					if (v.isWithdrawnValueAt(j)) {
						g.setColor(Color.gray);
						g.fillRect(x, y, cw - 1, ch - 1);
					}
					else {
						g.setColor(varColor);
						g.fillRect(x, y, cw - 1, ch - 1);
						/**
						             * When a solution or failure occurs, the rectangle is
						             * highlited with green or red respectively.
						             */
						if (this.hiliteColor != null) {
							g.setColor(this.hiliteColor);
							if (cw > 2 && ch > 2) {
								g.fillRect(
									x + cw / 4,
									y + ch / 4,
									cw / 2,
									ch / 2);
							}
							else {
								g.fillRect(x, y, cw - 1, ch - 1);
							}
						}
					}
					if (showValues) {
						g.clipRect(x, y, cw - 1, ch - 1);
						g.setColor(Color.black);
						g.drawString("" + v.getValueAt(j), x + 1, y + ch / 2);
						g.setClip(s);
					}
					x += cw;
				}
				x = 0;
				y += ch;
				//	varNum++;
			}
		}
	}
	public void setGlass(final GlassPane pane) {
		this.glass = pane;
	}
	/**
	 * Set the value of hiliteColor.
	 * @param v  Value to assign to hiliteColor.
	 */
	public void setHiliteColor(final Color v) {
		this.hiliteColor = v;
	}
	public void setNoRepaintOnNotify(final boolean b) {
		this.noRepaintOnNotify = b;
	}
	public void setVariableManager(final VariableManager m) {
		this.varManager = m;
	}
	/**
	 * Set the value of withdrawnColor.
	 * @param v  Value to assign to withdrawnColor.
	 */
	public void setWithdrawnColor(final Color v) {
		this.withdrawnColor = v;
	}
	public void update() {
		if (!this.noRepaintOnNotify) {
			this.repaint();
		}
	}

}