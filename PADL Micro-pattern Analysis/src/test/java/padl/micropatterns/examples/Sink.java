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

public class Sink {
	public int pubAtt1;
	public long pricAtt;
	
	Sink () {
		this.pubAtt1 = 0;
		this.pricAtt = 0;
	}

	public int TestMethod() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int TestMethod2() {
		// TODO Auto-generated method stub
		this.pubAtt1 = 0;
		return 1;
	}
}
