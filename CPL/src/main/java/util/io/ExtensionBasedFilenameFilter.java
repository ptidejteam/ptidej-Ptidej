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
import java.io.FilenameFilter;

/**
 * @version	0.1
 * @author 	Yann-Gaël Guéhéneuc
 * @since		2002/10/08
 */
public final class ExtensionBasedFilenameFilter implements FilenameFilter {
	private final String extension;

	public ExtensionBasedFilenameFilter(final String extension) {
		this.extension = extension;
	}
	public boolean accept(final File dir, final String name) {
		if (name.endsWith(this.extension)) {
			return true;
		}

		return false;
	}
}
