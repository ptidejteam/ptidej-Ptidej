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
package sad.designsmell.detection.repository;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;
import padl.kernel.IAbstractLevelModel;
import sad.kernel.ICodeSmell;
import sad.kernel.IDesignSmell;
import util.io.ProxyConsole;

/**
 * This class represents the detection of the antipatterns
 * 
 * @author Naouel Moha
 * @author Yann-Gael 
 * @version 2.0
 * @since 2006/02/04
 */

public abstract class AbstractDesignSmellDetection {
	private Set setOfDesignSmells;

	public abstract void detect(final IAbstractLevelModel anAbstractLevelModel);
	public Set getDesignSmells() {
		return this.setOfDesignSmells;
	}
	public String getHelpURL() {
		return "http://www.ptidej.net/publications/documents/TSE09.doc.pdf";
	}
	public abstract String getName();
	public void output(final PrintWriter aWriter) {
		try {
			if (this.setOfDesignSmells == null) {
				aWriter.println();
				aWriter
					.println("# Method performDetection() must be called before outputing results.");
				aWriter.println();
			}
			else {
				aWriter.println();
				aWriter.println("# Results of the detection ");
				aWriter.println();

				final Iterator iterator = this.setOfDesignSmells.iterator();
				int count = 0;
				while (iterator.hasNext()) {
					final IDesignSmell ant = (IDesignSmell) iterator.next();
					count++;
					aWriter.println("\n#" + " ------>" + ant.getName()
							+ " num: " + count);
					aWriter.println();

					aWriter.println(count + ".100.Name = " + ant.getName());
					final Iterator iter2 = ant.listOfCodeSmells().iterator();
					while (iter2.hasNext()) {
						final ICodeSmell codeSmell = (ICodeSmell) iter2.next();
						aWriter.println(codeSmell.toString(count));
					}
				}
				aWriter.println("\n\n#----> Total:" + count);
			}

			aWriter.close();
		}
		catch (final NumberFormatException e) {
			ProxyConsole
				.getInstance()
				.errorOutput()
				.println("Format error in input file: " + e);
		}
	}
	protected void setSetOfDesignSmells(final Set setOfAntiPatterns) {
		this.setOfDesignSmells = setOfAntiPatterns;
	}
}
