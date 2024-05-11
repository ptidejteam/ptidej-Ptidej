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

import java.awt.Insets;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JToolBar;
import ptidej.viewer.action.ActionsRepository;
import ptidej.viewer.utils.Resources;
import ptidej.viewer.widget.DropDownButton;

public class ToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;

	private final ActionsRepository actionsRepository = ActionsRepository
		.getInstance();
	public DropDownButton dropdown;

	public ToolBar() {
		this.setFloatable(true);
		this.setName(Resources.getToolbarTitle(Resources.FILE, ToolBar.class));
		this.setToolTipText(this.getName());
		this.putClientProperty("JToolBar.isRollover", Boolean.TRUE);

		// TODO To reestablish when multilingual projects are really possible.
		//	this.addToolbarButton(
		//		Constants.NEW_GRAPHICAL_PROJECT,
		//		Constants.FILE,
		//		true);
		//	this.addToolbarButton(
		//		Constants.NEW_HIERARCHICAL_PROJECT,
		//		Constants.FILE,
		//		true);
		//	this.addSeparator();
		this.addToolbarButton(
			Resources.LOAD_GRAPHICAL_PROJECT,
			Resources.FILE,
			true);
		this.addToolbarButton(
			Resources.LOAD_HIERARCHICAL_PROJECT,
			Resources.FILE,
			true);
		this.addToolbarButton(
			Resources.LOAD_DUAL_HIERARCHICAL_PROJECT,
			Resources.FILE,
			true);
		this.addSeparator();

		this.dropdown =
			new DropDownButton(2, new String[] { Resources.AOL_CODE_FILE,
					Resources.AOL_IDIOM_FILE, Resources.ASPECTJ_FILE,
					Resources.CPP_FILE, Resources.ECLIPSE_JDT_PROJECT,
					Resources.JAVA_CLASS_FILE, Resources.JAVA_ARCHIVE_FILE,
					Resources.JAVA_SOURCE_FILE, Resources.MSE_FILE,
					Resources.PTIDEJ_FILE }, Resources.FILE, Resources.ADD);
		this.dropdown.addToToolBar(this);

		this.addSeparator();

		this.addToolbarButton(Resources.SAVE_ALL, Resources.FILE, false);
		this.addToolbarButton(Resources.SAVE_ACTIVE, Resources.FILE, false);
		this.addToolbarButton(Resources.PRINT, Resources.FILE, false);
	}
	private void addAction(
		final AbstractButton ab,
		final String strKey,
		final String strGroup) {

		final AbstractAction action =
			this.actionsRepository.getAction(strKey, strGroup);
		ab.addActionListener(action);
	}
	private void addToolbarButton(
		final String strKey,
		final String strGroupName,
		boolean isEnabled) {

		final JButton cmd = new JButton();
		cmd.setToolTipText(Resources.getButtonText(strKey, ToolBar.class));
		cmd.setIcon(ptidej.ui.Utils.getIcon(Resources.getButtonIconPath(
			strKey,
			ToolBar.class)));
		cmd.setMargin(new Insets(0, 0, 0, 0));
		cmd.setRolloverEnabled(true);
		cmd.setActionCommand(strKey);
		cmd.setEnabled(isEnabled);
		this.addAction(cmd, strKey, strGroupName);
		this.add(cmd);
	}
}
