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
package ptidej.viewer.awt.entities;

import java.awt.Color;
import util.multilingual.MultilingualManager;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/08/19
 */
interface Constants {
	Color NORMAL_COLOR = Color.BLUE;
	Color HIGHLIGHT_COLOR = Color.RED;

	String NO_GRAPH_MODEL_LABEL = MultilingualManager.getString(
		"NO_GRAPH_MODEL_LABEL",
		Constants.class);
	String NO_SOURCE_MODEL_LABEL = MultilingualManager.getString(
		"NO_SOURCE_MODEL_LABEL",
		Constants.class);
}
