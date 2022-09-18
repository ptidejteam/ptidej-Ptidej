/*
 * @(#)FollowURLTool.java 5.1
 *
 */

package CH.ifa.draw.samples.javadraw;

import java.applet.Applet;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;

import CH.ifa.draw.framework.DrawingView;
import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.standard.AbstractTool;

class FollowURLTool extends AbstractTool {
	 private Applet         fApplet;

	 FollowURLTool(DrawingView view, Applet applet) {
		super(view);
		fApplet = applet;
	 } 

	/**
	 * Handles mouse move events in the drawing view.
	 */
	public void mouseMove(MouseEvent e, int x, int y) {
		String urlstring = null;
	    Figure figure = drawing().findFigureInside(x,y);
	    if (figure != null)
		    urlstring = (String) figure.getAttribute("URL");
	    if (urlstring != null)
	        fApplet.showStatus(urlstring);
	    else
	        fApplet.showStatus("");
	}

	/**
	 * Handles mouse up in the drawing view.
	 */
	public void mouseUp(MouseEvent e, int x, int y) {
	    Figure figure = drawing().findFigureInside(x, y);
	    if (figure == null)
	        return;
	    String urlstring = (String) figure.getAttribute("URL");
	    if (urlstring == null)
	        return;

	    try {
		    URL url = new URL(fApplet.getDocumentBase(), urlstring);
			fApplet.getAppletContext().showDocument(url);
		} catch (MalformedURLException exception) {
			fApplet.showStatus(exception.toString());
		}
	}
}
