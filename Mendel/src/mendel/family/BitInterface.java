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
package mendel.family;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import mendel.model.SetOps;

/**
 * An instance of BitInterface represents a class interface as a bit vector
 * encoded through a signature vector (i.e. a vector containing all method
 * signatures we want to encode in the bit vector). A bit set to 1 in a bit
 * vector means the class declares the corresponding method in the signature
 * vector.
 * 
 * @author Simon Denier
 * @since Jul 8, 2008
 * 
 */
public class BitInterface {
	
	private BigInteger bitVector;
	
	private List<String> sigVector;

	private int totalDistance;

	private Vector<Integer> distances;
	
//	public BitInterface() {
//		this(BigInteger.ZERO, new Vector<String>());
//	}
	
//	public BitInterface(List<String> sigVector) {
//		this(BigInteger.ZERO, sigVector);
//	}
	
	public BitInterface(Set<String> classSignatures, List<String> sigVector) {
		this(BigInteger.ZERO, sigVector);
		for (String sig : classSignatures) {
			this.bitVector = this.bitVector.setBit(sigVector.indexOf(sig));
		}
	}
	
	public BitInterface(BigInteger bitVector, List<String> sigVector) {
		this.bitVector = bitVector;
		this.sigVector = sigVector;
		this.distances = new Vector<Integer>();
		this.totalDistance = -1;
	}

	
	public int nbBits() {
		return this.sigVector.size();
	}

	public BigInteger bitVector() {
		return this.bitVector;
	}

	public void setBitVector(BigInteger bitVector) {
		this.bitVector = bitVector;
	}

	public List<String> sigVector() {
		return this.sigVector;
	}

	public void setSigVector(Vector<String> sigVector) {
		this.sigVector = sigVector;
	}
	
	public String toString() {
		StringBuffer str = new StringBuffer();
		int nbBits = nbBits();
		for (int i = 0; i < nbBits; i++) {
			str.append(this.bitVector.testBit(nbBits - i - 1)? "1" : "0");
		}
		return str.toString();
	}
	
	public Set<String> toInterface() {
		Set<String> classInterface = new HashSet<String>();
		for (int i = 0; i < this.bitVector.bitLength(); i++) {
			if( this.bitVector.testBit(i) ) {
				classInterface.add(this.sigVector.get(i));
			}
		}
		return classInterface;
	}
	
	
	
	public void inc() {
		setBitVector(bitVector().add(BigInteger.ONE));
	}
	
	public int distance(BitInterface anInterface) {
		return bitVector().xor(anInterface.bitVector()).bitCount();
	}

	
	public int hammingDistance(BitInterface[] set, int[] distances) {
		assert(set.length==distances.length);
		int distance = 0;
		for (int i=0; i< set.length; i++) {
			distances[i] = distance(set[i]);
			distance += distances[i];
		}
		this.totalDistance = distance;
		return distance;
	}

	/**
	 * @param vectors
	 */
	public void hammingDistances(List<BitInterface> interfaces) {
		this.distances = new Vector<Integer>();
		for (BitInterface iface : interfaces) {
			this.distances.add(distance(iface));
		}
	}
	
	public Vector<Integer> getDistances() {
		return this.distances;
	}

	public int totalDistance() {
		if( this.totalDistance==-1 ) {
			this.totalDistance = 0;
			for (Integer dist : getDistances()) {
				this.totalDistance += dist;
			}
		}
		return this.totalDistance;
	}
	
	public BitInterface optimizeHamming(BitInterface[] sets, int[] distances) {
		BigInteger max = new BigInteger("2").pow(this.sigVector.size());
		BitInterface c = new BitInterface(bitVector(), sigVector());		
//		c.setBitVector(commonBits(sets).bitVector());
		
		int distance;
		int minDistance = c.hammingDistance(sets, distances);
		while ( max.compareTo(c.bitVector())==1 ) {
			distance = c.hammingDistance(sets, distances);
			if( distance<minDistance ) {
				minDistance = distance;
				setBitVector(c.bitVector());
			}
			c.inc();
		}
		hammingDistance(sets, distances); // update distance with current prototype
		return this;
	}

	
	/*
	 * Static, functional interface.
	 */
	
	public static int distanceBetweenSets(Set<String> set1, Set<String> set2) {
		List<String> sigVector = new Vector<String>();
		sigVector.addAll(SetOps.union(set1, set2));
		return new BitInterface(set1, sigVector).distance(new BitInterface(set2, sigVector));
	}

	public static BigInteger[] bitVectors(BitInterface[] set) {
		BigInteger[] vectors = new BigInteger[set.length];
		for (int i = 0; i < vectors.length; i++) {
			vectors[i] = set[i].bitVector();
		}
		return vectors;
	}

	public static BigInteger commonBits(BigInteger[] sets) {
		BigInteger c = sets[0];
		for (BigInteger bits: sets) {
			c = c.and(bits);
		}
		return c;
	}

}
