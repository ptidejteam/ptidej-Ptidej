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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import util.io.NamedInputStream;
import util.io.ProxyConsole;
import util.repository.IFileRepository;

/**
 * Peps's IFileRepository. Is used to find resources in Eclipse's jar files.
 * <br/> Should be discussed.
 * 
 * @author Yann-Gaël Guéhéneuc 
 * @author Nelson Cabral
 * @since 2008/01/01
 */
class EclipseBundleRepository implements IFileRepository {
	private final ClassLoader cl;

	/** The file streams. */
	private NamedInputStream[] fileStreams;

	/**
	 * Instantiates a new JarFileRepository from the given bundle name.
	 * 
	 * @param bundleName
	 *            the bundle name
	 */
	public EclipseBundleRepository(final Collection<?> bundleNames)
			throws Exception {
		this(EclipseBundleRepository.class.getClassLoader(), bundleNames);
	}

	public EclipseBundleRepository(
		final ClassLoader cl,
		final Collection<?> bundleNames) throws Exception {

		this.cl = cl;
		final Collection<NamedInputStream> listOfStreams = this.getStreams(bundleNames);
		this.fileStreams = new NamedInputStream[listOfStreams.size()];
		listOfStreams.toArray(this.fileStreams);
	}

	public NamedInputStream[] getFiles() {
		return this.fileStreams;
	}

	public String toString() {
		return this.fileStreams.length + " files in repository.";
	}

	private Collection<NamedInputStream> getStreams(final Collection<?> bundleNames)
			throws Exception {
		Collection<NamedInputStream> streams = new ArrayList<NamedInputStream>();

		try {
			final Iterator<?> iteratorOnBundleNames = bundleNames.iterator();
			while (iteratorOnBundleNames.hasNext()) {
				final String bundleName = (String) iteratorOnBundleNames.next();
				final Class<?> platformClass =
					this.cl.loadClass("org.eclipse.core.runtime.Platform");
				final Class<?> bundleClass =
					this.cl.loadClass("org.osgi.framework.Bundle");
				//	final Class fileLocatorClass =
				//		this.cl.loadClass("org.eclipse.core.runtime.FileLocator");
				final Class<?> abstractBundleClass =
					this.cl
						.loadClass("org.eclipse.osgi.framework.internal.core.AbstractBundle");
				final Class<?> bundleDataClass =
					this.cl
						.loadClass("org.eclipse.osgi.framework.adaptor.BundleData");

				final Method getBundle =
					platformClass.getDeclaredMethod(
						"getBundle",
						new Class[] { String.class });
				final Method asLocalURL =
					platformClass.getDeclaredMethod(
						"asLocalURL",
						new Class[] { URL.class });
				final Method getResource =
					bundleClass.getDeclaredMethod(
						"getResource",
						new Class[] { String.class });
				final Method getBundleData =
					abstractBundleClass.getDeclaredMethod(
						"getBundleData",
						new Class[0]);
				final Method getEntryPaths =
					bundleDataClass.getDeclaredMethod(
						"getEntryPaths",
						new Class[] { String.class });
				final Method getEntry =
					bundleDataClass.getDeclaredMethod(
						"getEntry",
						new Class[] { String.class });

				final Object bundle =
					getBundle
						.invoke(platformClass, new Object[] { bundleName });

				// Method toFileURL = fileLocatorClass.getDeclaredMethod(
				// "toFileURL", URL.class);
				if (bundle == null) {
					System.currentTimeMillis();
				}

				System.out.println("Loading: " + bundle);
				URL urlURL =
					(URL) getResource.invoke(bundle, new Object[] { "/bin" });
				System.out.println("Loading: " + urlURL);
				if (urlURL == null) {
					System.currentTimeMillis();
				}
				Object bundleData = getBundleData.invoke(bundle, new Object[0]);

				final Enumeration<?> entries =
					(Enumeration<?>) getEntryPaths.invoke(
						bundleData,
						new Object[] { "." });

				while (entries.hasMoreElements()) {
					String entry = (String) entries.nextElement();

					final URL entryURL =
						(URL) getEntry.invoke(
							bundleData,
							new Object[] { entry });
					final URL localURL =
						(URL) asLocalURL.invoke(
							platformClass,
							new Object[] { entryURL });

					System.out.println("Entry: " + entry);
					System.out.println("Elements: " + entryURL);
					System.out.println("Local: " + localURL);

					if (entry.equals("./bin/")) {
						String localFile = localURL.getFile();
						System.out.println(localFile);
						EclipseBundleRepository.injectStreams(new File(
							localFile), streams);
						System.out.println("current size: " + streams.size());
					}
				}
			}
		}
		catch (final Exception e) {
			System.err.println("Error");
			throw new Exception(
				"Eclipse project cannot be parsed correctly. Check configuration",
				e);
		}
		return streams;
	}
	private static final void injectStreams(
		final File theCurrentDirectory,
		final Collection<NamedInputStream> aListOfFiles) {

		final String[] files = theCurrentDirectory.list();
		if (files == null) {
			System.out.println("Cannot process : "
					+ theCurrentDirectory.getAbsolutePath());
			System.exit(1);
		}
		for (int i = 0; i < files.length; i++) {
			final File file = new File(theCurrentDirectory, files[i]);
			if (file.isFile()) {
				FileInputStream fileInputStream = null;
				try {
					fileInputStream = new FileInputStream(file);
					aListOfFiles.add(new NamedInputStream(file
						.getCanonicalPath(), fileInputStream));
				}
				catch (final FileNotFoundException fnfe) {
					fnfe.printStackTrace(ProxyConsole.getInstance().errorOutput());
				}
				catch (final IOException ioe) {
					ioe.printStackTrace(ProxyConsole.getInstance().errorOutput());
				}
				finally {
					if (fileInputStream != null) {
						try {
							fileInputStream.close();
						}
						catch (IOException ioe) {
							ProxyConsole.getInstance().errorOutput().println(
								"Warning: cannot close file!");
						}
					}
				}
			}
			else {
				injectStreams(file, aListOfFiles);
				System.out.println("Current size: " + aListOfFiles.size());
			}
		}
	}
}
