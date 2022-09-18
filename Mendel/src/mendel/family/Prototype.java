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

import java.util.Set;
import java.util.Vector;

import mendel.model.JClassEntity;

/**
 * @author Simon Denier
 * @since Sep 6, 2008
 *
 */
public class Prototype {

	private BitInterface bitInterface;
	
	private Set<String> itface;
	
	private String qualifier;
	
	public Prototype(BitInterface bits) {
		setBitInterface(bits);
	}
	
	public Prototype(Set<String> itface) {
		this.itface = itface;
	}

	public Prototype(Set<String> itface, String qualifier) {
		this(itface);
		setQualifier(qualifier);
	}

	
	public void setBitInterface(BitInterface bits) {
		this.bitInterface = bits;
		this.itface = bits.toInterface();
	}
	

	public BitInterface getBitInterface() {
		return this.bitInterface;
	}

	public Set<String> getInterface() {
		return this.itface;
	}
	
	public String getQualifier() {
		return this.qualifier;
	}

	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}

	public int hammingDistance() {
		return this.bitInterface.totalDistance();
	}
	
	public Vector<Integer> getHammingDistances() {
		return this.bitInterface.getDistances();
	}

	public int computeHammingFor(JClassEntity clazz) {
		return this.bitInterface.distance(new BitInterface(clazz.getLocalMethods(), this.bitInterface.sigVector()));
	}
	
}
