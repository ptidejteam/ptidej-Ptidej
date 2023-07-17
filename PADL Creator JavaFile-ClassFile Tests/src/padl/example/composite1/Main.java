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
package padl.example.composite1;

/**
 * @author  Yann-Gaël Guéhéneuc
 * @version 0.1
 * 
 * One use relationship with String through the
 * parameter of method main().
 * 
 * One association relationship with Document thought the
 * local variable declaration final Document document and
 * the call to method addComponent() on variable document
 * of type Document.
 * 
 * TODO: Remove the creation of an association relationship
 * with Element thourgh the use of method addComponent().
 * 
 * Six creation relationships with Document, Title,
 * Paragraph, IndentedParagraph.
 */
public class Main {
	public static void main(final String[] args) {
		final Document document = new Document();
		document.addComponent(new Title());
		document.addComponent(new Paragraph());
		document.addComponent(new Title());
		document.addComponent(new IndentedParagraph());
		document.addComponent(new IndentedParagraph());

		document.printAll();
	}
}
