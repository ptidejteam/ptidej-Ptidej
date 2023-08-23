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
package sad.detection.generators;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

public class SmellDetectionCaller {
	public static void main(final String[] args) {
		//	final SmellDetectionCaller caller = new SmellDetectionCaller();
		//	caller.analyseCodeLevelModelFromEclipse(
		//		"C:/Program Files (x86)/Eclipse 3.5/",
		//		"rsc/Smells/");
		//	caller.analyseCodeLevelModelFromEclipse(
		//		"../../P-MARt Workspace/eclipse-SDK-2.1-win32/",
		//		"rsc/Smells/");

		//	caller.analyseCodeLevelModelFromJavaClassFiles(
		//		"../../P-MARt Workspace/SIP Communicator v1.0-draft/bin/",
		//		"SIP-communicator-1-0-draft",
		//		"rsc/Smells/");
		//	caller.analyseCodeLevelModelFromJavaClassFiles(
		//		"../../P-MARt Workspace/Pooka v2.0/bin/",
		//		"Pooka v2.0",
		//		"rsc/Smells/");

		//	final SmellDetectionCaller sdc = new SmellDetectionCaller();
		//
		//	final String path = "../../P-MARt Workspace/";
		//	final String[] folders = new File(path).list(new FilenameFilter() {
		//		public boolean accept(final File dir, final String name) {
		//			return name.toLowerCase().startsWith("argouml-")
		//					&& !(name.indexOf("0.15.1") > -1
		//							|| name.indexOf("0.15.4") > -1
		//							|| name.indexOf("0.19.1") > -1
		//							|| name.indexOf("0.19.2") > -1
		//							|| name.indexOf("0.23.1") > -1
		//							|| name.indexOf("0.25.2") > -1
		//							|| name.indexOf("0.26.2") > -1 || name
		//						.indexOf("0.27.1") > -1);
		//		}
		//	});
		//	Arrays.sort(folders);
		//	for (int i = 0; i < folders.length; i++) {
		//		final String argoUMLName = folders[i];
		//		final String argoUMLPath = path + argoUMLName + '/';
		//		final String[] jarFiles =
		//			new File(argoUMLPath).list(new FilenameFilter() {
		//				public boolean accept(final File dir, final String name) {
		//					return (name.toLowerCase().startsWith("argouml") || name
		//						.toLowerCase()
		//						.startsWith("nsuml"))
		//							&& name.endsWith(".jar");
		//				}
		//			});
		//
		//		if (jarFiles != null) {
		//			for (int j = 0; j < jarFiles.length; j++) {
		//				jarFiles[j] = argoUMLPath + jarFiles[j];
		//			}
		//			sdc
		//				.analyseCodeLevelModelFromJARs(
		//					jarFiles,
		//					argoUMLName,
		//					"rsc/");
		//		}
		//		else {
		//			System.err.print(argoUMLPath);
		//			System.err.println(" is not a path.");
		//		}
		//	}

		//	final SmellDetectionCaller sdc = new SmellDetectionCaller();
		//
		//	final String path = "../../P-MARt Workspace/";
		//	final String[] folders = new File(path).list(new FilenameFilter() {
		//		public boolean accept(final File dir, final String name) {
		//			return name.toLowerCase().startsWith("xerces v");
		//		}
		//	});
		//	Arrays.sort(folders);
		//	for (int i = 0; i < folders.length; i++) {
		//		final String name = folders[i];
		//		final String directory = path + name + "/bin/";
		//		sdc
		//			.analyseCodeLevelModelFromJavaClassFiles(
		//				directory,
		//				name,
		//				"rsc/");
		//	}

		final String path = "../../P-MARt Workspace/";
		final String[] folders = new File(path).list(new FilenameFilter() {
			public boolean accept(final File dir, final String name) {
				return name.toLowerCase().startsWith("xerces v");
			}
		});
		Arrays.sort(folders);
		for (int i = 0; i < folders.length; i++) {
			final String name = folders[i];
			final String directory = path + name + "/bin/";
			SmellDetectionHelper.analyseCodeLevelModelFromJavaClassFiles(
				directory,
				name,
				"rsc/");
		}
	}
}
