/*
 * $Id: DefaultVarDomainList.java,v 1.1 2004/03/01 15:37:16 guehene Exp $
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
package fr.emn.oadymppac;

import java.util.Vector;

/**
 * Default implementation of the <code>VarDomainList</code> interface.
 *
 * @author Jean-Daniel Fekete
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 */

public class DefaultVarDomainList implements VarDomainList {
	/**
	 * A Vector of vardomains.
	 */
	Vector varDomains;

	/**
	 * Default constructor.
	 */
	public DefaultVarDomainList() {
		this.varDomains = new Vector();
	}
	/**
	 * Creates an n element long vardomain list.
	 */
	public DefaultVarDomainList(final int n) {
		this.varDomains = new Vector(n);
	}
	/**
	 * Copy constructor.
	 */
	public DefaultVarDomainList(final VarDomainList other, final boolean deep) {
		this.varDomains = new Vector(other.getLength());
		for (int i = 0; i < other.getLength(); i++) {
			if (deep) {
				this.addVarDomain(new DefaultVarDomain(
					other.getVarDomain(i),
					true));
			}
			else {
				this.addVarDomain(other.getVarDomain(i));
			}
		}
	}

	/**
	 * Adds the given <code>VarDomain</code> to the list.
	 */
	public void addVarDomain(final VarDomain vd) {
		this.varDomains.addElement(vd);
	}
	/**
	 * Clears the list.
	 */
	public void clear() {
		this.varDomains.clear();
	}
	/**
	 * Returns the number of vardomains in the list.
	 */
	public int getLength() {
		return this.varDomains.size();
	}
	/**
	 * Returns the <code>VarDomain</code> at <code>index</code>.
	 */
	public VarDomain getVarDomain(final int index) {
		return (VarDomain) this.varDomains.elementAt(index);
	}

	/**
	 * Removes the given <code>VarDomain</code> from the list.
	 */
	public void removeVarDomain(final VarDomain vd) {
		this.varDomains.removeElement(vd);
	}
}
