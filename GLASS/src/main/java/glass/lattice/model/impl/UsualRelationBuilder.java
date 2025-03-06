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

/**
 * Class responsible for building the 'classical' binary relation, where each class is in relation with the
 * methods that are accessible from that class
 * 
 * @author Luca Scistri
 */
public class UsualRelationBuilder implements IRelationBuilder{

	private IProject sourceProject;
	private HashMap<String, MethodEntry> methodImplementations = new HashMap<String, MethodEntry>();
	private Collection<IType> definedTypes = null;
	
	public HashMap<String, MethodEntry> getMethodImplementations() {
		return methodImplementations;
	}
	
	public Collection<IType> getDefinedTypes() {
		return definedTypes;
	}
	
	@Override
	public IRelation buildRelationFrom(IProject project) {
		this.sourceProject = project;
		definedTypes = project.getDefinedTypes();
		
		HashMap<IType, IMethod[]> domainInterfaces = new HashMap<IType, IMethod[]>();
		
		for (IType type : definedTypes) {
			IMethod[] typeDomainInterface = domainInterface(definedTypes, type);
			this.setReferencePoint(typeDomainInterface);
			IMethod[] newDomainInterface = this.modifyDomainWithReference(typeDomainInterface);
			domainInterfaces.put(type, newDomainInterface);
			if (typeDomainInterface == null) {
				System.out.println("Type " + type + " has null domain interface");
			}
		}
		return buildImageFrom(domainInterfaces);
	}
	
	protected IRelation buildImageFrom(HashMap<IType, IMethod[]> interfaces) {
		IRelation relation = new Relation();
		
		for (IType type : interfaces.keySet()) {
			IMethod[] methods = interfaces.get(type);
			
			for (IMethod method : methods) {
				relation.addRelation(type, method);
			}
		}
		return relation;
	}
	
	private void setReferencePoint(IMethod[] typeDomainInterface) {
		for (IMethod method : typeDomainInterface) {
			String key = method.getSignature();
			MethodEntry entry = methodImplementations.get(key);
			if (entry == null) {
				entry = new MethodEntry(method);
				methodImplementations.put(key, entry);
			} else {
				entry.addImplementation(method);
			}
		}
	}
	
