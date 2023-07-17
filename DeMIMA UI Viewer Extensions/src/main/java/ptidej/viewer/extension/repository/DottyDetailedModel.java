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
package ptidej.viewer.extension.repository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import padl.visitor.IGenerator;
import ptidej.viewer.IRepresentation;
import ptidej.viewer.event.SourceAndGraphModelEvent;
import ptidej.viewer.extension.IViewerExtension;
import util.help.IHelpURL;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/10/06
 */
public final class DottyDetailedModel extends Dotty implements
		IViewerExtension, IHelpURL {

	private static final long serialVersionUID = 1L;
	private void callDotty(final IRepresentation aRepresentation) {
		// Yann 2003/07/11: Improvements!
		// The builder uses the visibility of elements in the
		// graph model to minimize the number of elements to
		// display by SugiBib.
		final IGenerator builder =
			new DottyGenerator(aRepresentation
				.getSourceGraph()
				.getVisibleElements(), true);
		aRepresentation.getSourceModel().generate(builder);

		try {
			final String path =
				System.getProperty("user.dir") + File.separatorChar
						+ "Temp.dot";
			// TODO Use ProxyDisk
			final FileWriter writer = new FileWriter(path);
			writer.write(builder.getCode().toString());
			writer.close();

			this.callDotty(aRepresentation, path);
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}
	public String getHelpURL() {
		return "http://www.graphviz.org/";
	}
	public String getName() {
		return "Dotty on Model (Detailed)";
	}
	public void invoke(final IRepresentation aRepresentation) {
		this.callDotty(aRepresentation);
	}
	public void sourceModelAvailable(final SourceAndGraphModelEvent aViewerEvent) {
		if (this.isVisible()) {
			this.callDotty(aViewerEvent.getRepresentation());
		}
	}
	public void sourceModelChanged(final SourceAndGraphModelEvent aViewerEvent) {
		this.sourceModelAvailable(aViewerEvent);
	}
	public void sourceModelUnavailable() {
		// Nothing to do...
	}
}
