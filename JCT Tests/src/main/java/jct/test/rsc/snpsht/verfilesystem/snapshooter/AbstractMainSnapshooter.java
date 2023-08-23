	/*
	 * (c) Copyright 2008 and following years, Julien Tanteri, University of
	 * Montreal.
	 * 
	 * Use and copying of this software and preparation of derivative works
	 * based upon this software are permitted. Any copy of this software or of
	 * any derivative work must include the above copyright notice of the
	 * author, this paragraph and the one after it.
	 * 
	 * This software is made available AS IS, and THE AUTHOR DISCLAIMS ALL
	 * WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE IMPLIED
	 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE, AND
	 * NOT WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN, ANY LIABILITY FOR
	 * DAMAGES RESULTING FROM THE SOFTWARE OR ITS USE IS EXPRESSLY DISCLAIMED,
	 * WHETHER ARISING IN CONTRACT, TORT (INCLUDING NEGLIGENCE) OR STRICT
	 * LIABILITY, EVEN IF THE AUTHOR IS ADVISED OF THE POSSIBILITY OF SUCH
	 * DAMAGES.
	 * 
	 * All Rights Reserved.
	 */
	package jct.test.rsc.snpsht.verfilesystem.snapshooter;

		import java.util.Arrays;
	import java.util.Date;
	import javax.swing.event.EventListenerList;

