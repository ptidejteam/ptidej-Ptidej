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
package util.awt;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import util.multilingual.MultilingualManager;

public class NameDialog extends Dialog {
	private static final long serialVersionUID = 3685715927255933454L;
	private static final String BUTTON_LABEL = MultilingualManager.getString(
		"BUTTON_LABEL",
		NameDialog.class);
	private final TextField textField;

	public NameDialog(final Frame owner, final String question) {
		super(
			owner,
			MultilingualManager.getString("TITLE", NameDialog.class),
			true);

		new WindowCloser(this);

		this.textField = new TextField("");
		final Label label = new Label(question);
		final Button button = new Button(NameDialog.BUTTON_LABEL);

		button.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				NameDialog.this.dispose();
			}
		});

		this.textField.addKeyListener(new KeyListener() {
			public void keyPressed(final KeyEvent e) {
			}
			public void keyReleased(final KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					button.dispatchEvent(new ActionEvent(
						NameDialog.this.textField,
						ActionEvent.ACTION_PERFORMED,
						NameDialog.BUTTON_LABEL));
				}
				else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					NameDialog.this.textField.setText("");
					button.dispatchEvent(new ActionEvent(
						NameDialog.this.textField,
						ActionEvent.ACTION_PERFORMED,
						NameDialog.BUTTON_LABEL));
				}
			}
			public void keyTyped(final KeyEvent e) {
			}
		});

		this.setLayout(new BorderLayout());
		this.add(label, BorderLayout.NORTH);
		this.add(this.textField, BorderLayout.CENTER);
		this.add(button, BorderLayout.SOUTH);
		this.pack();

		final Dimension screenDimension =
			Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(
			(screenDimension.width - this.getWidth()) / 2,
			(screenDimension.height - this.getHeight()) / 2);

		this.setVisible(true);
	}
	public String getName() {
		return this.textField.getText().trim();
	}
}
