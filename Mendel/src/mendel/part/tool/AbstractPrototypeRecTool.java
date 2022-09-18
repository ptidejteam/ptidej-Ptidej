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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import mendel.family.Family;
import mendel.family.Prototype;
import mendel.model.JClassEntity;
import mendel.model.SetOps;
import mendel.part.AbstractPart;

/**
 * @author Simon Denier
 * @since Sep 6, 2008
 *
 */
public abstract class AbstractPrototypeRecTool extends AbstractPart {

	private Map<JClassEntity, Prototype> memo;
	
	private AbstractPrototypeTool tool;
	
	public AbstractPrototypeRecTool(AbstractPrototypeTool tool) {
		this.memo = new HashMap<JClassEntity, Prototype>();
		this.tool = tool;
	}
	
	public Object compute(Object entry) {
		Family family = ((Family) entry);
		Prototype prototype = buildPrototype(family.parent());
		family.setPrototype(prototype, "prototype");
		family.setPrototype(prototype, getUniqueKey());
		return family;			
	}
	
	public abstract String getUniqueKey();

	public Prototype buildPrototype(JClassEntity parent) {
		/*
		 * This is a rather special case of the recursive algorithm. 
		 * If the given family is single-child, then this single child is considered as the
		 * prototype AND WE DONT LOOK BELOW.
		 * However, in the general case (family with 2+ children), 
		 * we will flatten all the children and grandchildren, including single-child family!
		 * That means that the memo map will store a different prototype for some single-child family
		 * in some cases (single-child family with grandchildren) than the given one.
		 */
		if( parent.familySize()==1 ) {
			JClassEntity child = parent.getChildren().get(0);
			return new Prototype(child.getLocalMethods(), getUniqueKey());
		} else
			return recCompute(parent);
	}
	
	public Prototype recCompute(JClassEntity parent) {
		if( !this.memo.containsKey(parent) ) {
			Vector<Set<String>> interfaces = new Vector<Set<String>>();
			for (JClassEntity child : parent.getChildren()) {
				if( child.familySize()>0 ) {
					interfaces.add(SetOps.union(recCompute(child).getInterface(), child.getLocalMethods()));
				} else {
					interfaces.add(child.getLocalMethods());
				}
			}
			this.memo.put(parent, new Prototype(this.tool.buildPrototypeInterface(interfaces), getUniqueKey()));
		}
		return this.memo.get(parent);
	}
	
//	public BitInterface memoCompute(JClassEntity parent) {
//		if( !this.memo.containsKey(parent) ) {
//			Vector<Set<String>> interfaces = new Vector<Set<String>>();
//			for (JClassEntity child : parent.getChildren()) {
//				if( child.familySize()>0 ) {
//					interfaces.add(SetOps.union(memoCompute(child).toInterface(), child.getLocalMethods()));
//				} else {
//					interfaces.add(child.getLocalMethods());
//				}
//			}
////			System.out.println("Family: " + parent.getBaseName());
//			this.memo.put(parent, this.tool.computeInterface(interfaces));
//		}
//		return this.memo.get(parent);
//	}

}
