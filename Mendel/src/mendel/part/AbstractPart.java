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
import java.util.LinkedList;
import java.util.Properties;

import mendel.Driver;
import mendel.IPart;
import mendel.MendelProject;

/**
 * Initializating Properties:
 *  - see subclasses for specific properties
 *  
 * @author deniersi
 *
 */
public abstract class AbstractPart implements IPart {
	
	private Driver driver;
	
	private IPart nextPart;
	
	private LinkedList data;
	
	
	public AbstractPart() {
		this.nextPart = NullPart.instance();
		this.data = new LinkedList();
	}
	
	
	public void initialize(Properties prop) {

	}
	
	public void initialize(Driver driver) {
		this.driver = driver;
	}
	
	public Driver getDriver() {
		return this.driver;
	}
	
	public MendelProject getProject() {
		return getDriver().getProject();
	}
	
	public void setNextPart(IPart part) {
		this.nextPart = part;
	}
	
	public IPart next() {
		return this.nextPart;
	}

	
	public void add(Object data) {
		this.data.add(data);
	}
	
	public void addAll(Collection data) {
		this.data.addAll(data);
	}
	
	protected Collection getData() {
		return this.data;
	}
	
	public void compute() {
		if( !this.data.isEmpty() ) {
			Object result = compute(this.data.removeFirst());
			if( result!=null ) {
				next().add(result);			
			}			
		}
	}
	
	public void computeAll() {
		while( !this.data.isEmpty() ) {
			compute();
		}
	}
	
	public void run() { // TODO: do not call/override if BatchOnly (ex: for threshold computation)
		compute();
		// TODO: we could check for return value and stop if null result
		next().run();
	}
	
	public void runAll() {
		computeAll();
		next().runAll();
	}
	
	
	public void start() {
		startMe();
		next().start();
	}

	public void end() {
		endMe();
		next().end();
	}


	public void endMe() {	}


	public void startMe() {	}
	
}
