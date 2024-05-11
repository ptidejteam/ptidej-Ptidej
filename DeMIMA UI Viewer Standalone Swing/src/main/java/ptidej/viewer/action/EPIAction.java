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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import javax.swing.AbstractAction;
import padl.kernel.IIdiomLevelModel;
import ptidej.viewer.ViewerCommons;
import ptidej.viewer.ui.DesktopPane;
import ptidej.viewer.utils.Resources;
import util.help.Browser;
import util.io.ProxyConsole;
import util.io.ProxyDisk;
import util.io.ReaderInputStream;
import epi.solver.Solution;
import epi.solver.StringBuilder;
import epi.ui.EPIDialog;
import epi.ui.SolutionDialog;

public class EPIAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private static EPIAction UniqueInstance;
	public static EPIAction getInstance() {
		return (EPIAction.UniqueInstance == null) ? EPIAction.UniqueInstance =
			new EPIAction() : EPIAction.UniqueInstance;
	}

	private EPIAction() {
	}
	public void actionPerformed(final ActionEvent ae) {
		final String action = ae.getActionCommand();
		if (action.equals(Resources.EPI_FIND_SIMILAR_MICRO_ARCHITECTURE)) {
			final IIdiomLevelModel idiomLevelModel =
				(IIdiomLevelModel) DesktopPane
					.getInstance()
					.getAbstractRepresentationWindow()
					.getSourceModel();
			final EPIDialog inst =
				new EPIDialog(
					javax.swing.JOptionPane.getFrameForComponent(DesktopPane
						.getInstance()),
					true,
					idiomLevelModel,
					StringBuilder.buildModelString(idiomLevelModel));
			inst.setVisible(true);

			if (inst.isSolved()) {
				final Solution[] sol = inst.getSolutions();

				try {
					final PrintWriter out =
						new PrintWriter(ProxyDisk.getInstance().fileTempOutput(
							"rsc/" + inst.getLogger().getIdentificationName()
									+ ".ini"));
					Solution.print(sol, out);
				}
				catch (Exception e) {
					e.printStackTrace(ProxyConsole.getInstance().errorOutput());
				}

				SolutionDialog inst2 =
					new SolutionDialog(
						javax.swing.JOptionPane.getFrameForComponent(DesktopPane
							.getInstance()),
						false);
				inst2.setData(inst.getLogger());
				inst2.show();

				final Properties solutions = new Properties();
				try {
					solutions.load(new ReaderInputStream(ProxyDisk
						.getInstance()
						.fileAbsoluteInput(
							"rsc/" + inst.getLogger().getIdentificationName()
									+ ".ini")));
				}
				catch (final IOException e) {
					e.printStackTrace(ProxyConsole.getInstance().errorOutput());
				}
				ViewerCommons.loadConstraintsData(DesktopPane
					.getInstance()
					.getAbstractRepresentationWindow(), solutions);
			}
		}
		else if (action.equals(Resources.EPI_SIMILAR_MICRO_ARCHITECTURE_HELP)) {
			Browser.displayURL(Resources
				.getLink(Resources.EPI_SIMILAR_MICRO_ARCHITECTURE_HELP));
		}
	}
}
