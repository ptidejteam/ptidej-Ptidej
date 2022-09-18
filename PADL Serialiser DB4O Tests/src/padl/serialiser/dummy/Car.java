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

import java.util.ArrayList;
import java.util.List;

public class Car {
	private String model;
	private List listOfPilots = new ArrayList();

	public Car(String model) {
		this.model = model;
	}

	//	public Pilot getPilot() {
	//		return (Pilot) listOfPilots.get(0);
	//	}

	public void setPilot(Pilot pilot) {
		this.listOfPilots.add(pilot);
	}

	public String getModel() {
		return this.model;
	}

	public String toString() {
		return this.model + "[" + this.listOfPilots.get(0) + "]";
	}
}
