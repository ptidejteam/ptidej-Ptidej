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
package ptidej.viewer.widget;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import ptidej.viewer.event.ISourceModelListener;
import ptidej.viewer.event.SourceAndGraphModelEvent;
import ptidej.viewer.ui.DesktopFrame;
import ptidej.viewer.ui.rulecard.IRuleCardListener;
import ptidej.viewer.ui.rulecard.RuleCardEvent;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2006/07/17
 */
public final class Button extends JButton implements ISourceModelListener,
		IRuleCardListener {

	private static final long serialVersionUID = 1L;
	public Button(final String label) {
		super();
		this.setText(label);
	}
	protected void fireActionPerformed(final ActionEvent anEvent) {
		DesktopFrame.getInstance().setCursor(
			Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		super.fireActionPerformed(anEvent);

		DesktopFrame.getInstance().setCursor(Cursor.getDefaultCursor());
	}
	public void ruleCardAvailable(final RuleCardEvent aRuleCardEvent) {
		this.setEnabled(true);
	}
	public void ruleCardChanged(final RuleCardEvent aRuleCardEvent) {
		this.ruleCardAvailable(aRuleCardEvent);
	}
	public void ruleCardUnavailable() {
		this.setEnabled(false);
	}
	public void sourceModelAvailable(final SourceAndGraphModelEvent aViewerEvent) {
		this.setEnabled(true);
	}
	public void sourceModelChanged(final SourceAndGraphModelEvent aViewerEvent) {
		this.sourceModelAvailable(aViewerEvent);
	}
	public void sourceModelUnavailable() {
		this.setEnabled(false);
	}
}
