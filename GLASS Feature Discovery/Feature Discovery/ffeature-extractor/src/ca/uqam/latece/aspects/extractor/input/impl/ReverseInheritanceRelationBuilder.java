package ca.uqam.latece.aspects.extractor.input.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeHierarchy;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;

import ca.uqam.latece.aspects.extractor.input.RelationBuilder;
import ca.uqam.latece.aspects.extractor.lattice.model.Relation;
import ca.uqam.latece.aspects.extractor.lattice.model.impl.ReverseInheritanceRelation;

public class ReverseInheritanceRelationBuilder implements RelationBuilder {


	public class MethodEntry {
		/**
		 * method is one of the implementations used as a reference point does
		 * not matter which. We can't even take the highest defined method
		 * because we can have several inheritance trees in the same project
		 */
		public IMethod method = null;

		/**
		 * all the implementations (all IMethod objects) with the same
		 * signature, including method itself
		 */
		public Set<IMethod> allImplementations;

		public MethodEntry() {
			allImplementations = new HashSet<IMethod>();
		}

		public MethodEntry(IMethod aMethod) {
			this();
			addImplementation(aMethod);
		}

		public void addImplementation(IMethod anImplementation) {
			if (anImplementation == null)
				return;

			// if this is the first, then set it
			if (method == null)
				method = anImplementation;

			// add it to the set of all implementations
			allImplementations.add(anImplementation);
		}
	}

