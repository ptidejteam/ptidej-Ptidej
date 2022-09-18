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
package mendel.part;

import java.util.Collection;
import java.util.Properties;

import mendel.Driver;
import mendel.IPart;

public class NullPart implements IPart {

	private static IPart instance = new NullPart();
	
	public static IPart instance() {
		return instance;
	}

	
	private NullPart() {	}

	public void add(Object result) {	}

	public void addAll(Collection data) {	}

	public void compute() {	}

	public Object compute(Object data) {
		return null;
	}

	public void computeAll() {	}

	public void end() {	}

	public void initialize(Properties prop) {	}

	public void initialize(Driver driver) {	}

	public IPart next() {
		return instance();
	}

	public void run() {	}

	public void runAll() {	}

	public void setNextPart(IPart part) {	}

	public void start() {	}

	public void endMe() {	}

	public void startMe() {	}

	public Driver getDriver() {
		return null; // TODO: return the real driver?
	}

}
