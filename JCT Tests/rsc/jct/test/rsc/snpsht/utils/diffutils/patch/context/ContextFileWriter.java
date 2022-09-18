package jct.test.rsc.snpsht.utils.diffutils.patch.context;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
public class ContextFileWriter
implements jct.test.rsc.snpsht.utils.diffutils.patch.context.IContextFileWriter
{
private java.io.BufferedWriter writer;

private java.lang.String lastLine;

private int currentLineNumber;

public void <init>(java.io.OutputStream source)
{
this.<init>();
this.currentLineNumber = 0;
this.writer = new java.io.BufferedWriter(new java.io.OutputStreamWriter(source));

}

public java.lang.String getLastLine()
{
return this.lastLine;

}

public int getLineNumber()
{
return this.currentLineNumber;

}

public void writeLine(java.lang.String toWrite) throws java.io.IOException
{
this.lastLine = toWrite;
this.writer.write(toWrite + "
", 0, toWrite.length() + 1);
this.currentLineNumber ++;

}

public void close() throws java.io.IOException
{
this.writer.close();

}


}
