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
package ptidej.viewer.ui.panel;

import ptidej.viewer.utils.Resources;
import ptidej.viewer.utils.Controls;
import ptidej.viewer.widget.EmbeddedPanel;
import util.help.IHelpURL;

public class MetricalSolver4Panel extends EmbeddedPanel {
	private static final long serialVersionUID = 1L;

	public MetricalSolver4Panel() {
		super();

		this.addButton(
			Resources.METRICAL_PTIDEJ_SOLVER_4_FIND_SIMILAR_MICRO_ARCHITECTURE,
			MetricalSolver4Panel.class,
			Resources.PTIDEJ_METRICAL_SOLVER_4,
			false,
			false,
			Controls.getInstance().areDesignPatternsListening(),
			new IHelpURL() {
				public String getHelpURL() {
					return "http://www.ptidej.net/publications/documents/WCRE04.doc.pdf";
				}
			});
	}
}
