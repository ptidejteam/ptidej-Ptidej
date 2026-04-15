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
package util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;

import com.ibm.toad.cfparse.ClassFile;

import util.lang.CFParseBCELConvertorVisitor;
import util.multilingual.MultilingualManager;

public final class SubtypeLoader {
	private static class ClassNameAlphabeticalComparator
			implements Comparator<Object> {

		public int compare(final ClassFile c1, final ClassFile c2) {
			return c1.getName().compareTo(c2.getName());
		}

		public int compare(final Object o1, final Object o2) {
			if (o1 instanceof ClassFile && o2 instanceof ClassFile) {
				return this.compare((ClassFile) o1, (ClassFile) o2);
			}
			return 0;
		}
	}

	public static ClassFile[] loadRecursivelySubtypesFromDir(
			final String aSuperTypeName, final String aDirectoryName,
			final String aFileExtension) {

		final File currentDirectory = new File(aDirectoryName);
		final String currentList[] = currentDirectory.list();

		if (currentList == null || currentList.length == 0) {
			ProxyConsole.getInstance().warningOutput()
					.println(MultilingualManager.getString(
							"Err_FILES_NOT_FOUND", SubtypeLoader.class,
							new Object[] { aDirectoryName }));
			return new ClassFile[0];
		}

		final List<ClassFile> aListOfClasses = new ArrayList<ClassFile>(
				currentList.length);
		for (int x = 0; x < currentList.length; x++) {
			final String itemName = aDirectoryName + currentList[x];

			if (new File(itemName).isFile()) {
				aListOfClasses
						.addAll(Arrays.asList(SubtypeLoader.loadSubtypeFromFile(
								aSuperTypeName, itemName, aFileExtension)));
			}
			else if (new File(itemName).isDirectory()) {
				aListOfClasses.addAll(Arrays
						.asList(SubtypeLoader.loadRecursivelySubtypesFromDir(
								aSuperTypeName, itemName + File.separatorChar,
								aFileExtension)));
			}
		}

		if (aListOfClasses.size() == 0) {
			ProxyConsole.getInstance().warningOutput()
					.println(MultilingualManager.getString(
							"Err_FILES_NOT_FOUND", SubtypeLoader.class,
							new Object[] { aDirectoryName }));
			return new ClassFile[0];
		}
		else {
			final ClassFile[] results = new ClassFile[aListOfClasses.size()];
			aListOfClasses.toArray(results);
			Arrays.sort(results, new ClassNameAlphabeticalComparator());
			return results;
		}
	}

	// aliiimaher 2026/02/10:
	// I removed the logging part from the following method, which was not really useful,
	// it was just printing the name of the file being loaded.
	
	// TODO: Make it private
	public static ClassFile[] loadSubtypeFromFile(final String aSuperTypeName,
			final String aFileName, final String aFileExtension) {

		final List<ClassFile> aListOfClasses = new ArrayList<ClassFile>();
		if (aFileName.endsWith(aFileExtension)) {
			try {
				final InputStream anInputStream = new FileInputStream(
						aFileName);
				SubtypeLoader.loadSubtypeFromStream(aSuperTypeName,
						aListOfClasses,
						new NamedInputStream(aFileName, anInputStream));
				anInputStream.close();
			}
			catch (final Exception e) {
				ProxyConsole.getInstance().errorOutput().print(e);
				ProxyConsole.getInstance().errorOutput()
						.print(": Exception while reading file: ");
				ProxyConsole.getInstance().errorOutput().println(aFileName);
			}
		}

		final ClassFile[] results = new ClassFile[aListOfClasses.size()];
		aListOfClasses.toArray(results);
		return results;
	}

	private static void loadSubtypeFromStream(final String aSuperTypeName,
			final List<ClassFile> aListOfClasses,
			final NamedInputStream aNamedInputStream) throws IOException {

		final InputStream inputStream0 = aNamedInputStream.getStream();
		final ClassFile currentClass_CFPARSE = new ClassFile(inputStream0);
		inputStream0.close();

		final InputStream inputStream1 = aNamedInputStream.getStream();
		final ClassParser parser = new ClassParser(inputStream1, "");
		final JavaClass javaClass = parser.parse();
		final ClassFile currentClass_BCEL1 = CFParseBCELConvertorVisitor
				.convertClassFile(javaClass);
		/*
		final ClassFile currentClass_BCEL2 = CFParseBCELConvertorAdhoc
				.convertClassFile(javaClass);
		inputStream1.close();
		 */

		ClassFile currentClass;
		/*
		if (currentClass_CFPARSE.equals(currentClass_BCEL1)) {
			currentClass = currentClass_BCEL1;
		}
		else if (currentClass_CFPARSE.equals(currentClass_BCEL2)) {
			currentClass = currentClass_BCEL2;
		}
		else {
			ProxyConsole.getInstance().debugOutput()
					.print("(Beware that the classfile of ");
			ProxyConsole.getInstance().debugOutput()
					.print(currentClass_BCEL1.getName());
			ProxyConsole.getInstance().debugOutput()
					.println(" is incomplete!)");
			currentClass = currentClass_CFPARSE;
		}
		*/

		// Force the use of CFParse for the moment...
		currentClass = currentClass_CFPARSE;
		if (aSuperTypeName == null
				|| currentClass.getSuperName().equals(aSuperTypeName)) {

			aListOfClasses.add(currentClass);
		}
		else {
			boolean isSuperInterfaceFound = false;
			for (int i = 0; i < currentClass.getInterfaces().length()
					&& !isSuperInterfaceFound; i++) {

				if (currentClass.getInterfaces().get(i)
						.equals(aSuperTypeName)) {
					aListOfClasses.add(currentClass);
					isSuperInterfaceFound = true;
				}
			}
		}
	}

