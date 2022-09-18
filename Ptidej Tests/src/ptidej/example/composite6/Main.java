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
package ptidej.example.composite6;

public class Main {
	public static void main(final String[] args) {
		final Document document = new Document();

		document.addElement(new Title("Introduction"));
		document.addElement(new Paragraph("We present a new approach..."));
		document.addElement(new Title("Motivations"));
		document.addElement(new Paragraph("Maintainance is important..."));
		document.addElement(new IndentedParagraph(
			"...50% of overall devleopment cost..."));
		document.addElement(new IndentedParagraph(
			"...understanding ... 75% of maintainance..."));
		document.addElement(new Title("Our approach"));
		document.addElement(new Paragraph("..."));

		document.printAll();
	}
}
