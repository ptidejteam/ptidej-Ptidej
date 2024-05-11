package jct.test.rsc.snpsht.filesystem;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import jct.test.rsc.snpsht.filesystem.descriptor.FsFileDescriptor;
import jct.test.rsc.snpsht.filesystem.descriptor.FsTempFileDescriptor;
import jct.test.rsc.snpsht.filesystem.descriptor.FsZipEntryDescriptor;
import jct.test.rsc.snpsht.filesystem.descriptor.IFsDescriptor;
public class FsZipFileManager
implements jct.test.rsc.snpsht.filesystem.IFsManager
{
private java.util.Map id2FileMap;

private java.io.File source;

private boolean toUpdate;

protected void <init>()
{
this.<init>();
this.id2FileMap = new java.util.HashMap();

}

public void load(java.io.File source) throws java.io.IOException
{
this.id2FileMap.clear();
if(source.exists() && source.isDirectory()) 
{
throw new java.lang.IllegalArgumentException("Source file must be a zip file, but is a directory.");

}
 else 
{
this.source = source;
if(source.exists() && source.length() > 0) 
{
this.loadFiles();
this.toUpdate = false;

}
 else 
{
this.toUpdate = true;

}

}

}

private void loadFiles() throws java.io.IOException, java.util.zip.ZipException
{
java.util.zip.ZipFile zip = new java.util.zip.ZipFile(this.source);
java.util.Enumeration enumEntry = zip.entries();
java.util.zip.ZipEntry currEntry;
java.lang.String currId;
while(enumEntry.hasMoreElements()) 
{
currEntry = enumEntry.nextElement();
currId = currEntry.getName();
this.id2FileMap.put(currId, new jct.test.rsc.snpsht.filesystem.FsFileEntity(new jct.test.rsc.snpsht.filesystem.descriptor.FsZipEntryDescriptor(currEntry, this.source), currId));

}
zip.close();

}

public java.io.File getSource()
{
return this.source;

}

public jct.test.rsc.snpsht.filesystem.FsFileEntity add(java.lang.String id) throws java.io.IOException
{
jct.test.rsc.snpsht.filesystem.IFsFileEntity entity = this.getFileEntity(id);
if(entity == null) 
{
entity = new jct.test.rsc.snpsht.filesystem.FsFileEntity(new jct.test.rsc.snpsht.filesystem.descriptor.FsTempFileDescriptor(), id);
this.id2FileMap.put(id, (jct.test.rsc.snpsht.filesystem.FsFileEntity)entity);
this.toUpdate = true;
return (jct.test.rsc.snpsht.filesystem.FsFileEntity)entity;

}
 else 
{
throw new java.io.IOException("Id '" + id + "' already exist in manager.");

}

}

public jct.test.rsc.snpsht.filesystem.FsFileEntity add(java.lang.String id, java.io.File file) throws java.io.IOException
{
return this.add(id, file, false);

}

public jct.test.rsc.snpsht.filesystem.FsFileEntity add(java.lang.String id, java.io.File file, boolean isTemp) throws java.io.IOException
{
jct.test.rsc.snpsht.filesystem.IFsFileEntity entity = this.getFileEntity(id);
jct.test.rsc.snpsht.filesystem.descriptor.IFsDescriptor descr;
if(entity == null) 
{
if(isTemp) 
{
descr = new jct.test.rsc.snpsht.filesystem.descriptor.FsTempFileDescriptor(file);

}
 else 
{
descr = new jct.test.rsc.snpsht.filesystem.descriptor.FsFileDescriptor(file);

}
entity = new jct.test.rsc.snpsht.filesystem.FsFileEntity(descr, id);
this.id2FileMap.put(id, (jct.test.rsc.snpsht.filesystem.FsFileEntity)entity);
this.toUpdate = true;
return (jct.test.rsc.snpsht.filesystem.FsFileEntity)entity;

}
 else 
{
throw new java.io.IOException("Id '" + id + "' already exist in manager.");

}

}

public void update() throws java.io.IOException
{
java.util.zip.ZipEntry entry;
java.io.InputStream in;
java.util.zip.ZipOutputStream out;
java.io.File tmp;
java.util.zip.CRC32 crc;
if(this.toUpdate) 
{
tmp = java.io.File.createTempFile("fsZipFileManagerTempZip", ".zip");
if(this.getAllFilesEntity().length > 0) 
{
out = new java.util.zip.ZipOutputStream(new java.io.FileOutputStream(tmp));
byte[] buf = new byte[1024];
crc = new java.util.zip.CRC32();
for(jct.test.rsc.snpsht.filesystem.FsFileEntity entity : this.getAllFilesEntity()) 
{
entry = new java.util.zip.ZipEntry(entity.getId());
out.putNextEntry(entry);
in = entity.getInputStream();
crc.reset();
int len;
while((len = in.read(buf)) > 0) 
{
crc.update(buf, 0, len);
out.write(buf, 0, len);

}
entry.setCrc(crc.getValue());
in.close();
out.closeEntry();
entity.setDescriptor(new jct.test.rsc.snpsht.filesystem.descriptor.FsZipEntryDescriptor(entry, this.source));

}
out.close();

}
if(this.source.exists()) 
{
if(! this.source.delete()) 
{
throw new java.io.IOException("Can't delete old source file");

}

}
 else 
{
this.source.getParentFile().mkdirs();

}
tmp.renameTo(this.source);
this.toUpdate = false;

}

}

public jct.test.rsc.snpsht.filesystem.FsFileEntity delete(java.lang.String id)
{
jct.test.rsc.snpsht.filesystem.IFsFileEntity file = this.getFileEntity(id);
if(file != null && (file instanceof jct.test.rsc.snpsht.filesystem.FsFileEntity)) 
{
((jct.test.rsc.snpsht.filesystem.FsFileEntity)file).delete();
this.toUpdate = true;
return this.id2FileMap.remove(id);

}
 else return null;

}

public jct.test.rsc.snpsht.filesystem.FsFileEntity delete(jct.test.rsc.snpsht.filesystem.IFsFileEntity file)
{
return this.delete(file.getId());

}

public jct.test.rsc.snpsht.filesystem.FsFileEntity[] getAllFilesEntity()
{
return this.id2FileMap.values().toArray(new jct.test.rsc.snpsht.filesystem.FsFileEntity[0]);

}

public jct.test.rsc.snpsht.filesystem.IFsFileEntity getFileEntity(java.lang.String id)
{
if(id.compareTo(jct.test.rsc.snpsht.filesystem.IFsManager.NULL_ENTITY_ID) == 0) return this.getANullEntity();
 else return this.id2FileMap.get(id);

}

public jct.test.rsc.snpsht.filesystem.FsNullEntity getANullEntity()
{
return new jct.test.rsc.snpsht.filesystem.FsNullEntity();

}

public java.io.InputStream getInputStrem(jct.test.rsc.snpsht.filesystem.IFsRealEntity file) throws java.io.IOException
{
return file.getInputStream();

}

public java.io.OutputStream getOutputStrem(jct.test.rsc.snpsht.filesystem.IFsRealEntity file) throws java.io.IOException
{
this.toUpdate = true;
return file.getOutputStream();

}

public java.io.FileWriter getFileWriter(jct.test.rsc.snpsht.filesystem.IFsRealEntity file) throws java.io.IOException
{
if(file instanceof jct.test.rsc.snpsht.filesystem.FsNullEntity) throw new java.io.IOException("");
this.toUpdate = true;
return file.getFileWriter();

}


}
