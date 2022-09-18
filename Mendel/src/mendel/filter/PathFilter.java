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
/**
 * 
 */
package mendel.filter;

import java.io.File;

import mendel.model.IEntity;

public class PathFilter extends EntityFilter {

	private String[] prefixes;
	
	public PathFilter() {
		setPrefixes("");
	}
	
	/**
	 * 
	 * @param prefixes classpath format list of prefix
	 */
	public PathFilter(String prefixes) {		
		setPrefixes(prefixes);
	}
	
	public PathFilter(String[] prefixes) {
		setPrefixes(prefixes);
	}
	
	public boolean accept(IEntity entity) {
		String name = entity.getEntityName();
		String packagePrefix = name.substring(0, name.lastIndexOf('.'));
		String subpath = packagePrefix.replace('.', '/');
		
		boolean pass = false;
		int i = 0;
		while( !pass && i < this.prefixes.length ) {
			pass = this.prefixes[i].endsWith(subpath);
			i++;
		}
		return pass;
	}
	
	public String toString() {
		return "Accept Entities in Path";
	}

	
	public String[] getPrefixes() {
		return this.prefixes;
	}

	public void setPrefixes(String[] prefixes) {
		this.prefixes = prefixes;
	}
	
	public void setPrefixes(String prefixes) {
		setPrefixes(prefixes.split(File.pathSeparator));
	}
	
}
