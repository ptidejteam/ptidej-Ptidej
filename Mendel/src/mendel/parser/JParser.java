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
package mendel.parser;

import java.util.List;


import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.Type;

import mendel.IParser;
import mendel.IRepository;
import mendel.model.IEntity;

public class JParser implements IParser {

	private IRepository repository;
	private JClassParser classParser;
	private JInterfaceParser interfaceParser;

	private String startClass;
	private String[] packageFilters;
	
	public JParser(IRepository aRepository) {
		this(aRepository, new String[] {""} ); // "" is the 'always accept' filter
	}
	
	public JParser(IRepository aRepository, List<String> packageFilters) {
		this(aRepository, packageFilters.toArray(new String[0]));
	}

	public JParser(IRepository aRepository, String[] packageFilters) {
		this.repository = aRepository;
		this.classParser = new JClassParser(this);
		this.interfaceParser = new JInterfaceParser(this);
//		this.packageFilters = Arrays.copyOf(packageFilters, packageFilters.length);
		this.packageFilters = new String[packageFilters.length];
		System.arraycopy(packageFilters, 0, this.packageFilters, 0, packageFilters.length);
	}

	
	/*
	 * Protocol
	 */

	public IEntity run(String className) {
		start(className);
		return getResult();
	}
	
	public void start(String className) {
		this.startClass = className;
		try {
			visitEntity(Repository.lookupClass(className));
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String getStartClass() {
		return this.startClass;
	}
	
	public IEntity getResult() {
		return this.repository.getEntity(getStartClass());
	}
	
	public IEntity getEntityByName(String className) {
		// return null in not in repositoty
		// should only happen for root type if correct protocol use
		return this.repository.getEntity(className);
	}
	
	/*
	 * Protocol:
	 * start
	 * visitEntity
	 * - if not in repository
	 *  - visitEntity(superInterfaces)
	 *  - visitEntity(superClass)
	 *  - parseEntity (delegate)
	 *  - retrieve entity and store in repository
	 * end (getresult)
	 * 
	 * parseEntity (in delegates)
	 * - createEntity with registered supers (get them via JParser repository)
	 * - build entity with visitMethods
	 * - return new entity
	 * 
	 * NOTE: recursive parsing (up inheritance/implemetation tree) takes place HERE
	 * and not in delegate parsers.
	 * Delegate parsers only request super entities via parent parser (that is, JParser)
	 */

	/*
	 * Filter entities to parse - see parseEntity(JavaClass)
	 * distinction between a deep parsing (with full method sets) and a shallow parsing
	 * (only entities and relationships)
	 * Shallow parsing greatly reduces information overload while still providing the
	 * essential information of relations
	 * 
	 * If shallow parsing is activated, a simple filter (package prefix) can decide whether
	 * or not an entity should be deep/shallow parsed. Need the collaboration of DelegateParser
	 * though, which will proceed (or not) with visiting methods depending on the filter flag
	 */
	
	public void visitEntity(JavaClass aClass) {
		if( !this.repository.hasEntityRegistered(aClass.getClassName()) ) {
//			System.out.println(aClass.getMajor() + "." + aClass.getMinor() + " " + aClass.getClassName());
			try {
				visitSuperClass(aClass);
				visitSuperInterfaces(aClass);
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			IEntity newEntity = parseEntity(aClass);
			this.repository.registerEntity(aClass.getClassName(), newEntity);			
		}
	}

	public void visitSuperClass(JavaClass aClass)
		throws ClassNotFoundException {
		JavaClass superClass = aClass.getSuperClass();
		if( superClass!=null ) {
			visitEntity(superClass);
		}
	}

	public void visitSuperInterfaces(JavaClass aClass)
		throws ClassNotFoundException {
		for (JavaClass superInt : aClass.getInterfaces()) {
			visitEntity(superInt);
		}
	}

	public IEntity parseEntity(JavaClass aClass) {
//		boolean deepParse = filterEntity(aClass.getClassName());
		boolean deepParse = true; // TODO: do we keep filtering or not?
		// dispatch based on dynamic types (place for a multimethod)
		if( aClass.isInterface() )
			return this.interfaceParser.parseEntity(aClass, deepParse);
		else
			return this.classParser.parseEntity(aClass, deepParse);
	}
	
	public boolean filterEntity(String className) {
		boolean match = false;
		int i = 0;
		while (!match && i<this.packageFilters.length) {
			match = className.startsWith(this.packageFilters[i]);
			i++;
		}
		return match;
	}

	
	/*
	 * Helper methods
	 */

	// TODO: we dont take into account covariant return type here in overriding.
	// One solution is to exclude return type from signature
	// but then we lose some information.
	// We can drop return type from signature when checking two signatures for equivalence.
	// See extractSignificant... below
	public String getFullSignature(Method m) {
		return m.getName() + buildTypeSignature(m.getArgumentTypes(), m.getReturnType());
	}
	
	public String buildTypeSignature(Type[] args, Type returnType) {
		StringBuffer sig = new StringBuffer("(");
		for (Type argType : args) {
			sig.append(argType.toString());
			sig.append(" ");
		}
		sig.append(") ");
		sig.append(returnType.toString());
		return sig.toString();
	}
	
	public String extractSignificantSignature(String sig) {
		// drop substring after last space (ie before return type)
		return sig.substring(0, sig.lastIndexOf(' '));
	}

}
