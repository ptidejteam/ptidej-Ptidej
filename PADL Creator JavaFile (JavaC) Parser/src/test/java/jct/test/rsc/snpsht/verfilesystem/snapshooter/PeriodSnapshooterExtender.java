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

import jct.test.rsc.snpsht.utils.Pair;

public class PeriodSnapshooterExtender extends AbstractSnapshooterExtender {
	private Pair<Integer, Integer> period;

	public PeriodSnapshooterExtender(ISnapshooter extendedSnapshooter) {
		super(extendedSnapshooter);

		this.period = new Pair<Integer, Integer>();
		
		// Set a period witch cover all snapshots
		setShotPeriod(
			extendedSnapshooter.getSnapshot(0).getSnapshotStartTime(),
			extendedSnapshooter
				.getSnapshot(extendedSnapshooter.getSnapshotCount() - 1)
				.getSnapshotStartTime());
	}

	/**
	 * Set an including time period
	 * 
	 * @param startTime
	 * @param endTime
	 * @throws IllegalArgumentException
	 *         If startTime > endTime
	 */
	public void setShotPeriod(Date startTime, Date endTime)
			throws IllegalArgumentException {
		Pair<Integer, Integer> bounds =
			getExtendedSnapshooter().getPeriodBounds(startTime, endTime);

		if (bounds.car() > bounds.cdr())
			throw new IllegalArgumentException(
				"Illegal period : start time > end time");

		if (bounds.car() < 0)
			bounds.car(0);

		if (bounds.car() > getExtendedSnapshooter().getSnapshotCount() - 1)
			bounds.car(getExtendedSnapshooter().getSnapshotCount() - 1);

		if (bounds.cdr() > getExtendedSnapshooter().getSnapshotCount())
			bounds.cdr(getExtendedSnapshooter().getSnapshotCount());

		this.period.copy(bounds);
	}

	@Override
	public Pair<Integer, Integer> getPeriodBounds(Date startTime, Date endTime)
			throws IllegalArgumentException {
		int index;
		Pair<Integer, Integer> bounds =
			this.getExtendedSnapshooter().getPeriodBounds(startTime, endTime);

		index = bounds.car() - this.period.car();
		bounds.car(Math.min(Math.max(-1, index), getSnapshotCount()));

		index = bounds.cdr() - this.period.car();
		bounds.cdr(Math.min(Math.max(-1, index), getSnapshotCount()));

		return bounds;
	}

	/**
	 * Returns snapshot at index i
	 * 
	 * @param i
	 *        Snapshot index
	 * @return Snapshot at index i
	 * @throws IllegalArgumentException
	 *         If index out of bounds : [0, getSnapshotCount()[
	 */
	@Override
	public Snapshot getSnapshot(int i) throws IllegalArgumentException {
		if (i < 0 || i >= getSnapshotCount())
			throw new IllegalArgumentException("Index out of bounds");

		return this.getExtendedSnapshooter().getSnapshot(i + this.period.car());
	}

	@Override
	public int getSnapshotCount() {
		return this.period.cdr() - this.period.car();
	}

	@Override
	public int getSnapshotIndex(Date d) {
		int index =
			this.getExtendedSnapshooter().getSnapshotIndex(d)
					- this.period.car();
		return Math.min(Math.max(-1, index), getSnapshotCount());
	}

}
