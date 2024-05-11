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

import jct.test.rsc.snpsht.parser.revml.sax.fsm.AbstractRevMLState;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState;
import jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.DefaultUnknowMakerStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public abstract class AbstractStringLoaderState extends AbstractRevMLState {
	
	private String loadedString;
	private VerFsFileRev file;
	private AbstractStateSaxFsm callBackState;

	public AbstractStringLoaderState(
		SimpleSaxFsmParser fsm,
		AbstractStateSaxFsm previewState,
		VerFsManager manager,
		VerFsFileRev file) {
		super(fsm, previewState, manager);
		
		this.loadedString = "";
		this.file = file;
		this.callBackState = previewState;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		this.loadedString += new String(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		if (name.trim().toLowerCase().compareTo(getWaitedMarker()) == 0) {
			
			processString(getLoadedString());

			getFsm().changeState(this.callBackState);
		} else {
			getFsm().changeState(
				new ErrorStateRevMLState(
					getFsm(),
					this,
					getManager(),
					"Misformed xml document.\nEnd marker unexpected.\nReceived: "
							+ name + "\nExpected: " + getWaitedMarker(),
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
			new DefaultUnknowMakerStateSaxFsm(
				getFsm(),
				this,
				this,
				attributes,
				localName,
				name,
				uri));
	}
	
	public String getLoadedString() {
		return this.loadedString;
	}

	public VerFsFileRev getFile() {
		return this.file;
	}
	
	protected void setCallBackSate(AbstractStateSaxFsm state) {
		this.callBackState= state;
	}

	protected abstract void processString(String loadedString);
	
	protected abstract String getWaitedMarker();
	
	@Override
    public abstract String toString();
	
}
