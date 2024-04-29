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
/*
 * (c) Copyright 2000-2003 Yann-Gaël Guéhéneuc ,
 * Ecole des Mines de Nantes and Object Technology International, Inc.
 * 
 * Use and copying of this software and preparation of derivative works
 * based upon this software are permitted. Any copy of this software or
 * of any derivative work must include the above copyright notice of
 * Yann-Gaël Guéhéneuc , this paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS
 * ALL WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE, AND NOT WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN, ANY
 * LIABILITY FOR DAMAGES RESULTING FROM THE SOFTWARE OR ITS USE IS
 * EXPRESSLY DISCLAIMED, WHETHER ARISING IN CONTRACT, TORT (INCLUDING
 * NEGLIGENCE) OR STRICT LIABILITY, EVEN IF YANN-GAEL GUEHENEUC IS ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
package sad.codesmell.detection;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import com.ibm.toad.cfparse.ClassFile;

import util.io.ProxyConsole;
import util.io.SubtypeLoader;
import util.repository.FileAccessException;
import util.repository.IRepository;
import util.repository.impl.FileRepositoryFactory;

public class CodeSmellDetectionsRepository implements IRepository {
	private static CodeSmellDetectionsRepository UniqueInstance;

	public static CodeSmellDetectionsRepository getInstance() {
		// TODO: Do not use Singleton pattern to force reload
		if (CodeSmellDetectionsRepository.UniqueInstance == null) {
			CodeSmellDetectionsRepository.UniqueInstance = new CodeSmellDetectionsRepository();
		}
		return CodeSmellDetectionsRepository.UniqueInstance;
	}

	private ICodeSmellDetection[] codeSmells;

	private CodeSmellDetectionsRepository() {
		try {
			final ClassFile[] classFiles = SubtypeLoader.loadSubtypesFromStream(
					"sad.codesmell.detection.ICodeSmellDetection",
					FileRepositoryFactory.getInstance().getFileRepository(this)
							.getFiles(),
					"sad.codesmell.detection.repository", ".class");

			// For debugging on MacOS X only!
			//	ProxyConsole
			//		.getInstance()
			//		.errorOutput()
			//		.print("classFiles.length = ");
			//	ProxyConsole.getInstance().errorOutput().println(classFiles.length);

			final Set codeSmells = new TreeSet(new Comparator() {
				public int compare(final Object o1, final Object o2) {
					return ((ICodeSmellDetection) o1).getName()
							.compareTo(((ICodeSmellDetection) o2).getName());
				}
			});
			for (int i = 0; i < classFiles.length; i++) {
				try {
					// Yann 2007/03/18: Member classes...
					// I do not care about member classes of
					// detection algorithms.
					if (classFiles[i].getName().indexOf('$') == -1) {
						final Class codeSmellClass = Class
								.forName(classFiles[i].getName());
						final ICodeSmellDetection detection = (ICodeSmellDetection) codeSmellClass
								.newInstance();
						codeSmells.add(detection);
					}
				}
				catch (final Throwable t) {
					//	System.err.println(MultilingualManager
					//		.getString(
					//			"Err_LOAD_CODE_SMELL",
					//			CodeSmellRepository.class,
					//			new Object[] { classFiles[i].getName(),
					//					t.getMessage() }));
					t.printStackTrace(ProxyConsole.getInstance().errorOutput());
				}
			}

			this.codeSmells = new ICodeSmellDetection[codeSmells.size()];
			codeSmells.toArray(this.codeSmells);
		}
		catch (final FileAccessException fae) {
			fae.printStackTrace(ProxyConsole.getInstance().errorOutput());
			this.codeSmells = new ICodeSmellDetection[0];
		}
	}

	public ICodeSmellDetection getCodeSmellDetection(
			String aCodeSmellDetectionName) {
		for (int i = 0; i < this.codeSmells.length; i++) {
			final ICodeSmellDetection codeSmellDetection = this.codeSmells[i];
			final String codeSmellDetectionName = codeSmellDetection.getName();
			if (codeSmellDetectionName.equals(aCodeSmellDetectionName)) {
				return codeSmellDetection;
			}
		}

		return null;
	}

	public ICodeSmellDetection[] getCodeSmellDetections() {
		return this.codeSmells;
	}

	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("Code smell repository:\n");

		for (int i = 0; i < this.codeSmells.length; i++) {
			final String name = this.codeSmells[i].getName();
			buffer.append('\t');
			buffer.append(name);
			buffer.append('\n');
		}
		return buffer.toString();
	}
}
