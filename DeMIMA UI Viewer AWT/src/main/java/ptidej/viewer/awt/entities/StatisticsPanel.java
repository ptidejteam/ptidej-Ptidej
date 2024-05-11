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
/*
 * (c) Copyright 2001, 2002 Yann-Gaël Guéhéneuc, Ecole des Mines de Nantes and
 * Object Technology International, Inc.
 * 
 * Use and copying of this software and preparation of derivative works based
 * upon this software are permitted. Any copy of this software or of any
 * derivative work must include the above copyright notice of Yann-Gaël
 * Guéhéneuc, this paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS ALL
 * WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE, AND NOT
 * WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN, ANY LIABILITY FOR DAMAGES
 * RESULTING FROM THE SOFTWARE OR ITS USE IS EXPRESSLY DISCLAIMED, WHETHER
 * ARISING IN CONTRACT, TORT (INCLUDING NEGLIGENCE) OR STRICT LIABILITY, EVEN IF
 * YANN-GAEL GUEHENEUC IS ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
package ptidej.viewer.awt.entities;

import java.awt.Panel;
import java.awt.TextArea;
import ptidej.viewer.awt.IAWTRepresentation;
import ptidej.viewer.event.SourceAndGraphModelEvent;
import ptidej.viewer.layout.PercentLayout;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since 2014/03/30
 */
public final class StatisticsPanel extends Panel {
	private static final long serialVersionUID = 1L;

	private static StatisticsPanel UniqueInstance;
	public static StatisticsPanel getInstance() {
		if (StatisticsPanel.UniqueInstance == null) {
			StatisticsPanel.UniqueInstance = new StatisticsPanel();
		}
		return StatisticsPanel.UniqueInstance;
	}

	private final TextArea textArea;

	private StatisticsPanel() {
		this.setLayout(new PercentLayout());

		this.textArea = new TextArea();
		this.textArea.setEditable(false);
		this.add(this.textArea, new PercentLayout.Constraint(0, 0, 100, 100));

		// Yann 2013/09/28: Initialisation!
		this.sourceModelUnavailable();
	}
	public void sourceModelAvailable(final SourceAndGraphModelEvent aSourceEvent) {
		final IAWTRepresentation newRepresentation =
			(IAWTRepresentation) aSourceEvent.getRepresentation();
		this.textArea
			.setText(newRepresentation.getModelStatistics().toString());

		// I must refresh the window.
		this.validate();
		this.repaint();
	}
	public void sourceModelChanged(final SourceAndGraphModelEvent aSourceEvent) {
		this.sourceModelUnavailable();
		this.sourceModelAvailable(aSourceEvent);
	}
	public void sourceModelUnavailable() {
		this.textArea.setText(Constants.NO_SOURCE_MODEL_LABEL);

		// I must refresh the window.
		this.validate();
		this.repaint();
	}
}