	private IMethod[] modifyDomainWithReference(IMethod[] typeDomainInterface) {
		IMethod[] newDomainInterface = new IMethod[typeDomainInterface.length];
		for (int i = 0; i<typeDomainInterface.length; i++) {
			String key = typeDomainInterface[i].getSignature();
			MethodEntry entry = methodImplementations.get(key);
			if (entry == null) {
				System.out.println("Entry null, should not be happening");
			} else {
				newDomainInterface[i] = entry.method;
			}
		}
		return newDomainInterface;
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
	

	/**
	 * this method computes the domain interface of a type (class or interface),
	 * i.e. the set of methods supported by the type that are specific to the
	 * domain, and that are not utility methods inherited from the Java API.
	 * 
	 * Example of utility methods: 1) things inherited from Object (hash(),
	 * clone(), etc.) 2) getters and setters of specific attributes
	 * 
	 * We assume that everything else is domain method.
	 * 
	 * A simple heuristic to find domain methods would start from the
	 * <code>argument</code>, climb the type hierarchy, collect all the methods
	 * gathered from types along the way to the root <i>that are included within
	 * the project</i>, i.e. included in <code>allProjectTypes</code>. This
	 * presents a problem with projects that include external domain code (from
	 * other parent open source projects) as jar files. Those "domain types"
	 * won't show up in <code>allProjectTypes</code>. In such a case, we can use
	 * an explicit exclusion list that filters out "java" or "non-domain types"
	 * from the type hierarchy (things in java.lang or java.util, etc.).
	 * 
	 * Will see ...
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
	private IMethod[] domainInterface(Collection<IType> allProjectTypes, IType type) {
		IMethod[] domInterface = null;

		// in the end, domain methods will be here
		Collection<IMethod> domainMethods = new ArrayList<IMethod>();

		// external methods are methods that are defined from classes external
		// to the project
		Collection<IMethod> externalMethods = new ArrayList<IMethod>();

		// will enclose non-domain methods, which, for the type being, are
		// methods defined by classes along the type hierarchy that are not
		// part of this project (i.e. types not included in allProjectTypes)
		Collection<IMethod> nonDomainMethods = new ArrayList<IMethod>();

		// all methods. The problem here is that even if a method is defined in
		// <code>type</code>
		// it may simply be a redefinition of a utility method (e.g. clone())
		// first defined by object.
		// thus, we first collect all methods defined by classes within the
		// project, and then remove
		// those methods that were first defined in classes OUTSIDE of the
		// project
		Collection<IMethod> allMethods = new ArrayList<IMethod>();

		// add the methods defined in <code>type</code>
		if (type == null) {
			System.out.println("How come type is null?");
		}

		// get the methods defined in the type
		IMethod[] localMethods = type.getMethods();
		allMethods.addAll(Arrays.asList(localMethods));
		
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

		// collect methods defined within project
		Iterator<IType> withinProjectAncestors = withinProjectAncestorsSet.iterator();
		while (withinProjectAncestors.hasNext()) {
			// get all methods defined in ancestor
			IMethod[] withinProjectAncestorMethods = withinProjectAncestors.next().getMethods();
			// of those, add only the ones that have not been redefined,
			// i.e. those that have no similar
			// method in localMethods
			for (int i = 0; i < withinProjectAncestorMethods.length; i++) {
				boolean found = false;
				Iterator<IMethod> allMethodsIterator = allMethods.iterator();
				while (!found && allMethodsIterator.hasNext()) {
					IMethod nextMethod = allMethodsIterator.next();
					found = nextMethod.isSimilar(withinProjectAncestorMethods[i]);
				}
				if (!found)
					allMethods.add(withinProjectAncestorMethods[i]);

			}
		}

		// collect methods defined outside project
		Iterator<IType> outsideAncestors = outsideProjectAncestorsSet.iterator();
		while (outsideAncestors.hasNext()) {
			externalMethods.addAll(Arrays.asList(outsideAncestors.next().getMethods()));
		}

		// now, we remove from allMethods those methods that are
		// redefinitions of
		// methods defined outside of the project
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
		// minus the getters and setters
		Iterator<IMethod> remainingMethodsIterator = remainingMethods.iterator();
		while (remainingMethodsIterator.hasNext()) {
			// check whether it fits getter/setter pattern
			IMethod next = remainingMethodsIterator.next();

			// getter case. Loose check: name starts with get, no arguments,
			// and a non-void return
			// type

			boolean nameStartsWithGet = next.getElementName().startsWith("get");
			boolean noArguments = (next.getParameterNames().length == 0);
			boolean nonVoidReturnType = (!next.getReturnType().equalsIgnoreCase("V"));

			// setter case. Loose check: name starts with set, one argument,
			// and a void return type
			boolean nameStartsWithSet = next.getElementName().startsWith("set");
			boolean oneArugument = (next.getParameterNames().length == 1);

			// constructor case
			boolean constructor = next.isConstructor();

			// now, the exclusion
			if ( // getter
			(nameStartsWithGet && noArguments && nonVoidReturnType) ||
					// setter
					(nameStartsWithSet && oneArugument && !nonVoidReturnType) ||
					// constructor
					constructor) {
				System.out.println("exclude method " + next.getElementName() + " from domain methods of "
						+ type.getElementName());
			} else {
				// it is a domain method
				domainMethods.add(next);
				//System.out.println("method " + next.getElementName() + " is a domain method.");
			}

		}
		domInterface = domainMethods.toArray(new IMethod[domainMethods.size()]);
		return domInterface;
	}

}
