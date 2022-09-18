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
package ptidej.example.chainOfResponsibility1;

public class HelpHandler {
	public static int NO_HELP_TOPIC = -1;
	public static int WHAT_IS_HELP_TOPIC = 0;

	private HelpHandler successor = null;
	private int topic = HelpHandler.NO_HELP_TOPIC;
	public HelpHandler(final HelpHandler successor, final int topic) {
		this.successor = successor;
		this.topic = topic;
	}
	public void handleHelp() {
		if (this.successor != null) {
			this.successor.handleHelp();
		}
	}
	public boolean hasHelp() {
		return this.topic != HelpHandler.NO_HELP_TOPIC;
	}
	public void setHandler(final HelpHandler handler, final int topic) {
		this.successor = handler;
		this.topic = topic;
	}
}
