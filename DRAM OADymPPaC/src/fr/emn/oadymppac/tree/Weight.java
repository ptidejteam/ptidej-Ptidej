/*
 * $Id: Weight.java,v 1.1 2004/03/01 15:37:17 guehene Exp $
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

/**
 * Title:        ODYMPPAC
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Ecole des Mines de Nantes
 * @author Mohammad Ghoniem
 * @version 1.0
 */

public class Weight {

	private double value = 0;
	private double total = 0;
	public Weight() {
	}
	public Weight(final double val) {
		this.value = val;
	}
	public double getTotal() {
		return this.total;
	}
	public double getValue() {
		return this.value;
	}
	public void incrTotal(final double val) {
		this.total += val;
	}
	public void setTotal(final double t) {
		this.total = t;
	}
	public void setValue(final double val) {
		this.value = val;
	}
}
