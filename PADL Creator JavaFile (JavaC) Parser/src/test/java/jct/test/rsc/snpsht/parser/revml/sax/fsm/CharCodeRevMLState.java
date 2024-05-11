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
package jct.test.rsc.snpsht.parser.revml.sax.fsm;

import jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings;
import jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * This state allows to handle the particular encoding format of some ASCII
 * characters.<br>
 * This state understand characters witch are in form of <char
 * code="[char code]"/>, where [char code] can be equals, for example, to "0xc3"<br>
 * This class is call if this kind of maker is found, and will generate a
 * characters() method call on the calling state.
 * 
 * @author Julien Tanteri
 * 
 */
public class CharCodeRevMLState extends AbstractRevMLState {

	private Attributes attributes;

	@SuppressWarnings("unused")
	public CharCodeRevMLState(
		SimpleSaxFsmParser fsm,
		AbstractStateSaxFsm previewState,
		VerFsManager manager,
		Attributes attributes) {
		super(fsm, previewState, manager);
	}

	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {

		if (RevMLDocCommonsStrings.CHAR_CODE_MARKER.compareTo(name
			.trim()
			.toLowerCase()) == 0) {

			if (this.attributes != null) {
				for (int i = 0; i < this.attributes.getLength(); i++) {
					if (RevMLDocCommonsStrings.REVML_CHAR_CODE_ATTRIBUTE
						.compareTo(this.attributes
							.getLocalName(i)
							.trim()
							.toLowerCase()) == 0) {

						Integer.valueOf(this.attributes.getValue(i));

						// TODO Well formed char marker, change to check char sequence
						break;
					}
				}
				
				// TODO : Misformed char marker
			}
		} else {
			getFsm().changeState(
				new ErrorStateRevMLState(
					getFsm(),
					this,
					getManager(),
					"Unexpected end marker.\nReceived: " + name
							+ "\nExpected: "
							+ RevMLDocCommonsStrings.REVML_CHAR_CODE_ATTRIBUTE,
					ErrorStateRevMLState.MISFORMED_XML_DOCUMENT));
		}

	}

	@Override
	public void startElement(
		String uri,
		String localName,
		String name,
		Attributes attributes) throws SAXException {

		getFsm().changeState(
			new ErrorStateRevMLState(
				getFsm(),
				this,
				getManager(),
				"Char code maker sould be a leaf marker.",
				ErrorStateRevMLState.MISFORMED_XML_DOCUMENT));
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		getFsm().changeState(
			new ErrorStateRevMLState(
				getFsm(),
				this,
				getManager(),
				"Char code maker sould be a leaf marker.",
				ErrorStateRevMLState.MISFORMED_XML_DOCUMENT));
	}

}
