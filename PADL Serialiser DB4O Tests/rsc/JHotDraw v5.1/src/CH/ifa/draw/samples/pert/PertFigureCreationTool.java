/*
 * @(#)PertFigureCreationTool.java 5.1
 *
 */

package CH.ifa.draw.samples.pert;

import CH.ifa.draw.framework.DrawingView;
import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.standard.CreationTool;

/**
 * A more efficient version of the generic Pert creation
 * tool that is not based on cloning.
 */

public  class PertFigureCreationTool extends CreationTool {

	public PertFigureCreationTool(DrawingView view) {
		super(view);
	}

	/**
	 * Creates a new PertFigure.
	 */
	protected Figure createFigure() {
		return new PertFigure();
	}
}
