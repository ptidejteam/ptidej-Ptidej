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
package ptidej.viewer.awt.occurrences;

import java.awt.Color;
import util.multilingual.MultilingualManager;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/08/08
 */
interface Constants {
	Color NORMAL_COLOR = Color.BLUE;
	Color HIGHLIGHT_COLOR = Color.RED;

	String NO_OCCURRENCES_LABEL = MultilingualManager.getString(
		"NO_OCCURRENCES_LABEL",
		Constants.class);
	String SHOW_HIDE = MultilingualManager.getString(
		"SHOW_HIDE",
		Constants.class);
	String FOLD_UNFOLD = MultilingualManager.getString(
		"FOLD_UNFOLD",
		Constants.class);
	String FOLD_UNFOLD_ALL = MultilingualManager.getString(
		"FOLD_UNFOLD_ALL",
		Constants.class);
	String FOLD_UNFOLD_OBJECT = MultilingualManager.getString(
		"FOLD_UNFOLD_OBJECT",
		Constants.class);
	String FOLD_UNFOLD_GHOSTS = MultilingualManager.getString(
		"FOLD_UNFOLD_GHOSTS",
		Constants.class);
}
