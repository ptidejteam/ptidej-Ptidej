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
package jct.test.rsc.snpsht.verfilesystem.flag;

import java.util.Date;

import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.attribute.IVerFsTime;
import jct.test.rsc.snpsht.verfilesystem.attribute.VerFsUpdatePeriod;
import jct.test.rsc.snpsht.verfilesystem.attribute.VerFsUpdateTime;

public class VerFsCommit extends AbstractVerFsFlag implements IVerFsCommit {
	public IVerFsTime committingTime;

	/**
	 * Returns committing time.<br>
	 * In some version manager (i.e. CVS), there is no information about exactly
	 * when a commit was made, in this case, update time must be computed. The
	 * returned time will be a VerFsUpdatePeriod, start time of this period will
	 * be the lowest update time among all revisions in commit and end time will
	 * be the highest. Of course, this VerFsUpdatePeriod can be use as a
	 * IVerFsTime. In this case function getTime() will return the most valuable
	 * time i.e. lowest update time.<br>
	 * 
	 * @return Committing time.
	 */
	@Override
	public IVerFsTime getCommittingTime() {

		if (this.committingTime != null) {
			return this.committingTime;
		} else {
			Date min = null, max = null;

			for (VerFsFileRev child : getChildren()) {
				if (min == null
						|| child.getUpdateTime().getTime().compareTo(min) < 0) {
					min = child.getUpdateTime().getTime();
				}

				if (max == null
						|| child.getUpdateTime().getTime().compareTo(max) > 0) {
					max = child.getUpdateTime().getTime();
				}
			}

			return new VerFsUpdatePeriod(min, max);
		}
	}

	/**
	 * Set committing time from a Date.<br>
	 * 
	 * @param d
	 */
	public void setCommittingTime(Date d) {
		setCommittingTime(new VerFsUpdateTime(d));
	}

	/**
	 * Set committing time from a IVerFsTime.<br>
	 * 
	 * @param committingTime
	 */
	public void setCommittingTime(IVerFsTime committingTime) {
		this.committingTime = committingTime;
	}
}
