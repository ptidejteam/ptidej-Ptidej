package ca.uqam.latece.aspects.extractor.input.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class MultipleInheritanceRecord {
	
	private Collection<String> extendsTypes = null;
	
	private Collection<String> implementsInterfaces = null;
	
	private String name;
	
	MultipleInheritanceRecord() {
		extendsTypes = new ArrayList<String>();
		implementsInterfaces = new ArrayList<String>();	
	}
	
	public MultipleInheritanceRecord(String className){
		this();
		name = className;
	}
	
	public MultipleInheritanceRecord(String className, Collection<String> superTypes, Collection<String> impTypes){
		name = className;
		// double check whether supertypes or implemented interfaces are null, and initialize
		// them if they are
		if (superTypes == null) superTypes = new ArrayList<String>();
		extendsTypes = superTypes;
		
		if (impTypes == null) impTypes = new ArrayList<String>();
		implementsInterfaces = impTypes;
	}

	/**
	 * @param arg0
	 * @return
	 * @see java.util.Collection#add(java.lang.Object)
	 */
	public boolean addSupertype(String arg0) {
		return extendsTypes.add(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	public boolean addAllSupertypes(Collection<? extends String> arg0) {
		return extendsTypes.addAll(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 * @see java.util.Collection#remove(java.lang.Object)
	 */
	public boolean removeSupertype(Object arg0) {
		return extendsTypes.remove(arg0);
	}

	/**
	 * @return
	 * @see java.util.Collection#size()
	 */
	public int numberSupertypes() {
		return extendsTypes.size();
	}

	/**
	 * @param e
	 * @return
	 * @see java.util.Collection#add(java.lang.Object)
	 */
	public boolean addImplementedInterface(String e) {
		return implementsInterfaces.add(e);
	}

	/**
	 * @param c
	 * @return
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	public boolean addAllImplementationInterfaces(Collection<? extends String> c) {
		return implementsInterfaces.addAll(c);
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.Collection#remove(java.lang.Object)
	 */
	public boolean removeImplementedInterface(Object o) {
		return implementsInterfaces.remove(o);
	}

	/**
	 * @return
	 * @see java.util.Collection#size()
	 */
	public int numberImplementedInterfaces() {
		return implementsInterfaces.size();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 * @see java.util.Collection#iterator()
	 */
	public Iterator<String> getSuperTypes() {
		return extendsTypes.iterator();
	}
	
	
	public Iterator<String> getImplementedTypes() {
		return implementsInterfaces.iterator();
	}

}
