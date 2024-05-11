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

import java.util.Date;
import java.util.LinkedList;

import jct.test.rsc.snpsht.utils.Pair;
import jct.test.rsc.snpsht.verfilesystem.IVerFsSimpleRevision;

public class StructureDiffSnapshooterExtender extends
		AbstractSnapshooterExtender {
	private int[] diffList;

	public StructureDiffSnapshooterExtender(ISnapshooter extendedSnapshooter) {
		super(extendedSnapshooter);
		init();
	}

	private void init() {
		SnapshotDiff<IVerFsSimpleRevision> diff;
		LinkedList<Integer> diffList = new LinkedList<Integer>();
		int i = 0;

		// Find first non-empty snapshot
		for (i = 0; i < getExtendedSnapshooter().getSnapshotCount(); i++) {
			if (getExtendedSnapshooter().getSnapshot(i).getFilesRev().size() != 0) {
				diffList.addLast(i);
				break;
			}
		}

		// Find snapshot with different structure from its previous
		for (i++; i < getExtendedSnapshooter().getSnapshotCount(); i++) {
			diff =
				getExtendedSnapshooter().getSnapshot(i).filesRevDiff(
					getExtendedSnapshooter().getSnapshot(diffList.getLast()));
			if (diff.isDiff()) {
				diffList.addLast(i);
			}
		}

		this.diffList = new int[diffList.size()];
		for (i = 0; i < diffList.size(); i++)
			this.diffList[i] = diffList.get(i);
	}

	@Override
	public Pair<Integer, Integer> getPeriodBounds(Date startTime, Date endTime)
			throws IllegalArgumentException {

		if (endTime.compareTo(startTime) <= 0) {
			throw new IllegalArgumentException(
				"Illegal period : start time >= end time");
		}
		
		Pair<Integer, Integer> boundsExt =
			this.getExtendedSnapshooter().getPeriodBounds(startTime, endTime);

		Pair<Integer, Integer> bounds = new Pair<Integer, Integer>(-1, 0);
		for (int currIndex : this.diffList) {
			if (boundsExt.car() >= currIndex)
				bounds.car(bounds.car() + 1);

			
			if (boundsExt.cdr() >= currIndex)
				bounds.cdr(bounds.cdr() + 1);
			else return bounds;
		}

		return bounds;
	}

	@Override
	public Snapshot getSnapshot(int i) {
		if (i < 0 || i >= getSnapshotCount())
			throw new IllegalArgumentException("Index out of bounds");

		// Grab different snapshot in extended snapshooter
		Snapshot sS = getExtendedSnapshooter().getSnapshot(this.diffList[i]);
		// If snapshot is not the last in this snapshooter
		if (i < getSnapshotCount() - 1) {
			// Set end time on next snapshot start time
			sS.setSnapshotEndTime(getExtendedSnapshooter()
				.getSnapshot(this.diffList[i + 1])
				.getSnapshotStartTime());
			
			// Add covered commits
			for(int j=this.diffList[i]+1; j<this.diffList[i+1]; j++) {
				sS.addAllCoveredCommits(getExtendedSnapshooter().getSnapshot(j).getCoveredCommits());
			}
		} else {
			// Set end time to infinite
			sS.setSnapshotEndTime(Snapshot.INFINITE_TIME);
			
			// Add covered commits
			for(int j=this.diffList[i]+1; j<getExtendedSnapshooter().getSnapshotCount(); j++) {
				sS.addAllCoveredCommits(getExtendedSnapshooter().getSnapshot(j).getCoveredCommits());
			}
		}

		return sS;
	}

	@Override
	public int getSnapshotCount() {
		return this.diffList.length;
	}

	@Override
	public int getSnapshotIndex(Date d) {
		int i = this.getExtendedSnapshooter().getSnapshotIndex(d);

		int index = -1;
		for (int currIndex : this.diffList) {
			if (i >= currIndex)
				index++;
			else
				return index;
		}

		return index;
	}

}
