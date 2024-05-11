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
package padl.kernel.impl;

import util.io.ProxyConsole;
import util.io.SubtypeLoader;
import util.repository.FileAccessException;
import util.repository.IFileRepository;
import util.repository.IRepository;
import util.repository.impl.FileRepositoryFactory;
import com.ibm.toad.cfparse.ClassFile;

public class ConstituentsRepository implements IRepository {
	private static ConstituentsRepository UniqueInstance;
	public static ConstituentsRepository getInstance() {
		if (ConstituentsRepository.UniqueInstance == null) {
			ConstituentsRepository.UniqueInstance = new ConstituentsRepository();
		}

		return ConstituentsRepository.UniqueInstance;
	}

	private ClassFile[] elements;
	private ClassFile[] entities;
	private ConstituentsRepository() {
	}
	public ClassFile[] getElements() {
		if (this.elements == null) {
			try {
				this.elements =
					SubtypeLoader.loadSubtypesFromStream(
						"padl.kernel.IElementMarker",
						this.getFileRepository().getFiles(),
						"padl.kernel.impl",
						".class");
			}
			catch (final FileAccessException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
		}
		return this.elements;
	}
	public ClassFile[] getEntities() {
		if (this.elements == null) {
			try {
				this.entities =
					SubtypeLoader.loadSubtypesFromStream(
						"padl.kernel.IEntityMarker",
						this.getFileRepository().getFiles(),
						"padl.kernel.impl",
						".class");
			}
			catch (final FileAccessException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
		}
		return this.entities;
	}
	private IFileRepository getFileRepository() {
		return FileRepositoryFactory.getInstance().getFileRepository(this);
	}
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("Type Repository:\n\tEntities\n");
		for (int x = 0; x < this.getEntities().length; x++) {
			buffer.append("\t\t");
			buffer.append(this.getEntities()[x].getName());
			buffer.append('\n');
		}
		buffer.append("\tElements\n");
		for (int x = 0; x < this.getElements().length; x++) {
			buffer.append("\t\t");
			buffer.append(this.getElements()[x].getName());
			buffer.append('\n');
		}
		return buffer.toString();
	}
}
