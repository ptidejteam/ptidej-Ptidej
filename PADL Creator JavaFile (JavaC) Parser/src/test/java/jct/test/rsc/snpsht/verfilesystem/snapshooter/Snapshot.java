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

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jct.test.rsc.snpsht.filesystem.IFsFileEntity;
import jct.test.rsc.snpsht.utils.Pair;
import jct.test.rsc.snpsht.verfilesystem.IVerFsSimpleRevision;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsCommit;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsFile;

public class Snapshot implements Comparable<Snapshot> {
	public static final Date INFINITE_TIME = new Date(Long.MAX_VALUE);

	private Set<IVerFsSimpleRevision> systemState;
	private Pair<Date, Date> snapshotPeriod;
	private List<IVerFsCommit> coveredCommit;

	/**
	 * Default constructor. Build an empty snapshot
	 */
	public Snapshot() {
		this(new HashSet<IVerFsSimpleRevision>());
	}

	/**
	 * Build a snapshot with the given file revisions list
	 * 
	 * @param systemState
	 *        File revisions in this snapshot
	 */
	public Snapshot(Set<IVerFsSimpleRevision> systemState) {
		this.systemState = systemState;
		this.snapshotPeriod = new Pair<Date, Date>();

		this.coveredCommit = new ArrayList<IVerFsCommit>();
	}

	/**
	 * Build a snapshot with the given file revisions list, and the given
	 * validity period
	 * 
	 * @param systemState
	 * @param snapshotPeriod
	 */
	public Snapshot(
		Set<IVerFsSimpleRevision> systemState,
		Pair<Date, Date> snapshotPeriod) {
		this(systemState);

		setSnapshotPeriod(snapshotPeriod);
	}

	/**
	 * Allows to set snapshot start time
	 * 
	 * @param endTime
	 */
	public void setSnapshotStartTime(Date startTime) {
		this.snapshotPeriod.car(startTime);
	}

	/**
	 * Returns snapshot start time. The time is an included bound.
	 * 
	 * @return
	 */
	public Date getSnapshotStartTime() {
		return this.snapshotPeriod.car();
	}

	/**
	 * Allows to set snapshot end time
	 * 
	 * @param endTime
	 */
	public void setSnapshotEndTime(Date endTime) {
		this.snapshotPeriod.cdr(endTime);
	}

	/**
	 * Returns snapshot end time. The time is an excluded bound.<br>
	 * If snapshot is the last on repository history, returned time will be
	 * Snapshot.INFINITE_TIME.
	 * 
	 * @return
	 */
	public Date getSnapshotEndTime() {
		return this.snapshotPeriod.cdr();
	}

	/**
	 * Returns snapshot period as a pair of date. First element is snapshot
	 * start time, and second element is snapshot end time.
	 * 
	 * @return
	 */
	public Pair<Date, Date> getSnapshotPeriod() {
		return this.snapshotPeriod;
	}

	/**
	 * Allows to set snapshot period from time bounds
	 * 
	 * @param startTime
	 * @param endTime
	 */
	public void setSnapshotPeriod(Date startTime, Date endTime) {
		this.snapshotPeriod.car(startTime);
		this.snapshotPeriod.cdr(endTime);
	}

	/**
	 * Allows to set snapshot period from a pair of Date witch represent time
	 * bounds
	 * 
	 * @param snapshotPeriod
	 */
	public void setSnapshotPeriod(Pair<Date, Date> snapshotPeriod) {
		this.snapshotPeriod.copy(snapshotPeriod);
	}

	/**
	 * Returns files revisions for this snapshot.
	 * 
	 * @return
	 */
	public Set<IVerFsSimpleRevision> getFilesRev() {
		return this.systemState;
	}

	/**
	 * Returns files revisions for this snapshot, and for a given file.
	 */
	public Set<IVerFsSimpleRevision> getFilesRev(IVerFsFile file) {
		Set<IVerFsSimpleRevision> toRet = new HashSet<IVerFsSimpleRevision>();
		for (IVerFsSimpleRevision rev : this.systemState) {
			if (rev.getFile() == file)
				toRet.add(rev);
		}

		return toRet;
	}

	/**
	 * Returns a set of snapshot's files
	 * 
	 * @return
	 */
	public Set<IVerFsFile> getFiles() {
		Set<IVerFsFile> toRet = new HashSet<IVerFsFile>();
		for (IVerFsSimpleRevision rev : this.systemState) {
			toRet.add(rev.getFile());
		}

		return toRet;
	}

	public void addCoveredCommit(IVerFsCommit commit) {
		this.coveredCommit.add(commit);
	}

