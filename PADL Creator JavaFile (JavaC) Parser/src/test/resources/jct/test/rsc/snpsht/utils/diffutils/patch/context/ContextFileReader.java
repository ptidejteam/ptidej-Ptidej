package jct.test.rsc.snpsht.utils.diffutils.patch.context;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
public class ContextFileReader
implements jct.test.rsc.snpsht.utils.diffutils.patch.context.IContextFileReader
{
private java.io.BufferedReader reader;

private java.lang.String lastLine;

private boolean eofReached;

private int currentLineNumber;

public void <init>(java.io.InputStream source)
{
this.<init>();
this.eofReached = false;
this.currentLineNumber = 0;
this.reader = new java.io.BufferedReader(new java.io.InputStreamReader(source));

}

public java.lang.String readLine() throws java.io.IOException
{
this.lastLine = this.reader.readLine();
if(this.lastLine == null) this.eofReached = true;
this.currentLineNumber ++;
return this.lastLine;

}

public java.lang.String getLastLine()
{
return this.lastLine;

}

public int getLineNumber()
{
return this.currentLineNumber;

}

public boolean isEOFReached()
{
return this.eofReached;

}

public void close() throws java.io.IOException
{
this.reader.close();

}


}
