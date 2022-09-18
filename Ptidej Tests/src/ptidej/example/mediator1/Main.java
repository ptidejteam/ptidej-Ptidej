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
package ptidej.example.mediator1;

public class Main {
	public static void main(final String[] args) {
		final ViewManager viewManager = new ViewManager();
		final Button button1 = new Button(viewManager);
		final Button button2 = new Button(viewManager);
		final ListBox listBox = new ListBox(viewManager);
		final TextPane textPane = new TextPane(viewManager);

		viewManager.add(button1);
		viewManager.add(button2);
		viewManager.add(listBox);
		viewManager.add(textPane);
	}
}
