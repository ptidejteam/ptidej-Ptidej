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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jct.test.rsc.snpsht.filesystem.FsManagerFactory;
import jct.test.rsc.snpsht.filesystem.IFsFileEntity;
import jct.test.rsc.snpsht.filesystem.IFsManager;
import jct.test.rsc.snpsht.verfilesystem.attribute.VerFsUpdateTime;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.visitor.FindOrMakeFileRev;
import jct.test.rsc.snpsht.verfilesystem.flag.AbstractVerFsFlag;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsAuthor;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsBranch;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsComment;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsCommit;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsFile;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsFileName;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsRevID;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsTag;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsTrunk;
import jct.test.rsc.snpsht.verfilesystem.flag.action.AbstractVerFsAction;
import jct.test.rsc.snpsht.verfilesystem.flag.action.IVerFsAction;
import jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsBranchAction;
import jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsCreateAction;
import jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsDeleteAction;
import jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsEditAction;
import jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsMoveAction;
import jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsResurrectAction;

public class VerFsManager implements IVerFsManager {
	private IFsManager fileManager;

	private VerFsRootRepository root;
	private String revmlVersion;
	private String description;
	private Date updateDate;

	private Map<String, VerFsFileRev> filesRevMap;

	private Map<String, VerFsFile> fileMap;
	private Map<String, VerFsComment> commentMap;
	private Map<String, VerFsAuthor> authorMap;
	private Map<String, VerFsBranch> branchMap;
	private Map<String, VerFsFileName> fileNameMap;
	private Map<String, VerFsRevID> revIDMap;
	private Map<String, VerFsTag> tagsMap;

	private AbstractVerFsAction[] actions;

	private Set<VerFsCommit> commitSet;

	/**
	 * Default constructor
	 */
	public VerFsManager() {
		this.filesRevMap = new HashMap<String, VerFsFileRev>();

		this.fileMap = new HashMap<String, VerFsFile>();
		this.commentMap = new HashMap<String, VerFsComment>();
		this.authorMap = new HashMap<String, VerFsAuthor>();
		this.branchMap = new HashMap<String, VerFsBranch>();
		this.fileNameMap = new HashMap<String, VerFsFileName>();
		this.revIDMap = new HashMap<String, VerFsRevID>();
		this.tagsMap = new HashMap<String, VerFsTag>();
		this.commitSet = new HashSet<VerFsCommit>();
	}

	/**
	 * Create root node from repository path and return it.
	 * 
	 * @param repositoryPath
	 *        Path to root node.
	 * @return Root node.
	 */
	@Override
	public VerFsRootRepository makeRoot(String repositoryPath) {
		String repoName;
		// String repoPath;

		this.filesRevMap.clear();
		this.commentMap.clear();
		this.authorMap.clear();
		this.branchMap.clear();
		this.fileNameMap.clear();
		this.revIDMap.clear();
		this.tagsMap.clear();

		this.actions =
			new AbstractVerFsAction[] { new VerFsBranchAction(),
					new VerFsCreateAction(), new VerFsDeleteAction(),
					new VerFsEditAction(), new VerFsMoveAction(),
					new VerFsResurrectAction() };

		this.branchMap.put(VerFsTrunk.TRUNK_BRANCH_NAME, new VerFsTrunk());

		this.commitSet.clear();

		if (repositoryPath.endsWith("/")) {
			repoName = repositoryPath.substring(0, repositoryPath.length() - 1);
			// repoPath = repoName;
		} else {
			repoName = repositoryPath;
			// repoPath = repositoryPath;
		}

		if (repositoryPath.startsWith("/")) {
			repoName = repositoryPath.substring(1);
		} else {
			// repoPath = "/" + repositoryPath;
		}

		this.root = new VerFsRootRepository(repoName);

		return this.root;
	}

	/**
	 * Returns CVS file system manager's root node.
	 * 
	 * @return CVS file system root node
	 */
	@Override
	public VerFsRootRepository getRoot() {
		return this.root;
	}

