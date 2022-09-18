/*
 * $Id: TreeDirLoader.java,v 1.1 2004/03/01 15:37:17 guehene Exp $
 *
 * Copyright (c) 2001-2002 Jean-Daniel Fekete, Mohammad Ghoniem and
 * Ecole des Mines de Nantes.  All rights reserved.
 *
 * This software is proprietary information of Jean-Daniel Fekete and
 * Ecole des Mines de Nantes.  You shall use it only in accordance
 * with the terms of the license agreement you accepted when
 * downloading this software.  The license is available in the file
 * licence.txt and at the following URL:
 * http://www.emn.fr/fekete/oadymppac/licence.html
 */

package fr.emn.oadymppac.tree;

import java.io.File;

public class TreeDirLoader {
	AttributedTree tree;

	StringAttributeVector name;
	FloatAttributeVector mod;
	FloatAttributeVector len;

	public TreeDirLoader(final AttributedTree tree) {
		this.tree = tree;
		this.name = StringAttributeVector.find("name", tree);
		this.mod = FloatAttributeVector.find("mtime", tree);
		this.len = FloatAttributeVector.find("length", tree);
	}
	public int add(final File file, final int parent) {
		final int node = this.tree.add(parent);
		int cnt = 1;

		this.len.setAttribute(node, file.length());
		this.mod.setAttribute(node, file.lastModified());
		if (file.isDirectory()) {
			this.name.setAttribute(node, file.getName() + "/");
			final String[] l = file.list();
			for (int i = 0; i < l.length; i++) {
				final String fname = l[i];
				if (fname.startsWith(".")) {
					continue;
				}
				cnt += this.add(new File(file, fname), node);
			}
		}
		else {
			this.name.setAttribute(node, file.getName());
		}
		return cnt;
	}

	public int add(final String filename, final int parent) {
		return this.add(new File(filename), parent);
	}
}