	public static ClassFile[] loadSubtypesFromDir(final String aSuperTypeName,
			final String aDirectoryName, final String aFileExtension) {

		final File currentDirectory = new File(aDirectoryName);
		final String currentList[] = currentDirectory
				.list(new ExtensionBasedFilenameFilter(aFileExtension));
		if (currentList == null || currentList.length == 0) {
			ProxyConsole.getInstance().warningOutput()
					.println(MultilingualManager.getString(
							"Err_FILES_NOT_FOUND", SubtypeLoader.class,
							new Object[] { aDirectoryName }));
			return new ClassFile[0];
		}

		final List<ClassFile> aListOfClasses = new ArrayList<ClassFile>(
				currentList.length);
		for (int x = 0; x < currentList.length; x++) {
			aListOfClasses.addAll(Arrays
					.asList(SubtypeLoader.loadSubtypeFromFile(aSuperTypeName,
							aDirectoryName + currentList[x], aFileExtension)));
		}

		final ClassFile[] results = new ClassFile[aListOfClasses.size()];
		aListOfClasses.toArray(results);
		Arrays.sort(results, new ClassNameAlphabeticalComparator());
		return results;
	}

	public static ClassFile[] loadSubtypesFromJar(final String aSuperTypeName,
			final String aJARFileName, final String aFileExtension) {

		try {
			final JarFile currentJarFile = new JarFile(aJARFileName);
			if (currentJarFile.size() == 0) {
				ProxyConsole.getInstance().errorOutput()
						.println(MultilingualManager.getString(
								"Err_READING_FILE", SubtypeLoader.class,
								new Object[] { aJARFileName }));
				currentJarFile.close();
				throw new RuntimeException("JAR file has size 0!?");
			}

			final List<ClassFile> aListOfClasses = new ArrayList<ClassFile>();
			final Enumeration<?> classList = currentJarFile.entries();

			while (classList.hasMoreElements()) {
				try {
					final JarEntry jarEntry = (JarEntry) classList
							.nextElement();

					if (!jarEntry.isDirectory()
							&& jarEntry.getName().endsWith(aFileExtension)) {

						final InputStream anInputStream = currentJarFile
								.getInputStream(jarEntry);
						final ClassFile currentClass = new ClassFile(
								anInputStream);

						if (aSuperTypeName == null
								|| currentClass.getSuperName() != null
										&& currentClass.getSuperName()
												.equals(aSuperTypeName)) {

							aListOfClasses.add(currentClass);
						}
						else {
							boolean isSuperInterfaceFound = false;
							for (int i = 0; i < currentClass.getInterfaces()
									.length() && !isSuperInterfaceFound; i++) {
								if (currentClass.getInterfaces().get(i)
										.equals(aSuperTypeName)) {

									aListOfClasses.add(currentClass);
									isSuperInterfaceFound = true;
								}
							}
						}

						anInputStream.close();
					}
				}
				catch (final Exception e) {
					e.printStackTrace(ProxyConsole.getInstance().errorOutput());
				}
			}
			currentJarFile.close();

			final ClassFile[] results = new ClassFile[aListOfClasses.size()];
			aListOfClasses.toArray(results);
			Arrays.sort(results, new ClassNameAlphabeticalComparator());
			return results;
		}
		catch (final IOException ioe) {
			ProxyConsole.getInstance().errorOutput()
					.println(MultilingualManager.getString("Err_READING_FILE",
							SubtypeLoader.class,
							new Object[] { aJARFileName }));
			ioe.printStackTrace(ProxyConsole.getInstance().errorOutput());
			throw new RuntimeException(ioe);
		}
	}

