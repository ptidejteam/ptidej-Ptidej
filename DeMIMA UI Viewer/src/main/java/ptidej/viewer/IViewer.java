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
package ptidej.viewer;

import java.awt.event.ActionListener;

import padl.kernel.IAbstractModel;
import ptidej.ui.kernel.builder.Builder;
import util.help.IHelpURL;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2006/07/17
 */
public interface IViewer {
	void addButton(
		final String buttonLabel,
		final ActionListener listener,
		final boolean isEnabled,
		final boolean isSourceChangeListerner,
		final IHelpURL helpURL);
	void addButton(
		final String buttonLabel,
		final ActionListener listener,
		final boolean isEnabled,
		final boolean isSourceChangeListerner);
	void addSource(
		final IAbstractModel anAbstractModel,
		final Builder aBuilder);
	IRepresentation getRepresentation();
}
