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
public interface IVerFsSimpleRevision
extends jct.test.rsc.snpsht.verfilesystem.IVerFsElement
{
public jct.test.rsc.snpsht.filesystem.IFsFileEntity getFileRevLocation()
{

}

public jct.test.rsc.snpsht.verfilesystem.flag.IVerFsFile getFile()
{

}

public jct.test.rsc.snpsht.verfilesystem.flag.IVerFsComment getComment()
{

}

public jct.test.rsc.snpsht.verfilesystem.flag.IVerFsAuthor getAuthor()
{

}

public jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch getBranch()
{

}

public jct.test.rsc.snpsht.verfilesystem.flag.IVerFsFileName getFileName()
{

}

public jct.test.rsc.snpsht.verfilesystem.flag.action.IVerFsAction getAction()
{

}

public jct.test.rsc.snpsht.verfilesystem.attribute.IVerFsTime getUpdateTime()
{

}

public jct.test.rsc.snpsht.verfilesystem.flag.IVerFsRevID getRevID()
{

}

public jct.test.rsc.snpsht.verfilesystem.flag.IVerFsCommit getCommit()
{

}

public java.util.Set getTags()
{

}

public jct.test.rsc.snpsht.verfilesystem.IVerFsSimpleRevision getPrevRevision()
{

}

public java.util.Set getNextRevisions()
{

}


}
