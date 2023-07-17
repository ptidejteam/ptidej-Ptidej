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
import org.apache.bcel.Repository;
import ptidej.viewer.IRepresentation;
import ptidej.viewer.event.SourceAndGraphModelEvent;
import ptidej.viewer.extension.IViewerExtension;
import ptidej.viewer.extension.repository.callgraph.CallGraphGenerator;
import ptidej.viewer.extension.repository.callgraph.model.Method;
import ptidej.viewer.extension.repository.callgraph.visitor.DottyGenerator;
import util.awt.NameDialog;
import util.help.IHelpURL;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/10/06
 */
public final class DottyCallGraph extends Dotty implements IViewerExtension,
		IHelpURL {

	private static final long serialVersionUID = 1L;
	public String getHelpURL() {
		return "http://www.graphviz.org/";
	}
	public String getName() {
		return "Dotty on Call Graph";
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
	private void callDotty(final IRepresentation aRepresentation) {
		final NameDialog classNameDialog =
			new NameDialog(this, "Name of the Class?");
		final String className = classNameDialog.getName();
		final NameDialog methodNameDialog =
			new NameDialog(this, "Name of the Method?");
		final String methodName = methodNameDialog.getName();

		try {
			final CallGraphGenerator generator =
				CallGraphGenerator.getInstance();
			final Method method =
				generator.create(Repository.lookupClass(className), methodName);
			final DottyGenerator builder =
				new DottyGenerator(aRepresentation.getSourceModel());
			method.accept(builder);

			final String path =
				System.getProperty("user.dir") + File.separatorChar
						+ "Temp.dot";
			// TODO Use ProxyDisk 
			final FileWriter writer = new FileWriter(path);
			writer.write(builder.getResult().toString());
			writer.close();

			this.callDotty(aRepresentation, path);
		}
		catch (final ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
