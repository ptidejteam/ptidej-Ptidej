package jct.test.rsc.snpsht.filesystem.descriptor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
public class FsZipEntryDescriptor
implements jct.test.rsc.snpsht.filesystem.descriptor.IFsDescriptor
{
private java.io.File zipFile;

private java.util.zip.ZipEntry entry;

public void <init>(java.util.zip.ZipEntry entry, java.io.File zipFile)
{
this.<init>();
this.entry = entry;
this.zipFile = zipFile;

}

public void copyTo(java.io.File dest) throws java.io.IOException
{
java.io.InputStream in = this.getInputStream();
java.io.OutputStream out = new java.io.FileOutputStream(dest);
byte[] buf = new byte[1024];
int len;
while((len = in.read(buf)) > 0) 
{
out.write(buf, 0, len);

}
out.close();
in.close();

}

public java.io.InputStream getInputStream() throws java.io.IOException
{
return new this.InputStreamWrapperAutocloseZipFile();

}

public java.io.File getZipFile()
{
return this.zipFile;

}

public java.util.zip.ZipEntry getEntry()
{
return this.entry;

}

public long getSize()
{
return this.entry.getSize();

}

private class InputStreamWrapperAutocloseZipFile
extends java.io.InputStream
{
private java.io.InputStream innerStream;

private java.util.zip.ZipFile zf;

public void <init>() throws java.io.IOException
{
this.<init>();
this.zf = new java.util.zip.ZipFile(this.zipFile);
this.innerStream = this.zf.getInputStream(this.entry);

}

public int available() throws java.io.IOException
{
return this.innerStream.available();

}

public void close() throws java.io.IOException
{
this.zf.close();

}

public synchronized void mark(int readlimit)
{
this.innerStream.mark(readlimit);

}

public boolean markSupported()
{
return this.innerStream.markSupported();

}

public int read() throws java.io.IOException
{
return this.innerStream.read();

}

public int read(byte[] b, int off, int len) throws java.io.IOException
{
return this.innerStream.read(b, off, len);

}

public int read(byte[] b) throws java.io.IOException
{
return this.innerStream.read(b);

}

public synchronized void reset() throws java.io.IOException
{
this.innerStream.reset();

}

public long skip(long n) throws java.io.IOException
{
return this.innerStream.skip(n);

}


}


}
