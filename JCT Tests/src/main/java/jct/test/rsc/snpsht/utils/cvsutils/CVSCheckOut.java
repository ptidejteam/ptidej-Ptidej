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
package jct.test.rsc.snpsht.utils.cvsutils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import jct.test.rsc.snpsht.utils.FSUtils;

public class CVSCheckOut {
	private CVSRoot cvsRoot;
	private Runtime rt = Runtime.getRuntime();
	private File tempDir;

	public CVSCheckOut(CVSRoot cvsRoot) {
		this.cvsRoot = cvsRoot;
		getAndFlushTempDir();
	}

	private File getAndFlushTempDir() {
		this.tempDir =
			new File(System.getProperty("java.io.tmpdir"), "tmp_dir_cvs_co_"
					+ this.hashCode());

		if (this.tempDir.exists()) {
			FSUtils.rmDir(this.tempDir);
		}

		this.tempDir.mkdirs();

		return this.tempDir;
	}

	public boolean checkOut(String module, File targetDir) throws IOException {
		Process p;
		InputStream is;
		int c;
		String answer = "";

		if (!targetDir.exists()) {
			if (!targetDir.mkdirs()) {
				throw new IllegalArgumentException("Can't create direcory '"
						+ targetDir.getAbsolutePath() + "'");
			}
		} else if (!targetDir.isDirectory()) {
			throw new IllegalArgumentException("Destination directory ('"
					+ targetDir.getAbsolutePath() + "') is a file.");
		}

		p =
			this.rt.exec("cvs co " + module, new String[] { "CVSROOT="
					+ this.cvsRoot.getCVSROOT() }, targetDir);

		// Display server answer
		is = p.getInputStream();
		while ((c = is.read()) != -1) {
			System.out.print((char) c);
		}

		is.close();

		is = p.getErrorStream();
		while ((c = is.read()) != -1) {
			answer += (char) c;
		}
		System.err.println(answer);

		is.close();

		return !answer.contains("aborted") && !answer.contains("warning");
	}

	public boolean checkOut(String fileRevPath, String revision, File targetFile)
			throws IOException {
		Process p;
		InputStream is;
		int c;
		String answer = "";
		File tmpFile;

		if (targetFile.isDirectory())
			throw new IllegalArgumentException("Target file is a diretory");

		getAndFlushTempDir();
		
		p =
			this.rt.exec(
				"cvs co -r " + revision + " " + fileRevPath,
				new String[] { "CVSROOT=" + this.cvsRoot.getCVSROOT() },
				this.tempDir);
		System.out.println("cvs co -r " + revision + " " + fileRevPath);
		
		// Display server answer
		is = p.getInputStream();
		while ((c = is.read()) != -1) {
			answer += (char) c;
		}
		System.out.print(answer);

		is.close();

		answer = "";
		is = p.getErrorStream();
		while ((c = is.read()) != -1) {
			answer += (char) c;
		}
		System.out.print(answer);

		is.close();

		if (answer.contains("aborted") || answer.contains("warning")) {
			return false;
		} else {
			tmpFile = getFile(fileRevPath, this.tempDir);

			if (!tmpFile.isFile() || !tmpFile.exists())
				return false;

			targetFile.getParentFile().mkdirs();
			//FSUtils.copyFile(tmpFile, targetFile);
			tmpFile.renameTo(targetFile);

			return (targetFile.isFile() && targetFile.exists());
		}
	}

	private File getFile(String cvsPath, File contentDir) {
		int firstIndex = cvsPath.indexOf(this.cvsRoot.getSeparator());

		if (contentDir.isDirectory()) {
			if (firstIndex == -1 || firstIndex == cvsPath.length() - 1) {
				for (File child : contentDir.listFiles()) {
					if (child.getName().compareTo(cvsPath) == 0)
						return child;
				}
			} else {
				for (File child : contentDir.listFiles()) {
					if (child.getName().compareTo(
						cvsPath.substring(0, firstIndex)) == 0) {
						return getFile(cvsPath.substring(firstIndex + 1), child);
					}
				}
			}
		}

		return null;
	}

}
