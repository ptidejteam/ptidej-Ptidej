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
package ptidej.viewer.ui;

import java.awt.Component;
import javax.swing.JInternalFrame;
import ptidej.ui.canvas.event.CanvasEvent;
import ptidej.ui.event.GraphEvent;
import ptidej.viewer.event.SourceAndGraphModelEvent;
import ptidej.viewer.utils.Resources;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/08/19
 */
public abstract class AbstractInternalWindow extends JInternalFrame implements
		IListeningWindow {

	private static final long serialVersionUID = 1L;

	protected AbstractInternalWindow(final String aTitle) {
		this.setFrameIcon(ptidej.ui.Utils.getIcon("PtidejIcon.png"));
		this.setTitle(aTitle);
		DesktopPane.getInstance().setLayer(this, Resources.PROJECTS_LAYER);
		DesktopPane.getInstance().add(this);

		DesktopPane.getInstance().addCanvasListener(this);
		DesktopPane.getInstance().addGraphModelListener(this);
		DesktopPane.getInstance().addSourceModelListener(this);
	}
	public void backgroundElementSelected(final CanvasEvent aCanvasEvent) {
	}
	public void backgroundElementUnSelected(final CanvasEvent aCanvasEvent) {
	}
	public void constituentSelected(final GraphEvent aGraphEvent) {
	}
	public void constituentUnSelected(final GraphEvent aGraphEvent) {
	}
	public void foregroundElementSelected(final CanvasEvent aCanvasEvent) {
	}
	public void foregroundElementUnSelected(final CanvasEvent aCanvasEvent) {
	}
	public void graphModelAvailable(
		final SourceAndGraphModelEvent aSourceModelEvent) {
	}
	public void graphModelChanged(
		final SourceAndGraphModelEvent aSourceModelEvent) {
	}
	public void graphModelUnavailable() {
	}
	public void setVisible(final boolean isVisible) {
		super.setVisible(isVisible);

		if (isVisible) {
			// Yann 2007/08/20: JInternalFrame...
			// Apparently, when a JInternalFrame is closed
			// it is also removed from its container. So I
			// must make sure that I add it back when I set
			// its visibility to true after closing it.
			final Component[] components =
				DesktopPane.getInstance().getComponents();
			boolean found = false;
			for (int i = 0; i < components.length && !found; i++) {
				final Component component = components[i];
				if (component == this) {
					found = true;
				}
			}
			if (!found) {
				DesktopPane.getInstance().add(this);
			}

			// Yann 2007/08/19: First update!
			// After adding a IListeningWindow, I request
			// the DesktopPane to request the current 
			// AbstractRepresentationWindow (if any) to
			// let this IListeningWindow about any interesting
			// event as a bootstrap mechanism.
			final AbstractRepresentationWindow window =
				DesktopPane.getInstance().getAbstractRepresentationWindow();
			if (window != null) {
				window.bootstrapListeningWindows();
			}
		}
	}
	public void sourceModelAvailable(
		final SourceAndGraphModelEvent aSourceModelEvent) {
	}
	public void sourceModelChanged(
		final SourceAndGraphModelEvent aSourceModelEvent) {
	}
	public void sourceModelUnavailable() {
	}
}
