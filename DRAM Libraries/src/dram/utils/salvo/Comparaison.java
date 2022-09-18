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
package dram.utils.salvo;

import java.util.Comparator;

import salvo.jesus.graph.Vertex;

/**
 * @author rachedsa
 */
public class Comparaison implements  Comparator{
	public int compare(Object o1, Object o2) {
		String s1 = ((Vertex) o1).toString();
		String s2 = ((Vertex) o2).toString();
		return s1.compareTo(s2);
	}

}
