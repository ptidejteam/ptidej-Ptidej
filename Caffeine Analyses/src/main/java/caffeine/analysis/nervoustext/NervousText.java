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
package caffeine.analysis.nervoustext;

import java.applet.Applet;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * An applet that displays jittering text on the screen.
 *
 * @author Daniel Wyszynski 04/12/95
 * @version 1.10, 02/05/97
 * @modified 05/09/95 kwalrath Changed string; added thread suspension
 * @modified 02/06/98 madbot removed use of suspend and resume and cleaned up
 * @modified 27/03/01 Yann-Gaël Guéhéneuc transformed the applet into an application (very unclean!)
 */

@SuppressWarnings("removal")
public class NervousText extends Applet implements Runnable, MouseListener {
	private static final long serialVersionUID = 8490652697797528457L;

	private static int WIDTH = 600;
	private static int HEIGHT = 80;
	private Graphics g;

	public static void main(final String[] args) {
		final Frame frame = new Frame();

		frame.setSize(NervousText.WIDTH, NervousText.HEIGHT);
		frame.setLocation(200, 200);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		frame.setVisible(true);

		NervousText nervousText = new NervousText();
		nervousText.init(frame.getGraphics());
		nervousText.start();
	}

	String banner; // The text to be displayed
	char bannerChars[]; // The same text as an array of characters
	Thread runner = null; // The thread that is displaying the text
	boolean threadSuspended; // True when thread suspended (via mouse click)

	public void destroy() {
		removeMouseListener(this);
	}
	public String getAppletInfo() {
		return "Title: NervousText\nAuthor: Daniel Wyszynski\nDisplays a text banner that jitters.";
	}
	public String[][] getParameterInfo() {
		String pinfo[][] = { { "text", "string", "Text to display" }, };
		return pinfo;
	}
	public void init(final Graphics g) {
		this.g = g;

		// banner = getParameter("text");
		if (this.banner == null) {
			this.banner = "Caffeine, the very essence of Java!";
		}
		int bannerLength = this.banner.length();
		this.bannerChars = new char[bannerLength];
		this.banner.getChars(0, this.banner.length(), this.bannerChars, 0);

		this.threadSuspended = false;

		resize(15 * (bannerLength + 1), 100);
		setFont(new Font("TimesRoman", Font.BOLD, 36));
		addMouseListener(this);
	}
	public void mouseClicked(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public synchronized void mousePressed(MouseEvent e) {
		e.consume();
		this.threadSuspended = !this.threadSuspended;
		if (!this.threadSuspended)
			notify();
	}
	public void mouseReleased(MouseEvent e) {
	}
	public void paint(Graphics g) {
		for (int i = 0, length = this.banner.length(); i < length; i++) {
			int x = (int) (10 * Math.random() + 15 * i + 10);
			int y = (int) (10 * Math.random() + 40);
			g.drawChars(this.bannerChars, i, 1, x, y);
		}
	}
	public void run() {
		Thread me = Thread.currentThread();
		while (this.runner == me) {
			try {
				Thread.sleep(100);
				synchronized (this) {
					while (this.threadSuspended) {
						wait();
					}
				}
			}
			catch (InterruptedException e) {
			}
			this.g.clearRect(0, 0, NervousText.WIDTH, NervousText.HEIGHT);
			paint(this.g);
		}
	}
	public void start() {
		this.runner = new Thread(this);
		this.runner.start();
	}
	public synchronized void stop() {
		this.runner = null;
		if (this.threadSuspended) {
			this.threadSuspended = false;
			notify();
		}
	}
}
