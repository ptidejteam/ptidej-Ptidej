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
package util.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/04/21
 */
public class DOMVisitorAdapter {
	private final Document document;

	public DOMVisitorAdapter(final Document document) {
		this.document = document;
	}
	public void accept(final DOMVisitor aDOMVisitor) {
		aDOMVisitor.open(this.document);
		this.accept(this.document, aDOMVisitor);
		aDOMVisitor.close(this.document);
	}
	private void accept(final Node aNode, final DOMVisitor aDOMVisitor) {
		aDOMVisitor.open(aNode);

		final NodeList nodeList = aNode.getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++) {
			final Node node = nodeList.item(i);
			this.accept(node, aDOMVisitor);
		}

		aDOMVisitor.close(aNode);
	}
}
