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
package util.repository.impl;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import util.io.ProxyConsole;
import util.repository.FileAccessException;
import util.repository.IFileRepository;
import util.repository.IRepository;

public class FileRepositoryFactory {
	private static final IRepository DUMMY_REPOSITORY_FOR_JAR_FILE_REPOSITORIES =
		new IRepository() {};
	private static int ECLIPSE_BUNDLE_REPOSITORY = 1;
	private static int FILE_FOLDER_REPOSITORY = 0;
	private static int FLAT_JAR_FILE_REPOSITORY = 3;
	private static int JAR_IN_JAR_FILE_REPOSITORY = 2;
	private static File RunningPath;
	private static FileRepositoryFactory UniqueInstance;
	private static int UNKNOWN = -1;
	public static FileRepositoryFactory getInstance() {
		if (FileRepositoryFactory.UniqueInstance == null) {
			FileRepositoryFactory.UniqueInstance = new FileRepositoryFactory();
		}
		return FileRepositoryFactory.UniqueInstance;
	}
	public static File getRunningPath() {
		if (FileRepositoryFactory.RunningPath == null) {
			String path;
			// Yann 2013/08/14: Test of the JAR-in-JAR version of Ptidej
			// If this system property is set, then I use it and assume
			// that it points to the JAR-in-JAR :-)
			final String pathToJarInJar = System.getenv("ptidej.jarinjar.path");
			if (pathToJarInJar == null) {
				// Yann 2013/07/06: Crapy piece of code because of Sun!
				// See http://stackoverflow.com/questions/4114702/how-to-get-name-of-jar-which-is-a-desktop-application-run-from
				path =
					FileRepositoryFactory.class
						.getProtectionDomain()
						.getCodeSource()
						.getLocation()
						.getPath();
				final File tentativePath = new File(path);
				if (tentativePath.isDirectory()) {
					//	final String commandArgs =
					//		System.getProperty("sun.java.command");
					// Yann: I set the path plus add the arguments if any.
					// path += commandArgs;
				}
				else {
					try {
						path = URLDecoder.decode(path, "UTF-8");
					}
					catch (final UnsupportedEncodingException e) {
						e.printStackTrace(ProxyConsole
							.getInstance()
							.errorOutput());
					}
				}
			}
			else {
				path = pathToJarInJar;
			}

			ProxyConsole
				.getInstance()
				.warningOutput()
				.print(FileRepositoryFactory.class.getName());
			ProxyConsole
				.getInstance()
				.warningOutput()
				.print(" has for running path: ");

			ProxyConsole.getInstance().warningOutput().println(path);
			FileRepositoryFactory.RunningPath = new File(path);
		}
		return FileRepositoryFactory.RunningPath;
	}

