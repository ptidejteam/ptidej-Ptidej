/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Contributors:
 *     Yann-Gaël Guéhéneuc  and others, see in file; API and its implementation
 ******************************************************************************/
package ptidej.viewer.extension.repository;

import java.util.HashMap;
import mendel.IRepository;

public class MendelRepositories {
	private static HashMap repositories = new HashMap();
	public static IRepository getProjectRepository(String projectName) {
		return (IRepository) repositories.get(projectName);
	}
	public static void setProjectRepository(
		String projectName,
		IRepository repository) {
		repositories.put(projectName, repository);
	}
}
