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
package jct.test.rsc.snpsht.verfilesystem.snapshooter;

import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsFile;
import jct.test.rsc.snpsht.verfilesystem.snapshooter.SystemState.FileState;

public class MainMultiBranchPrioSnapshooter extends
		AbstractMainBranchPrioSnapshooter {

	public MainMultiBranchPrioSnapshooter(VerFsManager manager) {
		super(manager);
	}

	public MainMultiBranchPrioSnapshooter(
		VerFsManager manager,
		IVerFsBranch defaultBranch) {
		super(manager, defaultBranch);
	}

	@Override
	public Snapshot getSnapshot(int i) {
		if (i < 0 || i >= getSnapshotCount())
			throw new IllegalArgumentException("Index out of bounds");

		SystemState systemState =
			getSystemSate(i);
		FileState fileState;
		Snapshot snapshot = new Snapshot();
		
		snapshot.setSnapshotPeriod(systemState.getPeriod());
		snapshot.addCoveredCommit(getCommit(i));

//		snapshot.setSnapshotStartTime(getCommit(i)
//			.getCommittingTime()
//			.getTime());
//
//		if (i + 1 < getSnapshotCount()) {
//			snapshot.setSnapshotEndTime(getCommit(i + 1)
//				.getCommittingTime()
//				.getTime());
//		} else {
//			snapshot.setSnapshotEndTime(Snapshot.INFINITE_TIME);
//		}

		for (IVerFsFile file : systemState.getFiles()) {
			fileState = systemState.getFileState(file);
			for (int j = this.branchesByPriority.size() - 1; j >= -1; j--) {
				if (j >= 0) {
					if (fileState.containsBranch(this.branchesByPriority.get(j))) {
						snapshot.addAll(fileState.getBranchState(this.branchesByPriority
							.get(j)));
						break;
					}
				} else {
					if (fileState.containsBranch(this.defaultBranch)) {
						snapshot.addAll(fileState.getBranchState(this.defaultBranch));
					}
				}
			}
		}

		return snapshot;
	}

}
