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
package sad.designsmell.detection;

import java.io.PrintWriter;
import java.util.Set;
import padl.kernel.IAbstractLevelModel;
import util.help.IHelpURL;

public interface IDesignSmellDetection extends IHelpURL {
	String getName();
	String getRuleCardFile();
	// TODO: We may have a vocabulary problem: 
	// Do we detect antipatterns or rather micro-architectures 
	// similar to some anti-patters? Similarly to what happens 
	// with design patterns...
	Set getDesignSmells();
	void output(final PrintWriter aWriter);
	void detect(final IAbstractLevelModel anAbstractLevelModel);
}
