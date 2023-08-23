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
import jct.test.rsc.snpsht.verfilesystem.attribute.VerFsUpdateTime;

public class VerFsTrunk extends VerFsBranch implements IVerFsBranch {

	/**
	 * Default branch (trunk) as String
	 */
	public static final String TRUNK_BRANCH_NAME = "<trunk>";

	public VerFsTrunk() {
		super(null);
	}

	@Override
	public String getValue() {
		return TRUNK_BRANCH_NAME;
	}

	/**
	 * Returns trunk creation time.<br>
	 * Because some version manager, like CVS, don't keep track of branch
	 * creation time, returned time may be computed (if no creation time was set
	 * on this VerFsBranch).<br>
	 * In this case, returned time will be a VerFsUpdateTime with time of first
	 * trunk revision update time.
	 * 
	 * @return Branch creation time.
	 */
	@Override
	public IVerFsTime getCreationTime() {
		Date min = null;
		Date curr;

		if (this.creationTime != null) {
			return this.creationTime;
		} else {

			for (VerFsFileRev fileRev : getChildren()) {

				// Catch lowest update time among all children revisions
				curr = fileRev.getUpdateTime().getTime();
				if (curr != null) {
					if (min == null || min.compareTo(curr) > 0) {
						min = curr;
					}
				}
			}

			return new VerFsUpdateTime(min);
		}
	}

}
