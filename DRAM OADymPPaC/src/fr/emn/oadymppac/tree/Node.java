/*
 * $Id: Node.java,v 1.2 2006/08/11 23:11:03 guehene Exp $
 *
 * Copyright (c) 2001-2002 Jean-Daniel Fekete, Mohammad Ghoniem and
 * Ecole des Mines de Nantes.  All rights reserved.
 *
 * This software is proprietary information of Jean-Daniel Fekete and
 * Ecole des Mines de Nantes.  You shall use it only in accordance
 * with the terms of the license agreement you accepted when
 * downloading this software.  The license is available in the file
 * licence.txt and at the following URL:
 * http://www.emn.fr/fekete/oadymppac/licence.html
 */
package fr.emn.oadymppac.tree;

import java.util.Observable;
import java.util.Vector;

/**
 * Title:        ODYMPPAC
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Ecole des Mines de Nantes
 * @author Mohammad Ghoniem
 * @version 1.0
 */

@SuppressWarnings("deprecation")
public class Node extends Observable {
	public Vector children = new Vector();
	public Node parent;
	public String name = "";
	public String type = "";
	public Vector weight = new Vector(); // a vector of instances of Weight per Node
	public int id;
	public int current; // unsigned int
	public static int last_id = 0;

	public String indent = "   ";
	public boolean unchanged = true;

	public Node() {
	}
	public Node(final String name, final double w, final String type) {
		this.parent = null;
		this.name = name;
		this.type = type;
		this.add(w);
		this.current = 0;
		this.id = Node.last_id++;
	}
	public synchronized void add(final double w) {
		this.weight.addElement(new Weight(w));
	}
	public synchronized void add(final Node node) {
		// initialization if required
		if (this.children == null) {
			this.children = new Vector();
		}
		// linking the parent and the child in both directions
		node.setParent(this);
		this.children.addElement(node);
	}
	public boolean check(final int sz) {
		if (sz < this.getWeightSize()) {
			return false;
		}
		if (this.getSize() == 0) {
			for (int cur = 0; cur < sz; cur++) {
				if (this.getTotalWeight(cur) != this.getWeight(cur)) {
					return false;
				}
			}
			return true;
		}

		for (int cur = 0; cur < sz; cur++) {
			double total = this.getWeight(cur);
			for (int i = 0; i < this.children.size(); i++) {
				final Node n = this.getChild(i);
				if (!n.check(sz)) {
					return false;
				}
				total += n.getTotalWeight(cur);
			}
			if (total != this.getTotalWeight(cur)) {
				return false;
			}
		}

		return true;
	}
	public Node getChild(final int i) {
		return (Node) this.children.elementAt(i);
	}
	public synchronized Vector getChildren() {
		return this.children;
	}
	public int getCurrent() {
		return this.current;
	}
	public int getId() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	public Node getParent() {
		return this.parent;
	}
	public int getSize() {
		return this.children.size();
	}
	public double getTotalWeight() {
		return ((Weight) this.weight.elementAt(this.current)).getTotal();
	}
	public double getTotalWeight(final int c) {
		return ((Weight) this.weight.elementAt(c)).getTotal();
	}
	public String getType() {
		return this.type;
	}
	public double getWeight() {
		return ((Weight) this.weight.elementAt(this.current)).getValue();
	}
	public double getWeight(final int c) {
		return ((Weight) this.weight.elementAt(c)).getValue();
	}
	public Vector getWeights() {
		return this.weight;
	}
	public int getWeightSize() {
		return this.weight.size();
	}
	public void incrTotalWeight(final double val) {
		((Weight) this.weight.elementAt(this.current)).incrTotal(val);
	}
	public void incrTotalWeight(final int cur, final double val) {
		((Weight) this.weight.elementAt(cur)).incrTotal(val);
	}

	public boolean isLeaf() {
		boolean result = false;
		if (this.getChildren() == null) {
			result = true;
		}
		else if (this.getChildren().size() == 0) {
			result = true;
		}
		return result;
	}
	public boolean isRoot() {
		return this.getParent() == null;
	}
	public String pathname() {
		if (this.parent != null) {
			return this.parent.pathname() + this.getName();
		}
		else {
			return "/" + this.getName();
		}
	}
	public void printall(int level) {
		this.printinfo();
		level++;
		for (int i = 0; i < this.getSize(); i++) {
			for (int j = level; j > 0; j--) {
				System.out.print(this.indent);
			}
			((Node) this.getChildren().elementAt(i)).printall(level);
		}
		level--;
	}
	public void printinfo() {
		System.out.println("Name : " + this.getName() + " Type : "
				+ this.getType() + " Size : " + this.getWeight()
				+ " Total Weight : " + this.getTotalWeight());
	}
	public void setChildren(final Vector children) {
		this.children = children;
	}
	public void setCurrent(final int c) {
		if (c < this.weight.size()) {
			this.current = c;
		}
	}
	public void setName(final String name) {
		this.name = name;
	}
	public void setParent(final Node parent) {
		this.parent = parent;
	}
	public void setTotalWeight(final double val) {
		((Weight) this.weight.elementAt(this.current)).setTotal(val);
	}

	public void setTotalWeight(final int c, final double f) {
		((Weight) this.weight.elementAt(c)).setTotal(f);
	}

	public synchronized void setType(final String type) {
		this.type = type;
	}
	public void setup(final int size) {
		//
		while (this.getWeightSize() < size) {
			this.add(0.0);
		}
		for (int cur = 0; cur < size; cur++) {
			this.setTotalWeight(cur, this.getWeight(cur));
		}
		for (int i = 0; i < this.children.size(); i++) {
			final Node n = this.getChild(i);
			n.setup(size);
			for (int cur = 0; cur < this.getWeightSize(); cur++) {
				this.incrTotalWeight(cur, n.getTotalWeight(cur));
			}
		}
	}
	public void setWeight(final double val) {
		((Weight) this.weight.elementAt(this.current)).setValue(val);
	}
	public void setWeight(final int c, final double f) {
		((Weight) this.weight.elementAt(c)).setValue(f);
	}
	public void tagChildren(final String s) {
		for (int i = 0; i < this.getSize(); i++) {
			if (!this.getChild(i).getType().equals("pruned")) {
				this.getChild(i).setType(s);
			}
			if (!this.isLeaf()) {
				this.getChild(i).tagChildren(s);
			}
		}
	}

	/*  public void hasChanged(){ /// A VOIR DEMAIN
	    unchanged = false;
	  }*/
}
