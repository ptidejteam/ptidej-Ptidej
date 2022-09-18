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
package parser.defuse;

import java.util.Comparator;

public class CharComparator implements Comparator<char[]> {

	public int compare(final char[] arg0, final char[] arg1) {
		/*		if((new String(arg0)).equalsIgnoreCase(new String(arg1))){
					return 0;
				}
				else 
				return -1;*/
		//		if(ArrayUtils.isEquals(arg0, arg1)){
		//			return 0;
		//		}
		//		else 
		//		return -1;
		return new String(arg0).compareTo(new String(arg1));
	}

}
