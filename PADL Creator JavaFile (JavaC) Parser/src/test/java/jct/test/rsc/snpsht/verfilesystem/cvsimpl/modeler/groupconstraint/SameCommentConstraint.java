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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsComment;

public class SameCommentConstraint implements IGroupingConstraint {

	@Override
	public Set<IGroup> applyContrainst(Set<IGroup> groups) {
		Set<IGroup> outGroups = new HashSet<IGroup>();

		for (IGroup group : groups) {
			outGroups.addAll(applyConstraint(group));
		}

		return outGroups;
	}

	private Set<IGroup> applyConstraint(IGroup group) {
		VerFsComment currComment;
		IGroup currGroup;

		Map<VerFsComment, IGroup> groupsMap =
			new HashMap<VerFsComment, IGroup>();

		for (VerFsFileRev fileRev : group.getGroup()) {
			currComment = fileRev.getComment();
			if (currComment == null)
				throw new IllegalArgumentException("File revision '"
						+ fileRev.getId() + "' is not commented.\n"
						+ "All files revions should contains a comment.");

			currGroup = groupsMap.get(currComment);
			if (currGroup == null) {
				currGroup = new CommitGroup();
				groupsMap.put(currComment, currGroup);
			}

			currGroup.add(fileRev);
		}

		return new HashSet<IGroup>(groupsMap.values());
	}

	@Override
	public String[][] getAttributes() {
		return new String[][] {};
	}

	@Override
	public String getDescription() {
		return "Same comment grouping constraint";
	}

	@Override
	public String getName() {
		return "sameComment";
	}
}
