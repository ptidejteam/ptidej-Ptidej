package jct.test.rsc.snpsht.utils.cvsutils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import jct.test.rsc.snpsht.utils.FSUtils;
public class CVSCheckOut
{
private jct.test.rsc.snpsht.utils.cvsutils.CVSRoot cvsRoot;

private java.lang.Runtime rt = java.lang.Runtime.getRuntime();

private java.io.File tmpDir;

public void <init>(jct.test.rsc.snpsht.utils.cvsutils.CVSRoot cvsRoot)
{
this.<init>();
this.cvsRoot = cvsRoot;
this.getAndFlushtmpDir();

}

private java.io.File getAndFlushtmpDir()
{
this.tmpDir = new java.io.File(java.lang.System.getProperty("java.io.tmpdir"), "tmp_dir_cvs_co_" + this.hashCode());
if(this.tmpDir.exists()) 
{
jct.test.rsc.snpsht.utils.FSUtils.rmDir(this.tmpDir);

}
this.tmpDir.mkdirs();
return this.tmpDir;

}

public boolean checkOut(java.lang.String module, java.io.File targetDir) throws java.io.IOException
{
java.lang.Process p;
java.io.InputStream is;
int c;
java.lang.String answer = "";
if(! targetDir.exists()) 
{
if(! targetDir.mkdirs()) 
{
throw new java.lang.IllegalArgumentException("Can't create direcory '" + targetDir.getAbsolutePath() + "'");

}

}
 else if(! targetDir.isDirectory()) 
{
throw new java.lang.IllegalArgumentException("Destination directory ('" + targetDir.getAbsolutePath() + "') is a file.");

}
p = this.rt.exec("cvs co " + module, new java.lang.String[] { "CVSROOT=" + this.cvsRoot.getCVSROOT() }, targetDir);
is = p.getInputStream();
while((c = is.read()) != -1) 
{
java.lang.System.out.print((char)c);

}
is.close();
is = p.getErrorStream();
while((c = is.read()) != -1) 
{
answer += (char)c;

}
java.lang.System.err.println(answer);
is.close();
return ! answer.contains("aborted") && ! answer.contains("warning");

}

public boolean checkOut(java.lang.String fileRevPath, java.lang.String revision, java.io.File targetFile) throws java.io.IOException
{
java.lang.Process p;
java.io.InputStream is;
int c;
java.lang.String answer = "";
java.io.File tmpFile;
if(targetFile.isDirectory()) throw new java.lang.IllegalArgumentException("Target file is a diretory");
this.getAndFlushtmpDir();
p = this.rt.exec("cvs co -r " + revision + " " + fileRevPath, new java.lang.String[] { "CVSROOT=" + this.cvsRoot.getCVSROOT() }, this.tmpDir);
java.lang.System.out.println("cvs co -r " + revision + " " + fileRevPath);
is = p.getInputStream();
while((c = is.read()) != -1) 
{
answer += (char)c;

}
java.lang.System.out.print(answer);
is.close();
answer = "";
is = p.getErrorStream();
while((c = is.read()) != -1) 
{
answer += (char)c;

}
java.lang.System.out.print(answer);
is.close();
if(answer.contains("aborted") || answer.contains("warning")) 
{
return false;

}
 else 
{
tmpFile = this.getFile(fileRevPath, this.tmpDir);
if(! tmpFile.isFile() || ! tmpFile.exists()) return false;
targetFile.getParentFile().mkdirs();
tmpFile.renameTo(targetFile);
return (targetFile.isFile() && targetFile.exists());

}

}

private java.io.File getFile(java.lang.String cvsPath, java.io.File contentDir)
{
int firstIndex = cvsPath.indexOf(this.cvsRoot.getSeparator());
if(contentDir.isDirectory()) 
{
if(firstIndex == -1 || firstIndex == cvsPath.length() - 1) 
{
for(java.io.File child : contentDir.listFiles()) 
{
if(child.getName().compareTo(cvsPath) == 0) return child;

}

}
 else 
{
for(java.io.File child : contentDir.listFiles()) 
{
if(child.getName().compareTo(cvsPath.substring(0, firstIndex)) == 0) 
{
return this.getFile(cvsPath.substring(firstIndex + 1), child);

}

}

}

}
return null;

}


}
