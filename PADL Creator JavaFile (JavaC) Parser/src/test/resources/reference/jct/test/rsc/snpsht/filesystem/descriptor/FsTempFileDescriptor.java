package jct.test.rsc.snpsht.filesystem.descriptor;
import java.io.File;
import java.io.IOException;
public class FsTempFileDescriptor
extends jct.test.rsc.snpsht.filesystem.descriptor.FsFileDescriptor
{
public void <init>() throws java.io.IOException
{
this.<init>(java.io.File.createTempFile("fsTempFileDescriptor", null));
this.getFile().deleteOnExit();

}

public void <init>(java.io.File file)
{
this.<init>(file);
this.getFile().deleteOnExit();

}


}
