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

import jct.test.rsc.snpsht.filesystem.IFsRealEntity;
import jct.test.rsc.snpsht.utils.FSUtils;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;

/**
 * Allows to serialize a directory content, to an XML file (except this XML
 * file).<br>
 * Directory and file structure is serialized, thus for each files a MD5
 * checksum is serialized to.<br>
 * This XML file allows to check if the serialized system is complete and not
 * modified.<br>
 * Of course, this serializer should be the last one to be call else, some files
 * should be miss.
 * 
 * @author Julien Tantéri
 * 
 */
public class VerFsValidationDataSerializer extends AbstractVerFsSerializer {
	private VerFsManager manager;

	/**
	 * Default constructor.
	 * 
	 * @param manager
	 */
	public VerFsValidationDataSerializer(VerFsManager manager) {
		this.manager = manager;
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

	@Override
	public void serialize() throws IOException {
		String path;
		IFsRealEntity validFile =
			this.manager.getSourceManager().add(
				VerFsCommonStrings.VALIDATION_DATA_XML_FILE_NAME);

		FileWriter writer = this.manager.getSourceManager().getFileWriter(validFile);

		// XML headers
		writer.write(VerFsCommonStrings.XML_HEADER);
		writer.write(VerFsCommonStrings.DTD_MARKER);
		{
			writer.write("<" + VerFsCommonStrings.VALIDATION_DATA_MARKER
					+ ">");
			{
				for (IFsRealEntity entity : this.manager
					.getSourceManager()
					.getAllFilesEntity()) {

					path = VerFsCommonStrings.PATH2XML(entity.getId());

					writer.write("<"
							+ VerFsCommonStrings.VALID_DATA_FILE_MARKER + " "
							+ VerFsCommonStrings.VALID_DATA_NAME_ATTRIBUTE
							+ "=\"" + path + "\">");
					{
						writer.write(genMarker(
							VerFsCommonStrings.VALID_DATA_PATH_MARKER,
							path));

						writer.write(genMarker(
							VerFsCommonStrings.VALID_DATA_MD5_MARKER,
							VerFsCommonStrings.ASCII2XML(FSUtils
								.checksum(this.manager
									.getSourceManager()
									.getInputStrem(entity)))));
					}
					writer.write("</"
							+ VerFsCommonStrings.VALID_DATA_FILE_MARKER + ">");
				}
			}
			writer.write("</" + VerFsCommonStrings.VALIDATION_DATA_MARKER
					+ ">");
		}

		writer.close();

	}

//	private void serializeDir(File dir) throws IOException {
//		String path;
//
//		if (dir.isFile()) {
//			serializeFile(dir);
//		} else {
//			path =
//				VerFsCommonStrings.PATH2XML(FSUtils.getRelativePath(
//					this.serialDirtarget,
//					dir));
//
//			this.writer.write("<" + VerFsCommonStrings.VALID_DATA_DIR_MARKER
//					+ " " + VerFsCommonStrings.VALID_DATA_NAME_ATTRIBUTE
//					+ "=\"" + path + "\">");
//			{
//				this.writer.write(genMarker(
//					VerFsCommonStrings.VALID_DATA_PATH_MARKER,
//					path));
//
//				this.writer.write("<"
//						+ VerFsCommonStrings.VALID_DATA_CONTENT_MARKER + ">");
//				{
//					for (File child : dir.listFiles()) {
//						serializeDir(child);
//					}
//				}
//				this.writer.write("</"
//						+ VerFsCommonStrings.VALID_DATA_CONTENT_MARKER + ">");
//			}
//			this.writer.write("</" + VerFsCommonStrings.VALID_DATA_DIR_MARKER
//					+ ">");
//		}
//	}
//
//	private void serializeFile(File file) throws IOException {
//		String path;
//
//		if (!file.equals(this.xmlFile)) {
//			if (!file.isFile()) {
//				throw new IllegalArgumentException("Can't serialize file. '"
//						+ file.getAbsolutePath() + "' is not a file.");
//			} else {
//				path =
//					VerFsCommonStrings.PATH2XML(FSUtils.getRelativePath(
//						this.serialDirtarget,
//						file));
//
//				this.writer.write("<"
//						+ VerFsCommonStrings.VALID_DATA_FILE_MARKER + " "
//						+ VerFsCommonStrings.VALID_DATA_NAME_ATTRIBUTE + "=\""
//						+ path + "\">");
//				{
//					this.writer.write(genMarker(
//						VerFsCommonStrings.VALID_DATA_PATH_MARKER,
//						path));
//
//					this.writer.write(genMarker(
//						VerFsCommonStrings.VALID_DATA_MD5_MARKER,
//						VerFsCommonStrings.ASCII2XML(FSUtils.checksum(file))));
//				}
//				this.writer.write("</"
//						+ VerFsCommonStrings.VALID_DATA_FILE_MARKER + ">");
//			}
//		}
//	}
}
