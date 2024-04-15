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
package jct.test.rsc.snpsht.parser.revml.modeler;

import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.IModeler;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsCommit;
import jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsCreateAction;
import jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsDeleteAction;
import jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsMoveAction;
import jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsResurrectAction;

/**
 * This class aims to detect moved files, and link them in meta-model
 * 
 * @author Julien Tantéri
 * 
 */
public class MovedFilesModeler implements IModeler {

	@Override
	public VerFsManager modl(VerFsManager manager) {
		for (VerFsCommit commit : manager.getAllCommits()) {
			detectAndLinkMovedFiles(manager, commit);
		}

		return manager;
	}

	// Detect and link moved files in a commit
	// A file is considered as moved, if 2 files revisions with the same name
	// are in the same commit, and 1 end during 1 start.
	private void detectAndLinkMovedFiles(
		VerFsManager manager,
		VerFsCommit commit) {

		for (VerFsFileRev delFile : commit.getChildren()) {

			// A deleted file found
			if (delFile.getAction() instanceof VerFsDeleteAction) {
				// Unlink potential next revisions, to set action as resurrect
				// We don't consider a delete then create file as the same file
				
				// Looking if file was created again
				for (VerFsFileRev creaFile : commit.getChildren()) {
					if (creaFile.getAction() instanceof VerFsCreateAction
							&& delFile.getName().compareTo(creaFile.getName()) == 0 &&
							delFile.getBranch() == creaFile.getBranch()) {

						// If delFile have next rev on same branch, that's
						// because it have been resurrected. In this case, we
						// consider, that's the resurrected file is a new file.
						for (VerFsFileRev nextFile : delFile.getNextRevisions()) {
							if (nextFile.getBranch() == creaFile.getBranch()) {
								// manager.removeNextVersion(nextFile, delFile);
								// manager.setPrevVersion(null, nextFile);
								manager.setAction(VerFsResurrectAction.class,
									nextFile);

							}
						}

						manager.setPrevRevision(delFile, creaFile);
						manager.addNextRevision(creaFile, delFile);
						manager.setAction(
							VerFsMoveAction.class,
							creaFile);
					}
				}
			}
		}
	}
}
