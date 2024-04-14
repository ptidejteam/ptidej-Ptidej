package jct.test.rsc.snpsht.utils.cvsutils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import jct.test.rsc.snpsht.utils.FSUtils;
public class CVSRevMLCheckOut
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
this.tmpDir = new java.io.File(java.lang.System.getProperty("java.io.tmpdir"), "tmp_dir_revML_co_" + this.hashCode());
if(this.tmpDir.exists()) 
{
jct.test.rsc.snpsht.utils.FSUtils.rmDir(this.tmpDir);

}
this.tmpDir.mkdirs();
return this.tmpDir;

}

public boolean checkOut(java.lang.String module, java.io.File targetFile) throws java.io.IOException, java.net.URISyntaxException
{
java.lang.Process p;
java.io.InputStream is;
int c;
java.lang.String answer = "";
java.lang.String osName;
java.lang.String repo;
java.lang.String targetReativePath;
java.io.File vcpExe;
if(targetFile.isDirectory()) throw new java.lang.IllegalArgumentException("Target file is a diretory");
this.getAndFlushtmpDir();
if(module.startsWith("/")) 
{
repo = module.substring(1);

}
 else 
{
repo = module;

}
if(module.endsWith("/")) 
{
repo += "...";

}
osName = java.lang.System.getProperty("os.name").toLowerCase();
if(osName.contains("linux")) 
{
vcpExe = new java.io.File(class.getResource("/jct.test.rsc.snpsht.verfilesystem/resource/vcp").toURI());
p = this.rt.exec("chmod u+x " + vcpExe.getAbsolutePath());

}
 else if(osName.contains("os x")) 
{
vcpExe = new java.io.File(class.getResource("/jct.test.rsc.snpsht.verfilesystem/resource/vcp").toURI());
p = this.rt.exec("chmod u+x " + vcpExe.getAbsolutePath());

}
 else if(osName.contains("win")) 
{
vcpExe = new java.io.File(class.getResource("/jct.test.rsc.snpsht.verfilesystem/resource/vcp.exe").toURI());

}
 else 
{
throw new java.lang.Error("Unknow host system '" + osName + "'");

}
targetReativePath = jct.test.rsc.snpsht.utils.FSUtils.getRelativePath(new java.io.File(java.lang.System.getProperty("user.dir")), targetFile);
p = this.rt.exec(vcpExe.getAbsolutePath() + " cvs:" + this.cvsRoot.getCVSROOT() + ":" + repo + " revml:" + targetReativePath);
java.lang.System.out.println(vcpExe.getAbsolutePath() + " cvs:" + this.cvsRoot.toString() + ":" + repo + " revml:" + targetReativePath);
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
answer = answer.toLowerCase();
if(answer.contains("aborted") || answer.contains("failed") || answer.contains("stderr") || answer.contains("undefined")) 
{
return false;

}
 else 
{
return (targetFile.isFile() && targetFile.exists());

}

}


}
