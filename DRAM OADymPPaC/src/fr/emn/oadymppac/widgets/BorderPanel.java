package fr.emn.oadymppac.widgets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

class BorderPanel extends JPanel implements BorderStyle {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5658778019670142693L;
	protected int borderStyle = BorderStyle.NONE;
	protected boolean skipTop = false;
	protected boolean skipLeft = false;
	protected boolean skipBottom = false;
	protected boolean skipRight = false;

	BorderPanel() {
		this(BorderStyle.NONE);
	}

	BorderPanel(final int borderStyle) {
		this.borderStyle = borderStyle;
	}

	public void draw3DBorder(final Graphics g) {

		final Color oldColor = g.getColor();
		final Dimension bounds = this.getSize();

		switch (this.borderStyle) {

			case FLAT :
				g.setColor(new Color(128, 128, 128));
				//TOP
				if (!this.skipTop) {
					g.drawLine(0, 0, bounds.width - 1, 0);
				}
				//LEFT
				if (!this.skipLeft) {
					g.drawLine(0, 0, 0, bounds.height - 1);
				}
				//BOTTOM
				if (!this.skipBottom) {
					g.drawLine(0, bounds.height - 1, bounds.width - 1, 0);
				}
				//RIGHT
				if (!this.skipRight) {
					g.drawLine(
						bounds.width - 1,
						0,
						bounds.width - 1,
						bounds.height - 1);
				}
				break;

			case LOWERED :
				g.setColor(new Color(128, 128, 128));
				//TOP
				if (!this.skipTop) {
					g.drawLine(0, 0, bounds.width - 1, 0);
				}
				//LEFT
				if (!this.skipLeft) {
					g.drawLine(0, 0, 0, bounds.height - 1);
				}
				g.setColor(Color.white);
				//BOTTOM
				if (!this.skipBottom) {
					g.drawLine(
						0,
						bounds.height - 1,
						bounds.width - 1,
						bounds.height - 1);
				}
				//RIGHT
				if (!this.skipRight) {
					g.drawLine(
						bounds.width - 1,
						0,
						bounds.width - 1,
						bounds.height - 1);
				}
				break;

			case RAISED :
				g.setColor(Color.white);
				//TOP
				if (!this.skipTop) {
					g.drawLine(0, 0, bounds.width - 1, 0);
				}
				//LEFT
				if (!this.skipLeft) {
					g.drawLine(0, 0, 0, bounds.height - 1);
				}
				g.setColor(Color.black);
				//BOTTOM
				if (!this.skipBottom) {
					g.drawLine(
						0,
						bounds.height - 1,
						bounds.width - 1,
						bounds.height - 1);
				}
				//RIGHT
				if (!this.skipRight) {
					g.drawLine(
						bounds.width - 1,
						0,
						bounds.width - 1,
						bounds.height - 1);
				}
				//INSET
				g.setColor(new Color(128, 128, 128));
				g.drawLine(
					1,
					bounds.height - 2,
					bounds.width - 2,
					bounds.height - 2);
				g.drawLine(
					bounds.width - 2,
					1,
					bounds.width - 2,
					bounds.height - 2);
				break;

			case RIDGED :
			case GROOVED :

				g.setColor(this.borderStyle == BorderStyle.RIDGED ? new Color(
					128,
					128,
					128) : Color.white);
				//TOP
				if (!this.skipTop) {
					g.drawLine(0, 0, bounds.width - 1, 0);
				}
				//LEFT
				if (!this.skipLeft) {
					g.drawLine(0, 0, 0, bounds.height - 1);
				}
				//BOTTOM
				if (!this.skipBottom) {
					g.drawLine(
						0,
						bounds.height - 1,
						bounds.width - 1,
						bounds.height - 1);
				}
				//RIGHT
				if (!this.skipRight) {
					g.drawLine(
						bounds.width - 1,
						0,
						bounds.width - 1,
						bounds.height - 1);
				}
				// second
				g.drawLine(1, 1, bounds.width - 2, 1);
				g.drawLine(1, 1, 1, bounds.height - 2);

				//DRAW INSETS
				g.setColor(this.borderStyle == BorderStyle.RIDGED ? Color.white
						: new Color(128, 128, 128));
				//TOP
				if (!this.skipTop) {
					g.drawLine(0, 0, bounds.width - 2, 0);
				}
				//LEFT
				if (!this.skipLeft) {
					g.drawLine(0, 0, 0, bounds.height - 2);
				}
				//BOTTOM
				if (!this.skipBottom) {
					g.drawLine(
						0,
						bounds.height - 2,
						bounds.width - 2,
						bounds.height - 2);
				}
				//RIGHT
				if (!this.skipRight) {
					g.drawLine(
						bounds.width - 2,
						0,
						bounds.width - 2,
						bounds.height - 2);
				}
				break;
		}
		g.setColor(oldColor);
	}

	public int getBorderStyle() {
		return this.borderStyle;
	}

	public boolean getSkipBottom() {
		return this.skipBottom;
	}

	public boolean getSkipLeft() {
		return this.skipLeft;
	}

	public boolean getSkipRight() {
		return this.skipRight;
	}

	public boolean getSkipTop() {
		return this.skipTop;
	}

	public void paint(final Graphics g) {
		if (this.borderStyle != BorderStyle.NONE) {
			this.draw3DBorder(g);
		}

		super.paint(g);
	}

	public void setBorderStyle(final int borderStyle) {
		this.borderStyle = borderStyle;
	}

	public void setSkipBottom(final boolean onOff) {
		this.skipBottom = onOff;
	}

	public void setSkipLeft(final boolean onOff) {
		this.skipLeft = onOff;
	}

	public void setSkipRight(final boolean onOff) {
		this.skipRight = onOff;
	}

	public void setSkipTop(final boolean onOff) {
		this.skipTop = onOff;
	}
}
