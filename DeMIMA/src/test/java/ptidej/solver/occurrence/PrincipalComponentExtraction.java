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
package ptidej.solver.occurrence;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import util.xml.DOMVisitor;
import util.xml.DOMVisitorAdapter;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/0/07
 */
public class PrincipalComponentExtraction {
	public static void main(final String[] args) {
		final PrincipalComponentExtraction extractor =
			new PrincipalComponentExtraction();
		PrincipalComponents.print(extractor.xmlExtraction(
			"../SAD Tests/rsc/Antipatterns in Xerces v2.7.0.xml",
			new char[][] { "blob".toCharArray() }));
	}
	public ReducedOccurrence[] xmlExtraction(
		final String anXMLFilePath,
		final char[][] somePrincipalComponents) {

		try {
			final DocumentBuilderFactory factory =
				DocumentBuilderFactory.newInstance();
			final DocumentBuilder builder = factory.newDocumentBuilder();
			final Document document = builder.parse(new File(anXMLFilePath));

			final SortedSet principalComponents = new TreeSet();
			new DOMVisitorAdapter(document).accept(new DOMVisitor() {
				public void open(final Document aDocument) {
				}
				public void close(final Document aDocument) {
				}
				public void open(final Node aNode) {
					for (int i = 0; i < somePrincipalComponents.length; i++) {
						final char[] name = somePrincipalComponents[i];
						if (Arrays.equals(
							aNode.getNodeName().toCharArray(),
							name)) {
							principalComponents.add(aNode
								.getFirstChild()
								.getFirstChild()
								.getNodeValue());
						}
					}
				}
				public void close(final Node aNode) {
				}
			});

			final ReducedOccurrence[] sortedPrincipalComponents =
				new ReducedOccurrence[principalComponents.size()];
			principalComponents.toArray(sortedPrincipalComponents);
			return sortedPrincipalComponents;
		}
		catch (final ParserConfigurationException e) {
			e.printStackTrace();
		}
		catch (final SAXException e) {
			e.printStackTrace();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}

		return new ReducedOccurrence[0];
	}
}
