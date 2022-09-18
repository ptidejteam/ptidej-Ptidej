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
package padl.creator.cppfile.eclipse.plugin.internal;

import java.util.HashSet;
import java.util.Set;
import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IBinding;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPBase;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPClassType;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPConstructor;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPField;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPFunction;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPMethod;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPParameter;
import org.eclipse.cdt.core.index.IIndex;
import org.eclipse.cdt.core.index.IIndexManager;
import org.eclipse.cdt.core.model.CModelException;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import padl.cpp.kernel.IAmicable;
import padl.cpp.kernel.IGlobalFunction;
import padl.cpp.kernel.impl.CPPFactoryEclipse;
import padl.creator.cppfile.eclipse.Common;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IOperation;
import padl.kernel.IPackage;
import padl.util.ModelStatistics;
import util.io.ProxyConsole;

public class GeneratorFromCPPProject {
	private Accumulator accumulator;
	private Set<IASTTranslationUnit> astTranslationUnits;
	private ICodeLevelModel codeLevelModel;
	private ICProject cppProject;
	private GeneratorHelper generatorHelper;
	private IIndex index;
	private ModelStatistics modelStatistics;

	public GeneratorFromCPPProject() {
		this.codeLevelModel =
			CPPFactoryEclipse.getInstance().createCodeLevelModel("CppModel");

		final IPackage defaultPackage =
			CPPFactoryEclipse.getInstance().createPackageDefault();
		this.codeLevelModel.addConstituent(defaultPackage);
	}
	public ICodeLevelModel build() {
		//	Output.getInstance().debugOutput().println("Creation");
		//	this.projectCreation();

		ProxyConsole.getInstance().debugOutput().println("Initialisation");
		this.initializeGeneration();

		ProxyConsole.getInstance().debugOutput().println("Build");
		ProxyConsole.getInstance().debugOutput().println("\tResolving classes");
		this.handleNamespacesAndClasses();

		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("\tResolving nested classes");
		this.handleNestedClasses();

		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("\tResolving global variables");
		this.handleGlobalFunctionsAndVariables();

		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("\tResoving hierarchies");
		this.handleClassHierarchies();

		ProxyConsole.getInstance().debugOutput().println("\tResolving fields");
		this.handleClassFields();

		ProxyConsole.getInstance().debugOutput().println("\tResolving methods");
		this.handleClassMethods();

		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("\tResolving friendships");
		this.handleClassFriendships();

		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("\tResolving parameters");
		this.handleOperationParameters();

		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("\tResolving method invocations");
		this.handleMethodInvocations();

		ProxyConsole.getInstance().debugOutput().println("Finalisation");
		this.finalizeGeneration();

		ProxyConsole
			.getInstance()
			.debugOutput()
			.print(this.codeLevelModel.getNumberOfTopLevelEntities());
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println(" top-level entities found.");

		return this.codeLevelModel;
	}
	/**
	 * Finalize the PADL Generation
	 * @throws Exception 
	 */
	private void finalizeGeneration() {
		this.index.releaseReadLock();
		final IWorkspace ws = ResourcesPlugin.getWorkspace();
		final IProject project =
			ws.getRoot().getProject(Constants.CPP_PROJECT_NAME);
		try {
			project.close(Common.NULL_PROGRESS_MONITOR);
			ws.save(true, Common.NULL_PROGRESS_MONITOR);
		}
		catch (final CoreException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		ProxyConsole.getInstance().normalOutput().println(this.modelStatistics);
	}
	private void handleClassFields() {
		for (final ICPPClassType classType : this.accumulator.getClassTypes()) {
			for (final ICPPField field : classType.getDeclaredFields()) {
				final IFirstClassEntity clazz =
					SearchHelper.getExistingFirstClassEntityOrCreateGhost(
						this.codeLevelModel,
						this.accumulator,
						classType);
				this.generatorHelper.addFieldToClass(field, clazz);
			}
		}
	}
	private void handleClassFriendships() {
		for (final ICPPClassType classType : this.accumulator.getClassTypes()) {
			for (final IBinding friendType : classType.getFriends()) {
				final IFirstClassEntity clazz =
					SearchHelper.getExistingFirstClassEntityOrCreateGhost(
						this.codeLevelModel,
						this.accumulator,
						classType);

				if (friendType instanceof ICPPClassType) {
					final IFirstClassEntity friendClass =
						SearchHelper.getExistingFirstClassEntityOrCreateGhost(
							this.codeLevelModel,
							this.accumulator,
							(ICPPClassType) friendType);
					((IAmicable) clazz).addFriendClass(friendClass);
				}
				else if (friendType instanceof ICPPFunction) {
					final IGlobalFunction friendFunction =
						(IGlobalFunction) SearchHelper
							.getExistingFirstClassEntityOrCreateGhost(
								this.codeLevelModel,
								(ICPPFunction) friendType);
					((IAmicable) clazz).addFriendFunction(friendFunction);
				}
			}
		}
	}
	private void handleClassHierarchies() {
		for (final ICPPClassType daughterType : this.accumulator
			.getClassTypes()) {

			for (final ICPPBase base : daughterType.getBases()) {
				// It is well possible that neither the baseType
				// nor the daughterType have corresponding 
				// IFirstClassEntity because:
				// (1) The baseType may be a "problem", i.e., 
				// may not be bound and, similarly, (2) the
				// daughterType may also have been a "problem".
				// In these case, I must create corresponding
				// and appropriate Ghosts, including their 
				// their possible namespaces.
				final ICPPClassType baseType =
					(ICPPClassType) base.getBaseClass();
				if (baseType == null) {
					// If something is wrong with the hierarchy, next!
					break;
				}

				final IFirstClassEntity baseClass =
					SearchHelper.getExistingFirstClassEntityOrCreateGhost(
						this.codeLevelModel,
						this.accumulator,
						baseType);

				final IFirstClassEntity daughterClass =
					SearchHelper.getExistingFirstClassEntityOrCreateGhost(
						this.codeLevelModel,
						this.accumulator,
						daughterType);

				daughterClass.addInheritedEntity(baseClass);
			}
		}
	}
	private void handleClassMethods() {
		for (final ICPPClassType classType : this.accumulator.getClassTypes()) {
			for (final ICPPConstructor constructor : classType
				.getConstructors()) {

				final IFirstClassEntity clazz =
					SearchHelper.getExistingFirstClassEntityOrCreateGhost(
						this.codeLevelModel,
						this.accumulator,
						classType);
				this.generatorHelper.addMethodToClass(
					this.accumulator,
					constructor,
					Utils.getBody(this.astTranslationUnits, constructor),
					clazz);
			}
			for (final ICPPMethod method : classType.getDeclaredMethods()) {
				final IFirstClassEntity clazz =
					SearchHelper.getExistingFirstClassEntityOrCreateGhost(
						this.codeLevelModel,
						this.accumulator,
						classType);
				this.generatorHelper.addMethodToClass(
					this.accumulator,
					method,
					Utils.getBody(this.astTranslationUnits, method),
					clazz);
			}
		}
	}
	private void handleGlobalFunctionsAndVariables() {
		this.visitTranslationUnits(new VisitorGlobalFunctionsAndVariables(
			this.codeLevelModel,
			this.generatorHelper,
			this.accumulator));
	}
	private void handleMethodInvocations() {
		this.visitTranslationUnits(new VisitorMethodInvocations(
			this.codeLevelModel,
			this.generatorHelper,
			this.accumulator));
	}
	private void handleNamespacesAndClasses() {
		this.visitTranslationUnits(new VisitorNamespacesAndClasses(
			this.codeLevelModel,
			this.generatorHelper,
			this.accumulator));
	}
	private void handleNestedClasses() {
		this.handleNestedClasses(this.accumulator);
	}
	private void handleNestedClasses(final Accumulator anAccumulator) {
		final Accumulator temporaryAccumulator = new Accumulator();
		for (final ICPPClassType classType : anAccumulator.getClassTypes()) {
			for (final ICPPClassType nestedType : classType.getNestedClasses()) {
				final IFirstClassEntity clazz =
					SearchHelper.getExistingFirstClassEntityOrCreateGhost(
						this.codeLevelModel,
						this.accumulator,
						classType);
				// Yann 2013/09/10: Weird "nested" classes!
				// It seems that nested classes include friends so
				// before created a member class, I check if the
				// "nested" (friend) class does not already exist.
				// If it does, I do not create a spurious member.
				// Yet, this test reads weird... very weird...
				this.generatorHelper.addMemberToClass(
					temporaryAccumulator,
					nestedType,
					clazz);
			}
		}
		// Before recursing, I don't forget to keep
		// track of all the class-types and functions in
		// the "master" accumulator for later usages...
		anAccumulator.addClassTypesAndFunctions(temporaryAccumulator);

		// Recursion
		if (temporaryAccumulator.hasClassTypes()) {
			this.handleNestedClasses(temporaryAccumulator);
		}
	}
	private void handleOperationParameters() {
		for (final ICPPFunction cppFunction : this.accumulator.getFunctions()) {
			final IOperation operation =
				this.accumulator.getOperation(cppFunction);

			for (final ICPPParameter parameter : cppFunction.getParameters()) {
				this.generatorHelper.addParameterToOperation(
					this.accumulator,
					parameter,
					operation);
			}
		}
	}
	/**
	 * Initialise the PADL Generation
	 * @throws Exception 
	 */
	private void initializeGeneration() {
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		try {
			ProxyConsole
				.getInstance()
				.debugOutput()
				.println("Workspace root exist at " + root.getLocationURI());
			root.refreshLocal(
				IResource.DEPTH_INFINITE,
				Common.NULL_PROGRESS_MONITOR);
		}
		catch (final CoreException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		final IProject project = root.getProject(Constants.CPP_PROJECT_NAME);
		try {
			if (!project.exists()) {
				project.create(Common.NULL_PROGRESS_MONITOR);
			}
			project.open(Common.NULL_PROGRESS_MONITOR);
			ProxyConsole
				.getInstance()
				.debugOutput()
				.println("Is Eclipse project open: " + project.isOpen());
			project.refreshLocal(
				IResource.DEPTH_INFINITE,
				Common.NULL_PROGRESS_MONITOR);
		}
		catch (final CoreException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		this.cppProject =
			CoreModel.getDefault().getCModel().getCProject(project.getName());
		try {
			this.cppProject.open(Common.NULL_PROGRESS_MONITOR);
			ProxyConsole
				.getInstance()
				.debugOutput()
				.println("Is C++ project open: " + this.cppProject.isOpen());
			this.cppProject.makeConsistent(Common.NULL_PROGRESS_MONITOR);
		}
		catch (final CoreException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		final IIndexManager manager = CCorePlugin.getIndexManager();
		manager.reindex(this.cppProject);
		manager
			.joinIndexer(IIndexManager.FOREVER, Common.NULL_PROGRESS_MONITOR);
		try {
			this.index = manager.getIndex(this.cppProject);
			this.index.acquireReadLock();
		}
		catch (final CoreException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final InterruptedException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		this.accumulator = new Accumulator();
		this.generatorHelper =
			new GeneratorHelper(this.astTranslationUnits, this.codeLevelModel);

		this.modelStatistics = new ModelStatistics();
		this.codeLevelModel.addModelListener(this.modelStatistics);
	}
	private void visitTranslationUnits(final ASTVisitor aVisitor) {
		// Yann 2013/07/16: Consistency
		// I make sure to use exactly the same transitional 
		// units for different calls to this method.
		if (this.astTranslationUnits == null) {
			this.astTranslationUnits = new HashSet<IASTTranslationUnit>();

			final int astStyle =
				ITranslationUnit.AST_CONFIGURE_USING_SOURCE_CONTEXT
						| ITranslationUnit.AST_SKIP_INDEXED_HEADERS;

			// Parsing of the source code
			try {
				// Yann 2013/05/18: Hidden Translation Units!
				// There may be no translation units in the root directory
				// because they may be hidden in subdirectories, like in QT
				// so I must find them all first...
				final Set<ITranslationUnit> translationUnits =
					Utils.findTranslationUnits(this.cppProject
						.getAllSourceRoots());

				for (final ITranslationUnit unit : translationUnits) {
					try {
						final IASTTranslationUnit astTranslationUnit =
							unit.getAST(this.index, astStyle);
						this.astTranslationUnits.add(astTranslationUnit);
					}
					catch (final NullPointerException e) {
						// The AST may be null but there does not seem to
						// exist a reliable way to know in advance whether
						// it is null or not...
					}
				}
			}
			catch (final CModelException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
			catch (final CoreException e) {
				e.printStackTrace();
			}
		}

		for (IASTTranslationUnit astTranslationUnit : this.astTranslationUnits) {
			astTranslationUnit.accept(aVisitor);
		}
	}
}