	private Map<IRepository, IFileRepository> repositories =
		new HashMap<IRepository, IFileRepository>();
	private int type = FileRepositoryFactory.UNKNOWN;
	public FileRepositoryFactory() {
		// Yann 2013/07/02: Cases
		// I manage three cases:
		// - the simple, regular case when running from class path;
		// - the case when running from a JAR file;
		// - the case when running from as an Eclipse plug-in.
		final ClassLoader cl = FileRepositoryFactory.class.getClassLoader();
		boolean tryFailed = true;

		if (tryFailed) {
			try {
				this.tryWebApp(cl);
				tryFailed = false;
			}
			catch (final Exception e) {
				tryFailed = true;
			}
		}

		if (tryFailed) {
			try {
				this.tryEclipsePlugin(cl);
				tryFailed = false;
			}
			catch (final Exception e) {
				tryFailed = true;
			}
		}

		if (tryFailed) {
			try {
				this.tryJarsInJar(cl);
				tryFailed = false;
			}
			catch (final Exception e) {
				tryFailed = true;
			}
		}

		if (tryFailed) {
			try {
				this.tryFoldersAndFiles(cl);
				tryFailed = false;
			}
			catch (final Exception e) {
				tryFailed = true;
			}
		}

		if (tryFailed) {
			try {
				this.tryFlatJar(cl);
				tryFailed = false;
			}
			catch (final Exception e) {
				tryFailed = true;
			}
		}

		if (tryFailed) {
			throw new RuntimeException(new FileAccessException(
				"Cannot find adequate FileRepository for this running context"));
		}
	}
	public IFileRepository getFileRepository(final IRepository aRepository) {
		if (!this.repositories.containsKey(aRepository)
				&& !this.repositories
					.containsKey(FileRepositoryFactory.DUMMY_REPOSITORY_FOR_JAR_FILE_REPOSITORIES)) {

			if (this.type == FileRepositoryFactory.ECLIPSE_BUNDLE_REPOSITORY) {
				// TODO Implement!
				//	final EclipseBundleRepository eclipseRepository =
				//		new EclipseBundleRepository(cl, resourceNames);
				final EclipseBundleRepository eclipseRepository = null;
				this.repositories.put(aRepository, eclipseRepository);
			}
			else if (this.type == FileRepositoryFactory.FILE_FOLDER_REPOSITORY) {
				this.repositories.put(aRepository, new FileFolderRepository(
					aRepository));
			}
			else if (this.type == FileRepositoryFactory.FLAT_JAR_FILE_REPOSITORY) {
				this.repositories
					.put(
						FileRepositoryFactory.DUMMY_REPOSITORY_FOR_JAR_FILE_REPOSITORIES,
						JarFileRepository.getInstance(FileRepositoryFactory
							.getRunningPath()
							.getAbsolutePath()));
			}
			else if (this.type == FileRepositoryFactory.JAR_IN_JAR_FILE_REPOSITORY) {
				this.repositories
					.put(
						FileRepositoryFactory.DUMMY_REPOSITORY_FOR_JAR_FILE_REPOSITORIES,
						JarFileRepository.getInstance(FileRepositoryFactory
							.getRunningPath()
							.getAbsolutePath()));
			}
			else {
				throw new RuntimeException(
					new FileAccessException(
						"FileRepositoryFactory cannot identify the type of repository!"));
			}
		}

		if (this.repositories
			.containsKey(FileRepositoryFactory.DUMMY_REPOSITORY_FOR_JAR_FILE_REPOSITORIES)) {

			return (IFileRepository) this.repositories
				.get(FileRepositoryFactory.DUMMY_REPOSITORY_FOR_JAR_FILE_REPOSITORIES);
		}
		else {
			return (IFileRepository) this.repositories.get(aRepository);
		}
	}
	private void tryEclipsePlugin(final ClassLoader cl)
			throws ClassNotFoundException, NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		// Check if run within environments like eclipse
		final Class<?> workspaceClass =
			cl.loadClass("org.eclipse.core.resources.IWorkspace");
		final Class<?> resourcesClass =
			cl.loadClass("org.eclipse.core.resources.ResourcesPlugin");
		if (workspaceClass != null || resourcesClass != null) {
			// In eclipse
			final Method getWorkspace =
				resourcesClass.getDeclaredMethod("getWorkspace", new Class[0]);
			final Object o =
				getWorkspace.invoke(resourcesClass, new Object[] {});
			if (o != null) {
				// There is a workspace, we should use eclipse bundles
				ProxyConsole
					.getInstance()
					.warningOutput()
					.print(this.getClass().getName());
				ProxyConsole
					.getInstance()
					.warningOutput()
					.print(" is creating an EclipseBundleRepository for: ");
				ProxyConsole
					.getInstance()
					.warningOutput()
					.println(EclipseBundleRepository.class.getName());

				this.type = FileRepositoryFactory.ECLIPSE_BUNDLE_REPOSITORY;
			}
			else {
				throw new RuntimeException(
					"FileRepositoryBuilder cannot invoke ResourcesPlugin");
			}
		}
		else {
			throw new RuntimeException(
				"FileRepositoryBuilder cannot find either IWorkspace or ResourcesPlugin");
		}
	}
	private void tryFlatJar(final ClassLoader cl) {
		ProxyConsole
			.getInstance()
			.warningOutput()
			.print(this.getClass().getName());
		ProxyConsole
			.getInstance()
			.warningOutput()
			.print(" is creating a JarFileRepository for: ");
		ProxyConsole
			.getInstance()
			.warningOutput()
			.println(JarFileRepository.class.getName());

		this.type = FileRepositoryFactory.FLAT_JAR_FILE_REPOSITORY;
	}
	private void tryFoldersAndFiles(final ClassLoader cl) throws IOException {
		final File path = FileRepositoryFactory.getRunningPath();
		if (path.isDirectory()) {
			ProxyConsole
				.getInstance()
				.warningOutput()
				.print(this.getClass().getName());
			ProxyConsole
				.getInstance()
				.warningOutput()
				.print(" is creating a ClassFileRepository for: ");
			ProxyConsole
				.getInstance()
				.warningOutput()
				.println(FileFolderRepository.class.getName());

			this.type = FileRepositoryFactory.FILE_FOLDER_REPOSITORY;
		}
		else {
			throw new IOException();
		}
	}
	private void tryJarsInJar(final ClassLoader cl)
			throws ClassNotFoundException {

		// Warning! Eclipse of course does funny things...
		final Class<?> jarInJarLoader =
			cl
				.loadClass("org.eclipse.jdt.internal.jarinjarloader.JarRsrcLoader");

		ProxyConsole
			.getInstance()
			.warningOutput()
			.print(this.getClass().getName());
		ProxyConsole
			.getInstance()
			.warningOutput()
			.print(" is creating a JarFileRepository for: ");
		ProxyConsole
			.getInstance()
			.warningOutput()
			.println(jarInJarLoader.getName());

		this.type = FileRepositoryFactory.JAR_IN_JAR_FILE_REPOSITORY;
	}
	private void tryWebApp(final ClassLoader cl) throws NoSuchMethodException {
		throw new NoSuchMethodException();
	}
}
