package consensus.algorithms;

import java.util.BitSet;

public class BitVector extends BitSet implements Comparable<BitVector> {

	/**
	 *
	 */
	private static final long serialVersionUID = -2544800168547293680L;
	public int value;
	//public int nbOfOnes;

	public BitVector(final BitVector other) {
		super(other.size());
		for (int i = 0; i < other.size(); i++) {
			this.set(i, other.get(i));
		}
		this.value = other.value;
	}

	public BitVector(final int i) {
		// TODO Auto-generated constructor stub
		super(i);
		this.value = 0;
	}
	@Override
	public int compareTo(final BitVector other) {
		// TODO Auto-generated method stub
		if (this.size() == other.size()) {
			for (int i = 0; i < other.size(); i++) {
				if (this.get(i) && !other.get(i)) {
					return 1;
				}
				else if (!this.get(i) && other.get(i)) {
					return -1;
				}
				else {
					//nothing
				}
			}
			return 0;

		}
		else {
			if (this.size() < other.size()) {
				return -1;
			}
			else {
				return 1;
			}
		}
	}
	/*
	@Override
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
	
	}
	
	@Override
	public int hashCode() { //presentement useless
		int valeur =0;
		for (int i=0;i<size;i++)
			valeur += tab[i]*Math.pow(size, i);
		return valeur;
	}
	*/
	public int getValue() {
		return this.value;
	}

	public void setValue(final int newValue) {
		this.value = newValue;
	}

	public String toString() {
		String s = super.toString();
		s = s + " " + this.value;
		return s;
	}

}
