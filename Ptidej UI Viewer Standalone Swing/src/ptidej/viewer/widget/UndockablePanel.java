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
package ptidej.viewer.widget;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import ptidej.viewer.ui.DesktopPane;
import ptidej.viewer.utils.Resources;

public class UndockablePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton cmdDock, cmdUndock;
	private boolean isDocked = true;
	private JInternalFrame jifPalette;
	private JPanel pnlDock, pnlContent;
	private String strContentTitle;
	private JTabbedPane tab;

	public UndockablePanel(
		final JDesktopPane desktop,
		final JTabbedPane tab,
		final JPanel content,
		final String strContentTitle) {

		this.setLayout(new BorderLayout());
		this.pnlDock = new JPanel(new BorderLayout());
		this.manageDockPanel();
		this.tab = tab;
		this.pnlContent = content;
		this.strContentTitle = strContentTitle;
		this.add(this.pnlContent, BorderLayout.CENTER);
		this.add(this.pnlDock, BorderLayout.SOUTH);
	}
	public UndockablePanel(
		final JDesktopPane desktop,
		final JTabbedPane tab,
		final JPanel content,
		final String strContentTitle,
		final boolean isDocked) {

		this(desktop, tab, content, strContentTitle);
		this.setDocked(isDocked);
	}
	public void dockingAction() {
		if (this.isDocked()) { // wants to undock
			this.setDocked(false);
			this.cmdDock.setVisible(true);
			this.cmdUndock.setVisible(false);
			this.jifPalette = new JInternalFrame();
			this.jifPalette.putClientProperty(
				"JInternalFrame.isPalette",
				Boolean.TRUE);
			this.jifPalette.setTitle(this.strContentTitle);
			this.jifPalette.setResizable(true);
			this.jifPalette.setIconifiable(false);
			this.jifPalette.setClosable(false);
			this.jifPalette.getContentPane().add(this);

			DesktopPane.getInstance().setLayer(
				this.jifPalette,
				Resources.OPTIONS_LAYER);
			DesktopPane.getInstance().add(this.jifPalette);

			this.jifPalette.setBounds(10, 10, 220, 500);
			this.jifPalette.show();
			this.tab.remove(this);
		}
		else { // wants to dock
			this.setDocked(true);
			this.cmdDock.setVisible(false);
			this.cmdUndock.setVisible(true);
			this.tab.addTab(this.strContentTitle, this);
			this.tab.setSelectedComponent(this);
			DesktopPane.getInstance().remove(this.jifPalette);
			DesktopPane.getInstance().revalidate();
		}
		this.validate();
		this.repaint();
	}
	public boolean isDocked() {
		return this.isDocked;
	}
	public void manageDockPanel() {
		this.pnlDock = new JPanel(new BorderLayout());

		this.cmdDock =
			new JButton(Resources.getButtonText(
				Resources.DOCK,
				UndockablePanel.class));
		this.cmdDock.setHorizontalTextPosition(SwingConstants.LEFT);
		this.cmdDock.setBorderPainted(false);
		this.cmdDock.setContentAreaFilled(false);
		this.cmdDock.setFocusPainted(false);
		this.cmdDock.setMargin(new Insets(0, 0, 0, 0));
		this.cmdDock.setIcon(ptidej.ui.Utils.getIcon(Resources
			.getButtonIconPath(Resources.DOCK, UndockablePanel.class)));
		this.cmdDock.setRolloverIcon(ptidej.ui.Utils.getIcon(Resources
			.getRolloverButtonIconPath(Resources.DOCK, UndockablePanel.class)));
		this.cmdUndock =
			new JButton(Resources.getButtonText(
				Resources.UNDOCK,
				UndockablePanel.class));
		this.cmdUndock.setBorderPainted(false);
		this.cmdUndock.setContentAreaFilled(false);
		this.cmdUndock.setFocusPainted(false);
		this.cmdUndock.setMargin(new Insets(0, 0, 0, 0));
		this.cmdUndock.setIcon(ptidej.ui.Utils.getIcon(Resources
			.getButtonIconPath(Resources.UNDOCK, UndockablePanel.class)));
		this.cmdUndock
			.setRolloverIcon(ptidej.ui.Utils.getIcon(Resources
				.getRolloverButtonIconPath(
					Resources.UNDOCK,
					UndockablePanel.class)));
		this.pnlDock.add(this.cmdUndock, BorderLayout.LINE_START);
		this.pnlDock.add(this.cmdDock, BorderLayout.AFTER_LINE_ENDS);
		if (this.isDocked()) {
			this.cmdUndock.setVisible(true);
			this.cmdDock.setVisible(false);
		}
		else {
			this.cmdDock.setVisible(true);
			this.cmdUndock.setVisible(false);
		}
		this.cmdDock.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				UndockablePanel.this.dockingAction();
			}
		});
		this.cmdUndock.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				UndockablePanel.this.dockingAction();
			}
		});
	}
	public void setDocked(final boolean isDocked) {
		this.isDocked = isDocked;
	}
}
