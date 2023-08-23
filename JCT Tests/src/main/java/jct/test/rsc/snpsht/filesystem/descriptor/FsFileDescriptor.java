/* (c) Copyright 2008 and following years, Julien Tanteri,
 * University of Montreal.
 * 
 * Use and copying of this software and preparation of derivative works
 * based upon this software are permitted. Any copy of this software or
 * of any derivative work must include the above copyright notice of
 * the author, this paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS
 * ALL WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE, AND NOT WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN,
 * ANY LIABILITY FOR DAMAGES RESULTING FROM THE SOFTWARE OR ITS USE IS
 * EXPRESSLY DISCLAIMED, WHETHER ARISING IN CONTRACT, TORT (INCLUDING
 * NEGLIGENCE) OR STRICT LIABILITY, EVEN IF THE AUTHOR IS ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
package jct.test.rsc.snpsht.filesystem.descriptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FsFileDescriptor implements IFsDescriptor {
	private File file;

	public FsFileDescriptor(File file) {
		this.file = file;
	}

	@Override
	public void copyTo(File dest) throws IOException {
		InputStream in;
		OutputStream out;
		int c = 0;

		if (!this.file.isFile() || !this.file.exists()) {
			throw new IllegalArgumentException(
				"Source must be a file and exist.");
		}

		if (dest.isDirectory()) {
			throw new IllegalArgumentException("Destination is a directory.");
		}

		in = new FileInputStream(this.file);

		out = new FileOutputStream(dest);

		while ((c = in.read()) != -1)
			out.write(c);

		in.close();
		out.close();
	}

	public void delete() {
		this.file.delete();
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new FileInputStream(this.file);
	}

	public OutputStream getOutputStream() throws IOException {
		return new FileOutputStream(this.file);
	}

	public File getFile() {
		return this.file;
	}

	@Override
	public long getSize() {
		return this.file.length();
	}

}
