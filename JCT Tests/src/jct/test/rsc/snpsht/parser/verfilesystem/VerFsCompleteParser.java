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
package jct.test.rsc.snpsht.parser.verfilesystem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import jct.test.rsc.snpsht.filesystem.FsManagerFactory;
import jct.test.rsc.snpsht.filesystem.IFsFileEntity;
import jct.test.rsc.snpsht.filesystem.IFsManager;
import jct.test.rsc.snpsht.filesystem.IFsNullEntity;
import jct.test.rsc.snpsht.filesystem.IFsRealEntity;
import jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings;
import jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager;

import org.w3c.dom.Document;

public class VerFsCompleteParser extends AbstractVerFsParser {

	/** DOM Generic Parser */
	private DocumentBuilder domGeneralInfoIndex;

	private List<IVerFsParser> parsers;

	public VerFsCompleteParser() {
		this.parsers = new ArrayList<IVerFsParser>();
	}

	public void addParser(IVerFsParser parser) {
		this.parsers.add(parser);
	}

	public void removeParser(IVerFsParser parser) {
		this.parsers.remove(parser);
	}

	public List<IVerFsParser> getParsers() {
		return this.parsers;
	}

	public VerFsManager parse(File source) throws VerFsParserException {
		IFsFileEntity entity;
		String type;
		IFsManager fsManager;

		try {
			fsManager = FsManagerFactory.getNewManager(source);
		} catch (IOException e1) {
			throw new VerFsParserException(
				VerFsParserException.SOURCE_NOT_FOUND,
				"Can't build a IFsManager with " + source.getAbsolutePath());
		}

		// Check versioned file system type

		entity =
			fsManager
				.getFileEntity(VerFsCommonStrings.GENERAL_INFO_XML_FILE_NAME);
		if (entity == null || entity instanceof IFsNullEntity) {
			throw new VerFsParserException(
				VerFsParserException.SOURCE_NOT_FOUND,
				"Can't find general info XML file. Should be in "
						+ VerFsCommonStrings.GENERAL_INFO_XML_FILE_NAME);
		}

		try {
			// Create jct.test.rsc.snpsht.parser with schemas
			if (this.domGeneralInfoIndex == null) {
				this.domGeneralInfoIndex =
					getParser("/jct.test.rsc.snpsht.verfilesystem/resource/repo_general_info.xsd");
			}

			// Get DOM
			Document generalInfo;
			try {
				generalInfo =
					getDocument(
						fsManager.getInputStrem((IFsRealEntity) entity),
						this.domGeneralInfoIndex);
			} catch (IOException e) {
				throw new VerFsParserException(
					VerFsParserException.SOURCE_NOT_FOUND,
					e.getMessage());
			}

			XPath xpath = XPathFactory.newInstance().newXPath();

			type =
				VerFsCommonStrings.XML2ASCII(xpath.evaluate(
					VerFsCommonStrings.GENERAL_INFO_MAKER + "/"
							+ VerFsCommonStrings.GENERAL_TYPE_MAKER,
					generalInfo));

			// Build manager
			if (type.compareTo(CvsFsCommonStrings.CVS_TYPE_ATTRIBUTE_VALUE) == 0) {
				this.manager = new CvsFsManager();
				try {
					this.manager.setSource(source);
				} catch (IOException e1) {
					throw new VerFsParserException(
						VerFsParserException.SOURCE_NOT_FOUND,
						e1.getMessage());
				}
			} else {
				throw new VerFsParserException(
					VerFsParserException.UNEXPECTED_NODE_VALUE,
					"Can't build a manager for type : " + type);
			}
		} catch (XPathExpressionException e) {
			throw new VerFsParserException(VerFsParserException.BAD_XPATH, e);
		}

		return parse(this.manager);
	}

	@Override
	public VerFsManager parse(VerFsManager manager) throws VerFsParserException {

		for (IVerFsParser parser : this.parsers) {
			this.manager = parser.parse(this.manager);
		}

		return this.manager;
	}
}
