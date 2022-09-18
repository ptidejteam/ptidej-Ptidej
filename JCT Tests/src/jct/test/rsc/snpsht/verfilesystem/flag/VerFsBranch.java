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
import jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsBranchAction;

public class VerFsBranch extends AbstractVerFsFlag implements IVerFsBranch {
	private String branch;
	protected IVerFsTime creationTime;

	public VerFsBranch(String branch) {
		this.branch = branch;
	}

	@Override
	public String getValue() {
		return this.branch;
	}

	/**
	 * Returns branch creation time.<br>
	 * Because some version manager, like CVS, don't keep track of branch
	 * creation time, returned time may be computed (if no creation time was set
	 * on this VerFsBranch).<br>
	 * In this case, returned time will be a VerFsUpdatePeriod. Start time will
	 * be the lowest possible creation time, and and time the highest.
	 * 
	 * @return Branch creation time.
	 */
	@Override
	public IVerFsTime getCreationTime() {
		Date min = null;
		Date max = null;
		Date curr;
		VerFsUpdatePeriod currRevPeriod;

		if (this.creationTime != null) {
			return this.creationTime;
		} else {
			for (VerFsFileRev fileRev : getChildren()) {
				if (!(fileRev.getAction() instanceof VerFsBranchAction))
					continue;

				currRevPeriod = (VerFsUpdatePeriod) fileRev.getUpdateTime();

				// Catch the biggest update time among all previous files of
				// first revision on current branch
				curr = currRevPeriod.getStartTime();
				if (curr != null) {
					if (min == null || min.compareTo(curr) < 0) {
						min = curr;
					}
				}

				// Catch the smallest update time among all previous files of
				// first revision on current branch
				curr = currRevPeriod.getEndTime();
				if (curr != null) {
					if (max == null || max.compareTo(curr) > 0) {
						max = curr;
					}
				}
			}

			return new VerFsUpdatePeriod(min, max, false);
		}
	}

	/**
	 * Set creation time from a Date.<br>
	 * 
	 * @param creationTime
	 */
	public void setCreationTime(Date creationTime) {
		setCreationTime(new VerFsUpdateTime(creationTime));
	}

	/**
	 * Set creation time from a IVerFsTime.<br>
	 * 
	 * @param creationTime
	 */
	public void setCreationTime(IVerFsTime creationTime) {
		this.creationTime = creationTime;
	}

}
