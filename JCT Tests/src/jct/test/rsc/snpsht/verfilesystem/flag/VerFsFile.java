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

import jct.test.rsc.snpsht.filesystem.IFsFileEntity;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.VerFsNullRev;

public class VerFsFile extends AbstractVerFsFlag implements IVerFsFile {
	private String id;
	private VerFsNullRev nullRev;
	private IFsFileEntity fileLocation;

	/**
	 * Default constructor.<br>
	 * Create a CvsFsFile with the given id, null revision and children.<br>
	 * Children collection should not contains null revision.
	 * 
	 * @param id
	 *        File id
	 * @param nullRev
	 *        Null revision
	 * @param children
	 *        Children (null revision is not a child)
	 */
	public VerFsFile(String id, VerFsNullRev nullRev, VerFsFileRev[] children) {
		this.id = id;
		this.nullRev = nullRev;
		super.addChildren(children);
	}

	public void setFileLocation(IFsFileEntity fileLocation) {
		this.fileLocation = fileLocation;
	}

	@Override
	public IFsFileEntity getFileLocation() {
		return this.fileLocation;
	}

	@Override
	public String getId() {
		return this.id;
	}

//	@Override
//	public IVerFsSimpleRevision getFilesRevAt(Date at, IVerFsBranch branch) {
//		return getFileRevByBranchAt(at).get(branch);
//	}
//
//	@Override
//	public Collection<VerFsFileRev> getFilesRevAt(Date at) {
//		return getFileRevByBranchAt(at).values();
//	}
//
//	@Override
//	public Map<VerFsBranch, VerFsFileRev> getFileRevByBranchAt(Date at) {
//		Map<VerFsBranch, VerFsFileRev> candidatesMap =
//			new HashMap<VerFsBranch, VerFsFileRev>();
//
//		// Catch first rev.
//		VerFsFileRev currRev;
//		Stack<VerFsFileRev> waitingElems = new Stack<VerFsFileRev>();
//		waitingElems.push(getFirstRev());
//
//		while (!waitingElems.empty()) {
//			currRev = waitingElems.pop();
//
//			// Check if currRev update time is before 'at' time
//			if (currRev.getUpdateTime().getTime().compareTo(at) <= 0) {
//
//				// Check if this revision is delete
//				if (currRev.getAction().getAction().compareTo(
//					VerFsManager.DELETE_FILE_ACTION) == 0) {
//					// Remove current revision for this branch
//					candidatesMap.remove(currRev.getBranch());
//				} else {
//					// Current revision is a candidate for its branch
//					candidatesMap.put(currRev.getBranch(), currRev);
//				}
//
//				// But we need to check if its next revisions are not better
//				// candidates
//				for (VerFsFileRev nextRev : currRev.getNextRevisions())
//					waitingElems.push(nextRev);
//			}
//		}
//
//		return candidatesMap;
//	}

	public VerFsFileRev getFirstRev() {
		return this.nullRev.getNextRevision();
	}

	@Override
	public VerFsNullRev getNullRevision() {
		return this.nullRev;
	}
}
