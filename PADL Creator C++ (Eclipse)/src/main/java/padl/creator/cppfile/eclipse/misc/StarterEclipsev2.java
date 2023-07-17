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
package padl.creator.cppfile.eclipse.misc;

import org.eclipse.core.runtime.adaptor.EclipseStarter;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import util.io.ProxyConsole;

public class StarterEclipsev2 {
	public static void main(final String[] args) {
		try {
			String theArgs[] =
				{
						"-application ",
						"PADL_Creator_Cpp_Eclipse.Launcher ",
						"-data ",
						"\"D:/Software/Ptidej 5 Workspace 3/PADL Creator C++ (Eclipse) Helper/Runtime Workspace\" ",
						"-configuration ",
						"\"file:D:/Software/Ptidej 5 Workspace 3/.metadata/.plugins/org.eclipse.pde.core/PADL Creator C++ (Eclipse)/\" ",
						"-dev ",
						"\"file:D:/Software/Ptidej 5 Workspace 3/.metadata/.plugins/org.eclipse.pde.core/PADL Creator C++ (Eclipse)/dev.properties\" ",
						"-os ", "win32 ", "-ws ", "win32 ", "-arch ", "x86 ",
						"-nl ", "en_CA ", "-consoleLog " };
			final BundleContext ctx = EclipseStarter.startup(theArgs, null);
			final Bundle b =
				ctx.installBundle("file://PADL Creator C++ (Eclipse)/");
			b.start();
			// ctx.getBundles();
			EclipseStarter.shutdown();
		}
		catch (final Exception e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
}
