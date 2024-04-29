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
package sad.designsmell.detection;

import java.security.AccessControlException;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import com.ibm.toad.cfparse.ClassFile;

import util.io.ProxyConsole;
import util.io.SubtypeLoader;
import util.repository.FileAccessException;
import util.repository.IRepository;
import util.repository.impl.FileRepositoryFactory;

public class DesignSmellDetectionsRepository implements IRepository {
	private static DesignSmellDetectionsRepository UniqueInstance;

	public static DesignSmellDetectionsRepository getInstance() {
		// TODO: Do not use Singleton pattern to force reload
		//if (AntipatternRepository.UniqueInstance == null) {
		DesignSmellDetectionsRepository.UniqueInstance = new DesignSmellDetectionsRepository();
		//}
		return DesignSmellDetectionsRepository.UniqueInstance;
	}

	private IDesignSmellDetection[] designSmells;

	private DesignSmellDetectionsRepository() {
		try {
			final ClassFile[] classFiles = SubtypeLoader.loadSubtypesFromStream(
					"sad.designsmell.detection.IDesignSmellDetection",
					FileRepositoryFactory.getInstance().getFileRepository(this)
							.getFiles(),
					"sad.designsmell.detection.repository", ".class");

			final Set codeSmells = new TreeSet(new Comparator() {
				public int compare(final Object o1, final Object o2) {
					return ((IDesignSmellDetection) o1).getName()
							.compareTo(((IDesignSmellDetection) o2).getName());
				}
			});
			for (int i = 0; i < classFiles.length; i++) {
				try {
					// Yann 2007/03/18: Member classes...
					// I do not care about membre classes of
					// detection algorithms.
					if (classFiles[i].getName().indexOf('$') == -1) {
						final Class antiPatternClass = Class
								.forName(classFiles[i].getName());
						final IDesignSmellDetection detection = (IDesignSmellDetection) antiPatternClass
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

			this.designSmells = new IDesignSmellDetection[codeSmells.size()];
			codeSmells.toArray(this.designSmells);
		}
		catch (final AccessControlException ace) {
			this.designSmells = new IDesignSmellDetection[0];
		}
		catch (final FileAccessException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			this.designSmells = new IDesignSmellDetection[0];
		}
	}

	public IDesignSmellDetection getDesignSmellDetection(
			String aDesignSmellDetectionName) {
		for (int i = 0; i < this.designSmells.length; i++) {
			final IDesignSmellDetection designSmellDetection = this.designSmells[i];
			final String designSmellDetectionName = designSmellDetection
					.getName();
			if (designSmellDetectionName.equals(aDesignSmellDetectionName)) {
				return designSmellDetection;
			}
		}

		return null;
	}

	public IDesignSmellDetection[] getDesignSmellDetections() {
		return this.designSmells;
	}

	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("Design smell repository:\n");

		for (int i = 0; i < this.designSmells.length; i++) {
			final String name = this.designSmells[i].getName();
			buffer.append('\t');
			buffer.append(name);
			buffer.append('\n');
		}
		return buffer.toString();
	}
}
