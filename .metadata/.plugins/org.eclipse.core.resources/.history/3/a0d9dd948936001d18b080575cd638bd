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
package padl.creator.classfile;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.jar.JarInputStream;
import org.apache.commons.lang.ArrayUtils;
import com.ibm.toad.cfparse.ClassFile;
import com.ibm.toad.cfparse.ConstantPool;
import com.ibm.toad.cfparse.ConstantPoolEntry;
import com.ibm.toad.cfparse.InterfaceList;
import com.ibm.toad.cfparse.MethodInfo;
import com.ibm.toad.cfparse.attributes.AttrInfoList;
import com.ibm.toad.cfparse.attributes.CodeAttrInfo;
import com.ibm.toad.cfparse.instruction.ImmutableCodeSegment;
import com.ibm.toad.cfparse.instruction.JVMConstants;
import com.ibm.toad.cfparse.utils.AttrUtils;
import com.ibm.toad.cfparse.utils.ByteArray;
import padl.creator.classfile.relationship.ContainerRelationshipAnalyzer;
import padl.creator.classfile.relationship.Context;
import padl.creator.classfile.relationship.RelationshipAnalyzer;
import padl.creator.classfile.util.ExtendedFieldInfo;
import padl.creator.classfile.util.ExtendedMethodInfo;
import padl.creator.classfile.util.PackageCreator;
import padl.creator.classfile.util.Utils;
import padl.event.AnalysisEvent;
import padl.event.IModelListener;
import padl.kernel.Constants;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IConstituentOfEntity;
import padl.kernel.IConstructor;
import padl.kernel.IContainerAggregation;
import padl.kernel.IDelegatingMethod;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGhost;
import padl.kernel.IInterfaceActor;
import padl.kernel.IMethod;
import padl.kernel.IPackage;
import padl.kernel.IParameter;
import padl.kernel.impl.ConstituentsRepository;
import padl.kernel.impl.Factory;
import padl.util.Util;
import util.io.ProxyConsole;
import util.io.SubtypeLoader;

/**
 * @author Yann-Gaël Guéhéneuc
 */
abstract class AbstractClassFileCreator {
	private static String[] computeMethodCallees(
		final ConstantPool aConstantPool,
		final ExtendedMethodInfo aMethodInfo) {

		final AttrInfoList attributeInfoList = aMethodInfo.getAttributes();
		final CodeAttrInfo codeAttributeInfo =
			(CodeAttrInfo) attributeInfoList.get("Code");
		final List calles = new ArrayList();

		if (codeAttributeInfo != null) {
			final byte[] rawCode = codeAttributeInfo.getCode();
			final ImmutableCodeSegment immutableCodeSegment =
				new ImmutableCodeSegment(rawCode);
			final int numberOfInstructions =
				immutableCodeSegment.getNumInstructions();

			for (int j = 0; j < numberOfInstructions; j++) {
				final int offset = immutableCodeSegment.getOffset(j);
				final int opCode = ByteArray.getByteAtOffset(rawCode, offset);

				switch (opCode) {
					case JVMConstants.INVOKEINTERFACE :
					case JVMConstants.INVOKESPECIAL :
					case JVMConstants.INVOKESTATIC :
					case JVMConstants.INVOKEVIRTUAL :
						{
							int num =
								ByteArray.getShortAtOffset(rawCode, offset + 1);
							ConstantPoolEntry constantPoolEntry =
								aConstantPool.get(num);
							int[] indices = constantPoolEntry.getIndices();
							// TODO: This test should not exist and is there only because of the CFParseBCELConvertor
							if (indices != null) {
								String className =
									aConstantPool.getAsJava(indices[0]);
								String nameAndType =
									aConstantPool.getAsJava(indices[1]);
								calles.add(
									className + '.' + nameAndType.substring(
										0,
										nameAndType.indexOf('.')));
							}
						}
						break;
					default :
						break;
				}
			}
		}

		final String[] arrayOfCallees = new String[calles.size()];
		calles.toArray(arrayOfCallees);
		return arrayOfCallees;
	}

	private final ConstituentsRepository constituentsRepository;
	private final List listOfClassFiles;
	private final Map mapOfIDsEntities;
	private final Map mapOfPackageNamesPackages;
	private final boolean storeMethodInvocation;

