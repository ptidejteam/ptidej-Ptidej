package ca.uqam.latece.aspects.extractor.lattice.visitors.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeHierarchy;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;



import ca.uqam.latece.aspects.extractor.input.impl.ReverseInheritanceRelationBuilder;
import ca.uqam.latece.aspects.extractor.lattice.model.LatticeNode;
import ca.uqam.latece.aspects.extractor.lattice.visitors.Visitor;

public class FeatureDetectorVisitor extends AbstractVisitor implements Visitor {
	/**
	 * we need a reference to the builder that built the relation to have access to
	 * the Java project when we compute types of fields
	 */
	private ReverseInheritanceRelationBuilder relationBuilder;

	public static enum FeatureTypeName {
		ADHOC, FULL_EXTENT_FULL_BEHAVIOR_EXPLICIT_INTERFACE_IMPLEMENTATIONS, PARTIAL_EXTENT_FULL_BEHAVIOR_EXPLICIT_INTERFACE_IMPLEMENTATIONS, FULL_EXTENT_PARTIAL_BEHAVIOR_EXPLICIT_INTERFACE_IMPLEMENTATIONS, PARTIAL_EXTENT_PARTIAL_BEHAVIOR_EXPLICIT_INTERFACE_IMPLEMENTATIONS, FULL_EXTENT_FULL_BEHAVIOR_EXPLICIT_AGGREGATIONS, PARTIAL_EXTENT_FULL_BEHAVIOR_EXPLICIT_AGGREGATIONS, FULL_EXTENT_PARTIAL_BEHAVIOR_EXPLICIT_AGGREGATIONS, PARTIAL_EXTENT_PARTIAL_BEHAVIOR_EXPLICIT_AGGREGATIONS, FULL_EXTENT_FULL_BEHAVIOR_EXPLICIT_CLASS_SUBCLASS_REDEFINITIONS, PARTIAL_EXTENT_FULL_BEHAVIOR_EXPLICIT_CLASS_SUBCLASS_REDEFINITIONS, FULL_EXTENT_PARTIAL_BEHAVIOR_EXPLICIT_CLASS_SUBCLASS_REDEFINITIONS, PARTIAL_EXTENT_PARTIAL_BEHAVIOR_EXPLICIT_CLASS_SUBCLASS_REDEFINITIONS
	}

	/**
	 * a class to represent the classification of a node. There are five different
	 * classes, enumerated in FeatureTypeTag. Depending on the classification, we
	 * may want to represent additional classification data.
	 * 
	 * For AD-HOC, nothing to add
	 * 
	 * For FULL_EXTENT_*_BEHAVIOR_EXPLICIT_INTERFACE_IMPLEMENTATIONS,
	 * FULL_EXTENT_*_BEHAVIOR_EXPLICIT_AGGREGATIONS, and
	 * FULL_EXTENT_*_BEHAVIOR_EXPLCIT_CLASS_SUBCLASS_REDEFINITIONS we want to
	 * represent the interface (anchorType) or the component class (for aggregation)
	 * which could be an interface
	 * 
	 * For PARTIAL_EXTENT_*_BEHAVIOR_*, we not only represent the anchorType, but we
	 * also represent the classes that implement the classes that are concerned by
	 * the pattern, which constitute a subset of the extent.
	 * 
	 * For *_EXTENT_PARTIAL_BEHAVIOR_*, we represent the percentage of methods of
	 * the anchorType that are in the intent
	 * 
	 * Note that the same node may qualify for several classifications. For this
	 * reason, we collect all of the classifications. The analyst will figure out
	 * what to do with those
	 * 
	 * @author Hafedh
	 *
	 */

	public static LatticePrettyPrinter.ElementPrinter printer = LatticePrettyPrinter.JAVA_ELEMENT_PRINTER;

	public static class FeatureTypeTag implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 8598660777814818523L;

		public FeatureTypeName name;

		public float configurationBehaviorCoverage;

		public float anchorTypeBehaviorCoverage;

		public IType anchorType;

		public Collection<IType> relatedTypes;

		public FeatureTypeTag(FeatureTypeName aName) {
			name = aName;
			configurationBehaviorCoverage = 0;
			anchorTypeBehaviorCoverage = 0;
		}

		private FeatureTypeTag(FeatureTypeName aName, IType anchor) {
			this(aName);
			anchorType = anchor;
		}

		public FeatureTypeTag(FeatureTypeName aName, IType anchor, float anchorTypeBcoverage) {
			this(aName, anchor);
			anchorTypeBehaviorCoverage = anchorTypeBcoverage;
		}

