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
package jct.test.rsc.snpsht.parser.revml.sax.fsm.contentloader;

import jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.AbstractRevMLState;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState;
import jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.DefaultUnknowMakerStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class LoadInfoRevisionsScanState extends AbstractRevMLState {

	public LoadInfoRevisionsScanState(
		SimpleSaxFsmParser fsm,
		AbstractStateSaxFsm previewState,
		VerFsManager manager) {

		super(fsm, previewState, manager);
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
	}

	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {

		if (RevMLDocCommonsStrings.REVML_DOCUMENT_MARKER.compareTo(name
			.trim()
			.toLowerCase()) == 0) {

			getFsm().changeState(
				new LoadInfoEndState(getFsm(), this, getManager()));
		} else {
			getFsm().changeState(
				new ErrorStateRevMLState(
					getFsm(),
					this,
					getManager(),
					"Misformed xml document.\nEnd marker unexpected.\nReceived: "
							+ name + "\nExpected: "
							+ RevMLDocCommonsStrings.REVML_DOCUMENT_MARKER,
					ErrorStateRevMLState.MISFORMED_XML_DOCUMENT));
		}

	}

	@Override
	public void startElement(
		String uri,
		String localName,
		String name,
		Attributes attributes) throws SAXException {
		String fileId;
		VerFsFileRev file;

		if (name.trim().toLowerCase().compareTo(
			RevMLDocCommonsStrings.FILE_REV_MARKER) == 0) {

			if (attributes != null) {
				for (int i = 0; i < attributes.getLength(); i++) {
					String aName = attributes.getLocalName(i);

					if (RevMLDocCommonsStrings.REVML_VERSION_ID_ATTRIBUTE
						.equals(aName.toLowerCase().trim())) {
						fileId = attributes.getValue(i);

						file = getManager().getSimpleRevision(fileId);

						if (file == null) {
							getFsm()
								.changeState(
									new ErrorStateRevMLState(
										getFsm(),
										this,
										getManager(),
										"Can't find revision file in manager.",
										ErrorStateRevMLState.MISFORMED_REVML_DOCUMENT));
						} else {
							getFsm().changeState(
								new LoadInfoRevFileScanState(
									getFsm(),
									this,
									getManager(),
									file));
						}

						return;
					}
				}
			}

			getFsm().changeState(
				new ErrorStateRevMLState(
					getFsm(),
					this,
					getManager(),
					"File revision as no ID.",
					ErrorStateRevMLState.MISFORMED_REVML_DOCUMENT));

		} else {
			getFsm().changeState(
				new DefaultUnknowMakerStateSaxFsm(
					getFsm(),
					this,
					this,
					attributes,
					localName,
					name,
					uri));
		}
	}

	@Override
	public void endDocument() throws SAXException {
	}

	@Override
	public String toString() {
		String toRet = "<LoadInfoRevisionsScanState>";
		return toRet;
	}
}
