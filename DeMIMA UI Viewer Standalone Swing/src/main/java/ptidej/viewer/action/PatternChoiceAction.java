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
package ptidej.viewer.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import padl.motif.DesignMotifsRepository;
import padl.motif.IDesignMotifModel;
import ptidej.viewer.ui.DesktopPane;

public class PatternChoiceAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private static PatternChoiceAction UniqueInstance;
	public static PatternChoiceAction getInstance() {
		if (PatternChoiceAction.UniqueInstance == null) {
			PatternChoiceAction.UniqueInstance = new PatternChoiceAction();
		}
		return PatternChoiceAction.UniqueInstance;
	}
	private PatternChoiceAction() {
	}
	public void actionPerformed(final ActionEvent e) {
		final String patternName = e.getActionCommand();
		DesktopPane.getInstance().setPatternName(patternName.toCharArray());

		final DesignMotifsRepository patternRepository =
			DesignMotifsRepository.getInstance();
		final IDesignMotifModel[] patterns =
			patternRepository.getDesignMotifs();
		boolean found = false;
		for (int i = 0; i < patterns.length && !found; i++) {
			final IDesignMotifModel model = patterns[i];
			if (model.getName().equals(patternName)) {
				DesktopPane.getInstance().setPattern(model);
				found = true;
			}
		}
	}
}
