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

import jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings;
import jct.test.rsc.snpsht.verfilesystem.IVerFsSimpleRevision;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.IModeler;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsAuthor;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsTrunk;
import jct.test.rsc.snpsht.verfilesystem.flag.action.IVerFsAction;

public class BranchModeler implements IModeler {

	@Override
	public VerFsManager modl(VerFsManager manager) {
		addTrunkBranch(manager);
		addBrancherUsers(manager);

		return manager;
	}

	// If a file is on the CVS trunk, RevML file will not tag their branch.
	// This method will create a special branch called 'trunk', and link all
	// trunk's revisions files on it.
	private void addTrunkBranch(VerFsManager manager) {
		IVerFsBranch branch;

		for (IVerFsSimpleRevision file : manager.getAllSimplesRevisions()) {
			branch = file.getBranch();
			if (branch == null) {
				manager.setBranch(
					VerFsTrunk.TRUNK_BRANCH_NAME,
					(VerFsFileRev) file);
			}
		}
	}

	// Because CVS do not save user witch make a branch,
	// we set a 'virtual' user witch makes the branch
	private void addBrancherUsers(VerFsManager manager) {
		IVerFsAuthor author;
		IVerFsBranch branch;

		IVerFsAction branchAction =
			manager.getAction(RevMLDocCommonsStrings.REVML_BRANCH_ACTION);

		// If a branch exist in this CVS repository
		if (branchAction != null) {
			for (IVerFsSimpleRevision file : branchAction.getChildren()) {
				author = file.getAuthor();
				// Because it'as branch creation 'branch' must be not null
				branch = file.getBranch();
				if (branch != null) {
					// As CVS does not save users witch make branch, 'author'
					// should be null
					if (author == null)
						manager.setAuthor(
							"[" + CvsFsManager.BRANCHER_USER_NAME + ":"
									+ branch.getValue() + "]",
							(VerFsFileRev) file);
				} else {
					throw new NullPointerException("File revision '"
							+ file.getId()
							+ "' is a first file on a new branch, "
							+ "and so its branch must be not null");
				}
			}
		}
	}

}
