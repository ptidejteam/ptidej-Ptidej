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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Set;
import ptidej.viewer.action.AntiPatternChoiceAction;
import ptidej.viewer.ui.DesktopPane;
import ptidej.viewer.ui.rulecard.IRuleCardListener;
import ptidej.viewer.ui.rulecard.RuleCardEvent;
import ptidej.viewer.utils.Resources;
import ptidej.viewer.utils.Controls;
import ptidej.viewer.widget.Button;
import ptidej.viewer.widget.EmbeddedPanel;
import sad.designsmell.detection.DesignSmellDetectionsRepository;
import sad.designsmell.detection.IDesignSmellDetection;

public class DesignSmellChoicePanel extends EmbeddedPanel {
	private static final long serialVersionUID = 1L;
	public DesignSmellChoicePanel() {
		super();

		this.loadDesignDefectList();
		DesktopPane.getInstance().addRuleCardListener(new IRuleCardListener() {
			public void ruleCardAvailable(final RuleCardEvent aRuleCardEvent) {
			}
			public void ruleCardChanged(final RuleCardEvent aRuleCardEvent) {
				this.update();
			}
			public void ruleCardUnavailable() {
				this.update();
			}
			private void update() {
				DesignSmellChoicePanel.this.removeAll();
				DesignSmellChoicePanel.this.loadDesignDefectList();

				// TODO Not updated correctly ????
				DesignSmellChoicePanel.this.validate();
				DesignSmellChoicePanel.this.repaint();
				DesignSmellChoicePanel.super.validate();
				DesignSmellChoicePanel.super.repaint();
			}
		});
	}
	private void loadDesignDefectList() {
		final DesignSmellDetectionsRepository designSmellDetectionsRepository =
			DesignSmellDetectionsRepository.getInstance();

		final IDesignSmellDetection[] designSmellDetections =
			designSmellDetectionsRepository.getDesignSmellDetections();
		for (int i = 0; i < designSmellDetections.length; i++) {
			final IDesignSmellDetection designSmellDetection =
				designSmellDetections[i];
			final String defectName = designSmellDetection.getName();

			// Create edit button
			final Button editButton = new Button("Edit");
			editButton.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					DesktopPane.getInstance().createRuleCardWindow(
						new File(designSmellDetection.getRuleCardFile()),
						defectName);
				}
			});
			editButton.setEnabled(Controls.getInstance().canModifyRuleCards());

			final Set designDefects =
				DesktopPane.getInstance().getDesignDefects();

			final boolean selected = designDefects.contains(defectName);
			this.addCheckBox(
				defectName,
				Resources.DESIGN_SMELLS,
				selected,
				AntiPatternChoiceAction.getInstance(),
				editButton);
		}
	}
}
