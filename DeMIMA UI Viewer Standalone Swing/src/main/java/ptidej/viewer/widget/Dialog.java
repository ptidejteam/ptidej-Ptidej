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
import java.awt.Frame;
import java.awt.HeadlessException;
import javax.swing.JDialog;

//import ptidej.swing.custom.panels.PtidejPanel;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class Dialog extends JDialog {
	private static final long serialVersionUID = 1L;

	private int width, height;
	private Frame owner;

	public Dialog(
		Frame owner,
		String title,
		boolean modal,
		int width,
		int height) throws HeadlessException {
		super(owner, title, modal);
		this.width = width;
		this.height = height;
		this.owner = owner;
		this.init();
	}
	private void init() {
		this.getContentPane().setLayout(new BorderLayout());
		this.setResizable(false);
		this.setSize(this.width, this.height);
		this.setLocationRelativeTo(this.owner);
	}
}