	public static ClassFile[] loadSubtypesFromJarInputStream(
			final String aSuperTypeName, final JarInputStream aJARInputStream,
			final String aFileExtension) {

		try {
			if (aJARInputStream.available() == 0) {
				ProxyConsole.getInstance().errorOutput()
						.println(MultilingualManager.getString(
								"Err_READING_STREAM", SubtypeLoader.class,
								new Object[] { "jar input stream" }));
				aJARInputStream.close();
				throw new RuntimeException("No data available");
			}

			final List<ClassFile> aListOfClasses = new ArrayList<ClassFile>();

			for (JarEntry jarEntry = aJARInputStream
					.getNextJarEntry(); jarEntry != null; jarEntry = aJARInputStream
							.getNextJarEntry()) {
				try {
					if (!jarEntry.isDirectory()
							&& jarEntry.getName().endsWith(aFileExtension)) {

						final InputStream anInputStream = aJARInputStream;
						/*
						final BufferedInputStream anInputStream = new BufferedInputStream(
								aJARInputStream, (int) jarEntry.getSize());
						*/
						final ClassFile currentClass = new ClassFile(
								anInputStream);

						if (aSuperTypeName == null
								|| currentClass.getSuperName() != null
										&& currentClass.getSuperName()
												.equals(aSuperTypeName)) {

							aListOfClasses.add(currentClass);
						}
						else {
							boolean isSuperInterfaceFound = false;
							for (int i = 0; i < currentClass.getInterfaces()
									.length() && !isSuperInterfaceFound; i++) {
								if (currentClass.getInterfaces().get(i)
										.equals(aSuperTypeName)) {

									aListOfClasses.add(currentClass);
									isSuperInterfaceFound = true;
								}
							}
						}
					}

					aJARInputStream.closeEntry();
				}
				catch (final Exception e) {
					e.printStackTrace(ProxyConsole.getInstance().errorOutput());
				}
			}
			aJARInputStream.close();

			final ClassFile[] results = new ClassFile[aListOfClasses.size()];
			aListOfClasses.toArray(results);
			Arrays.sort(results, new ClassNameAlphabeticalComparator());
			return results;
		}
		catch (final IOException ioe) {
			ProxyConsole.getInstance().errorOutput()
					.println(MultilingualManager.getString("Err_READING_FILE",
							SubtypeLoader.class,
							new Object[] { "Jar Input Stream" }));
			ioe.printStackTrace(ProxyConsole.getInstance().errorOutput());
			throw new RuntimeException(ioe);
		}
	}

	// aliiimaher 2026/02/10:
	// I removed the logging part from the following method, which was not really useful,
	// it was just printing the name of the file being loaded.
	public static ClassFile[] loadSubtypesFromStreams(
			final String aSuperTypeName,
			final NamedInputStream[] someNamedInputStreams,
			final String aPackageName, final String aFileExtension) {

		final String aDirectoryName = aPackageName.replace('.',
				util.io.Files.getSeparatorChar())
				+ util.io.Files.getSeparatorChar();

		// For debugging on MacOS X only!
		//	ProxyConsole.getInstance().errorOutput().println(
		//		util.io.Files.getSeparatorChar());
		//	ProxyConsole.getInstance().errorOutput().println(File.separatorChar);
		//	ProxyConsole.getInstance().errorOutput().print("aDirectoryName = ");
		//	ProxyConsole.getInstance().errorOutput().println(aDirectoryName);

		final List<ClassFile> aListOfClasses = new ArrayList<ClassFile>();
		for (int i = 0; i < someNamedInputStreams.length; i++) {
			// For debugging on MacOS X only!
			//	ProxyConsole
			//		.getInstance()
			//		.errorOutput()
			//		.print("someNamedInputStreams[i].getName() = ");
			//	ProxyConsole
			//		.getInstance()
			//		.errorOutput()
			//		.println(someNamedInputStreams[i].getName());

			if (someNamedInputStreams[i].getName().endsWith(aFileExtension)
					&& someNamedInputStreams[i].getName()
							.indexOf(aDirectoryName) > -1) {

				try {
					SubtypeLoader.loadSubtypeFromStream(aSuperTypeName,
							aListOfClasses, someNamedInputStreams[i]);
				}
				catch (final IOException e) {
					ProxyConsole.getInstance().errorOutput().print(e);
					ProxyConsole.getInstance().errorOutput()
							.print(": Exception while reading file: ");
					ProxyConsole.getInstance().errorOutput()
							.println(someNamedInputStreams[i]);
				}
			}
		}

		final ClassFile[] results = new ClassFile[aListOfClasses.size()];
		aListOfClasses.toArray(results);
		Arrays.sort(results, new ClassNameAlphabeticalComparator());
		return results;
	}

	private SubtypeLoader() {
	}
}
