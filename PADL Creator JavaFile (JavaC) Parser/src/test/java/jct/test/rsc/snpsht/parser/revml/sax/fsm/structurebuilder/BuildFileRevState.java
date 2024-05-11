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
package jct.test.rsc.snpsht.parser.revml.sax.fsm.structurebuilder;

import jct.test.rsc.snpsht.parser.revml.sax.fsm.AbstractRevMLState;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState;
import jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.DefaultUnknowMakerStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class BuildFileRevState extends AbstractRevMLState {
	private VerFsFileRev file;
	private String fileId;

	public BuildFileRevState(
		SimpleSaxFsmParser fsm,
		AbstractStateSaxFsm previewState,
		VerFsManager manager,
		String fileId) {
		super(fsm, previewState, manager);

		this.fileId = fileId;
	}

	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		if (name.trim().toLowerCase().compareTo(
			jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_MARKER) == 0) {

			if (this.file == null) {
				getFsm().changeState(
					new ErrorStateRevMLState(
						getFsm(),
						this,
						getManager(),
						"Misformed xml document.\nEnd marker unexpected.\nReceived: "
								+ name + "\nExpected: "
								+ jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_NAME_MARKER,
						ErrorStateRevMLState.MISFORMED_XML_DOCUMENT));
			} else {
				getFsm().changeState(getPreviewState());
			}
		} else {
			getFsm().changeState(
				new ErrorStateRevMLState(
					getFsm(),
					this,
					getManager(),
					"Misformed xml document.\nEnd marker unexpected.\nReceived: "
							+ name + "\nExpected: "
							+ jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_MARKER,
					ErrorStateRevMLState.MISFORMED_XML_DOCUMENT));
		}
	}

	@Override
	public void startElement(
		String uri,
		String localName,
		String name,
		Attributes attributes) throws SAXException {

		if (name.trim().toLowerCase().compareTo(
			jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_NAME_MARKER) == 0) {

			getFsm()
				.changeState(
					new CreateFileRevStateRevMLSaxFsm(
						getFsm(),
						this,
						getManager()));
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
	public String toString() {
		String toRet =
			"<BuildFileRevState>\n File ID: " + this.fileId;
		return toRet;
	}

	private class CreateFileRevStateRevMLSaxFsm extends
			AbstractRevMLState {
		private String name;

		public CreateFileRevStateRevMLSaxFsm(
			SimpleSaxFsmParser fsm,
			AbstractStateSaxFsm previewState,
			VerFsManager manager) {
			super(fsm, previewState, manager);

			this.name = "";
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			this.name += new String(ch, start, length);
		}

		@Override
		public void endElement(String uri, String localName, String name)
				throws SAXException {
			if (name.trim().toLowerCase().compareTo(
				jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_NAME_MARKER) == 0) {

				BuildFileRevState.this.file =
					getManager().addSimpleRevision(
						this.name,
						BuildFileRevState.this.fileId);

				getFsm().changeState(BuildFileRevState.this);
			} else {
				getFsm().changeState(
					new ErrorStateRevMLState(
						getFsm(),
						this,
						getManager(),
						"Misformed xml document.\nEnd marker unexpected.\nReceived: "
								+ name + "\nExpected: "
								+ jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_MARKER,
						ErrorStateRevMLState.MISFORMED_XML_DOCUMENT));
			}
		}

		@Override
		public String toString() {
			String toRet =
				"<CreateFileRevStateRevMLSaxFsm>\n File name: " + this.name;
			return toRet;
		}

	}
}
