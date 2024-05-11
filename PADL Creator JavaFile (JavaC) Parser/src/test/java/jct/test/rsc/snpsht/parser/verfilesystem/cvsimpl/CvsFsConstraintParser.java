/*
 * (c) Copyright 2008 and following years, Julien Tanteri, University of
 * Montreal.
 * 
 * Use and copying of this software and preparation of derivative works based
 * upon this software are permitted. Any copy of this software or of any
 * derivative work must include the above copyright notice of the author, this
 * paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS ALL
 * WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE, AND NOT
 * WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN, ANY LIABILITY FOR DAMAGES
 * RESULTING FROM THE SOFTWARE OR ITS USE IS EXPRESSLY DISCLAIMED, WHETHER
 * ARISING IN CONTRACT, TORT (INCLUDING NEGLIGENCE) OR STRICT LIABILITY, EVEN IF
 * THE AUTHOR IS ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
package jct.test.rsc.snpsht.parser.verfilesystem.cvsimpl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import jct.test.rsc.snpsht.parser.verfilesystem.AbstractVerFsParser;
import jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException;
import jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings;
import jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CvsFsConstraintParser extends AbstractVerFsParser {
	protected CvsFsManager manager;

	/** DOM Generic Parser */
	private DocumentBuilder domBranchIndex;

	@Override
	public VerFsManager parse(VerFsManager manager) throws VerFsParserException {
		// Parser commit constraints
		String constrType, attrType, value;
		List<String[]> attrs = new ArrayList<String[]>();
		String[] typeValue;
		Node constraint, attribute;
		NodeList constraints, attributes;

		try {
			XPath xpath = XPathFactory.newInstance().newXPath();

			// Check if manager is a CvsFsManager
			try {
				this.manager = (CvsFsManager) manager;
			} catch (Exception e) {
				throw new VerFsParserException(
					VerFsParserException.ILLEGAL_MANAGER_TYPE,
					"Manger must be a CvsFsManager but is "
							+ manager.getClass().getName());
			}

			// Create parsers with schemas
			if (this.domBranchIndex == null) {
				this.domBranchIndex =
					getParser("/jct.test.rsc.snpsht.verfilesystem/resource/repo_general_info.xsd");
			}

			// Get DOM for files index XML file
			Document generalInfo =
				getDocument(
					manager,
					VerFsCommonStrings.GENERAL_INFO_XML_FILE_NAME,
					this.domBranchIndex);

			constraints =
				(NodeList) xpath
					.evaluate(
						"/"
								+ VerFsCommonStrings.GENERAL_INFO_MAKER
								+ "/"
								+ CvsFsCommonStrings.CVS_COMMIT_GROUP_CONSTRAINTS_MARKER
								+ "/"
								+ CvsFsCommonStrings.CVS_COMMIT_GROUP_CONSTRAINT_MARKER,
						generalInfo,
						XPathConstants.NODESET);

			// For each constraint
			for (int i = 0; i < constraints.getLength(); i++) {
				constraint = constraints.item(i);

				constrType =
					VerFsCommonStrings.XML2ASCII(xpath.evaluate(
						CvsFsCommonStrings.CVS_COMMIT_GROUP_CONSTR_TYPE_MARKER,
						constraint));

				attributes =
					(NodeList) xpath
						.evaluate(
							CvsFsCommonStrings.CVS_COMMIT_GROUP_CONSTR_ATTRS_MARKER
									+ "/"
									+ CvsFsCommonStrings.CVS_COMMIT_GROUP_CONSTR_ATTR_MARKER,
							constraint,
							XPathConstants.NODESET);

				for (int j = 0; j < attributes.getLength(); j++) {
					attribute = attributes.item(j);

					attrType =
						VerFsCommonStrings
							.XML2ASCII(attribute
								.getAttributes()
								.getNamedItem(
									CvsFsCommonStrings.CVS_COMMIT_GROUP_CONSTR_ATTR_TYPE_ATTR)
								.getNodeValue());

					value =
						VerFsCommonStrings.XML2ASCII(attribute
							.getFirstChild()
							.getNodeValue());

					typeValue = new String[] { attrType, value };
					attrs.add(typeValue);
				}

				this.manager.addConstraint(constrType, attrs
					.toArray(new String[0][]));
			}

			return this.manager;
		} catch (XPathExpressionException e) {
			throw new VerFsParserException(VerFsParserException.BAD_XPATH, e);
		}
	}
}
