package glass.lattice.model.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import glass.ast.IMethod;
import glass.ast.IProject;
import glass.ast.IType;
import glass.lattice.model.IRelation;
import glass.lattice.model.IRelationBuilder;

public class ReverseInheritanceRelationBuilder implements IRelationBuilder{
	
	private IProject sourceProject;

	
	/**
	 * variable to store the local domain interfaces of the defined types of 
	 * the project. It is used during the purging of the extents
	 */
	HashMap<IType, IMethod[]> localDomainInterfaces = null;

	/**
	 * variable to store the cumulative domain interfaces of the defined types
	 * of the project (i.e. the union of the local domain interfaces of types with
	 * those of all their subtypes. This structure is used, beyond relation creation,
	 * by extent purge
	 */
	HashMap<IType, IMethod[]> subhierarchyDomainInterfaces = null;
	
	private Collection<IType> definedTypes = null;
	
	private HashMap<String, MethodEntry> methodImplementations = new HashMap<String, MethodEntry>();
	

	public HashMap<String, MethodEntry> getMethodImplementations() {
		return methodImplementations;
	}

	public Collection<IType> getDefinedTypes() {
		return definedTypes;
	}
	
	public HashMap<IType, IMethod[]> getLocalDomainInterfaces() {
		return localDomainInterfaces;
	}

	public void setLocalDomainInterfaces(HashMap<IType, IMethod[]> localDomainInterfaces) {
		this.localDomainInterfaces = localDomainInterfaces;
	}

	public HashMap<IType, IMethod[]> getSubhierarchyDomainInterfaces() {
		return subhierarchyDomainInterfaces;
	}

	public void setSubhierarchyDomainInterfaces(HashMap<IType, IMethod[]> subhierarchyDomainInterfaces) {
		this.subhierarchyDomainInterfaces = subhierarchyDomainInterfaces;
	}
	
	public IProject getProcessedProject() {
		return sourceProject;
	}


	/**
	 * a utility method used to collect all methods in the project
	 * 
	 * @return
	 */
	public Set<Object> getAllMethods() {
		HashSet<Object> allMethods = new HashSet<Object>();

		// iterate over the methods in methodImplementations
		for (String methodSignature : methodImplementations.keySet()) {
			// for each signature, use the header method (public field method of
			// class MethodEntry)
			allMethods.add(methodImplementations.get(methodSignature).method);
		}
		return allMethods;
	}

	@Override
	public IRelation buildRelationFrom(IProject aProject) {
		
		this.sourceProject = aProject;

		definedTypes = sourceProject.getDefinedTypes();

		HashMap<IType, IMethod[]> unpurgedDomainInterfaces = new HashMap<IType, IMethod[]>(),
				purgedDomainInterfaces = new HashMap<IType, IMethod[]>();

		// compute the domain
		boolean excludeAccessors = false;
		for (IType type : definedTypes) {
			IMethod[] typeLocalDomainInterface = localDomainInterface(definedTypes, type, excludeAccessors);
			if (typeLocalDomainInterface == null) {
				System.out.println("Type " + type + " has null domain interface");
			}
			unpurgedDomainInterfaces.put(type, typeLocalDomainInterface);
		}

		// purge domainInterfaces by reducing references to different
		// implementations of the same method
		// to a reference to a single method
		purgedDomainInterfaces = purge(unpurgedDomainInterfaces);
		
		setLocalDomainInterfaces(purgedDomainInterfaces);

		// now, cumulate domain interfaces for a subhierarchy (reverse
		// inheritance)
		HashMap<IType, IMethod[]> cumulativeDomainInterfaces = cumulativeDomainInterfaces(purgedDomainInterfaces);

		setSubhierarchyDomainInterfaces(cumulativeDomainInterfaces);
		
		// now, build images from purgedDomainInterfaces
		return buildImagesFrom(cumulativeDomainInterfaces);

	}

