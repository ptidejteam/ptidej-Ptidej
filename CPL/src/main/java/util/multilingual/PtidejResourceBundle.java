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
package util.multilingual;

import java.util.ListResourceBundle;

/**
 * @author Mehdi Lahlou
 * @author Yann-Gaël Guéhéneuc
 * @since  18/05/2005
 */
public class PtidejResourceBundle extends ListResourceBundle {
	private static final String VERSION = "v5.8.2";
	private static final Object[][] CONTENTS =
		{
				// *********** CPL ***********
				{ "util.awt.NameDialog::BUTTON_LABEL", "Okay" },
				{ "util.awt.NameDialog::TITLE", "Notice" },
				{ "util.help.Browser::BROWSER_URL", "http://www.javaworld.com" },
				{ "util.help.Browser::Err_BRINGING_BROWSER",

				"Error bringing up browser with command : {0}" },
				{ "util.help.Browser::Err_INVOKE_BROWSER",

				"Could not invoke browser with command : {0}" },
				{ "util.io.LogoBytesArrayBuilder::Err_BUILDING_BYTES",
						"Error while building bytes array." },
				{ "util.io.SubtypeLoader::Err_FILES_NOT_FOUND",
						"Loader: No files found into {0}" },
				{ "util.io.SubtypeLoader::Err_READING_FILE",
						"Loader: Error while reading file {0}" },
				{ "util.io.SubtypeLoader::LOADING_FROM", "Loading from: {0}" },
				{ "util.lang.ClassLoader::Err_FILE",
						"The file {0} produces an error." },

				// *********** Metrical Ptidej Solver ***********
				{
						"ptidej.solver.fingerprint.Fingerprint::IAException_UNKNOWN_OP",
						"Unknown operator" },
				{ "ptidej.solver.fingerprint.MetricUnknownException::STR",
						"Unknown metric" },
				{ "ptidej.solver.fingerprint.Problem::USING_METRICS",

				"++ Using metrics to fingerprint roles and to reduce search space efficiently" },
				{
						"ptidej.solver.fingerprint.problem.AbstractFactoryMotif::Problem_FMDM",
						"Factory method Design Motif" },
				{
						"ptidej.solver.fingerprint.problem.AdapterMotif::Problem_ADM",
						"Adapter Design Motif" },
				{
						"ptidej.solver.fingerprint.problem.CommandMotif::Problem_CDM",
						"Command Design Motif" },
				{
						"ptidej.solver.fingerprint.problem.CompositeMotif::Problem_CDM",
						"Composite Design Motif" },
				{
						"ptidej.solver.fingerprint.problem.FactoryMethodMotif::Problem_FMDM",
						"Factory method Design Motif" },

				// *********** PADL Analyses ***********
				{ "padl.analysis.Repository::LOAD_ANALYSIS",
						"Cannot load analysis {0} :\n{1}" },
				{
						"padl.analysis.micropattern.MicroPatternDetectionRepository::LOAD_ANALYSIS",
						"Cannot load analysis {0} :\n{1}" },
				{
						"padl.analysis.repository.SystematicUMLAnalysis::ANALYSIS_TIME",
						"Analysis time: {0} ms." },
				{ "padl.analysis.repository.SystematicUMLAnalysis::NAME",
						"Systematic UML" },

				// *********** PADL Creators ***********
				{ "padl.creator.aolfile.AOLCreator::CLASSES", " classes..." },
				{ "padl.creator.aolfile.AOLCreator::FILTERING_DONE",
						"Filtering done." },
				{ "padl.creator.aolfile.AOLCreator::PARSING", "Parsing" },
				{ "padl.creator.aolfile.AOLCreator::PARSING_DONE",
						"Parsing done." },
				{ "padl.creator.parser.AOLLexer::Err_BAD_IN_STREAM_INIT",
						"Error: Bad input stream initializer." },
				{ "padl.creator.parser.AOLLexer::Err_REPORT",

				"Error: {0}\nInfo: {1}\nLine: {2} Char: {3} Text: \"{4}\"" },
				{ "padl.creator.parser.AOLLexer::FATAL_ERR", "Fatal Error.\n" },
				{ "padl.creator.parser.AOLLexer::ILLEGAL_CHAR",
						"Illegal character: {0}" },
				{ "padl.creator.parser.AOLLexer::LEXICAL_ERR",
						"Lexical Error: Unmatched Input." },
				{ "padl.creator.parser.AOLCodeParser::INVALID_ACTION_NUMBER",

				"Invalid action number found in internal parse table" },
				{ "padl.creator.parser.AOLCodeParser::SYN_ERR",
						"Syntax error ({0})" },
				{ "padl.creator.aolfile.AOLIdiomParser::SYN_ERR",
						"Syntax error ({0})" },
				{
						"padl.creator.aolfile.AOLDesignParser::INVALID_ACTION_NUMBER",

						"Invalid action number found in internal parse table" },
				{ "padl.creator.aolfile.AOLDesignParser::SYN_ERR",
						"Syntax error ({0})" },
				{ "padl.creator.AspectCreator::CALL_AJC", "> Calling ajc..." },
				{ "padl.creator.AspectCreator::COMPILER_FAIL",
						"Compiler - Fail" },
				{ "padl.creator.AspectCreator::TERM_AJC", "> ajc terminate..." },
				{ "padl.creator.parserClassScope::TYPE_NAME",
						"ClassScope.IsTypeName: {0}" },

				{ "padl.creator.msefile.MSECreator::PARSING", "Parsing" },
				{ "padl.creator.msefile.MSECreator::PARSING_DONE", "Parsing done." },
				{ "padl.creator.msefile.MSELexer::Err_BAD_IN_STREAM_INIT",
						"Error: Bad input stream initializer." },
				{ "padl.creator.msefile.MSELexer::Err_REPORT", "Error: {0}\nInfo: {1}\nLine: {2} Char: {3} Text: \"{4}\"" },
				{ "padl.creator.msefile.MSEParser::SYN_ERR", "Syntax error ({0})" },

				// *********** PADL ***********
				{ "padl.kernel.impl.CodeLevelModel::ENT_ADD_ORG_LEVEL",

				"Cannot add entity directly in a model, must add a package first" },
				{ "padl.kernel.impl.Class::ALREADY_IMPL",
						"{0} is already implemented by {1}" },
				{ "padl.kernel.impl.Class::NOT_IMPL",
						"{0} is not implemented by {1}" },
				{ "padl.kernel.impl.Constituent::ACTOR_ID_NULL",
						"An actorID cannot be null" },
				{ "padl.kernel.impl.Constituent::ELEM_ABSTRACT",
						"This element can't be abstract" },
				{ "padl.kernel.impl.Constituent::ELEM_CODE_DEF",
						"This Element can't contain code definition" },
				{ "padl.kernel.impl.Constituent::Exception_ACCEPT_METHOD",
						"Accept method: {0} ({1})" },
				{ "padl.kernel.impl.Operation::PARAM_OR_METHOD_ADD",

				"Only a parameter or a method invocation can be added to a method." },
				{ "padl.kernel.impl.ContainerAggregation::ELEMS_ATTACH",

				"Elements only can be attached to a container aggregation at instantiation." },
				{ "padl.kernel.impl.DelegatingMethod::ABSTRACT",
						"{0} can't be abstract." },
				{ "padl.motif.kernel.impl.DesignLevelModel::ENT_OR_PATTERN_ADD",

				"Only an entity or a pattern can be added to a design-level model." },
				{ "padl.kernel.IElement::ATTACH",
						"{0} cannot be attached to {1}" },
				{ "padl.kernel.IElement::ELEM_ATTACH",
						"A Element cannot be attached to itself." },
				{ "padl.kernel.IFirstClassEntity::ALREADY_INHERITED",
						"{0} is already inherited from {1}" },
				{ "padl.kernel.IFirstClassEntity::ELEM_ADD_ENT",
						"Only an element can be added to an entity." },
				{ "padl.kernel.IFirstClassEntity::ENT_INHERIT_ITSELF",
						"An entity cannot inherit from itself" },
				{ "padl.kernel.impl.Factory::ADD",
						"Cannot add array or primitive types." },
				{ "padl.kernel.impl.Field::CARDINALITY",

				"The cardinality of a field must be one or more (was {0})" },
				{ "padl.kernel.impl.GlobalField::CARDINALITY",

				"The cardinality of a globalField must be one or more (was {0})" },
				{ "padl.kernel.impl.IdiomLevelModel::ENT_ADD_ORG_LEVEL",

				"Only an entity can be added to an organisation-level model." },
				{ "padl.kernel.impl.PatternModel::ENT_ADD_MODEL",
						"Only an entity can be added to a model." },
				{ "padl.kernel.impl.PatternModel::Err_INIT_ALMD",

				"Error: unable to initialize AbstractLevelModel Detector." },
				{ "padl.kernel.impl.Relationship::CARDINALITY",

				"The cardinality of a relationship must be one or more (was {0})" },
				{ "padl.kernel.impl.Package::ENTITY_ADD_PACKAGE",

				"Cannot add constituents that are NOT entities or packages to a package." },

				{
						"padl.motif.repository.ChainOfResponsibility::INTENT",

						"Avoid coupling the sender of a request\nto its receiver by giving more\nthan one object a chance to handle\nthe request. Chain the receiving objects\nand pass the request along\nthe chain until an object handles it." },
				{ "padl.motif.repository.Composite::Err_INIT_ALMD",

				"Error: unable to initialize the AbstractLevelModel detector." },
				{
						"padl.motif.repository.Composite::INTENT",

						"Compose objects into tree structures\nto represent part-whole hierarchies.\nComposite lets clients treat individual\nobjects and compositions of objects\nuniformly." },
				{
						"padl.motif.repository.Facade::INTENT",

						"Provide a unified interface to a set\nof interfaces in a subsystem.\nFacade defines a higher-level interface\nthat makes the subsystem easier to use." },
				{
						"padl.motif.repository.FactoryMethod::INTENT",

						"Define an interface for creating\na familly of objects, but let subclasses decide\nwhich class to instantiate.\nFactory Method lets a class defer\ninstantiation to subclasses." },
				{ "padl.motif.repository.GoodInheritance::INTENT",

				"Define that a superclass must ignore\ncompletely its subclass(es)." },
				{
						"padl.motif.repository.Mediator::INTENT",

						"Define an object that encapsulates\nhow a set of objects interact.\nMediator promotes loose coupling by keeping objects\nfrom referring to each other explicitly,\nand it lets you vary their interaction independently." },
				{
						"padl.motif.repository.Memento::INTENT",

						"Without violating encapsulation, capture\nand externalize an object's internal state\nso that the object can be restored to this state later." },
				{
						"padl.motif.repository.Observer::ConcreteObserver_CLASS_PURPOSE",

						"1. Maintains a reference to a ConcreteSubject object\n2. Stores state that should stay consistent with the subject's\n3. Implements the Observer updating interface to keep its state consistent with the subject's" },
				{
						"padl.motif.repository.Observer::ConcreteSubject_CLASS_PURPOSE",

						"1. Stores state of interest to ConcreteObserver objects\n2. Sends a notification to its observers when its state changes" },
				{ "padl.motif.repository.Observer::DELEG_METHOD_COMMENT",
						"Add specific operations in this method" },
				{ "padl.motif.repository.Observer::IDIOM",

				"Exist in package java.util as class Observer." },
				{
						"padl.motif.repository.Observer::INTENT",

						"Define a one-to-many dependancy\nbetween objects.\nWhen one object changes state,\nall its dependants are notified\nand updated automatically." },
				{
						"padl.motif.repository.Observer::Observer_PURPOSE",

						"Defines a common interface for objects that should be notified when the subject changes." },
				{
						"padl.motif.repository.Observer::Subject_PURPOSE",

						"1. Knows  its observers. Any number of Observer objects may observe a subject\n2. Provides an interface for attaching and detaching Observer objects" },
				{
						"padl.motif.repository.Proxy::INTENT",

						"Provide a surrogate or placeholder\nfor another object to control access to it." },
				{ "padl.motif.repository.Visitor::INTENT",
						"(See the GoF's book.)" },

				// *********** Ptidej Solver 4 ***********
				{ "ptidej.solver.java.combination.BitString::NB_OF_BITS",
						"The number of bits must be {0}" },
				{ "ptidej.solver.java.branching.InteractiveBranching::ANOTHER_SOL",
						"Do you want another solution ? (y/n)" },
				{ "ptidej.solver.java.branching.InteractiveBranching::SOL",
						"> Solution {0} :" },
				{
						"ptidej.solver.java.branching.InteractiveRepair::CONST_CONTRADICTION",

						"The following constraint(s) led to a contradiction:" },
				{ "ptidej.solver.java.branching.InteractiveRepair::NO_MORE_SOL",

				"There is no more solution because of the constraint" },
				{ "ptidej.solver.java.branching.InteractiveRepair::TO_BE_REPLACED",
						"To be replaced with: {0}" },
				{
						"ptidej.solver.java.branching.InteractiveRepair::WHICH_TO_PUT_BACK",

						"Which one do you want to put back? (-1 -> none)" },
				{ "ptidej.solver.java.branching.InteractiveRepair::WHICH_TO_RELAX",

				"Which one do you want to relax? (-1 -> none)" },
				{
						"ptidej.solver.java.Problem::PTIDEJ",

						"++ JPtidejSolver v1.5 (February, 2007), Copyright (c) 2001-2013 Y.-G. Guéhéneuc\n++ Constraint programming for design patterns and design defects identification" },
				{ "ptidej.solver.java.solver.SimpleInteractiveRepair::NO_MORE_SOL",

				"There is no more solution because of the constraints: {0}" },
				{
						"ptidej.solver.java.solver.SimpleInteractiveRepair::WHICH_TO_RELAX",

						"Which one do you want to relax? (-1 -> none)" },

				// *********** Ptidej UI Analyses ***********
				{
						"ptidej.ui.analysis.repository.DifferenceHighlighterFromClasses::DIALOG_TITLE",
						"Choose a file with model differences" },
				{
						"ptidej.ui.analysis.repository.DifferenceHighlighterFromClasses::NAME",

						"Model difference highlighter (from classes)" },
				{
						"ptidej.ui.analysis.repository.DifferenceHighlighterFromMethods::DIALOG_TITLE",
						"Choose a file with model differences" },
				{
						"ptidej.ui.analysis.repository.DifferenceHighlighterFromMethods::NAME",

						"Model difference highlighter (from methods)" },
				{
						"ptidej.ui.analysis.repository.comparator.HighlighterFromMethods::Err_UNKNOWN_KEY",
						"Unknown key in file : {0}" },
				{
						"ptidej.ui.analysis.repository.comparator.Manager::Err_METHODE_NOT_FOUND_IN_ENTITY",
						"Cannot find method \"{0}\" in entity {1}" },
				{
						"ptidej.ui.analysis.repository.ModelComparator::DIALOG_TITLE",
						"Choose an AOL file" },
				{ "ptidej.ui.analysis.repository.ModelComparator::NAME",
						"AOL models comparison" },
				{ "ptidej.ui.analysis.Repository::Err_LOAD_ANALYSIS",
						"Cannot load analysis {0} :{1}" },

				// *********** Ptidej UI Viewer Extensions ***********
				{
						"ptidej.viewer.extension.repository.CSVGraphReader::Err_SYNTAX_ERR",
						"Syntax error on line {0}" },
				{
						"ptidej.viewer.extension.repository.CSVGraphReader::Err_UNEXPECTED_TOKEN",

						"Unexpected token {0} read from stream, a word was expected" },
				{
						"ptidej.viewer.extension.repository.POMCalculator::COMPUTE_TIME",
						"Metrics computed in {0} ms.\n{1}" },
				{ "ptidej.viewer.extension.Repository::LOAD_EXTENSION",
						"Cannot load extension {0} :\n{1}" },

				// *********** Ptidej UI Viewer Plugin ***********
				{
						"Ptidej UI Viewer Plugin::AddFolderAction::Err_Exception_OPEN_DIAGRAM_EDITOR",

						"throwable exception while opening DiagramEditor!" },
				{
						"Ptidej UI Viewer Plugin::AddFolderAction::Err_INIT_DIAGRAM_EDITOR",

						"(class: PopActionClass) could not initialize Diagram Editor!" },
				{
						"Ptidej UI Viewer Plugin::AddFolderAction::Err_OPEN_DIAGRAM_EDITOR",
						"Error while opening DiagramEditor!" },
				{
						"Ptidej UI Viewer Plugin::AddFolderAction::MessageDialog_CONTENT",

						"Ptidej Viewer: No class files found in :\n{0}\nAction: {1}" },
				{
						"Ptidej UI Viewer Plugin::AddFolderAction::MessageDialog_TITLE",
						"Ptidej Viewer" },
				{
						"Ptidej UI Viewer Plugin::AddFolderAction::NO_CLASSFILE_FOUND",
						"No class files found in: {0}" },
				{
						"Ptidej UI Viewer Plugin::AddJavaAction::Err_Exception_IN_RUN",
						"Exception in run()" },
				{
						"Ptidej UI Viewer Plugin::AddJavaAction::Err_INIT_DIAGRAM_EDITOR",
						"Couldn't initialize DiagramEditor" },
				{
						"Ptidej UI Viewer Plugin::AddJavaAction::Err_NPException_IN_RUN",
						"NullPointerException in run()" },
				{ "Ptidej UI Viewer Plugin::AddJavaAction::Err_TException",
						"Thorowable Exception" },
				{
						"Ptidej UI Viewer Plugin::AddJDTPackage::Err_Exception_IN_RUN",
						"Exception in run()" },
				{
						"Ptidej UI Viewer Plugin::AddJDTPackage::Err_INIT_DIAGRAM_EDITOR",
						"Couldn't initialize DiagramEditor" },
				{
						"Ptidej UI Viewer Plugin::AddJDTPackage::Err_NPException_IN_RUN",
						"NullPointerException in run()" },
				{ "Ptidej UI Viewer Plugin::AddJDTPackage::Err_TException",
						"Thorowable Exception" },
				{
						"Ptidej UI Viewer Plugin::AddJDTPackageAction::Err_Exception_IN_RUN",
						"Exception in run()" },
				{
						"Ptidej UI Viewer Plugin::AddJDTPackageAction::Err_INIT_DIAGRAM_EDITOR",
						"Couldn't initialize DiagramEditor" },
				{
						"Ptidej UI Viewer Plugin::AddJDTPackageAction::Err_NPException_IN_RUN",
						"NullPointerException in run()" },
				{
						"Ptidej UI Viewer Plugin::AddJDTPackageAction::Err_TException",
						"Thorowable Exception" },
				{
						"Ptidej UI Viewer Plugin::AddJDTPackageAction::MessageDialog_CONTENT",

						"Ptidej Viewer: No class files found in :\n{0}\nAction: {1}" },
				{
						"Ptidej UI Viewer Plugin::AddJDTPackageAction::MessageDialog_TITLE",
						"Ptidej Viewer" },
				{
						"Ptidej UI Viewer Plugin::AddJDTPackageAction::NO_CLASSFILE_FOUND",
						"No class files found in: {0}" },
				{
						"Ptidej UI Viewer Plugin::AddNewFolderAction::Err_Exception_OPEN_DIAGRAM_EDITOR",

						"throwable exception while opening DiagramEditor!" },
				{
						"Ptidej UI Viewer Plugin::AddNewFolderAction::Err_INIT_DIAGRAM_EDITOR",

						"(class: PopActionClass) could not initialize Diagram Editor!" },
				{
						"Ptidej UI Viewer Plugin::AddNewFolderAction::Err_OPEN_DIAGRAM_EDITOR",
						"Error while opening DiagramEditor!" },
				{
						"Ptidej UI Viewer Plugin::AddNewFolderAction::MessageDialog_CONTENT",

						"Ptidej Viewer: No class files found in :\n{0}\nAction: {1}" },
				{
						"Ptidej UI Viewer Plugin::AddNewFolderAction::MessageDialog_TITLE",
						"Ptidej Viewer" },
				{
						"Ptidej UI Viewer Plugin::AddNewFolderAction::NO_CLASSFILE_FOUND",
						"No class files found in: {0}" },
				{
						"Ptidej UI Viewer Plugin::AddNewJavaAction::Err_Exception_IN_RUN",
						"Exception in run()" },
				{
						"Ptidej UI Viewer Plugin::AddNewJavaAction::Err_INIT_DIAGRAM_EDITOR",
						"Couldn't initialize DiagramEditor" },
				{
						"Ptidej UI Viewer Plugin::AddNewJavaAction::Err_NPException_IN_RUN",
						"NullPointerException in run()" },
				{ "Ptidej UI Viewer Plugin::AddNewJavaAction::Err_TException",
						"Thorowable Exception" },
				{
						"Ptidej UI Viewer Plugin::AddNewJDTPackage::Err_Exception_IN_RUN",
						"Exception in run()" },
				{
						"Ptidej UI Viewer Plugin::AddNewJDTPackage::Err_INIT_DIAGRAM_EDITOR",
						"Couldn't initialize DiagramEditor" },
				{
						"Ptidej UI Viewer Plugin::AddNewJDTPackage::Err_NPException_IN_RUN",
						"NullPointerException in run()" },
				{ "Ptidej UI Viewer Plugin::AddNewJDTPackage::Err_TException",
						"Thorowable Exception" },
				{
						"Ptidej UI Viewer Plugin::AddNewJDTPackageAction::Err_Exception_OPEN_DIAGRAM_EDITOR",

						"throwable exception while opening DiagramEditor!" },
				{
						"Ptidej UI Viewer Plugin::AddNewJDTPackageAction::Err_INIT_DIAGRAM_EDITOR",

						"(class: PopActionClass) could not initialize Diagram Editor!" },
				{
						"Ptidej UI Viewer Plugin::AddNewJDTPackageAction::Err_OPEN_DIAGRAM_EDITOR",
						"Error while opening DiagramEditor!" },
				{
						"Ptidej UI Viewer Plugin::AddNewJDTPackageAction::MessageDialog_CONTENT",

						"Ptidej Viewer: No class files found in :\n{0}\nAction: {1}" },
				{
						"Ptidej UI Viewer Plugin::AddNewJDTPackageAction::MessageDialog_TITLE",
						"Ptidej Viewer" },
				{
						"Ptidej UI Viewer Plugin::AddNewJDTPackageAction::NO_CLASSFILE_FOUND",
						"No class files found in: {0}" },
				{
						"Ptidej UI Viewer Plugin::AddSelectedPackageAction::Err_Exception_OPEN_DIAGRAM_EDITOR",

						"throwable exception while opening DiagramEditor!" },
				{
						"Ptidej UI Viewer Plugin::AddSelectedPackageAction::Err_INIT_DIAGRAM_EDITOR",

						"(class: PopActionClass) could not initialize Diagram Editor!" },
				{
						"Ptidej UI Viewer Plugin::AddSelectedPackageAction::Err_OPEN_DIAGRAM_EDITOR",
						"Error while opening DiagramEditor!" },
				{
						"Ptidej UI Viewer Plugin::AddSelectedPackageAction::MessageDialog_CONTENT",

						"Ptidej Viewer: No class files found in :\n{0}\nAction: {1}" },
				{
						"Ptidej UI Viewer Plugin::AddSelectedPackageAction::MessageDialog_TITLE",
						"Ptidej Viewer" },
				{
						"Ptidej UI Viewer Plugin::AddSelectedPackageAction::NO_CLASSFILE_FOUND",
						"No class files found in: {0}" },
				{ "Ptidej UI Viewer Plugin::DiagramEditor::CANVAS_IS_NULL",
						"Canvas == NULL" },
				{ "Ptidej UI Viewer Plugin::DiagramEditor::CANVAS_NOT_NULL",
						"Canvas NOT NULL" },
				{ "Ptidej UI Viewer Plugin::DiagramEditor::Err_FILE_NOT_FOUND",
						"Error: Couldn't find selected file!" },
				{ "Ptidej UI Viewer Plugin::DiagramEditor::Err_INVALID_INPUT",
						"Invalid Input: Must be IFileEditorInput" },
				{ "Ptidej UI Viewer Plugin::DiagramEditor::PROJECT_NAME",
						"File 1" },
				{ "Ptidej UI Viewer Plugin::FileList::DEFAULT_SELECT",
						"defaut selection" },
				{
						"Ptidej UI Viewer Plugin::FindConcretePatterns::MessageDialog_CONTENT",
						"Action: {0}" },
				{
						"Ptidej UI Viewer Plugin::FindConcretePatterns::MessageDialog_TITLE",
						"Ptidej Viewer" },
				{
						"Ptidej UI Viewer Plugin::GUIOptions::Button_FIND_CONCRETE_PATTERNS",
						"Find Occurrences" },
				{ "Ptidej UI Viewer Plugin::GUIOptions::Button_GEN_EXEC_INFO",
						"Generate Execution Information" },
				{ "Ptidej UI Viewer Plugin::GUIOptions::Button_GEN_MODEL",
						"Generate Model" },
				{
						"Ptidej UI Viewer Plugin::GUIOptions::Button_LOAD_CONCRETE_PATTERNS",
						"Load Occurrences" },
				{
						"Ptidej UI Viewer Plugin::GUIOptions::Button_MODIFY_STRUCTURE",
						"Modify Structure" },
				{
						"Ptidej UI Viewer Plugin::GUIOptions::Button_REMOVE_ALL_CONCRETE_PATTERNS",
						"Remove All Occurrences" },
				{ "Ptidej UI Viewer Plugin::GUIOptions::Button_SHOW_SOLUTIONS",
						"Show Occurrences" },
				{ "Ptidej UI Viewer Plugin::GUIOptions::FILE_DIALOG_TITLE",
						"Choose Identification Result File" },
				{ "Ptidej UI Viewer Plugin::GUIOptions::Group_OPTIONS",
						"Options" },
				{ "Ptidej UI Viewer Plugin::NewProject::MessageDialog_CONTENT",
						"Action: {0}" },
				{ "Ptidej UI Viewer Plugin::NewProject::MessageDialog_TITLE",
						"JTU Viewer" },
				{
						"Ptidej UI Viewer Plugin::PopActionClass::Err_INIT_DIAGRAM_EDITOR",

						"Could not initialize diagram editor!" },
				{
						"Ptidej UI Viewer Plugin::PopActionClass::Err_OPEN_DIAGRAM_EDITOR",
						"Error while opening diagram editor!" },
				{
						"Ptidej UI Viewer Plugin::PopActionJar::Err_INIT_DIAGRAM_EDITOR",

						"Could not initialize diagram editor!" },
				{
						"Ptidej UI Viewer Plugin::PopActionJava::Err_Exception_IN_RUN",
						"Exception in run()" },
				{
						"Ptidej UI Viewer Plugin::PopActionJava::Err_INIT_DIAGRAM_EDITOR",
						"Could not initialize diagram editor!" },
				{
						"Ptidej UI Viewer Plugin::PopActionJava::Err_NPException_IN_RUN",
						"NullPointerException in run()" },
				{
						"Ptidej UI Viewer Plugin::PopActionJavaPackage::Err_Exception_IN_RUN",
						"Exception in run()" },
				{
						"Ptidej UI Viewer Plugin::PopActionJavaPackage::Err_INIT_DIAGRAM_EDITOR",
						"Could not initialize diagram editor!" },
				{
						"Ptidej UI Viewer Plugin::PopActionJavaPackage::Err_NPException_IN_RUN",
						"NullPointerException in run()" },
				{
						"Ptidej UI Viewer Plugin::PopActionNewClass::Err_INIT_DIAGRAM_EDITOR",

						"Could not initialize diagram editor!" },
				{
						"Ptidej UI Viewer Plugin::PopActionNewJar::Err_INIT_DIAGRAM_EDITOR",

						"Could not initialize diagram editor!" },
				{
						"Ptidej UI Viewer Plugin::PopActionNewJava::Err_Exception_IN_RUN",
						"Exception in run()" },
				{
						"Ptidej UI Viewer Plugin::PopActionNewJava::Err_INIT_DIAGRAM_EDITOR",
						"Couldn't initialize DiagramEditor" },
				{
						"Ptidej UI Viewer Plugin::PopActionNewJavaPackage::Err_Exception_IN_RUN",
						"Exception in run()" },
				{
						"Ptidej UI Viewer Plugin::PopActionNewJavaPackage::Err_INIT_DIAGRAM_EDITOR",
						"Could not initialize diagram editor!" },
				{
						"Ptidej UI Viewer Plugin::PopActionNewJavaPackage::Err_NPException_IN_RUN",
						"NullPointerException in run()" },
				{
						"Ptidej UI Viewer Plugin::PopActionNewPackage::Err_Exception_INIT_DIAGRAM_EDITOR",

						"Could not initialize diagram editor!" },
				{
						"Ptidej UI Viewer Plugin::PopActionNewPackage::Err_INIT_DIAGRAM_EDITOR",

						"Could not initialize diagram editor!" },
				{
						"Ptidej UI Viewer Plugin::PopActionNewPtidej::Err_INIT_DIAGRAM_EDITOR",
						"Could not initialize diagram editor!" },
				{
						"Ptidej UI Viewer Plugin::PopActionPackage::Err_INIT_DIAGRAM_EDITOR",
						"Could not initialize diagram editor!" },
				{
						"Ptidej UI Viewer Plugin::PopActionPackage::Err_OPEN_DIAGRAM_EDITOR",
						"Could not initialize diagram editor!" },
				{
						"Ptidej UI Viewer Plugin::PopActionPtidej::Err_INIT_DIAGRAM_EDITOR",
						"Could not initialize diagram editor!" },
				{ "Ptidej UI Viewer Plugin::Preferences::AGGREGATION_DISPLAY",
						"Aggregation display" },
				{ "Ptidej UI Viewer Plugin::Preferences::AGGREGATION_NAMES",
						"Aggregation names" },
				{ "Ptidej UI Viewer Plugin::Preferences::ASSOCIATION_DISPLAY",
						"Association display" },
				{ "Ptidej UI Viewer Plugin::Preferences::ASSOCIATION_NAMES",
						"Association names" },
				{ "Ptidej UI Viewer Plugin::Preferences::COMPOSITION_DISPLAY",
						"Composition display" },
				{ "Ptidej UI Viewer Plugin::Preferences::COMPOSITION_NAMES",
						"Composition names" },
				{
						"Ptidej UI Viewer Plugin::Preferences::CONTAINER_AGGREGATION_DISPLAY",
						"Container aggregation display" },
				{
						"Ptidej UI Viewer Plugin::Preferences::CONTAINER_AGGREGATION_NAMES",
						"Container aggregation names" },
				{
						"Ptidej UI Viewer Plugin::Preferences::CONTAINER_COMPOSITION_DISPLAY",
						"Container composition display" },
				{
						"Ptidej UI Viewer Plugin::Preferences::CONTAINER_COMPOSITION_NAMES",
						"Container composition names" },
				{ "Ptidej UI Viewer Plugin::Preferences::CREATION_DISPLAY",
						"Creation display" },
				{ "Ptidej UI Viewer Plugin::Preferences::CREATION_NAMES",
						"Creation names" },
				{ "Ptidej UI Viewer Plugin::Preferences::FIELD_NAMES",
						"Field names" },
				{
						"Ptidej UI Viewer Plugin::Preferences::FULLY_QUALIFIED_NAMES",
						"Fully qualified names" },
				{
						"Ptidej UI Viewer Plugin::Preferences::GHOST_ENTITIES_DISPLAY",
						"Ghost entities display" },
				{ "Ptidej UI Viewer Plugin::Preferences::HIERARCHY_DISPLAY",
						"Hierarchy display" },
				{ "Ptidej UI Viewer Plugin::Preferences::HIERARCHY_NAMES",
						"Hierarchy names" },
				{ "Ptidej UI Viewer Plugin::Preferences::USE_DISPLAY",
						"Use relationship display" },
				{ "Ptidej UI Viewer Plugin::Preferences::USE_NAMES",
						"Use relationship names" },
				{ "Ptidej UI Viewer Plugin::Preferences::METHOD_NAMES",
						"Method names" },
				{
						"Ptidej UI Viewer Plugin::PtidejDiagramEditor::Err_INVALID_INPUT",
						"Invalid Input: Must be IFileEditorInput" },
				{ "Ptidej UI Viewer Plugin::PtidejDiagramEditor::LOADING_TIME",
						"Loading time: {0} ms.\n\n" },
				{
						"Ptidej UI Viewer Plugin::PtidejDiagramEditor::STATISTICS",

						"\nStatistics:\n{0}\n              Total memory\tFree memory\nBefore build  {1}\t\t{2}\t\t{3}\nAfter build   {4}\t\t{5}\t\t{6}\nAfter display {7}\t\t{8}\t\t{9}\n" },
				{
						"Ptidej UI Viewer Plugin::RemoveConcretePatterns::MessageDialog_CONTENT",
						"Action: {0}" },
				{
						"Ptidej UI Viewer Plugin::RemoveConcretePatterns::MessageDialog_TITLE",
						"Ptidej Viewer" },
				{
						"Ptidej UI Viewer Plugin::SaveProject::MessageDialog_CONTENT",
						"Action: {0}" },
				{ "Ptidej UI Viewer Plugin::SaveProject::MessageDialog_TITLE",
						"Ptidej Viewer" },

				// *********** Ptidej UI Viewer Standalone AWT ***********
				{
						"ptidej.viewer.core.ConstraintViewerPanel::CHECKBOXES_TEXTS_R01",
						"Custom problem" },
				{
						"ptidej.viewer.core.ConstraintViewerPanel::CHECKBOXES_TEXTS_R02",
						"AC-4 problem" },
				{
						"ptidej.viewer.core.ConstraintViewerPanel::CHECKBOXES_TEXTS_R03",
						"Simple automatic solver" },
				{
						"ptidej.viewer.core.ConstraintViewerPanel::CHECKBOXES_TEXTS_R04",
						"Combinatorial automatic solver" },
				{
						"ptidej.viewer.core.ConstraintViewerPanel::CHECKBOXES_TEXTS_R05",
						"Automatic solver" },
				{
						"ptidej.viewer.core.ConstraintViewerPanel::FILE_DIALOG_CHOOSE_CRF_TITLE",
						"Choose constraints result file" },
				{
						"ptidej.viewer.core.ConstraintViewerPanel::FILE_DIALOG_SAVE_MODEL_TITLE",
						"Save Claire domain to..." },
				{
						"ptidej.viewer.core.ConstraintViewerPanel::FIND_SIMILAR_MA_TEXT",
						"Find similar micro-architectures" },
				{
						"ptidej.viewer.core.ConstraintViewerPanel::GENERATE_PROGRAM_MODEL",
						"Generate program model" },
				{
						"ptidej.viewer.core.ConstraintViewerPanel::GENERATE_SOLVER_DATA_TEXT",
						"Generate solver execution data" },
				{
						"ptidej.viewer.core.ConstraintViewerPanel::HELP_PTIDEJSOLVER3",

						"http://www.ptidej.net/publications/documents/IJCAI01MSPC.doc.pdf" },
				{
						"ptidej.viewer.core.ConstraintViewerPanel::HELP_PTIDEJSOLVER4",

						"http://www.yann-gael.gueheneuc.net/Work/Tutoring/Documents/040924+JPtidejSolver.doc.pdf" },
				{
						"ptidej.viewer.core.ConstraintViewerPanel::HELP_PTIDEJSOLVER4_METRICAL",

						"http://www.ptidej.net/publications/documents/WCRE04.doc.pdf" },
				{ "ptidej.viewer.core.ConstraintViewerPanel::LIST_ALL_MA_TEXT",
						"List similar micro-architectures" },
				{ "ptidej.viewer.core.ConstraintViewerPanel::LOAD_MA_TEXT",
						"Load similar micro-architectures" },
				{
						"ptidej.viewer.core.ConstraintViewerPanel::MODIFY_PROGRAM_MODEL_TEXT",
						"Modify program model" },
				{
						"ptidej.viewer.core.ConstraintViewerPanel::PTIDEJSOLVER_ALL_TEXT",
						"Ptidej Solvers" },
				{
						"ptidej.viewer.core.ConstraintViewerPanel::PTIDEJSOLVER3_TEXT",
						"Ptidej Solver 3" },
				{
						"ptidej.viewer.core.ConstraintViewerPanel::PTIDEJSOLVER4_METRICAL_TEXT",
						"Metrical Ptidej Solver 4" },
				{
						"ptidej.viewer.core.ConstraintViewerPanel::PTIDEJSOLVER4_TEXT",
						"Ptidej Solver 4" },
				{
						"ptidej.viewer.core.ConstraintViewerPanel::REMOVE_ALL_MA_TEXT",
						"Remove all similar micro-architectures" },
				{
						"ptidej.viewer.core.MetricResultsActionListener::FOLD_UNFOLD",
						"Fold/Unfold" },
				{
						"ptidej.viewer.core.MetricResultsActionListener::FOLD_UNFOLD_ALL",
						"Fold/Unfold All" },
				{ "ptidej.viewer.core.MetricResultsActionListener::GO_TO",
						"Go to" },
				{ "ptidej.viewer.core.MetricResultsActionListener::SHOW_HIDE",
						"Show/Hide" },
				{ "ptidej.viewer.core.MetricResultsFrame::Lbl_COMPONENT_LABEL",
						"Metric {0} = {1}" },
				{
						"ptidej.viewer.core.MetricResultsFrame::Lbl_NO_SOLUTION_LABEL",
						"No metric computed" },
				{ "ptidej.viewer.core.MetricResultsFrame::TITLE",
						"Metrics results" },
				{
						"ptidej.viewer.core.MetricViewerPanel::Button_COMPUTE_METRICS",
						"Compute metrics" },
				{ "ptidej.viewer.core.MetricViewerPanel::Button_SHOW_RESULTS",
						"Show results" },
				{
						"ptidej.viewer.layout.PercentLayout::IAException_INVALID_CONSTRAINT",
						"Invalid constraint" },
				{
						"ptidej.viewer.core.ProjectViewerPanel::CREATE_NEW_PROJECT_TEXT",
						"Create New Project" },
				{ "ptidej.viewer.core.ProjectViewerPanel::TYPE_AOL_NAME",
						"AOL File" },
				{ "ptidej.viewer.core.ProjectViewerPanel::ADD_AOL_FILE",
						"an AOL file" },
				{ "ptidej.viewer.core.ProjectViewerPanel::ADD_AOL_FILE_TEXT",
						"Add AOL file (.aol)" },
				{ "ptidej.viewer.core.ProjectViewerPanel::ADD_ASPECTJ_FILE",
						"an AspectJ list" },
				{
						"ptidej.viewer.core.ProjectViewerPanel::ADD_ASPECTJ_FILE_TEXT",
						"Add AspectJ list (.lst)" },
				{ "ptidej.viewer.core.ProjectViewerPanel::ADD_MSE_FILE",
						"an MSE exchange format file" },
				{ "ptidej.viewer.core.ProjectViewerPanel::ADD_MSE_FILE_TEXT",
						"Add MSE file (.mse)" },
				{
						"ptidej.viewer.core.ProjectViewerPanel::ADD_ECLIPSE_JDT_PROJECT",
						"an Eclipse JDT project" },
				{
						"ptidej.viewer.core.ProjectViewerPanel::ADD_ECLIPSE_JDT_PROJECT_TEXT",
						"Add Eclipse JDT project" },
				{ "ptidej.viewer.core.ProjectViewerPanel::ADD_CPP_FILE",
						"a C++ file" },
				{ "ptidej.viewer.core.ProjectViewerPanel::ADD_CPP_FILE_TEXT",
						"Add C++ file (.cpp)" },
				{ "ptidej.viewer.core.ProjectViewerPanel::ADD_JAR_FILE",
						"a jar file" },
				{ "ptidej.viewer.core.ProjectViewerPanel::ADD_JAR_FILE_TEXT",
						"Add JAR file (.jar)" },
				{ "ptidej.viewer.core.ProjectViewerPanel::ADD_JAVA_FILE",
						"a class file" },
				{ "ptidej.viewer.core.ProjectViewerPanel::ADD_JAVA_FILE_TEXT",
						"Add Java file (.class)" },
				{
						"ptidej.viewer.core.ProjectViewerPanel::ADD_JAVA_SOURCE_FILE",
						"a Java file" },
				{
						"ptidej.viewer.core.ProjectViewerPanel::ADD_JAVA_SOURCE_FILE_TEXT",
						"Add Java file (.java)" },
				{
						"ptidej.viewer.core.ProjectViewerPanel::CREATE_NEW_GRAPHICAL_PROJECT_TEXT",
						"Create Graphical Ptidej project" },
				{
						"ptidej.viewer.core.ProjectViewerPanel::CREATE_NEW_HIERARCHICAL_PROJECT_TEXT",
						"Create Hierachical Ptidej project" },
				{
						"ptidej.viewer.core.ProjectViewerPanel::Err_LOAD_ONLY_JAVA_PTIDEJ_PROJECT",
						"Loading Java-only Ptidej project." },
				{
						"ptidej.viewer.core.ProjectViewerPanel::Err_UNKNOWN_FILE_TYPE",
						"Unknown file type!" },
				{
						"ptidej.viewer.core.ProjectViewerPanel::FILE_DIALOG_ADD_TITLE",
						"Choose {0} to add" },
				{
						"ptidej.viewer.core.ProjectViewerPanel::FILE_DIALOG_CHOOSE_PROJECT_FILE",
						"Choose a project file" },
				{
						"ptidej.viewer.core.ProjectViewerPanel::FILE_DIALOG_CHOOSE_PROJECT_TITLE",
						"Choose a project to load" },
				{
						"ptidej.viewer.core.ProjectViewerPanel::FILE_DIALOG_EXTRA_DATA_TITLE",
						"Choose extra data to load" },
				{
						"ptidej.viewer.core.ProjectViewerPanel::FILE_DIALOG_PROJECT_NAME_TITLE",
						"Please, enter a project name:" },
				{ "ptidej.viewer.core.ProjectViewerPanel::HELP_PADL",

				"http://www.ptidej.net/publications/documents/LMO02.doc.pdf" },
				{
						"ptidej.viewer.core.ProjectViewerPanel::LOAD_EXTRINSIC_DATA_TEXT",
						"Load extrinsic data" },
				{
						"ptidej.viewer.core.ProjectViewerPanel::LOAD_PTIDEJ_PROJECT_TEXT",
						"Load Ptidej project" },
				{ "ptidej.viewer.core.ProjectViewerPanel::LOADING_TIME",
						"Loading time: {0} ms.\n\n" },
				{ "ptidej.viewer.core.ProjectViewerPanel::PROJECT_NAME", "Name" },
				{ "ptidej.viewer.core.ProjectViewerPanel::SAVE_PROJECT_TEXT",
						"Save Ptidej project" },
				{
						"ptidej.viewer.core.ProjectViewerPanel::STATISTICS",

						"\nStatistics:\n{0}\n              Total memory\tFree memory\nBefore build  {1}\t\t{2}\t\t{3}\nAfter build   {4}\t\t{5}\t\t{6}\nAfter display {7}\t\t{8}\t\t{9}\n" },
				{ "ptidej.viewer.core.ProjectViewerPanel::TYPE_AOL_CODE_NAME",
						"AOL Code" },
				{ "ptidej.viewer.core.ProjectViewerPanel::TYPE_AOL_IDIOM_NAME",
						"AOL Idiom" },
				{ "ptidej.viewer.core.ProjectViewerPanel::TYPE_ASPECTJ_NAME",
						"AspectJ" },
				{ "ptidej.viewer.core.ProjectViewerPanel::TYPE_MSE_NAME", "MSE" },
				{
						"ptidej.viewer.core.ProjectViewerPanel::TYPE_ECLIPSE_JDT_PROJECT_NAME",
						"Eclipse JDT Project" },
				{ "ptidej.viewer.core.ProjectViewerPanel::TYPE_CPP_NAME", "C++" },
				{ "ptidej.viewer.core.ProjectViewerPanel::TYPE_JAVA_NAME",
						"JavaCode" },
				{ "ptidej.viewer.core.ProjectViewerPanel::TYPE_PTIDEJ_NAME",
						"Ptidej" },
				{ "ptidej.viewer.awt.occurrences.Constants::FOLD_UNFOLD",
						"Fold/Unfold" },
				{ "ptidej.viewer.awt.occurrences.Constants::FOLD_UNFOLD_ALL",
						"Fold/Unfold All" },
				{
						"ptidej.viewer.awt.occurrences.Constants::FOLD_UNFOLD_GHOSTS",
						"Fold/Unfold containing Ghost" },
				{
						"ptidej.viewer.awt.occurrences.Constants::FOLD_UNFOLD_OBJECT",
						"Fold/Unfold containing Object" },
				{
						"ptidej.viewer.awt.occurrences.Constants::NO_OCCURRENCES_LABEL",
						"No (displayed) occurrences" },
				{ "ptidej.viewer.awt.occurrences.Constants::SHOW_HIDE",
						"Show/Hide" },
				{
						"ptidej.viewer.awt.occurrences.ListPanel::Lbl_SOLUTION_LABEL",

						"Micro-architecture {0} similar at {1}% with {2}" },
				{ "ptidej.viewer.awt.occurrences.ListPanel::TITLE",
						"Occurrences" },
				{ "ptidej.viewer.awt.ActionListener::GO_TO", "Go to" },
				{ "ptidej.viewer.awt.entities.Constants::NO_GRAPH_MODEL_LABEL",
						"No (displayed) entities" },
				{
						"ptidej.viewer.awt.entities.Constants::NO_SOURCE_MODEL_LABEL",
						"No source model available." },
				{
						"ptidej.viewer.splash.SplashProgram::Err_SPLASH_IMG_NOT_FOUND",
						"Splash image not found" },
				{ "ptidej.viewer.splash.SplashProgram::IMG_SPLASH_FILE_NAME",
						"Setup.gif" },
				{ "ptidej.viewer.core.ViewerPanel::ABOUT_TEXT", "About" },
				{ "ptidej.viewer.core.ViewerPanel::AGGREGATION_DISPLAY",
						"Aggregation display" },
				{ "ptidej.viewer.core.ViewerPanel::AGGREGATION_NAMES",
						"Aggregation names" },
				{ "ptidej.viewer.core.ViewerPanel::ASSOCIATION_DISPLAY",
						"Association display" },
				{ "ptidej.viewer.core.ViewerPanel::ASSOCIATION_NAMES",
						"Association names" },
				{ "ptidej.viewer.core.ViewerPanel::Button_IMAGE_BUTTON",
						"About" },
				{ "ptidej.viewer.core.ViewerPanel::COMPOSITION_DISPLAY",
						"Composition display" },
				{ "ptidej.viewer.core.ViewerPanel::COMPOSITION_NAMES",
						"Composition names" },
				{
						"ptidej.viewer.core.ViewerPanel::CONTAINER_AGGREGATION_DISPLAY",
						"Container Aggregation display" },
				{
						"ptidej.viewer.core.ViewerPanel::CONTAINER_AGGREGATION_NAMES",
						"Container Aggregation names" },
				{
						"ptidej.viewer.core.ViewerPanel::CONTAINER_COMPOSITION_DISPLAY",
						"Container Composition display" },
				{
						"ptidej.viewer.core.ViewerPanel::CONTAINER_COMPOSITION_NAMES",
						"Container Composition names" },
				{ "ptidej.viewer.core.ViewerPanel::CREATION_DISPLAY",
						"Creation display" },
				{ "ptidej.viewer.core.ViewerPanel::CREATION_NAMES",
						"Creation names" },
				{ "ptidej.viewer.core.ViewerPanel::EXIT_TEXT", "Exit" },
				{ "ptidej.viewer.core.ViewerPanel::FIELD_NAMES", "Field names" },
				{ "ptidej.viewer.core.ViewerPanel::FULLY_QUALIFIED_NAMES",
						"Fully qualified names" },
				{ "ptidej.viewer.core.ViewerPanel::GHOST_ENTITIES_DISPLAY",
						"Ghost entities display" },
				{ "ptidej.viewer.core.ViewerPanel::HELP_TEXT", "Help" },
				{ "ptidej.viewer.core.ViewerPanel::HIERARCHY_DISPLAY",
						"Hierarchy display" },
				{ "ptidej.viewer.core.ViewerPanel::HIERARCHY_NAMES",
						"Hierarchy names" },
				{ "ptidej.viewer.core.ViewerPanel::METHOD_NAMES",
						"Method names" },
				{ "ptidej.viewer.core.ViewerPanel::NO_PROJECT_TEXT",
						"No Ptidej project" },
				{ "ptidej.viewer.core.ViewerPanel::PRINT_TEXT", "Print" },
				{ "ptidej.viewer.core.ViewerPanel::USE_DISPLAY", "Use display" },
				{ "ptidej.viewer.core.ViewerPanel::USE_NAMES", "Use names" },
				{
						"ptidej.viewer.core.VisitorViewerPanel::FILE_DIALOG_SAVE_MODEL_TITLE",
						"Save model to..." },

				// *********** Ptidej UI Viewer ***********
				{
						"ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R01_C01",

						"The Ptidej tool suite is a set of tools to evaluate and to enhance the quality of object-" },
				{
						"ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R01_C02",

						"oriented programs, promoting the use of patterns, either at the language-, design-, or" },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R01_C03",
						"architectural-levels." },
				{
						"ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R01_C04",

						"The Ptidej tool suite (Pattern Trace Identification, Detection, and Enhancement in Java)" },
				{
						"ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R01_C05",

						"by Yann-Gaël Guéhéneuc uses the PADL meta-model (Pattern and Abstract-level" },
				{
						"ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R01_C06",

						"Description Language), extension of the PDL meta-model (Pattern Description Language)" },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R01_C07",
						"by Hervé Albin-Amiot." },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R01_C08",
						"Get more information at www.ptidej.net." },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R01_C09",

				"Send comments and questions to yann-gael@gueheneuc.net." },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R02_C01",
						"Main developpers:" },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R02_C02",
						"        - Yann-Gaël Guéhéneuc" },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R02_C03",
						"                Université de Montréal" },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R02_C04",

				"                École des Mines de Nantes" },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R02_C05",

				"                Object Technology International, Inc." },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R02_C06",
						"        - Hervé Albin-Amiot." },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R02_C07",

				"                École des Mines de Nantes" },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R02_C08",
						"                Softmaint S.A." },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R02_C10",
						"With contributions by:" },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R02_C11",
						"        - Jean-Yves Guyomarc'h" },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R02_C12",

				"                On the Ptidej Solver in Java and metrics" },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R02_C13",
						"                Graduate, fall 2004" },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R02_C14",

				"                Université de Montréal." },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R02_C15",
						"        - Lulzim Laloshi and Driton Salihu" },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R02_C16",
						"                On the Eclipse plug-in" },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R02_C17",

				"                Undergraduates, Summer 2004" },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R02_C18",

				"                Université de Montréal." },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R02_C19",

				"        - Ward Flores and Sébastien Robidoux" },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R02_C20",

				"                On the C++ creator and PADL" },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R02_C21",

				"                Undergraduates, Summer 2004" },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R02_C22",

				"                Université de Montréal." },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R02_C23",

				"        - Salime Bensemmane, Iyadh Sidhom, and Fayçal Skhiri" },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R02_C24",

				"                On the Ptidej Solver in Java" },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R02_C25",

				"                Undergraduates, Summer 2004" },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R02_C26",

				"                Université de Montréal." },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R02_C27",
						"        - Farouk Zaidi" },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R02_C28",

				"                On the Java classfile creator and metrics" },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R02_C29",
						"                Trainee, Winter 2004" },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R02_C30",

				"                Université de technologie Belfort-Montbéliard" },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R02_C31",

				"                Centre de Recherche en Informatique de Montréal" },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R02_C32",

				"                Université de Montréal." },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R03_C01",

				"The Ptidej tool suite, copyright (c) 2000-2004," },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R03_C02",
						"Yann-Gaël Guéhéneuc." },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R03_C03",
						"All right reserved." },
				{
						"ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R04_C01",

						"Use and copying of this software and preparation of derivative works based upon this" },
				{
						"ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R04_C02",

						"software are permitted. Any copy of this software or of any derivative work must include" },
				{
						"ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R04_C03",

						"the above copyright notice of the authors, this paragraph and the one after it." },
				{
						"ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R05_C01",

						"This software is made available AS IS, and THE AUTHORS DISCLAIM ALL WARRANTIES," },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R05_C02",

				"EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE IMPLIED WARRANTIES" },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R05_C03",

				"OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE, AND NOT" },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R05_C04",

				"WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN ANY LIABILITY FOR" },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R05_C05",

				"DAMAGES RESULTING FROM THE SOFTWARE OR ITS USE IS EXPRESSLY" },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R05_C06",

				"DISCLAIMED, WHETHER ARISING IN CONTRACT, TORT (INCLUDING NEGLIGENCE)" },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R05_C07",

				"OR STRICT, LIABILITY EVEN IF THE AUTHORS ARE ADVISED OF THE POSSIBILITY" },
				{ "ptidej.viewer.ProjectData::COPYRIGHT_MESSAGE_R05_C08",
						"OF SUCH DAMAGES." },
				{ "ptidej.viewer.ProjectData::PROJECTS_R01", "CPL v1.0.0" },
				{ "ptidej.viewer.ProjectData::PROJECTS_R02", "PADL v1.0.0" },
				{ "ptidej.viewer.ProjectData::PROJECTS_R03",
						"PADL AOL Creator v1.0.0" },
				{ "ptidej.viewer.ProjectData::PROJECTS_R04",
						"PADL C++ Creator v1.0.0" },
				{ "ptidej.viewer.ProjectData::PROJECTS_R05",
						"PADL ClassFile Creator v1.0.0" },
				{ "ptidej.viewer.ProjectData::PROJECTS_R06", "POM v1.0.0" },
				{ "ptidej.viewer.ProjectData::PROJECTS_R07", "Ptidej v1.0.0" },
				{ "ptidej.viewer.ProjectData::PROJECTS_R08",
						"Ptidej Solver 3 v1.0.0" },
				{ "ptidej.viewer.ProjectData::PROJECTS_R09",
						"Ptidej Solver 4 v1.0.0" },
				{ "ptidej.viewer.ProjectData::PROJECTS_R10", "Ptidej UI v1.0.0" },
				{ "ptidej.viewer.ProjectData::PROJECTS_R11",
						"Ptidej UI Viewer v1.0.0" },

				// *********** Ptidej UI ***********
				{ "ptidej.ui.kernel.Constituent::NAME", "An instance of {0}" },
				{
						"ptidej.ui.layout.InheritanceClusterLayout::Err_CALL_getDepth",

						"The depth of the DepthAwareEntity must be computed (computeDepth(Map)) prior to a call to getDepth()." },
				{
						"ptidej.ui.layout.InheritanceDepthLayout::Err_CALL_getDepth",

						"The depth of the DepthAwareDEntity must be computed (computeDepth(Hashtable)) prior to a call to getDepth()." },

				// *********** Ptidej ***********
				{ "ptidej.solver.SolutionGenerator::UNKNOWN_PROBLEM_TYPE",
						"SolutionGenerator: Unknown problem type." },
				{ "ptidej.solver.SolutionGenerator::UNKNOWN_SOLVER_ALGO",

				"SolutionGenerator: Unknown solver algorithm." },
				{ "ptidej.solver.SolutionGenerator::UNKNOWN_SOLVER_VER",
						"SolutionGenerator: Unknown solver version." },

				{
						"ptidej.viewer.ui.window.ModelStatisticsWindow::NO_MODEL_LABEL",
						"No source model available." },

				// *********** Ptidej UI Viewer Standalone Swing ***********
				{ "EPI_SIMILAR_MICRO_ARCHITECTURE_HELP_LINK",

				"http://www.ptidej.net/publications/documents/CSMR06a.doc.pdf" },
				{ "PTIDEJ_SOLVER_3_SIMILAR_MICRO_ARCHITECTURE_HELP_LINK",

				"http://www.ptidej.net/publications/documents/IJCAI01MSPC.doc.pdf" },
				{
						"PTIDEJ_SOLVER_4_SIMILAR_MICRO_ARCHITECTURE_HELP_LINK",

						"http://www.yann-gael.gueheneuc.net/Work/Tutoring/Documents/040924+JPtidejSolver.doc.pdf" },
				{
						"METRICAL_PTIDEJ_SOLVER_4_SIMILAR_MICRO_ARCHITECTURE_HELP_LINK",

						"http://www.ptidej.net/publications/documents/WCRE04.doc.pdf" },
				{ "CREATE_PROJECT_HELP_LINK",

				"http://www.ptidej.net/publications/documents/LMO02.doc.pdf" },
				{ "SYSTEMATIC_UML_HELP_LINK",

				"http://www.ptidej.net/publications/documents/APSEC04.doc.pdf" },
				{ "DOTTY_HELP_LINK", "http://www.graphviz.org/" },
				{ "ADJACENCY_MATRIX_INFOVIS_HELP_LINK",
						"http://ivtk.sourceforge.net/" },
				{ "ADJACENCY_MATRIX_OADYMPPAC_HELP_LINK",
						"http://contraintes.inria.fr/OADymPPaC/" },
				{ "SUGIBIB_HELP_LINK",

				"http://www2.informatik.uni-wuerzburg.de/SugiBib/english.html" },
				{ "ptidej.viewer.ui.MenuBar::MENU_FILE", "File" },
				{ "ptidej.viewer.ui.MenuBar::MENU_FILE_MNEMONIC", "F" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_NEW_GRAPHICAL_PROJECT",
						"New Graphical Project" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_NEW_GRAPHICAL_PROJECT_SMALL_ICON",
						"New16.gif" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_NEW_GRAPHICAL_PROJECT_MNEMONIC",
						"N" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_NEW_HIERARCHICAL_PROJECT",
						"New Hierarchical Project" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_NEW_HIERARCHICAL_PROJECT_SMALL_ICON",
						"NewHierarchical16.gif" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_NEW_HIERARCHICAL_PROJECT_MNEMONIC",
						"N" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_LOAD_GRAPHICAL_PROJECT",
						"Load Graphical Project" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_LOAD_GRAPHICAL_PROJECT_SMALL_ICON",
						"Open16.gif" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_LOAD_GRAPHICAL_PROJECT_MNEMONIC",
						"G" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_LOAD_HIERARCHICAL_PROJECT",
						"Load Hierarchical Project" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_LOAD_HIERARCHICAL_PROJECT_SMALL_ICON",
						"OpenHierarchical16.gif" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_LOAD_HIERARCHICAL_PROJECT_MNEMONIC",
						"H" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_LOAD_DUAL_HIERARCHICAL_PROJECT",
						"Load Dual Hierarchical Project" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_LOAD_DUAL_HIERARCHICAL_PROJECT_SMALL_ICON",
						"OpenDualHierarchical16.gif" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_LOAD_DUAL_HIERARCHICAL_PROJECT_MNEMONIC",
						"D" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_LOAD_EXTRINSIC",
						"Load Extrinsic data" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_LOAD_EXTRINSIC_MNEMONIC", "E" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_USAGE", "Usage" },
				{ "ptidej.viewer.ui.MenuBar::MENU_ADD", "Add ..." },
				{ "ptidej.viewer.ui.MenuBar::MENU_ADD_MNEMONIC", "A" },
				{ "ptidej.viewer.ui.MenuBar::MENU_ADD_SMALL_ICON", "Add16.gif" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_JAVA_CLASS_FILE",
						"Java Class Files (.class)" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_JAVA_CLASS_FILE_MNEMONIC",
						"J" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_JAVA_ARCHIVE_FILE",
						"Java Archive File (.jar)" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_JAVA_ARCHIVE_FILE_MNEMONIC",
						"A" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_JAVA_SOURCE_FILE",
						"Java Source Files (.java)" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_JAVA_SOURCE_FILE_MNEMONIC",
						"P" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_CPP_FILE", "C++ Files (.cpp)" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_CPP_FILE_MNEMONIC", "C" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_AOL_CODE_FILE",
						"AOL Code File (.aol)" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_AOL_CODE_FILE_MNEMONIC", "O" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_AOL_IDIOM_FILE",
						"AOL Idiom File (.aol)" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_AOL_IDIOM_FILE_MNEMONIC", "O" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_ASPECTJ_FILE",
						"AspectJ File (.lst)" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_ASPECTJ_FILE_MNEMONIC", "A" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_MSE_FILE", "MSE File (.mse)" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_MSE_FILE_MNEMONIC", "M" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_ECLIPSE_JDT_PROJECT",
						"Eclipse JDT Project (.project)" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_ECLIPSE_JDT_PROJECT_MNEMONIC",
						"E" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_EXPORT_SVG", "Export SVG" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_CLOSE_ALL", "Close All" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_CLOSE_ACTIVE", "Close" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_CLOSE_ACTIVE_MNEMONIC", "C" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_CLOSE_ALL_EXPECT_ACTIVE",
						"Close All But Current" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_SAVE_ALL", "Save All" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_SAVE_ALL_SMALL_ICON",
						"SaveAll16.gif" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_SAVE_ALL_MNEMONIC", "v" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_SAVE_ACTIVE", "Save" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_SAVE_ACTIVE_SMALL_ICON",
						"Save16.gif" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_SAVE_ACTIVE_MNEMONIC", "S" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_PRINT", "Print" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_PRINT_SMALL_ICON",
						"Print16.gif" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_PRINT_MNEMONIC", "P" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_EXIT", "Exit" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_EXIT_SMALL_ICON",
						"Stop16.gif" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_EXIT_MNEMONIC", "x" },
				{ "ptidej.viewer.ui.MenuBar::MENU_VIEW", "View" },
				{ "ptidej.viewer.ui.MenuBar::MENU_VIEW_MNEMONIC", "V" },
				{ "ptidej.viewer.ui.MenuBar::MENU_LAYOUT_TOPICS",
						"Graph Layout" },
				{ "ptidej.viewer.ui.MenuBar::MENU_LAYOUT_TOPICS_MNEMONIC", "L" },
				{ "ptidej.viewer.ui.MenuBar::MENU_TOOLBARS", "Toolbars" },
				{ "ptidej.viewer.ui.MenuBar::MENU_TOOLBARS_MNEMONIC", "T" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_SHOW_ALL", "Show All" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_SHOW_ALL_MNEMONIC", "S" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_HIDE_ALL", "Hide All" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_HIDE_ALL_MNEMONIC", "H" },
				{ "ptidej.viewer.ui.MenuBar::MENU_SETTINGS", "Settings" },
				{ "ptidej.viewer.ui.MenuBar::MENU_SETTINGS_MNEMONIC", "S" },
				{ "ptidej.viewer.ui.MenuBar::MENU_LANGUAGE", "Language" },
				{ "ptidej.viewer.ui.MenuBar::MENU_LANGUAGE_MNEMONIC", "L" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_ITEM_ENGLISH", "English" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_ITEM_FRENCH", "Français" },
				{ "ptidej.viewer.ui.MenuBar::MENU_WINDOW", "Window" },
				{ "ptidej.viewer.ui.MenuBar::MENU_WINDOW_MNEMONIC", "W" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_MINIMIZE_ALL", "Minimize All" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_MINIMIZE_ALL_MNEMONIC", "M" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_CASCADE", "Cascade" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_CASCADE_MNEMONIC", "C" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_TILE_VERTICALLY",
						"Tile Vertically" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_TILE_VERTICALLY_MNEMONIC",
						"V" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_TILE_HORIZONTALLY",
						"Tile Horizontally" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_TILE_HORIZONTALLY_MNEMONIC",
						"H" },
				{ "ptidej.viewer.ui.MenuBar::EMPTY_ICON", "Empty16.gif" },
				{ "ptidej.viewer.ui.MenuBar::MENU_HELP", "Help" },
				{ "ptidej.viewer.ui.MenuBar::MENU_HELP_MNEMONIC", "H" },
				{ "ptidej.viewer.ui.MenuBar::MENU_HELP_TOPICS", "Help Topics" },
				{ "ptidej.viewer.ui.MenuBar::MENU_HELP_TOPICS_MNEMONIC", "H" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_ABOUT", "About" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_ABOUT_MNEMONIC", "A" },
				{ "ptidej.viewer.ui.MenuBar::MENU_TOOLS", "Tools" },
				{ "ptidej.viewer.ui.MenuBar::MENU_TOOLS_MNEMONIC", "T" },
				{ "ptidej.viewer.ui.MenuBar::MENU_UML", "UML" },
				{ "ptidej.viewer.ui.MenuBar::MENU_UML_MNEMONIC", "U" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_SYSTEMATIC_UML",
						"Systematic UML" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_SYSTEMATIC_UML_MNEMONIC", "S" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_SYSTEMATIC_UML_HELP",
						"Systematic UML" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_SYSTEMATIC_UML_HELP_MNEMONIC",
						"H" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_AOL_MODELS_COMPARISION",
						"AOL models comparision" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_AOL_MODELS_COMPARISION_MNEMONIC",
						"A" },
				{
						"ptidej.viewer.ui.MenuBar::MENU_MODEL_DIFFERENCE_HIGHLIGHTER",
						"Model difference highlighter" },
				{
						"ptidej.viewer.ui.MenuBar::MENU_MODEL_DIFFERENCE_HIGHLIGHTER_MNEMONIC",
						"h" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_MODEL_DIFFERENCE_HIGHLIGHTER_FROM_METHODS",
						"From Methods" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_MODEL_DIFFERENCE_HIGHLIGHTER_FROM_METHODS_MNEMONIC",
						"M" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_MODEL_DIFFERENCE_HIGHLIGHTER_FROM_CLASSES",
						"From Classes" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_MODEL_DIFFERENCE_HIGHLIGHTER_FROM_CLASSES_MNEMONIC",
						"C" },
				{ "ptidej.viewer.ui.MenuBar::MENU_SUGIBIB", "SugiBib" },
				{ "ptidej.viewer.ui.MenuBar::MENU_SUGIBIB_MNEMONIC", "S" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_SUGIBIB", "SugiBib" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_SUGIBIB_MNEMONIC", "S" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_SUGIBIB_HELP", "SugiBib" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_SUGIBIB_HELP_MNEMONIC", "H" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_POM_BASED_METRICS",
						"POM-Based metrics" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_POM_BASED_METRICS_MNEMONIC",
						"P" },
				{ "ptidej.viewer.ui.MenuBar::MENU_ADJACENCY_MATRIX",
						"Adjacency matrix" },
				{ "ptidej.viewer.ui.MenuBar::MENU_ADJACENCY_MATRIX_MNEMONIC",
						"m" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_ADJACENCY_MATRIX_OADYMPPAC",
						"OADymPPaC" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_ADJACENCY_MATRIX_OADYMPPAC_MNEMONIC",
						"O" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_ADJACENCY_MATRIX_OADYMPPAC_HELP",
						"OADymPPaC" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_ADJACENCY_MATRIX_OADYMPPAC_HELP_MNEMONIC",
						"H" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_ADJACENCY_MATRIX_INFOVIS",
						"InfoVis" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_ADJACENCY_MATRIX_INFOVIS_MNEMONIC",
						"I" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_ADJACENCY_MATRIX_INFOVIS_HELP",
						"InfoVis" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_ADJACENCY_MATRIX_INFOVIS_HELP_MNEMONIC",
						"e" },
				{ "ptidej.viewer.ui.MenuBar::MENU_DOTTY", "Dotty" },
				{ "ptidej.viewer.ui.MenuBar::MENU_DOTTY_MNEMONIC", "D" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_DOTTY", "Dotty" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_DOTTY_MNEMONIC", "D" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_DOTTY_HELP", "Dotty" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_DOTTY_HELP_MNEMONIC", "H" },
				{ "ptidej.viewer.ui.MenuBar::MENU_DESIGN_MOTIFS",
						"Design Motifs" },
				{ "ptidej.viewer.ui.MenuBar::MENU_DESIGN_MOTIFS_MNEMONIC", "m" },
				{ "ptidej.viewer.ui.MenuBar::MENU_DESIGN_SMELLS",
						"Design Smells" },
				{ "ptidej.viewer.ui.MenuBar::MENU_DESIGN_SMELLS_MNEMONIC", "a" },

				{ "ptidej.viewer.ui.MenuBar::RADIO_Creator", "Creators" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_Creator_MNEMONIC", "C" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_InheritanceDepthClustering",
						"Inheritance-depth Clustering" },
				{
						"ptidej.viewer.ui.MenuBar::RADIO_InheritanceDepthClustering_MNEMONIC",
						"D" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_InheritanceClustering",
						"Inheritance-depth Clustering" },
				{
						"ptidej.viewer.ui.MenuBar::RADIO_InheritanceClustering_MNEMONIC",
						"I" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_Simple", "Simple" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_Simple_MNEMONIC", "B" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_SugiyamaAlgorithm",
						"SugiyamaAlgorithm" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_SugiyamaAlgorithm_MNEMONIC",
						"S" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_AbstractFactory",
						"Abstract Factory" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_AbstractFactory_MNEMONIC",
						"A" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_Adapter", "Adapter" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_Adapter_MNEMONIC", "A" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_Builder", "Builder" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_Builder_MNEMONIC", "B" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_ChainOfResponsibility",
						"Chain Of Responsibility" },
				{
						"ptidej.viewer.ui.MenuBar::RADIO_ChainOfResponsibility_MNEMONIC",
						"R" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_Command", "Command" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_Command_MNEMONIC", "C" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_Composite", "Composite" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_Composite_MNEMONIC", "C" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_Decorator", "Decorator" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_Decorator_MNEMONIC", "D" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_Facade", "Facade" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_Facade_MNEMONIC", "a" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_FactoryMethod",
						"Factory Method" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_FactoryMethod_MNEMONIC", "F" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_Flyweight", "Fly Weight" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_Flyweight_MNEMONIC", "F" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_GoodInheritance",
						"Good Inheritance" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_GoodInheritance_MNEMONIC",
						"G" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_Mediator", "Mediator" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_Mediator_MNEMONIC", "M" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_Memento", "Memento" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_Memento_MNEMONIC", "e" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_Observer", "Observer" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_Observer_MNEMONIC", "O" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_Prototype", "Proxy" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_Prototype_MNEMONIC", "P" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_Proxy", "Proxy" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_Proxy_MNEMONIC", "P" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_Singleton", "Singleton" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_Singleton_MNEMONIC", "S" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_State", "State" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_State_MNEMONIC", "S" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_TemplateMethod",
						"Template Method" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_TemplateMethod_MNEMONIC",
						"T" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_Visitor", "Visitor" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_Visitor_MNEMONIC", "V" },
				{ "ptidej.viewer.ui.MenuBar::MENU_SOLVER", "Solver" },
				{ "ptidej.viewer.ui.MenuBar::MENU_SOLVER_MNEMONIC", "S" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_AUTOMATIC_SOLVER",
						"Automatic Solver" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_AUTOMATIC_SOLVER_MNEMONIC",
						"A" },
				{
						"ptidej.viewer.ui.MenuBar::RADIO_COMBINATORIAL_AUTOMATIC_SOLVER",
						"Combinatorial Automatic Solver" },
				{
						"ptidej.viewer.ui.MenuBar::RADIO_COMBINATORIAL_AUTOMATIC_SOLVER_MNEMONIC",
						"C" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_SIMPLE_AUTOMATIC_SOLVER",
						"Simple Automatic Solver" },
				{
						"ptidej.viewer.ui.MenuBar::RADIO_SIMPLE_AUTOMATIC_SOLVER_MNEMONIC",
						"S" },
				{ "ptidej.viewer.ui.MenuBar::MENU_PTIDEJ_SOLVER_3",
						"Ptidej Solver 3" },
				{ "ptidej.viewer.ui.MenuBar::MENU_PTIDEJ_SOLVER_3_MNEMONIC",
						"3" },
				{ "ptidej.viewer.ui.MenuBar::MENU_PROBLEM", "Problem" },
				{ "ptidej.viewer.ui.MenuBar::MENU_PROBLEM_MNEMONIC", "P" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_AC4_PROBLEM", "AC-4 Problem" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_AC4_PROBLEM_MNEMONIC", "A" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_CUSTOM_PROBLEM",
						"Custom Problem" },
				{ "ptidej.viewer.ui.MenuBar::RADIO_CUSTOM_PROBLEM_MNEMONIC",
						"C" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_GENERATE_PROGRAM_MODEL",
						"Generate program model" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_GENERATE_PROGRAM_MODEL_MNEMONIC",
						"m" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_GENERATE_SOLVER_EXECUTION_DATA",
						"Generate solver execution data" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_GENERATE_SOLVER_EXECUTION_DATA_MNEMONIC",
						"d" },
				{
						"ptidej.viewer.ui.MenuBar::MENU_PTIDEJ_SOLVER_3_SIMILAR_MICRO_ARCHITECTURE",
						"Similar Micro-Architectures" },
				{
						"ptidej.viewer.ui.MenuBar::MENU_PTIDEJ_SOLVER_3_SIMILAR_MICRO_ARCHITECTURE_MNEMONIC",
						"S" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_PTIDEJ_SOLVER_3_FIND_SIMILAR_MICRO_ARCHITECTURE",
						"Find" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_PTIDEJ_SOLVER_3_FIND_SIMILAR_MICRO_ARCHITECTURE_MNEMONIC",
						"F" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_PTIDEJ_SOLVER_3_SIMILAR_MICRO_ARCHITECTURE_HELP",

						"Ptidej Solver 3 Similar Micro-Architectures" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_PTIDEJ_SOLVER_3_SIMILAR_MICRO_ARCHITECTURE_HELP_MNEMONIC",
						"H" },
				{ "ptidej.viewer.ui.MenuBar::MENU_PTIDEJ_SOLVER_4",
						"Ptidej Solver 4" },
				{ "ptidej.viewer.ui.MenuBar::MENU_PTIDEJ_SOLVER_4_MNEMONIC",
						"4" },
				{
						"ptidej.viewer.ui.MenuBar::MENU_PTIDEJ_SOLVER_4_SIMILAR_MICRO_ARCHITECTURE",
						"Similar Micro-Architectures" },
				{
						"ptidej.viewer.ui.MenuBar::MENU_PTIDEJ_SOLVER_4_SIMILAR_MICRO_ARCHITECTURE_MNEMONIC",
						"S" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_PTIDEJ_SOLVER_4_FIND_SIMILAR_MICRO_ARCHITECTURE",
						"Find" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_PTIDEJ_SOLVER_4_FIND_SIMILAR_MICRO_ARCHITECTURE_MNEMONIC",
						"F" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_PTIDEJ_SOLVER_4_SIMILAR_MICRO_ARCHITECTURE_HELP",

						"Ptidej Solver 4 Similar Micro-Architectures" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_PTIDEJ_SOLVER_4_SIMILAR_MICRO_ARCHITECTURE_HELP_MNEMONIC",
						"H" },
				{ "ptidej.viewer.ui.MenuBar::MENU_EPI", "EPI" },
				{ "ptidej.viewer.ui.MenuBar::MENU_EPI_MNEMONIC", "E" },
				{
						"ptidej.viewer.ui.MenuBar::MENU_EPI_SIMILAR_MICRO_ARCHITECTURE",
						"Similar Micro-Architectures" },
				{
						"ptidej.viewer.ui.MenuBar::MENU_EPI_SIMILAR_MICRO_ARCHITECTURE_MNEMONIC",
						"S" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_EPI_FIND_SIMILAR_MICRO_ARCHITECTURE",
						"Find" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_EPI_FIND_SIMILAR_MICRO_ARCHITECTURE_MNEMONIC",
						"F" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_EPI_SIMILAR_MICRO_ARCHITECTURE_HELP",
						"EPI Similar Micro-Architectures" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_EPI_SIMILAR_MICRO_ARCHITECTURE_HELP_MNEMONIC",
						"H" },
				{ "ptidej.viewer.ui.MenuBar::MENU_METRICAL_PTIDEJ_SOLVER_4",
						"Metrical Ptidej Solver 4" },
				{
						"ptidej.viewer.ui.MenuBar::MENU_METRICAL_PTIDEJ_SOLVER_4_MNEMONIC",
						"M" },
				{
						"ptidej.viewer.ui.MenuBar::MENU_METRICAL_PTIDEJ_SOLVER_4_SIMILAR_MICRO_ARCHITECTURE",
						"Similar Micro-Architectures" },
				{
						"ptidej.viewer.ui.MenuBar::MENU_METRICAL_PTIDEJ_SOLVER_4_SIMILAR_MICRO_ARCHITECTURE_MNEMONIC",
						"S" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_METRICAL_PTIDEJ_SOLVER_4_FIND_SIMILAR_MICRO_ARCHITECTURE",
						"Find" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_METRICAL_PTIDEJ_SOLVER_4_FIND_SIMILAR_MICRO_ARCHITECTURE_MNEMONIC",
						"F" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_METRICAL_PTIDEJ_SOLVER_4_SIMILAR_MICRO_ARCHITECTURE_HELP",

						"Metrical Ptidej Solver 4 Similar Micro-Architectures" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_METRICAL_PTIDEJ_SOLVER_4_SIMILAR_MICRO_ARCHITECTURE_HELP_MNEMONIC",
						"H" },
				{ "ptidej.viewer.ui.MenuBar::MENU_PTIDEJ_SOLVERS",
						"Ptidej Solvers" },
				{ "ptidej.viewer.ui.MenuBar::MENU_PTIDEJ_SOLVERS_MNEMONIC", "t" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_LOAD_SIMILAR_MICRO_ARCHITECTURES",
						"Load Similar Micro-Architectures" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_LOAD_SIMILAR_MICRO_ARCHITECTURES_MNEMONIC",
						"L" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_LIST_SIMILAR_MICRO_ARCHITECTURES",
						"List Similar Micro-Architectures" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_LIST_SIMILAR_MICRO_ARCHITECTURES_MNEMONIC",
						"i" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_REMOVE_ALL_SIMILAR_MICRO_ARCHITECTURES",
						"Remove All Similar Micro-Architectures" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_REMOVE_ALL_SIMILAR_MICRO_ARCHITECTURES_MNEMONIC",
						"R" },
				{ "ptidej.viewer.ui.MenuBar::ITEM_MODIFY_PROGRAM_MODEL",
						"Modify program model" },
				{
						"ptidej.viewer.ui.MenuBar::ITEM_MODIFY_PROGRAM_MODEL_MNEMONIC",
						"M" },
				{ "ptidej.viewer.ui.ToolBar::TOOLBAR_FILE_TITLE",
						"Main toolbar" },
				{ "ptidej.viewer.ui.ToolBar::CMD_NEW_GRAPHICAL_PROJECT",
						"New Graphical Project" },
				{ "ptidej.viewer.ui.ToolBar::CMD_NEW_GRAPHICAL_PROJECT_ICON",
						"New24.gif" },
				{ "ptidej.viewer.ui.ToolBar::CMD_NEW_HIERARCHICAL_PROJECT",
						"New Hierarchical Project" },
				{
						"ptidej.viewer.ui.ToolBar::CMD_NEW_HIERARCHICAL_PROJECT_ICON",
						"NewHierarchical24.gif" },
				{ "ptidej.viewer.ui.ToolBar::CMD_LOAD_GRAPHICAL_PROJECT",
						"Load Graphical Project" },
				{ "ptidej.viewer.ui.ToolBar::CMD_LOAD_GRAPHICAL_PROJECT_ICON",
						"Open24.gif" },
				{ "ptidej.viewer.ui.ToolBar::CMD_LOAD_HIERARCHICAL_PROJECT",
						"Load Hierarchical Project" },
				{
						"ptidej.viewer.ui.ToolBar::CMD_LOAD_HIERARCHICAL_PROJECT_ICON",
						"OpenHierarchical24.gif" },
				{
						"ptidej.viewer.ui.ToolBar::CMD_LOAD_DUAL_HIERARCHICAL_PROJECT",
						"Load Dual Hierarchical Project" },
				{
						"ptidej.viewer.ui.ToolBar::CMD_LOAD_DUAL_HIERARCHICAL_PROJECT_ICON",
						"OpenDualHierarchical24.gif" },
				{ "ptidej.viewer.ui.ToolBar::CMD_ADD", "Add File ..." },
				{ "ptidej.viewer.ui.ToolBar::CMD_SAVE_ALL", "Save All" },
				{ "ptidej.viewer.ui.ToolBar::CMD_SAVE_ACTIVE", "Save" },
				{ "ptidej.viewer.ui.ToolBar::CMD_PRINT", "Print" },
				{ "ptidej.viewer.ui.ToolBar::CMD_SAVE_ALL_ICON",
						"SaveAll24.gif" },
				{ "ptidej.viewer.ui.ToolBar::CMD_SAVE_ACTIVE_ICON",
						"Save24.gif" },
				{ "ptidej.viewer.ui.ToolBar::CMD_PRINT_ICON", "Print24.gif" },
				{ "ptidej.viewer.ui.ToolBar::CMD_JAVA_CLASS_FILE_MNEMONIC", "J" },
				{ "ptidej.viewer.ui.ToolBar::CMD_JAVA_ARCHIVE_FILE_MNEMONIC",
						"A" },
				{ "ptidej.viewer.ui.ToolBar::CMD_JAVA_SOURCE_FILE_MNEMONIC",
						"P" },
				{ "ptidej.viewer.ui.ToolBar::CMD_CPP_FILE_MNEMONIC", "C" },
				{ "ptidej.viewer.ui.ToolBar::CMD_AOL_FILE_MNEMONIC", "O" },
				{ "ptidej.viewer.ui.ToolBar::CMD_ASPECTJ_FILE_MNEMONIC", "A" },
				{ "ptidej.viewer.ui.ToolBar::CMD_MSE_FILE_MNEMONIC", "M" },
				{ "ptidej.viewer.ui.ToolBar::CMD_ECLIPSE_JDT_PROJECT_MNEMONIC",
						"E" },
				{ "ptidej.viewer.widget.DropDownButton::CMD_ADD_ICON",
						"Add24.gif" },
				{ "ptidej.viewer.widget.DropDownButton::CMD_JAVA_CLASS_FILE",
						"Java Class Files (.class)" },
				{ "ptidej.viewer.widget.DropDownButton::CMD_JAVA_ARCHIVE_FILE",
						"Java Archive File (.jar)" },
				{ "ptidej.viewer.widget.DropDownButton::CMD_JAVA_SOURCE_FILE",
						"Java Source Files (.java)" },
				{ "ptidej.viewer.widget.DropDownButton::CMD_CPP_FILE",
						"C++ Files (.cpp)" },
				{ "ptidej.viewer.widget.DropDownButton::CMD_AOL_CODE_FILE",
						"AOL Code File (.aol)" },
				{ "ptidej.viewer.widget.DropDownButton::CMD_AOL_IDIOM_FILE",
						"AOL Idiom File (.aol)" },
				{ "ptidej.viewer.widget.DropDownButton::CMD_ASPECTJ_FILE",
						"AspectJ File (.lst)" },
				{ "ptidej.viewer.widget.DropDownButton::CMD_MSE_FILE",
						"MSE File (.mse)" },
				{
						"ptidej.viewer.widget.DropDownButton::CMD_ECLIPSE_JDT_PROJECT",
						"Eclipse JDT Project (.project)" },
				{ "ptidej.viewer.widget.DropDownButton::CMD_PTIDEJ_FILE",
						"Ptidej File (.ptidej)" },
				{ "ptidej.viewer.widget.DropDownButton::CMD_DROP_DOWN_ICON",
						"Dropdown16.gif" },
				{ "ptidej.viewer.action.FileAction::TYPE_AOL_CODE_NAME",
						"AOL Code" },
				{ "ptidej.viewer.action.FileAction::TYPE_AOL_IDIOM_NAME",
						"AOL Idiom" },
				{ "ptidej.viewer.action.FileAction::TYPE_ASPECTJ_NAME",
						"AspectJ" },
				{ "ptidej.viewer.action.FileAction::TYPE_MSE_NAME", "MSE" },
				{
						"ptidej.viewer.action.FileAction::TYPE_ECLIPSE_JDT_PROJECT_NAME",
						"Eclipse JDT Project" },
				{ "ptidej.viewer.action.FileAction::TYPE_CPP_NAME", "C++" },
				{ "ptidej.viewer.action.FileAction::TYPE_JAVA_NAME", "Java" },
				{ "ptidej.viewer.action.FileAction::TYPE_PTIDEJ_NAME", "Ptidej" },
				{ "ptidej.viewer.action.FileAction::ADD_ASPECTJ_FILE",
						"Add AspectJ File" },
				{ "ptidej.viewer.action.FileAction::ADD_MSE_FILE",
						"Add MSE File" },
				{ "ptidej.viewer.action.FileAction::ADD_ECLIPSE_JDT_PROJECT",
						"Add Eclipse JDT Project" },
				{ "ptidej.viewer.action.FileAction::ADD_JAVA_FILE",
						"Add Java File" },
				{ "ptidej.viewer.action.FileAction::ADD_JAR_FILE",
						"Add Jar File" },
				{ "ptidej.viewer.action.FileAction::ADD_JAVA_SOURCE_FILE",
						"Add Java File" },
				{ "ptidej.viewer.action.FileAction::ADD_PTIDEJ_FILE",
						"Add from Ptidej File" },
				{ "ptidej.viewer.action.FileAction::ADD_CPP_FILE",
						"Add C++ File" },
				{ "ptidej.viewer.action.FileAction::ADD_AOL_CODE_FILE",
						"Add AOL Code File" },
				{ "ptidej.viewer.action.FileAction::ADD_AOL_IDIOM_FILE",
						"Add AOL Idiom File" },
				{ "ptidej.viewer.ui.panel.OptionPanel::VIEWER_OPT_FRAME_TITLE",
						"Viewer Options" },
				{ "ptidej.viewer.ui.panel.OptionPanel::CHK_CREATION_NAMES",
						"Creation Names" },
				{ "ptidej.viewer.ui.panel.OptionPanel::CHK_CREATION_DISPLAY",
						"Creation Display" },
				{ "ptidej.viewer.ui.panel.OptionPanel::CHK_USE_NAMES",
						"Use Names" },
				{ "ptidej.viewer.ui.panel.OptionPanel::CHK_USE_DISPLAY",
						"Use Display" },
				{ "ptidej.viewer.ui.panel.OptionPanel::CHK_ASSOCIATION_NAMES",
						"Association Names" },
				{
						"ptidej.viewer.ui.panel.OptionPanel::CHK_ASSOCIATION_DISPLAY",
						"Association Display" },
				{ "ptidej.viewer.ui.panel.OptionPanel::CHK_AGGREGATION_NAMES",
						"Aggregation Names" },
				{
						"ptidej.viewer.ui.panel.OptionPanel::CHK_AGGREGATION_DISPLAY",
						"Aggregation Display" },
				{ "ptidej.viewer.ui.panel.OptionPanel::CHK_COMPOSITION_NAMES",
						"Composition Names" },
				{
						"ptidej.viewer.ui.panel.OptionPanel::CHK_COMPOSITION_DISPLAY",
						"Composition Display" },
				{
						"ptidej.viewer.ui.panel.OptionPanel::CHK_CONT_AGGREGATION_NAMES",
						"Container Aggregation Names" },
				{
						"ptidej.viewer.ui.panel.OptionPanel::CHK_CONT_AGGREGATION_DISPLAY",
						"Container Aggregation Display" },
				{
						"ptidej.viewer.ui.panel.OptionPanel::CHK_CONT_COMPOSITION_NAMES",
						"Container Composition Names" },
				{
						"ptidej.viewer.ui.panel.OptionPanel::CHK_CONT_COMPOSITION_DISPLAY",
						"Container Composition Display" },
				{ "ptidej.viewer.ui.panel.OptionPanel::CHK_HIERARCHY_NAMES",
						"Hierarchy Names" },
				{ "ptidej.viewer.ui.panel.OptionPanel::CHK_HIERARCHY_DISPLAY",
						"Hierarchy Display" },
				{ "ptidej.viewer.ui.panel.OptionPanel::CHK_FIELD_NAMES",
						"Field Names" },
				{ "ptidej.viewer.ui.panel.OptionPanel::CHK_METHOD_NAMES",
						"Method Names" },
				{
						"ptidej.viewer.ui.panel.OptionPanel::CHK_FULLY_QUALIFIED_NAMES",
						"Fully Qualified Names" },
				{
						"ptidej.viewer.ui.panel.OptionPanel::CHK_GHOST_ENTITIES_DISPLAY",
						"Ghost Entities Display" },
				{ "ptidej.viewer.ui.panel.OptionPanel::RADIO_SELECT_ALL",
						"Select All" },
				{ "ptidej.viewer.ui.panel.OptionPanel::RADIO_UNSELECT_ALL",
						"Unselect All" },
				{ "ptidej.viewer.widget.CollapsablePanel::TOGGLE_EXPAND_ICON",
						"ExpandPanel.png" },
				{
						"ptidej.viewer.widget.CollapsablePanel::TOGGLE_EXPAND_ROLLOVER_ICON",
						"ExpandPanelHover.png" },
				{
						"ptidej.viewer.widget.CollapsablePanel::TOGGLE_EXPAND_SELECTED_ICON",
						"CollapsePanel.png" },
				{
						"ptidej.viewer.widget.CollapsablePanel::TOGGLE_EXPAND_SELECTED_ROLLOVER_ICON",
						"CollapsePanelHover.png" },
				{ "ptidej.viewer.widget.CollapsablePanel::CMD_CLOSE_ICON",
						"ClosePanel.png" },
				{
						"ptidej.viewer.widget.CollapsablePanel::CMD_CLOSE_ROLLOVER_ICON",
						"ClosePanelHover.png" },
				{ "ptidej.viewer.widget.UndockablePanel::CMD_UNDOCK_ICON",
						"Undock16.png" },
				{
						"ptidej.viewer.widget.UndockablePanel::CMD_UNDOCK_ROLLOVER_ICON",
						"UndockHover16.png" },
				{ "ptidej.viewer.widget.UndockablePanel::CMD_DOCK_ICON",
						"Dock16.png" },
				{
						"ptidej.viewer.widget.UndockablePanel::CMD_DOCK_ROLLOVER_ICON",
						"DockHover16.png" },
				{ "ptidej.viewer.widget.UndockablePanel::CMD_DOCK", "Dock" },
				{ "ptidej.viewer.widget.UndockablePanel::CMD_UNDOCK", "Undock" },
				{
						"ptidej.viewer.ui.panel.EPIPanel::CMD_EPI_FIND_SIMILAR_MICRO_ARCHITECTURE",
						"Find Similar Micro-Architectures" },
				{
						"ptidej.viewer.ui.panel.DesignMotifChoicePanel::RADIO_AbstractFactory",
						"Abstract Factory" },
				{
						"ptidej.viewer.ui.panel.DesignMotifChoicePanel::RADIO_Adapter",
						"Adapter" },
				{
						"ptidej.viewer.ui.panel.DesignMotifChoicePanel::RADIO_Builder",
						"Builder" },
				{
						"ptidej.viewer.ui.panel.DesignMotifChoicePanel::RADIO_ChainOfResponsibility",
						"Chain Of Responsibility" },
				{
						"ptidej.viewer.ui.panel.DesignMotifChoicePanel::RADIO_Command",
						"Command" },
				{
						"ptidej.viewer.ui.panel.DesignMotifChoicePanel::RADIO_Composite",
						"Composite" },
				{
						"ptidej.viewer.ui.panel.DesignMotifChoicePanel::RADIO_Creator",
						"Creator" },
				{
						"ptidej.viewer.ui.panel.DesignMotifChoicePanel::RADIO_Decorator",
						"Decorator" },
				{
						"ptidej.viewer.ui.panel.DesignMotifChoicePanel::RADIO_Facade",
						"Facade" },
				{
						"ptidej.viewer.ui.panel.DesignMotifChoicePanel::RADIO_FactoryMethod",
						"Factory Method" },
				{
						"ptidej.viewer.ui.panel.DesignMotifChoicePanel::RADIO_Flyweight",
						"Flyweight" },
				{
						"ptidej.viewer.ui.panel.DesignMotifChoicePanel::RADIO_GoodInheritance",
						"Good Inheritance" },
				{
						"ptidej.viewer.ui.panel.DesignMotifChoicePanel::RADIO_Mediator",
						"Mediator" },
				{
						"ptidej.viewer.ui.panel.DesignMotifChoicePanel::RADIO_Memento",
						"Memento" },
				{
						"ptidej.viewer.ui.panel.DesignMotifChoicePanel::RADIO_Observer",
						"Observer" },
				{
						"ptidej.viewer.ui.panel.DesignMotifChoicePanel::RADIO_Prototype",
						"Prototype" },
				{ "ptidej.viewer.ui.panel.DesignMotifChoicePanel::RADIO_Proxy",
						"Proxy" },
				{
						"ptidej.viewer.ui.panel.DesignMotifChoicePanel::RADIO_Singleton",
						"Singleton" },
				{ "ptidej.viewer.ui.panel.DesignMotifChoicePanel::RADIO_State",
						"State/Strategy" },
				{
						"ptidej.viewer.ui.panel.DesignMotifChoicePanel::RADIO_TemplateMethod",
						"Template Method" },
				{
						"ptidej.viewer.ui.panel.DesignMotifChoicePanel::RADIO_Visitor",
						"Visitor" },
				{
						"ptidej.viewer.ui.panel.SolverGeneralPanel::RADIO_AUTOMATIC_SOLVER",
						"Automatic Solver" },
				{
						"ptidej.viewer.ui.panel.SolverGeneralPanel::RADIO_COMBINATORIAL_AUTOMATIC_SOLVER",
						"Combinatorial Automatic Solver" },
				{
						"ptidej.viewer.ui.panel.SolverGeneralPanel::RADIO_SIMPLE_AUTOMATIC_SOLVER",
						"Simple Automatic Solver" },
				{ "ptidej.viewer.ui.panel.Solver3Panel::RADIO_AC4_PROBLEM",
						"AC-4 Problem" },
				{ "ptidej.viewer.ui.panel.Solver3Panel::RADIO_CUSTOM_PROBLEM",
						"Custom Problem" },
				{
						"ptidej.viewer.ui.panel.Solver3Panel::CMD_GENERATE_PROGRAM_MODEL",
						"Generate program model" },
				{
						"ptidej.viewer.ui.panel.Solver3Panel::CMD_GENERATE_SOLVER_EXECUTION_DATA",
						"Generate solver execution data" },
				{
						"ptidej.viewer.ui.panel.Solver3Panel::CMD_PTIDEJ_SOLVER_3_FIND_SIMILAR_MICRO_ARCHITECTURE",
						"Find Similar Micro-Architectures" },
				{
						"ptidej.viewer.ui.panel.Solver4Panel::CMD_PTIDEJ_SOLVER_4_FIND_SIMILAR_MICRO_ARCHITECTURE",
						"Find Similar Micro-Architectures" },
				{
						"ptidej.viewer.ui.panel.MetricalSolver4Panel::CMD_METRICAL_PTIDEJ_SOLVER_4_FIND_SIMILAR_MICRO_ARCHITECTURE",
						"Find Similar Micro-Architectures" },
				{ "ptidej.viewer.ui.panel.ToolPanel::CMD_SHOW_STATISTICS",
						"Show Model Statistics" },
				{ "ptidej.viewer.ui.panel.ToolPanel::CMD_LIST_ENTITIES",
						"List Model Entities" },
				{
						"ptidej.viewer.ui.panel.ToolPanel::CMD_LOAD_SIMILAR_MICRO_ARCHITECTURES",
						"Load Similar Micro-Architectures" },
				{
						"ptidej.viewer.ui.panel.ToolPanel::CMD_LIST_SIMILAR_MICRO_ARCHITECTURES",
						"List Similar Micro-Architectures" },
				{
						"ptidej.viewer.ui.panel.ToolPanel::CMD_REMOVE_ALL_SIMILAR_MICRO_ARCHITECTURES",
						"Remove All Similar Micro-Architectures" },
				{ "ptidej.viewer.ui.panel.ToolPanel::CMD_MODIFY_PROGRAM_MODEL",
						"Modify program model" },
				{ "ptidej.viewer.ui.DesktopPane::FRM_MAIN_TITLE",
						"Ptidej " + VERSION },
				{ "ptidej.viewer.ui.DesktopFrame::TAB_DESIGN_MOTIFS_TITLE",
						"Design Motifs" },
				{ "ptidej.viewer.ui.DesktopFrame::TAB_DESIGN_SMELLS_TITLE",
						"Design Smells" },
				{ "ptidej.viewer.ui.DesktopFrame::TAB_CODE_SMELLS_TITLE",
						"Code Smells" },
				{ "ptidej.viewer.ui.DesktopFrame::TAB_MICRO_PATTERNS_TITLE",
						"Micro Patterns" },
				{ "ptidej.viewer.ui.DesktopFrame::TAB_TOOLS_TITLE", "Tools" },
				{ "ptidej.viewer.ui.DesktopFrame::TAB_VISITORS_TITLE",
						"Visitors" },
				{ "ptidej.viewer.ui.DesktopFrame::TAB_VIEWER_OPTIONS_TITLE",
						"Viewer Options" },
				{ "ptidej.viewer.ui.panel.DesignMotifPanel::PNL_EPI_TITLE",
						"EPI" },
				{
						"ptidej.viewer.ui.panel.AntiPatternPanel::PNL_DESIGN_SMELLS_TITLE",
						"Design Smells" },
				{
						"ptidej.viewer.ui.panel.AntiPatternPanel::PNL_DESIGN_SMELLS_CHOICE_TITLE",
						"Design Smells" },
				{
						"ptidej.viewer.ui.panel.AntiPatternPanel::PNL_DESIGN_SMELLS_ACTION_TITLE",
						"Defintion and Detection" },
				{
						"ptidej.viewer.ui.panel.CodeSmellPanel::PNL_CODE_SMELLS_TITLE",
						"Code Smells" },
				{ "ptidej.viewer.ui.panel.DesignMotifPanel::PNL_DESIGN_MOTIFS_TITLE",
						"Design Motifs" },
				{ "ptidej.viewer.ui.panel.DesignMotifPanel::PNL_SOLVER_TITLE",
						"Solver" },
				{
						"ptidej.viewer.ui.panel.DesignMotifPanel::PNL_PTIDEJ_SOLVER_3_TITLE",
						"Ptidej Solver 3" },
				{
						"ptidej.viewer.ui.panel.DesignMotifPanel::PNL_PTIDEJ_SOLVER_4_TITLE",
						"Ptidej Solver 4" },
				{
						"ptidej.viewer.ui.panel.DesignMotifPanel::PNL_METRICAL_PTIDEJ_SOLVER_4_TITLE",
						"Metrical Ptidej Solver 4" },
				{
						"ptidej.viewer.ui.panel.DesignMotifPanel::PNL_PTIDEJ_SOLVERS_TITLE",
						"Ptidej Solvers" },
				{ "ptidej.viewer.ui.panel.VisitorPanel::PNL_WALKERS_TITLE",
						"Walkers" },
				{ "ptidej.viewer.ui.panel.VisitorPanel::PNL_WALKERS_TITLE",
						"Walkers" },
				{ "ptidej.viewer.ui.panel.VisitorPanel::PNL_GENERATORS_TITLE",
						"Generators" },
				{
						"ptidej.viewer.ui.panel.MicroPatternPanel::PNL_MICRO_PATTERNS_TITLE",
						"Micro Patterns" },
				{ "ptidej.viewer.ui.panel.ToolPanel::CMD_SYSTEMATIC_UML",
						"Systematic UML" },
				{
						"ptidej.viewer.ui.panel.ModelPanel::CMD_AOL_MODELS_COMPARISION",
						"AOL models comparision" },
				{
						"ptidej.viewer.ui.panel.ModelPanel::CMD_MODEL_DIFFERENCE_HIGHLIGHTER_FROM_METHODS",

						"Model difference highlighter (From Methods)" },
				{
						"ptidej.viewer.ui.panel.ModelPanel::CMD_MODEL_DIFFERENCE_HIGHLIGHTER_FROM_CLASSES",

						"Model difference highlighter (From Classes)" },
				{ "ptidej.viewer.ui.panel.OtherToolPanel::CMD_SUGIBIB",
						"SugiBib" },
				{
						"ptidej.viewer.ui.panel.OtherToolPanel::CMD_POM_BASED_METRICS",
						"POM-Based metrics" },
				{
						"ptidej.viewer.ui.panel.OtherToolPanel::CMD_ADJACENCY_MATRIX_OADYMPPAC",
						"Adjacency matrix (OADymPPaC)" },
				{
						"ptidej.viewer.ui.panel.OtherToolPanel::CMD_ADJACENCY_MATRIX_INFOVIS",
						"Adjacency matrix (InfoVis)" },
				{ "ptidej.viewer.ui.panel.OtherToolPanel::CMD_DOTTY", "Dotty" },

				{
						"ptidej.viewer.ui.panel.AntiPatternActionPanel::CMD_NEW_RULECARD",
						"New RuleCard" },
				{
						"ptidej.viewer.ui.panel.AntiPatternActionPanel::CMD_DETECT_DESIGN_SMELLS",
						"Detect Design Smell(s)" },
				{
						"ptidej.viewer.ui.panel.AntiPatternActionPanel::CMD_DELETE_RULECARD",
						"Delete RuleCard" },

				{ "sad.util.DesignSmellRepository::Err_LOAD_DESIGN_SMELL",
						"Cannot load design-smell list" },
				{ "sad.util.CodeSmellRepository::Err_LOAD_CODE_SMELL",
						"Cannot load code-smell list" },

				{ "ptidej.viewer.ui.panel.ToolPanel::PNL_UML_TITLE", "UML" },
				{ "ptidej.viewer.ui.panel.ToolPanel::PNL_MODELS_TITLE",
						"Models" },
				{ "ptidej.viewer.ui.panel.ToolPanel::PNL_OTHER_TOOLS_TITLE",
						"Other Tools" },
				{ "ptidej.viewer.widget.LookAndFeelMenu::MENU_LOOKANDFEELS",
						"Look and feels" },
				{
						"ptidej.viewer.widget.LookAndFeelMenu::MENU_LOOKANDFEELS_MNEMONIC",
						"o" },
				{ "ptidej.viewer.ui.about.AboutDialog::FRM_ABOUT_TITLE",
						"About the Ptidej Suite" },
				{ "ptidej.viewer.ui.about.AboutDialog::PTIDEJ_LOGO_ICON",
						"PtidejSplash.png" },
				{
						"ptidej.viewer.ui.about.AboutDialog::PTIDEJ_LOGO_FOOTER_ICON",
						"PtidejFooter.png" },
				{ "ptidej.viewer.ui.about.AboutDialog::TAB_PTIDEJ_TITLE",
						"Ptidej" },
				{ "ptidej.viewer.ui.about.AboutDialog::TAB_DEVELOPPERS_TITLE",
						"Developpers" },
				{ "ptidej.viewer.ui.about.AboutDialog::TAB_COPYRIGHT_TITLE",
						"Copyright" },
				{ "ptidej.viewer.ui.about.AboutDialog::TAB_TOOLS_SUITE_TITLE",
						"Tools Suite" },
				{ "ptidej.viewer.ui.splash.SplashScreen::FRM_ABOUT_TITLE",
						"About the Ptidej Suite" },
				{ "ptidej.viewer.ui.splash.SplashScreen::PTIDEJ_LOGO_ICON",
						"PtidejSplash.png" },
				{
						"ptidej.viewer.ui.splash.SplashScreen::PTIDEJ_LOGO_FOOTER_ICON",
						"PtidejFooter.png" },
				{ "ptidej.viewer.ui.splash.SplashScreen::TAB_PTIDEJ_TITLE",
						"Ptidej" },
				{
						"ptidej.viewer.ui.splash.SplashScreen::TAB_DEVELOPPERS_TITLE",
						"Developpers" },
				{ "ptidej.viewer.ui.splash.SplashScreen::TAB_COPYRIGHT_TITLE",
						"Copyright" },
				{
						"ptidej.viewer.ui.splash.SplashScreen::TAB_TOOLS_SUITE_TITLE",
						"Tools Suite" },
				{
						"ptidej.viewer.ui.splash.SplashScreen::RICH_TXT_GEODES",

						"<center><b>Ptidej Team</b><br>PolyMORSE, DGIGL<Br>Polytechnique Montréal</center>" },
				{
						"ptidej.viewer.ui.about.AboutDialog::RICH_TXT_PTIDEJ",

						"<html><body>The <b>Ptidej tool suite</b> is a set of tools to evaluate and to enhance the quality of object-"
								+ "oriented programs,<Br>promoting the use of patterns, either at the language-, design-, or "
								+ "architectural-level."
								+ "<p>The Ptidej tool suite (Pattern Trace Identification, Detection, and Enhancement in Java)<Br>"
								+ "by Yann-Gaël Guéhéneuc uses the PADL meta-model (Pattern and Abstract-level "
								+ "Description Language),<Br>extension of the PDL meta-model (Pattern Description Language)<Br>"
								+ "by Hervé Albin-Amiot.</p>"
								+ "<p>Get more information at <a href=\"http://www.ptidej.net\">www.ptidej.net</a> and <a href=\"http://wiki.ptidej.net\">wiki.ptidej.net</a>.<Br>"
								+ "Send comments and questions to <a href=\"mailto:info@ptidej.net\">info@ptidej.net</a>.</p></body></html>" },
				{
						"ptidej.viewer.ui.about.AboutDialog::RICH_TXT_DEVELOPPERS",

						"<html><body><b>Main developpers:</b>"
								+ "<blockquote>- <i>Yann-Gaël Guéhéneuc</i>"
								+ "<blockquote>Polytechnique Montréal<br>"
								+ "Université de Montréal<br>"
								+ "Ecole des Mines de Nantes<br>"
								+ "Object Technology International, Inc.</blockquote>"
								+ "- <i>Hervé Albin-Amiot.</i>"
								+ "<blockquote>Ecole des Mines de Nantes<br>"
								+ "Softmaint S.A.</blockquote></blockquote>"
								+ "<hr><b>With contributions by:</b><blockquote>"
								+

								"- <i>Aminata Sabané</i>"
								+ "<blockquote>Java creator of PADL models<br>"
								+ "Ph.D. student, 2012<br>"
								+ "Polytechnique Montréal.</blockquote>"
								+

								"- <i>Sébastien Colladon</i>"
								+ "<blockquote>C++ creator of PADL models<br>"
								+ "Intern, 2012<br>"
								+ "Polytechnique Montréal.</blockquote>"
								+

								"- <i>Gerardo Cepeda and Yousra Tagmouti</i>"
								+ "<blockquote>C# creator of PADL models<br>"
								+ "Post-docs, 2010-2011<br>"
								+ "Polytechnique Montréal.</blockquote>"
								+

								"- <i>Foutse Khomh</i>"
								+ "<blockquote>Patterns and Quality of Object-oriented Programs<br>"
								+ "Ph.D. student, 2010<br>"
								+ "Université de Montréal.</blockquote>"
								+

								"- <i>Naouel Moha</i>"
								+ "<blockquote>Classification, Identification, and Correction of Design Defects<br>"
								+ "Ph.D. student, 2008<br>"
								+ "Université de Montréal.</blockquote>"
								+

								"- <i>Saliha Bouden</i>"
								+ "<blockquote>Refactorings and Traceability of Models Transformation<br>"
								+ "Master student, December 2006<br>"
								+ "Université de Montréal.</blockquote>"
								+

								"- <i>Jean-Yves Guyomarc'h</i>"
								+ "<blockquote>Metrics for Aspect-Oriented Programs<br>"
								+ "Master student, April 2006<br>"
								+ "Université de Montréal.</blockquote>"
								+

								"- <i>Olivier Kaczor</i>"
								+ "<blockquote>Efficient Pattern Identification<br>"
								+ "Master student, August 2005<br>"
								+ "Université de Montréal.</blockquote>"
								+

								"- <i>Khashayar Khosravi</i>"
								+ "<blockquote>Design Pattern-Enabled Object-Oriented Metrics<br>"
								+ "Master student, August 2005<br>"
								+ "Université de Montréal.</blockquote>"
								+

								"- <i>Samah Rached</i>"
								+ "<blockquote>Program Behaviour Analysis with Adjacency Matrices and Formal Concept Analysis<br>"
								+ "Master student, August 2005<br>"
								+ "Université de Montréal.</blockquote>"
								+

								"- <i>Mohamed Kahla</i>"
								+ "<blockquote>Algorithms to lay-out Class Diagrams<br>"
								+ "Undergraduate, Summer 2006<br>"
								+ "Université de Montréal.</blockquote>"
								+

								"- <i>Pierre Leduc</i>"
								+ "<blockquote>A Language to Define Design Defects and"
								+ "to Generate Detection Algorithms Automatically<br>"
								+ "Undergraduate, Summer 2006<br>"
								+ "Université de Montréal.</blockquote>"
								+

								"- <i>Pierre Leduc and Julien Tanteri</i>"
								+ "<blockquote>Implementing Micro-patterns in Ptidej"
								+ "Undergraduates, Winter 2006<br>"
								+ "Université de Montréal.</blockquote>"
								+

								"- <i>Mehdi Lahlou</i>"
								+ "<blockquote>On the Ptidej UI Standalone Swing Interface and<br>"
								+ "Multilanguage Managing<br>"
								+ "Undergraduate, Summer 2005<br>"
								+ "Université de Montréal.</blockquote>"
								+

								"- <i>Others including but not limited to</i>"
								+ "- <i>Jean-Yves Guyomarc'h</i>"
								+ "<blockquote>On the Ptidej Solver in Java and metrics<br>"
								+ "Graduate, fall 2004<br>"
								+ "Université de Montréal.</blockquote>"
								+ "- <i>Lulzim Laloshi and Driton Salihu</i>"
								+ "<blockquote>On the Eclipse plug-in<br>"
								+ "Undergraduates, Summer 2004<br>"
								+ "Université de Montréal.</blockquote>"
								+ "- <i>Ward Flores and Sébastien Robidoux</i>"
								+ "<blockquote>On the C++ creator and PADL<br>"
								+ "Undergraduates, Summer 2004<br>"
								+ "Université de Montréal.</blockquote>"
								+ "- <i>Salime Bensemmane, Iyadh Sidhom, and Fayéal Skhiri</i>"
								+ "<blockquote>On the Ptidej Solver in Java<br>"
								+ "Undergraduates, Summer 2004<br>"
								+ "Université de Montréal.</blockquote>"
								+ "- <i>Farouk Zaidi</i>"
								+ "<blockquote>On the Java classfile creator and metrics<br>"
								+ "Trainee, Winter 2004<br>"
								+ "Université de technologie Belfort-Montbéliard<br>"
								+ "Centre de Recherche en Informatique de Montréal<br>"
								+ "Université de Montréal.</blockquote></body></html>" },
				{
						"ptidej.viewer.ui.about.AboutDialog::RICH_TXT_COPYRIGHT",

						"<html><body><p align='center'><b>The Ptidej tool suite, copyright (c) 2000-2013,<br>"
								+ "Polytechnique Montréal,<br>"
								+ "Yann-Gaël Guéhéneuc.<br>"
								+ "All right reserved.</p></b>"
								+ "<p color='red'>This software is made available AS IS, and THE AUTHORS DISCLAIM ALL WARRANTIES,<br>"
								+ "EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE IMPLIED WARRANTIES<br>"
								+ "OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE, AND NOT<br>"
								+ "WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN ANY LIABILITY FOR<br>"
								+ "DAMAGES RESULTING FROM THE SOFTWARE OR ITS USE IS EXPRESSLY<br>"
								+ "DISCLAIMED, WHETHER ARISING IN CONTRACT, TORT (INCLUDING NEGLIGENCE)<br>"
								+ "OR STRICT, LIABILITY EVEN IF THE AUTHORS ARE ADVISED OF THE POSSIBILITY<br>"
								+ "OF SUCH DAMAGES.<p><body><html>" },
				{
						"ptidej.viewer.ui.about.AboutDialog::RICH_TXT_TOOLS_SUITE",
						"<html><body>" + "- Ptidej UI Viewer " + VERSION
								+ "<blockquote>" + "- CPL<br>" + "- EPI<br>"
								+ "- JavaParser<br>" + "- JChoco<br>"
								+ "- Mendel<br>" + "- PADL<br>"
								+ "- PADL Analyses<br>"
								+ "- PADL Creator AOL<br>"
								+ "- PADL Creator AspectJ<br>"
								+ "- PADL Creator C#<br>"
								+ "- PADL Creator C++<br>"
								+ "- PADL Creator ClassFile<br>"
								+ "- PADL Creator JavaFile<br>"
								+ "- PADL Design Motifs<br>"
								+ "- PADL Generator<br>"
								+ "- PADL Micro-pattern Analysis<br>"
								+ "- PADL Statements<br>"
								+ "- PADL Statements Creator AOL<br>"
								+ "- PADL Statements Creator ClassFile<br>"
								+ "- POM<br>" + "- Ptidej<br>"
								+ "- Ptidej Solver 4<br>"
								+ "- Ptidej Solver Metrics<br>"
								+ "- Ptidej UI<br>"
								+ "- Ptidej UI Analyses<br>"
								+ "- Ptidej UI AspectJ<br>"
								+ "- Ptidej UI C++<br>"
								+ "- Ptidej UI Layouts<br>"
								+ "- Ptidej UI Primitives AWT<br>"
								+ "- Ptidej UI Viewer"
								+ "- Ptidej UI Viewer Extensions<br>"
								+ "- Ptidej UI Viewer Standalone Swing<br>"
								+ "- SAD<br>" + "- SAD Rules<br>"
								+ "- SQUAD<br>" + "</blockquote>"
								+ "</body></html>" } };

	public Object[][] getContents() {
		return PtidejResourceBundle.CONTENTS;
	}
}
