package caffeine.example.arctest;

/*
 * @(#)ArcTest.java	1.5 98/06/29
 *
 * Copyright (c) 1997, 1998 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) Licensee does not utilize the software in a manner
 * which is disparaging to Sun.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 */

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class ArcCanvas extends Canvas {
	private static final long serialVersionUID = -7284526035292199514L;

	int startAngle = 0;
	int endAngle = 45;
	boolean filled = false;
	Font font;

	public void paint(Graphics g) {
		Rectangle r = getBounds();
		int hlines = r.height / 10;
		int vlines = r.width / 10;

		g.setColor(Color.pink);
		for (int i = 1; i <= hlines; i++) {
			g.drawLine(0, i * 10, r.width, i * 10);
		}
		for (int i = 1; i <= vlines; i++) {
			g.drawLine(i * 10, 0, i * 10, r.height);
		}

		g.setColor(Color.red);
		if (this.filled) {
			g.fillArc(0, 0, r.width - 1, r.height - 1, this.startAngle, this.endAngle);
		}
		else {
			g.drawArc(0, 0, r.width - 1, r.height - 1, this.startAngle, this.endAngle);
		}

		g.setColor(Color.black);
		g.setFont(this.font);
		g.drawLine(0, r.height / 2, r.width, r.height / 2);
		g.drawLine(r.width / 2, 0, r.width / 2, r.height);
		g.drawLine(0, 0, r.width, r.height);
		g.drawLine(r.width, 0, 0, r.height);
		int sx = 10;
		int sy = r.height - 28;
		g.drawString("S = " + this.startAngle, sx, sy);
		g.drawString("E = " + this.endAngle, sx, sy + 14);
	}
	public void redraw(boolean filled, int start, int end) {
		this.filled = filled;
		this.startAngle = start;
		this.endAngle = end;
		repaint();
	}
}