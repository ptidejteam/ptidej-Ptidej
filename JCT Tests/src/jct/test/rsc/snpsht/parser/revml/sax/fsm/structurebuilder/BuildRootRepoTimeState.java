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

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.AbstractRevMLState;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState;
import jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;

import org.xml.sax.SAXException;

public class BuildRootRepoTimeState extends AbstractRevMLState {
	private String dateString;
	private Date date;

	public BuildRootRepoTimeState(
		SimpleSaxFsmParser fsm,
		AbstractStateSaxFsm previewState,
		VerFsManager manager) {

		super(fsm, previewState, manager);

		this.dateString = "";
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		this.dateString += new String(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		if (RevMLDocCommonsStrings.ROOT_REPOSITORY_TIME_MARKER.compareTo(name
			.trim()
			.toLowerCase()) == 0) {

			try {
				this.date =
					DateFormat.getDateTimeInstance().parse(this.dateString);

				getManager().setUpdateDate(this.date);

				getFsm().changeState(getPreviewState());
			} catch (ParseException e) {

				getFsm().changeState(
					new ErrorStateRevMLState(
						getFsm(),
						this,
						getManager(),
						"Can't parse received date.\nReceived date: "
								+ this.dateString,
						ErrorStateRevMLState.MISFORMED_DATE_FORMAT));
			}

		} else {
			getFsm()
				.changeState(
					new ErrorStateRevMLState(
						getFsm(),
						this,
						getManager(),
						"Misformed xml document.\nEnd marker unexpected.\nReceived: "
								+ name
								+ "\nExpected: "
								+ RevMLDocCommonsStrings.ROOT_REPOSITORY_TIME_MARKER,
						ErrorStateRevMLState.MISFORMED_XML_DOCUMENT));
		}
	}

	@Override
	public String toString() {
		String toRet =
			"<BuildRootRepoTimeState>\nDate: " + this.dateString;
		return toRet;
	}

}