	/**
	 * 
	 * @param domainInterfaces
	 * @param jProject
	 * @return
	 */
	protected HashMap<IType, IMethod[]> cumulativeDomainInterfaces(HashMap<IType, IMethod[]> domainInterfaces) {
		HashMap<IType, IMethod[]> cumulativeDomainInterfaces = new HashMap<IType, IMethod[]>();

		// use a set so that duplicates are counted only once
		Set<IMethod> cumulativeInterface = null;

		// iterate over types
		for (IType type : domainInterfaces.keySet()) {
			cumulativeInterface = new HashSet<IMethod>();

			// first, add the type's interface
			cumulativeInterface.addAll(Arrays.asList(domainInterfaces.get(type)));

			// next, get all the subtypes of type, and add their interfaces,
			// as well.

			// get all the subtypes
			IType[] subtypes = type.getAllSubtypes();

			// now, add the domain interfaces of the subtypes
			for (IType subtype : subtypes) {
				// notice that if type is an interface, its implementing
				// classes will be considered as part of its subtypes,
				// including the anonymous classes. However, the anonymous classes
				// are not considered as DEFINED in the compilation unit, and thus, will
				// not have a domain interface associated with it
				IMethod[] subtypeDomainInterface = domainInterfaces.get(subtype);
				if (subtypeDomainInterface != null) {
					cumulativeInterface.addAll(Arrays.asList(subtypeDomainInterface));
				}
			}

			// now, create an array of IMethod from cumulativeInterface
			IMethod[] cumulative = cumulativeInterface.toArray(new IMethod[cumulativeInterface.size()]);

			// now, add the new <key,value> pair in
			// cumulativeDomainInterfaces
			cumulativeDomainInterfaces.put(type, cumulative);
		}

		return cumulativeDomainInterfaces;
	}

	protected IRelation buildImagesFrom(HashMap<IType, IMethod[]> interfaces) {
		IRelation relation = new Relation();

		// iterate over the types

		for (IType type : interfaces.keySet()) {

			// get the interface
			IMethod[] methodSet = interfaces.get(type);

			// iterate over the methods of the interface
			for (IMethod method : methodSet) {
				relation.addRelation(type, method);
			}
		}
		return relation;
	}

	protected HashMap<IType, IMethod[]> purge(HashMap<IType, IMethod[]> unpurgedDomainInterfaces) {

		// the new hashmap
		HashMap<IType, IMethod[]> purged = new HashMap<IType, IMethod[]>();

		// the types (keys)
		Set<IType> types = unpurgedDomainInterfaces.keySet();


		// first build the dictionary of method implementations for all
		// types
		for (IType type : types) {
			IMethod[] domainInterface = unpurgedDomainInterfaces.get(type);

			// iterate over the domain interface and add the methods
			for (IMethod method : domainInterface) {
				String key = hashKey(method);
				MethodEntry entry = methodImplementations.get(key);
				if (entry == null) {
					entry = new MethodEntry(method);
					methodImplementations.put(key, entry);
				} else {
					entry.addImplementation(method);
				}
			}
		}

		// then rebuild domain interfaces HashMap by making sure that
		// different implementations
		// of the same method use the same IMethod object (any one, it does
		// not matter).
		for (IType type : types) {
			IMethod[] domainInterface = unpurgedDomainInterfaces.get(type);

			IMethod[] newDomainInterface = new IMethod[domainInterface.length];

			// iterate over the domain interface and add the methods
			for (int i = 0; i < domainInterface.length; i++) {
				String key = hashKey(domainInterface[i]);
				MethodEntry entry = methodImplementations.get(key);
				if (entry == null) {
					System.out.println("Houston: we have a problem");
				} else {
					newDomainInterface[i] = entry.method;
				}
			}

			// now, add the entry <type,newDomainInterface> to the
			// purgedDomainInterfaces
			purged.put(type, newDomainInterface);
		}

		return purged;
	}

	/**
	 * returns the "string signature" of the method. I am not including the
	 * exception types because they can be modified by a subclass implementation
	 * 
	 * @param method
	 * @return
	 */
	protected String hashKey(IMethod method) {
		return method.getSignature();
	}

