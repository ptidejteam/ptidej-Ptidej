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

public class DataManager {
	public int pubAtt1;
	public long pubAtt2;
	
	DataManager () {
		this.pubAtt1 = 0;
		this.pubAtt2 = 0;
	}

	public long getpubAtt2() {
		return this.pubAtt2;
	}

	public void setpubAtt2(long pubAtt2) {
		this.pubAtt2 = pubAtt2;
	}

	public int getPubAtt1() {
		return this.pubAtt1;
	}

	public void setPubAtt1(int pubAtt1) {
		this.pubAtt1 = pubAtt1;
	}
}
