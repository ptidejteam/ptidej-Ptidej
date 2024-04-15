/* (c) Copyright 2008 and following years, Julien Tanteri,
 * University of Montreal.
 * 
 * Use and copying of this software and preparation of derivative works
 * based upon this software are permitted. Any copy of this software or
 * of any derivative work must include the above copyright notice of
 * the author, this paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS
 * ALL WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE, AND NOT WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN,
 * ANY LIABILITY FOR DAMAGES RESULTING FROM THE SOFTWARE OR ITS USE IS
 * EXPRESSLY DISCLAIMED, WHETHER ARISING IN CONTRACT, TORT (INCLUDING
 * NEGLIGENCE) OR STRICT LIABILITY, EVEN IF THE AUTHOR IS ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
package jct.test.rsc.snpsht.parser.revml.sax.fsm.structurebuilder;

import jct.test.rsc.snpsht.parser.revml.sax.fsm.AbstractRevMLState;
import jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;

import org.xml.sax.SAXException;

public class BuildRepoStructEndState extends AbstractRevMLState {

	public BuildRepoStructEndState(
		SimpleSaxFsmParser fsm,
		AbstractStateSaxFsm previewState,
		VerFsManager manager) {
		super(fsm, previewState, manager);
	}

	@Override
	public void endDocument() throws SAXException {
	}

	@Override
	public String toString() {
		String toRet = "<BuildRepoStructEndState>";
		return toRet;
	}

}
