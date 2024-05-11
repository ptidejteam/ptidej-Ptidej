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
package padl.micropatterns.examples;

public class Record {
	public int pubAtt1;
	public long pricAtt;
	
	Record () {
		this.pubAtt1 = 0;
		this.pricAtt = 0;
	}

	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		return super.equals(arg0);
	}

	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
