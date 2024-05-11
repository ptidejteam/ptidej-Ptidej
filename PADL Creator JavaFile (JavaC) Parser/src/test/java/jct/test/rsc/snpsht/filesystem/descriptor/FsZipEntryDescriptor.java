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
package jct.test.rsc.snpsht.filesystem.descriptor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FsZipEntryDescriptor implements IFsDescriptor {
	private File zipFile;
	private ZipEntry entry;

	public FsZipEntryDescriptor(ZipEntry entry, File zipFile) {
		this.entry = entry;
		this.zipFile = zipFile;
	}

	@Override
	public void copyTo(File dest) throws IOException {
		InputStream in = getInputStream();
		OutputStream out = new FileOutputStream(dest);

		// Transfer bytes from the ZIP file to the output file
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}

		out.close();
		in.close();
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new InputStreamWrapperAutocloseZipFile();
	}

	public File getZipFile() {
		return this.zipFile;
	}

	public ZipEntry getEntry() {
		return this.entry;
	}

	@Override
	public long getSize() {
		return this.entry.getSize();
	}

	/*
	 * Close input stream is not enough to release all file access, we need to
	 * close ZipFile too. To abstract ZipFile use, we make a wrapper for zip
	 * input stream witch will close ZipFile on stream closing. Of course, this
	 * input stream must be the only stream open on this ZipFile.
	 */
	private class InputStreamWrapperAutocloseZipFile extends InputStream {
		private InputStream innerStream;
		private ZipFile zf;

		public InputStreamWrapperAutocloseZipFile() throws IOException {
			this.zf = new ZipFile(FsZipEntryDescriptor.this.zipFile);
			this.innerStream =
				this.zf.getInputStream(FsZipEntryDescriptor.this.entry);
		}

		@Override
		public int available() throws IOException {
			return this.innerStream.available();
		}

		@Override
		public void close() throws IOException {
			this.zf.close();
		}

		@Override
		public synchronized void mark(int readlimit) {
			this.innerStream.mark(readlimit);
		}

		@Override
		public boolean markSupported() {
			return this.innerStream.markSupported();
		}

		@Override
		public int read() throws IOException {
			return this.innerStream.read();
		}

		@Override
		public int read(byte[] b, int off, int len) throws IOException {
			return this.innerStream.read(b, off, len);
		}

		@Override
		public int read(byte[] b) throws IOException {
			return this.innerStream.read(b);
		}

		@Override
		public synchronized void reset() throws IOException {
			this.innerStream.reset();
		}

		@Override
		public long skip(long n) throws IOException {
			return this.innerStream.skip(n);
		}

	}
}
