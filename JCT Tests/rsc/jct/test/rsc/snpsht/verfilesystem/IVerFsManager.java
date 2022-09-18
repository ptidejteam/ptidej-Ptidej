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
public interface IVerFsManager
{
public jct.test.rsc.snpsht.verfilesystem.IVerFsComplexElement makeRoot(java.lang.String repository)
{

}

public jct.test.rsc.snpsht.verfilesystem.IVerFsComplexElement getRoot()
{

}

public java.lang.String getRevmlVersion()
{

}

public java.lang.String getDescription()
{

}

public java.util.Date getUpdateDate()
{

}

public jct.test.rsc.snpsht.verfilesystem.IVerFsElement getElement(java.lang.String path)
{

}

public jct.test.rsc.snpsht.verfilesystem.IVerFsSimpleRevision getSimpleRevision(java.lang.String id)
{

}

public java.util.Collection getAllSimplesRevisions()
{

}

public java.lang.String toFullPath(java.lang.String path)
{

}

public java.lang.String toRelativePath(java.lang.String path)
{

}

public boolean isValidPath(java.lang.String path)
{

}

public java.util.Collection getAllComments()
{

}

public jct.test.rsc.snpsht.verfilesystem.flag.IVerFsComment getComment(java.lang.String commentString)
{

}

public jct.test.rsc.snpsht.verfilesystem.flag.action.IVerFsAction[] getAllActions()
{

}

public jct.test.rsc.snpsht.verfilesystem.flag.action.IVerFsAction getAction(java.lang.String actionString)
{

}

jct.test.rsc.snpsht.verfilesystem.flag.action.IVerFsAction getAction(java.lang.Class c)
{

}

public java.util.Collection getAllAuthors()
{

}

public jct.test.rsc.snpsht.verfilesystem.flag.IVerFsAuthor getAuthor(java.lang.String authorString)
{

}

public java.util.Collection getAllBranches()
{

}

public jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch getBranch(java.lang.String branchString)
{

}

public jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch getTrunk()
{

}

public java.util.Collection getAllFilesNames()
{

}

public jct.test.rsc.snpsht.verfilesystem.flag.IVerFsFileName getFileName(java.lang.String fnString)
{

}

public java.util.Collection getAllRevIDs()
{

}

public jct.test.rsc.snpsht.verfilesystem.flag.IVerFsRevID getRevID(java.lang.String idString)
{

}

public java.util.Collection getAllFiles()
{

}

public jct.test.rsc.snpsht.verfilesystem.flag.IVerFsFile getFile(java.lang.String idString)
{

}

public java.util.Collection getAllTags()
{

}

public jct.test.rsc.snpsht.verfilesystem.flag.IVerFsTag getTag(java.lang.String tagString)
{

}

public java.util.Collection getAllCommits()
{

}

public jct.test.rsc.snpsht.filesystem.IFsManager getSourceManager()
{

}

public void setSource(java.io.File source) throws java.io.IOException
{

}


}
