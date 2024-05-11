package jct.test.rsc.snpsht.filesystem;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import jct.test.rsc.snpsht.filesystem.descriptor.FsFileDescriptor;
import jct.test.rsc.snpsht.filesystem.descriptor.FsTempFileDescriptor;
import jct.test.rsc.snpsht.filesystem.descriptor.FsZipEntryDescriptor;
import jct.test.rsc.snpsht.filesystem.descriptor.IFsDescriptor;
public class FsFileEntity
implements jct.test.rsc.snpsht.filesystem.IFsRealEntity
{
private java.lang.String id;

private jct.test.rsc.snpsht.filesystem.descriptor.IFsDescriptor descriptor;

protected void <init>(jct.test.rsc.snpsht.filesystem.descriptor.IFsDescriptor descriptor, java.lang.String id)
{
this.<init>();
this.setDescriptor(descriptor);
this.id = id;

}

public java.lang.String getId()
{
return this.id;

}

public void copyTo(java.io.File dest) throws java.io.IOException
{
java.io.OutputStream out;
java.io.InputStream in;
int c = 0;
if(dest.exists() && dest.isDirectory()) 
{
throw new java.lang.IllegalArgumentException("Destination must be a file or don't exist, but is a directory.");

}
 else 
{
dest.getParentFile().mkdirs();
in = this.getInputStream();
out = new java.io.FileOutputStream(dest);
while((c = in.read()) != -1) out.write(c);
in.close();
out.close();

}

}

public long getSize()
{
return this.descriptor.getSize();

}

public jct.test.rsc.snpsht.filesystem.descriptor.IFsDescriptor getDescriptor()
{
return this.descriptor;

}

protected void setDescriptor(jct.test.rsc.snpsht.filesystem.descriptor.IFsDescriptor descriptor)
{
try
{
jct.test.rsc.snpsht.filesystem.descriptor.FsTempFileDescriptor descr = (jct.test.rsc.snpsht.filesystem.descriptor.FsTempFileDescriptor)this.descriptor;
descr.delete();

}
catch(java.lang.Exception e1) 
{

}
this.descriptor = descriptor;

}

protected void delete()
{
try
{
jct.test.rsc.snpsht.filesystem.descriptor.FsFileDescriptor descr = (jct.test.rsc.snpsht.filesystem.descriptor.FsFileDescriptor)this.descriptor;
descr.delete();

}
catch(java.lang.Exception e1) 
{
try
{
jct.test.rsc.snpsht.filesystem.descriptor.FsZipEntryDescriptor descr = (jct.test.rsc.snpsht.filesystem.descriptor.FsZipEntryDescriptor)this.descriptor;

}
catch(java.lang.Exception e2) 
{
throw new java.lang.IllegalArgumentException("File descriptor must be a FsFileDescriptor or a FsZipEntryDescriptor, but is a " + <NULL>.getClass().getName());

}

}

}

public java.io.InputStream getInputStream() throws java.io.IOException
{
return this.descriptor.getInputStream();

}

public java.io.OutputStream getOutputStream() throws java.io.IOException
{
try
{
jct.test.rsc.snpsht.filesystem.descriptor.FsFileDescriptor descr = (jct.test.rsc.snpsht.filesystem.descriptor.FsFileDescriptor)this.descriptor;
return descr.getOutputStream();

}
catch(java.lang.Exception e1) 
{
try
{
jct.test.rsc.snpsht.filesystem.descriptor.FsZipEntryDescriptor descr = (jct.test.rsc.snpsht.filesystem.descriptor.FsZipEntryDescriptor)this.descriptor;
this.setDescriptor(new jct.test.rsc.snpsht.filesystem.descriptor.FsTempFileDescriptor());
return this.getOutputStream();

}
catch(java.lang.Exception e2) 
{
throw new java.lang.IllegalArgumentException("File descriptor must be a FsFileDescriptor or a FsZipEntryDescriptor, but is a " + <NULL>.getClass().getName());

}

}

}

public java.io.FileWriter getFileWriter()
{
try
{
jct.test.rsc.snpsht.filesystem.descriptor.FsFileDescriptor descr = (jct.test.rsc.snpsht.filesystem.descriptor.FsFileDescriptor)this.descriptor;
return new java.io.FileWriter(descr.getFile());

}
catch(java.lang.Exception e1) 
{
try
{
jct.test.rsc.snpsht.filesystem.descriptor.FsZipEntryDescriptor descr = (jct.test.rsc.snpsht.filesystem.descriptor.FsZipEntryDescriptor)this.descriptor;
this.setDescriptor(new jct.test.rsc.snpsht.filesystem.descriptor.FsTempFileDescriptor());
return this.getFileWriter();

}
catch(java.lang.Exception e2) 
{
throw new java.lang.IllegalArgumentException("File descriptor must be a FsFileDescriptor or a FsZipEntryDescriptor, but is a " + <NULL>.getClass().getName());

}

}

}


}
