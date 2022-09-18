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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

/**
 * Initializating Properties:
 * - driver: qualified classname of the IDriver class to use
 * - parts: comma-separated list of qualified classnames of IPart2
 * - see subclasses and IPart classes for specific properties
 * 
 * @author deniersi
 *
 */
public class Driver {
	
	private MendelProject project;
	
	private IPart firstPart;
	
	// Basically an easy but bad way to pass metadata around (like headers)
	private Map properties;
	
	
	public Driver() {
		this.properties = new HashMap();
	}
	
	public void initialize(MendelProject project, Properties prop) {
		String[] partNames = Util.extractValues(prop, "parts");
		Vector parts = new Vector();
		for (int i = 0; i < partNames.length; i++) {
			IPart part = (IPart) Util.createInstance(partNames[i]);
			part.initialize(prop);
			parts.add(part);
		}
		initialize(project, parts);
	}
	
	public void initialize(MendelProject project, List parts) {
		this.project = project;
		IPart previous = null;
		for (Iterator it = parts.iterator(); it.hasNext();) {
			IPart part = (IPart) it.next();
			part.initialize(this);
			previous = addPart(part, previous);
		}
	}
	
	public IPart addPart(IPart part, IPart previous) {
		if( firstPart()==null ) {
			this.firstPart = part;
		} else {
			previous.setNextPart(part);
		}
		return part;
	}
	
	public MendelProject getProject() {
		return this.project;
	}
	
	public void setProperty(Object key, Object value) {
		this.properties.put(key, value);
	}
	
	public Object getProperty(Object key) {
		return this.properties.get(key);
	}
	

	public IPart firstPart() {
		return this.firstPart;
//		return ((AbstractTool) this.parts.getFirst());
	}
	
	
	public void start() {
		firstPart().start();
//		for (Iterator it = this.parts.iterator(); it.hasNext();) {
//			((AbstractTool) it.next()).start();
//		}
	}

	public void end() {
		firstPart().end();
//		for (Iterator it = this.parts.iterator(); it.hasNext();) {
//			((AbstractTool) it.next()).end();
//		}		
	}

	
	public void setInput(Collection data) {
		firstPart().addAll(data);
	}

	public void run() {
		firstPart().run();
	}

	public void runAll() {
		firstPart().runAll();
	}
	
//	public void run() {
//		for (Iterator it = this.parts.iterator(); it.hasNext();) {
//			((AbstractTool) it.next()).compute();
//		}
//	}
//	
//	public void runAll() {
//		for (Iterator it = this.parts.iterator(); it.hasNext();) {
//			((AbstractTool) it.next()).computeAll();
//		}
//	}

}
