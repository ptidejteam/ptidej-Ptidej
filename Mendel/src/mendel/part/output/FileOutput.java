/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package mendel.part.output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import mendel.Util;
import mendel.part.AbstractPart;
import util.io.ProxyDisk;

/**
 * Suffix: txt.
 * 
 * Initializating Properties:
 * - path: absolute path (default: System.getProperty("user.dir"))
 * - outputfile: filename where to save results (default: projectname)
 * - qualifier: prefix appended to outputfile (can be a path like 'mdl/csv/') - can be null
 * - suffix: suffix appended to outputfile (like '.dot' or '.csv') - can be null
 * 
 * fullpath = path + / + qualifier + outputfile + suffix
 * 
 * Input: Object
 * Output: Object
 * 
 * @author Simon Denier
 * @since Feb 15, 2008
 * 
 */
public class FileOutput extends AbstractPart {

	private String name;

	private String path;

	private String qualifier;

	private String suffix;

	private BufferedWriter writer;

	public FileOutput() {
		setSuffix(".txt");
	}

	/* (non-Javadoc)
	 * @see mendel.IPart#initialize(java.util.Properties)
	 */
	public void initialize(Properties properties) {
		setPath(properties.getProperty("path"));
		setFilename(properties.getProperty("outputfile"));
		setQualifier(properties.getProperty("qualifier"));
		setSuffix(properties.getProperty("suffix"));
	}

	public void initializePath(
		String path,
		String qualifier,
		String name,
		String suffix) {
		setFilename(name);
		setPath(path);
		setQualifier(qualifier);
		setSuffix(suffix);
	}

	/**
	 * 
	 * @return
	 */
	public String fullPath() {
		String name = qualifier() + filename() + suffix();
		return path() + File.separatorChar + name;
	}

	/**
	 * @return the filename
	 */
	public String filename() {
		if (this.name != null) {
			return this.name;
		}
		else {
			return Util.escape(getProject().getProjectname());
		}

	}

	/**
	 * @param object
	 */
	public void setFilename(String outputfile) {
		this.name = outputfile;
	}

	public String path() {
		if (this.path == null) {
			return System.getProperty("user.dir");
		}
		else {
			return this.path();
		}
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String qualifier() {
		if (this.qualifier == null) {
			return "";
		}
		else {
			return this.qualifier;
		}
	}

	public void setQualifier(String q) {
		this.qualifier = q;
	}

	public String suffix() {
		return this.suffix;
	}

	public void setSuffix(String suf) {
		/*
		 * This code smells bad. We do that because we can have a default
		 * initialization in constructor
		 */
		if (suf != null) {
			this.suffix = suf; // set suffix if parameter not null
		}
		else { // if null
			if (this.suffix == null) { // set suffix if null
				this.suffix = ""; // with empty string
			}
			// else do not set suffix if already set
		}
	}

	public BufferedWriter writer() {
		return this.writer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mendel.IOutput#open()
	 */
	public void startMe() {
		this.writer =
			new BufferedWriter(ProxyDisk.getInstance().fileTempOutput(
				this.fullPath()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mendel.IOutput#close()
	 */
	public void endMe() {
		try {
			if (writer() != null) {
				writer().close();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Object compute(Object data) {
		write(data);
		return data;
	}

	protected void write(Object data) {
		try {
			if (writer() != null) {
				writer().write(data.toString());
				writer().newLine();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
