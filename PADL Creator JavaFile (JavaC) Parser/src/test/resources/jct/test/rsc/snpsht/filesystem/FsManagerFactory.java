package jct.test.rsc.snpsht.filesystem;
import java.io.File;
import java.io.IOException;
public class FsManagerFactory
{
public void <init>()
{
this.<init>();

}

public static jct.test.rsc.snpsht.filesystem.IFsManager getNewManager(java.io.File source) throws java.io.IOException
{
jct.test.rsc.snpsht.filesystem.IFsManager manager;
if(source.exists()) 
{
if(source.isFile()) 
{
manager = new jct.test.rsc.snpsht.filesystem.FsZipFileManager();
manager.load(source);

}
 else 
{
throw new java.io.IOException("Directory management feature not yet implemented");

}

}
 else 
{
manager = new jct.test.rsc.snpsht.filesystem.FsZipFileManager();
manager.load(source);

}
return manager;

}


}
