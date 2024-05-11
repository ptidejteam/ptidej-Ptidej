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
package jct.test.rsc.snpsht.parser.revml;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.xml.parsers.ParserConfigurationException;

import jct.test.rsc.snpsht.filesystem.IFsFileEntity;
import jct.test.rsc.snpsht.parser.revml.modeler.BranchModeler;
import jct.test.rsc.snpsht.parser.revml.modeler.FileGrouper;
import jct.test.rsc.snpsht.parser.revml.modeler.FilePostProcessing;
import jct.test.rsc.snpsht.parser.revml.modeler.MovedFilesModeler;
import jct.test.rsc.snpsht.parser.revml.sax.RevMLContentSaxParser;
import jct.test.rsc.snpsht.parser.revml.sax.RevMLStructureSaxParser;
import jct.test.rsc.snpsht.parser.sax.SaxFsmParseException;
import jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings;
import jct.test.rsc.snpsht.utils.cvsutils.CVSCheckOut;
import jct.test.rsc.snpsht.utils.cvsutils.CVSRevMLCheckOut;
import jct.test.rsc.snpsht.utils.cvsutils.CVSRoot;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.CommitGrouper;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.SameAuthorConstraint;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.SameCommentConstraint;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.SlidingWindowConstraint;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsFile;
import jct.test.rsc.snpsht.verfilesystem.flag.action.AbstractVerFsAction;
import jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsBranchAction;
import jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsDeleteAction;

import org.xml.sax.SAXException;

/**
 * This class allows to download CVS repository historical data, and build a
 * CvsFsManager with it.
 * 
 * @author Julien Tanteri
 * 
 */
public class RevMLCvsCompleteBuilder {

	private VerFsManager manager;
	private CommitGrouper grouper;
	private RevMLStructureSaxParser structParser;
	private RevMLContentSaxParser contentParser;

	private CVSRoot cvsRoot;
	private CVSCheckOut cvsCo;
	private CVSRevMLCheckOut revMLCo;

	public RevMLCvsCompleteBuilder(CVSRoot cvsRoot) {
		// Create default grouper
		this.grouper = new CommitGrouper();
		this.grouper.addConstraint(new SameCommentConstraint());
		this.grouper.addConstraint(new SameAuthorConstraint());
		this.grouper.addConstraint(new SlidingWindowConstraint());

		init(cvsRoot);
	}

	public RevMLCvsCompleteBuilder(CVSRoot cvsRoot, CommitGrouper grouper) {
		this.grouper = grouper;

		init(cvsRoot);
	}

	private void init(CVSRoot cvsRoot) {
		this.cvsRoot = cvsRoot;
		this.structParser = new RevMLStructureSaxParser();
		this.contentParser = new RevMLContentSaxParser();
		this.revMLCo = new CVSRevMLCheckOut(this.cvsRoot);
		this.cvsCo = new CVSCheckOut(this.cvsRoot);
	}

	public CommitGrouper getCommitGrouper() {
		return this.grouper;
	}

	public void setCommitGrouper(CommitGrouper grouper) {
		this.grouper = grouper;
	}

	public VerFsManager getManager() {
		return this.manager;
	}

	private CvsFsManager parse(File xmlFile, File saveFile)
			throws SAXException, IOException, ParserConfigurationException,
			SaxFsmParseException {

		this.manager = this.structParser.parseStructure(xmlFile, this.manager);
		this.manager.setSource(saveFile);
		this.manager = this.contentParser.parseContent(xmlFile, this.manager);

		return (CvsFsManager) this.manager;
	}

	private void modlModel() {
		BranchModeler bm;
		FilePostProcessing frdl;
		MovedFilesModeler mfm;
		FileGrouper fg;

		// Create files revisions prev & next files
		frdl = new FilePostProcessing();
		this.manager = frdl.modl(this.manager);

		// As CVS does not save users witch make a branch
		// we need to create specials users (i.e. brancher users)
		// and link them as branch creator
		bm = new BranchModeler();
		this.manager = bm.modl(this.manager);

		// Group revisions in commits
		this.manager = this.grouper.modl(this.manager);

		// Detect and link moved files
		mfm = new MovedFilesModeler();
		this.manager = mfm.modl(this.manager);

		// Group files revisions in files
		fg = new FileGrouper();
		this.manager = fg.modl(this.manager);

	}

