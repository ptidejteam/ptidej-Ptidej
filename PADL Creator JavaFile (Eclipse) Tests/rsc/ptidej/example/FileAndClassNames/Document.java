package ptidej.example.FileAndClassNames;

import java.util.Enumeration;
import java.util.Vector;

public class Document {
	private Vector elements = new Vector();
	public void addComponent(Element e) {
		elements.addElement(e);
	}
	public Element getComponent(int pos) {
		return (Element) elements.elementAt(pos);
	}
	public void printAll() {
		Enumeration e = elements.elements();
		while (e.hasMoreElements()) {
			((AbstractDocument) e.nextElement()).print();
		}
	}
	public Element removeComponent(Element e) {
		return null;
	}
}
