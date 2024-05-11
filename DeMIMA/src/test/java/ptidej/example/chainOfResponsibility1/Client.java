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

public class Client {
	public static void main(final String[] args) {

		// Declaration.

		Document document;
		Title title;
		Paragraph paragraph;
		IndentedParagraph indentedParagraph;

		// Construction.

		document = new Document();

		title = new Title(document, HelpHandler.WHAT_IS_HELP_TOPIC);
		document.addElement(title);
		paragraph = new Paragraph(title, HelpHandler.WHAT_IS_HELP_TOPIC);
		document.addElement(paragraph);

		title = new Title(document, HelpHandler.WHAT_IS_HELP_TOPIC);
		document.addElement(title);
		paragraph = new Paragraph(document, HelpHandler.WHAT_IS_HELP_TOPIC);
		document.addElement(paragraph);
		indentedParagraph =
			new IndentedParagraph(paragraph, HelpHandler.WHAT_IS_HELP_TOPIC);
		document.addElement(indentedParagraph);
		indentedParagraph =
			new IndentedParagraph(paragraph, HelpHandler.WHAT_IS_HELP_TOPIC);
		document.addElement(indentedParagraph);

		// Use.

		System.out
			.println("-- HELP -----------------------------------------------");
		indentedParagraph.handleHelp();
		title.handleHelp();
		document.handleHelp();
		System.out
			.println("-- PRINT ----------------------------------------------");
		document.printAll();
	}
}
