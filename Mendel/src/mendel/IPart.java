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
package mendel;

import java.util.Collection;
import java.util.Properties;

public interface IPart {

	/**
	 * Hotspot.
	 */
	public void initialize(Properties prop);

	/**
	 * Hotspot.
	 * 
	 * @param data
	 * @return
	 */
	public Object compute(Object data);
	
	/**
	 * Hotspot.
	 */
	public void startMe();

	/**
	 * Hotspot.
	 */	
	public void endMe();

	public void initialize(Driver driver2);

	public void setNextPart(IPart part);

	public IPart next();

	public void add(Object result);

	public void addAll(Collection data);

	public void computeAll();

	public void compute();

	public void run();

	public void runAll();

	public void start();

	public void end();

	public Driver getDriver();

}