	/**
	 * the project from which to build the relation. The first call to build
	 * relation passes IProject as argument. Inside that method we convert to an
	 * IJavaProject
	 */
	private IJavaProject javaProject = null;
	
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

	
	public IJavaProject getJavaProject() {
		return javaProject;
	}
	


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
	public Relation buildRelationFrom(IProject aProject) {

		try {
			if (!aProject.hasNature(JavaCore.NATURE_ID))
				return null;

			javaProject = JavaCore.create(aProject);
			definedTypes = new ArrayList<IType>();
			int numberCompilationUnits = 0;

			// get info about packages
			IPackageFragment[] packages = javaProject.getPackageFragments();
			for (IPackageFragment myPackage : packages) {
				// for each package fragment, if it is a source package,
				// then
				// access its compilation units
				if (myPackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
					ICompilationUnit[] compUnits = myPackage.getCompilationUnits();
					for (ICompilationUnit compUnit : compUnits) {
						numberCompilationUnits++;
						IType[] compUnitTypes = compUnit.getAllTypes();
						for (IType nextType : compUnitTypes)
							definedTypes.add(nextType);
						System.out.println("processing compilation unit: " + compUnit.getElementName());
					}
				}
			}

			System.out.println("Project " + aProject.getName() + " has : " + numberCompilationUnits
					+ " compilation units, and " + definedTypes.size() + " defined types");

			// next, compute the domain interfaces of all the types
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

		} catch (JavaModelException e1) {
			e1.printStackTrace();
		} catch (CoreException ce) {
			ce.printStackTrace();
		}
		return null;
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

		IProgressMonitor pMonitor = new NullProgressMonitor();
		ITypeHierarchy typeHierarchy = null;

		try {

			// iterate over types
			for (IType type : domainInterfaces.keySet()) {
				cumulativeInterface = new HashSet<IMethod>();

				// first, add the type's interface
				cumulativeInterface.addAll(Arrays.asList(domainInterfaces.get(type)));

				// next, get all the subtypes of type, and add their interfaces,
				// as well.

				typeHierarchy = type.newTypeHierarchy(pMonitor);

				// get all the subtypes
				IType[] subtypes = typeHierarchy.getAllSubtypes(type);

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
		} catch (JavaModelException jme) {

		}

		return cumulativeDomainInterfaces;
	}

	protected Relation buildImagesFrom(HashMap<IType, IMethod[]> interfaces) {
		ReverseInheritanceRelation relation = new ReverseInheritanceRelation(this);

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
		try {

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

		} catch (JavaModelException jme) {
			jme.printStackTrace();
		}
		return purged;
	}

	/**
	 * returns the "string signature" of the method. I am not including the
	 * exception types because they can be modified by a subclass implementation
	 * 
	 * @param method
	 * @return
	 * @throws JavaModelException
	 */
	protected String hashKey(IMethod method) throws JavaModelException {
		return Signature.toString(method.getSignature(), method.getElementName(), method.getParameterNames(), true,
				true);
	}

	/**
	 * 
	 * @param element
	 * @param types
	 * 
	 * @deprecated
	 */
	protected void navigateElement(IJavaElement element, Collection<IType> types) {
		// check if element is a class file (terminal node)
		switch (element.getElementType()) {
		case IJavaModel.COMPILATION_UNIT:
			// yes, add the type to the list of types
			IType[] definedTypes;
			try {
				definedTypes = ((ICompilationUnit) element).getAllTypes();
				for (int i = 0; i < definedTypes.length; i++)
					types.add(definedTypes[i]);
			} catch (JavaModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case IJavaModel.PACKAGE_FRAGMENT:
			// in this case, iterate over its children
			IJavaElement[] children;
			try {
				children = ((IPackageFragment) element).getChildren();
				for (int j = 0; j < children.length; j++) {
					navigateElement(children[j], types);
				}
			} catch (JavaModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		default:
			System.out.println("The element with name: " + element.getElementName()
					+ " is neither a compilation unit nor a subdirectory");
		}
	}

	/**
	 * This function analyses the types defined in the project jProject to see
	 * which ones "support" two or more types, through a combination of
	 * <code>extends</code> (for both classes and interfaces) and
	 * <code>implements</code>, for classes.
	 * 
	 * Thus, we iterate over the collection of types, and for each type: 1) if
	 * it is class, record its superclass in <code>typeExtensions</code> and its
	 * implemented interfaces in <code>typeImplementations</code> 2) if it is an
	 * interface, record its super interfaces in <code>typeExtensions</code>
	 * 
	 * At the end, if the sum of type extensions and type implementations is >=
	 * 2, this is a candidate 'multiple inheritance' instance. This function
	 * performs no filtering yet.
	 * 
	 * Note that we don't hande enumerations (java 5) or annotation types (what
	 * is that, anyway?), nor anonymous types. None of this bothers us anyway.
	 * 
	 * @param jProject
	 * @param types
	 * 
	 * @deprecated
	 */
	void analyzeMultipleInheritance(IJavaProject jProject, Collection<IType> types) {
		// get directory containing compiled classes
		// we assume that the project contains a single package fragment
		// root
		// that corresponds to this output location
		// now, print the defined types
		// for the time being, represent types and interfaces by strings
		HashMap<IType, Collection<String>> typeExtensions = new HashMap<IType, Collection<String>>();
		HashMap<IType, Collection<String>> typeImplementations = new HashMap<IType, Collection<String>>();

		// la liste des multiple inheritance records
		Collection<MultipleInheritanceRecord> candidateInstancesMultipleInheritance = new ArrayList<MultipleInheritanceRecord>();
		Iterator<IType> itTypes = types.iterator();
		while (itTypes.hasNext()) {
			IType type = itTypes.next();
			try {
				Collection<String> extensions = null, interfaces = null;
				if (type.isClass()) {
					// get its extensions and its implemented interfaces

					// extensions
					extensions = typeExtensions.get(type);
					if (extensions == null) {
						extensions = new ArrayList<String>();
						typeExtensions.put(type, extensions);
					}
					// right now, the superclass name is not qualified
					String superClassName = type.getSuperclassName();
					String resolvedName = null;
					if (superClassName != null) {
						// first let us try to resolve the superclass name
						String[][] resolvedNames = type.resolveType(superClassName);
						// if there is a single resolution use it, else say that
						// it is unresolved
						resolvedName = (resolvedNames.length == 1 ? resolvedNames[0][0] + "." + resolvedNames[0][1]
								: "UNRESOLVED." + superClassName);
						extensions.add(resolvedName);
					}
					// System.out.println("Type : " + type.getElementName() +
					// " extends "+ resolvedName);

					// implemented interfaces
					interfaces = typeImplementations.get(type);
					if (interfaces == null) {
						interfaces = new ArrayList<String>();
						typeImplementations.put(type, interfaces);
					}
					String[] impInterfaces = type.getSuperInterfaceNames();
					for (int i = 0; i < impInterfaces.length; i++) {
						String interfaceName = impInterfaces[i];
						String[][] resolvedNames = type.resolveType(interfaceName);
						// if there is a single resolution use it, else say that
						// it is unresolved
						resolvedName = (resolvedNames.length == 1 ? resolvedNames[0][0] + "." + resolvedNames[0][1]
								: "UNRESOLVED." + interfaceName);
						interfaces.add(resolvedName);
						// System.out.println("Type : " + type.getElementName()
						// + " implements "+ resolvedName);
					}

				}
				if (type.isInterface()) {
					extensions = typeExtensions.get(type);
					if (extensions == null) {
						extensions = new ArrayList<String>();
						typeExtensions.put(type, extensions);
					}
					String[] extInterfaces = type.getSuperInterfaceNames();
					for (int i = 0; i < extInterfaces.length; i++) {
						extensions.add(extInterfaces[i]);
					}

				}
				// check if the sum of extended types and implemented types is
				// two or more, then add it
				int sumOfTypes = (extensions == null ? 0 : extensions.size())
						+ (interfaces == null ? 0 : interfaces.size());
				if (sumOfTypes >= 2) {
					candidateInstancesMultipleInheritance
							.add(new MultipleInheritanceRecord(type.getFullyQualifiedName(), extensions, interfaces));
				}

			} catch (JavaModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// now, process candidate instances of multiple inheritance
		processCandidateInstancesMultipleInheritance(jProject, candidateInstancesMultipleInheritance);
	}

	/**
	 * This function analyses the set of types defined in the project
	 * <code>jProject</code> (parameter <code>types</code>) for candidate
	 * instances of delegation. We identified several "variants" for
	 * implementing delegation, from the most disciplined ones, to the sloppiest
	 * ones. We review them here: 1) most disciplined: this is the case where,
	 * a) a class has an attribute of type T, b) the class implements T
	 * (explicitly), and c) the methods of the class (corresponding to type T)
	 * call the corresponding methods on the attribute
	 * 
	 * @param jProject
	 * @param types
	 * @deprecated
	 */
	public void analyzeDelegation(IJavaProject jProject, Collection<IType> types) {
		// for the time being, just print the domain interface for each type
		Iterator<IType> typeIterator = types.iterator();

		// create a collection of delegation records
		List<DelegationRecord> delegationRecords = new ArrayList<DelegationRecord>();

		while (typeIterator.hasNext()) {
			IType nextType = typeIterator.next();
			// first, get domain interface of the type
			IMethod[] typeDomainInterface = domainInterface(jProject, types, nextType);
			// print info about domain interface
			System.out.println("Domain interface of type: " + nextType.getElementName());
			for (int i = 0; i < typeDomainInterface.length; i++) {
				System.out.print(typeDomainInterface[i] + ", ");
			}

			// next, check the attributes.
			try {
				IField[] fields = nextType.getFields();
				for (int j = 0; j < fields.length; j++) {
					IField next = fields[j];
					String fieldName = next.getElementName();
					String typeSignature = next.getTypeSignature();
					// if the field has class type then we analyse, else (base
					// type, array type, type variable,
					// wildcard type, or 'capture type'), skip because, a) it is
					// unlikely that such a field would
					// be involved in a delegation, and b) we would not know how
					// to deal with it, anyway :-)
					int typeSignatureKind = Signature.getTypeSignatureKind(typeSignature);
					if (typeSignatureKind == Signature.CLASS_TYPE_SIGNATURE) {
						// parse typeSignature to figure out the actual
						// attribute
						String elementType = Signature.getElementType(typeSignature), resolvedFieldTypeName = null;
						// the first character indicates whether it is a
						// unresolved type (starting with Q) or not
						char first = elementType.charAt(0);
						if (first == Signature.C_UNRESOLVED) {
							// it is unresolved, and thus we try to resolve it
							String unresolvedTypeName = elementType.substring(1, elementType.length() - 1);
							System.out.println("\tAttribute :" + fieldName + " with type signature: " + typeSignature
									+ " and element type: " + unresolvedTypeName);
							// a type name may resolve to different types (which
							// normally leads to a syntax error).
							String[][] candidateTypeNames = nextType.resolveType(unresolvedTypeName);
							if (candidateTypeNames != null) {
								if (candidateTypeNames.length != 1) {
									System.out.println("Cannot resolve type: " + unresolvedTypeName + " of field "
											+ fieldName + " within the context of: " + nextType.getElementName());
								} else {
									// this is the case where we have a single
									// choice: that is the valid type name (the
									// element [0][0]
									// represents the package, and [0][1]
									// represents the class name)
									resolvedFieldTypeName = candidateTypeNames[0][0] + "." + candidateTypeNames[0][1];
								}
							} else {
								continue;
							}
						}
						// now, we get the IType of the field
						IType fieldType = jProject.findType(resolvedFieldTypeName);

						// if the type of the field is secondary type (not
						// defined in a compilation
						// unit that has the same name as the type itself), then
						// IJavaProject.findType(String)
						// will return null. There seems to be a way to find the
						// IType some other way (using
						// the search engine), but we won't use that. We will
						// just skip the corresponding
						// field for now
						if (fieldType == null) {
							System.out.println("Could not find IType for :" + resolvedFieldTypeName
									+ ". Skipping the field: " + fieldName);
							continue;
						}

						// now check if it is an internal type to the project or
						// not.
						// if it is an external type, we will ignore the field
						if (!types.contains(fieldType)) {
							System.out.println("Field " + fieldName + " is ignored because its type "
									+ fieldType.getElementName() + " is external to project");
							continue;
						}
						// we get the domain interface of the field
						IMethod[] fieldDomainInterface = domainInterface(jProject, types, fieldType);

						// and compare it the domain interface of the enclosed
						// type. This method
						// checks three situations: 1) one of, 2) some ratio,
						// and 3) 100%, corresponding to
						// the cases where:
						// 1) a single method of fieldDomainInterface is present
						// in typeDomainInterface
						// 2) ratio percentage of fieldDomainInterface is
						// present in typeDomainInterface
						// 3) all of the methods of fieldDomainInterface are
						// included in typeDomainInterface
						DelegationRecord delegationRecord = compareDomainInterfaces(jProject, types, nextType,
								typeDomainInterface, fieldName, fieldType, fieldDomainInterface);
						if (delegationRecord != null)
							delegationRecords.add(delegationRecord);
					}
				}

			} catch (JavaModelException e) {
				e.printStackTrace();
			}

		}

		printDelegationRecords(jProject, delegationRecords);

	}

	/**
	 * This function prints the candidate delegation records for the java
	 * project jProject. It prints them by decreasing order of fitness, starting
	 * with the "perfect matches", i.e. cases where the entire domain interface
	 * of an attribute is included in the domain interface of the enclosing
	 * class.
	 * 
	 * @param jProject
	 * @param delegationRecords
	 * 
	 * @deprecated
	 */
	void printDelegationRecords(IJavaProject jProject, List<DelegationRecord> delegationRecords) {
		String outputFileName = "../" + jProject.getElementName() + "_delegation.out";
		try {
			PrintStream out = new PrintStream(new FileOutputStream(outputFileName));

			// First, we will sort delegationRecords by decreasing order of
			// coveragePercentage
			// first, the comparator
			Comparator<DelegationRecord> delegationRecordComparator = new Comparator<DelegationRecord>() {
				public int compare(DelegationRecord first, DelegationRecord second) {
					if (first.getCoveragePercentage() < second.getCoveragePercentage())
						return 1;
					if (first.getCoveragePercentage() == second.getCoveragePercentage())
						return 0;
					return -1;
				}

				public boolean equals(DelegationRecord first, DelegationRecord second) {
					return first.getCoveragePercentage() == second.getCoveragePercentage();
				}
			};

			// the set of delegation records

			out.println("*** DELEGATION CANDIDATES FOR PROJECT " + jProject.getElementName() + "\n\n");

			// first, we will sort the collection
			Collections.sort(delegationRecords, delegationRecordComparator);
			Iterator<DelegationRecord> records = delegationRecords.iterator();
			boolean perfectMatches = false; // used for custom printing: we want
											// to
											// separately identify perfect
											// matches
			while (records.hasNext()) {
				DelegationRecord deleg = records.next();
				if (deleg.isPerfectDelegationCase() && !perfectMatches) {
					perfectMatches = true;
					out.println("PERFECT MATCHES");
				}
				if (!deleg.isPerfectDelegationCase() && perfectMatches) {
					perfectMatches = false;
					out.println("PARTIAL MATCHES");
				}
				out.println(deleg);
			}
		} catch (FileNotFoundException fnfe) {
			System.out.println("Cannot open file: " + outputFileName);
			fnfe.printStackTrace();
		}
	}

	/**
	 * this method compares the domain interfaces of a field with that of the
	 * enclosing class. It at least one method of the field is included in the
	 * domain interface of the enclosing type, we have a case of delegation. If
	 * we find more methods, we have "stronger" candidate, with the strongest
	 * one when *all* of the methods of the field are supported by the enclosing
	 * type.
	 * 
	 * @param fieldDomainInterface
	 * @param typeDomainInterface
	 * @param jProject
	 * @param allTypes
	 * 
	 * @deprecated
	 * @return
	 */
	private DelegationRecord compareDomainInterfaces(IJavaProject jProject, Collection<IType> allTypes, IType mainType,
			IMethod[] typeDomainInterface, String fieldName, IType fieldType, IMethod[] fieldDomainInterface) {
		DelegationRecord delegationRecord = null;
		int numberMatches = 0;
		for (int i = 0; i < fieldDomainInterface.length; i++) {
			IMethod fieldMethod = fieldDomainInterface[i];
			for (int j = 0; j < typeDomainInterface.length; j++) {
				IMethod mainTypeMethod = typeDomainInterface[j];
				if (mainTypeMethod.isSimilar(fieldMethod)) {
					numberMatches++;
				}
			}
		}

		// if numberMatches > 0; then we have a delegation record
		if (numberMatches > 0) {
			delegationRecord = new DelegationRecord(mainType, typeDomainInterface, fieldName, fieldType,
					fieldDomainInterface);
			delegationRecord.setCoveragePercentage(((float) numberMatches) / fieldDomainInterface.length);
			// check whether this a perfect case or no
			delegationRecord.setPerfectDelegationCase(numberMatches == fieldDomainInterface.length);
		}
		return delegationRecord;

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
	IMethod[] domainInterface(IJavaProject jProject, Collection<IType> allProjectTypes, IType type) {
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

		IProgressMonitor pMonitor = new NullProgressMonitor();
		ITypeHierarchy typeHierarchy = null;

		try {
			// add the methods defined in <code>type</code>
			if (type == null) {
				System.out.println("How come type is null?");
			}

			// get the methods defined in the type
			IMethod[] localMethods = type.getMethods();
			allMethods.addAll(Arrays.asList(localMethods));

			// now, get the type hierarchy to climb along it
			typeHierarchy = type.newTypeHierarchy(pMonitor);

			// get all the supertypes, both within the project, and outside

			// all ancestors
			IType[] allAncestors = typeHierarchy.getAllSupertypes(type);
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
					System.out.println("method " + next.getElementName() + " is a domain method.");
				}

			}
			domInterface = domainMethods.toArray(new IMethod[domainMethods.size()]);
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return domInterface;
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

		// will enclose non-domain methods, which, for the type being, are
		// methods defined by classes along the type hierarchy that are not
		// part of this project (i.e. types not included in allProjectTypes)
		Collection<IMethod> nonDomainMethods = new ArrayList<IMethod>();

		IProgressMonitor pMonitor = new NullProgressMonitor();
		ITypeHierarchy typeHierarchy = null;

		try {
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
			
			typeHierarchy = type.newTypeHierarchy(pMonitor);

			// get all the supertypes, both within the project, and outside

			// all ancestors
			IType[] allAncestors = typeHierarchy.getAllSupertypes(type);
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
								+ Signature.toString(next.getSignature(), next.getElementName(),
										next.getParameterNames(), true, true)
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
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return localDomInterface;
	}

	/**
	 * This method processes a collection of
	 * <code>MultipleInheritanceRecord</code>s. For the time being, all it does
	 * is print the extended types and the implemented types
	 * 
	 * @param jProject
	 * @param candidates
	 * 
	 * @deprecated
	 */
	void processCandidateInstancesMultipleInheritance(IJavaProject jProject,
			Collection<MultipleInheritanceRecord> candidates) {
		try {
			String outputFileName = "../" + jProject.getElementName() + "_multiple_inheritance.out";
			PrintStream out = new PrintStream(new FileOutputStream(outputFileName));

			Iterator<MultipleInheritanceRecord> instancesMI = candidates.iterator();
			out.println("=====Multiple Inheritance Candidates for Projet " + jProject.getElementName() + "=====");
			out.println("\t" + candidates.size() + " instances");
			while (instancesMI.hasNext()) {
				MultipleInheritanceRecord instanceMI = instancesMI.next();
				out.println("Type: " + instanceMI.getName());
				out.print("\t\textends: ");
				Iterator<String> superTypes = instanceMI.getSuperTypes();
				while (superTypes.hasNext()) {
					out.print(superTypes.next() + ", ");
				}
				out.println();
				Iterator<String> implementedTypes = instanceMI.getImplementedTypes();
				out.print("\t\timplements: ");
				while (implementedTypes.hasNext()) {
					out.print(implementedTypes.next() + ", ");
				}
				out.println();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Cannot open file: ");
			e.printStackTrace();
		}
	}

}
