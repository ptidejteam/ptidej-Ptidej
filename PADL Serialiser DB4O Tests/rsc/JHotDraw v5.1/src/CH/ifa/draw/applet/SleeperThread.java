/*
 * @(#)DrawApplet.java 5.1
 *
 */

package CH.ifa.draw.applet;

import java.applet.Applet;


class SleeperThread extends Thread {

	Applet  fApplet;

	SleeperThread(Applet applet) {
		fApplet = applet;
	}

	public void run() {
		try {
			for (;;) {
				fApplet.showStatus("loading icons...");
				sleep(50);
			}
		} catch (InterruptedException e) {
			return;
		}
	}

}

