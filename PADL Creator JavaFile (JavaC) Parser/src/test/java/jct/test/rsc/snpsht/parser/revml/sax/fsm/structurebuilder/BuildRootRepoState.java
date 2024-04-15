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

import jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.AbstractRevMLState;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState;
import jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import jct.test.rsc.snpsht.verfilesystem.VerFsRepository;

import org.xml.sax.SAXException;

public class BuildRootRepoState extends AbstractRevMLState {

	private String rootRepository;

	public VerFsRepository root;

	public BuildRootRepoState(
		SimpleSaxFsmParser fsm,
		AbstractStateSaxFsm previewState,
		VerFsManager manager) {

		super(fsm, previewState, manager);

		this.rootRepository = "";
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		this.rootRepository += new String(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		if (RevMLDocCommonsStrings.ROOT_REPOSITORY_MARKER.compareTo(name
			.trim()
			.toLowerCase()) == 0) {

			this.root = getManager().makeRoot(this.rootRepository);

			getFsm().changeState(getPreviewState());

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
								+ RevMLDocCommonsStrings.ROOT_REPOSITORY_MARKER,
						ErrorStateRevMLState.MISFORMED_XML_DOCUMENT));
		}
	}

	@Override
	public String toString() {
		String toRet =
			"<BuildRootRepoState>\nRoot repository: " + this.rootRepository;
		return toRet;
	}

}