	/**
	 * Download CVS module content (to a RevML file), parse it, and build a
	 * CvsFsManager with it.
	 * 
	 * @param module
	 * @return New Manager
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws SaxFsmParseException
	 */
	public CvsFsManager build(String module, File saveFile) throws IOException,
			URISyntaxException, SAXException, ParserConfigurationException,
			SaxFsmParseException {
		if (saveFile.isDirectory()) {
			throw new IllegalArgumentException(
				"Save file must be a file, but is a directory.");
		}

		// #1 - Download RevML file
		File revMLFile = checkOutRevML(module);
		revMLFile.deleteOnExit();

		// #2 - Parse this file in 2 times : (1) build structure, (2) populate
		// model
		parse(revMLFile, saveFile);

		// #3 - Make some modeling on manager (double link revisions, group
		// commits, etc...
		modlModel();

		// #4 - Download revisions from CVS
		// downloadFilesRev(saveFile);

		return (CvsFsManager) this.manager;
	}

	private File checkOutRevML(String module) throws IOException,
			URISyntaxException {
		File revMLFile = File.createTempFile("CVSRevMLTempFile", ".revml");

		if (!this.revMLCo.checkOut(module, revMLFile)) {
			throw new Error("Error during RevML checkout. Can't checkout '"
					+ module + "' on '" + this.cvsRoot.toString() + "'");
		}

		this.manager = new CvsFsManager(this.cvsRoot);

		return revMLFile;
	}

	// Download all files revisions of all files in manager
	@SuppressWarnings("unused")
	private void downloadFilesRev(File saveFile) throws IOException {
		String fileRevPath, targetFilePath;
		File targetFile;

		this.manager.setSource(saveFile);

		for (VerFsFile file : this.manager.getAllFiles()) {
			for (VerFsFileRev fileRev : file.getChildren()) {

				if (!(fileRev.getAction() instanceof VerFsBranchAction)
						&& !(fileRev.getAction() instanceof VerFsDeleteAction)) {

					targetFilePath =
						VerFsCommonStrings.REVS_FILES_PATH
								+ this.manager
									.toRelativePath(fileRev.getPath())
									.replace(
										((CvsFsManager) this.manager)
											.getCvsRoot()
											.getSeparator(),
										File.separator)
								+ "."
								+ fileRev.getRevID().getValue().replace(
									'.',
									'_') + ".rev";

					targetFile = File.createTempFile("tempRev", null);

					fileRevPath =
						this.manager.getRoot().getName()
								+ ((CvsFsManager) this.manager)
									.getCvsRoot()
									.getSeparator()
								+ this.manager
									.toRelativePath(fileRev.getPath());

					this.cvsCo.checkOut(fileRevPath, fileRev
						.getRevID()
						.getValue(), targetFile);

					IFsFileEntity entity =
						this.manager.getSourceManager().add(
							targetFilePath,
							targetFile,
							true);

					this.manager.setFileRevLocation(entity, fileRev);
				}
			}
		}

		linkFileRevWithAction2FileLocation(VerFsBranchAction.class);
		setNullEntityOnDeleteRev();
	}

	private void setNullEntityOnDeleteRev() {
		for (VerFsFileRev fileRev : this.manager.getAction(
			VerFsDeleteAction.class).getChildren()) {
			this.manager.setFileRevLocation(this.manager
				.getSourceManager()
				.getANullEntity(), fileRev);
		}
	}

	// Link files rev. with the given action to the next previous available file
	// location.
	// Useful for files rev. action witch not modify file content ('delete',
	// 'branch')
	private void linkFileRevWithAction2FileLocation(
		Class<? extends AbstractVerFsAction> c) {
		for (VerFsFileRev fileRev : this.manager.getAction(c).getChildren()) {
			this.manager.setFileRevLocation(
				getFileRevNotModifContentFileLocation(fileRev),
				fileRev);
		}
	}

	// If action for a file revision is 'delete' or 'branch', then the file
	// content is not modified. So we need to link it with the next previous
	// available file content location.
	// Recursive function witch find the next previous available file content
	// location
	private IFsFileEntity getFileRevNotModifContentFileLocation(
		VerFsFileRev fileRev) {
		if (fileRev.getAction() instanceof VerFsBranchAction
				|| fileRev.getAction() instanceof VerFsDeleteAction) {
			return getFileRevNotModifContentFileLocation(fileRev
				.getPrevRevision());
		} else
			return fileRev.getFileRevLocation();
	}
}
