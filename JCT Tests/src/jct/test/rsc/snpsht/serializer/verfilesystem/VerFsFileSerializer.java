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
import java.util.Set;

import jct.test.rsc.snpsht.filesystem.IFsFileEntity;
import jct.test.rsc.snpsht.filesystem.IFsRealEntity;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import jct.test.rsc.snpsht.verfilesystem.VerFsNullRev;
import jct.test.rsc.snpsht.verfilesystem.comparator.VerFsFileRevUpdateTimeComparator;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsFile;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsTag;

public class VerFsFileSerializer extends AbstractVerFsSerializer {
	private VerFsManager manager;

	/**
	 * Default constructor.
	 * 
	 * @param manager
	 */
	public VerFsFileSerializer(VerFsManager manager) {
		setManager(manager);
	}

	/**
	 * Returns current manager
	 * 
	 * @return current manager
	 */
	@Override
	public VerFsManager getManager() {
		return this.manager;
	}

	/**
	 * Allows to set another manager to serialize
	 * 
	 * @param manager
	 *        new manager
	 */
	public void setManager(VerFsManager manager) {
		this.manager = manager;
	}

	/**
	 * Serialize general CVS manager, CVSRoot and validation info to a XML file
	 * in the given directory.
	 * 
	 * @param dir
	 *        Directory in witch serialize general CVS manager, CVSRoot and
	 *        validation info.
	 * @throws Exception
	 *         If serialization failed,
	 */
	@Override
    public void serialize() throws IOException {
		for (VerFsFile file : this.manager.getAllFiles()) {
			//downloadFilesRev(file);
			serializeFileToXML(file);
		}
	}

	// Serialize a file to XML in the given directory
	private void serializeFileToXML(VerFsFile file) throws IOException {
		String filePath =
			VerFsCommonStrings.META_INFO_XML_FILES_PATH + file.getId() + "."
					+ VerFsCommonStrings.META_INFO_XML_FILE_EXT;

		IFsFileEntity entity = this.manager.getSourceManager().add(filePath);
		FileWriter writer =
			this.manager.getSourceManager().getFileWriter((IFsRealEntity) entity);

		this.manager.setFileLocation(entity, file);

		// XML headers
		writer.write(VerFsCommonStrings.XML_HEADER);
		writer.write(VerFsCommonStrings.DTD_MARKER);
		{
			// File
			writer.write("<" + VerFsCommonStrings.FILE_MAKER + " "
					+ VerFsCommonStrings.FILE_ID_ATTRIBUTE + "=\""
					+ VerFsCommonStrings.ASCII2XML(file.getId()) + "\">");
			{
				writer.write(genMarker(
					VerFsCommonStrings.FILE_ID_MAKER,
					VerFsCommonStrings.ASCII2XML(file.getId())));

				//serializeNullRevToXML(writer, file.getNullRevision());

				writer.write("<" + VerFsCommonStrings.REVISIONS_MAKER + ">");
				{
					VerFsFileRev[] files =
						file.getChildren().toArray(new VerFsFileRev[0]);
					Arrays.sort(files, new VerFsFileRevUpdateTimeComparator());
					for (VerFsFileRev fileRev : files) {
						serializeFileRevToXML(writer, fileRev);
					}
				}
				writer.write("</" + VerFsCommonStrings.REVISIONS_MAKER + ">");
			}
			writer.write("</" + VerFsCommonStrings.FILE_MAKER + ">");
		}

		writer.close();
	}

