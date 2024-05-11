package jct.test.rsc.snpsht.filesystem.descriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
public class FsFileDescriptor
implements jct.test.rsc.snpsht.filesystem.descriptor.IFsDescriptor
{
private java.io.File file;

public void <init>(java.io.File file)
{
this.<init>();
this.file = file;

}

public void copyTo(java.io.File dest) throws java.io.IOException
{
java.io.InputStream in;
java.io.OutputStream out;
int c = 0;
if(! this.file.isFile() || ! this.file.exists()) 
{
throw new java.lang.IllegalArgumentException("Source must be a file and exist.");

}
if(dest.isDirectory()) 
{
throw new java.lang.IllegalArgumentException("Destination is a directory.");

}
in = new java.io.FileInputStream(this.file);
out = new java.io.FileOutputStream(dest);
while((c = in.read()) != -1) out.write(c);
in.close();
out.close();

}

public void delete()
{
this.file.delete();

}

public java.io.InputStream getInputStream() throws java.io.IOException
{
return new java.io.FileInputStream(this.file);

}

public java.io.OutputStream getOutputStream() throws java.io.IOException
{
return new java.io.FileOutputStream(this.file);

}

public java.io.File getFile()
{
return this.file;

}

public long getSize()
{
return this.file.length();

}


}
