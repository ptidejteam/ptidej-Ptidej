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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import padl.kernel.IAbstractModel;
import ptidej.viewer.IRepresentation;
import ptidej.viewer.event.SourceAndGraphModelEvent;
import ptidej.viewer.extension.IViewerExtension;

/**
 * @author Foutse Khomh
 * @since  2007/03/01
 */
public final class QMOOD implements IViewerExtension {
	private static final QMOODMetrics qmoodCalculator = new QMOODMetrics();
	public QMOOD() {

	}
	public String getName() {
		return "QMOOD model";
	}
	private void computeMetrics(final IAbstractModel model) {
		try {
			// TODO Use ProxyDisk
			final FileWriter fw = new FileWriter("QMOOD.values");
			final BufferedWriter out = new BufferedWriter(fw);

			model.walk(qmoodCalculator);

			out.write((qmoodCalculator.getResult()).toString());
			out.write('\n');
			out.flush();
			out.close();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}
	public void invoke(final IRepresentation aRepresentation) {
		this.computeMetrics(aRepresentation.getSourceModel());
	}
	public boolean isVisible() {
		return false;
	}
	public void setVisible(final boolean aVisibility) {
	}
	public void sourceModelAvailable(
		final SourceAndGraphModelEvent aSourceModelEvent) {
	}
	public void sourceModelChanged(
		final SourceAndGraphModelEvent aSourceModelEvent) {
	}
	public void sourceModelUnavailable() {
	}
}
