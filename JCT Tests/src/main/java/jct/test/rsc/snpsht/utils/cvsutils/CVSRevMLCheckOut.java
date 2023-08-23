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
import java.net.URISyntaxException;

import jct.test.rsc.snpsht.utils.FSUtils;

public class CVSRevMLCheckOut {
	private CVSRoot cvsRoot;
	private Runtime rt = Runtime.getRuntime();
	private File tempDir;

	public CVSRevMLCheckOut(CVSRoot cvsRoot) {
		this.cvsRoot = cvsRoot;
		getAndFlushTempDir();
	}

	private File getAndFlushTempDir() {
		this.tempDir =
			new File(System.getProperty("java.io.tmpdir"), "tmp_dir_revML_co_"
					+ this.hashCode());

		if (this.tempDir.exists()) {
			FSUtils.rmDir(this.tempDir);
		}

		this.tempDir.mkdirs();

		return this.tempDir;
	}

	/**
	 * Checkout RevML file from the given module to the given target file.<br>
	 * A module can be a file, or a repository. If module is a repository it's
	 * name must end with "/".<br>
	 * Target file must be a file or not exist, but it's parent directory must
	 * exist.
	 * 
	 * @param module
	 *        String that represent path to the module.
	 * @param targetFile
	 *        New RevML file.
	 * @throw IOException If can't create or overwrite target file.
	 * @throw URISyntaxException if VCP exe can't be find in resource package.
	 */
	public boolean checkOut(String module, File targetFile) throws IOException,
			URISyntaxException {
		Process p;
		InputStream is;
		int c;
		String answer = "", osName, repo, targetReativePath;
		File vcpExe;

		if (targetFile.isDirectory())
			throw new IllegalArgumentException("Target file is a diretory");

		getAndFlushTempDir();

		// Trim '/' of repository first char
		if (module.startsWith("/")) {
			repo = module.substring(1);
		} else {
			repo = module;
		}

		// If module is a repository, we specify that we want to download all
		// sud-repo content
		if (module.endsWith("/")) {
			repo += "...";
		}

		// Load VCP exe depending host OS
		osName = System.getProperty("os.name").toLowerCase();
		if (osName.contains("linux")) {
			vcpExe =
				new File(CVSRevMLCheckOut.class.getResource(
					"/jct.test.rsc.snpsht.verfilesystem/resource/vcp").toURI());

			// Ensure that VCP file is executable
			p = this.rt.exec("chmod u+x " + vcpExe.getAbsolutePath());

		} else if (osName.contains("os x")) {
			// Never tested, need to check execution rights
			// Implementation normally close to linux
			vcpExe =
				new File(CVSRevMLCheckOut.class.getResource(
					"/jct.test.rsc.snpsht.verfilesystem/resource/vcp").toURI());

			// Ensure that VCP file is executable
			p = this.rt.exec("chmod u+x " + vcpExe.getAbsolutePath());
		} else if (osName.contains("win")) {
			vcpExe =
				new File(CVSRevMLCheckOut.class.getResource(
					"/jct.test.rsc.snpsht.verfilesystem/resource/vcp.exe").toURI());
		} else {
			// Never tested
			throw new Error("Unknow host system '" + osName + "'");
		}

		// Because VCP works only with relative paths
		targetReativePath =
			FSUtils.getRelativePath(
				new File(System.getProperty("user.dir")),
				targetFile);

		p =
			this.rt.exec(vcpExe.getAbsolutePath() + " cvs:"
					+ this.cvsRoot.getCVSROOT() + ":" + repo + " revml:"
					+ targetReativePath);

		System.out.println(vcpExe.getAbsolutePath() + " cvs:"
				+ this.cvsRoot.toString() + ":" + repo + " revml:"
				+ targetReativePath);

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

		answer = answer.toLowerCase();

		if (answer.contains("aborted") || answer.contains("failed")
				|| answer.contains("stderr") || answer.contains("undefined")) {
			return false;
		} else {
			return (targetFile.isFile() && targetFile.exists());
		}
	}

}
