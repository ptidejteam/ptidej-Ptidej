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
package jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.comparator.VerFsFileRevUpdateTimeComparator;

/**
 * Sliding windows constraint allows to group files revisions between theirs
 * update time.<br>
 * In plain English two files revisions are in the same group if the delta of
 * theirs update time is less than MAX_TIME attribute.<br>
 * This constraint should be the last to apply. Because for example, you should
 * not want to consider, as in the same group, two files witch have not the same
 * author, even if theirs update times are close.
 * 
 * @author Julien Tantéri
 * 
 */
public class SlidingWindowConstraint implements IGroupingConstraint {
	public static final double SLIDING_WINDOWS_DEFAULT_SIZE_SEC = 5.0 * 60.0;
	
	private double slidingSizeMilSec;
	
	public SlidingWindowConstraint() {
		this(SLIDING_WINDOWS_DEFAULT_SIZE_SEC);
	}
	
	public SlidingWindowConstraint(double slidingSizeSec) {
		this.slidingSizeMilSec = slidingSizeSec * 1000.0;
	}

	@Override
	public Set<IGroup> applyContrainst(Set<IGroup> groups) {
		Set<IGroup> outGroups = new HashSet<IGroup>();

		for (IGroup group : groups) {
			outGroups.addAll(applyConstraint(group));
		}

		return outGroups;
	}

	// Apply a sliding windows constraint to a group
	private Set<IGroup> applyConstraint(IGroup group) {
		IGroup currGroup;
		Date currTime = null;
		Set<IGroup> groups = new HashSet<IGroup>();

		// Sort file's group in update time order
		VerFsFileRev[] files = group.getGroup().toArray(new VerFsFileRev[0]);
		Arrays.sort(files, new VerFsFileRevUpdateTimeComparator());

		if (files.length > 0) {
			currGroup = new CommitGroup();
			groups.add(currGroup);
			currTime = files[0].getUpdateTime().getTime();

			// For each file (in time order)
			for (VerFsFileRev fileRev : files) {

				// If file revision update time exceed SLIDING_WINDOWS_SIZE, we
				// create a new group
				if (fileRev.getUpdateTime().getTime().getTime()
						- currTime.getTime() > this.slidingSizeMilSec) {
					currGroup = new CommitGroup();
					groups.add(currGroup);
				}

				currGroup.add(fileRev);
				currTime = fileRev.getUpdateTime().getTime();
			}
		} else {
			groups.add(group);
		}

		return groups;
	}
	
	@Override
	public String[][] getAttributes() {
		Double sizeSec = this.slidingSizeMilSec / 1000.0;
		return new String[][] {{"size", sizeSec.toString()}};
	}

	@Override
	public String getDescription() {
		return "Sliding windows grouping constraint";
	}

	@Override
	public String getName() {
		return "slidingWindows";
	}
}
