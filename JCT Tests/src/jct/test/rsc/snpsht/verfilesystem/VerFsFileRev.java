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
package jct.test.rsc.snpsht.verfilesystem;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jct.test.rsc.snpsht.filesystem.IFsFileEntity;
import jct.test.rsc.snpsht.verfilesystem.attribute.VerFsUpdatePeriod;
import jct.test.rsc.snpsht.verfilesystem.attribute.VerFsUpdateTime;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsCommit;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsAuthor;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsComment;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsFile;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsFileName;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsRevID;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsTag;
import jct.test.rsc.snpsht.verfilesystem.flag.action.AbstractVerFsAction;

public class VerFsFileRev extends AbstractVerFsElement implements
		IVerFsSimpleRevision {
	private String id;

	private VerFsComment comment;
	private AbstractVerFsAction action;
	private VerFsAuthor author;
	private IVerFsBranch branch;
	private VerFsUpdateTime updateDate;
	private VerFsFileName fileName;
	private VerFsRevID revID;
	private Set<VerFsTag> tags;
	private IVerFsCommit commit;
	private VerFsFile file;

	private IFsFileEntity fileRevLocation;

	private VerFsFileRev prevRevision;
	private Set<VerFsFileRev> nextRevisions;

	protected VerFsFileRev(String id, String name, VerFsRepository parent) {

		super(name, parent);

		this.id = id;
		this.tags = new HashSet<VerFsTag>();
		this.nextRevisions = new HashSet<VerFsFileRev>();
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String prettyPrint() {
		return "+-" + this.name + " (" + this.id + ")";
	}

	protected void setFileRevLocation(IFsFileEntity fileRev) {
		this.fileRevLocation = fileRev;
	}

	@Override
	public IFsFileEntity getFileRevLocation() {
		return this.fileRevLocation;
	}

	@Override
	public VerFsComment getComment() {
		return this.comment;
	}

	protected void setComment(VerFsComment comment) {
		this.comment = comment;
	}

	@Override
	public VerFsAuthor getAuthor() {
		return this.author;
	}

	protected void setAuthor(VerFsAuthor author) {
		this.author = author;
	}

	@Override
	public AbstractVerFsAction getAction() {
		return this.action;
	}

	protected void setAction(AbstractVerFsAction action) {
		this.action = action;
	}

	@Override
	public IVerFsBranch getBranch() {
		return this.branch;
	}

	protected void setBranch(IVerFsBranch branch) {
		this.branch = branch;
	}

	@Override
	public VerFsFileName getFileName() {
		return this.fileName;
	}

	protected void setFileName(VerFsFileName fileName) {
		this.fileName = fileName;
	}

	@Override
	public VerFsRevID getRevID() {
		return this.revID;
	}

	protected void setRevID(VerFsRevID revID) {
		this.revID = revID;
	}

	@Override
	public Set<VerFsTag> getTags() {
		return this.tags;
	}

	protected void addTag(VerFsTag tag) {
		this.tags.add(tag);
	}

	@Override
	public IVerFsCommit getCommit() {
		return this.commit;
	}

	public void setCommit(IVerFsCommit commit) {
		this.commit = commit;
	}

	@Override
	public VerFsFile getFile() {
		return this.file;
	}

	protected void setFile(VerFsFile file) {
		this.file = file;
	}

	/**
	 * Returns file revision creation time.<br>
	 * If file revision as no update time, an update period will be compute and
	 * return. So in this case, Returned time will be a IVerFsPeriod<br>
	 * This behavior is need for some king of revisions in some version server
	 * type. For example, CVS don't keep track of update time for first file
	 * revision of a branch (not for trunk). It results that creation time of
	 * these revisions is include after creation time of previous revision, and
	 * the lowest creation time of next revision.
	 * 
	 * @return File revision update time
	 */
	@Override
	public VerFsUpdateTime getUpdateTime() {
		return (this.updateDate == null ? compUpdatePeriod() : this.updateDate);
	}

	private VerFsUpdatePeriod compUpdatePeriod() {
		return new VerFsUpdatePeriod(
			getFileRevMinDate(),
			getFileRevMaxDate(),
			false);
	}

	// Returns next available time among previous revisions chain
	private Date getFileRevMinDate() {
		return getFileRevMinDate(this);
	}

	// Returns next available time among previous revisions chain
	private Date getFileRevMinDate(VerFsFileRev fileRev) {
		if (fileRev.getPrevRevision() == null
				|| fileRev.getPrevRevision() instanceof IVerFsNullRevision) {
			return null;
		} else {
			if (fileRev.getPrevRevision().getUpdateTime() == null) {
				return getFileRevMinDate(fileRev.getPrevRevision());
			} else {
				// Round time to the next available time (+1*10^-3 sec).
				return new Date(fileRev
					.getPrevRevision()
					.getUpdateTime()
					.getTime()
					.getTime() + 1L);
			}
		}
	}

	// Returns minimum time among next revisions
	private Date getFileRevMaxDate() {
		return getFileRevMaxDate(this);
	}

	// Returns minimum time among next revisions
	private Date getFileRevMaxDate(VerFsFileRev fileRev) {
		Date min = null;
		Date curr;

		for (VerFsFileRev nextRev : fileRev.getNextRevisions()) {
			if (nextRev.getUpdateTime() == null) {
				curr = getFileRevMaxDate(nextRev);
			} else {
				curr = nextRev.getUpdateTime().getTime();
			}

			if (curr != null && (min == null || curr.compareTo(min) < 0)) {
				min = curr;
			}
		}

		// Round time to the next available previous time (-1*10^-3 sec).
		return (min == null ? null : new Date(min.getTime() - 1L));
	}

	/**
	 * Set file revision update time.<br>
	 * Because update time is an attribute an not a tag, it can be set directly
	 * (without manager)
	 * 
	 * @param date
	 *        Update time
	 */
	public void setUpdateTime(VerFsUpdateTime date) {
		this.updateDate = date;
	}

	@Override
	public Set<VerFsFileRev> getNextRevisions() {
		return this.nextRevisions;
	}

	@Override
	public VerFsFileRev getPrevRevision() {
		return this.prevRevision;
	}

	protected void setPrevRevision(VerFsFileRev prevRevision) {
		this.prevRevision = prevRevision;
	}

	protected void addNextRevision(VerFsFileRev nextRevision) {
		this.nextRevisions.add(nextRevision);
	}

	protected void removeNextRevision(VerFsFileRev nextRevision) {
		this.nextRevisions.remove(nextRevision);
	}

}
