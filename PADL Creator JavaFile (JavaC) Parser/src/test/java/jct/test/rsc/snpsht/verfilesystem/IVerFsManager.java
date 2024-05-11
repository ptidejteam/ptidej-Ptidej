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

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import jct.test.rsc.snpsht.filesystem.IFsManager;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsAuthor;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsComment;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsCommit;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsFile;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsFileName;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsRevID;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsTag;
import jct.test.rsc.snpsht.verfilesystem.flag.action.IVerFsAction;

public interface IVerFsManager {
	public IVerFsComplexElement makeRoot(String repository);
	public IVerFsComplexElement getRoot();

	public String getRevmlVersion();

	public String getDescription();

	public Date getUpdateDate();

	public IVerFsElement getElement(String path);
	public IVerFsSimpleRevision getSimpleRevision(String id);
	public Collection<? extends IVerFsSimpleRevision> getAllSimplesRevisions();

	public String toFullPath(String path);
	public String toRelativePath(String path);
	public boolean isValidPath(String path);

	public Collection<? extends IVerFsComment> getAllComments();
	public IVerFsComment getComment(String commentString);

	public IVerFsAction[] getAllActions();
	public IVerFsAction getAction(String actionString);
	IVerFsAction getAction(Class<? extends IVerFsAction> c);

	public Collection<? extends IVerFsAuthor> getAllAuthors();
	public IVerFsAuthor getAuthor(String authorString);

	public Collection<? extends IVerFsBranch> getAllBranches();
	public IVerFsBranch getBranch(String branchString);
	public IVerFsBranch getTrunk();

	public Collection<? extends IVerFsFileName> getAllFilesNames();
	public IVerFsFileName getFileName(String fnString);

	public Collection<? extends IVerFsRevID> getAllRevIDs();
	public IVerFsRevID getRevID(String idString);

	public Collection<? extends IVerFsFile> getAllFiles();
	public IVerFsFile getFile(String idString);

	public Collection<? extends IVerFsTag> getAllTags();
	public IVerFsTag getTag(String tagString);

	public Collection<? extends IVerFsCommit> getAllCommits();

	public IFsManager getSourceManager();
	public void setSource(File source) throws IOException;
}
