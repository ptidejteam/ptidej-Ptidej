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
package ptidej.viewer.ui.panel;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import ptidej.viewer.ViewerCommons;
import ptidej.viewer.utils.Resources;
import ptidej.viewer.utils.Controls;
import ptidej.viewer.widget.EmbeddedPanel;

public class ToolPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public ToolPanel() {
		// Yann 2006/07/18: Merge with AWT version
		// I now use a common algorithm to build buttons
		// both for the AWT and the Swing version of the
		// user-interface.
		//	final PtidejPanel umlPanel = new PtidejPanel();
		//	umlPanel.addButton(
		//		PtidejConstants.SYSTEMATIC_UML,
		//		ToolPanel.class,
		//		PtidejConstants.TOOLS,
		//		PtidejPanel.ALONE);
		//	this.addCollapsablePanel(
		//		PtidejConstants.UML,
		//		ToolPanel.class,
		//		umlPanel,
		//		PtidejPanel.FIRST);
		//
		//	this.addCollapsablePanel(
		//		PtidejConstants.MODELS,
		//		ToolPanel.class,
		//		new ModelPanel());
		//
		//	this.addCollapsablePanel(
		//		PtidejConstants.OTHER_TOOLS,
		//		ToolPanel.class,
		//		new OtherToolPanel(),
		//		PtidejPanel.LAST);

		final EmbeddedPanel panel = new EmbeddedPanel();
		this.setLayout(new BorderLayout());
		this.add(panel, BorderLayout.NORTH);

		EmbeddedPanel content = new EmbeddedPanel();
		content.addButton(
			Resources.SHOW_STATISTICS,
			ToolPanel.class,
			Resources.PTIDEJ_SOLVERS,
			true,
			false,
			false);
		content.addButton(
			Resources.LIST_ENTITIES,
			ToolPanel.class,
			Resources.PTIDEJ_SOLVERS,
			true,
			false,
			false);
		panel.addCollapsablePanel("Models", content);

		content = new EmbeddedPanel();
		content.addButton(
			Resources.LOAD_SIMILAR_MICRO_ARCHITECTURES,
			ToolPanel.class,
			Resources.PTIDEJ_SOLVERS,
			false,
			false,
			true);
		content.addButton(
			Resources.LIST_SIMILAR_MICRO_ARCHITECTURES,
			ToolPanel.class,
			Resources.PTIDEJ_SOLVERS,
			false,
			false,
			true);
		content.addButton(
			Resources.REMOVE_ALL_SIMILAR_MICRO_ARCHITECTURES,
			ToolPanel.class,
			Resources.PTIDEJ_SOLVERS,
			false,
			false,
			true);
		//	content.addButton(
		//		Constants.MODIFY_PROGRAM_MODEL,
		//		SolverGeneralPanel.class,
		//		Constants.PTIDEJ_SOLVERS,
		//		false,
		//		false,
		//		true);
		panel.addCollapsablePanel("Occurrences", content);

		content = new EmbeddedPanel();
		ViewerCommons.addAnalyses(
			content,
			"padl.analysis.AnalysesRepository",
			"getInstance",
			"getAnalyses",
			Controls.getInstance().areToolsListening());
		panel.addCollapsablePanel("PADL Analyses", content);

		content = new EmbeddedPanel();
		ViewerCommons.addAnalyses(
			content,
			"ptidej.ui.analysis.UIAnalysesRepository",
			"getInstance",
			"getUIAnalyses",
			Controls.getInstance().areToolsListening());
		panel.addCollapsablePanel("UI Analyses", content);

		content = new EmbeddedPanel();
		ViewerCommons.addAnalyses(
			content,
			"ptidej.viewer.extension.ViewerExtensionsRepository",
			"getInstance",
			"getViewerExtensions",
			Controls.getInstance().areToolsListening());
		panel.addCollapsablePanel("Viewer Extensions", content);
	}
}
