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

public class PackageFilter extends EntityFilter {

	private String[] packages;
	
	public PackageFilter() {
		setPackages("");
	}
	
	/**
	 * 
	 * @param packages classpath format list of prefix
	 */
	public PackageFilter(String packages) {		
		setPackages(packages);
	}
	
	public PackageFilter(String[] packages) {
		setPackages(packages);
	}
	
	public boolean accept(IEntity entity) {
		String name = entity.getEntityName();
		String packagePrefix;
		try {
			packagePrefix = name.substring(0, name.lastIndexOf('.'));			
		}
		catch (StringIndexOutOfBoundsException e) {
			packagePrefix = ""; //NOTE: dealing with the rare case where we have default package
		}
		
		boolean pass = false;
		int i = 0;
		while( !pass && i < this.packages.length ) {
			pass = packagePrefix.startsWith(this.packages[i]); 
//				this.packages[i].endsWith(subpath);
			i++;
		}
		return pass;
	}
	
	public String toString() {
		return "Accept Entities in Packages";
	}

	
	public String[] getPackages() {
		return this.packages;
	}

	public void setPackages(String[] packages) {
		this.packages = packages;
	}
	
	public void setPackages(String packages) {
		setPackages(packages.split(File.pathSeparator));
	}
	
}
