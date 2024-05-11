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
package jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.ICvsFsCommitConstraint;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.IModeler;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.CommitGroup;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.IGroup;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.IGroupingConstraint;

public class CommitGrouper implements IModeler {

	// List of constraints to apply
	private List<IGroupingConstraint> constraints;

	public CommitGrouper() {
		this.constraints = new ArrayList<IGroupingConstraint>();
	}

	/**
	 * Clear constraints list
	 */
	public void clearConstraints() {
		this.constraints.clear();
	}

	/**
	 * Returns the current constraints list.
	 * 
	 * @return The current constraints list.
	 */
	public List<IGroupingConstraint> getConstraints() {
		return this.constraints;
	}

	/**
	 * Add a constraint.<br>
	 * Constraints are apply in adding order.
	 * 
	 * @param constraint
	 *        Constraint to add.
	 */
	public void addConstraint(IGroupingConstraint constraint) {
		this.constraints.add(constraint);
	}

	/**
	 * Remove a constraint.
	 * 
	 * @param constraint
	 *        Constraint to remove.
	 */
	public void removeConstraint(IGroupingConstraint constraint) {
		this.constraints.remove(constraint);
	}

	@Override
	public VerFsManager modl(VerFsManager manager) {
		try {
			modl((CvsFsManager) manager);
		} catch (ClassCastException e) {
			throw new IllegalArgumentException(
				"Unexpected manager type.\nReceived manager should be a 'VerFsManager', but is a '"
						+ manager.getClass().getName() + "'");
		}
		
		return manager;
	}

	public VerFsManager modl(CvsFsManager manager) {
		Set<IGroup> groups = new HashSet<IGroup>();

		CommitGroup mainGroup =
			new CommitGroup(new HashSet<VerFsFileRev>(manager
				.getAllSimplesRevisions()));

		groups.add(mainGroup);

		for (IGroupingConstraint constraint : this.constraints) {
			groups = constraint.applyContrainst(groups);
		}

		makeCommits(manager, groups);

		manager.setConstraints(
			this.constraints.toArray(new ICvsFsCommitConstraint[0]));

		// TODO remove => for testing purpose
//		int i = 1;
//		for (IGroup group : groups) {
//			System.out.println();
//			System.out.println("Group #" + i);
//			i++;
//			for (VerFsFileRev fileRev : group.getGroup()) {
//				System.out.println("ID: " + fileRev.getId());
//				// System.out.println("ID: " + fileRev.getId() + "\nComment: "
//				// + fileRev.getComment().getComment() + "\nAuthor: "
//				// + fileRev.getAuthor().getAuthor() + "\nDate: "
//				// + fileRev.getUpdateDate().getTime() + "\nBranch: "
//				// + fileRev.getBranch().getBranch() + "\n");
//			}
//		}
//
//		i = 1;
//		for (VerFsCommit commit : manager.getAllCommits()) {
//			System.out.println();
//			System.out.println("Commit #" + i);
//			i++;
//			for (VerFsFileRev fileRev : commit.getChildren()) {
//				System.out.println("ID: " + fileRev.getId());
//				// System.out.println("ID: " + fileRev.getId() + "\nComment: "
//				// + fileRev.getComment().getComment() + "\nAuthor: "
//				// + fileRev.getAuthor().getAuthor() + "\nDate: "
//				// + fileRev.getUpdateDate().getTime() + "\nBranch: "
//				// + fileRev.getBranch().getBranch() + "\n");
//			}
//		}

		return manager;
	}

	private void makeCommits(VerFsManager manager, Set<IGroup> groups) {
		for (IGroup group : groups) {
			manager.setAsCommit(group.getGroup().toArray(new VerFsFileRev[0]));
		}
	}
}