	/**
	 * Returns RevML version
	 */
	@Override
	public String getRevmlVersion() {
		return this.revmlVersion;
	}

	/**
	 * Set RevML version
	 * 
	 * @param revmlVersion
	 */
	public void setRevmlVersion(String revmlVersion) {
		this.revmlVersion = revmlVersion;
	}

	/**
	 * Returns repository/CVS server description.
	 */
	@Override
	public String getDescription() {
		return this.description;
	}

	/**
	 * Set repository/CVS server description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public VerFsFileRev getElement(String path) {
		return null;
	}

	@Override
	public VerFsFileRev getSimpleRevision(String id) {
		return this.filesRevMap.get(id);
	}

	@Override
	public Collection<VerFsFileRev> getAllSimplesRevisions() {
		return this.filesRevMap.values();
	}

	/**
	 * Converts a relative path to full path in it not already done.
	 * 
	 * @param path
	 *        Relative path to parse.
	 * @return Full path
	 */
	@Override
	public String toFullPath(String path) {
		if (!path.startsWith("/")) {
			return "/" + this.root.getName() + "/" + path;
		} else {
			return path;
		}
	}

	/**
	 * Converts a full path to relative path if not already done.<br>
	 * If the full path is misformed, this function will returns null.
	 * 
	 * @param path
	 *        Full path to parse.
	 * @return Relative path, or null if can't parse input path.
	 */
	@Override
	public String toRelativePath(String path) {
		String rootPath;

		if (!path.startsWith("/")) {
			return path;
		} else {
			rootPath = "/" + this.root.getName() + "/";
			if (path.startsWith(rootPath) && path.length() > rootPath.length()) {
				return path.substring(("/" + this.root.getName() + "/")
					.length());
			} else {
				return null;
			}
		}
	}

