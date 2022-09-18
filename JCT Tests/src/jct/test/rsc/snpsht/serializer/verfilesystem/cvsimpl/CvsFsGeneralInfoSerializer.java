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
package jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl;

import java.io.FileWriter;
import java.io.IOException;

import jct.test.rsc.snpsht.filesystem.IFsFileEntity;
import jct.test.rsc.snpsht.filesystem.IFsRealEntity;
import jct.test.rsc.snpsht.serializer.verfilesystem.AbstractVerFsSerializer;
import jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.ICvsFsCommitConstraint;

/**
 * Serialize CvsManager general info and validation info to a XML file in the
 * given directory.
 * 
 * @author Julien Tantéri
 * 
 */
public class CvsFsGeneralInfoSerializer extends AbstractVerFsSerializer {
	private CvsFsManager manager;

	/**
	 * Default constructor.
	 * 
	 * @param manager
	 */
	public CvsFsGeneralInfoSerializer(CvsFsManager manager) {
		this.manager = manager;
	}

	/**
	 * Returns current manager
	 * 
	 * @return current manager
	 */
	@Override
	public CvsFsManager getManager() {
		return this.manager;
	}

	/**
	 * Allows to set another manager to serialize
	 * 
	 * @param manager
	 *        new manager
	 */
	public void setManager(CvsFsManager manager) {
		this.manager = manager;
	}

	/**
	 * Serialize general CVS manager, and CVSRoot info to a XML file in the
	 * given directory.
	 * 
	 * @param dir
	 *        Directory in witch serialize general CVS manager, CVSRoot and
	 *        validation info.
	 * @throws Exception
	 *         If serialization failed,
	 */
	@Override
	public void serialize() throws IOException {
		IFsFileEntity entity =
			this.manager.getSourceManager().add(
				VerFsCommonStrings.GENERAL_INFO_XML_FILE_NAME);

		FileWriter writer =
			this.manager.getSourceManager().getFileWriter((IFsRealEntity) entity);

		// XML headers
		writer.write(VerFsCommonStrings.XML_HEADER);
		writer.write(VerFsCommonStrings.DTD_MARKER);
		{
			writer.write("<" + VerFsCommonStrings.GENERAL_INFO_MAKER + " "
					+ VerFsCommonStrings.GENERAL_TYPE_ATTRIBUTE + "=\""
					+ CvsFsCommonStrings.CVS_TYPE_ATTRIBUTE_VALUE + "\">");
			{
				writer.write(genMarker(
					VerFsCommonStrings.GENERAL_TYPE_MAKER,
					CvsFsCommonStrings.CVS_TYPE_ATTRIBUTE_VALUE));

				serializeCVSRoot(writer);
				serializeCVSManagerInfo(writer);
				serializeCVSCommitConstraints(writer);
			}
			writer.write("</" + VerFsCommonStrings.GENERAL_INFO_MAKER + ">");
		}

		writer.close();
	}

	// Serialize CVSRoot to XML
	private void serializeCVSCommitConstraints(FileWriter writer)
			throws IOException {
		String attrs = "";

		writer.write("<"
				+ CvsFsCommonStrings.CVS_COMMIT_GROUP_CONSTRAINTS_MARKER + ">");
		{
			for (ICvsFsCommitConstraint constr : this.manager.getConstraints()) {
				writer.write("<"
						+ CvsFsCommonStrings.CVS_COMMIT_GROUP_CONSTRAINT_MARKER
						+ ">");
				{
					writer.write(genMarker(
						CvsFsCommonStrings.CVS_COMMIT_GROUP_CONSTR_TYPE_MARKER,
						VerFsCommonStrings.ASCII2XML(constr.getName())));

					writer
						.write(genMarker(
							CvsFsCommonStrings.CVS_COMMIT_GROUP_CONSTR_DESCR_MARKER,
							VerFsCommonStrings.ASCII2XML(constr
								.getDescription())));

					for (String[] attr : constr.getAttributes()) {
						attrs +=
							"<"
									+ CvsFsCommonStrings.CVS_COMMIT_GROUP_CONSTR_ATTR_MARKER
									+ " "
									+ CvsFsCommonStrings.CVS_COMMIT_GROUP_CONSTR_ATTR_TYPE_ATTR
									+ "=\""
									+ attr[0]
									+ "\">"
									+ attr[1]
									+ "</"
									+ CvsFsCommonStrings.CVS_COMMIT_GROUP_CONSTR_ATTR_MARKER
									+ ">";
					}
					writer
						.write(genMarker(
							CvsFsCommonStrings.CVS_COMMIT_GROUP_CONSTR_ATTRS_MARKER,
							attrs));
				}
				writer.write("</"
						+ CvsFsCommonStrings.CVS_COMMIT_GROUP_CONSTRAINT_MARKER
						+ ">");
			}
		}
		writer.write("</"
				+ CvsFsCommonStrings.CVS_COMMIT_GROUP_CONSTRAINTS_MARKER + ">");

	}

