/*
 * $Id: JLabelingManager.java,v 1.2 2005/10/14 21:34:08 guehene Exp $
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
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import fr.emn.oadymppac.LabelingManager;
import fr.emn.oadymppac.Variable;
import fr.emn.oadymppac.event.DomainChangedEvent;
import fr.emn.oadymppac.event.DomainChangedListener;

public class JLabelingManager extends JComponent implements JObjectLocator,
		DomainChangedListener, MouseListener, MouseMotionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3968952674148247967L;
	LabelingManager labelingManager;
	int labelSize = 30;
	Color rejectColor = Color.red;
	Color solutionColor = Color.green;
	Variable picked;

	GlassPane glass;

	static final Color varColors[] = { Color.blue, Color.cyan, Color.magenta,
			Color.yellow, Color.pink, Color.orange };

	public JLabelingManager(final LabelingManager m) {
		m.getVariableManager().addDomainChangedListener(this);
		this.setLabelingManager(m);
		this.setToolTipText("don't use this one");
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	Shape buildShape(final int x, final int y, final int w, final int h) {
		return new Rectangle(x, y, w, h);
	}

	public void domainChanged(final DomainChangedEvent e) {
	}

	/**
	 * Get the value of labelingManager.
	 * @return value of labelingManager.
	 */
	public LabelingManager getLabelingManager() {
		return this.labelingManager;
	}

	public Object getObjectAtCoords(final int px, final int py) {
		final float w = this.getWidth() - this.labelSize;
		final int h = this.getHeight();
		Variable v = null;

		synchronized (this.labelingManager) {
			final int totalVarCnt =
				this.labelingManager.getVariableManager().getVariableCount();
			int ch = 0;
			if (totalVarCnt != 0) {
				ch = h / totalVarCnt;
			}
			else {
				return null;
			}
			if (ch < 2) {
				ch = 2;
			}

			int y = 0;
			double valuesNum = 1;
			double prevX = 0;
			double prevW = w;

			for (int varNum = 0; varNum < this.labelingManager
				.labeledVariableCount(); varNum++) {
				v = this.labelingManager.getVariableAt(varNum);
				final int curOn = v.getMin();

				valuesNum *= v.getValueCount(); // increases exponentially
				prevW = w / valuesNum;
				prevX = prevX + curOn * prevW;

				final int x = (int) prevX;
				final int dx = Math.max(1, (int) prevW);

				if (this
					.buildShape(x + this.labelSize, y, dx, ch - 1)
					.contains(px, py)) {
					return v;
				}

				y += ch;
			}
		}
		return null;
	}

	/**
	 * This method returns a vector of <code>Point</code> objects corresponding
	 * to the variable, constraint or object of interest passed as parameter.
	 */
	public Vector getPositions(final Object o, final Vector positions) {
		final float w = this.getWidth() - this.labelSize;
		final int h = this.getHeight();

		synchronized (this.labelingManager) {
			final int totalVarCnt =
				this.labelingManager.getVariableManager().getVariableCount();
			int ch = 0;
			if (totalVarCnt != 0) {
				ch = h / totalVarCnt;
			}
			else {
				return positions;
			}
			if (ch < 2) {
				ch = 2;
			}

			int y = 0;
			double valuesNum = 1;
			double prevX = 0;
			double prevW = w;

			new GeneralPath();

			for (int varNum = 0; varNum < this.labelingManager
				.labeledVariableCount(); varNum++) {
				final Variable v = this.labelingManager.getVariableAt(varNum);
				final int curOn = v.getMin();

				valuesNum *= v.getValueCount(); // increases exponentially
				prevW = w / valuesNum;
				prevX = prevX + curOn * prevW;

				final int x = (int) prevX;
				final int dx = Math.max(1, (int) prevW);
				if (v == (Variable) o) {
					Point p =
						new Point(x + this.labelSize + dx / 2, y + (ch - 1) / 2);
					p = SwingUtilities.convertPoint(this, p, this.glass);
					positions.addElement(p);
					return positions;
				}
				y += ch;
			}
		}
		return positions;
	}

	/**
	 * This method returns a vector of <code>Point</code> objects corresponding
	 * to the variable, constraint or object of interest. In this method, the
	 * objects name or id is passed as parameter.
	 */
	public Vector getPositions(final String name, final Vector positions) {
		final float w = this.getWidth() - this.labelSize;
		final int h = this.getHeight();
		final Variable target =
			this.labelingManager.getVariableManager().getVariable(name);

		synchronized (this.labelingManager) {
			final int totalVarCnt =
				this.labelingManager.getVariableManager().getVariableCount();
			int ch = 0;
			if (totalVarCnt != 0) {
				ch = h / totalVarCnt;
			}
			else {
				return positions;
			}
			if (ch < 2) {
				ch = 2;
			}

			int y = 0;
			double valuesNum = 1;
			double prevX = 0;
			double prevW = w;

			final boolean varFound = false;
			new GeneralPath();

			for (int varNum = 0; varNum < this.labelingManager
				.labeledVariableCount() && !varFound; varNum++) {
				final Variable v = this.labelingManager.getVariableAt(varNum);
				final int curOn = v.getMin();

				valuesNum *= v.getValueCount(); // increases exponentially
				prevW = w / valuesNum;
				prevX = prevX + curOn * prevW;

				final int x = (int) prevX;
				final int dx = Math.max(1, (int) prevW);
				if (v == target) {
					Point p =
						new Point(x + this.labelSize + dx / 2, y + (ch - 1) / 2);
					p = SwingUtilities.convertPoint(this, p, this.glass);
					positions.addElement(p);
					return positions;
				}
				y += ch;
			}
		}
		return positions;
	}

	public Dimension getPreferredSize(final JComponent c) {
		return new Dimension(100, 100);
	}

	/**
	 * Get the value of rejectColor.
	 * @return value of rejectColor.
	 */
	public Color getRejectColor() {
		return this.rejectColor;
	}

	/**
	 * This method returns a vector of <code>Shape</code> objects corresponding
	 * to the variable, constraint or object of interest passed as parameter.
	 */
	public Vector getShapes(final Object o, final Vector shapes) {
		final Point tp = new Point(0, 0); // translation vector
		SwingUtilities.convertPointToScreen(tp, this);
		final AffineTransform at = new AffineTransform();
		at.setToTranslation(tp.getX(), tp.getY());

		final float w = this.getWidth() - this.labelSize;
		final int h = this.getHeight();

		synchronized (this.labelingManager) {
			final int totalVarCnt =
				this.labelingManager.getVariableManager().getVariableCount();
			int ch = 0;
			if (totalVarCnt != 0) {
				ch = h / totalVarCnt;
			}
			else {
				return shapes;
			}
			if (ch < 2) {
				ch = 2;
			}

			int y = 0;
			double valuesNum = 1;
			double prevX = 0;
			double prevW = w;

			final GeneralPath gp = new GeneralPath();

			for (int varNum = 0; varNum < this.labelingManager
				.labeledVariableCount(); varNum++) {
				final Variable v = this.labelingManager.getVariableAt(varNum);
				final int curOn = v.getMin();

				valuesNum *= v.getValueCount(); // increases exponentially
				prevW = w / valuesNum;
				prevX = prevX + curOn * prevW;

				final int x = (int) prevX;
				final int dx = Math.max(1, (int) prevW);
				if (v == (Variable) o) {
					final Shape shape =
						this.buildShape(x + this.labelSize, y, dx, ch - 1);
					gp.append(shape, true);
					shapes.addElement(shape.getPathIterator(at));
					return shapes;
				}
				y += ch;
			}
		}
		return shapes;
	}

	/**
	 * This method returns a vector of <code>Shape</code> objects corresponding
	 * to the variable, constraint or object of interest. In this method, the
	 * objects name or id is passed as parameter.
	 */
	public Vector getShapes(final String name, final Vector shapes) {
		final Point tp = new Point(0, 0); // translation vector
		SwingUtilities.convertPointToScreen(tp, this);
		final AffineTransform at = new AffineTransform();
		at.setToTranslation(tp.getX(), tp.getY());

		final float w = this.getWidth() - this.labelSize;
		final int h = this.getHeight();

		final Variable target =
			this.labelingManager.getVariableManager().getVariable(name);

		synchronized (this.labelingManager) {
			final int totalVarCnt =
				this.labelingManager.getVariableManager().getVariableCount();
			int ch = 0;
			if (totalVarCnt != 0) {
				ch = h / totalVarCnt;
			}
			else {
				return shapes;
			}
			if (ch < 2) {
				ch = 2;
			}

			int y = 0;
			double valuesNum = 1;
			double prevX = 0;
			double prevW = w;

			final GeneralPath gp = new GeneralPath();

			for (int varNum = 0; varNum < this.labelingManager
				.labeledVariableCount(); varNum++) {
				final Variable v = this.labelingManager.getVariableAt(varNum);
				final int curOn = v.getMin();

				valuesNum *= v.getValueCount(); // increases exponentially
				prevW = w / valuesNum;
				prevX = prevX + curOn * prevW;

				final int x = (int) prevX;
				final int dx = Math.max(1, (int) prevW);
				if (v == target) {
					final Shape shape =
						this.buildShape(x + this.labelSize, y, dx, ch - 1);
					gp.append(shape, true);
					shapes.addElement(shape.getPathIterator(at));
					return shapes;
				}
				y += ch;
			}
		}
		return shapes;
	}
	/**
	 * Get the value of solutionColor.
	 * @return value of solutionColor.
	 */
	public Color getSolutionColor() {
		return this.solutionColor;
	}

	public void mouseClicked(final MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		this.picked = (Variable) this.getObjectAtCoords(e.getX(), e.getY());
		e = SwingUtilities.convertMouseEvent(this, e, this.glass);
		this.glass.setOrigin(e.getX(), e.getY());
		if (this.glass != null) {
			this.glass.getPositions().clear();
			this.glass.setPositions(this
				.getLabelingManager()
				.getVariableManager()
				.getPositions(this.picked, this.glass.getPositions()));
			System.out.println("Number of positions : "
					+ this.glass.getPositions().size());
			this.glass.repaint();
		}
	}

	public void mouseEntered(final MouseEvent e) {
	}
	public void mouseExited(final MouseEvent e) {
		this.glass.getPositions().clear();
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
		final float w = this.getWidth() - this.labelSize;
		final int h = this.getHeight();

		final VarColorManager mgr =
			VarColorManager
				.getVarColorManager(this.labelingManager.getSolver());

		synchronized (this.labelingManager) {
			final int totalVarCnt =
				this.labelingManager.getVariableManager().getVariableCount();
			int ch = 0;
			if (totalVarCnt != 0) {
				ch = h / totalVarCnt;
			}
			else {
				return;
			}
			if (ch < 2) {
				ch = 2;
			}

			int y = 0;
			g.setFont(this.getFont());
			double valuesNum = 1;
			double prevX = 0;
			double prevW = w;
			for (int varNum = 0; varNum < this.labelingManager
				.labeledVariableCount(); varNum++) {
				final Variable v = this.labelingManager.getVariableAt(varNum);
				g.setColor(this.getForeground());
				final int curOn = v.getMin();
				g.drawString(v.getName() + ":" + v.getValueAt(curOn), 0, y + ch
						/ 2);
				final Color varColor = mgr.getVarColor(v);
				g.setColor(varColor);

				valuesNum *= v.getValueCount(); // increases exponentially
				prevW = w / valuesNum;
				prevX = prevX + curOn * prevW;

				final int x = (int) prevX;
				final int dx = Math.max(1, (int) prevW);

				g.fillRect(x + this.labelSize, y, dx, ch - 1);

				if (w > 2 * valuesNum) {
					final double cw = w / valuesNum;
					g.setColor(Color.black);
					for (int i = 0; i < valuesNum; i++) {
						final int nx = this.labelSize + (int) (i * cw);
						g.drawLine(nx, y, nx, y + ch);
					}
				}
				y += ch;
			}
		}
	}
	public void setGlass(final GlassPane pane) {
		this.glass = pane;
	}
	/**
	 * Set the value of labelingManager.
	 * @param v  Value to assign to labelingManager.
	 */
	public void setLabelingManager(final LabelingManager v) {
		this.labelingManager = v;
	}
	/**
	 * Set the value of rejectColor.
	 * @param v  Value to assign to rejectColor.
	 */
	public void setRejectColor(final Color v) {
		this.rejectColor = v;
	}
	/**
	 * Set the value of solutionColor.
	 * @param v  Value to assign to solutionColor.
	 */
	public void setSolutionColor(final Color v) {
		this.solutionColor = v;
	}
}