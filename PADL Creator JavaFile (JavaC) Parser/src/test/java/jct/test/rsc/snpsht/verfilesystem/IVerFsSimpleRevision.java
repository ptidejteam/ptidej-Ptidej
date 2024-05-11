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

import java.util.Set;

import jct.test.rsc.snpsht.filesystem.IFsFileEntity;
import jct.test.rsc.snpsht.verfilesystem.attribute.IVerFsTime;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsAuthor;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsComment;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsCommit;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsFile;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsFileName;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsRevID;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsTag;
import jct.test.rsc.snpsht.verfilesystem.flag.action.IVerFsAction;

public interface IVerFsSimpleRevision extends IVerFsElement {
	public IFsFileEntity getFileRevLocation();
	
	public IVerFsFile getFile();	
	
	public IVerFsComment getComment();	
	
	public IVerFsAuthor getAuthor();	
	
	public IVerFsBranch getBranch();
	
	public IVerFsFileName getFileName();
	
	public IVerFsAction getAction();
	
	public IVerFsTime getUpdateTime();
	
	public IVerFsRevID getRevID();
	
	public IVerFsCommit getCommit();
	
	public Set<? extends IVerFsTag> getTags();
	
	public IVerFsSimpleRevision getPrevRevision();
	
	public Set<? extends IVerFsSimpleRevision> getNextRevisions();
}
