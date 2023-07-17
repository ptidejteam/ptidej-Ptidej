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
package padl.serialiser.dummy;

public class Pilot {
	private String name;
	private int points;

	public Pilot(String name, int points) {
		this.name = name;
		this.points = points;
	}

	public int getPoints() {
		return this.points;
	}

	public void addPoints(int points) {
		this.points += points;
	}

	public String getName() {
		return this.name;
	}

	public String toString() {
		return this.name + "/" + this.points;
	}
}