	public void addAllCoveredCommits(Collection<IVerFsCommit> commits) {
		this.coveredCommit.addAll(commits);
	}

	public List<IVerFsCommit> getCoveredCommits() {
		return this.coveredCommit;
	}

	/**
	 * Add a revision to snapshot.
	 * 
	 * @param revision
	 */
	public void add(IVerFsSimpleRevision revision) {
		this.systemState.add(revision);
	}

	/**
	 * Add several revisions to snapshot.
	 * 
	 * @param revisions
	 */
	public void addAll(Collection<IVerFsSimpleRevision> revisions) {
		this.systemState.addAll(revisions);
	}

	/**
	 * Returns true if this snapshot contains the given file.
	 * 
	 * @param file
	 * @return
	 */
	public boolean containsFile(IVerFsFile file) {
		for (IVerFsSimpleRevision rev : this.systemState) {
			if (rev.getFile() == file)
				return true;
		}

		return false;
	}
	
	@SuppressWarnings("unused")
	public void generateDataIn(File targetDir) {
		
	}

	/**
	 * Makes and returns files revisions difference with the given snapshot.
	 * 
	 * @param from
	 * @return
	 */
	public SnapshotDiff<IVerFsSimpleRevision> filesRevDiff(Snapshot from) {
		Set<IVerFsSimpleRevision> added = new HashSet<IVerFsSimpleRevision>();
		Set<IVerFsSimpleRevision> removed = new HashSet<IVerFsSimpleRevision>();

		added.addAll(getFilesRev());
		added.removeAll(from.getFilesRev());

		removed.addAll(from.getFilesRev());
		removed.removeAll(getFilesRev());

		return new SnapshotDiff<IVerFsSimpleRevision>(added, removed);
	}

	/**
	 * Makes and returns files difference with the given snapshot.
	 * 
	 * @param from
	 * @return
	 */
	public SnapshotDiff<IVerFsFile> filesDiff(Snapshot from) {
		Set<IVerFsFile> added = new HashSet<IVerFsFile>();
		Set<IVerFsFile> removed = new HashSet<IVerFsFile>();

		added.addAll(getFiles());
		added.removeAll(from.getFiles());

		removed.addAll(from.getFiles());
		removed.removeAll(getFiles());

		return new SnapshotDiff<IVerFsFile>(added, removed);
	}

	/**
	 * Makes and returns data difference with the given snapshot.<br>
	 * Data difference, shows difference at a file content level.
	 * 
	 * @param from
	 * @return
	 */
	public SnapshotDiff<IFsFileEntity> dataDiff(Snapshot from) {
		Set<IFsFileEntity> added = new HashSet<IFsFileEntity>();
		Set<IFsFileEntity> removed = new HashSet<IFsFileEntity>();

		for (IVerFsSimpleRevision rev : getFilesRev()) {
			added.add(rev.getFileRevLocation());
		}

		for (IVerFsSimpleRevision rev : from.getFilesRev()) {
			added.remove(rev.getFileRevLocation());
		}

		for (IVerFsSimpleRevision rev : from.getFilesRev()) {
			removed.add(rev.getFileRevLocation());
		}

		for (IVerFsSimpleRevision rev : getFilesRev()) {
			removed.remove(rev.getFileRevLocation());
		}

		return new SnapshotDiff<IFsFileEntity>(added, removed);
	}

	/**
	 * Will compare snapshot in order on period start time, period end time,
	 * #commits, commits committing time, #revisions, revision 
	 */
	@Override
	public int compareTo(Snapshot o) {
		int result;

		result = getSnapshotStartTime().compareTo(o.getSnapshotStartTime());
		if (result != 0)
			return result;

		result = getSnapshotEndTime().compareTo(o.getSnapshotEndTime());
		if (result != 0)
			return result;

		result = getCoveredCommits().size() - o.getCoveredCommits().size();
		if (result != 0)
			return result;

		for (int i = 0; i < getCoveredCommits().size(); i++) {
			if (getCoveredCommits().get(i) != o.getCoveredCommits().get(i)) {
				result =
					getCoveredCommits()
						.get(i)
						.getCommittingTime()
						.getTime()
						.compareTo(
							o
								.getCoveredCommits()
								.get(i)
								.getCommittingTime()
								.getTime());
				
				if (result != 0)
					return result;
				else
					return -1;
			}
		}

		result = getFilesRev().size() - o.getFilesRev().size();
		if (result != 0)
			return result;

		for (IVerFsSimpleRevision rev : getFilesRev()) {
			if (!o.getFilesRev().contains(rev)) {
				return 1;
			}
		}

		return 0;
	}

}
