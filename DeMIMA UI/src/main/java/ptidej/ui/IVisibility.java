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
package ptidej.ui;

public interface IVisibility {
	public static int AGGREGATION_DISPLAY_ELEMENTS = 2;
	public static int AGGREGATION_NAMES = 1;
	public static int ASSOCIATION_DISPLAY_ELEMENTS = 8;
	public static int ASSOCIATION_NAMES = 4;
	public static int COMPOSITION_DISPLAY_ELEMENTS = 32;
	public static int COMPOSITION_NAMES = 16;
	public static int CONTAINER_AGGREGATION_DISPLAY_ELEMENTS = 128;
	public static int CONTAINER_AGGREGATION_NAMES = 64;
	public static int CONTAINER_COMPOSITION_DISPLAY_ELEMENTS = 1024;
	public static int CONTAINER_COMPOSITION_NAMES = 256;
	public static int CREATION_DISPLAY_ELEMENTS = 4096;
	public static int CREATION_NAMES = 2048;
	public static int DELEGATION_DISPLAY_ELEMENTS = 16384;
	public static int DELEGATION_NAMES = 8192;
	public static int FIELD_NAMES = 32768;
	public static int FULLY_QUALIFIED_NAMES = 65536;
	public static int GHOST_ENTITIES_DISPLAY = 131072;
	public static int HIERARCHY_DISPLAY_ELEMENTS = 524288;
	public static int HIERARCHY_NAMES = 262144;
	public static int METHOD_NAMES = 1048576;
	public static int USE_DISPLAY_ELEMENTS = 4194304;
	public static int USE_NAMES = 2097152;

	int getVisibleElements();
	void setVisibleElements(final int visibility);
}