	// Serialize a file revision to XML
	private void serializeFileRevToXML(FileWriter writer, VerFsFileRev fileRev)
			throws IOException {
		writer.write("<" + VerFsCommonStrings.FILE_REV_MAKER + " "
				+ VerFsCommonStrings.FILE_REV_ID_ATTRIBUTE + "=\""
				+ VerFsCommonStrings.ASCII2XML(fileRev.getId()) + "\">");
		{
			writer.write(genMarker(
				VerFsCommonStrings.FILE_REV_ID_MAKER,
				VerFsCommonStrings.ASCII2XML(fileRev.getId())));

			writer.write(genMarker(
				VerFsCommonStrings.FILE_REV_NAME_MAKER,
				VerFsCommonStrings.ASCII2XML(fileRev.getName())));

			writer.write(genMarker(
				VerFsCommonStrings.FILE_REV_FILE_NAME_MAKER,
				VerFsCommonStrings.ASCII2XML(fileRev
					.getFileName()
					.getValue())));

			writer.write(genMarker(
				VerFsCommonStrings.REPO_PATH_MAKER,
				VerFsCommonStrings.PATH2XML(fileRev.getPath())));

			serializeTimeOnlyToXML(writer, fileRev.getUpdateTime());

			if (fileRev.getFileRevLocation() == null) {
				System.out.println();
			}

			writer.write(genMarker(
				VerFsCommonStrings.FILE_REV_FILE_LOCATION_MAKER,
				VerFsCommonStrings.PATH2XML(fileRev
					.getFileRevLocation()
					.getId())));

			writer.write(genMarker(
				VerFsCommonStrings.FILE_REV_REV_MAKER,
				VerFsCommonStrings.ASCII2XML(fileRev.getRevID().getValue())));

			writer.write(genMarker(
				VerFsCommonStrings.FILE_REV_ACTION_MAKER,
				VerFsCommonStrings.ASCII2XML(fileRev.getAction().getValue())));

			writer.write(genMarker(
				VerFsCommonStrings.FILE_REV_AUTHOR_MAKER,
				VerFsCommonStrings.ASCII2XML(fileRev.getAuthor().getValue())));

			writer
				.write(genMarker(
					VerFsCommonStrings.FILE_REV_COMMENT_MAKER,
					VerFsCommonStrings.ASCII2XML(fileRev
						.getComment()
						.getValue())));

			writer.write(genMarker(
				VerFsCommonStrings.FILE_REV_BRANCH_MAKER,
				VerFsCommonStrings.ASCII2XML(fileRev.getBranch().getValue())));

			serializeNextPrevFilesRevToXML(writer, fileRev);

			serializeTagsToXML(writer, fileRev.getTags());
		}
		writer.write("</" + VerFsCommonStrings.FILE_REV_MAKER + ">");
	}

	// Serialize tags to XML
	private void serializeTagsToXML(FileWriter writer, Set<VerFsTag> tags)
			throws IOException {
		String tagsXML = "";

		for (VerFsTag tag : tags) {
			tagsXML +=
				genMarker(
					VerFsCommonStrings.FILE_REV_TAG_MAKER,
					VerFsCommonStrings.ASCII2XML(tag.getValue()));
		}

		writer
			.write(genMarker(VerFsCommonStrings.FILE_REV_TAGS_MAKER, tagsXML));
	}

	// Serialize next files revisions to XML
	private void serializeNextPrevFilesRevToXML(
		FileWriter writer,
		VerFsFileRev fileRev) throws IOException {
		String nextRevXML = "";

		try {
			VerFsNullRev nullRev = (VerFsNullRev) fileRev.getPrevRevision();
			writer.write(genMarker(
				VerFsCommonStrings.FILE_REV_PREV_REV_MAKER,
				(nullRev.getPrevRevision() == null ? "" : nullRev
					.getPrevRevision()
					.getId())));
		} catch (Exception e) {
			writer.write(genMarker(
				VerFsCommonStrings.FILE_REV_PREV_REV_MAKER,
				fileRev.getPrevRevision().getId()));
		}

		for (VerFsFileRev rev : fileRev.getNextRevisions()) {
			nextRevXML +=
				genMarker(
					VerFsCommonStrings.FILE_REV_ID_MAKER,
					VerFsCommonStrings.ASCII2XML(rev.getId()));
		}

		writer.write(genMarker(
			VerFsCommonStrings.FILE_REV_NEXT_REV_MAKER,
			nextRevXML));
	}
}
