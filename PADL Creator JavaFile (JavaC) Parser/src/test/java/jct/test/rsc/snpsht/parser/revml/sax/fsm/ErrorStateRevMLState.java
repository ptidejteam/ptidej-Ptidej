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

import jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;

import org.xml.sax.SAXException;

public class ErrorStateRevMLState extends AbstractRevMLState {

	public static final int REVML_MARKER_NOT_FOUND = 0;
	public static final int UNEXPECTED_DOCUMENT_END = 1;
	public static final int MISFORMED_XML_DOCUMENT = 2;
	public static final int MISFORMED_DATE_FORMAT = 3;
	public static final int MISFORMED_REVML_DOCUMENT = 4;
	public static final int UNEXPECTED_FILE_ID = 5;
	public static final int MISFORMED_XML_MARKER = 6;
	public static final int EXCEPTION_CATCHED = 7;

	private int errorType;
	private String message;

	public ErrorStateRevMLState(
		SimpleSaxFsmParser fsm,
		AbstractStateSaxFsm previewState,
		VerFsManager manager,
		String message,
		int errorType) {
		super(fsm, previewState, manager);

		this.message = message;
		this.errorType = errorType;
	}

	public int getErrorType() {
		return this.errorType;
	}

	public String getMessage() {
		return this.message;
	}

	@Override
	public String toString() {
		String toRet = "<ErrorStateRevMLSaxFsm>\n" +
		"Error during revml file parsing.\nMessage: " + 
		getMessage() + "\nError type: " + this.errorType +
		"\nPreview state: " + getPreviewState();
		return toRet;
	}
	
	@Override
	public void endDocument() throws SAXException {
	}
}
