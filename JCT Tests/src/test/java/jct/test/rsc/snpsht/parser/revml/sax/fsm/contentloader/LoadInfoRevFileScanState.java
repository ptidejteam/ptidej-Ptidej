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

public class LoadInfoRevFileScanState extends AbstractRevMLState {
	private String fileId;
	private VerFsFileRev file;

	public LoadInfoRevFileScanState(
		SimpleSaxFsmParser fsm,
		AbstractStateSaxFsm previewState,
		VerFsManager manager,
		VerFsFileRev file) {
		super(fsm, previewState, manager);

		this.file = file;
	}

	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		if (name.trim().toLowerCase().compareTo(
			RevMLDocCommonsStrings.FILE_REV_MARKER) == 0) {

			getFsm().changeState(getPreviewState());
		} else {
			getFsm().changeState(
				new ErrorStateRevMLState(
					getFsm(),
					this,
					getManager(),
					"Misformed xml document.\nEnd marker unexpected.\nReceived: "
							+ name + "\nExpected: "
							+ RevMLDocCommonsStrings.FILE_REV_MARKER,
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
			RevMLDocCommonsStrings.FILE_REV_COMMENT_MARKER) == 0) {

			getFsm().changeState(
				new LoadCommentState(getFsm(), this, getManager(), this.file));
		} else if (name.trim().toLowerCase().compareTo(
			RevMLDocCommonsStrings.FILE_REV_ACTION_MARKER) == 0) {

			getFsm().changeState(
				new LoadActionState(getFsm(), this, getManager(), this.file));
		} else if (name.trim().toLowerCase().compareTo(
			RevMLDocCommonsStrings.FILE_REV_AUTHOR_MARKER) == 0) {

			getFsm().changeState(
				new LoadAuthorState(getFsm(), this, getManager(), this.file));
		} else if (name.trim().toLowerCase().compareTo(
			RevMLDocCommonsStrings.FILE_REV_BRANCH_MARKER) == 0) {

			getFsm().changeState(
				new LoadBranchState(getFsm(), this, getManager(), this.file));
		} else if (name.trim().toLowerCase().compareTo(
			RevMLDocCommonsStrings.FILE_REV_FILE_NAME_MARKER) == 0) {

			getFsm().changeState(
				new LoadFileNameState(getFsm(), this, getManager(), this.file));
		} else if (name.trim().toLowerCase().compareTo(
			RevMLDocCommonsStrings.FILE_REV_REV_ID_MARKER) == 0) {

			getFsm().changeState(
				new LoadRevIDState(getFsm(), this, getManager(), this.file));
		} else if (name.trim().toLowerCase().compareTo(
			RevMLDocCommonsStrings.FILE_REV_CONTENT_MARKER) == 0) {

			getFsm().changeState(
				new LoadContentState(getFsm(), this, getManager(), this.file));
			getFsm().getState().startElement(uri, localName, name, attributes);
		} else if (name.trim().toLowerCase().compareTo(
			RevMLDocCommonsStrings.FILE_REV_DELTA_MARKER) == 0) {

			getFsm().changeState(
				new LoadDeltaState(getFsm(), this, getManager(), this.file));
			getFsm().getState().startElement(uri, localName, name, attributes);
		} else if (name.trim().toLowerCase().compareTo(
			RevMLDocCommonsStrings.FILE_REV_UPDATE_TIME_MARKER) == 0) {

			getFsm()
				.changeState(
					new LoadUpdateTimeState(
						getFsm(),
						this,
						getManager(),
						this.file));
		} else if (name.trim().toLowerCase().compareTo(
			RevMLDocCommonsStrings.FILE_REV_TAG_MARKER) == 0) {

			getFsm().changeState(
				new LoadTagState(getFsm(), this, getManager(), this.file));
		} else if (name.trim().toLowerCase().compareTo(
			RevMLDocCommonsStrings.FILE_REV_PREV_VERSION_MARKER) == 0) {

			getFsm().changeState(
				new LoadPrevVersionState(
					getFsm(),
					this,
					getManager(),
					this.file));
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
		String toRet = "<LoadInfoRevFileScanState>\nFile ID: " + this.fileId;
		return toRet;
	}

}
