package jct.test.rsc.snpsht.filesystem;
import java.io.File;
import java.io.IOException;
public interface IFsFileEntity
{
abstract public java.lang.String getId()
{

}

abstract public void copyTo(java.io.File dest) throws java.io.IOException
{

}

abstract public long getSize()
{

}


}
