/*
 * (c) Copyright 2008 and following years, Julien Tanteri, University of
 * Montreal.
 * 
 * Use and copying of this software and preparation of derivative works based
 * upon this software are permitted. Any copy of this software or of any
 * derivative work must include the above copyright notice of the author, this
 * paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS ALL
 * WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE, AND NOT
 * WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN, ANY LIABILITY FOR DAMAGES
 * RESULTING FROM THE SOFTWARE OR ITS USE IS EXPRESSLY DISCLAIMED, WHETHER
 * ARISING IN CONTRACT, TORT (INCLUDING NEGLIGENCE) OR STRICT LIABILITY, EVEN IF
 * THE AUTHOR IS ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
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

public class FsZipFileManager implements IFsManager {
	private Map<String, FsFileEntity> id2FileMap;
	private File source;
	private boolean toUpdate;

	protected FsZipFileManager() {
		this.id2FileMap = new HashMap<String, FsFileEntity>();
	}

	@Override
	public void load(File source) throws IOException {
		this.id2FileMap.clear();

		if (source.exists() && source.isDirectory()) {
			throw new IllegalArgumentException(
				"Source file must be a zip file, but is a directory.");
		} else {
			this.source = source;

			if (source.exists() && source.length() > 0) {
				loadFiles();

				this.toUpdate = false;
			} else {
				this.toUpdate = true;
			}
		}
	}

	// Read zip source file, and initialize this manager by adding content files
	private void loadFiles() throws ZipException, IOException {
		ZipFile zip = new ZipFile(this.source);
		Enumeration<? extends ZipEntry> enumEntry = zip.entries();
		ZipEntry currEntry;
		String currId;

		while (enumEntry.hasMoreElements()) {
			currEntry = enumEntry.nextElement();
			currId = currEntry.getName();
			this.id2FileMap.put(currId, new FsFileEntity(
				new FsZipEntryDescriptor(currEntry, this.source),
				currId));
		}
		zip.close();
	}

	@Override
	public File getSource() {
		return this.source;
	}

	@Override
	public FsFileEntity add(String id) throws IOException {

		IFsFileEntity entity = getFileEntity(id);

		if (entity == null) {
			entity = new FsFileEntity(new FsTempFileDescriptor(), id);
			this.id2FileMap.put(id, (FsFileEntity) entity);
			this.toUpdate = true;

			return (FsFileEntity) entity;
		} else {
			throw new IOException("Id '" + id + "' already exist in manager.");
		}
	}

	@Override
	public FsFileEntity add(String id, File file) throws IOException {
		return add(id, file, false);
	}

	@Override
	public FsFileEntity add(String id, File file, boolean isTemp)
			throws IOException {

		IFsFileEntity entity = getFileEntity(id);
		IFsDescriptor descr;

		if (entity == null) {
			if (isTemp) {
				descr = new FsTempFileDescriptor(file);
			} else {
				descr = new FsFileDescriptor(file);
			}

			entity = new FsFileEntity(descr, id);
			this.id2FileMap.put(id, (FsFileEntity) entity);
			this.toUpdate = true;

			return (FsFileEntity) entity;
		} else {
			throw new IOException("Id '" + id + "' already exist in manager.");
		}
	}

	@Override
	public void update() throws IOException {
		ZipEntry entry;
		InputStream in;
		ZipOutputStream out;
		File tmp;
		CRC32 crc;

		if (this.toUpdate) {
			tmp = File.createTempFile("fsZipFileManagerTempZip", ".zip");

			if (getAllFilesEntity().length > 0) {
				out = new ZipOutputStream(new FileOutputStream(tmp));

				byte[] buf = new byte[1024];
				crc = new CRC32();

				for (FsFileEntity entity : getAllFilesEntity()) {
					entry = new ZipEntry(entity.getId());
					out.putNextEntry(entry);

					// Transfer bytes from the file to the ZIP file
					in = entity.getInputStream();
					crc.reset();
					int len;
					while ((len = in.read(buf)) > 0) {
						crc.update(buf, 0, len);
						out.write(buf, 0, len);
					}
					entry.setCrc(crc.getValue());
					in.close();
					out.closeEntry();

					entity.setDescriptor(new FsZipEntryDescriptor(
						entry,
						this.source));
				}

				out.close();
			}

			if (this.source.exists()) {
				if(!this.source.delete()) {
					throw new IOException("Can't delete old source file");
				}
			} else {
				this.source.getParentFile().mkdirs();
			}

			tmp.renameTo(this.source);

			this.toUpdate = false;
		}
	}

	@Override
	public FsFileEntity delete(String id) {
		IFsFileEntity file = getFileEntity(id);

		if (file != null && (file instanceof FsFileEntity)) {
			((FsFileEntity) file).delete();
			this.toUpdate = true;
			return this.id2FileMap.remove(id);
		} else
			return null;

	}

	@Override
	public FsFileEntity delete(IFsFileEntity file) {
		return delete(file.getId());
	}

	@Override
	public FsFileEntity[] getAllFilesEntity() {
		return this.id2FileMap.values().toArray(new FsFileEntity[0]);
	}

	@Override
	public IFsFileEntity getFileEntity(String id) {
		if (id.compareTo(NULL_ENTITY_ID) == 0)
			return getANullEntity();
		else
			return this.id2FileMap.get(id);
	}
	
	@Override
	public FsNullEntity getANullEntity() {
		return new FsNullEntity();
	}

	@Override
	public InputStream getInputStrem(IFsRealEntity file) throws IOException {
		return file.getInputStream();
	}

	@Override
	public OutputStream getOutputStrem(IFsRealEntity file) throws IOException {
		this.toUpdate = true;

		return file.getOutputStream();
	}

	@Override
	public FileWriter getFileWriter(IFsRealEntity file) throws IOException {
		if (file instanceof FsNullEntity)
			throw new IOException("");
		this.toUpdate = true;

		return file.getFileWriter();
	}
}