		private FeatureTypeTag(FeatureTypeName aName, IType anchor, Collection<IType> types) {
			this(aName, anchor);
			relatedTypes = types;
		}

		public FeatureTypeTag(FeatureTypeName aName, IType anchor, float anchorTypeBcoverage, Collection<IType> types,
				float configBcoverage) {
			this(aName, anchor, types);
			anchorTypeBehaviorCoverage = anchorTypeBcoverage;
			configurationBehaviorCoverage = configBcoverage;
		}

		public String toString() {
			String output = name.toString();

			// if it has an anchor, add it
			if (anchorType != null) {
				output = output + "; ANCHOR: [" + printer.printExtentElement(anchorType) + "]";
				output = output + "; ANCHOR TYPE BEHAVIOR COVERAGE: [" + anchorTypeBehaviorCoverage + " ]";
			}

			// if it has a non-zero intent coverage, print it
			if (configurationBehaviorCoverage > 0)
				output = output + "; CONFIGURATION BEHAVIOR COVERAGE: [ " + configurationBehaviorCoverage + " ]";

			// if it has related type, add them
			if (relatedTypes != null)
				output = output + "; RELATED TYPES: " + printStringRelatedTypes();

			// return output
			return output;

		}

		/**
		 * 
		 * @return
		 */
		private String printStringRelatedTypes() {
			SortedSet<String> printStrings = new TreeSet<String>();
			for (Object object : relatedTypes) {
				printStrings.add(printer.printExtentElement(object));
			}
			return printStrings.toString();
		}

	}

	public static class FeatureType implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 2174048266250283743L;
		Collection<FeatureTypeTag> featureTags = null;

		public FeatureType() {
			featureTags = new ArrayList<FeatureTypeTag>();
		}

		public FeatureType(FeatureTypeTag tag) {
			this();
			featureTags.add(tag);
		}

		public String toString() {
			// iterate over the tags, putting the full tags first
			String output = "";
			for (FeatureTypeTag tag : featureTags) {
				// put the tags that cover the full extent first
				if (tag.name.toString().startsWith("FULL_EXTENT")) {
					output = tag.toString() + "##" + output;

				} else {
					// simply concatenate
					output = output + " ## " + tag.toString();
				}
			}

			return output;
		}
	}

	/**
	 * structure to store the candidate features
	 */
	private HashMap<LatticeNode, FeatureType> candidateFeatureNodes = new HashMap<LatticeNode, FeatureType>();

	private LatticeNode predecessor;

	public FeatureDetectorVisitor(ReverseInheritanceRelationBuilder builder) {
		relationBuilder = builder;
	}

	@Override
	public void processNode(LatticeNode node) {
		int currentNodeExtentSize = node.getExtent().size();
		if (currentNodeExtentSize > 1) {
			// if the node has an extent bigger than 1, it means that we have a
			// case of two or more independent occurrences of the same feature
			// (intent).
			// However, we know that if a feature (a set of methods) occurred in
			// more than two places, all
			// of its subsets will also have occurred in those places. Thus, if
			// we have features F1 < F2 < F3
			// which occurred in the same places, we only want to keep F3. I.e.,
			// for a given number of
			// occurrences, we can to keep the largest feature
			// with this node, we know that its children will necessarily have
			// bigger intents (more attributes).
			// If one of its children has the same extent size (same number of
			// independent occurrences) as the
			// current node, it means that the current node represents a subset
			// of a bigger feature, and we won't
			// consider it as a candidate feature
			boolean isCandidate = true;
			for (LatticeNode child : node.getChildren()) {
				if (child.getExtent().size() == currentNodeExtentSize) {
					isCandidate = false;
				}
			}
			// if it survived the scan AND the intent is not empty, then it is a
			// candidate
			// call classifyFeature to figure out what type it is
			if (isCandidate && !node.getIntent().isEmpty())
				addCandidateFeatureNode(node, classifyFeature(node));
		}
	}

	/**
	 * for the time being, we distinguish between four types: 1)
	 * <code>EXPLICIT_INTERFACE_IMPLEMENTATIONS</code>: corresponds to the case
	 * where one element of the extent is an interface, and the others are its
	 * implementations, 2) <CODE>EXPLICIT_CLASS_SUBCLASS_REDEFINITIONS</CODE>, which
	 * is the case where we have an abstract class and its subclasses, or simply, a
	 * class that defines a default behaviour, that is overridden by subclasses, 3)
	 * <code>EXPLICIT_AGGREGATIONS</code>: corresponds to the case where one element
	 * of the extent is a class or an interface, and the others are classes having
	 * that element as a data member, and delegating some of its domain methods, and
	 * 4) <code>ADHOC</code>, which represents cases where the similarity was NOT
	 * seen/detected by the developer, or captured using ANY of the techniques
	 * above. The "value" of our method is to detect THESE cases (AD-HOC).
	 * 
	 * The first three cases were supposed to be recognised by the other algorithm
	 * 
	 * For the first three cases, we have PARTIAL_* variants, where a SUBSET of the
	 * extent fits the pattern. For example
	 * PARTIAL_EXPLICIT_INTERFACE_IMPLEMENTATIONS where one element extent is an
	 * interface and AT LEAST another member is an implementation, and
	 * PARTIAL_EXPLICIT_AGGREGATIONS where one element of the extent is class or
	 * interface and AT LEAST another member is an aggregate.
	 * 
	 * @param candidateFeature
	 * @return
	 */
	protected FeatureType classifyFeature(LatticeNode candidateFeatureNode) {
		// 0. initialize return value
		FeatureType featureType = new FeatureType();

		// 1. first, check for interface implementations

		findInterfaceImplementationTags(candidateFeatureNode, featureType);

		// 2. second, check for class/subclass redefinitions

		findClassSubclassRedefinitionTags(candidateFeatureNode, featureType);

		// 3. third, check for aggregations
		findAggregationTags(candidateFeatureNode, featureType);

		// 3. If no other tags have been assigned to this node, that means that
		// it is an ADHOC case
		if (featureType.featureTags.isEmpty())
			featureType.featureTags.add(new FeatureTypeTag(FeatureTypeName.ADHOC));

		return featureType;
	}

	/**
	 * The way we do this: 1) for each type in the extent, 1.1) find the member
	 * types that are in the extent. 1.2) fill the inverted list of member types =>
	 * aggregates 2) iterate over the types => aggregates table and 3.1) add
	 * AGGREGATIONS tag for each 3.1.a) if the number of aggregates >= extent size -
	 * 1, then we have FULL_EXTENT_*_BEHAVIOR_EXPLICIT_AGGREGATTIONS, 3.1.b) if the
	 * number of aggregates is smaller than extent size - 1, then it is a
	 * PARTIAL_EXTENT_*_BEHAVIOR_EXPLICIT_AGGREGATIONS
	 * 
	 * The distinction between *_FULL_BEHAVIOR_* and *_PARTIAL_BEHAVIOR* depends on
	 * whether the intent covers the shared between a component and its aggregates,
	 * in its entirety, or not.
	 * 
	 * The shared behavior between a component and its aggregates is computed using
	 * the method commonBehaviorBetweenAnchorAndRelatedTypes(...)
	 * 
	 * @param candidateFeatureNode
	 * @param featureType
	 */
	protected void findAggregationTags(LatticeNode candidateFeatureNode, FeatureType featureType) {

		Set<Object> intersection = null, extent = candidateFeatureNode.getExtent();

		ArrayList<Object> typesToProcess = new ArrayList<Object>();
		typesToProcess.addAll(extent);

		HashMap<IType, Set<IType>> memberTypes = new HashMap<IType, Set<IType>>(),
				typesToAggregates = new HashMap<IType, Set<IType>>();

		// first, create a type hierarchy to get the inheritance relationships
		IProgressMonitor pMonitor = new NullProgressMonitor();
		ITypeHierarchy typeHierarchy = null;

		// 1) for each type in the extent,
		while (!typesToProcess.isEmpty()) {
			try {
				// first remove first element from classesToProcess
				IType nextType = (IType) typesToProcess.remove(0);

				// 1.1) find the member types that are in the extent.

				// 1.1.a) Get its fields
				IField[] itsFields = nextType.getFields();

				Set<IType> itsMemberTypes = new HashSet<IType>();
				for (IField field : itsFields) {
					// get field type
					IType fieldType = getFieldType(nextType, field);
					if (fieldType != null)
						itsMemberTypes.add(fieldType);
				}

				// 1.1.b) do the intersection between the field types and extent
				itsMemberTypes.retainAll(extent);

				// 1.2) fill the inverted list of member types => aggregates
				for (IType memberTypeInExtent : itsMemberTypes) {
					// 1.2.a) get the set of aggregates that are in the extent,
					// for memberTypeInExtent
					Set<IType> aggregateSet = typesToAggregates.get(memberTypeInExtent);
					// 1.2.b) if the set is empty, then nextType is the first
					// aggregate found for memberTypeInExtent
					// so create the set of aggregates from scratch
					if (aggregateSet == null) {
						aggregateSet = new HashSet<IType>();
						typesToAggregates.put(memberTypeInExtent, aggregateSet);
					}
					aggregateSet.add(nextType);
				}
			} catch (JavaModelException jme) {
				jme.printStackTrace();
			}
		}

		// 2) iterate over the types => aggregates table and add AGGREGATIONS
		// tag for each
		for (IType componentType : typesToAggregates.keySet()) {
			Set<IType> setOfAggregates = typesToAggregates.get(componentType);

			// get the common behavior between component and aggregates
			Set<IMethod> commonBehavior = commonBehaviorBetweenAnchorAndRelatedTypes(componentType, setOfAggregates);
			boolean fullBehavior = commonBehavior.size() == candidateFeatureNode.getIntent().size();
			float configurationBehaviorCoverage = ((float) candidateFeatureNode.getIntent().size())
					/ commonBehavior.size();
			float anchorTypeBehaviorCoverage = ((float) candidateFeatureNode.getIntent().size())
					/ relationBuilder.getLocalDomainInterfaces().get(componentType).length;
			FeatureTypeTag tag = null;

			if (setOfAggregates.size() >= extent.size() - 1) {
				// 3.1.a) if the number of aggregates = extent size - 1, then we
				// have FULL_EXTENT_FULL_BEHAVIOR_EXPLICIT_AGGREGATIONS. We just need to worry
				// about
				// how much of the behavior of the component is covered by the intent
				tag = new FeatureTypeTag(FeatureTypeName.FULL_EXTENT_FULL_BEHAVIOR_EXPLICIT_AGGREGATIONS, componentType,
						anchorTypeBehaviorCoverage);
			} else {
				// 3.1.b) if the number of aggregates is smaller than extent
				// size - 1, then
				// it is a PARTIAL_EXTENT_*_EXPLICIT_AGGREGATIONS
				if (fullBehavior)
					tag = new FeatureTypeTag(FeatureTypeName.PARTIAL_EXTENT_FULL_BEHAVIOR_EXPLICIT_AGGREGATIONS,
							componentType, anchorTypeBehaviorCoverage, setOfAggregates, 1.0f);
				else
					tag = new FeatureTypeTag(FeatureTypeName.PARTIAL_EXTENT_PARTIAL_BEHAVIOR_EXPLICIT_AGGREGATIONS,
							componentType, anchorTypeBehaviorCoverage, setOfAggregates, configurationBehaviorCoverage);
			}
			featureType.featureTags.add(tag);
		}
	}

	/**
	 * This function returns the common methods between an 'anchor type' and a set
	 * of types. This is needed to help characterise some 'configurations' of
	 * classes that may occur within the extent of a node. For example, the extent
	 * of a node may contain an interface and a (sub)set of its implementations. We
	 * should NOT presume that the intent of the candidate node consists of the set
	 * of methods of the interface. Actually, most likely, a specific configuration
	 * made into the extent because the share behaviour between
	 * <code>componentType</code> and <code>relatedTypes<code/> is a STRICT SUPERSET
	 * of the intent. In any event, it is the job of the calling method
	 * (findClassSubclassRedefinitionTags(...), findAggregationTags(...),
	 * findInterfaceImplementationTags(...)) to make that analysis to assign the
	 * appropriate tag (with *_FULL_BEHAVIOR_* or *_PARTIAL_BEHAVIOR_*).
	 * 
	 * The question raised by this computation: should I use the intersection of
	 * local domain interfaces, or of cumulative domain interfaces. After some
	 * thought, the answer is as follows: 1) for *_EXPLICIT_INTERFACE_IMPLEMENTATION
	 * and *_EXPLICIT_CLASS_SUBCLASS_REDEFINITIONS, the answer is "clear": we should
	 * use the local domain interface of the anchor type (the interface being
	 * implemented or the class being subclassed), because, a) since the anchor type
	 * was NOT purged, we know that its local interface, ALONE, contains the intent,
	 * and b) with interfaces, the cumulative domain interface will include the
	 * interfaces of the classes implementing the interface, and we don't want
	 * those. As for the related types, we should stick with the reverse inheritance
	 * relationship, and use the cumulative domain interfaces 2) for
	 * *_EXPLICIT_AGGREGATIONS, we should also use the local interface for the
	 * component because "reverse-inherited" could not be delegated to (they are not
	 * technically part of the API of the component), and won't show up anyway.
	 * 
	 * The two choices can be debated. But for now, that is what is being done
	 * 
	 * @param componentType
	 * @param relatedTypes
	 * @return
	 */
	protected Set<IMethod> commonBehaviorBetweenAnchorAndRelatedTypes(IType componentType, Set<IType> relatedTypes) {
		Set<IMethod> commonMethods = new HashSet<IMethod>();
		// 1. first, initialize the set with the local domain interface of componentType
		commonMethods.addAll(Arrays.asList(relationBuilder.getLocalDomainInterfaces().get(componentType)));

		// 2. Then, iterate over the relatedTypes, and for each related type, compute
		// the intersection
		// between commonMethods and the cumulative domain interface of the related type
		for (IType relatedType : relatedTypes) {
			commonMethods.retainAll(Arrays.asList(relationBuilder.getSubhierarchyDomainInterfaces().get(relatedType)));
		}
		// what remains in commonMethods in the desired intersection
		return commonMethods;
	}

	/**
	 * This function takes as arguments a type called <code>context</code> and the
	 * name of an unresolved type <code>unresolvedTypeName</code>, and returns the
	 * name of the corresponding type, which is fully resolved. For example, if
	 * <code>unresolvedTypeName</code> is "Customer", and the file containing
	 * <code>context</code> had import ca.uqam.Customer, this method will return an
	 * array consisting of the different possible fully qualified names for
	 * "Customer", each as an array of strings representing the different packages
	 * and the classname. In this case, it will return {{"ca", "uqam", "Customer"}}.
	 * If there are several possible types called "Customer", for example,
	 * ca.uqam.Customer and sun.java.Customer, then the method returns
	 * {{"ca"."uqam","Customer"},{"sun","java","Customer"}}.
	 * 
	 * For the simple cases such as above, this method calls
	 * IType.resolveType(String unresolvedTypeName)
	 * 
	 * If the unresolved type is a parameterized type, where one of the type
	 * parameters is unresolved, then we need to resolve the type parameters, FIRST,
	 * before we resolve the parameterized type.
	 * 
	 * Example: unresolvedTypeName = HashMap<QString;QSet<QEnum;>;>
	 * 
	 * In this, we start with the innermost type parameter, which is Enum. We first
	 * resolve Enum using IType.resolveType(), which will return by java.util.Enum.
	 * 
	 * Next, we need to resolve QSet<java.util.Enum>, and QString, using
	 * IType.resolveTtype, which returns java.util.Set<java.util.Enum> and
	 * java.lang.String
	 * 
	 * Now, we can resolve HashMap<java.lang.String,java.util.Set<java.util.Enum>>
	 * using, again IType.resolveType, which will return
	 * java.util.HashMap<java.lang.String,java.util.Set<java.util.Enum>> then the
	 * resolution
	 * 
	 * @param context:
	 *            the IType within the context of which we will resolve the type
	 *            described by unresolvedTypeName
	 * @param unresolvedTypeName
	 * @return
	 * @throws JavaModelException
	 */

	/*
	 * protected String[][] resolveType(IType context,String unresolvedTypeName)
	 * throws JavaModelException { String[][] potentialTypes = null; if
	 * (unresolvedTypeName.indexOf(Signature.C_GENERIC_START)==0) { // cas de type
	 * non-parametre return context.resolveType(unresolvedTypeName); } else { // we
	 * need to resolve the type parameters before we resolve the whole
	 * 
	 * // first, build the type parameter tree recursively ParameterizedType root =
	 * buildParameterizedType(unresolvedTypeName);
	 * 
	 * // second, resolve the type parameter tree doing a depthfirst search return
	 * resolveTypeParameterTree(root); }
	 * 
	 * return potentialTypes; }
	 * 
	 */

	protected String[][] resolveType(IType context, String unresolvedTypeName) throws JavaModelException {

		Parser parser = new Parser();
		PrettyPrinter printer = new PrettyPrinter(false, false);
		TypeResolver resolver = new TypeResolver(context);
		Node root;

		try {
			root = parser.parse(unresolvedTypeName, false);
		} catch (Exception e) {
			return null;
		}

		try {
			resolver.visit(root);
		} catch (Exception e) {
			return null;
		}

		try {
			printer.visit(root);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String qualifiedUnresolvedTypeName = printer.getOutput();
		return context.resolveType(qualifiedUnresolvedTypeName);

	}

	/**
	 * this method returns the IType that corresponds to the type of an IField of
	 * another IType (aggregate). Recall that JDT represents code and NOT class
	 * files. Hence, a field is characterised SYNTACTICALLY through its 'text'/code,
	 * and not in a compiled form. Thus, the ITYpe of the field needs to be
	 * determined from its textual representation. For example, you declare a field
	 * of type "String". It takes some doing to associate that with the java class
	 * "java.lang.String". You do that by looking for all types that are called
	 * "String" within your scope (depending on what you imported and what different
	 * String types are available). That is the business with resolved versus
	 * unresolved types. Thus, initially, the type of the field is represented
	 * (Signature) by a string that says that the type is unresolved (which is the
	 * default mode for source code). Hence the complications illustrated here.
	 * 
	 * The only thing I need to double check: this function, copied after a similar
	 * function developed for the other part of the research (detecting instances of
	 * multiple inheritance and aggregation), makes no provision for the case where
	 * the field type IS already resolved. The documentation does say that types are
	 * unresolved for source code, but here the projects will contain both source
	 * files and class files, and I am not clear on whether class files are analyzed
	 * or not. Thus, I am not 100% sure that field types will ALWAYS show-up as
	 * unresolved
	 * 
	 * @param aggregate
	 * @param aField
	 * @return
	 * @throws JavaModelException
	 */
	protected IType getFieldType(IType aggregate, IField aField) throws JavaModelException {
		IProgressMonitor pMonitor = new NullProgressMonitor();
		String typeSignature = aField.getTypeSignature();
		String fieldName = aField.getElementName();
		IType fieldType = null;
		// if the field has class type then we analyse, else (base type, array
		// type, type variable,
		// wildcard type, or 'capture type'), skip because, a) it is unlikely
		// that such a field would
		// be involved in a delegation, and b) we would not know how to deal
		// with it, anyway :-)
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
				// System.out.println("\tAttribute :" + fieldName + " with type
				// signature: " + typeSignature
				// + " and element type: " + unresolvedTypeName);
				// a type name may resolve to different types (which
				// normally leads to a syntax error).
				// String[][] candidateTypeNames = aggregate.resolveType(unresolvedTypeName);
				String[][] candidateTypeNames = resolveType(aggregate, unresolvedTypeName);
				if (candidateTypeNames != null) {
					if (candidateTypeNames.length != 1) {
						System.out.println("Cannot resolve type: " + unresolvedTypeName + " of  field " + fieldName
								+ " within the context of: " + aggregate.getElementName());
					} else {
						// this is the case where we have a single choice: that
						// is the valid type name (the
						// element [0][0] represents the package, and [0][1]
						// represents the class name)
						resolvedFieldTypeName = candidateTypeNames[0][0] + "." + candidateTypeNames[0][1];
						// now, we get the IType of the field
						fieldType = relationBuilder.getJavaProject().findType(resolvedFieldTypeName, pMonitor);
					}
				}
			} else {
				System.out.println("Gee, the type signature " + elementType + " of field " + aField.getElementName()
						+ " of class " + aggregate.getFullyQualifiedName() + " showed up as resolved!");
			}

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
				System.out.println(
						"Could not find IType for :" + resolvedFieldTypeName + ". Skipping the field: " + fieldName);
			}
		}
		return fieldType;
		// fix this, I don't trust it
	}

	/**
	 * 
	 * @param candidateFeatureNode
	 * @param featureType
	 */
	protected void findInterfaceImplementationTags(LatticeNode candidateFeatureNode, FeatureType featureType) {

		Set<Object> intersection = null, extent = candidateFeatureNode.getExtent();

		ArrayList<Object> typesToProcess = new ArrayList<Object>();
		typesToProcess.addAll(extent);

		// first, create a type hierarchy to get the inheritance relationships
		IProgressMonitor pMonitor = new NullProgressMonitor();
		ITypeHierarchy typeHierarchy = null;

		// while there are still classes to process from the extent
		while (!typesToProcess.isEmpty()) {

			// first remove first element from classesToProcess
			IType nextType = (IType) typesToProcess.remove(0);

			try {
				typeHierarchy = nextType.newTypeHierarchy(pMonitor);

				// if nextType is an interface
				if (nextType.isInterface()) {
					// then check if the remainder of the of the extent is ALL
					// implementations
					// of next class
					List<IType> listOfItsImplementations = Arrays
							.asList(typeHierarchy.getImplementingClasses(nextType));
					Set<IType> setOfItsImplementations = new HashSet<IType>();
					setOfItsImplementations.addAll(listOfItsImplementations);

					// now, I need to add the subclasses of the classes that
					// directly implement the interface, and include
					// those in the analysis
					for (IType implementingClass : listOfItsImplementations) {
						setOfItsImplementations.addAll(Arrays.asList(typeHierarchy.getAllSubtypes(implementingClass)));
					}

					// to see if the extent consists of nextClass + a (sub)set
					// of its implementations, we check the size
					// of the intersection of setOfItsImplementations with the extent

					// now the intersection
					setOfItsImplementations.retainAll(extent);

					// now, depending on the sizes,
					if (setOfItsImplementations.size() >= 1) {
						// this is the case where the extent contains AT LEAST
						// one implementation of the interface nextType
						// Thus, we have *_EXTENT_*_BEHAVIOR_EXPLICIT_INTERFACE_IMPLEMENTATIONS

						// first, get the common behavior between nextType and its implementations
						Set<IMethod> commonBehavior = commonBehaviorBetweenAnchorAndRelatedTypes(nextType,
								setOfItsImplementations);
						boolean fullBehavior = commonBehavior.size() == candidateFeatureNode.getIntent().size();
						float configurationBehaviorCoverage = ((float) candidateFeatureNode.getIntent().size())
								/ commonBehavior.size();
						float anchorTypeBehaviorCoverage = ((float) candidateFeatureNode.getIntent().size())
								/ relationBuilder.getLocalDomainInterfaces().get(nextType).length;

						if (setOfItsImplementations.size() == extent.size() - 1) {
							// we have a case of
							// FULL_EXTENT_FULL_BEHAVIOR_EXPLICIT_INTERFACE_IMPLEMENTATIONS
							// we just need to specify how much of the API/behavior of the interface
							// (nextType) is
							// covered by the intent
							featureType.featureTags.add(new FeatureTypeTag(
									FeatureTypeName.FULL_EXTENT_FULL_BEHAVIOR_EXPLICIT_INTERFACE_IMPLEMENTATIONS,
									nextType, anchorTypeBehaviorCoverage));
						} else {
							if (fullBehavior)
								featureType.featureTags.add(new FeatureTypeTag(
										FeatureTypeName.PARTIAL_EXTENT_FULL_BEHAVIOR_EXPLICIT_INTERFACE_IMPLEMENTATIONS,
										nextType, anchorTypeBehaviorCoverage, setOfItsImplementations, 1.0f));
							else
								featureType.featureTags.add(new FeatureTypeTag(
										FeatureTypeName.PARTIAL_EXTENT_PARTIAL_BEHAVIOR_EXPLICIT_INTERFACE_IMPLEMENTATIONS,
										nextType, anchorTypeBehaviorCoverage, setOfItsImplementations,
										configurationBehaviorCoverage));
						}

					}
				}
			} catch (JavaModelException jme) {
				jme.printStackTrace();
			}
		}
	}

	/**
	 * This method looks for extents that look like {ClassA, SubclassB, SubclassC,
	 * SubclassD}, where ClassA survived the 'purge'. Remember that candidate
	 * features have their extents purged of classes that are NOT minimal with
	 * regard to the extension (or implements) relationship, because such classes
	 * would 'reverse inherit' the feature by virtue of their subclasses, and thus,
	 * should be eliminated. However, the purge keeps some classes that are NOT
	 * minimal IF AND ONLY IF they have their own implementations of the intent. An
	 * example of that is an abstract class with a bunch of abstract methods that
	 * are redefined by its extensions. Or, simply a regular class that defines a
	 * default behaviour, which is then redefined by its subclasses. These should be
	 * counted as multiple occurrences of the feature AND they were recognised as
	 * such by the developer.
	 * 
	 * Again, we have four cases, depending on whether: 1) the full extent is
	 * covered by such configuration: this distinguishes between FULL_EXTENT_* and
	 * PARTIAL_EXTENT 2) the full intent is covered by such configuration: this
	 * distinguishes between *_FULL_BEHAVIOR_* and *_PARTIAL_BEHAVIOR_*
	 * 
	 * For the partial/full intent, we use the intersection of the local domain
	 * interface of the anchorType (classA in the example above) with the cumulative
	 * interfaces of relatedTypes (classes SubclassB, SubclassC, and SubclassD in
	 * our example) as the basis for deciding between *_FULL_BEHAVIOR_* and
	 * *_PARTIAL_BEHAVIOR_*. We know that the intersection in question will be a
	 * SUPERSET of the intent. Thus, we want to know how much of that common
	 * behaviour is included in the intent, i.e. the ratio between the intent size
	 * and the shared behaviour size.
	 * 
	 * @param candidateFeatureNode
	 * @param featureType
	 */
	protected void findClassSubclassRedefinitionTags(LatticeNode candidateFeatureNode, FeatureType featureType) {

		Set<Object> intersection = null, extent = candidateFeatureNode.getExtent();

		ArrayList<Object> typesToProcess = new ArrayList<Object>();
		typesToProcess.addAll(extent);

		// first, create a type hierarchy to get the inheritance relationships
		IProgressMonitor pMonitor = new NullProgressMonitor();
		ITypeHierarchy typeHierarchy = null;

		// while there are still classes to process from the extent
		while (!typesToProcess.isEmpty()) {

			// first remove first element from classesToProcess
			IType nextType = (IType) typesToProcess.remove(0);

			try {
				typeHierarchy = nextType.newTypeHierarchy(pMonitor);

				// build the set of its subtypes that are within the extent
				Set<IType> theSetOfItsSubtypesWithinExtent = new HashSet<IType>();

				// initialize it with the set of its subtypes
				List<IType> itsSubtypes = Arrays.asList(typeHierarchy.getAllSubtypes(nextType));

				if (nextType.isInterface()) {
					// if nextType is an interface, itsSubtypes will include its
					// subinterfaces, but also its implementations. Thus, we need to
					// keep ONLY its subtypes that are interfaces.

					for (IType subType : itsSubtypes) {
						if (subType.isInterface())
							theSetOfItsSubtypesWithinExtent.add(subType);
					}

				} else {
					// if it is a class, then add all of its subtytpes
					theSetOfItsSubtypesWithinExtent.addAll(itsSubtypes);
				}
				// then intersect with the extent
				theSetOfItsSubtypesWithinExtent.retainAll(extent);

				// now, if the intersection is not empty, we have a case of
				// EXPLICIT_CLASS_SUBCLASS_REDEFINITIONS. Depending on the size of the
				// intersection, we have
				// either FULL_EXTENT_* or PARTIAL_EXTENT_*. And depending on the behavior
				// coverage, we
				// have either *_FULL_BEHAVIOR_* or *_PARTIAL_BEHAVIOR_*
				if (!theSetOfItsSubtypesWithinExtent.isEmpty()) {
					// get the common behavior between nextType and theSetOfItsSubtypesWithinExtent
					Set<IMethod> commonBehavior = commonBehaviorBetweenAnchorAndRelatedTypes(nextType,
							theSetOfItsSubtypesWithinExtent);
					boolean fullBehavior = commonBehavior.size() == candidateFeatureNode.getIntent().size();
					float configurationBehaviorCoverage = ((float) candidateFeatureNode.getIntent().size())
							/ commonBehavior.size();
					float anchorTypeBehaviorCoverage = ((float) candidateFeatureNode.getIntent().size())
							/ relationBuilder.getLocalDomainInterfaces().get(nextType).length;

					if (theSetOfItsSubtypesWithinExtent.size() == extent.size() - 1) {

						// 1. FULL_EXTENT_FULL_BEHAVIOR_EXPLICIT_CLASS_SUBCLASS_REDEFINITIONS
						// we just need to specify how much of the behavior of the anchor type
						// is covered by the intent
						featureType.featureTags.add(new FeatureTypeTag(
								FeatureTypeName.FULL_EXTENT_FULL_BEHAVIOR_EXPLICIT_CLASS_SUBCLASS_REDEFINITIONS,
								nextType, anchorTypeBehaviorCoverage));
					} else {
						// 2. PARTIAL_EXTENT_*_EXPLICIT_CLASS_SUBCLASS_REDEFINITIONS
						if (fullBehavior) {
							// 2.a PARTIAL_EXTENT_FULL_BEHAVIOR_EXPLICIT_CLASS_SUBCLASS_REDEFINITIONS
							featureType.featureTags.add(new FeatureTypeTag(
									FeatureTypeName.PARTIAL_EXTENT_FULL_BEHAVIOR_EXPLICIT_CLASS_SUBCLASS_REDEFINITIONS,
									nextType, anchorTypeBehaviorCoverage, theSetOfItsSubtypesWithinExtent, 1.0f));
						} else {
							// 2.b PARTIAL_EXTENT_FULL_BEHAVIOR_EXPLICIT_CLASS_SUBCLASS_REDEFINITIONS
							featureType.featureTags.add(new FeatureTypeTag(
									FeatureTypeName.PARTIAL_EXTENT_PARTIAL_BEHAVIOR_EXPLICIT_CLASS_SUBCLASS_REDEFINITIONS,
									nextType, anchorTypeBehaviorCoverage, theSetOfItsSubtypesWithinExtent,
									configurationBehaviorCoverage));
						}
					}
				}
			} catch (JavaModelException jme) {
				jme.printStackTrace();
			}
		}
	}

	public HashMap<LatticeNode, FeatureType> getCandidateFeatureNodes() {
		return candidateFeatureNodes;
	}

	public FeatureType addCandidateFeatureNode(LatticeNode e, FeatureType type) {
		return candidateFeatureNodes.put(e, type);
	}

	public boolean containsCandidateFeatureNode(LatticeNode o) {
		return candidateFeatureNodes.containsKey(o);
	}

	public FeatureType removeCandidateFeatureNode(LatticeNode o) {
		return candidateFeatureNodes.remove(o);
	}

}
