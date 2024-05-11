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
package ptidej.viewer.awt;

import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import ptidej.ui.awt.AWTCanvas;
import ptidej.ui.canvas.Canvas;
import ptidej.ui.kernel.Constituent;
import ptidej.ui.kernel.ModelGraph;
import util.io.ProxyConsole;
import util.multilingual.MultilingualManager;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/08/19
 */
public class ActionListener implements java.awt.event.ActionListener {
	public static final String GO_TO = MultilingualManager.getString(
		"GO_TO",
		ActionListener.class);

	protected final AWTCanvas awtCanvas;
	protected final Canvas canvas;
	protected final ModelGraph modelGraph;

	public ActionListener(
		final AWTCanvas anAWTCanvas,
		final Canvas aCanvas,
		final ModelGraph aModelGraph) {

		this.awtCanvas = anAWTCanvas;
		this.canvas = aCanvas;
		this.modelGraph = aModelGraph;
	}
	public void actionPerformed(final ActionEvent anEvent) {
		// Yann 2014/03/31: MenuComponent is not a Component!
		// I must use reflection to get the parent and the name
		// of the source component of the event flexibly.
		//	final Component sourceComponent =
		//		(Component) ((Component) e.getSource()).getParent();
		//	final String sourceName = sourceComponent.getName();
		// Excellent example of the use of reflection! :-)
		try {
			final Object source = anEvent.getSource();
			final Method getParentMethod =
				source.getClass().getMethod("getParent", new Class[0]);
			final Object parent = getParentMethod.invoke(source, new Object[0]);
			final Method getNameMethod =
				parent.getClass().getMethod("getName", new Class[0]);
			final String parentName =
				(String) getNameMethod.invoke(parent, new Object[0]);

			if (anEvent.getActionCommand().equals(ActionListener.GO_TO)) {
				final Constituent[] constituents =
					this.modelGraph.listEntities();

				for (int i = 0; i < constituents.length; i++) {
					if (constituents[i].getName().equals(parentName)) {
						this.awtCanvas.goTo(constituents[i].getPosition());
					}
				}
			}

			// Finally, I refresh the whole stuff (i.e., the instances
			// of the ScrollPane, of the AWTCanvas, and of the Canvas.
			this.awtCanvas.repaint();
		}
		catch (final NoSuchMethodException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final SecurityException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final IllegalAccessException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final IllegalArgumentException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final InvocationTargetException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
}