	/**
	 * this method computes the local domain interface of a type (class or
	 * interface), i.e. the set of methods supported by the type that are
	 * specific to the domain, and that are not utility methods inherited from
	 * the Java API.
	 * 
	 * Example of utility methods: 1) things inherited from Object (hash(),
	 * clone(), etc.) 2) getters and setters of specific attributes
	 * 
	 * We assume that everything else is domain method.
	 * 
	 * unlike the domainInterface(...,...) method, this one does not look at
	 * inherited methods: just the ones that are locally defined.
	 * 
	 * Also, this method has a switch to exclude (or not) accessors
	 * 
	 * @param jProject
	 *            : the containing project/software
	 * @param allProjectTypes
	 *            : the types included/defined in the project. Makes certain
	 *            things easier
	 * @param type
	 *            : the type for which we want to compute the domain interface
	 * @return
	 * 
	 */
	IMethod[] localDomainInterface(Collection<IType> allProjectTypes, IType type, boolean excludeAccessors) {
		IMethod[] localDomInterface = null;

		// in the end, domain methods will be here
		Collection<IMethod> domainMethods = new ArrayList<IMethod>();

		// external methods are methods that are defined from classes external
		// to the project
		Collection<IMethod> externalMethods = new ArrayList<IMethod>();

		// add the methods defined in <code>type</code>
		if (type == null) {
			System.out.println("How come type is null?");
		}

		// next, check if type is anonymous
		if (type.isAnonymous()) {
			// normally, I should exit, but I want to check what the program
			// does here
			System.out.println("Type is anonymous");
		}

		// get the methods defined in the type. The problem here is that
		// even if a method is defined in
		// <code>type</code>, it may simply be a redefinition of a utility
		// method (e.g. clone())
		// first defined by object. Thus, we first collect all methods
		// defined by classes within the
		// project, and then remove those methods that were first defined in
		// classes OUTSIDE of the project
		IMethod[] localMethods = type.getMethods();

		Collection<IMethod> allMethods = new ArrayList<IMethod>();

		allMethods.addAll(Arrays.asList(localMethods));

		// now, get the type hierarchy to climb along it

		// get all the supertypes, both within the project, and outside

		// all ancestors
		IType[] allAncestors = type.getAllSupertypes();
		HashSet<IType> outsideProjectAncestorsSet = (new HashSet<IType>(Arrays.asList(allAncestors))),
				withinProjectAncestorsSet = (HashSet<IType>) outsideProjectAncestorsSet.clone();

		// those outside project: all ancestors, excluding the types of the
		// project
		outsideProjectAncestorsSet.removeAll(allProjectTypes);

		// those within project: all the ancestors, excluding those that are
		// outside :-)
		withinProjectAncestorsSet.removeAll(outsideProjectAncestorsSet);

		// collect methods defined outside project
		Iterator<IType> outsideAncestors = outsideProjectAncestorsSet.iterator();
		while (outsideAncestors.hasNext()) {
			externalMethods.addAll(Arrays.asList(outsideAncestors.next().getMethods()));
		}

		// now, we remove from allMethods those methods that are
		// redefinitions of methods defined outside of the project
		Collection<IMethod> remainingMethods = new ArrayList<IMethod>();
		Iterator<IMethod> allMethodsIterator = allMethods.iterator();
		while (allMethodsIterator.hasNext()) {
			IMethod nextMethod = allMethodsIterator.next();
			boolean found = false;
			Iterator<IMethod> externalMethodsIterator = externalMethods.iterator();
			while (!found && externalMethodsIterator.hasNext()) {
				IMethod nextExternalMethod = externalMethodsIterator.next();
				found = nextExternalMethod.isSimilar(nextMethod);
			}
			if (!found)
				remainingMethods.add(nextMethod);

		}

		// for the time being, the domain interface is whatever is in
		// remainingMethods
		Iterator<IMethod> remainingMethodsIterator = remainingMethods.iterator();

		while (remainingMethodsIterator.hasNext()) {

			// if excludeAccessors is true, then we need to exclude
			// accessors
			IMethod next = remainingMethodsIterator.next();

			if (excludeAccessors) {
				// check whether it fits getter/setter pattern

				// getter case. Loose check: name starts with get, no
				// arguments,
				// and a non-void return
				// type

				boolean nameStartsWithGet = next.getElementName().startsWith("get");
				boolean noArguments = (next.getParameterNames().length == 0);
				boolean voidReturnType = (next.getReturnType().equalsIgnoreCase("V"));

				// setter case. Loose check: name starts with set, one
				// argument,
				// and a void return type
				boolean nameStartsWithSet = next.getElementName().startsWith("set");
				boolean oneArgument = (next.getParameterNames().length == 1);

				// constructor case
				boolean constructor = next.isConstructor();

				// now, the exclusion
				if ( // getter
				(nameStartsWithGet && noArguments && !voidReturnType) ||
						// setter
						(nameStartsWithSet && oneArgument && voidReturnType) ||
						// constructor
						constructor) {
					System.out.println("exclude method "
							+ next.getSignature()
							+ " from domain methods of " + type.getElementName());
				} else {
					// it is a domain method
					domainMethods.add(next);
					System.out.println("method " + next.getElementName() + " is a domain method.");
				}

			} else {
				domainMethods.add(next);
			}
		}
		localDomInterface = domainMethods.toArray(new IMethod[domainMethods.size()]);
		
		return localDomInterface;
	}
}