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
package ptidej.viewer.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.AbstractAction;
import ptidej.solver.OccurrenceGenerator;
import ptidej.ui.IDrawable;
import ptidej.ui.occurrence.IGroupOccurrenceTip;
import ptidej.viewer.GroupOccurrence;
import ptidej.viewer.ViewerCommons;
import ptidej.viewer.ui.AbstractRepresentationWindow;
import ptidej.viewer.ui.DesktopFrame;
import ptidej.viewer.ui.DesktopPane;
import ptidej.viewer.ui.window.ListEntitiesWindow;
import ptidej.viewer.ui.window.ListOccurrencesWindow;
import ptidej.viewer.ui.window.ModelStatisticsWindow;
import ptidej.viewer.utils.Resources;
import ptidej.viewer.utils.SingletonBag;
import ptidej.viewer.utils.Utils;
import util.io.ProxyConsole;

public class SolversAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private static SolversAction UniqueInstance;
	public static SolversAction getInstance() {
		if (SolversAction.UniqueInstance == null) {
			SolversAction.UniqueInstance = new SolversAction();
		}
		return SolversAction.UniqueInstance;
	}

	private SolversAction() {
	}
	public void actionPerformed(final ActionEvent anActionEvent) {
		final String action = anActionEvent.getActionCommand();

		if (action.equals(Resources.SOLVER_PROBLEM_AUTOMATIC)) {
			DesktopPane.getInstance().setSolver(
				OccurrenceGenerator.SOLVER_AUTOMATIC);
		}
		else if (action.equals(Resources.SOLVER_COMBINATORIAL_AUTOMATIC)) {
			DesktopPane.getInstance().setSolver(
				OccurrenceGenerator.SOLVER_COMBINATORIAL_AUTOMATIC);
		}
		else if (action.equals(Resources.SOLVER_SIMPLE_AUTOMATIC)) {
			DesktopPane.getInstance().setSolver(
				OccurrenceGenerator.SOLVER_SIMPLE_AUTOMATIC);
		}
		else if (action.equals(Resources.SHOW_STATISTICS)) {
			final ModelStatisticsWindow window =
				(ModelStatisticsWindow) SingletonBag
					.getInstance(ModelStatisticsWindow.class);
			window.setVisible(true);
		}
		else if (action.equals(Resources.LIST_ENTITIES)) {
			final ListEntitiesWindow window =
				(ListEntitiesWindow) SingletonBag
					.getInstance(ListEntitiesWindow.class);
			window.setVisible(true);
		}
		else if (action.equals(Resources.LOAD_SIMILAR_MICRO_ARCHITECTURES)) {
			final File file =
				Utils.loadFile(
					DesktopFrame.getInstance(),
					false,
					"Choose file",
					"ini",
					"Occurrences");
			if (file == null) {
				return;
			}

			try {
				Properties solutions = new Properties();
				solutions.load(new FileInputStream(file));

				ViewerCommons.loadConstraintsData(DesktopPane
					.getInstance()
					.getAbstractRepresentationWindow(), solutions);
			}
			catch (IOException ioe) {
				ioe.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
		}
		else if (action.equals(Resources.LIST_SIMILAR_MICRO_ARCHITECTURES)) {
			final ListOccurrencesWindow window =
				(ListOccurrencesWindow) SingletonBag
					.getInstance(ListOccurrencesWindow.class);
			window.setVisible(true);
		}
		else if (action
			.equals(Resources.REMOVE_ALL_SIMILAR_MICRO_ARCHITECTURES)) {

			final AbstractRepresentationWindow window =
				DesktopPane.getInstance().getAbstractRepresentationWindow();

			IDrawable[] drawables = window.getCanvas().getBackgroundElements();
			for (int i = 0; i < drawables.length; i++) {
				if (drawables[i] instanceof GroupOccurrence) {
					window.getCanvas().removeFromBackground(drawables[i]);
				}
			}

			drawables = window.getCanvas().getForegroundElements();
			for (int i = 0; i < drawables.length; i++) {
				if (drawables[i] instanceof IGroupOccurrenceTip) {
					window.getCanvas().removeFromForeground(drawables[i]);
				}
			}

			window.clearOccurrences();
		}
		else if (action.equals(Resources.MODIFY_PROGRAM_MODEL)) {
			ProxyConsole
				.getInstance()
				.normalOutput()
				.print(Resources.MODIFY_PROGRAM_MODEL);
			ProxyConsole
				.getInstance()
				.normalOutput()
				.println(" not yet implemented.");
		}
	}
}
