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

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import jct.test.rsc.snpsht.filesystem.IFsFileEntity;
import jct.test.rsc.snpsht.filesystem.IFsRealEntity;
import jct.test.rsc.snpsht.verfilesystem.IVerFsManager;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.comparator.VerFsFileRevUpdateTimeComparator;
import jct.test.rsc.snpsht.verfilesystem.flag.action.IVerFsAction;

public class VerFsActionsIndexSerializer extends AbstractVerFsSerializer {
	private IVerFsManager manager;

	public VerFsActionsIndexSerializer(IVerFsManager manager) {
		this.manager = manager;
	}

	@Override
	public IVerFsManager getManager() {
		return this.manager;
	}

	public void setManager(IVerFsManager manager) {
		this.manager = manager;
	}

	@Override
    public void serialize() throws IOException {
		IFsFileEntity entity =
			this.manager.getSourceManager().add(
				VerFsCommonStrings.ACTIONS_INDEX_XML_FILE_NAME);

		FileWriter writer =
			this.manager.getSourceManager().getFileWriter((IFsRealEntity) entity);

		// XML headers
		writer.write(VerFsCommonStrings.XML_HEADER);
		writer.write(VerFsCommonStrings.DTD_MARKER);
		{
			writer.write("<" + VerFsCommonStrings.ACTIONS_MAKER + ">");
			{
				for (IVerFsAction action : this.manager.getAllActions()) {
					writer.write("<" + VerFsCommonStrings.ACTION_MAKER + " "
							+ VerFsCommonStrings.ACTION_TYPE_ATTRIBUTE + "=\""
							+ VerFsCommonStrings.ASCII2XML(action.getValue())
							+ "\">");
					{
						writer.write(genMarker(
							VerFsCommonStrings.ACTION_TYPE_MAKER,
							VerFsCommonStrings.ASCII2XML(action.getValue())));

						writer.write("<" + VerFsCommonStrings.REVISIONS_MAKER
								+ ">");
						{
							VerFsFileRev[] files =
								action.getChildren().toArray(
									new VerFsFileRev[0]);
							Arrays.sort(
								files,
								new VerFsFileRevUpdateTimeComparator());
							for (VerFsFileRev fileRev : files) {
								writer
									.write("<"
											+ VerFsCommonStrings.FILE_REV_MAKER
											+ " "
											+ VerFsCommonStrings.FILE_REV_ID_ATTRIBUTE
											+ "=\""
											+ VerFsCommonStrings
												.ASCII2XML(fileRev.getId())
											+ "\">");
								{
									serializeTimeOnlyToXML(writer, fileRev
										.getUpdateTime());

									writer
										.write(genMarker(
											VerFsCommonStrings.FILE_LOCATION_MARKER,
											VerFsCommonStrings.PATH2XML(fileRev
												.getFile()
												.getFileLocation()
												.getId())));

									writer
										.write(genMarker(
											VerFsCommonStrings.FILE_REV_FILE_LOCATION_MAKER,
											VerFsCommonStrings.PATH2XML(fileRev
												.getFileRevLocation()
												.getId())));
								}
								writer.write("</"
										+ VerFsCommonStrings.FILE_REV_MAKER
										+ ">");
							}
						}
						writer.write("</" + VerFsCommonStrings.REVISIONS_MAKER
								+ ">");
					}
					writer.write("</" + VerFsCommonStrings.ACTION_MAKER + ">");
				}
			}
			writer.write("</" + VerFsCommonStrings.ACTIONS_MAKER + ">");
		}

		writer.close();

	}
}
