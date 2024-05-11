/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package common.tools.time;

import java.util.Date;
import common.tools.constants.Constants;

public class Elapse implements Comparable<Elapse> {

	private final long elapseInMS;
	private final long days;
	private final int hours;
	private final int minutes;
	private final int seconds;
	private final int mSecond;
	private final String str;
	private final int hashCode;

	public Elapse(final Date start, final Date end) {

		if (start == null || end == null) {
			throw new IllegalArgumentException(
				"Both start and end time cannot be null.");
		}

		final StringBuffer buffer = new StringBuffer();
		this.elapseInMS = end.getTime() - start.getTime();

		this.days = this.elapseInMS / Constants.DAY_IN_MS;
		this.hours =
			(int) (this.elapseInMS % Constants.DAY_IN_MS / Constants.HOUR_IN_MS);
		this.minutes =
			(int) (this.elapseInMS % Constants.HOUR_IN_MS / Constants.MINUTE_IN_MS);
		this.seconds = (int) (this.elapseInMS % Constants.MINUTE_IN_MS / 1000);
		this.mSecond = (int) (this.elapseInMS % 1000);

		buffer
			.append(this.days)
			.append(" days ")
			.append(this.hours)
			.append(" hours ")
			.append(this.minutes)
			.append(" minutes ")
			.append(this.seconds)
			.append(" seconds ")
			.append(this.mSecond)
			.append(" ms");

		this.str = buffer.toString();
		this.hashCode = this.str.hashCode();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(final Elapse o) {
		if (this.elapseInMS > o.getElapseInMS()) {
			return 1;
		}
		else if (this.elapseInMS < o.getElapseInMS()) {
			return -1;
		}
		else {
			return 0;
		}

	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof Elapse)) {
			return false;
		}
		final Elapse theOther = (Elapse) obj;

		return this.elapseInMS == theOther.getElapseInMS();
	}

	public long getDays() {
		return this.days;
	}

	public long getElapseInMS() {
		return this.elapseInMS;
	}

	public int getHours() {
		return this.hours;
	}

	public int getMinutes() {
		return this.minutes;
	}

	public int getmSecond() {
		return this.mSecond;
	}

	public int getSeconds() {
		return this.seconds;
	}

	@Override
	public int hashCode() {
		return this.hashCode;
	}

	@Override
	public String toString() {
		return this.str;
	}

}
