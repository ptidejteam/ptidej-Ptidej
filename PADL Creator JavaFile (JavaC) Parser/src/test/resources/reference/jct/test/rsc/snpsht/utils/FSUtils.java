package jct.test.rsc.snpsht.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
public class FSUtils
{
public void <init>()
{
this.<init>();

}

public static void rmDir(java.io.File dir)
{
if(dir.isFile()) 
{
dir.delete();

}
 else 
{
for(java.io.File child : dir.listFiles()) 
{
jct.test.rsc.snpsht.utils.FSUtils.rmDir(child);

}
dir.delete();

}

}

public static void copyFile(java.io.File source, java.io.File dest) throws java.io.IOException
{
java.io.InputStream in;
java.io.OutputStream out;
int c = 0;
if(! source.isFile() || ! source.exists()) 
{
throw new java.lang.IllegalArgumentException("Source must be a file and exist.");

}
if(dest.isDirectory()) 
{
throw new java.lang.IllegalArgumentException("Destination is a directory.");

}
in = new java.io.FileInputStream(source);
out = new java.io.FileOutputStream(dest);
while((c = in.read()) != -1) out.write(c);
in.close();
out.close();

}

private static java.util.List getPathList(java.io.File f)
{
java.util.List l = new java.util.ArrayList();
java.io.File r;
try
{
r = f.getCanonicalFile();
while(r != null) 
{
l.add(r.getName());
r = r.getParentFile();

}

}
catch(java.io.IOException e) 
{
e.printStackTrace();
l = null;

}
return l;

}

private static java.lang.String matchPathLists(java.util.List r, java.util.List f)
{
int i;
int j;
java.lang.String s;
s = "";
i = r.size() - 1;
j = f.size() - 1;
while((i >= 0) && (j >= 0) && (r.get(i).equals(f.get(j)))) 
{
i --;
j --;

}
for(; i >= 0; i --) 
{
s += ".." + java.io.File.separator;

}
for(; j >= 1; j --) 
{
s += f.get(j) + java.io.File.separator;

}
s += f.get(j);
return s;

}

public static java.lang.String getRelativePath(java.io.File home, java.io.File f)
{
java.util.List homelist;
java.util.List filelist;
java.lang.String s;
if(home.equals(f)) return ".";
homelist = jct.test.rsc.snpsht.utils.FSUtils.getPathList(home);
filelist = jct.test.rsc.snpsht.utils.FSUtils.getPathList(f);
s = jct.test.rsc.snpsht.utils.FSUtils.matchPathLists(homelist, filelist);
return s;

}

public static java.lang.String checksum(java.io.File file)
{
try
{
java.io.InputStream fin = new java.io.FileInputStream(file);
return jct.test.rsc.snpsht.utils.FSUtils.checksum(fin);

}
catch(java.io.FileNotFoundException e) 
{
return null;

}

}

public static java.lang.String checksum(java.io.InputStream fin)
{
try
{
java.security.MessageDigest md5er = java.security.MessageDigest.getInstance("MD5");
byte[] buffer = new byte[1024];
int read;
do 
{
read = fin.read(buffer);
if(read > 0) md5er.update(buffer, 0, read);

}
 while(read != -1);
fin.close();
byte[] digest = md5er.digest();
if(digest == null) return null;
java.lang.String strDigest = "0x";
for(int i = 0; i < digest.length; i ++) 
{
strDigest += java.lang.Integer.toString((digest[i] & 255) + 256, 16).substring(1).toUpperCase();

}
return strDigest;

}
catch(java.lang.Exception e) 
{
return null;

}

}

public static void zip(java.io.File zipFile, java.io.File[] files2zip) throws java.io.IOException
{
jct.test.rsc.snpsht.utils.FSUtils.zip(zipFile, files2zip, false);

}

public static void zip(java.io.File zipFile, java.io.File dir2zip) throws java.io.IOException
{
jct.test.rsc.snpsht.utils.FSUtils.zip(zipFile, dir2zip.listFiles(), true);

}

public static void zip(java.io.File zipFile, java.io.File[] files2zip, boolean rec) throws java.io.IOException
{
java.util.zip.ZipOutputStream out = new java.util.zip.ZipOutputStream(new java.io.FileOutputStream(zipFile));
for(int i = 0; i < files2zip.length; i ++) 
{
jct.test.rsc.snpsht.utils.FSUtils.add2zip(files2zip[i].getParentFile(), files2zip[i], out, rec);

}
out.close();

}

private static void add2zip(java.io.File relativeDir, java.io.File file2add, java.util.zip.ZipOutputStream out, boolean rec) throws java.io.IOException
{
if(file2add.isDirectory()) 
{
if(rec) 
{
for(java.io.File child : file2add.listFiles()) 
{
jct.test.rsc.snpsht.utils.FSUtils.add2zip(relativeDir, child, out, rec);

}

}

}
 else 
{
byte[] buf = new byte[1024];
java.io.FileInputStream in = new java.io.FileInputStream(file2add);
out.putNextEntry(new java.util.zip.ZipEntry(jct.test.rsc.snpsht.utils.FSUtils.fsPath2zipPath(jct.test.rsc.snpsht.utils.FSUtils.getRelativePath(relativeDir, file2add))));
int len;
while((len = in.read(buf)) > 0) 
{
out.write(buf, 0, len);

}
out.closeEntry();
in.close();

}

}

public static void unzip(java.io.File zipFile, java.io.File outDir, java.lang.String fileToUnzip) throws java.io.IOException
{
java.util.zip.ZipEntry entry;
java.util.zip.ZipInputStream in;
in = new java.util.zip.ZipInputStream(new java.io.FileInputStream(zipFile));
do 
{
entry = in.getNextEntry();

}
 while(entry != null && entry.getName().compareTo(fileToUnzip) != 0);
if(entry != null) 
{
jct.test.rsc.snpsht.utils.FSUtils.unzip(in, new java.io.File(outDir, jct.test.rsc.snpsht.utils.FSUtils.zipPath2fsPath(entry.getName())));

}
in.close();

}

public static void unzip(java.io.File zipFile, java.io.File outDir) throws java.io.IOException
{
java.util.zip.ZipEntry entry;
java.util.zip.ZipInputStream in;
in = new java.util.zip.ZipInputStream(new java.io.FileInputStream(zipFile));
while((entry = in.getNextEntry()) != null) 
{
jct.test.rsc.snpsht.utils.FSUtils.unzip(in, new java.io.File(outDir, jct.test.rsc.snpsht.utils.FSUtils.zipPath2fsPath(entry.getName())));

}
in.close();

}

private static void unzip(java.util.zip.ZipInputStream in, java.io.File outFile) throws java.io.IOException
{
outFile.getParentFile().mkdirs();
java.io.OutputStream out = new java.io.FileOutputStream(outFile);
byte[] buf = new byte[1024];
int len;
while((len = in.read(buf)) > 0) 
{
out.write(buf, 0, len);

}
out.close();
in.close();

}

public static java.lang.String[] listZipContent(java.io.File zipFile) throws java.io.IOException
{
java.util.zip.ZipFile zf = new java.util.zip.ZipFile(zipFile);
java.util.List filesList = new java.util.ArrayList();
for(java.util.Enumeration entries = zf.entries(); entries.hasMoreElements(); ) 
{
filesList.add(((java.util.zip.ZipEntry)entries.nextElement()).getName());

}
zf.close();
return filesList.toArray(new java.lang.String[0]);

}

private static java.lang.String zipPath2fsPath(java.lang.String zipPath)
{
if(java.io.File.separator.compareTo("/") == 0) 
{
return zipPath;

}
 else 
{
return zipPath.replace(java.io.File.separator, "/");

}

}

private static java.lang.String fsPath2zipPath(java.lang.String fsPath)
{
if(java.io.File.separator.compareTo("/") == 0) 
{
return fsPath;

}
 else 
{
return fsPath.replace("/", java.io.File.separator);

}

}


}
