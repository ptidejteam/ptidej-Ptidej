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

import java.applet.Applet;
import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * An interactive test of the Graphics.drawArc and Graphics.fillArc
 * routines. Can be run either as a standalone application by
 * typing "java ArcTest" or as an applet in the AppletViewer.
 */
@SuppressWarnings({ "removal", "deprecation" })
public class ArcTest extends Applet {
	private static final long serialVersionUID = -7401286294954689606L;

	ArcControls controls; // The controls for marking and filling arcs
	ArcCanvas canvas; // The drawing area to display arcs

	public void destroy() {
		remove(this.controls);
		remove(this.canvas);
	}
	public String getAppletInfo() {
		return "An interactive test of the Graphics.drawArc and \nGraphics.fillArc routines. Can be run \neither as a standalone application by typing 'java ArcTest' \nor as an applet in the AppletViewer.";
	}
	public void init() {
		setLayout(new BorderLayout());
		this.canvas = new ArcCanvas();
		add("Center", this.canvas);
		add("South", this.controls = new ArcControls(this.canvas));
	}
	public static void main(String args[]) {
		Frame f = new Frame("ArcTest");
		ArcTest arcTest = new ArcTest();

		arcTest.init();
		arcTest.start();

		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		f.add("Center", arcTest);
		f.setSize(300, 300);
		f.setLocation(300, 300);
		f.setVisible(true);
	}
	public void processEvent(AWTEvent e) {
		if (e.getID() == Event.WINDOW_DESTROY) {
			System.exit(0);
		}
	}
	public void start() {
		this.controls.setEnabled(true);
	}
	public void stop() {
		this.controls.setEnabled(false);
	}
}