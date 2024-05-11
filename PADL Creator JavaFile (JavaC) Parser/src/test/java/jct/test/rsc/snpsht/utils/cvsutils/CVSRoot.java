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

import java.io.IOException;
import java.io.InputStream;

public class CVSRoot {

	private String protocol;
	private String server;
	private String user;
	private String password;
	private String rootRepository;
	private String repository;
	private String separator = "/";

	public CVSRoot(
		String protocol,
		String server,
		String user,
		String password,
		String rootRepository,
		String repository) {
		setPassword(password);
		setProtocol(protocol);
		setCurrentRepository(repository);
		setRootRepository(rootRepository);
		setServer(server);
		setUser(user);
	}

	public CVSRoot(
		String protocol,
		String server,
		String rootRepository,
		String repository) {
		setProtocol(protocol);
		setCurrentRepository(repository);
		setRootRepository(rootRepository);
		setServer(server);
	}

	public String getProtocol() {
		return this.protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getServer() {
		return this.server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRootRepository() {
		return this.rootRepository;
	}

	public void setRootRepository(String rootRepository) {
		if (rootRepository.substring(rootRepository.length() - 1).compareTo(
			this.separator) == 0) {
			this.rootRepository = rootRepository;
		} else {
			this.rootRepository = rootRepository + this.separator;
			// this.rootRepository = rootRepository;
		}
	}

	public String getCurrentRepository() {
		return this.repository;
	}

	public void setCurrentRepository(String repository) {
		this.repository = repository;
	}

	public String getSeparator() {
		return this.separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	/**
	 * Returns CVSROOT string with or without password.
	 * 
	 * @return CVSROOT string.
	 */
	public String getCVSROOT(boolean withPassword) {
		if (withPassword)
			return ":" + this.protocol + ":" + this.user + ":" + this.password
					+ "@" + this.server + this.rootRepository + this.repository;
		else
			return ":" + this.protocol + ":" + this.user + "@" + this.server
					+ this.rootRepository + this.repository;
	}

	/**
	 * Returns CVSROOT string with password.
	 * 
	 * @return CVSROOT string.
	 */
	public String getCVSROOT() {
		return getCVSROOT(true);
	}

	/**
	 * Returns CVSROOT string with an hidden password.
	 * 
	 * @return CVSROOT string with an hidden password.
	 */
	@Override
	public String toString() {
		return ":" + this.protocol + ":" + this.user + ":<password>" + "@"
				+ this.server + this.rootRepository + this.repository;
	}

	/**
	 * This function allows to check if this CVSRoot instance is correct and can
	 * be connect to server.<br>
	 * Thus, this function will display commands and server's answers on
	 * standard output & error.
	 * 
	 * @return False if connection failed, else True
	 * @throws IOException
	 * @throws IOException
	 *         If 'cvs' system command can't be executed
	 */
	public boolean checkCVSROOT() throws IOException {
		boolean error = false;
		int c;
		InputStream is;
		Process p;
		Runtime rt = Runtime.getRuntime();

		// Login to server
		p = rt.exec("cvs -d " + getCVSROOT() + " login");
		System.out.println("cvs -d " + this.toString() + " login");

		// Display server answer
		is = p.getInputStream();
		while ((c = is.read()) != -1) {
			System.out.print((char) c);
		}

		is = p.getErrorStream();
		if ((c = is.read()) != -1) {
			error = true;
			do {
				System.err.print((char) c);
			} while ((c = is.read()) != -1);
		}

		is.close();

		return !error;
	}

	// public static void main(String[] args) {
	// CVSRoot cvsroot =
	// new CVSRoot(
	// "pserver",
	// "cvs.iro.umontreal.ca",
	// "tanterij",
	// "Belle3li",
	// "/home/cvs/cvsgelo/cvsroot",
	// "");
	//
	// // CVSls ls = new CVSls(cvsroot);
	// //
	// // System.out.println(cvsroot.getCVSROOT());
	// //
	// // try {
	// // System.out.println("check CVSROOT: " + cvsroot.checkCVSROOT());
	// // FsElement root = ls.ls(new FsFolder("~", cvsroot.rootRepository +
	// "tanterij-program-wiiTablet/", null), -1);
	// // System.out.println(root.prettyPrint());
	// // // CVSls.ls(cvsroot, "/");
	// // } catch (IOException e) {
	// // // TODO Auto-generated catch block
	// // e.printStackTrace();
	// // }
	// }
}
