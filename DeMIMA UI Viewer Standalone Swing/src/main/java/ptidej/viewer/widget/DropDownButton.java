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

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import ptidej.viewer.action.ActionsRepository;
import ptidej.viewer.utils.Resources;

// OriginalVersion  santhosh kumar - santhosh@in.fiorano.com

public class DropDownButton extends JButton implements ChangeListener,
		PopupMenuListener, ActionListener, PropertyChangeListener {

	private static final long serialVersionUID = 1L;
	private ActionsRepository actions = ActionsRepository.getInstance();
	private JPopupMenu popup;
	private final JButton cmd;
	private final JButton cmdArrow;
	private boolean popupIsVisible = false;

	public DropDownButton(
		final int defaultActionIndex,
		final String[] keys,
		final String strGroup) {
		this.popup = new JPopupMenu();
		this.cmd = this;
		this.cmd.putClientProperty("hideActionText", Boolean.TRUE);
		this.cmd.setMargin(new Insets(0, 0, 0, 0));
		this.cmd.addChangeListener(this);
		this.cmd.addPropertyChangeListener("enabled", this); //NOI18N

		this.cmdArrow =
			new JButton(ptidej.ui.Utils.getIcon(Resources.getButtonIconPath(
				Resources.DROP_DOWN,
				DropDownButton.class)));
		this.cmdArrow.setSize(this.cmd.getWidth(), this.cmd.getHeight());
		this.cmdArrow.addChangeListener(this);
		this.cmdArrow.addActionListener(this);
		this.cmdArrow.setMargin(new Insets(0, 0, 0, 0));

		this.setDefaultAction(keys[defaultActionIndex], strGroup);

		for (int i = 0; i < keys.length; i++) {
			this.addAction(keys[i], strGroup);
		}
	}
	public DropDownButton(
		final int defaultActionIndex,
		final String[] keys,
		final String strGroup,
		String strIconKey) {
		this(defaultActionIndex, keys, strGroup);
		this.cmd.setIcon(ptidej.ui.Utils.getIcon(Resources.getButtonIconPath(
			strIconKey,
			DropDownButton.class)));
	}
	public void propertyChange(final PropertyChangeEvent evt) {
		this.cmdArrow.setEnabled(this.cmd.isEnabled());
	}
	public void stateChanged(final ChangeEvent e) {
		if (e.getSource() == this.cmd) {
			if (this.popupIsVisible && !this.cmd.getModel().isRollover()) {
				this.cmd.getModel().setRollover(true);
				return;
			}
			this.cmdArrow.getModel().setRollover(
				this.cmd.getModel().isRollover());
			this.cmdArrow.setSelected(this.cmd.getModel().isArmed()
					&& this.cmd.getModel().isPressed());
		}
		else {
			if (this.popupIsVisible && !this.cmdArrow.getModel().isSelected()) {
				this.cmdArrow.getModel().setSelected(true);
				return;
			}
			this.cmd.getModel().setRollover(
				this.cmdArrow.getModel().isRollover());
		}
	}
	public void actionPerformed(final ActionEvent ae) {
		this.popup.addPopupMenuListener(this);
		this.popup.show(this.cmd, 0, this.cmd.getHeight());
	}
	public void popupMenuWillBecomeVisible(final PopupMenuEvent e) {
		this.popupIsVisible = true;
		this.cmd.getModel().setRollover(true);
		this.cmdArrow.getModel().setSelected(true);
	}
	public void popupMenuWillBecomeInvisible(final PopupMenuEvent e) {
		this.popupIsVisible = false;
		this.cmd.getModel().setRollover(false);
		this.cmdArrow.getModel().setSelected(false);
		((JPopupMenu) e.getSource()).removePopupMenuListener(this);
	}
	public void popupMenuCanceled(final PopupMenuEvent e) {
		this.popupIsVisible = false;
	}
	public JPopupMenu getPopupMenu() {
		return this.popup;
	}
	public void addAction(final String strKey, final String strGroup) {
		final JMenuItem item =
			new JMenuItem(Resources.getButtonText(strKey, DropDownButton.class));
		final AbstractAction action = this.actions.getAction(strKey, strGroup);
		item.setActionCommand(strKey);
		item.addActionListener(action);
		// action.register(item);
		this.popup.add(item);
	}
	public void setDefaultAction(final String strKey, final String strGroup) {

		final AbstractAction action = this.actions.getAction(strKey, strGroup);
		this.cmd.setActionCommand(strKey);
		this.cmd.addActionListener(action);
	}
	public JButton addToToolBar(final JToolBar toolbar) {
		toolbar.add(this.cmd);
		toolbar.add(this.cmdArrow);
		return this.cmd;
	}
}
