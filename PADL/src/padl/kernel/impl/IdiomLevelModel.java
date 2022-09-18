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
package padl.kernel.impl;

import padl.kernel.Constants;
import padl.kernel.IIdiomLevelModel;

class IdiomLevelModel extends AbstractLevelModel implements IIdiomLevelModel,
		Cloneable {

	private static final long serialVersionUID = -8868592330581902153L;

	public IdiomLevelModel() {
		this(Constants.DEFAULT_IDIOMLEVELMODEL_ID);
	}
	public IdiomLevelModel(final char[] aName) {
		super(aName);
	}
	//	public void addConstituent(final IConstituent aConstituent) {
	//		// Yann 2008/11/04: No no-package!
	//		// In the process of adding the packages consistently,
	//		// I choose that no entity can be without package. Even
	//		// though it may not make sense for languages like C++.
	//		//	if (aConstituent instanceof IEntity) {
	//		//		this.addConstituent((IEntity) aConstituent);
	//		//	}
	//		//	else
	//		// TODO Enforce this constraint in the code-level and design-level models!?
	//		if (aConstituent instanceof IPackage) {
	//			this.addConstituent((IPackage) aConstituent);
	//		}
	//		else {
	//			throw new ModelDeclarationException(MultilingualManager.getString(
	//				"ENT_ADD_ORG_LEVEL",
	//				IdiomLevelModel.class));
	//		}
	//	}
	//	public void create(final IIdiomLevelModelCreator anIdiomLevelCreator) {
	//		anIdiomLevelCreator.create(this);
	//
	//		// Yann 2004/04/10: History!
	//		// The following code is now located (and improved) in the
	//		// ClassFileCompeteCreator.create() method!
	//		//	final List listOfSourceConstituents =
	//		//		anIdiomLevelCreator.getListOfSourceConstituents();
	//		//
	//		//	final ConstituentRepository constituentRepository =
	//		//		ConstituentRepository.getCurrentTypeRepository();
	//		//
	//		//	final ClassFile[] listOfEntities =
	//		//		constituentRepository.getEntities();
	//		//	final ClassFile[] listOfElements =
	//		//		constituentRepository.getElements();
	//		//
	//		//	int[] sortedEntities = new int[listOfEntities.length];
	//		//	int[] sortedElements = new int[listOfElements.length];
	//		//
	//		//	final StringBuffer buffer = new StringBuffer();
	//		//	String constituentName;
	//		//
	//		//	// Hervé a couple of years ago...
	//		//	// Analyses of the Entities,
	//		//	// sort based on the recognizeRequestOrder() method.
	//		//	// Yann 2003/12/22: IIdiomLevelCreator.
	//		//	// I now use the methods defined in the creator that
	//		//	// I introduce to allow different creators.
	//		//	//	for (int x = 0; x < listOfEntities.length; x++) {
	//		//	//		try {
	//		//	//			sortedEntities[x] =
	//		//	//				((Integer) Misc
	//		//	//					.getDeclaredMethod(
	//		//	//						constituentRepository.getEntities()[x],
	//		//	//						"recognizeRequestOrder")
	//		//	//					.invoke(null, new Object[0]))
	//		//	//					.intValue();
	//		//	//		}
	//		//	//		catch (final Exception e) {
	//		//	//			e.printStackTrace(ListenerManager.getCurrentListenerManager().getOutput());
	//		//	//		}
	//		//	//	}
	//		//	//	sortedEntities = this.sortByPriority(sortedEntities);
	//		//	for (int x = 0; x < listOfEntities.length; x++) {
	//		//		constituentName =
	//		//			constituentRepository.getEntities()[x].getName();
	//		//
	//		//		try {
	//		//			buffer.append("recognize");
	//		//			buffer.append(
	//		//				constituentName.substring(
	//		//					constituentName.lastIndexOf('.') + 1));
	//		//			buffer.append("Priority");
	//		//			sortedEntities[x] =
	//		//				((Integer) anIdiomLevelCreator
	//		//					.getClass()
	//		//					.getMethod(buffer.toString(), new java.lang.Class[0])
	//		//					.invoke(anIdiomLevelCreator, new Object[0]))
	//		//					.intValue();
	//		//			buffer.setLength(0);
	//		//		}
	//		//		catch (final Exception e) {
	//		//			e.printStackTrace(ListenerManager.getCurrentListenerManager().getOutput());
	//		//		}
	//		//	}
	//		//	sortedEntities = this.sortByPriority(sortedEntities);
	//		//
	//		//	// I compare the user's given entities against the Entities.
	//		//	for (int x = 0;
	//		//		x < listOfEntities.length && listOfSourceConstituents.size() > 0;
	//		//		x++) {
	//		//
	//		//		try {
	//		//			// Yann 2001/08/10: Hard to use ClassLoader!
	//		//			// I found a new bug linked to the use of a special instance of
	//		//			// ClassLoader. In the Eclipse version of Ptidej, all the
	//		//			// classes are loader through the Eclipse-specific class loader.
	//		//			// Thus, any use (on a non-system class) of the special field
	//		//			// .class calls the Eclipse class loader. Indeed, a the statement
	//		//			// IdiomLevelModel.class returned in fact:
	//		//			//     <Eclipse class loader>.IdiomLevelModel
	//		//			// And the method getMethod() threw an instance of exception
	//		//			// NoSuchMethodException, since it was expecting:
	//		//			//     <PatternsBox class loader>.IdiomLevelModel
	//		//			// The correction is: For the system class, the use of special
	//		//			// field .class is okay (the system class loader is the
	//		//			// superclass of all class loaders). For application-specific
	//		//			// classes, *never* use the special field .class, always use:
	//		//			//     <instance of a class loader>.loadClass(<class name>)
	//		//			//
	//		//			// Yann 2002/07/28: A year later...
	//		//			// I replaced use of the reflection API with instances of
	//		//			// the class ClassFile from CFParse. I also cleanup a bit
	//		//			// this method, in particular, I removed the local variable
	//		//			// paramIn.
	//		//			//
	//		//			// Yann 2003/02/22: Cleaning!
	//		//			// I want to include the implementation in my Ph.D. thesis,
	//		//			// so I tidy up a litte the code...
	//		//			ListenerManager
	//		//				.getCurrentListenerManager()
	//		//				.firePatternChange(
	//		//				IListener.ENTITY_RECOGNIZED,
	//		//				new RecognitionEvent(
	//		//					constituentRepository
	//		//						.getEntities()[sortedEntities[x]]
	//		//						.getName()));
	//		//
	//		//			// The method recognize takes a list of submitted PEntities,
	//		//			// and return a list with the PEntities not recognized
	//		//			// (that it remains to recognize).
	//		//
	//		//			// Yann 2002/07/29: No more clone...
	//		//			// I do not reassign the result of the recognize(...) method
	//		//			// to the current list of submitted classes. Indeed, PClass
	//		//			// and PInterfaces both already take care of sorting out
	//		//			// the instances of ClassFile according to their needs.
	//		//			// The assignation only saved some iterations when linking
	//		//			// interfaces together...
	//		//			// Yann 2003/12/22: IIdiomLevelModelCreator.
	//		//			// I now use the method provided by the instance
	//		//			// of IIdiomLevelModelCreator.
	//		//			//	Misc
	//		//			//		.getDeclaredMethod(
	//		//			//			constituentRepository
	//		//			//				.getEntities()[sortedEntities[x]],
	//		//			//			"recognize")
	//		//			//		.invoke(
	//		//			//			null,
	//		//			//			new Object[] { listOfSourceConstituents, this });
	//		//			constituentName =
	//		//				constituentRepository
	//		//					.getEntities()[sortedEntities[x]]
	//		//					.getName();
	//		//			buffer.append("recognize");
	//		//			buffer.append(
	//		//				constituentName.substring(
	//		//					constituentName.lastIndexOf('.') + 1));
	//		//			anIdiomLevelCreator
	//		//				.getClass()
	//		//				.getMethod(
	//		//					buffer.toString(),
	//		//					new java.lang.Class[] {
	//		//						List.class,
	//		//						IIdiomLevelModel.class })
	//		//				.invoke(
	//		//					anIdiomLevelCreator,
	//		//					new Object[] { listOfSourceConstituents, this });
	//		//			buffer.setLength(0);
	//		//		}
	//		//		catch (final Exception e) {
	//		//			e.printStackTrace(ListenerManager.getCurrentListenerManager().getOutput());
	//		//		}
	//		//	}
	//		//
	//		//	// Analyses of the Elements,
	//		//	// sort based on the recognizeRequestOrder() method.
	//		//	//	for (int x = 0; x < listOfElements.length; x++) {
	//		//	//		try {
	//		//	//			sortedElements[x] =
	//		//	//				((Integer) Misc
	//		//	//					.getDeclaredMethod(
	//		//	//						constituentRepository.getElements()[x],
	//		//	//						"recognizeRequestOrder")
	//		//	//					.invoke(null, new Object[0]))
	//		//	//					.intValue();
	//		//	//		}
	//		//	//		catch (final Exception e) {
	//		//	//			e.printStackTrace(ListenerManager.getCurrentListenerManager().getOutput());
	//		//	//		}
	//		//	//	}
	//		//	//	sortedElements = this.sortByPriority(sortedElements);
	//		//	for (int x = 0; x < listOfElements.length; x++) {
	//		//		constituentName =
	//		//			constituentRepository.getElements()[x].getName();
	//		//
	//		//		try {
	//		//			buffer.append("recognize");
	//		//			buffer.append(
	//		//				constituentName.substring(
	//		//					constituentName.lastIndexOf('.') + 1));
	//		//			buffer.append("Priority");
	//		//			sortedElements[x] =
	//		//				((Integer) anIdiomLevelCreator
	//		//					.getClass()
	//		//					.getMethod(buffer.toString(), new java.lang.Class[0])
	//		//					.invoke(anIdiomLevelCreator, new Object[0]))
	//		//					.intValue();
	//		//			buffer.setLength(0);
	//		//		}
	//		//		catch (final Exception e) {
	//		//			e.printStackTrace(ListenerManager.getCurrentListenerManager().getOutput());
	//		//		}
	//		//	}
	//		//	sortedElements = this.sortByPriority(sortedElements);
	//		//
	//		//	// Yann 2002/08/01: Concurrent modification.
	//		//	// While I iterate over the list of entities,
	//		//	// I may want to add new entities (instances of Ghost),
	//		//	// thus I cannot use the iterator mechanism provided
	//		//	// by Java, because it implements a fail-safe security.
	//		//	final List temporayListOfEntities = this.listOfConstituents();
	//		//	for (int i = 0; i < temporayListOfEntities.size(); i++) {
	//		//
	//		//		// I look for the entity corresponding instance of class Class.
	//		//		// I cannot use:
	//		//		//      Class.forName(((Entity) enum.nextElement()).getName())
	//		//		// Since the given user's classes may not be in the classpath and
	//		//		// have been loaded from a different class loader.
	//		//		final IEntity entity = (IEntity) temporayListOfEntities.get(i);
	//		//		final String currentClassName = entity.getName();
	//		//
	//		//		if (!(entity instanceof IGhost)) {
	//		//			ListenerManager
	//		//				.getCurrentListenerManager()
	//		//				.firePatternChange(
	//		//				IListener.ENTITY_ANALYZED,
	//		//				new AnalysisEvent(currentClassName));
	//		//
	//		//			final Iterator enumClasses =
	//		//				listOfSourceConstituents.iterator();
	//		//			ClassFile currentClass = null;
	//		//
	//		//			// I can use such a loop since I know (for sure) that the Class
	//		//			// I'm looking for exists.
	//		//			while (enumClasses.hasNext()
	//		//				&& !(currentClass = (ClassFile) enumClasses.next())
	//		//					.getName()
	//		//					.equals(
	//		//					currentClassName));
	//		//
	//		//			// I reset the list of submitted elements and entities.
	//		//			List listOfSubmittedElements = new ArrayList();
	//		//
	//		//			// I fill up the Element.
	//		//			for (int x = 0;
	//		//				x < currentClass.getMethods().length();
	//		//				listOfSubmittedElements.add(
	//		//					new ExtendedMethodInfo(
	//		//						currentClass,
	//		//						currentClass.getMethods().get(x++))));
	//		//			for (int x = 0;
	//		//				x < currentClass.getFields().length();
	//		//				listOfSubmittedElements.add(
	//		//					new ExtendedFieldInfo(
	//		//						currentClass,
	//		//						currentClass.getFields().get(x++))));
	//		//
	//		//			for (int x = 0;
	//		//				x < listOfElements.length
	//		//					&& listOfSubmittedElements.size() > 0;
	//		//				x++) {
	//		//
	//		//				try {
	//		//					// Yann 2002/07/31: Priorities.
	//		//					// I must keep the fields and the methods in
	//		//					// the list of submitted constituents,
	//		//					// because they might be used later to create
	//		//					// a Aggregation linked with them.
	//		//					// Yann 2003/12/22: IIdiomLevelModelCreator.
	//		//					// I now use the method provided by the instance
	//		//					// of IIdiomLevelModelCreator.
	//		//					//	Misc
	//		//					//		.getDeclaredMethod(
	//		//					//			constituentRepository
	//		//					//				.getElements()[sortedElements[x]],
	//		//					//			"recognize")
	//		//					//		.invoke(
	//		//					//			null,
	//		//					//			new Object[] {
	//		//					//				listOfSubmittedElements,
	//		//					//				this });
	//		//					constituentName =
	//		//						constituentRepository
	//		//							.getElements()[sortedElements[x]]
	//		//							.getName();
	//		//					buffer.append("recognize");
	//		//					buffer.append(
	//		//						constituentName.substring(
	//		//							constituentName.lastIndexOf('.') + 1));
	//		//					anIdiomLevelCreator
	//		//						.getClass()
	//		//						.getMethod(
	//		//							buffer.toString(),
	//		//							new java.lang.Class[] {
	//		//								List.class,
	//		//								IIdiomLevelModel.class })
	//		//						.invoke(
	//		//							anIdiomLevelCreator,
	//		//							new Object[] {
	//		//								listOfSubmittedElements,
	//		//								this });
	//		//				}
	//		//				catch (final InvocationTargetException e) {
	//		//					ListenerManager.getCurrentListenerManager().getOutput().print("Error: ");
	//		//					ListenerManager.getCurrentListenerManager().getOutput().println(
	//		//						listOfElements[sortedElements[x]].getName());
	//		//					e.printStackTrace(ListenerManager.getCurrentListenerManager().getOutput());
	//		//				}
	//		//				catch (final IllegalAccessException iae) {
	//		//					iae.printStackTrace(ListenerManager.getCurrentListenerManager().getOutput());
	//		//				}
	//		//				catch (final NoSuchMethodException nsme) {
	//		//					nsme.printStackTrace(ListenerManager.getCurrentListenerManager().getOutput());
	//		//				}
	//		//				finally {
	//		//					buffer.setLength(0);
	//		//				}
	//		//			}
	//		//		}
	//		//		else {
	//		//			ListenerManager
	//		//				.getCurrentListenerManager()
	//		//				.firePatternChange(
	//		//				IListener.ENTITY_SKIPPED,
	//		//				new AnalysisEvent(currentClassName));
	//		//		}
	//		//
	//		//	}
	//	}
	//	public List compare(final IAbstractModel aPattern) {
	//		// Shadowing AbstractLevelModel's compare, because this method does not make sense here.
	//		return null;
	//	}
	//	public Map compare(final PatternRepository aPatternsRepository) {
	//		final Map results = new HashMap();
	//		for (int x = 0;
	//			x < aPatternsRepository.listOfPatterns().length;
	//			x++) {
	//			(
	//				(IPatternModel) aPatternsRepository
	//					.listOfPatterns()[x])
	//					.setDetector(
	//				new Detector());
	//			results.put(
	//				aPatternsRepository.listOfPatterns()[x].getName(),
	//				aPatternsRepository.listOfPatterns()[x].compare(this));
	//		}
	//		return results;
	//	}
	//	public static void main(final String args[]) {
	//		// I parse the arguments (if any)
	//		String path =
	//			"C:/Documents and Settings/Yann/Work/Workspace/Ptidej Tests/ptidej/tests/composite2/";
	//		if (args != null) {
	//			if (args.length == 1) {
	//				path = args[0];
	//			}
	//			else {
	//				if (args.length != 0) {
	//					ListenerManager.getCurrentListenerManager().getOutput().println(
	//						"Usage: java padl.kernel.abstractlevel.IdiomLevelModel <classpath> <packge name>");
	//				}
	//			}
	//		}
	//
	//		final IIdiomLevelModel idiomLevelModel =
	//			Factory.getUniqueInstance().createIdiomLevelModel(path);
	//		final ModelStatistics patternStatistics = new ModelStatistics();
	//		ListenerManager.getCurrentListenerManager().addPatternListener(
	//			patternStatistics);
	//
	//		try {
	//			// Building the program representation.
	//			idiomLevelModel.create(
	//				new ClassFileCompleteCreator(
	//					Loader.loadSubtypesFromDir(null, path, ".class")));
	//			ListenerManager.getCurrentListenerManager().getOutput().println();
	//			ListenerManager.getCurrentListenerManager().getOutput().println(patternStatistics);
	//
	//			// Detecting design pattern.
	//			ListenerManager.getCurrentListenerManager().getOutput().println();
	//			final Map solutions =
	//				idiomLevelModel.compare(
	//					PatternRepository.getCurrentPatternRepository());
	//
	//			// Display solutions...
	//			final Iterator iterator = solutions.keySet().iterator();
	//			while (iterator.hasNext()) {
	//				final String currentPattern = (String) iterator.next();
	//				final List patternSolutions =
	//					(List) solutions.get(currentPattern);
	//				ListenerManager.getCurrentListenerManager().getOutput().println(
	//					"* Model: "
	//						+ currentPattern
	//						+ ": "
	//						+ patternSolutions.size()
	//						+ " instance(s).");
	//				if (patternSolutions.size() > 0) {
	//					final Iterator iterator2 = patternSolutions.iterator();
	//					while (iterator2.hasNext()) {
	//						final Map currentSol = (Map) iterator2.next();
	//						final Iterator iterator3 =
	//							currentSol.keySet().iterator();
	//						while (iterator3.hasNext()) {
	//							final String currentConstituent =
	//								(String) iterator3.next();
	//							ListenerManager.getCurrentListenerManager().getOutput().print(currentConstituent);
	//							ListenerManager.getCurrentListenerManager().getOutput().println(':');
	//							final Iterator iterator4 =
	//								((List) currentSol.get(currentConstituent))
	//									.iterator();
	//							while (iterator4.hasNext()) {
	//								ListenerManager.getCurrentListenerManager().getOutput().print('\t');
	//								ListenerManager.getCurrentListenerManager().getOutput().println(
	//									((IEntity) iterator4.next()).getName());
	//							}
	//							ListenerManager.getCurrentListenerManager().getOutput().println();
	//						}
	//						ListenerManager.getCurrentListenerManager().getOutput().println("----");
	//					}
	//				}
	//			}
	//		}
	//		catch (final Exception e) {
	//			e.printStackTrace(ListenerManager.getCurrentListenerManager().getOutput());
	//		}
	//	}
	//	public IDesignLevelModel upgrade(final String aName) {
	//		final IDesignLevelModel designLevelModel =
	//			Factory.getInstance().createDesignLevelModel(aName);
	//
	//		this.clone(designLevelModel);
	//
	//		return designLevelModel;
	//	}
}
