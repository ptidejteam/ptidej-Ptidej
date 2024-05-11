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
package jct.test.rsc.snpsht.serializer.verfilesystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import jct.test.rsc.snpsht.verfilesystem.attribute.IVerFsPeriod;
import jct.test.rsc.snpsht.verfilesystem.attribute.IVerFsTime;

public abstract class AbstractVerFsSerializer implements IVerFsSerializer {

	@Override
	public void serialize(File dest) throws IOException {
		getManager().setSource(dest);
		serialize();
		getManager().getSourceManager().update();
	}

	@Override
	public abstract void serialize() throws IOException;

	protected String genMarker(String marker, String content) {
		if (content == null || content.compareTo("") == 0) {
			return "<" + marker + "/>";
		} else {
			return "<" + marker + ">" + content + "</" + marker + ">";
		}
	}

	protected void serializeTimeOnlyToXML(FileWriter writer, IVerFsTime time) throws IOException {
		if(!(time instanceof IVerFsPeriod))
			serializeTimeToXML(writer, time);
	}

	protected void serializeTimeToXML(FileWriter writer, IVerFsTime time)
			throws IOException {
		IVerFsPeriod period;
		String strTime;

		strTime = Long.toString(time.getTime().getTime());

		try {
			period = (IVerFsPeriod) time;

			strTime =
				"<"
						+ VerFsCommonStrings.FILE_REV_UPDATE_TIME_MAKER
						+ " "
						+ VerFsCommonStrings.FILE_REV_UPDATE_TIME_TYPE_ATTRIBUTE
						+ "=\"" + VerFsCommonStrings.FILE_REV_PERIOD_ATT_VAL
						+ "\">";

			strTime +=
				genMarker(VerFsCommonStrings.FILE_REV_TIME_MAKER, Long
					.toString(period.getTime().getTime()));

			strTime +=
				genMarker(VerFsCommonStrings.FILE_REV_PERIOD_START_MAKER, Long
					.toString(period.getStartTime().getTime()));

			strTime +=
				genMarker(VerFsCommonStrings.FILE_REV_PERIOD_END_MAKER, Long
					.toString(period.getEndTime().getTime()));
		} catch (Exception e) {

			strTime =
				"<"
						+ VerFsCommonStrings.FILE_REV_UPDATE_TIME_MAKER
						+ " "
						+ VerFsCommonStrings.FILE_REV_UPDATE_TIME_TYPE_ATTRIBUTE
						+ "=\"" + VerFsCommonStrings.FILE_REV_TIME_ATT_VAL
						+ "\">";

			strTime +=
				genMarker(VerFsCommonStrings.FILE_REV_TIME_MAKER, Long
					.toString(time.getTime().getTime()));
		}

		strTime += "</" + VerFsCommonStrings.FILE_REV_UPDATE_TIME_MAKER + ">";

		writer.write(strTime);
	}

	protected File getTmpDir() {
		File tmpDir;

		tmpDir =
			new File(System.getProperty("java.io.tmpdir"), this
				.getClass()
				.getName()
					+ "_" + this.hashCode());

		return tmpDir;
	}
}
