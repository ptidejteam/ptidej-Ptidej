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
package sad.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * A custom class loader which enables the reloading
 * of classes for each test run. The class loader
 * can be configured with a list of package paths that
 * should be excluded from loading. The loading
 * of these packages is delegated to the system class
 * loader. They will be shared across test runs.
 * <p>
 * The list of excluded package paths is specified in
 * a properties file "excluded.properties" that is located in 
 * the same place as the TestCaseClassLoader class.
 * <p>
 * <b>Known limitation:</b> the TestCaseClassLoader cannot load classes
 * from jar files.
 */

public class ClassLoader extends java.lang.ClassLoader {
	/** scanned class path */
	private Vector fPathItems;
	/** default excluded paths */
	private String[] defaultInclusions = { "sad." };
	/** name of excluded properties file */
	static final String EXCLUDED_FILE = "excluded.properties";
	/** excluded paths */
	private Vector fIncluded;

	/**
	 * Constructs a TestCaseLoader. It scans the class path
	 * and the excluded package paths
	 */
	public ClassLoader() {
		this(System.getProperty("java.class.path"));
	}

	/**
	 * Constructs a TestCaseLoader. It scans the class path
	 * and the included package paths
	 */
	public ClassLoader(String classPath) {
		scanPath(classPath);
		readIncludedPackages();
	}

	private void scanPath(String classPath) {
		String separator = System.getProperty("path.separator");
		this.fPathItems = new Vector(10);
		StringTokenizer st = new StringTokenizer(classPath, separator);
		while (st.hasMoreTokens()) {
			this.fPathItems.addElement(st.nextToken());
		}
	}

	public URL getResource(String name) {
		return getSystemResource(name);
	}

	public InputStream getResourceAsStream(String name) {
		return getSystemResourceAsStream(name);
	}

	public boolean isIncluded(String name) {
		for (int i = 0; i < this.fIncluded.size(); i++) {
			if (name.startsWith((String) this.fIncluded.elementAt(i))) {
				return true;
			}
		}
		return false;
	}

	public synchronized Class loadClass(String name, boolean resolve)
			throws ClassNotFoundException {

		Class c = findLoadedClass(name);
		if (c != null)
			return c;
		//
		// Delegate the loading of excluded classes to the
		// standard class loader.
		//
		if (!isIncluded(name)) {
			try {
				c = findSystemClass(name);
				return c;
			}
			catch (ClassNotFoundException e) {
				// keep searching
			}
		}
		if (c == null) {
			byte[] data = lookupClassData(name);
			if (data == null)
				throw new ClassNotFoundException();
			c = defineClass(name, data, 0, data.length);
		}
		if (resolve)
			resolveClass(c);
		return c;
	}

	private byte[] lookupClassData(String className)
			throws ClassNotFoundException {
		byte[] data = null;
		for (int i = 0; i < this.fPathItems.size(); i++) {
			String path = (String) this.fPathItems.elementAt(i);
			String fileName = className.replace('.', '/') + ".class";
			if (isJar(path)) {
				data = loadJarData(path, fileName);
			}
			else {
				data = loadFileData(path, fileName);
			}
			if (data != null)
				return data;
		}
		throw new ClassNotFoundException(className);
	}

	boolean isJar(String pathEntry) {
		return pathEntry.endsWith(".jar") || pathEntry.endsWith(".zip");
	}

	private byte[] loadFileData(String path, String fileName) {
		File file = new File(path, fileName);
		if (file.exists()) {
			return getClassData(file);
		}
		return null;
	}

	private byte[] getClassData(File f) {
		try {
			FileInputStream stream = new FileInputStream(f);
			ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = stream.read(b)) != -1)
				out.write(b, 0, n);
			stream.close();
			out.close();
			return out.toByteArray();

		}
		catch (IOException e) {
		}
		return null;
	}

	private byte[] loadJarData(String path, String fileName) {
		ZipFile zipFile = null;
		InputStream stream = null;
		File archive = new File(path);
		if (!archive.exists())
			return null;
		try {
			zipFile = new ZipFile(archive);
		}
		catch (IOException io) {
			return null;
		}
		ZipEntry entry = zipFile.getEntry(fileName);
		if (entry == null) {
			try {
				zipFile.close();
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		int size = (int) entry.getSize();
		try {
			stream = zipFile.getInputStream(entry);
			byte[] data = new byte[size];
			int pos = 0;
			while (pos < size) {
				int n = stream.read(data, pos, data.length - pos);
				pos += n;
			}
			zipFile.close();
			return data;
		}
		catch (IOException e) {
		}
		finally {
			try {
				if (stream != null)
					stream.close();
			}
			catch (IOException e) {
			}
		}
		return null;
	}

	private void readIncludedPackages() {
		this.fIncluded = new Vector(10);
		for (int i = 0; i < this.defaultInclusions.length; i++)
			this.fIncluded.addElement(this.defaultInclusions[i]);
	}
}
