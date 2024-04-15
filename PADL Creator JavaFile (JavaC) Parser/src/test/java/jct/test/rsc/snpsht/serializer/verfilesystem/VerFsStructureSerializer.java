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

import jct.test.rsc.snpsht.filesystem.IFsFileEntity;
import jct.test.rsc.snpsht.filesystem.IFsRealEntity;
import jct.test.rsc.snpsht.verfilesystem.IVerFsComplexElement;
import jct.test.rsc.snpsht.verfilesystem.IVerFsElement;
import jct.test.rsc.snpsht.verfilesystem.IVerFsManager;
import jct.test.rsc.snpsht.verfilesystem.IVerFsSimpleRevision;
import jct.test.rsc.snpsht.verfilesystem.visitor.AbstractVerFsVisitor;

public class VerFsStructureSerializer extends AbstractVerFsSerializer {
	private IVerFsManager manager;

	public VerFsStructureSerializer(IVerFsManager manager) {
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
				VerFsCommonStrings.FILES_REV_STRUCT_XML_FILE_NAME);

		FileWriter writer =
			this.manager.getSourceManager().getFileWriter((IFsRealEntity) entity);

		StructVisitor visitor = new StructVisitor(writer);

		// XML headers
		writer.write(VerFsCommonStrings.XML_HEADER);
		writer.write(VerFsCommonStrings.DTD_MARKER);
		{
			writer.write("<" + VerFsCommonStrings.FILES_MAKER + ">");
			{
				if ((Boolean) this.manager.getRoot().accept(visitor) != true) {
					throw new IOException(
						"Can't serialize version file system structure.");
				}
			}
			writer.write("</" + VerFsCommonStrings.FILES_MAKER + ">");
		}

		writer.close();
	}

	private class StructVisitor extends AbstractVerFsVisitor {
		private FileWriter writer;

		public StructVisitor(FileWriter writer) {
			this.writer = writer;
		}

		@Override
		public Boolean visit(IVerFsComplexElement toVisit) {
			try {
				this.writer
					.write("<" + VerFsCommonStrings.REPOSITORY_MAKER + " "
							+ VerFsCommonStrings.REPO_ID_ATTRIBUTE + "=\""
							+ VerFsCommonStrings.ASCII2XML(toVisit.getId())
							+ "\">");
				{
					this.writer.write(genMarker(
						VerFsCommonStrings.REPO_NAME_MAKER,
						VerFsCommonStrings.ASCII2XML(toVisit.getName())));

					this.writer.write(genMarker(
						VerFsCommonStrings.REPO_PATH_MAKER,
						VerFsCommonStrings.PATH2XML(toVisit.getPath())));

					this.writer.write("<"
							+ VerFsCommonStrings.REPO_CONTENT_LIST_MAKER + ">");
					{
						for (IVerFsElement elem : toVisit.getChildren()) {
							elem.accept(this);
						}
					}
					this.writer.write("</"
							+ VerFsCommonStrings.REPO_CONTENT_LIST_MAKER + ">");
				}
				this.writer.write("</" + VerFsCommonStrings.REPOSITORY_MAKER
						+ ">");
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}

			return true;
		}

		@Override
		public Boolean visit(IVerFsSimpleRevision toVisit) {
			try {
				this.writer
					.write("<" + VerFsCommonStrings.FILE_REV_MAKER + " "
							+ VerFsCommonStrings.FILE_REV_ID_ATTRIBUTE + "=\""
							+ VerFsCommonStrings.ASCII2XML(toVisit.getId())
							+ "\">");
				{
					this.writer.write(genMarker(
						VerFsCommonStrings.FILE_REV_ID_MAKER,
						VerFsCommonStrings.ASCII2XML(toVisit.getId())));

					this.writer.write(genMarker(
						VerFsCommonStrings.FILE_REV_NAME_MAKER,
						VerFsCommonStrings.ASCII2XML(toVisit.getName())));

					this.writer.write(genMarker(
						VerFsCommonStrings.FILE_REV_PATH_MAKER,
						VerFsCommonStrings.ASCII2XML(toVisit.getPath())));

					serializeTimeOnlyToXML(this.writer, toVisit.getUpdateTime());

					this.writer.write(genMarker(
						VerFsCommonStrings.FILE_LOCATION_MARKER,
						VerFsCommonStrings.PATH2XML(toVisit
							.getFile()
							.getFileLocation()
							.getId())));

					this.writer.write(genMarker(
						VerFsCommonStrings.FILE_REV_FILE_LOCATION_MAKER,
						VerFsCommonStrings.PATH2XML(toVisit
							.getFileRevLocation()
							.getId())));

					this.writer.write(genMarker(
						VerFsCommonStrings.FILE_REV_BRANCH_MAKER,
						VerFsCommonStrings.ASCII2XML(toVisit
							.getBranch()
							.getValue())));
				}
				this.writer.write("</" + VerFsCommonStrings.FILE_REV_MAKER
						+ ">");
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}

			return true;
		}

	}
}