import jct.test.rsc.snpsht.utils.Pair;
import jct.test.rsc.snpsht.verfilesystem.IVerFsSimpleRevision;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import jct.test.rsc.snpsht.verfilesystem.comparator.VerFsCommitStartTimeComparator;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsCommit;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsFile;
import jct.test.rsc.snpsht.verfilesystem.snapshooter.SystemState.FileState;

	public abstract class AbstractMainSnapshooter implements IMainSnapshooter {
		private VerFsManager manager;
		private EventListenerList listeners;
		private Pair<IVerFsCommit, SystemState>[] systemStates;

		public AbstractMainSnapshooter(VerFsManager manager) {
			this.manager = manager;

			this.listeners = new EventListenerList();
			initCommitsMap();
		}

		@SuppressWarnings("unchecked")
		protected void initCommitsMap() {
			IVerFsCommit[] commits =
				this.manager.getAllCommits().toArray(new IVerFsCommit[0]);
			Arrays.sort(commits, new VerFsCommitStartTimeComparator());

			this.systemStates = new Pair[commits.length];

			for (int i = 0; i < commits.length; i++) {
				this.systemStates[i] =
					new Pair<IVerFsCommit, SystemState>(commits[i]);
			}
		}

		@Override
		public void setManager(VerFsManager manager) {
			this.manager = manager;
			initCommitsMap();
		}

		@Override
		public VerFsManager getManager() {
			return this.manager;
		}

		@Override
		public IVerFsCommit getCommit(int i) {
			return this.systemStates[i].car();
		}

		@Override
		public int getSnapshotIndex(Date d) {
			int i = -1;
			IVerFsCommit currCommit;

			for (Pair<IVerFsCommit, SystemState> pair : this.systemStates) {
				currCommit = pair.car();
				if (currCommit.getCommittingTime().getTime().compareTo(d) <= 0)
					i++;
				else
					break;
			}

			return i;
		}

		/**
		 * Compute and returns index bounds for the given period.<br>
		 * If startTime or endTime is before first commit time, -1 will be
		 * return as start/end index. <br>
		 * If startTime or endTime is after last commit time, getSnapshotCount()
		 * will be return as start/end index.<br>
		 * 
		 * @param startTime
		 *        Period start time (include)
		 * @param endTime
		 *        Period end time (exclude)
		 * @return A pair of integer like with car = start index (include), and
		 *         cdr = end index (exclude)
		 * 
		 * @throws IllegalArgumentException
		 *         If startTime > endTime
		 */
		@Override
		public Pair<Integer, Integer> getPeriodBounds(
			Date startTime,
			Date endTime) throws IllegalArgumentException {
			int startIndex = -1, endIndex = 0;
			IVerFsCommit currCommit;

			if (endTime.compareTo(startTime) <= 0) {
				throw new IllegalArgumentException(
					"Illegal period : start time >= end time");
			}

			for (Pair<IVerFsCommit, SystemState> pair : this.systemStates) {
				currCommit = pair.car();

				if (currCommit.getCommittingTime().getTime().compareTo(
					startTime) <= 0)
					startIndex++;

				if (currCommit.getCommittingTime().getTime().compareTo(endTime) < 0)
					endIndex++;
				else
					break;
			}

			return new Pair<Integer, Integer>(startIndex, endIndex);
		}

		/*
		 * Returns system state validity period for the given index. Throws
		 * IllegalArgumentException if index out of bounds
		 */
		private Pair<Date, Date> getSystemStatePeriodValidity(int index)
				throws IllegalArgumentException {
			Pair<Date, Date> period = new Pair<Date, Date>();

			if (index < 0 || index >= getSnapshotCount()) {
				throw new IllegalArgumentException("Index out of bounds");
			}

			for (int i = 0; i <= index && i < getSnapshotCount(); i++) {
				// Check if period define by commit time
				if (period.cdr() == null
						|| getCommit(i)
							.getCommittingTime()
							.getTime()
							.compareTo(period.cdr()) >= 0) {
					period.car(getCommit(i).getCommittingTime().getTime());
				} else {
					period.car(new Date(period.cdr().getTime()));
				}

				if (i < getSnapshotCount() - 1) {
					if (getCommit(i + 1)
						.getCommittingTime()
						.getTime()
						.compareTo(period.car()) > 0) {
						period.cdr(getCommit(i + 1)
							.getCommittingTime()
							.getTime());
					} else {
						period.cdr(new Date(period.car().getTime() + 1L));
						period.cdr(getCommit(i + 1)
							.getCommittingTime()
							.getTime());
					}
				} else {
					period.cdr(Snapshot.INFINITE_TIME);
				}
			}

			return period;
		}

		private SystemState generateNextSystemState(
			IVerFsCommit nextCommit,
			SystemState currSystemSate) {

			SystemState snapshotContent = new SystemState();

			FileState fileContent;

			// Generate snapshot for each revisions in commit
			for (IVerFsSimpleRevision fileRev : nextCommit.getChildren()) {
				snapshotContent.addFileIfNotExist(fileRev.getFile());

				fileContent = snapshotContent.getFileState(fileRev.getFile());

				fileContent.addBranchIfNotExist(fileRev.getBranch());

				fileContent.addRevision(fileRev);
			}

			// Complete snapshot with rest of revisions (witch not changed)
			for (IVerFsFile file : currSystemSate.getFiles()) {
				if (!snapshotContent.containsFile(file)) {
					snapshotContent.setFileSate(file, currSystemSate
						.getFileState(file));
				} else {
					fileContent = snapshotContent.getFileState(file);
					for (IVerFsBranch branch : currSystemSate
						.getFileState(file)
						.getBranches()) {
						if (!fileContent.containsBranch(branch)) {
							fileContent.setBranchSate(branch, currSystemSate
								.getFileState(file)
								.getBranchState(branch));
						}
					}
				}
			}

			return snapshotContent;
		}

		/**
		 * Returns system state for commit at index i.<br>
		 * Will return null if index out of bounds
		 * 
		 * @param i
		 * @return
		 */
		protected SystemState getSystemSate(int i) {
			SystemState prevSs, currSs;

			if (i < 0 || i > this.systemStates.length - 1)
				return null;

			currSs = this.systemStates[i].cdr();
			if (currSs != null)
				return currSs;

			if (i == 0) {
				currSs =
					generateNextSystemState(
						this.systemStates[i].car(),
						new SystemState());
			} else {
				prevSs = getSystemSate(i - 1);
				currSs =
					generateNextSystemState(this.systemStates[i].car(), prevSs);
			}

			currSs.setPeriod(getSystemStatePeriodValidity(i));
			this.systemStates[i].cdr(currSs);

			return currSs;
		}

		@Override
		public Snapshot getSnapshot(Date at) {
			return getSnapshot(getSnapshotIndex(at));
		}

		@Override
		public int getSnapshotCount() {
			return this.systemStates.length;
		}

		@Override
		public SnapshotIterator iterator() {
			return new SnapshotIterator(this);
		}

		@Override
		public void addListener(ISnapshooterListener listener) {
			this.listeners.add(ISnapshooterListener.class, listener);
		}

		@Override
		public ISnapshooterListener[] getListeners() {
			return this.listeners.getListeners(ISnapshooterListener.class);
		}

		@Override
		public void removeListener(ISnapshooterListener listener) {
			this.listeners.remove(ISnapshooterListener.class, listener);
		}
	}
