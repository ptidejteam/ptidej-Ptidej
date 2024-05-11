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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jct.test.rsc.snpsht.utils.Pair;
import jct.test.rsc.snpsht.verfilesystem.IVerFsSimpleRevision;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsFile;

public class SystemState {
	private Map<IVerFsFile, FileState> systemSate;
	private Pair<Date, Date> period;

	public SystemState() {
		this.systemSate =
			new HashMap<IVerFsFile, FileState>();
	}

	public Map<IVerFsFile, FileState> getState() {
		return this.systemSate;
	}

	public void setPeriod(Pair<Date, Date> period) {
		this.period = period;
	}

	public Pair<Date, Date> getPeriod() {
		return this.period;
	}

	public boolean containsFile(IVerFsFile file) {
		return this.systemSate.containsKey(file);
	}

	public boolean containsFile(IVerFsSimpleRevision rev) {
		if (rev != null) {
			return containsFile(rev.getFile());
		} else {
			return false;
		}
	}
	
	public void addFile(IVerFsFile file) {
		this.systemSate.put(file, new FileState());
	}
	
	public boolean addFileIfNotExist(IVerFsFile file) {
		if(!containsFile(file)) {
			addFile(file);
			return true;
		} else {
			return false;
		}
	}
	
	public void setFileSate(IVerFsFile file, FileState state) {
		this.systemSate.put(file, state);
	}
	
	public FileState getFileState(IVerFsFile file) {
		return this.systemSate.get(file);
	}
	
	public Set<IVerFsFile> getFiles() {
		return this.systemSate.keySet();
	}
	
	
	public class FileState {
		private Map<IVerFsBranch, List<IVerFsSimpleRevision>> fileState;

		public FileState() {
			this.fileState = new HashMap<IVerFsBranch, List<IVerFsSimpleRevision>>();
		}

		public Map<IVerFsBranch, List<IVerFsSimpleRevision>> getFileSate() {
			return this.fileState;
		}
		
		public boolean containsBranch(IVerFsBranch branch) {
			return this.fileState.containsKey(branch);
		}
		
		public void addBranch(IVerFsBranch branch) {
			this.fileState.put(branch, new ArrayList<IVerFsSimpleRevision>());
		}
		
		public boolean addBranchIfNotExist(IVerFsBranch branch) {
			if (!containsBranch(branch)) {
				addBranch(branch);
				return true;
			} else {
				return false;
			}
		}
		
		/**
		 * Add given revision on its branch.
		 * @param rev Revision to add
		 */
		public void addRevision(IVerFsSimpleRevision rev) {
			addRevision(rev, rev.getBranch());
		}
		
		public void addRevision(IVerFsSimpleRevision rev, IVerFsBranch branch) {
			if (!containsBranch(branch)) {
				throw new IllegalArgumentException("Unknow branch " + branch.getValue());
			} else {
				this.fileState.get(branch).add(rev);
			}
		}
		
		public Set<IVerFsBranch> getBranches() {
			return this.fileState.keySet();
		}

		public List<IVerFsSimpleRevision> getBranchState(IVerFsBranch branch) {
			return this.fileState.get(branch);
		}

		public void setBranchSate(IVerFsBranch branch, List<IVerFsSimpleRevision> state) {
			this.fileState.put(branch, state);
		}
	}
}
