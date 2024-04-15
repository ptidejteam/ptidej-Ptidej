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

import jct.test.rsc.snpsht.filesystem.descriptor.FsFileDescriptor;
import jct.test.rsc.snpsht.filesystem.descriptor.FsTempFileDescriptor;
import jct.test.rsc.snpsht.filesystem.descriptor.FsZipEntryDescriptor;
import jct.test.rsc.snpsht.filesystem.descriptor.IFsDescriptor;

public class FsFileEntity implements IFsRealEntity {
	private String id;
	private IFsDescriptor descriptor;

	protected FsFileEntity(IFsDescriptor descriptor, String id) {
		setDescriptor(descriptor);
		this.id = id;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void copyTo(File dest) throws IOException {
		OutputStream out;
		InputStream in;
		int c = 0;

		if (dest.exists() && dest.isDirectory()) {
			throw new IllegalArgumentException(
				"Destination must be a file or don't exist, but is a directory.");
		} else {
			dest.getParentFile().mkdirs();
			
			in = getInputStream();

			out = new FileOutputStream(dest);

			while ((c = in.read()) != -1)
				out.write(c);

			in.close();
			out.close();
		}
	}
	
	@Override
	public long getSize() {
		return this.descriptor.getSize();
	}

	public IFsDescriptor getDescriptor() {
		return this.descriptor;
	}

	protected void setDescriptor(IFsDescriptor descriptor) {
		try {
			FsTempFileDescriptor descr = (FsTempFileDescriptor) this.descriptor;

			descr.delete();
		} catch (Exception e1) {}
		
		this.descriptor = descriptor;
	}

	protected void delete() {
		try {
			FsFileDescriptor descr = (FsFileDescriptor) this.descriptor;

			descr.delete();
		} catch (Exception e1) {
			try {
				@SuppressWarnings("unused")
				FsZipEntryDescriptor descr =
					(FsZipEntryDescriptor) this.descriptor;

				// Nothing to do, just check descriptor type

			} catch (Exception e2) {
				throw new IllegalArgumentException(
					"File descriptor must be a FsFileDescriptor or a FsZipEntryDescriptor, but is a "
							+ this.descriptor.getClass().getName());
			}
		}
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return this.descriptor.getInputStream();
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		try {
			FsFileDescriptor descr = (FsFileDescriptor) this.descriptor;

			return descr.getOutputStream();
		} catch (Exception e1) {
			try {
				@SuppressWarnings("unused")
				FsZipEntryDescriptor descr =
					(FsZipEntryDescriptor) this.descriptor;

				setDescriptor(new FsTempFileDescriptor());

				return getOutputStream();
			} catch (Exception e2) {
				throw new IllegalArgumentException(
					"File descriptor must be a FsFileDescriptor or a FsZipEntryDescriptor, but is a "
							+ this.descriptor.getClass().getName());
			}
		}
	}
	
	@Override
	public FileWriter getFileWriter() {
		try {
			FsFileDescriptor descr = (FsFileDescriptor) this.descriptor;

			return new FileWriter(descr.getFile());
		} catch (Exception e1) {
			try {
				@SuppressWarnings("unused")
				FsZipEntryDescriptor descr =
					(FsZipEntryDescriptor) this.descriptor;

				setDescriptor(new FsTempFileDescriptor());

				return getFileWriter();
			} catch (Exception e2) {
				throw new IllegalArgumentException(
					"File descriptor must be a FsFileDescriptor or a FsZipEntryDescriptor, but is a "
							+ this.descriptor.getClass().getName());
			}
		}
	}
}
