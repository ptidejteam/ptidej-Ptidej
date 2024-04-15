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

import java.util.LinkedList;
import java.util.List;

import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch;

public abstract class AbstractMainBranchPrioSnapshooter extends AbstractMainSnapshooter {
	protected LinkedList<IVerFsBranch> branchesByPriority;
	protected IVerFsBranch defaultBranch;

	public AbstractMainBranchPrioSnapshooter(VerFsManager manager) {
		this(manager, manager.getTrunk());
	}

	public AbstractMainBranchPrioSnapshooter(
		VerFsManager manager,
		IVerFsBranch defaultBranch) {
		super(manager);

		this.branchesByPriority = new LinkedList<IVerFsBranch>();

		this.defaultBranch = defaultBranch;
	}

	public void setBranchPriority(List<IVerFsBranch> branchesByPriority) {
		this.branchesByPriority.clear();
		this.branchesByPriority.addAll(branchesByPriority);
	}

	public void addBranchHighestPriority(IVerFsBranch branch) {
		this.branchesByPriority.addLast(branch);
	}

	public void addBranchLowestPriority(IVerFsBranch branch) {
		this.branchesByPriority.addFirst(branch);
	}

	public List<IVerFsBranch> getBranchPriority() {
		return this.branchesByPriority;
	}

}
