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
package padl.serialiser.dummy;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class Util {
	public static final String DB4OFILENAME = "rsc/Test";

	public static void listResult(ObjectSet result) {
		System.out.println(result.size());
		while (result.hasNext()) {
			System.out.println(result.next());
		}
	}

	public static void listResult(java.util.List result) {
		System.out.println(result.size());
		for (int x = 0; x < result.size(); x++)
			System.out.println(result.get(x));
	}

	public static void listRefreshedResult(
		ObjectContainer container,
		ObjectSet result,
		int depth) {
		System.out.println(result.size());
		while (result.hasNext()) {
			Object obj = result.next();
			container.ext().refresh(obj, depth);
			System.out.println(obj);
		}
	}

	public static void retrieveAll(ObjectContainer db) {
		ObjectSet result = db.queryByExample(new Object());
		listResult(result);
	}

	public static void deleteAll(ObjectContainer db) {
		ObjectSet result = db.queryByExample(new Object());
		while (result.hasNext()) {
			db.delete(result.next());
		}
	}
}
