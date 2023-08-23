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

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState;
import jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;

public class LoadUpdateTimeState extends AbstractStringLoaderState {
	private static final DateFormat df = DateFormat.getDateTimeInstance();

	public LoadUpdateTimeState(
		SimpleSaxFsmParser fsm,
		AbstractStateSaxFsm previewState,
		VerFsManager manager,
		VerFsFileRev file) {
		super(fsm, previewState, manager, file);
	}

	@Override
	protected void processString(String loadedString) {
		Date updateTime;

		try {
			updateTime = LoadUpdateTimeState.df.parse(loadedString);

			getManager().setUpdateTime(updateTime, getFile());
		} catch (ParseException e) {
			System.out.println("Can't parse date");
			setCallBackSate(new ErrorStateRevMLState(
				getFsm(),
				this,
				getManager(),
				"Can't parse file revision update time\nFile: "
						+ getFile().getId() + "\nDate: " + loadedString,
				ErrorStateRevMLState.MISFORMED_DATE_FORMAT));
		}
	}
	
	@Override
	public String toString() {
		String toRet = "<LoadUpdateTimeState>\nUpdate time: " + getLoadedString();
		return toRet;
	}

	@Override
	protected String getWaitedMarker() {
		return RevMLDocCommonsStrings.FILE_REV_UPDATE_TIME_MARKER;
	}

}
