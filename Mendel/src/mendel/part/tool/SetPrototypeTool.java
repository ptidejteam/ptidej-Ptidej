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
package mendel.part.tool;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * 
 * Input: Family
 * Output: Family
 * 
 * @author deniersi
 *
 */
public class SetPrototypeTool extends AbstractPrototypeTool {

	public Set<String> buildPrototypeInterface(List<Set<String>> interfaces) {
		Set<String> protoInterface = null;
		for (Set<String> itface : interfaces) {
			if( protoInterface==null ) {
				protoInterface = new HashSet<String>(itface);
 			} else {
				protoInterface.retainAll(itface);
			}
		}
		return protoInterface;
	}

	@Override
	public String getUniqueKey() {
		return "prototype set";
	}

}
