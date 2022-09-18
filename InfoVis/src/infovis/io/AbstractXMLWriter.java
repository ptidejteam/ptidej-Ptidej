/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

package infovis.io;

import infovis.Column;
import infovis.Table;

import java.io.Writer;
import java.util.ArrayList;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.megginson.sax.XMLWriter;

/**
 * Abstract writer for XML format.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public abstract class AbstractXMLWriter extends AbstractTableWriter {
    static char[] LF = {'\n', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
    protected int depth;
    protected XMLWriter writer;
    protected boolean identing = true;
	
    /**
     * Constructor for an AbstractXMLWriter
     *
     * @param out the Writer
     * @param table the Table.
     */
    protected AbstractXMLWriter(Writer out, Table table) {
	super(out, table);
	this.writer = new XMLWriter(out);
    }

    /**
     * Writes the attributes associated with a row to a specified XMLWriter.
     *
     * @param row the row.
     * @param writes the XMLWriter.
     *
     * @exception SAXException passed from the XMLWriter
     */
    protected void writeAttributes(int row, XMLWriter writer)
	throws SAXException {
	ArrayList labels = getColumnLabels();
	AttributesImpl atts = new AttributesImpl();
	atts.addAttribute("", "name", "", "CDATA", "");
	atts.addAttribute("", "value", "", "CDATA", "");

	this.depth++;
	for (int col = 0; col < labels.size(); col++) {
	    String label = getColumnLabelAt(col);
	    Column c = this.table.getColumn(label);
	    if (! c.isValueUndefined(row)) {
		atts.setValue(0, label);
		atts.setValue(1, c.getValueAt(row));
		indent(writer);
		writer.emptyElement("", "attribute", "", atts);
	    }
	}
	this.depth--;
    }

    public void indent(XMLWriter writer) {
	if (! isIdenting())
	    return;
	int len = Math.min(this.depth+1, LF.length);
	try {
	    writer.ignorableWhitespace(LF, 0, len);
	}	
	catch(SAXException e) {
	}
    }

    /**
     * Returns the identing.
     * @return boolean
     */
    public boolean isIdenting() {
	return this.identing;
    }

    /**
     * Sets the identing.
     * @param identing The identing to set
     */
    public void setIdenting(boolean identing) {
	this.identing = identing;
    }

}