	public AbstractClassFileCreator(
		final String[] someClassFiles,
		final boolean recurseIntoDirectories,
		final boolean storeMethodInvocation) {

		this.constituentsRepository = ConstituentsRepository.getInstance();
		this.listOfClassFiles = new ArrayList();
		this.mapOfIDsEntities = new HashMap();
		this.mapOfPackageNamesPackages = new HashMap();
		this.storeMethodInvocation = storeMethodInvocation;

		for (int i = 0; i < someClassFiles.length; i++) {
			// I add this new package to the current AbstractLevelModel model.
			ProxyConsole
				.getInstance()
				.normalOutput()
				.print("Loading class files in: ");
			ProxyConsole
				.getInstance()
				.normalOutput()
				.println(someClassFiles[i]);

			final ClassFile[] classFiles;
			if (someClassFiles[i].endsWith(".jar")) {
				classFiles = SubtypeLoader
					.loadSubtypesFromJar(null, someClassFiles[i], ".class");
			}
			else if (someClassFiles[i].endsWith(".class")) {
				classFiles = SubtypeLoader
					.loadSubtypeFromFile(null, someClassFiles[i], ".class");
			}
			else if (recurseIntoDirectories) {
				// Yann 2004/10/16: Recursion!
				// I add a new flag to know whether or not to recurse
				// into directories when loading class files. I should
				// change this to get as parameter a new type Directory 
				// that would contain the name of a directory and whether
				// to recurse for this paricular directory.
				// TODO: Replace the recurseIntoDirectories with a type Directory
				classFiles = SubtypeLoader.loadRecursivelySubtypesFromDir(
					null,
					someClassFiles[i],
					".class");
			}
			else {
				classFiles = SubtypeLoader
					.loadSubtypesFromDir(null, someClassFiles[i], ".class");
			}

			// @Note David: Is this sorting of classes by the alphabetical order?
			// If it is, why is 
			// Arrays.sort(results, new ClassNameAlphabeticalComparator()); 
			// in SubtypeLoader.loadSubtypesFromJar?

			for (int j = 0; j < classFiles.length; j++) {
				// Yann 2005/09/26: Member classes!
				// I now handle membre classes, thus, I need class files
				// to be sorted by the alphabetical order of their names.
				final String currentClassFileName = classFiles[j].getName();
				final int numberOfClassFiles = this.listOfClassFiles.size();
				int index = 0;
				while (index < numberOfClassFiles
						&& ((ClassFile) this.listOfClassFiles.get(index))
							.getName()
							.compareTo(currentClassFileName) <= 0) {

					index++;
				}
				this.listOfClassFiles.add(index, classFiles[j]);
			}
		}
	}
	public AbstractClassFileCreator(
		final JarInputStream inputStream,
		final boolean storeMethodInvocation) {

		this.constituentsRepository = ConstituentsRepository.getInstance();
		this.listOfClassFiles = new ArrayList();
		this.mapOfIDsEntities = new HashMap();
		this.mapOfPackageNamesPackages = new HashMap();
		this.storeMethodInvocation = storeMethodInvocation;

		final ClassFile[] classFiles;
		// I add this new package to the current AbstractLevelModel model.
		ProxyConsole.getInstance().normalOutput().print(
			"Loading class files from in: ");
		ProxyConsole.getInstance().normalOutput().println("jar input stream");

		classFiles = SubtypeLoader
			.loadSubtypesFromJarInputStream(null, inputStream, ".class");

		// @Note David: Is this sorting of classes by the alphabetical order?
		// If it is, why is 
		// Arrays.sort(results, new ClassNameAlphabeticalComparator()); 
		// in SubtypeLoader.loadSubtypesFromJar?

		for (int j = 0; j < classFiles.length; j++) {
			// Yann 2005/09/26: Member classes!
			// I now handle membre classes, thus, I need class files
			// to be sorted by the alphabetical order of their names.
			final String currentClassFileName = classFiles[j].getName();
			final int numberOfClassFiles = this.listOfClassFiles.size();
			int index = 0;
			while (index < numberOfClassFiles
					&& ((ClassFile) this.listOfClassFiles.get(index))
						.getName()
						.compareTo(currentClassFileName) <= 0) {

				index++;
			}
			this.listOfClassFiles.add(index, classFiles[j]);
		}
	}
	public void create(final ICodeLevelModel aCodeLevelModel) {
		final ClassFile[] listOfEntities =
			this.constituentsRepository.getEntities();
		final ClassFile[] listOfElements =
			this.constituentsRepository.getElements();

		this.createPackages(
			aCodeLevelModel,
			this.listOfClassFiles,
			this.constituentsRepository,
			listOfEntities);

		this.createEntities(
			aCodeLevelModel,
			this.listOfClassFiles,
			this.constituentsRepository,
			listOfEntities);

		this.createElements(
			aCodeLevelModel,
			this.listOfClassFiles,
			this.constituentsRepository,
			listOfElements);
	}
	private void createElements(
		final ICodeLevelModel aCodeLevelModel,
		final List listOfSourceConstituents,
		final ConstituentsRepository constituentsRepository,
		final ClassFile[] listOfElements) {

		RelationshipAnalyzer.initialise(this.mapOfIDsEntities);
		Context.initialise(this.mapOfIDsEntities);

		int[] sortedElements = new int[listOfElements.length];
		final StringBuffer buffer = new StringBuffer();
		String constituentName;

		for (int x = 0; x < listOfElements.length; x++) {
			constituentName = constituentsRepository.getElements()[x].getName();

			try {
				buffer.append("recognize");
				buffer.append(
					constituentName
						.substring(constituentName.lastIndexOf('.') + 1));
				buffer.append("Priority");
				sortedElements[x] = ((Integer) this
					.getClass()
					.getMethod(buffer.toString(), new java.lang.Class[0])
					.invoke(this, new Object[0])).intValue();
				buffer.setLength(0);
			}
			catch (final Throwable t) {
				t.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
			finally {
				// Yann 2004/03/31: Reset!
				// In any case (okay or exception), I reset
				// the buffer from its content.
				buffer.setLength(0);
			}
		}
		sortedElements = Utils.sortByPriority(sortedElements);

		// Yann 2002/08/01: Concurrent modification.
		// While I iterate over the list of entities,
		// I may want to add new entities (instances of Ghost),
		// thus I cannot use the iterator mechanism provided
		// by Java, because it implements a fail-safe security.
		// Yann 2004/04/10: Sorting out!
		// The fix described in the above comment is no longer valid.
		// I now sort the entities and the elements to get them in a
		// consistent order across platforms and implementations
		// (mainly for testing purposes). Thus, ghosts created during
		// the creation of elements may "push down" the list the entity
		// being analyzed... and element will be added (or tried to)
		// more than once, resulting in not-good-looking exceptions.
		// I added code to refocus the i index.

		// Yann 2006/02/10: Wrong order... all this time!
		// I should not iterate over entities and call the
		// recognise() methods on it but iterate over each
		// recognise() methods and call each on all the entities
		// this should prevent the "duplicate actor ID" problem...

		final List listOfSubmittedElements = new ArrayList();
		for (int elementRecogniserNumber =
			0; elementRecogniserNumber < listOfElements.length; elementRecogniserNumber++) {

			// Yann 2006/08/03: Member entities!
			// I should not iterate over the entities of the code-level model
			// because this model does not contain *directly* references to
			// member entities and thus these entities are not fed with their
			// methods and fields!!!
			//	final Iterator iteratorOnEntities =
			//		aCodeLevelModel.getIteratorOnConstituents(IEntity.class);
			// I now iterate over the list of provided classfiles, thus
			// including method in member classes.
			final Iterator iteratorOnEntities =
				listOfSourceConstituents.iterator();
			while (iteratorOnEntities.hasNext()) {
				// I look for the entity corresponding instance of class Class.
				// I cannot use:
				//      Class.forName(((Entity) enum.nextElement()).getName())
				// Since the given user's classes may not be in the classpath and
				// have been loaded from a different class loader.
				// Yann 2005/10/07: Packages!
				// A model may now contrain entities and packages.
				final ClassFile currentClass =
					(ClassFile) iteratorOnEntities.next();
				final char[] currentClassName =
					currentClass.getName().toCharArray();

				if (!Utils.isAnonymousOrLocalEntity(currentClassName) && !Utils
					.isLocalOrLocalMemberEntity(currentClassName)) {

					final IFirstClassEntity firstClassEntity =
						Utils.getEntityOrCreateGhost(
							aCodeLevelModel,
							currentClassName,
							this.mapOfIDsEntities);

					constituentName = constituentsRepository
						.getElements()[sortedElements[elementRecogniserNumber]]
							.getName();

					if (!(firstClassEntity instanceof IGhost)) {
						aCodeLevelModel.fireModelChange(
							IModelListener.ENTITY_ANALYZED,
							new AnalysisEvent(
								currentClassName,
								constituentName.toCharArray()));

						// I reset the list of submitted elements and entities.
						listOfSubmittedElements.clear();

						// I fill up the Element.
						for (int i = 0; i < currentClass
							.getMethods()
							.length(); i++) {

							// Yann 2016/09/21: Synthetic attributes!
							// Starting with JDK 1.6. generic introduced
							// all kind of perks... including "new"
							// synthetic methods!
							// https://docs.oracle.com/javase/tutorial/java/generics/bridgeMethods.html
							final MethodInfo methodInfo =
								currentClass.getMethods().get(i);
							if (AttrUtils.getAttrByName(
								methodInfo.getAttrs(),
								"Synthetic") == null) {

								listOfSubmittedElements.add(
									new ExtendedMethodInfo(
										currentClass,
										methodInfo));
							}
						}

						for (int i = 0; i < currentClass
							.getFields()
							.length(); i++) {

							listOfSubmittedElements.add(
								new ExtendedFieldInfo(
									currentClass,
									currentClass.getFields().get(i)));
						}

						try {
							// Yann 2002/07/31: Priorities.
							// I must keep the fields and the methods in
							// the list of submitted constituents,
							// because they might be used later to create
							// a Aggregation linked with them.
							// Yann 2003/12/22: ICodeLevelModelCreator.
							// I now use the method provided by the instance
							// of ICodeLevelModelCreator.
							//	Misc
							//		.getDeclaredMethod(
							//			constituentRepository
							//				.getElements()[sortedElements[x]],
							//			"recognize")
							//		.invoke(
							//			null,
							//			new Object[] {
							//				listOfSubmittedElements,
							//				this });
							buffer.append("recognize");
							buffer.append(
								constituentName.substring(
									constituentName.lastIndexOf('.') + 1));

							this
								.getClass()
								.getMethod(
									buffer.toString(),
									new java.lang.Class[] { List.class,
											ICodeLevelModel.class })
								.invoke(
									this,
									new Object[] { listOfSubmittedElements,
											aCodeLevelModel });
						}
						catch (final NoSuchMethodException t) {
							ProxyConsole.getInstance().errorOutput().print(
								AbstractClassFileCreator.class.getName());
							ProxyConsole.getInstance().errorOutput().print(
								" cannot invoke ");
							ProxyConsole.getInstance().errorOutput().println(
								listOfElements[sortedElements[elementRecogniserNumber]]
									.getName());
							//	t.printStackTrace(ProxyConsole
							//		.getInstance()
							//		.errorOutput());
							// Yann 2004/06/02: Flush.
							// I make sure I flush the output stream to
							// display as soon as possible exceptions.
							// Yann 2004/07/15: Flush!
							// This is now taken care of by the OutputManager class.
							//	OutputManager
							//		.getCurrentOutputManager()
							//		.getErrorOutput()
							//		.flush();
						}
						catch (IllegalAccessException e) {
							e.printStackTrace(
								ProxyConsole.getInstance().errorOutput());
						}
						catch (IllegalArgumentException e) {
							e.printStackTrace(
								ProxyConsole.getInstance().errorOutput());
						}
						catch (InvocationTargetException e) {
							e.printStackTrace(
								ProxyConsole.getInstance().errorOutput());
						}
						catch (SecurityException e) {
							e.printStackTrace(
								ProxyConsole.getInstance().errorOutput());
						}
						finally {
							// Yann 2004/03/31: Reset!
							// In any case (okay or exception), I reset
							// the buffer from its content.
							buffer.setLength(0);
						}
					}
					else {
						aCodeLevelModel.fireModelChange(
							IModelListener.ENTITY_SKIPPED,
							new AnalysisEvent(
								currentClassName,
								constituentName.toCharArray()));
					}
				}
			}

			// Yann 2004/04/10: Refocus.
			// Ghosts created during the analysis may have
			// "pushed down" the current entities down the
			// temporary list. I need to refocus the index
			// accordingly. I start from the index because
			// entities can be added only, not deleted.
			// I need to do this for each entity.
			//	for (int j = i; j < temporayListOfEntities.size(); j++) {
			//		if (((IEntity) temporayListOfEntities.get(j))
			//			.getName()
			//			.equals(currentClassName)) {
			//
			//			i = j;
			//			j = temporayListOfEntities.size();
			//		}
			//	}
		}
	}
	private void createEntities(
		final ICodeLevelModel aCodeLevelModel,
		final List listOfSourceConstituents,
		final ConstituentsRepository constituentsRepository,
		final ClassFile[] listOfEntities) {

		// Yann 2006/02/03: Spring cleaning.
		// Handling member classes makes it harder and harder to
		// analyse entities using the legacy code below. I rewrite
		// this algorithm to perform the creation of entities
		// (including membre entitie) and their connection
		// (inheritance and implementation) in two pass:
		// 1. I create all the entities, including member entities.
		//    I put the entities in the right packages.
		// 2. I connect the entities with one another through
		//    inheritance and implementation, as appropriate.

		// 1.
		Iterator iterator = listOfSourceConstituents.iterator();
		while (iterator.hasNext()) {
			final ClassFile classFile = (ClassFile) iterator.next();
			final String entityDisplayID = classFile.getName();
			char[] entityID = entityDisplayID.toCharArray();

			// I assume that the classfiles are sorted such as member
			// entities appear *after* their containing entities.

			// Yann 2011/10/01: Local entity!
			// There is an entity with ID: 
			//	javax.swing.JSlider$1$SmartHashtable$LabelUIResource
			// that must be dealt with as a local entity.
			if (!Utils.isAnonymousOrLocalEntity(entityID)
					&& !Utils.isLocalOrLocalMemberEntity(entityID)) {
				if (!Utils.isMemberEntity(entityID)) {
					final IFirstClassEntity firstClassEntity;
					if (Utils.isClass(classFile)) {
						firstClassEntity =
							aCodeLevelModel.getFactory().createClass(
								entityID,
								Utils.computeSimpleName(entityID));
					}
					else {
						firstClassEntity =
							aCodeLevelModel.getFactory().createInterface(
								entityID,
								Utils.computeSimpleName(entityID));
					}
					firstClassEntity.setVisibility(classFile.getAccess());

					final IPackage enclosingPackage = Utils
						.getPackage(this.mapOfPackageNamesPackages, entityID);
					enclosingPackage.addConstituent(firstClassEntity);
					this.mapOfIDsEntities
						.put(entityDisplayID, firstClassEntity);
				}
				else {
					final IFirstClassEntity memberEntity;
					if (Utils.isClass(classFile)) {
						memberEntity =
							aCodeLevelModel.getFactory().createMemberClass(
								entityID,
								Utils.computeSimpleName(entityID));
					}
					else {
						memberEntity =
							aCodeLevelModel.getFactory().createMemberInterface(
								entityID,
								Utils.computeSimpleName(entityID));
					}
					memberEntity.setVisibility(classFile.getAccess());

					final char[] enclosingEntityID = ArrayUtils.subarray(
						entityID,
						0,
						ArrayUtils.lastIndexOf(entityID, '$'));
					Utils
						.searchForEntity(aCodeLevelModel, enclosingEntityID)
						.addConstituent((IConstituentOfEntity) memberEntity);
					this.mapOfIDsEntities.put(entityDisplayID, memberEntity);
				}
			}
		}

		// 2. At this point, all missing entities must be ghosts!
		iterator = listOfSourceConstituents.iterator();
		while (iterator.hasNext()) {
			final ClassFile classFile = (ClassFile) iterator.next();
			final String entityDisplayName = classFile.getName();
			final char[] entityName = entityDisplayName.toCharArray();

			if (!Utils.isAnonymousOrLocalEntity(entityName)
					&& !Utils.isLocalOrLocalMemberEntity(entityName)) {

				// Yann 2008/11/06: Performance.
				// To increase the performance when building a model
				// from Java code, I maintain internally a map of
				// IDs and entities.
				final IFirstClassEntity firstClassEntity =
					Utils.getEntityOrCreateGhost(
						aCodeLevelModel,
						entityName,
						this.mapOfIDsEntities);

				// Yann 2006/02/09: java.lang.Object
				// The java.lang.Object class does not have a superclass.
				// Hence, its superclass name would be "" but I don't
				// want to create a Ghost with no name...
				// Yann 2014/04/11: Object!
				// Wow, for the longest time, I was not adding Object 
				// as super-entity for interfaces: weird that it never 
				// caused problem before???
				if (!(firstClassEntity
					.equals(Factory.getInstance().createHierarchyRoot()))) {

					final String superClassDisplayName =
						classFile.getSuperName();
					final char[] superClassName =
						superClassDisplayName.toCharArray();
					final IFirstClassEntity superEntity =
						Utils.getEntityOrCreateGhost(
							aCodeLevelModel,
							superClassName,
							this.mapOfIDsEntities);
					((IFirstClassEntity) firstClassEntity)
						.addInheritedEntity(superEntity);
				}

				final InterfaceList interfaceList = classFile.getInterfaces();
				for (int i = 0; i < interfaceList.length(); i++) {
					final String superInterfaceDisplayName =
						interfaceList.get(i);
					final char[] superInterfaceName =
						superInterfaceDisplayName.toCharArray();
					final IFirstClassEntity superEntity =
						Utils.getEntityOrCreateGhost(
							aCodeLevelModel,
							superInterfaceName,
							this.mapOfIDsEntities);

					if (firstClassEntity instanceof IClass) {
						((IClass) firstClassEntity).addImplementedInterface(
							(IInterfaceActor) superEntity);
					}
					else {
						((IInterfaceActor) firstClassEntity)
							.addInheritedEntity(superEntity);
					}
				}
			}
		}
	}
	private void createPackages(
		final ICodeLevelModel aCodeLevelModel,
		final List listOfSourceConstituents,
		final ConstituentsRepository constituentsRepository,
		final ClassFile[] listOfEntities) {

		// Yann 2008/11/03: Packages.
		// Packages are now mandatory!

		final Iterator iterator = listOfSourceConstituents.iterator();
		while (iterator.hasNext()) {
			final ClassFile classFile = (ClassFile) iterator.next();
			final String entityDisplayName = classFile.getName();
			final char[] entityName = entityDisplayName.toCharArray();
			final char[] packageName = Utils.extractPackageName(entityName);
			final IPackage enclosingPackage = Utils.searchForPackage(
				aCodeLevelModel,
				entityName,
				new PackageCreator() {
					public IPackage create(final char[] aName) {
						return Factory.getInstance().createPackage(aName);
					}
				});
			if (!this.mapOfPackageNamesPackages.containsKey(packageName)) {
				this.mapOfPackageNamesPackages
					.put(String.valueOf(packageName), enclosingPackage);
			}
		}
	}
	public List recognizeAggregation(
		final List aListOfSubmittedConstituents,
		final ICodeLevelModel aCodeLevelModel) {

		return aListOfSubmittedConstituents;
	}
	public int recognizeAggregationPriority() {
		return Constants.NORMAL_RECOGNITION_PRIORITY;
	}
	public List recognizeAssociation(
		final List aListOfSubmittedConstituents,
		final ICodeLevelModel aCodeLevelModel) {

		return aListOfSubmittedConstituents;
	}
	public int recognizeAssociationPriority() {
		return Constants.HIGH_RECOGNITION_PRIORITY;
	}
	public List recognizeComposition(
		final List aListOfSubmittedConstituents,
		final ICodeLevelModel aCodeLevelModel) {

		return aListOfSubmittedConstituents;
	}
	public int recognizeCompositionPriority() {
		return Constants.NORMAL_RECOGNITION_PRIORITY;
	}
	public List recognizeConstructor(
		final List aListOfSubmittedConstituents,
		final ICodeLevelModel aCodeLevelModel) {

		final List notRecognized = new ArrayList();
		final Iterator iterator = aListOfSubmittedConstituents.iterator();

		while (iterator.hasNext()) {
			final Object element = iterator.next();
			if (element instanceof ExtendedMethodInfo) {
				ExtendedMethodInfo currentExtendedMethod =
					(ExtendedMethodInfo) element;
				// Yann 2004/03/28: Constructors.
				// If the method is a constructor, I take care
				// of giving it the proper name and return type.
				final char[] name = currentExtendedMethod.getName();

				// Yann 2005/08/15: Inner classes!
				// I now manage inner and anonymous classes...
				//	if (Utils.isSpecialMethod(name)
				//		&& !Misc.isAnonymousClass(
				//			currentExtendedMethod.getDeclaringClassName())) {
				if (Utils.isSpecialMethod(name)) {
					final IConstructor currentConstructor =
						aCodeLevelModel.getFactory().createConstructor(
							Utils.computeSignature(currentExtendedMethod),
							Util.computeSimpleName(
								currentExtendedMethod.getDeclaringClassName()));

					currentConstructor
						.setVisibility(currentExtendedMethod.getVisibility());

					final char[][] detectedParameters =
						currentExtendedMethod.getParameters();
					for (int i = 0; i < detectedParameters.length; i++) {
						// Yann 2004/01/25: Arrays and primitive types!
						// I must be careful with arrays (remove the
						// brackets) and primitive types (do not attempt
						// to create a Ghost from them).
						// Yann 2009/05/02: No String anymore!
						// Cool, eh?
						char[] paramType = detectedParameters[i];
						int cardinality = 1;
						final int bracketIndex =
							ArrayUtils.indexOf(paramType, '[');
						if (bracketIndex > -1) {
							cardinality =
								(paramType.length - bracketIndex) / 2 + 1;
							paramType =
								ArrayUtils.subarray(paramType, 0, bracketIndex);
						}

						final IParameter parameter = this.createParameter(
							aCodeLevelModel,
							paramType,
							cardinality);
						currentConstructor.addConstituent(parameter);
					}

					// Yann 2005/12/29: Duplicate...
					// Actually, the RelationshipAnalyser might already have created
					// a method in an entity if this method was called from another
					// entity. Thus, there would be an attempt to add a "duplicate"
					// element, which is legitimate but taken care of by the new condition.
					final IFirstClassEntity firstClassEntity =
						Utils.getEntityOrCreateGhost(
							aCodeLevelModel,
							currentExtendedMethod.getDeclaringClassName(),
							this.mapOfIDsEntities);
					if (!firstClassEntity.doesContainConstituentWithID(
						currentConstructor.getID())) {

						firstClassEntity.addConstituent(currentConstructor);
					}
				}
			}
			else {
				notRecognized.add(element);
				// This is not a method, next...
			}
		}

		return notRecognized;
	}
	private IParameter createParameter(
		final ICodeLevelModel aCodeLevelModel,
		final char[] someParamType,
		final int cardinality) {

		final IParameter parameter;
		if (Util.isPrimtiveType(someParamType)) {
			parameter = aCodeLevelModel.getFactory().createParameter(
				aCodeLevelModel
					.getFactory()
					.createPrimitiveEntity(someParamType),
				cardinality);
		}
		else {
			parameter = aCodeLevelModel.getFactory().createParameter(
				Utils.getEntityOrCreateGhost(
					aCodeLevelModel,
					someParamType,
					this.mapOfIDsEntities),
				cardinality);
		}
		return parameter;
	}
	public int recognizeConstructorPriority() {
		// Yann 2004/01/25: Priority.
		// Priority should be:
		//    return Constants.LOWEST_RECOGNITION_PRIORITY;
		// Yann 2004/08/09: Priority.
		// Priorities as they are now make more sense.
		return Constants.HIGH_RECOGNITION_PRIORITY;
	}
	public List recognizeContainerAggregation(
		final List aListOfSubmittedConstituents,
		final ICodeLevelModel aCodeLevelModel) {

		// Yann 2007/10/30: AACRelationshipAnalyser!
		// The building of the relationships is now
		// externalised in a real analysis.
		if (Utils.ENABLE_BUILT_IN_AAC_ANALYSIS) {
			ContainerRelationshipAnalyzer.recognizeContainerAggregation(
				aListOfSubmittedConstituents,
				aCodeLevelModel);
		}

		return aListOfSubmittedConstituents;
	}
	public int recognizeContainerAggregationPriority() {
		// Yann 2004/01/25: Priority.
		// Priority should be:
		//    return Constants.LOW_RECOGNITION_PRIORITY;
		// Yann 2004/08/09: Priority.
		// Priorities as they are now make more sense.
		return Constants.LOW_RECOGNITION_PRIORITY;
	}
	public List recognizeContainerComposition(
		final List aListOfSubmittedConstituents,
		final ICodeLevelModel aCodeLevelModel) {

		return aListOfSubmittedConstituents;
	}
	public int recognizeContainerCompositionPriority() {
		// Yann 2004/01/25: Priority.
		// Priority should be:
		//    return Constants.LOW_RECOGNITION_PRIORITY;
		// Yann 2004/08/09: Priority.
		// Priorities as they are now make more sense.
		return Constants.LOW_RECOGNITION_PRIORITY;
	}
	public List recognizeCreation(
		final List aListOfSubmittedConstituents,
		final ICodeLevelModel aCodeLevelModel) {

		return aListOfSubmittedConstituents;
	}
	public int recognizeCreationPriority() {
		return Constants.LOW_RECOGNITION_PRIORITY;
	}
	public List recognizeDelegatingMethod(
		final List aListOfSubmittedConstituents,
		final ICodeLevelModel aCodeLevelModel) {

		// A method submitted is a PDelegating method if inside the
		// the method body there is one method call to a method that
		// has the same name.
		final Iterator iterator = aListOfSubmittedConstituents.iterator();
		while (iterator.hasNext()) {
			final Object member = iterator.next();

			if (member instanceof ExtendedMethodInfo) {
				final ExtendedMethodInfo extendedMethodInfo =
					(ExtendedMethodInfo) member;
				// final MethodInfo methodInfo = extendedMethodInfo.getMethod();

				final String[] callees =
					AbstractClassFileCreator.computeMethodCallees(
						extendedMethodInfo.getDeclaringClassConstantPool(),
						extendedMethodInfo);

				if (callees.length == 1) {
					if (callees[0]
						.substring(callees[0].lastIndexOf('.') + 1)
						.equals(extendedMethodInfo.getName())) {

						final String calleesTypeName = callees[0]
							.substring(0, callees[0].lastIndexOf('.'));

						final IFirstClassEntity currentEntity =
							Utils.getEntityOrCreateGhost(
								aCodeLevelModel,
								extendedMethodInfo.getDeclaringClassName(),
								this.mapOfIDsEntities);

						final Iterator iteratorOnContainerAggregation =
							currentEntity.getIteratorOnConstituents(
								IContainerAggregation.class);
						while (iteratorOnContainerAggregation.hasNext()) {
							final IContainerAggregation aggregation =
								(IContainerAggregation) iteratorOnContainerAggregation
									.next();

							// Yann 2003/12/15: FieldType vs. TargetEntity
							// In previous version, the if was about
							// getFieldType() (see v1) in the stead of
							// getTargetEntity(). Now, it is the target
							// actor that we want to check.
							if (aggregation
								.getTargetEntity()
								.equals(calleesTypeName)) {

								// TODO: The target method should not be "null"! 
								final IDelegatingMethod delegatingMethod =
									aCodeLevelModel
										.getFactory()
										.createDelegatingMethod(
											extendedMethodInfo.getName(),
											aggregation,
											null);
								currentEntity.addConstituent(delegatingMethod);
								iterator.remove();
							}
						}
					}
				}
			}
		}

		return aListOfSubmittedConstituents;
	}
	public int recognizeDelegatingMethodPriority() {
		return Constants.LOWEST_RECOGNITION_PRIORITY;
	}
	public List recognizeEnum(
		final List aListOfSubmittedConstituents,
		final ICodeLevelModel aCodeLevelModel) {

		return aListOfSubmittedConstituents;
	}
	public int recognizeEnumPriority() {
		return Constants.NORMAL_RECOGNITION_PRIORITY;
	}
	public List recognizeField(
		final List aListOfSubmittedConstituents,
		final ICodeLevelModel aCodeLevelModel) {

		final List notRecognized = new ArrayList();
		final Iterator enumarator = aListOfSubmittedConstituents.iterator();
		while (enumarator.hasNext()) {
			final Object element = enumarator.next();
			if (element instanceof ExtendedFieldInfo) {
				final ExtendedFieldInfo currentField =
					(ExtendedFieldInfo) element;
				char[] fieldType = currentField.getType();
				final int cardinality = Util.isArrayOrCollection(fieldType)
						? Constants.CARDINALITY_MANY
						: Constants.CARDINALITY_ONE;

				// Yann 2003/11/29: Array!
				// I must take care of removing brackets for array.
				int index;
				if ((index = ArrayUtils.indexOf(fieldType, '[')) == -1) {
					index = fieldType.length;
				}
				fieldType = ArrayUtils.subarray(fieldType, 0, index);

				// Yann 2002/08/01: Ghost!
				// From now on, if an entity cannot be found in the
				// list of entities, I create a ghost for it...
				// Yann 2003/11/29: Primitive type.
				// I must take care not to add primitive type.
				final IField field;
				if (Util.isPrimtiveType(fieldType)) {
					field = aCodeLevelModel.getFactory().createField(
						currentField.getName(),
						currentField.getName(),
						fieldType,
						cardinality);
				}
				else {
					final IFirstClassEntity targetEntity =
						Utils.getEntityOrCreateGhost(
							aCodeLevelModel,
							fieldType,
							this.mapOfIDsEntities);
					// Yann 2006/02/08: Members!
					// The getConstituentFromID() method takes care of members and ghosts...
					//	if (targetEntity == null
					//		&& !Utils.isAnonymousEntity(fieldType)) {
					//
					//		targetEntity =
					//			aCodeLevelModel.getFactory().createGhost(
					//				fieldType);
					//		aCodeLevelModel.addConstituent(targetEntity);
					//	}
					field = aCodeLevelModel.getFactory().createField(
						currentField.getName(),
						currentField.getName(),
						targetEntity.getID(),
						cardinality);
				}
				field.setVisibility(currentField.getVisibility());

				final IFirstClassEntity firstClassEntity =
					Utils.getEntityOrCreateGhost(
						aCodeLevelModel,
						currentField.getDeclaringClassName(),
						this.mapOfIDsEntities);
				firstClassEntity.addConstituent(field);
			}
			else {
				// this is not a field, next ...
				notRecognized.add(element);
			}
		}

		return notRecognized;
	}
	public int recognizeFieldPriority() {
		// Yann 2004/01/25: Priority.
		// Priority should be:
		//    return Constants.LOWEST_RECOGNITION_PRIORITY;
		// Yann 2004/08/09: Priority.
		// Priorities as they are now make more sense.
		return Constants.HIGHEST_RECOGNITION_PRIORITY;
	}
	public List recognizeGeneralisation(
		final List aListOfSubmittedConstituents,
		final ICodeLevelModel aCodeLevelModel) {

		return aListOfSubmittedConstituents;
	}
	public int recognizeGeneralisationPriority() {
		return Constants.NORMAL_RECOGNITION_PRIORITY;
	}
	public List recognizeImplementation(
		final List aListOfSubmittedConstituents,
		final ICodeLevelModel aCodeLevelModel) {

		return aListOfSubmittedConstituents;
	}
	public int recognizeImplementationPriority() {
		return Constants.NORMAL_RECOGNITION_PRIORITY;
	}
	public List recognizeMethod(
		final List aListOfSubmittedConstituents,
		final ICodeLevelModel aCodeLevelModel) {

		final List notRecognized = new ArrayList();
		final Iterator iterator = aListOfSubmittedConstituents.iterator();

		while (iterator.hasNext()) {
			final Object element = iterator.next();
			if (element instanceof ExtendedMethodInfo) {
				ExtendedMethodInfo currentExtendedMethod =
					(ExtendedMethodInfo) element;
				// Yann 2004/03/28: Constructors.
				// If the method is a constructor, I take care
				// of giving it the proper name and return type.
				final char[] name = currentExtendedMethod.getName();
				if (!Utils.isSpecialMethod(name)) {
					final IMethod currentMethod =
						aCodeLevelModel.getFactory().createMethod(
							Utils.computeSignature(currentExtendedMethod),
							name);

					// Farouk 2004/02/19: Constituent ID.
					// The method makeSignature is the new way to give an
					// actor ID to a method, instead of using toString()
					// from MethodInfo.

					currentMethod
						.setReturnType(currentExtendedMethod.getReturnType());

					currentMethod
						.setVisibility(currentExtendedMethod.getVisibility());

					final char[][] detectedParameters =
						currentExtendedMethod.getParameters();
					for (int i = 0; i < detectedParameters.length; i++) {
						// Yann 2004/01/25: Arrays and primitive types!
						// I must be careful with arrays (remove the
						// brackets) and primitive types (do not attempt
						// to create a Ghost from them).
						// Yann 2006/02/08: Members!
						// The searchForEntity() method takes care of members and ghosts...
						//	if (anEntity == null
						//		&& !padl.util.Util.isPrimtiveType(paramType)
						//		&& !Utils.isAnonymousEntity(paramType)) {
						//
						//		anEntity =
						//			aCodeLevelModel.getFactory().createGhost(
						//				paramType);
						//		aCodeLevelModel.addConstituent(anEntity);
						//	}
						//
						// Yann 2004/08/07: Parameters!
						// Parameters now take care of their
						// cardinlity by themselves...
						// Yann 2007/08/31: Member...
						// I should not replace the $ sign by
						// a . because then I cannot know that
						// the type is a member type anymore...
						// this would not happen if IEntity
						// was used instead of String!
						//
						// Yann 2009/05/02: No String anymore!
						// Cool, eh?
						char[] paramType = detectedParameters[i];
						int cardinality = 1;
						final int bracketIndex =
							ArrayUtils.indexOf(paramType, '[');
						if (bracketIndex > -1) {
							cardinality =
								(paramType.length - bracketIndex) / 2 + 1;
							paramType =
								ArrayUtils.subarray(paramType, 0, bracketIndex);
						}

						final IParameter parameter = this.createParameter(
							aCodeLevelModel,
							paramType,
							cardinality);
						currentMethod.addConstituent(parameter);
					}

					// Yann 2005/12/29: Duplicate...
					// Actually, the RelationshipAnalyser might already have created
					// a method in an entity if this method was called from another
					// entity. Thus, there would be an attempt to add a "duplicate"
					// element, which is legitimate but taken care of by the new condition.
					final IFirstClassEntity firstClassEntity =
						Utils.getEntityOrCreateGhost(
							aCodeLevelModel,
							currentExtendedMethod.getDeclaringClassName(),
							this.mapOfIDsEntities);
					if (!firstClassEntity
						.doesContainConstituentWithID(currentMethod.getID())) {

						firstClassEntity.addConstituent(currentMethod);
					}
				}
			}
			else {
				notRecognized.add(element);
				// This is not a method, next...
			}
		}

		return notRecognized;
	}

	public int recognizeMethodPriority() {
		// Yann 2004/01/25: Priority.
		// Priority should be:
		//    return Constants.LOWEST_RECOGNITION_PRIORITY;
		// Yann 2004/08/09: Priority.
		// Priorities as they are now make more sense.
		return Constants.HIGH_RECOGNITION_PRIORITY;
	}
	public List recognizeRelationship(
		final List aListOfSubmittedConstituents,
		final ICodeLevelModel aCodeLevelModel) {

		// Yann 2007/10/30: Do not remove!
		// Even though I clearly distinguish now between
		// code-level models and idiom-level models, I
		// keep this call to make sure that MethodInvocation
		// are properly created because I need them for the
		// AACRelationshipAnalyser
		RelationshipAnalyzer.recogniseRelationship(
			aListOfSubmittedConstituents,
			aCodeLevelModel,
			this.storeMethodInvocation);

		return aListOfSubmittedConstituents;
	}
	public int recognizeRelationshipPriority() {
		// Yann 2004/01/25: Priority.
		// Priority should be:
		//    return Constants.HIGHEST_RECOGNITION_PRIORITY;
		// Yann 2004/08/09: Priority.
		// Priorities as they are now make more sense.
		return Constants.NORMAL_RECOGNITION_PRIORITY;
	}
	public List recognizeSpecialisation(
		final List aListOfSubmittedConstituents,
		final ICodeLevelModel aCodeLevelModel) {

		return aListOfSubmittedConstituents;
	}
	public int recognizeSpecialisationPriority() {
		return Constants.NORMAL_RECOGNITION_PRIORITY;
	}
	public List recognizeStructure(
		final List aListOfSubmittedConstituents,
		final ICodeLevelModel aCodeLevelModel) {

		return aListOfSubmittedConstituents;
	}
	public int recognizeStructurePriority() {
		return Constants.NORMAL_RECOGNITION_PRIORITY;
	}
	public List recognizeUnion(
		final List aListOfSubmittedConstituents,
		final ICodeLevelModel aCodeLevelModel) {

		return aListOfSubmittedConstituents;
	}
	public int recognizeUnionPriority() {
		return Constants.NORMAL_RECOGNITION_PRIORITY;
	}
	public List recognizeUseRelationship(
		final List aListOfSubmittedConstituents,
		final ICodeLevelModel aCodeLevelModel) {

		return aListOfSubmittedConstituents;
	}
	public int recognizeUseRelationshipPriority() {
		// Yann 2004/01/25: Priority.
		// Priority should be:
		//    return Constants.HIGHEST_RECOGNITION_PRIORITY;
		// Yann 2004/08/09: Priority.
		// Priorities as they are now make more sense.
		return Constants.LOWEST_RECOGNITION_PRIORITY;
	}
}
