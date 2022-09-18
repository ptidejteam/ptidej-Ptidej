/*
 * @(#)PasteCommand.java 5.1
 *
 */

package CH.ifa.draw.standard;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.Vector;

import CH.ifa.draw.framework.DrawingView;
import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.framework.FigureSelection;
import CH.ifa.draw.util.Clipboard;

/**
 * Command to insert the clipboard into the drawing.
 * @see Clipboard
 */
public class PasteCommand extends FigureTransferCommand {

   /**
	* Constructs a paste command.
	* @param name the command name
	* @param image the pathname of the image
	* @param view the target view
	*/
	public PasteCommand(String name, DrawingView view) {
		super(name, view);
	}

	public void execute() {
		Point lastClick = fView.lastClick();
		FigureSelection selection = (FigureSelection)Clipboard.getClipboard().getContents();
		if (selection != null) {
			Vector figures = (Vector)selection.getData(FigureSelection.TYPE);
			if (figures.size() == 0)
				return;

			Rectangle r = bounds(figures.elements());
			fView.clearSelection();

			insertFigures(figures, lastClick.x-r.x, lastClick.y-r.y);
			fView.checkDamage();
		}
	}

	Rectangle bounds(Enumeration k) {
		Rectangle r = ((Figure) k.nextElement()).displayBox();
		while (k.hasMoreElements())
			r.add(((Figure) k.nextElement()).displayBox());
		return r;
	}
}


