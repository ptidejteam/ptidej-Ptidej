package consensus.algorithms;

import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node> {
	public int data;
	public int position;
	public List<Node> parents;
	public List<Node> children;

	public Node(final int i) {
		this.data = i;
		this.position = i * 1000;
		this.parents = new ArrayList<Node>();
		this.children = new ArrayList<Node>();
	}

	public void addChild(final Node u) {
		this.children.add(u);
	}

	public void addParent(final Node u) {
		this.parents.add(u);
	}

	@Override
	public int compareTo(final Node other) {
		return this.position - other.position;
	}

	public boolean hasChildren() {
		return !(this.children.size() == 0);
	}

	@Override
	public int hashCode() { //presentement useless
		final int valeur = this.position;
		return valeur;
	}

	public void setPosition(final int pos) {
		this.position = pos;
	}

	/*
	
	public boolean equals(Object that) {
		if (this ==that) return true;
		if (!(that instanceof BitVector)) return false;
	
		//return false; // pour methode rapprochement
	
	
	
		//methode a tester (rique?)
		BitVector that2 = (BitVector)that;
		that2.
	
		tab2 = that2.getTab();
		//if (tab[0] != tab2[0]) return false;//je doute de l'effficacite de ceci
		//if (tab[1] != tab2[1]) return false;
		for (int i=0; i<size; i++){
			if (tab[i] != tab2[i]) return false;
		}
		return true;
	
	}*/

	public String toString() {
		//	String s = "{";
		//	for (final Node n : this.parents) {
		//		s += n.data + " ";
		//	}
		//	s += "} > *" + this.data + "* > {";
		//	for (final Node n : this.children) {
		//		s += n.data + " ";
		//	}
		//	s += "}";
		//	return s;
		String s = "";
		for (final Node n : this.parents) {
			if (this.data < n.data) {
				s += n.data + " -> " + this.data + "\n";
			}
		}
		for (final Node n : this.children) {
			if (this.data < n.data) {
				s += this.data + " -> " + n.data + "\n";
			}
		}
		return s;
	}
}
