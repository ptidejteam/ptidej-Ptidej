package fr.emn.oadymppac.widgets;

import javax.swing.tree.TreeModel;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import fr.emn.oadymppac.graph.clustering.MatrixClusterizer;
import fr.emn.oadymppac.tree.StateNode;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 *
 * This class handles documents where clusterings are stored.
 */
public class ClusteringHandler extends DefaultHandler {

	MatrixClusterizer clusterizer;
	StateNode currentNode;
	TreeModel currentTree;

	public ClusteringHandler(final MatrixClusterizer c) {
		this.clusterizer = c;
	}

	/**
	 * @see org.xml.sax.ContentHandler#characters(char[], int, int)
	 */
	public void characters(final char[] ch, final int start, final int length)
			throws SAXException {
	}

	/**
	 * @see org.xml.sax.ContentHandler#endDocument()
	 */
	public void endDocument() throws SAXException {
	}

	/**
	 * @see org.xml.sax.ContentHandler#endElement(String, String, String)
	 */
	public void endElement(
		final String namespaceURI,
		final String localName,
		final String qName) throws SAXException {
		if (qName.equals("node")) {
			this.currentNode = (StateNode) this.currentNode.getParent();
		}
		if (qName.equals("tree")) {
			this.currentNode = null;
		}
	}

	/**
	 * @see org.xml.sax.ContentHandler#endPrefixMapping(String)
	 */
	public void endPrefixMapping(final String prefix) throws SAXException {
	}

	/**
	 * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
	 */
	public void ignorableWhitespace(
		final char[] ch,
		final int start,
		final int length) throws SAXException {
	}

	/**
	 * @see org.xml.sax.ContentHandler#processingInstruction(String, String)
	 */
	public void processingInstruction(final String target, final String data)
			throws SAXException {
	}

	/**
	 * @see org.xml.sax.ContentHandler#setDocumentLocator(Locator)
	 */
	public void setDocumentLocator(final Locator locator) {
	}

	/**
	 * @see org.xml.sax.ContentHandler#skippedEntity(String)
	 */
	public void skippedEntity(final String name) throws SAXException {
	}

	/**
	 * @see org.xml.sax.ContentHandler#startDocument()
	 */
	public void startDocument() throws SAXException {
	}

	/**
	 * @see org.xml.sax.ContentHandler#startElement(String, String, String, Attributes)
	 */
	public void startElement(
		final String namespaceURI,
		final String localName,
		final String qName,
		final Attributes atts) throws SAXException {
		// Here we check whether the loaded clustering
		// has the same size as the matrix. In case, the size
		// differs, the clustering cannot be applied to the matrix
		// at hand.
		if (qName.equals("tree")) {
			final int size =
				atts.getValue("size") != null ? Integer.parseInt(atts
					.getValue("size")) : -1;
			if (atts.getValue("orientation").equals("horizontal")) {
				if (size != this.clusterizer.getColumnCount()) {
					throw new SAXException("Column count mismatch.");
				}
				System.out.println("updating horizontal tree");
				this.currentTree = this.clusterizer.getHorizontalTreeModel();
				((StateNode) this.currentTree.getRoot()).removeAllChildren();
			}
			else if (atts.getValue("orientation").equals("vertical")) {
				if (size != this.clusterizer.getRowCount()) {
					throw new SAXException("Row count mismatch.");
				}
				System.out.println("updating vertical tree");
				this.currentTree = this.clusterizer.getVerticalTreeModel();
				((StateNode) this.currentTree.getRoot()).removeAllChildren();
			}
		}
		if (qName.equals("node")) {
			final String name = atts.getValue("name");
			if (name == null) {
				throw new SAXException("missing node name");
			}
			if (this.currentNode == null) {
				this.currentNode = (StateNode) this.currentTree.getRoot();
			}
			else {
				System.out.println("adding " + name + " to "
						+ this.currentNode.getUserObject());
				final StateNode child = new StateNode(name);
				this.currentNode.add(child);
				this.currentNode = child;
			}
		}
	}

	/**
	 * @see org.xml.sax.ContentHandler#startPrefixMapping(String, String)
	 */
	public void startPrefixMapping(final String prefix, final String uri)
			throws SAXException {
	}
}
