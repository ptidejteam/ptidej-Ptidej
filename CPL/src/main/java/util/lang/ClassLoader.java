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
package util.lang;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import util.io.ProxyConsole;
import util.multilingual.MultilingualManager;

public class ClassLoader extends java.lang.ClassLoader {
	private final String directory;

	public ClassLoader(
		final java.lang.ClassLoader parent,
		final String directory) {

		super(parent);
		this.directory = directory;
	}

	private Class<?> defineClasses(final String name, final InputStream inputStream) {
		try {
			int b;
			int length = 0;
			byte[] bytes = new byte[4];
			while ((b = inputStream.read()) != -1) {
				if (length == bytes.length) {
					final byte[] temp = new byte[length + 4];
					System.arraycopy(bytes, 0, temp, 0, length);
					bytes = temp;
				}
				bytes[length] = (byte) b;
				length++;
			}

			System.out.println(name);
			final Class<?> newClass = this.defineClass(name, bytes, 0, length);
			return newClass;
		}
		catch (final IOException ioe) {
			return null;
		}
		catch (final NoClassDefFoundError ncdfe) {
			// This error happens when the defineClass(...) method
			// defines a class for which it cannot find dependent
			// classes. For example, this error happens when the
			// defineClass(...) method defines class:
			//     fr.emn.claire.ClairePlugin
			// while the following class is absent:
			//     org.eclipse.ui.plugin.AbstractUIPlugin

			ncdfe.printStackTrace(ProxyConsole.getInstance().errorOutput());
			return null;
		}
	}
	protected Class<?> findClass(final String name) {
		Class<?> newClass = null;
		try {
			//	newClass = this.getParent().loadClass(name);
			newClass = super.findClass(name);
		}
		catch (final ClassNotFoundException cnfe) {
			final String osName =
				this.directory + name.replace('.', '/') + ".class";

			try {
				final FileInputStream fis = new FileInputStream(osName);
				newClass = this.defineClasses(name, fis);
			}
			catch (final ClassFormatError cfe) {
				ProxyConsole
					.getInstance()
					.errorOutput()
					.print(
						MultilingualManager.getString(
							"Err_FILE",
							ClassLoader.class,
							new Object[] { osName }));
				cfe.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
			catch (final FileNotFoundException fnfe) {
				// fnfe.printStackTrace();
			}
		}

		return newClass;
	}
	protected URL findResource(final String name) {
		URL url = super.findResource(name);

		if (url == null) {
			try {
				url = new URL("file", "", "/" + this.directory);
			}
			catch (final MalformedURLException mfue) {
				mfue.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
		}

		return url;
	}
	//	public Class loadClass(String name, boolean resolve)
	//		throws ClassNotFoundException {
	//		// First, check if the class has already been loaded
	//		Class c = this.findLoadedClass(name);
	//
	//		if (c == null || resolve) {
	//			try {
	//				c = this.getParent().loadClass(name);
	//			}
	//			catch (final ClassNotFoundException e) {
	//				// If still not found, then call findClass in order
	//				// to find the class.
	//				c = this.findClass(name);
	//			}
	//		}
	//		if (resolve) {
	//			this.resolveClass(c);
	//		}
	//
	//		return c;
	//	}
	//	public Class loadClass(final String name, final JarFile jarFile) {
	//		Class newClass = null;
	//		try {
	//			newClass = super.findClass(name);
	//		}
	//		catch (final ClassNotFoundException cnfe) {
	//			final String osName = name.replace('.', '/') + ".class";
	//
	//			try {
	//				newClass =
	//					this.defineClasses(name, jarFile.getInputStream(jarFile
	//						.getJarEntry(osName)));
	//			}
	//			catch (final ClassFormatError cfe) {
	//				Output.getInstance().errorOutput().print(
	//					MultilingualManager.getString(
	//						"Err_FILE",
	//						ClassLoader.class,
	//						new Object[] { osName }));
	//				cfe.printStackTrace(Output.getInstance().errorOutput());
	//			}
	//			catch (final FileNotFoundException fnfe) {
	//				// fnfe.printStackTrace();
	//			}
	//			catch (final IOException ioe) {
	//				// ioe.printStackTrace();
	//			}
	//		}
	//
	//		return newClass;
	//	}
}
