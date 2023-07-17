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

public class AntiPatternActionPanel extends EmbeddedPanel {
	private static final long serialVersionUID = 1L;

	public AntiPatternActionPanel() {
		super();

		this.addButton(
			Resources.NEW_RULECARD,
			AntiPatternActionPanel.class,
			Resources.DESIGN_SMELLS,
			Controls.getInstance().canModifyRuleCards(),
			false,
			false);

		this.addButton(
			Resources.DELETE_RULECARD,
			AntiPatternActionPanel.class,
			Resources.DESIGN_SMELLS,
			Controls.getInstance().canModifyRuleCards(),
			false,
			false);

		this.addButton(
			Resources.DETECT_DESIGN_SMELLS,
			AntiPatternActionPanel.class,
			Resources.DESIGN_SMELLS,
			false,
			false,
			Controls.getInstance().areAntiPatternsListening(),
			new IHelpURL() {
				public String getHelpURL() {
					return "http://www.ptidej.net/publications/documents/TSE09.doc.pdf";
				}
			});
	}
}
