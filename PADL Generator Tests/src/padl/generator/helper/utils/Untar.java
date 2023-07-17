/*******************************************************************************
 *   Gisgraphy Project 
 * 
 *   This library is free software; you can redistribute it and/or
 *   modify it under the terms of the GNU Lesser General Public
 *   License as published by the Free Software Foundation; either
 *   version 2.1 of the License, or (at your option) any later version.
 * 
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *   Lesser General Public License for more details.
 * 
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the Free Software
 *   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA
 * 
 *  Copyright 2008  Gisgraphy project 
 *  David Masclet 
 *  
 *  
 *******************************************************************************/
/**
 * Aminata : I just modified the concatenation of strings by using StringBuffer 
 * I added also a method log to handle log information and reduce the number of instructions for that in the methods
 */
package padl.generator.helper.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import org.apache.tools.bzip2.CBZip2InputStream;
import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarInputStream;

/**
 * Utility class to untar files, files can be zipped in multi format (extension
 * tar, tar.gzip,tar.gz, tar.bz2, tar.bzip2 are supported).
 * 
 * @author <a href="mailto:david.masclet@gisgraphy.com">David Masclet</a>
 * 
 */

public class Untar {
	private final String tarFileName;
	private final File dest;

	/**
	 * (note : constructor that takes two files as parameter could probably be a better design)
	 * @param tarFileName
	 *            the path to the file we want to untar
	 * @param dest
	 *            the directory where the file should be untar
	 */
	public Untar(final String tarFileName, final File dest) {
		this.tarFileName = tarFileName;
		this.dest = dest;
	}

	private void log(String methodName, String fileName, File dest) {
		final StringBuffer logInfo = new StringBuffer();
		logInfo.append("methodName");
		logInfo.append(fileName);
		logInfo.append("to");
		logInfo.append(dest);
	}

	private InputStream getDecompressedInputStream(
		final String name,
		final InputStream istream) throws IOException {

		this.log("untar : decompress", name, this.dest);

		if (name == null) {
			throw new RuntimeException("fileName to decompress can not be null");
		}
		if (name.toLowerCase().endsWith("gzip")
				|| name.toLowerCase().endsWith("gz")) {
			return new BufferedInputStream(new GZIPInputStream(istream));
		}
		else if (name.toLowerCase().endsWith("bz2")
				|| name.toLowerCase().endsWith("bzip2")) {
			final char[] magic = new char[] { 'B', 'Z' };
			for (int i = 0; i < magic.length; i++) {
				if (istream.read() != magic[i]) {
					throw new RuntimeException("Invalid bz2 file." + name);
				}
			}
			return new BufferedInputStream(new CBZip2InputStream(istream));
		}
		else if (name.toLowerCase().endsWith("tar")) {
			return istream;
		}
		throw new RuntimeException(
			"can only detect compression for extension tar, gzip, gz, bz2, or bzip2");
	}

	/**
	 * process the untar operation
	 * 
	 * @throws IOException
	 */
	public void untar() throws IOException {

		this.log("untar: untar ", this.tarFileName, this.dest);

		TarInputStream tin = null;
		try {
			if (!this.dest.exists()) {
				this.dest.mkdir();
			}

			tin =
				new TarInputStream(this.getDecompressedInputStream(
					this.tarFileName,
					new FileInputStream(new File(this.tarFileName))));

			TarEntry tarEntry = tin.getNextEntry();

			while (tarEntry != null) {
				StringBuffer path = new StringBuffer();
				path.append(this.dest.toString());
				path.append(File.separatorChar);
				path.append(tarEntry.getName());
				final File destPath = new File(path.toString());

				if (tarEntry.isDirectory()) {
					destPath.mkdir();
				}
				else {
					if (!destPath.getParentFile().exists()) {
						destPath.getParentFile().mkdirs();
					}

					this.log("untar: untar ", tarEntry.getName(), destPath);

					final FileOutputStream fout =
						new FileOutputStream(destPath);
					try {
						tin.copyEntryContents(fout);
					}
					finally {
						fout.flush();
						fout.close();
					}
				}
				tarEntry = tin.getNextEntry();
			}
		}
		finally {
			if (tin != null) {
				tin.close();
			}
		}

	}
}