	// Serialize CVSRoot to XML
	private void serializeCVSRoot(FileWriter writer) throws IOException {
		writer.write("<" + CvsFsCommonStrings.CVS_ROOT_MAKER + ">");
		{
			writer.write(genMarker(
				CvsFsCommonStrings.CVS_ROOT_STRING_MAKER,
				VerFsCommonStrings.ASCII2XML(this.manager
					.getCvsRoot()
					.getCVSROOT(false))));

			writer.write(genMarker(
				CvsFsCommonStrings.CVS_ROOT_PROTOCOL_MAKER,
				VerFsCommonStrings.ASCII2XML(this.manager
					.getCvsRoot()
					.getProtocol())));

			writer.write(genMarker(
				CvsFsCommonStrings.CVS_ROOT_SERVER_MAKER,
				VerFsCommonStrings.ASCII2XML(this.manager
					.getCvsRoot()
					.getServer())));

			writer.write(genMarker(
				CvsFsCommonStrings.CVS_ROOT_USER_MAKER,
				VerFsCommonStrings.ASCII2XML(this.manager
					.getCvsRoot()
					.getUser())));

			writer.write(genMarker(
				CvsFsCommonStrings.CVS_ROOT_ROOT_REPO_MAKER,
				VerFsCommonStrings.ASCII2XML(this.manager
					.getCvsRoot()
					.getRootRepository())));

			writer.write(genMarker(
				CvsFsCommonStrings.CVS_ROOT_CURRENT_REPO_MAKER,
				VerFsCommonStrings.ASCII2XML(this.manager
					.getCvsRoot()
					.getCurrentRepository())));

			writer.write(genMarker(
				CvsFsCommonStrings.CVS_ROOT_SEPARATOR_MAKER,
				VerFsCommonStrings.ASCII2XML(this.manager
					.getCvsRoot()
					.getSeparator())));

		}
		writer.write("</" + CvsFsCommonStrings.CVS_ROOT_MAKER + ">");
	}

	// Serialize CVS manager info to XML
	private void serializeCVSManagerInfo(FileWriter writer) throws IOException {
		writer.write("<" + CvsFsCommonStrings.CVS_INFO_MAKER + ">");
		{
			writer.write(genMarker(
				CvsFsCommonStrings.CVS_INFO_DESCR_MAKER,
				VerFsCommonStrings.ASCII2XML(this.manager.getDescription())));

			writer
				.write(genMarker(
					CvsFsCommonStrings.CVS_ROOT_CURRENT_REPO_MAKER,
					VerFsCommonStrings.ASCII2XML(this.manager
						.getRoot()
						.getPath())));

			writer.write(genMarker(
				CvsFsCommonStrings.CVS_INFO_REVML_VERSION_MAKER,
				VerFsCommonStrings.ASCII2XML(this.manager.getRevmlVersion())));
		}
		writer.write("</" + CvsFsCommonStrings.CVS_INFO_MAKER + ">");
	}
}
