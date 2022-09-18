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

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.util.ClassPath;
import org.apache.bcel.util.SyntheticRepository;


/**
 * There is a bootstrap process where we load classes with BCEL to retrieve
 * packages, classes, and classpath.
 * 
 * TODO: to be used in Mendel Extension for Ptidej...
 * 
 * 
 * @author deniersi
 *
 */
public class PtidejClassfileEnv {
	
	private File[] classfiles;
	
	private String classpath;
	
	private String[] packages;
	
	private String[] classnames;
	
	
	public PtidejClassfileEnv(String[] path) {
		this(Util.extractClassFiles(path));
	}
	
	public PtidejClassfileEnv(File[] classfiles) {
		this.classfiles = classfiles;
		initialize();
	}

	public File[] getClassfiles() {
		return this.classfiles;
	}
	
	public String getClasspath() {
		return this.classpath;
	}
	
	public String[] getPackages() {
		return this.packages;
	}
	
	
	public String[] getClassnames() {
		return this.classnames;
	}


	public void initialize() {
		String lookupPaths = extractPath(this.classfiles);
		extractClassnames();
		extractPackages(lookupPaths);
		extractClasspath(lookupPaths);
		setupBcelRepository(getClasspath());
	}

	private void setupBcelRepository(String classpath) {
		Repository.setRepository(SyntheticRepository.getInstance(new ClassPath(classpath)));
	}
	
	
	private void extractClassnames() {
		this.classnames = new String[this.classfiles.length];
		for (int i = 0; i < this.classfiles.length; i++) {
			this.classnames[i] = Util.extractBasename(this.classfiles[i]);
		}
		
	}


	private void extractPackages(String lookupPaths) {
		// WARNING: preemptive class loading by BCEL to avoir messy
		// classpath extraction when we only have absolute paths
		// this way BCEL looks for each class in the same manner, before we
		// proceed with visiting the BCEL model
		// ie we can look for Toto class in /path/class/ (basename)
		// or we can look for class.Toto in /path/ (qualified name)
		// but we must look for classes with a consistent pattern relative to the
		// given classpath: fullpath + basename or classic classpath + qualified name		
		setupBcelRepository(lookupPaths);
		Set packages = new HashSet();
		
		for (int i = 0; i < this.classnames.length; i++) {
			try {
				JavaClass clazz = Repository.lookupClass(this.classnames[i]);
				packages.add(clazz.getPackageName());
				this.classnames[i] = clazz.getClassName(); // update full name
			}
			catch (ClassNotFoundException e) {
				System.err.println(e.getMessage());
			}			
		}
		this.packages = (String[]) packages.toArray(new String[0]);
	}


	private void extractClasspath(String lookupPaths) {
		String paths = lookupPaths;
		for (int i = 0; i < this.packages.length; i++) {
			paths = paths.replace(this.packages[i].replace('.', File.separatorChar), "");	
		}
		// flatten classpath after deleting package suffix
		String[] classpaths = paths.split(File.pathSeparator);
		Set cpath = new HashSet();
		for (int i = 0; i < classpaths.length; i++) {
			cpath.add(classpaths[i]);
		}
		this.classpath = Util.join(cpath, File.pathSeparator, new StringBuffer());
	}
	
	
	public String extractPath(File[] classfiles) {
		// Retrieve path from classfiles
		Set cpaths = new HashSet();
		for (int i = 0; i < classfiles.length; i++) {
			cpaths.add(classfiles[i].getParent());
		}
		// Build path
		return Util.join(cpaths, File.pathSeparator, new StringBuffer());		
	}
	
}
