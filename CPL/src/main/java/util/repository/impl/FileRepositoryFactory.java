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
	private static FileRepositoryFactory UniqueInstance;

	public static FileRepositoryFactory getInstance() {
		if (FileRepositoryFactory.UniqueInstance == null) {
			FileRepositoryFactory.UniqueInstance = new FileRepositoryFactory();
		}
		return FileRepositoryFactory.UniqueInstance;
	}

	private static File getRunningPath(final IRepository aRepository) {
		// Yann 2013/08/14: Test of the JAR-in-JAR version of Ptidej
		// If this system property is set, then I use it and assume
		// that it points to the JAR-in-JAR :-)
		final String pathToJarInJar = System.getenv("ptidej.jarinjar.path");

		String path;
		if (pathToJarInJar == null) {
			// Yann 2013/07/06: Crapy piece of code because of Sun!
			// See http://stackoverflow.com/questions/4114702/how-to-get-name-of-jar-which-is-a-desktop-application-run-from
			path = aRepository.getClass().getProtectionDomain().getCodeSource()
					.getLocation().getPath();
			final File tentativePath = new File(path);
			if (tentativePath.isDirectory()) {
			}
			else {
				try {
					path = URLDecoder.decode(path, "UTF-8").substring(1);
				}
				catch (final UnsupportedEncodingException e) {
					e.printStackTrace(ProxyConsole.getInstance().errorOutput());
				}
			}
		}
		else {
			path = pathToJarInJar;
		}

		ProxyConsole.getInstance().warningOutput()
				.print(FileRepositoryFactory.class.getName());
		ProxyConsole.getInstance().warningOutput()
				.print(" has for running path: ");
		ProxyConsole.getInstance().warningOutput().println(path);

		return new File(path);
	}

	private Map<IRepository, IFileRepository> repositories = new HashMap<IRepository, IFileRepository>();

	public FileRepositoryFactory() {
	}

	public IFileRepository getFileRepository(final IRepository aRepository) {
		if (!this.repositories.containsKey(aRepository)) {
			if (this.isEclipsePlugin(aRepository)) {
				// TODO Implement!
				//	final EclipseBundleRepository eclipseRepository =
				//		new EclipseBundleRepository(cl, resourceNames);
				final EclipseBundleRepository eclipseRepository = null;
				this.repositories.put(aRepository, eclipseRepository);
			}
			else if (this.isFoldersAndFiles(aRepository)) {
				this.repositories.put(aRepository,
						new FileFolderRepository(aRepository));
			}
			else if (this.isFlatJar(aRepository)) {
				this.repositories.put(aRepository,
						JarFileRepository.getInstance(FileRepositoryFactory
								.getRunningPath(aRepository)
								.getAbsolutePath()));
			}
			else if (this.isJarsInJar(aRepository)) {
				this.repositories.put(aRepository,
						JarFileRepository.getInstance(FileRepositoryFactory
								.getRunningPath(aRepository)
								.getAbsolutePath()));
			}
			else {
				throw new RuntimeException(new FileAccessException(
						"FileRepositoryFactory cannot identify the type of repository!"));
			}
		}

		return this.repositories.get(aRepository);
	}

	private boolean isEclipsePlugin(final IRepository aRepository) {
		// Check if run within environments like eclipse
		final ClassLoader cl = aRepository.getClass().getClassLoader();
		try {
			final Class<?> workspaceClass = cl
					.loadClass("org.eclipse.core.resources.IWorkspace");
			final Class<?> resourcesClass = cl
					.loadClass("org.eclipse.core.resources.ResourcesPlugin");
			if (workspaceClass != null || resourcesClass != null) {
				// In eclipse
				final Method getWorkspace = resourcesClass
						.getDeclaredMethod("getWorkspace", new Class[0]);
				final Object o = getWorkspace.invoke(resourcesClass,
						new Object[] {});
				if (o != null) {
					// There is a workspace, we should use eclipse bundles
					ProxyConsole.getInstance().warningOutput()
							.print(this.getClass().getName());
					ProxyConsole.getInstance().warningOutput().print(
							" is creating an EclipseBundleRepository for: ");
					ProxyConsole.getInstance().warningOutput()
							.println(EclipseBundleRepository.class.getName());

					return true;
				}
			}
		}
		catch (ClassNotFoundException | NoSuchMethodException
				| SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			// ProxyConsole.getInstance().errorOutput().println("Class org.eclipse.core.resources.IWorkspace not found");
		}

		return false;
	}

	private boolean isFlatJar(final IRepository aRepository) {
		ProxyConsole.getInstance().warningOutput()
				.print(this.getClass().getName());
		ProxyConsole.getInstance().warningOutput()
				.print(" is creating a JarFileRepository for: ");
		ProxyConsole.getInstance().warningOutput()
				.println(JarFileRepository.class.getName());

		return true;
	}

	private boolean isFoldersAndFiles(final IRepository aRepository) {
		final File path = FileRepositoryFactory.getRunningPath(aRepository);
		if (path.isDirectory()) {
			ProxyConsole.getInstance().warningOutput()
					.print(this.getClass().getName());
			ProxyConsole.getInstance().warningOutput()
					.print(" is creating a ClassFileRepository for: ");
			ProxyConsole.getInstance().warningOutput()
					.println(FileFolderRepository.class.getName());

			return true;
		}

		return false;
	}

	private boolean isJarsInJar(final IRepository aRepository) {
		// Warning! Eclipse of course does funny things...
		try {
			final Class<?> jarInJarLoader = aRepository.getClass()
					.getClassLoader().loadClass(
							"org.eclipse.jdt.internal.jarinjarloader.JarRsrcLoader");

			if (jarInJarLoader != null) {
				ProxyConsole.getInstance().warningOutput()
						.print(this.getClass().getName());
				ProxyConsole.getInstance().warningOutput()
						.print(" is creating a JarFileRepository for: ");
				ProxyConsole.getInstance().warningOutput()
						.println(jarInJarLoader.getName());

				return true;
			}
		}
		catch (final ClassNotFoundException e) {
			ProxyConsole.getInstance().errorOutput().println(
					"Class org.eclipse.jdt.internal.jarinjarloader.JarRsrcLoader not found");
		}

		return false;
	}
}
