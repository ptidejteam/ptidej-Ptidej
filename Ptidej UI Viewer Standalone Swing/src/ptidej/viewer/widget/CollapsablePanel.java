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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import ptidej.viewer.ui.DesktopFrame;
import ptidej.viewer.ui.MenuBar;
import ptidej.viewer.utils.Resources;

public class CollapsablePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pnlTitle, pnlContent;
	private GridBagConstraints gbc;
	private JButton cmdClose;
	private JToggleButton toggleExpand;
	private JLabel lblTitle;
	private JCheckBoxMenuItem menuItem;

	public CollapsablePanel(final String strTitle, final JPanel pnlContent) {
		this.setLayout(new GridBagLayout());
		this.gbc = new GridBagConstraints();

		this.pnlTitle = new JPanel(new BorderLayout());
		this.manageTitlePanel(strTitle);

		this.pnlContent = pnlContent;

		this.pnlTitle.setBorder(new BevelRoundedEdgeBorder(
			BevelRoundedEdgeBorder.RAISED,
			RoundedEdgeBorder.TOP_EDGES));
		this.pnlContent.setBorder(new BevelRoundedEdgeBorder(
			BevelRoundedEdgeBorder.RAISED,
			RoundedEdgeBorder.BOTTOM_EDGES));

		this.gbc.weightx = 1.0;
		this.gbc.fill = GridBagConstraints.HORIZONTAL;
		this.gbc.gridy = 0;
		this.gbc.insets = new Insets(1, 2, 1, 2);
		this.add(this.pnlTitle, this.gbc);
		this.gbc.insets = new Insets(0, 2, 1, 2);
		this.gbc.gridy = 1;
		this.add(pnlContent, this.gbc);
	}
	private void manageTitlePanel(final String strTitle) {
		this.lblTitle = new JLabel(strTitle);

		this.addMenuBarItem();

		this.toggleExpand = new JToggleButton();
		this.toggleExpand.setSelected(true);
		this.toggleExpand.setBorderPainted(false);
		this.toggleExpand.setContentAreaFilled(false);
		this.toggleExpand.setFocusPainted(false);
		this.toggleExpand.setMargin(new Insets(0, 0, 0, 0));
		this.toggleExpand
			.setIcon(ptidej.ui.Utils.getIcon(Resources.getToggleButtonIconPath(
				Resources.EXPAND,
				CollapsablePanel.class)));
		this.toggleExpand.setRolloverIcon(ptidej.ui.Utils.getIcon(Resources
			.getRolloverToggleButtonIconPath(
				Resources.EXPAND,
				CollapsablePanel.class)));
		this.toggleExpand.setSelectedIcon(ptidej.ui.Utils.getIcon(Resources
			.getSelectedToggleButtonIconPath(
				Resources.EXPAND,
				CollapsablePanel.class)));
		this.toggleExpand.setRolloverSelectedIcon(ptidej.ui.Utils
			.getIcon(Resources.getSelectedRolloverToggleButtonIconPath(
				Resources.EXPAND,
				CollapsablePanel.class)));
		this.toggleExpand.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				CollapsablePanel.this
					.actOnContentPanel(CollapsablePanel.this.toggleExpand
						.isSelected());
			}
		});

		this.cmdClose = new JButton();
		this.cmdClose.setBorderPainted(false);
		this.cmdClose.setContentAreaFilled(false);
		this.cmdClose.setFocusPainted(false);
		this.cmdClose.setMargin(new Insets(0, 0, 0, 0));
		this.cmdClose.setIcon(ptidej.ui.Utils.getIcon(Resources
			.getButtonIconPath(Resources.CLOSE, CollapsablePanel.class)));
		this.cmdClose
			.setRolloverIcon(ptidej.ui.Utils.getIcon(Resources
				.getRolloverButtonIconPath(
					Resources.CLOSE,
					CollapsablePanel.class)));

		this.cmdClose.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				CollapsablePanel.this.actOnPanel(false);
			}
		});

		this.pnlTitle.add(this.toggleExpand, BorderLayout.LINE_START);
		this.pnlTitle.add(this.lblTitle, BorderLayout.CENTER);
		this.pnlTitle.add(this.cmdClose, BorderLayout.AFTER_LINE_ENDS);
	}
	public void actOnContentPanel(final boolean isSelected) {
		if (isSelected) {
			this.gbc.gridy = 1;
			this.pnlTitle.setBorder(new BevelRoundedEdgeBorder(
				BevelRoundedEdgeBorder.RAISED,
				RoundedEdgeBorder.TOP_EDGES));
			this.pnlContent.setVisible(true);
			DesktopFrame.getInstance().validate();
		}
		else {
			this.pnlTitle.setBorder(new BevelRoundedEdgeBorder(
				BevelRoundedEdgeBorder.RAISED,
				RoundedEdgeBorder.ALL_EDGES));
			this.pnlContent.setVisible(false);
			DesktopFrame.getInstance().validate();
		}
	}
	public void actOnPanel(final boolean setVisible) {
		this.setVisible(setVisible);
		DesktopFrame.getInstance().validate();
		this.menuItem.setSelected(setVisible);
	}
	public void addMenuBarItem() {
		this.menuItem =
			MenuBar.getUniqueInstance().addToolBarItem(
				this.lblTitle.getText(),
				this.isVisible());
		this.menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				CollapsablePanel.this.actOnPanel(CollapsablePanel.this.menuItem
					.isSelected());
			}
		});
	}

}
