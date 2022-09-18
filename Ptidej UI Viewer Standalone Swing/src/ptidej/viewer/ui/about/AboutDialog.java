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
package ptidej.viewer.ui.about;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.HeadlessException;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import ptidej.viewer.utils.Resources;
import ptidej.viewer.utils.Utils;
import ptidej.viewer.widget.Dialog;
import ptidej.viewer.widget.EmbeddedPanel;
import ptidej.viewer.widget.ScrollPane;

public class AboutDialog extends Dialog {
	private static final long serialVersionUID = 1L;

	public class AboutCopyrightPanel extends EmbeddedPanel {
		private static final long serialVersionUID = 1L;

		public AboutCopyrightPanel() {
			super();
			this.addTextPane(Resources.COPYRIGHT, AboutDialog.class);
		}
	}
	public class AboutDeveloppersPanel extends EmbeddedPanel {
		private static final long serialVersionUID = 1L;

		public AboutDeveloppersPanel() {
			super();
			this.addTextPane(Resources.DEVELOPPERS, AboutDialog.class);
		}
	}
	public class AboutPtidejPanel extends EmbeddedPanel {
		private static final long serialVersionUID = 1L;

		public AboutPtidejPanel() {
			super();
			this.addTextPane(Resources.PTIDEJ, AboutDialog.class);
		}
	}
	public class AboutToolsSuitePanel extends EmbeddedPanel {
		private static final long serialVersionUID = 1L;

		public AboutToolsSuitePanel() {
			super();
			this.addTextPane(Resources.TOOLS_SUITE, AboutDialog.class);
		}
	}

	private static AboutDialog UniqueInstance;
	public static AboutDialog getUniqueInstance() {
		return AboutDialog.getUniqueInstance(null);
	}
	public static AboutDialog getUniqueInstance(final Frame owner) {
		return AboutDialog.getUniqueInstance(
			owner,
			Resources.getFrameTitle(Resources.ABOUT, AboutDialog.class),
			true,
			630,
			520);
	}
	public static AboutDialog getUniqueInstance(
		final Frame owner,
		final String title,
		final boolean modal,
		final int width,
		final int height) {

		if (AboutDialog.UniqueInstance == null) {
			AboutDialog.UniqueInstance =
				new AboutDialog(owner, title, modal, width, height);
			AboutDialog.UniqueInstance.setLocationRelativeTo(owner);
		}
		return AboutDialog.UniqueInstance;
	}
	public static void main(final String[] args) {
		AboutDialog.getUniqueInstance().setVisible(true);
	}

	private JTabbedPane pnlTabs;
	private AboutDialog(
		final Frame owner,
		final String title,
		final boolean modal,
		final int width,
		final int height) throws HeadlessException {

		super(owner, title, modal, width, height);
		AboutDialog.UniqueInstance = this;
		this.getContentPane().add(
			new JLabel(
				Utils.getImageIcon(Resources.PTIDEJ_LOGO, AboutDialog.class),
				0),
			BorderLayout.NORTH);

		this.pnlTabs = new JTabbedPane();
		this.pnlTabs.addTab(
			Resources.getTabTitle(Resources.PTIDEJ, AboutDialog.class),
			new ScrollPane(new AboutPtidejPanel()));
		this.pnlTabs.addTab(
			Resources.getTabTitle(Resources.DEVELOPPERS, AboutDialog.class),
			new ScrollPane(new AboutDeveloppersPanel()));
		this.pnlTabs.addTab(
			Resources.getTabTitle(Resources.COPYRIGHT, AboutDialog.class),
			new ScrollPane(new AboutCopyrightPanel()));
		this.pnlTabs.addTab(
			Resources.getTabTitle(Resources.TOOLS_SUITE, AboutDialog.class),
			new ScrollPane(new AboutToolsSuitePanel()));

		this.getContentPane().add(this.pnlTabs, BorderLayout.CENTER);
		// TODO Redo the PtidejFooter.png image
		//	this.getContentPane().add(
		//		new JLabel(Utils.getIcon(
		//			Constants.PTIDEJ_LOGO_FOOTER,
		//			AboutDialog.class), 0),
		//		BorderLayout.SOUTH);
	}
}