	/**
	 * Returns true if path is a valid path, false else.
	 * 
	 * @param path
	 *        Path to validate.
	 * @return true if path is a valid path, false else.
	 */
	@Override
	public boolean isValidPath(String path) {
		String fullPath = toFullPath(path);
		String[] pathTokens = fullPath.split("/");

		if (pathTokens.length > 2 && pathTokens[0].compareTo("") == 0
				&& pathTokens[1].compareTo(this.root.getName()) == 0
				&& pathTokens[2].compareTo("") != 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Add a complex element (i.e. {@linkplain jct.test.rsc.snpsht.verfilesystem.VerFsRepository})
	 * to file system.<br>
	 * This function allows to add a complex element to file system from its
	 * name and its parent.
	 * 
	 * @param parent
	 *        Parent of complex element to add.
	 * @param name
	 *        Name of complex element to add
	 * @return Added complex element.
	 */
	public VerFsRepository addComplexElement(VerFsRepository parent, String name) {
		VerFsRepository newRep;

		if (parent == null || name.compareTo("") == 0
				|| parent.containsChildId(name)) {
			return null;
		} else {
			newRep = new VerFsRepository(name, parent);
			parent.addChild(newRep);
			return newRep;
		}
	}

	/**
	 * Add a simple element (i.e. {@linkplain jct.test.rsc.snpsht.verfilesystem.VerFsRepository} )
	 * to file system.<br>
	 * This function allows to add a simple element to file system from its name
	 * and its parent.
	 * 
	 * @param parent
	 *        Parent of complex element to add.
	 * @param name
	 *        Name of complex element to add
	 * @return Added complex element.
	 */
	public VerFsFileRev addSimpleRevision(
		VerFsRepository parent,
		String name,
		String id) {
		VerFsFileRev newFile;

		if (parent == null || name.compareTo("") == 0
				|| this.filesRevMap.containsKey(id)) {
			return null;
		} else {
			newFile = new VerFsFileRev(id, name, parent);
			parent.addChild(newFile);
			this.filesRevMap.put(id, newFile);
			return newFile;
		}
	}

	/**
	 * Add a simple element (i.e. {@linkplain jct.test.rsc.snpsht.verfilesystem.VerFsFileRev}) to
	 * versioned file system.<br>
	 * This function allows to add a simple element from its full path and its
	 * ID.<br>
	 * This function returns null if adding failed. This case appears mainly if
	 * an element in FS have the same path.
	 * 
	 * @param path
	 *        Path of element to add.
	 * @param id
	 *        ID of element to add.
	 * @return New element or null.
	 */
	public VerFsFileRev addSimpleRevision(String path, String id) {
		IVerFsElement newElem;

		FindOrMakeFileRev visitor = new FindOrMakeFileRev(this);

		newElem = visitor.findOrCreate(toRelativePath(path), id);

		if (newElem == null) {
			return null;
		} else {
			return (VerFsFileRev) newElem;
		}
	}

	/**
	 * Remove a file from a file if flag and file are not null
	 * 
	 * @param file
	 */
	private void removeFileFromFlag(VerFsFileRev file, AbstractVerFsFlag flag) {
		if (file != null && flag != null) {
			flag.removeChild(file);
		}
	}

	public void setFileRevLocation(IFsFileEntity fileLocation, VerFsFileRev file) {
		file.setFileRevLocation(fileLocation);
	}

	public void setFileLocation(IFsFileEntity fileLocation, VerFsFile file) {
		file.setFileLocation(fileLocation);
	}

	/**
	 * Set a comment from its string to a file revision.
	 * 
	 * @param commentString
	 *        Comment as String
	 * @param file
	 *        Commented file
	 */
	public VerFsComment setComment(String commentString, VerFsFileRev file) {
		removeFileFromFlag(file, file.getComment());

		VerFsComment comment = this.commentMap.get(commentString);

		if (comment == null) {
			comment = new VerFsComment(commentString);
			this.commentMap.put(commentString, comment);
		}

		comment.addChild(file);
		file.setComment(comment);

		return comment;
	}

	/**
	 * Returns all comments.
	 * 
	 * @return All comments.
	 */
	@Override
	public Collection<VerFsComment> getAllComments() {
		return this.commentMap.values();
	}

	/**
	 * Returns a comment from its string, or null if this comment does not
	 * exist.
	 * 
	 * @param commentString
	 *        Comment as String
	 * @return Comment object or null.
	 */
	@Override
	public VerFsComment getComment(String commentString) {
		return this.commentMap.get(commentString);
	}

	/**
	 * Set an action from its string to a file revision.
	 * 
	 * @param actionString
	 *        Action as String
	 * @param file
	 *        File to set
	 */
	public IVerFsAction setAction(String actionString, VerFsFileRev file) {
		removeFileFromFlag(file, file.getAction());

		IVerFsAction action = getAction(actionString);

		if (action != null) {
			((AbstractVerFsFlag) action).addChild(file);
			file.setAction((AbstractVerFsAction) action);
		}

		return action;
	}
	
	public AbstractVerFsAction setAction(Class<? extends IVerFsAction> actionClass, VerFsFileRev file) {
		removeFileFromFlag(file, file.getAction());

		AbstractVerFsAction action = getAction(actionClass);

		if (action != null) {
			((AbstractVerFsFlag) action).addChild(file);
			file.setAction(action);
		}

		return action;
	}

	/**
	 * Returns all actions.
	 * 
	 * @return All actions.
	 */
	@Override
	public AbstractVerFsAction[] getAllActions() {
		return this.actions;
	}

	/**
	 * Returns an action from its string, or null if this action does not exist.
	 * 
	 * @param actionString
	 *        Action as String
	 * @return Action object or null
	 */
	@Override
	public AbstractVerFsAction getAction(String actionString) {
		for (AbstractVerFsAction action : this.actions) {
			if (action.getValue().compareTo(actionString) == 0)
				return action;
		}
		return null;
	}
	
	@Override
	public AbstractVerFsAction getAction(Class<? extends IVerFsAction> c) {
		for (AbstractVerFsAction action : this.actions) {
			if (action.getClass() == c)
				return action;
		}
		return null;
	}

	/**
	 * Set a file revision author.
	 * 
	 * @param authorString
	 *        Author as String
	 * @param file
	 *        File revision written by this author
	 */
	public VerFsAuthor setAuthor(String authorString, VerFsFileRev file) {
		removeFileFromFlag(file, file.getAuthor());

		VerFsAuthor author = this.authorMap.get(authorString);

		if (author == null) {
			author = new VerFsAuthor(authorString);
			this.authorMap.put(authorString, author);
		}

		author.addChild(file);
		file.setAuthor(author);

		return author;
	}

	/**
	 * Returns all authors.
	 * 
	 * @return All authors.
	 */
	@Override
	public Collection<VerFsAuthor> getAllAuthors() {
		return this.authorMap.values();
	}

	/**
	 * Return an author from its string, or null if this author does not exist.
	 * 
	 * @param authorString
	 *        author as String
	 * @return Author object
	 */
	@Override
	public VerFsAuthor getAuthor(String authorString) {
		return this.authorMap.get(authorString);
	}

	/**
	 * Set a file revision's branch.
	 * 
	 * @param branchString
	 *        Branch as string
	 * @param file
	 *        File revision to set on this branch
	 */
	public VerFsBranch setBranch(String branchString, VerFsFileRev file) {
		removeFileFromFlag(file, (VerFsBranch) file.getBranch());

		VerFsBranch branch = this.branchMap.get(branchString);

		if (branch == null) {
			branch = new VerFsBranch(branchString);
			this.branchMap.put(branchString, branch);
		}

		branch.addChild(file);
		file.setBranch(branch);

		return branch;
	}

	/**
	 * Returns all branches.
	 * 
	 * @return All branches
	 */
	@Override
	public Collection<VerFsBranch> getAllBranches() {
		return this.branchMap.values();
	}

	/**
	 * Returns a branch from its string, or null if this branch does not exist.
	 * 
	 * @param branchString
	 *        Branch as String
	 * @return Branch object
	 */
	@Override
	public VerFsBranch getBranch(String branchString) {
		return this.branchMap.get(branchString);
	}

	@Override
	public VerFsBranch getTrunk() {
		return this.branchMap.get(VerFsTrunk.TRUNK_BRANCH_NAME);
	}

	/**
	 * Set a file name to a file revision.
	 * 
	 * @param fnString
	 *        File name (just file name not complete path) as String
	 * @param file
	 *        File revision to set.
	 */
	public VerFsFileName setFileName(String fnString, VerFsFileRev file) {
		removeFileFromFlag(file, file.getFileName());

		VerFsFileName fileName = this.fileNameMap.get(fnString);

		if (fileName == null) {
			fileName = new VerFsFileName(fnString);
			this.fileNameMap.put(fnString, fileName);
		}

		fileName.addChild(file);
		file.setFileName(fileName);

		return fileName;
	}

	/**
	 * Returns all files revisions names.
	 * 
	 * @return All files revisions names.
	 */
	@Override
	public Collection<VerFsFileName> getAllFilesNames() {
		return this.fileNameMap.values();
	}

	/**
	 * Returns a file name from its string.
	 * 
	 * @param fnString
	 *        File name as String
	 * @return File name object
	 */
	@Override
	public VerFsFileName getFileName(String fnString) {
		return this.fileNameMap.get(fnString);
	}

	// /**
	// * Set branch creation period.
	// *
	// * @param startTime
	// * Period start time
	// * @param endTime
	// * Period end time
	// * @param branch
	// * Branch to set
	// */
	// public VerFsUpdatePeriod setCreationTime(
	// Date startTime,
	// Date endTime,
	// VerFsBranch branch) {
	// VerFsUpdatePeriod updPeriod = new VerFsUpdatePeriod(startTime, endTime);
	//
	// branch.setCreationTime(updPeriod);
	//
	// return updPeriod;
	// }

	/**
	 * Set file revision' update time.
	 * 
	 * @param updateTime
	 *        Update time
	 * @param file
	 *        File on this time
	 */
	public VerFsUpdateTime setUpdateTime(Date updateTime, VerFsFileRev file) {
		VerFsUpdateTime updTime;

		updTime = new VerFsUpdateTime(updateTime);

		file.setUpdateTime(updTime);

		return updTime;
	}

	/**
	 * Set a revision ID to a file revision.
	 * 
	 * @param idString
	 *        Revision ID as String
	 * @param file
	 *        File to set
	 */
	public VerFsRevID setRevID(String idString, VerFsFileRev file) {
		removeFileFromFlag(file, file.getRevID());

		VerFsRevID revID = this.revIDMap.get(idString);

		if (revID == null) {
			revID = new VerFsRevID(idString);
			this.revIDMap.put(idString, revID);
		}

		revID.addChild(file);
		file.setRevID(revID);

		return revID;
	}

	/**
	 * Returns all revisions IDs.
	 * 
	 * @return All revisions IDs.
	 */
	@Override
	public Collection<VerFsRevID> getAllRevIDs() {
		return this.revIDMap.values();
	}

	/**
	 * Returns a revision ID from its string.
	 * 
	 * @param idString
	 *        Revision ID as String
	 * @return Revision ID object
	 */
	@Override
	public VerFsRevID getRevID(String idString) {
		return this.revIDMap.get(idString);
	}

	/**
	 * Set a revision ID to a file revision.
	 * 
	 * @param idString
	 *        Revision ID as String
	 * @param file
	 *        File to set
	 */
	public VerFsFile setAsFile(VerFsFileRev[] filesRev) {
		return setAsFile(filesRev, null);
	}

	/**
	 * Set a revision ID to a file revision.
	 * 
	 * @param idString
	 *        Revision ID as String
	 * @param id
	 *        File id (can be null if id must be compute)
	 * @param file
	 *        Built file
	 */
	public VerFsFile setAsFile(final VerFsFileRev[] filesRev, final String id) {
		VerFsFile file;
		VerFsNullRev nullRev;
		IVerFsAction create =
			getAction(VerFsCreateAction.class);
		IVerFsAction resurect =
			getAction(VerFsResurrectAction.class);

		// A file must contains at least 1 revision
		if (filesRev.length < 1)
			return null;

		for (VerFsFileRev currFile : filesRev) {
			removeFileFromFlag(currFile, currFile.getFile());
		}

		// Find first revision
		VerFsFileRev fileRev = filesRev[0];
		while (fileRev.getAction() != create && fileRev.getAction() != resurect)
			fileRev = fileRev.getPrevRevision();

		// Set first revision's previous revision as null revision
		if (fileRev.getPrevRevision() != null) {
			removeNextRevision(fileRev, fileRev.getPrevRevision());
			nullRev = new VerFsNullRev(fileRev, fileRev.getPrevRevision());
		} else {
			nullRev = new VerFsNullRev(fileRev);
		}
		setPrevRevision(nullRev, fileRev);

		final String id2;
		if (id == null) {
			id2 = getFileNewIdFromName(fileRev.getName());
		}
		else {
			id2 = id;
		}

		file = new VerFsFile(id2, nullRev, filesRev);
		for (VerFsFileRev currFile : filesRev) {
			file.addChild(currFile);
			currFile.setFile(file);
		}

		this.fileMap.put(id, file);

		return file;
	}

	private String getFileNewIdFromName(String name) {
		int i = 0;
		String id;

		do {
			id = i + "_" + name;
			i++;
		} while (this.fileMap.containsKey(id));

		return id;
	}

	public VerFsFile addToFile(String idString, VerFsFileRev fileRevToAdd) {
		VerFsFile file = this.fileMap.get(idString);

		if (file == null)
			return null;

		removeFileFromFlag(fileRevToAdd, fileRevToAdd.getFile());
		file.addChild(fileRevToAdd);
		fileRevToAdd.setFile(file);

		return file;
	}

	/**
	 * Returns all files.
	 * 
	 * @return All files
	 */
	@Override
	public Collection<VerFsFile> getAllFiles() {
		return this.fileMap.values();
	}

	/**
	 * Returns a file (witch contains all revisions) from its ID
	 * 
	 * @param idString
	 *        File ID as String
	 * @return File object
	 */
	@Override
	public VerFsFile getFile(String idString) {
		return this.fileMap.get(idString);
	}

	/**
	 * Add a tag to a file from its string.
	 * 
	 * @param tagString
	 *        Tag as string
	 * @param file
	 *        File to tag
	 */
	public VerFsTag addTag(String tagString, VerFsFileRev file) {
		VerFsTag tag = this.tagsMap.get(tagString);

		if (tag == null) {
			tag = new VerFsTag(tagString);
			this.tagsMap.put(tagString, tag);
		}

		tag.addChild(file);
		file.addTag(tag);

		return tag;
	}

	/**
	 * Returns all tags.
	 * 
	 * @return All tags
	 */
	@Override
	public Collection<VerFsTag> getAllTags() {
		return this.tagsMap.values();
	}

	/**
	 * Returns a tag from its string, or null if tag does not exit.
	 * 
	 * @param tagString
	 *        Tag as string
	 * @return Tag object or null
	 */
	@Override
	public VerFsTag getTag(String tagString) {
		return this.tagsMap.get(tagString);
	}

	/**
	 * Set file revision's previous revision.
	 * 
	 * @param prevRevision
	 *        Previous revision
	 * @param file
	 *        File to set
	 */
	public void setPrevRevision(VerFsFileRev prevRevision, VerFsFileRev file) {
		file.setPrevRevision(prevRevision);
	}

	/**
	 * Add file revision's next revision.
	 * 
	 * @param nextRevision
	 *        Next revision
	 * @param file
	 *        File to set
	 */
	public void addNextRevision(VerFsFileRev nextRevision, VerFsFileRev file) {
		file.addNextRevision(nextRevision);
	}

	/**
	 * Remove file revision's next revision.
	 * 
	 * @param nextRevision
	 *        Next revision
	 * @param file
	 *        File to set
	 */
	public void removeNextRevision(VerFsFileRev nextRevision, VerFsFileRev file) {
		file.removeNextRevision(nextRevision);
	}

	public VerFsCommit setAsCommit(VerFsFileRev[] filesRev) {
		VerFsCommit commit = new VerFsCommit();

		// A file must contains at least 1 revision
		if (filesRev.length < 1)
			return null;

		for (VerFsFileRev currFile : filesRev) {
			removeFileFromFlag(currFile, (VerFsCommit) currFile.getCommit());
			currFile.setCommit(commit);
			commit.addChild(currFile);
		}

		this.commitSet.add(commit);

		return commit;
	}

	@Override
	public Set<VerFsCommit> getAllCommits() {
		return this.commitSet;
	}

	/**
	 * Returns source file manager, or null if source file was not setted.
	 * 
	 * @return Source file manager.
	 */
	@Override
	public IFsManager getSourceManager() {
		return this.fileManager;
	}

	/**
	 * Set source file, and build a manager with it.<br>
	 * For the moment, source must be a zip file or don't exist.<br>
	 * It will be really simple to implement directory management, take a look
	 * to {@linkplain jct.test.rsc.snpsht.filesystem.IFsManager}.<br>
	 * If source don't exist, a zip file will be create with it.<br>
	 * 
	 * @param source
	 *        Source file
	 * @throws IOException
	 *         If File can't be read, create, or is a directory
	 */
	@Override
	public void setSource(File source) throws IOException {
		this.fileManager = FsManagerFactory.getNewManager(source);
	}
}
