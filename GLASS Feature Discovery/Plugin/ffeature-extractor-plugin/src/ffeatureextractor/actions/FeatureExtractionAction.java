package ffeatureextractor.actions;

import java.io.File;  
import java.io.IOException;  
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeHierarchy;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

import ca.uqam.latece.aspects.extractor.input.impl.ReverseInheritanceRelationBuilder;
import ca.uqam.latece.aspects.extractor.lattice.LatticeBuilder;
import ca.uqam.latece.aspects.extractor.lattice.graph.model.Node;
import ca.uqam.latece.aspects.extractor.lattice.graph.model.NodeFeatureType;
import ca.uqam.latece.aspects.extractor.lattice.impl.LatticeBuilderImpl;
import ca.uqam.latece.aspects.extractor.lattice.model.Lattice;
import ca.uqam.latece.aspects.extractor.lattice.model.Relation;
import ca.uqam.latece.aspects.extractor.lattice.visitors.Visitor;
import ca.uqam.latece.aspects.extractor.lattice.visitors.impl.ComplexPurgeExtentsVisitor;
import ca.uqam.latece.aspects.extractor.lattice.visitors.impl.FeatureDetectorVisitor;
import ca.uqam.latece.aspects.extractor.lattice.visitors.impl.LatticeGraphGenerator;
import ca.uqam.latece.aspects.extractor.lattice.visitors.impl.LatticePrettyPrinter;
import ca.uqam.latece.aspects.extractor.lattice.visitors.impl.PrintCandidatesVisitor;
import ca.uqam.latece.aspects.extractor.lattice.visitors.impl.SimplePurgeExtentsVisitor;
import ffeatureextractor.viewpart.LatticeGraph;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class FeatureExtractionAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	private IProject project;
	/**
	 * The constructor.
	 */
	public FeatureExtractionAction() {
	}

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
				e.printStackTrace();
			}
		default:
			System.out.println("The element with name: " + element.getElementName()
					+ " is neither a compilation unit nor a subdirectory");
		}
	}

	/**
	 * The action has been activated. The argument of the method represents the
	 * 'real' action sitting in the workbench UI.
	 * 
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		// retourne le nom de tous les projects dans le workspace
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		
		IProject[] workspaceProjects = workspace.getRoot().getProjects();
		String[] workspaceProjectsNames = new String[workspaceProjects.length];
		for (int i = 0 ;  i < workspaceProjects.length; i++ ) {
			workspaceProjectsNames[i] = workspaceProjects[i].getName();
		}

		// 1. prompt user for project name
		ElementListSelectionDialog dialog =
			    new ElementListSelectionDialog(window.getShell(), new LabelProvider());
			dialog.setElements(workspaceProjectsNames);
			dialog.setTitle("Select a project for feature dicovery : ");
			// user pressed cancel
			dialog.open();
			Object[] result = dialog.getResult();
			//if (dialog.open() != window.OK) {
			     //   return false;
			//}
			//Object[] result = dialog.getResult();
		
		//InputDialog projectNameDialog = new InputDialog(window.getShell(), "Project Name", "Enter project name",
			//	"<PROJECT NAME>", null);
		//projectNameDialog.open();
		//String projectName = projectNameDialog.getValue();
		String projectName = (String)result[0];
		// 2. get project with provided name
		project = workspace.getRoot().getProject(projectName);

		if (project == null) {
			MessageDialog.openInformation(window.getShell(), "Feature Extractor",
					"No project with that name. Please try again");
			return;
		}

		// 3. build relation from project, and print it
		ReverseInheritanceRelationBuilder relationBuilder = new ReverseInheritanceRelationBuilder();
		Relation relation = relationBuilder.buildRelationFrom(project);
		System.out.println("Printing the relation");
		//System.out.println(relation.printString());

		// 4. build lattice from relation, and print it
		LatticeBuilder latticeBuilder = new LatticeBuilderImpl();

		Lattice aLattice = latticeBuilder.buildLattice(relation, null);

		System.out.println("Done building lattice!");

		// 5 display lattice (imen)
		// 5.1 pretty print lattice (hafedh)
		LatticePrettyPrinter latticePrinter = LatticePrettyPrinter.javaElementsLatticePrettyPrinter();
		aLattice.acceptTopVisitor(latticePrinter);

		System.out.println("Done printing lattice!");

		// LatticeGraphGenerator latticeGraphGenerator = new LatticeGraphGenerator();
		// aLattice.acceptTopVisitor(latticeGraphGenerator);

		// end (imen)

		// 6. now identify candidate features
		// 6.1 First, purge extents of non-minimas, and print new lattice
		boolean basicPurge = false;
		// ask what type of purge
		//InputDialog purgeType = new InputDialog(window.getShell(), "Type of purge", "Select purge type (erase one)",
		//		"SIMPLE COMPLEX", null);
		//purgeType.open();

		//String purge = purgeType.getValue();

		//if (purge.trim().equalsIgnoreCase("COMPLEX"))
		//	basicPurge = false;

		Visitor purgeVisitor = null;
		if (basicPurge) {
			purgeVisitor = new SimplePurgeExtentsVisitor();

		} else {
			System.out.println("Using complex purge");
			purgeVisitor = new ComplexPurgeExtentsVisitor(relationBuilder);
		}
		;
		aLattice.acceptTopVisitor(purgeVisitor);
		latticePrinter.reset();
		System.out.println("Printing lattice after purging extents");
		aLattice.acceptTopVisitor(latticePrinter);
		System.out.println("Done printing lattice!");

		// 6.2 Second, extract candidate features
		FeatureDetectorVisitor featureDetector = new FeatureDetectorVisitor(relationBuilder);
		aLattice.acceptTopVisitor(featureDetector);

		// 6.3 Third, print candidate feature nodes
		PrintCandidatesVisitor printCandidatesVisitor = new PrintCandidatesVisitor(
				featureDetector.getCandidateFeatureNodes());
		System.out.println("Printing candidate nodes");
		aLattice.acceptTopVisitor(printCandidatesVisitor);
		System.out.println("Done printing candidate nodes!");

		LatticeGraphGenerator latticeGraphGenerator = new LatticeGraphGenerator(
				featureDetector.getCandidateFeatureNodes());
		
		aLattice.acceptTopVisitor(latticeGraphGenerator);
		System.out.println(Platform.isRunning());
		
		// 5.1 building the view plugin and display lattice nodes (imen)
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IViewPart viewPart = page.findView("ffeatureExtractor.viewpart.LatticeGraph");
		LatticeGraph graphView = (LatticeGraph) viewPart;
		graphView.updateLabel(RemoveNonCandidateNodes(printCandidatesVisitor.getNodes(), aLattice));
		generateStaticsFile(printCandidatesVisitor.getNodes());
		
	}

	/**
	 * This function analyses the types defined in the project jProject to see which
	 * ones "support" two or more types, through a combination of
	 * <code>extends</code> (for both classes and interfaces) and
	 * <code>implements</code>, for classes.
	 *
	 * Thus, we iterate over the collection of types, and for each type: 1) if it is
	 * class, record its superclass in <code>typeExtensions</code> and its
	 * implemented interfaces in <code>typeImplementations</code> 2) if it is an
	 * interface, record its super interfaces in <code>typeExtensions</code>
	 *
	 * At the end, if the sum of type extensions and type implementations is >= 2,
	 * this is a candidate 'multiple inheritance' instance. This function performs
	 * no filtering yet.
	 *
	 * Note that we don't hande enumerations (java 5) or annotation types (what is
	 * that, anyway?), nor anonymous types. None of this bothers us anyway.
	 *
	 * @param jProject
	 * @param types
	 *
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
	 * <code>jProject</code> (parameter <code>types</code>) for candidate instances
	 * of delegation. We identified several "variants" for implementing delegation,
	 * from the most disciplined ones, to the sloppiest ones. We review them here:
	 * 1) most disciplined: this is the case where, a) a class has an attribute of
	 * type T, b) the class implements T (explicitly), and c) the methods of the
	 * class (corresponding to type T) call the corresponding methods on the
	 * attribute
	 *
	 * @param jProject
	 * @param types
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
	 * This function prints the candidate delegation records for the java project
	 * jProject. It prints them by decreasing order of fitness, starting with the
	 * "perfect matches", i.e. cases where the entire domain interface of an
	 * attribute is included in the domain interface of the enclosing class.
	 *
	 * @param jProject
	 * @param delegationRecords
	 *
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
			out.close();
		} catch (FileNotFoundException fnfe) {
			System.out.println("Cannot open file: " + outputFileName);
			fnfe.printStackTrace();
		}
	}

	/**
	 * this method compares the domain interfaces of a field with that of the
	 * enclosing class. It at least one method of the field is included in the
	 * domain interface of the enclosing type, we have a case of delegation. If we
	 * find more methods, we have "stronger" candidate, with the strongest one when
	 * *all* of the methods of the field are supported by the enclosing type.
	 *
	 * @param fieldDomainInterface
	 * @param typeDomainInterface
	 * @param jProject
	 * @param allTypes
	 *
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
	 * Example of utility methods: 1) things inherited from Object (hash(), clone(),
	 * etc.) 2) getters and setters of specific attributes
	 *
	 * We assume that everything else is domain method.
	 *
	 * A simple heuristic to find domain methods would start from the
	 * <code>argument</code>, climb the type hierarchy, collect all the methods
	 * gathered from types along the way to the root <i>that are included within the
	 * project</i>, i.e. included in <code>allProjectTypes</code>. This presents a
	 * problem with projects that include external domain code (from other parent
	 * open source projects) as jar files. Those "domain types" won't show up in
	 * <code>allProjectTypes</code>. In such a case, we can use an explicit
	 * exclusion list that filters out "java" or "non-domain types" from the type
	 * hierarchy (things in java.lang or java.util, etc.).
	 *
	 * Will see ...
	 *
	 * @param jProject        : the containing project/software
	 * @param allProjectTypes : the types included/defined in the project. Makes
	 *                        certain things easier
	 * @param type            : the type for which we want to compute the domain
	 *                        interface
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
		// Collection<IMethod> nonDomainMethods = new ArrayList<IMethod>();

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
				boolean nonVoidReturnType = (!next.getReturnType().equalsIgnoreCase("void"));

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
			e.printStackTrace();
		}
		return domInterface;
	}

	/**
	 * This method processes a collection of
	 * <code>MultipleInheritanceRecord</code>s. For the time being, all it does is
	 * print the extended types and the implemented types
	 *
	 * @param jProject
	 * @param candidates
	 *
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
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("Cannot open file: ");
			e.printStackTrace();
		}
	}

	/**
	 * Selection in the workbench has been changed. We can change the state of the
	 * 'real' action here if we want, but this can only happen after the delegate
	 * has been created.
	 * 
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system resources we previously
	 * allocated.
	 * 
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to be able to provide parent shell for
	 * the message dialog.
	 * 
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	/**
	 * removes non candidates node from nodes to d
	 *  
	 * @param nodes is a list that represents the graph that contains a copy of the lattice to display in the view lattice graph
	 * @return
	 * by (imen)
	 */
    private List<Node> RemoveNonCandidateNodes(List<Node> nodes, Lattice aLattice ) {
    	
    
		
		
		
		//there are nodes in the lattice that have two copies in the lattice : a copy as candidate and a copy as no candidate
		//this error is found when I displayed the lattice
		//this loop transform no candidates to candidates and assign to them the type of the candidate copy 
		for (int i = nodes.size()-1; i>0; i--) {
			List<Node> children = nodes.get(i).getChildren();
			for (Node child : children) {
				int indx = nodes.indexOf(child);
				
				if (indx>0) {
					child.setTypes(nodes.get(indx).getTypes()); 
				}
			}
		}		
		
//		
//		
//		
//		
		//for each mode if one child is non candidate assign his children to his parents 		
		List<Node> nodesToRemove = new ArrayList<Node>();
		for (int i = nodes.size()-1; i>=0; i--) {
			List<Node> children = nodes.get(i).getChildren();
			for (Node child : children) {
				if (child.getTypes().isEmpty() ) {
					List<Node> childrenOfChild = child.getChildren();
					children.addAll(childrenOfChild);			
				}
			}
		}
//		
//		
//	
//		//remove no candidate nodes from the list 
		for (Node node : nodes) {
			//System.out.println(" node" + node.getName() + "--" + node.getType());
		
			if (node.getTypes().isEmpty()  && node.getName() != null) {
				//System.out.pr0intln("removed node" + node.getName() + "--" + node.getType());
				nodesToRemove.add(node);			}
		}		
		nodes.removeAll(nodesToRemove);
		// remove children of the non candidate nodes 		
		for (Node node : nodes) {
			List<Node> children = node.getChildren();
			List<Node> removedChildren = new ArrayList<Node>();
			for (Node child : children) {
				//System.out.println(" child: " + child.getName() + "-- " +child.getType()+ "parent" + node.getName());			
				if (child.getTypes().isEmpty() ) {
					System.out.println("remove child: " + child.getName() + "-- " +child.getTypes()+ "parent" + node.getName());
					removedChildren.add(child);
				}
			}children.removeAll(removedChildren);
		}	
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			Writer writer = new FileWriter(project.getLocation().toString()+"/"+project.getName()+"_"+"lattice.json");
			gson.toJson(nodes,writer);
			writer.flush();    
		    writer.close(); 
		} catch (JsonIOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			Writer writer = new FileWriter(project.getLocation().toString()+"/"+project.getName()+"_"+"latticexx.json");
			
			gson.toJson(aLattice.getTop(),writer);
			writer.flush();    
		    writer.close(); 
		} catch (JsonIOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		 
	        
	        
		
		FileOutputStream fos;
		ObjectOutputStream oos;
		try {
			fos = new FileOutputStream(project.getLocation().toString()+"/"+project.getName()+"_"+"lattice.ltc");
			oos = new ObjectOutputStream(fos);
			oos.writeObject(nodes);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		
		System.out.println("**********************************print nodes ");
		for (Node node : nodes) {
			System.out.println("******************************");
			System.out.println(node.toString());
			System.out.println("******************************");
		}
		
		
		return nodes;
	}
    
  private int getTotalNumberOfCandidateNodes(List<Node> nodes ) {
    	   
   return nodes.size();	
    
  }
  private int getTotalNumberOfAdhocNodes(List<Node> nodes ) {
	   int numberAdhocs = -1; // to ignore the root of the lattice that is an empty node
	  for (Node node : nodes) {
		  if (node.getTypes()!=null && !node.getTypes().isEmpty()) {
			if (node.getTypes().get(0).toString().contains("ADHOC"))
				numberAdhocs ++;
			}
	  }
	  return numberAdhocs;
}
  
  private int getTotalNumberOfInterfaceNodes(List<Node> nodes ) {
	   int numberInterface = 0;
	  for (Node node : nodes) {
		  if (node.getTypes()!=null && !node.getTypes().isEmpty()) {
			if (node.getTypes().get(0).toString().contains("INTERFACE"))
				numberInterface ++;
			}
	  }
	  return numberInterface;
}
  private int getTotalNumberOfSubclassNodes(List<Node> nodes ) {
	   int numberSubclass = 0;
	  
	  for (Node node : nodes) {
		  if (node.getTypes()!=null && !node.getTypes().isEmpty()) {
			if (node.getTypes().get(0).toString().contains("CLASS_SUBCLASS"))
				numberSubclass ++;
			}
	   }
	  return numberSubclass;
}
  private int getTotalNumberOfAggregationNodes(List<Node> nodes ) {
		int numberAggregations = 0;
		 
	  for (Node node : nodes) {
		  if (node.getTypes()!=null && !node.getTypes().isEmpty()) {
			if (node.getTypes().get(0).toString().contains("AGGREGATIONS"))
				numberAggregations ++;
			}
		 }
	  return numberAggregations;
}

  
  private String generateStatistics(List<Node> nodes ) {
	  
	  String total = "#nodes : " + getTotalNumberOfCandidateNodes(nodes);
	  String adhoc = "#adhoc : " + getTotalNumberOfAdhocNodes(nodes);
	  String subclass = "#subclass : " + getTotalNumberOfSubclassNodes(nodes);
	  String interfaces = "#interface : " + getTotalNumberOfInterfaceNodes(nodes);
	  String aggregation = "#aggregations : " + getTotalNumberOfAggregationNodes(nodes);
	  
	  return total+ "\n"+adhoc+ "\n"+subclass+ "\n"+interfaces+ "\n"+aggregation+ "\n";
  }
  
  private void generateStaticsFile(List<Node> nodes ) {
	  try {
		  
		  FileWriter myWriter = new FileWriter(project.getLocation().toString()+"/"+project.getName()+"_"+"latticeStatistics.txt");
	      myWriter.write(generateStatistics(nodes));
	      myWriter.close();
	    	      
	    } catch (IOException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
  }
}