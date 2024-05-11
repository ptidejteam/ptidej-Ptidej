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

public class IndentedParagraph extends Paragraph {
	public IndentedParagraph(final Element parent, final int topic) {
		super(parent, topic);
	}
	public void handleHelp() {
		if (this.hasHelp()) {
			System.out.println("Display IndentedParagraph help here!");
		}
		else {
			((HelpHandler) this).handleHelp();
		}
	}
}
