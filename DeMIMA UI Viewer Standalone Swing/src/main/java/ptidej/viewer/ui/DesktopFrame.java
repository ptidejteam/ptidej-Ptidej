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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import ptidej.viewer.ui.panel.AntiPatternPanel;
import ptidej.viewer.ui.panel.CodeSmellPanel;
import ptidej.viewer.ui.panel.DesignMotifPanel;
import ptidej.viewer.ui.panel.MetricPanel;
import ptidej.viewer.ui.panel.MicroPatternPanel;
import ptidej.viewer.ui.panel.OptionPanel;
import ptidej.viewer.ui.panel.QualityPanel;
import ptidej.viewer.ui.panel.ToolPanel;
import ptidej.viewer.ui.panel.VisitorPanel;
import ptidej.viewer.utils.Resources;
import ptidej.viewer.widget.Frame;
import ptidej.viewer.widget.Panel;
import ptidej.viewer.widget.ScrollPane;

public class DesktopFrame extends Frame {
	private static final long serialVersionUID = 1L;
	private static DesktopFrame UniqueInstance;
	public static DesktopFrame getInstance() {
		if (DesktopFrame.UniqueInstance == null) {
			DesktopFrame.UniqueInstance = new DesktopFrame();
			DesktopFrame.UniqueInstance.setIconImage(ptidej.ui.Utils
				.getImage("PtidejIcon.png"));
		}
		return DesktopFrame.UniqueInstance;
	}

	private DesktopFrame() {
		super(
			Resources.getFrameTitle(Resources.MAIN, DesktopPane.class),
			900,
			600);
	}
	public void run() {
		this.setJMenuBar(MenuBar.getUniqueInstance());

		this.getContentPane().add(new ToolBar(), BorderLayout.NORTH);

		final JTabbedPane tabs = new JTabbedPane();
		tabs.addTab(
			Resources.getTabTitle(Resources.TOOLS, DesktopFrame.class),
			new ScrollPane(
				new ToolPanel(),
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
		tabs.addTab(Resources.getTabTitle(
			Resources.DESIGN_MOTIFS,
			DesktopFrame.class), new ScrollPane(
			new DesignMotifPanel(),
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
		tabs.addTab(Resources.getTabTitle(
			Resources.CODE_SMELLS,
			DesktopFrame.class), new ScrollPane(
			new CodeSmellPanel(),
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
		tabs.addTab(Resources.getTabTitle(
			Resources.DESIGN_SMELLS,
			DesktopFrame.class), new ScrollPane(
			new AntiPatternPanel(),
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
		tabs
			.addTab(Resources.getTabTitle(
				Resources.MICRO_PATTERNS,
				DesktopFrame.class), new ScrollPane(
				new MicroPatternPanel(),
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
		tabs.addTab(Resources.getTabTitle(
			Resources.VISITORS,
			DesktopFrame.class), new ScrollPane(
			new VisitorPanel(),
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
		tabs.addTab("Metrics", new ScrollPane(
			new MetricPanel(),
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
		tabs.addTab("Quality", new ScrollPane(
			new QualityPanel(),
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
		final OptionPanel optionPanel = new OptionPanel();
		//	tabs.addTab(
		//		Constants.getTabTitle(
		//			Constants.VIEWER_OPTIONS,
		//			Desktop.class),
		//		new UndockablePanel(
		//			desktopPane,
		//			tabs,
		//			optionPanel,
		//			Constants.getTabTitle(
		//				Constants.VIEWER_OPTIONS,
		//				Desktop.class)));
		tabs
			.addTab(Resources.getTabTitle(
				Resources.VIEWER_OPTIONS,
				DesktopFrame.class), new ScrollPane(
				optionPanel,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
		// Yann 2006/08/20: Visibility.
		// The OptionPanel listens to the DesktopPane to adjust its
		// content to the selected window, so that each source model
		// can have different visibility settings.
		DesktopPane.getInstance().addGraphModelListener(optionPanel);

		final Panel mainPanel = new Panel(new BorderLayout());
		mainPanel.add(DesktopPane.getInstance(), BorderLayout.CENTER);
		mainPanel.add(tabs, BorderLayout.EAST);

		final ScrollPane consolePanel =
			new ScrollPane(
				Console.getInstance(),
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		final JSplitPane mainAndConsolePanel =
			new JSplitPane(JSplitPane.VERTICAL_SPLIT, mainPanel, consolePanel);
		mainAndConsolePanel.setOneTouchExpandable(true);
		mainAndConsolePanel.getRightComponent().setMinimumSize(new Dimension());
		mainAndConsolePanel.setResizeWeight(1.0d);

		this.getContentPane().add(mainAndConsolePanel, BorderLayout.CENTER);
		this.setVisible(true);
	}
	public void setCursor(final Cursor aCursor) {
		super.setCursor(aCursor);

		// Yann 2014/03/29: Recursion!
		// I want to recursively set the cursors 
		// of the frame and all its internal frames.
		final Component[] components =
			DesktopPane.getInstance().getComponents();
		for (int i = 0; i < components.length; i++) {
			final Component component = components[i];
			if (component instanceof IWindow) {
				component.setCursor(aCursor);
			}
		}
	}
}
