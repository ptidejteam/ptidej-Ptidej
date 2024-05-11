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
package ptidej.viewer.utils;

import util.multilingual.MultilingualManager;

public final class Resources {
	// TODO: Remove most of these constants, in particular those related to design motfs! Use reflection!
	public static final String ABOUT = "ABOUT";
	public static final String ABSTRACT_FACTORY = "AbstractFactory";
	public static final String ADAPTER = "Adapter";
	public static final String ADD = "ADD";
	public static final String ADJACENCY_MATRIX = "ADJACENCY_MATRIX";
	public static final String ADJACENCY_MATRIX_INFOVIS =
		"ADJACENCY_MATRIX_INFOVIS";
	public static final String ADJACENCY_MATRIX_INFOVIS_HELP =
		"ADJACENCY_MATRIX_INFOVIS_HELP";
	public static final String ADJACENCY_MATRIX_OADYMPPAC =
		"ADJACENCY_MATRIX_OADYMPPAC";
	public static final String ADJACENCY_MATRIX_OADYMPPAC_HELP =
		"ADJACENCY_MATRIX_OADYMPPAC_HELP";
	public static final String AGGREGATION_DISPLAY = "AGGREGATION_DISPLAY";
	public static final String AGGREGATION_NAMES = "AGGREGATION_NAMES";
	public static final String AOL_CODE_FILE = "AOL_CODE_FILE";
	public static final String AOL_IDIOM_FILE = "AOL_IDIOM_FILE";
	public static final String AOL_MODELS_COMPARISION =
		"AOL_MODELS_COMPARISION";
	public static final String ASPECTJ_FILE = "ASPECTJ_FILE";
	public static final String ASSOCIATION_DISPLAY = "ASSOCIATION_DISPLAY";
	public static final String ASSOCIATION_NAMES = "ASSOCIATION_NAMES";
	public static final String BUILDER = "Builder";
	public static final String CASCADE = "CASCADE";
	public static final String CHAIN_OF_RESPONSIBILITY =
		"ChainOfResponsibility";
	public static final String CHK = "CHK_";
	public static final String CLOSE = "CLOSE";
	public static final String CLOSE_ACTIVE = "CLOSE_ACTIVE";
	public static final String CLOSE_ALL = "CLOSE_ALL";
	public static final String CLOSE_ALL_EXPECT_ACTIVE =
		"CLOSE_ALL_EXPECT_ACTIVE";
	public static final String CMD = "CMD_";
	public static final String CODE_SMELLS = "CODE_SMELLS";
	public static final String COMMAND = "Command";
	public static final String COMPOSITE = "Composite";
	public static final String COMPOSITION_DISPLAY = "COMPOSITION_DISPLAY";
	public static final String COMPOSITION_NAMES = "COMPOSITION_NAMES";
	public static final String CONTAINER_AGGREGATION_DISPLAY =
		"CONT_AGGREGATION_DISPLAY";
	public static final String CONTAINER_AGGREGATION_NAMES =
		"CONT_AGGREGATION_NAMES";
	public static final String CONTAINER_COMPOSITION_DISPLAY =
		"CONT_COMPOSITION_DISPLAY";
	public static final String CONTAINER_COMPOSITION_NAMES =
		"CONT_COMPOSITION_NAMES";
	public static final String COPYRIGHT = "COPYRIGHT";
	public static final String CPP_FILE = "CPP_FILE";
	public static final String CREATION_DISPLAY = "CREATION_DISPLAY";
	public static final String CREATION_NAMES = "CREATION_NAMES";
	public static final String CREATOR = "Creator";
	public static final String DECORATOR = "Decorator";
	public static final String DELETE_RULECARD = "DELETE_RULECARD";
	public static final String DESIGN_MOTIFS = "DESIGN_MOTIFS";
	public static final String DESIGN_SMELLS = "DESIGN_SMELLS";
	public static final String DESIGN_SMELLS_ACTION = "DESIGN_SMELLS_ACTION";
	public static final String DETECT_DESIGN_SMELLS = "DETECT_DESIGN_SMELLS";
	public static final String DEVELOPPERS = "DEVELOPPERS";
	public static final String DOCK = "DOCK";
	public static final String DOTTY = "DOTTY";
	public static final String DOTTY_HELP = "DOTTY_HELP";
	public static final String DROP_DOWN = "DROP_DOWN";
	public static final String ECLIPSE_JDT_PROJECT = "ECLIPSE_JDT_PROJECT";
	public static final String EMPTY = "EMPTY";
	public static final String EPI = "EPI";
	public static final String EPI_FIND_SIMILAR_MICRO_ARCHITECTURE =
		"EPI_FIND_SIMILAR_MICRO_ARCHITECTURE";
	public static final String EPI_SIMILAR_MICRO_ARCHITECTURE =
		"EPI_SIMILAR_MICRO_ARCHITECTURE";
	public static final String EPI_SIMILAR_MICRO_ARCHITECTURE_HELP =
		"EPI_SIMILAR_MICRO_ARCHITECTURE_HELP";
	public static final String EXIT = "EXIT";
	public static final String EXPAND = "EXPAND";
	public static final String EXPORT_SVG = "EXPORT_SVG";
	public static final String FACADE = "Facade";
	public static final String FACTORY_METHOD = "FactoryMethod";
	public static final String FIELD_NAMES = "FIELD_NAMES";
	public static final String FILE = "FILE";
	public static final String FLY_WEIGHT = "Flyweight";
	public static final String FRM = "FRM_";
	public static final String FULLY_QUALIFIED_NAMES = "FULLY_QUALIFIED_NAMES";
	public static final String GENERATE_PROGRAM_MODEL =
		"GENERATE_PROGRAM_MODEL";
	public static final String GENERATE_SOLVER_EXECUTION_DATA =
		"GENERATE_SOLVER_EXECUTION_DATA";
	public static final String GENERATORS = "GENERATORS";
	public static final String GHOST_ENTITIES_DISPLAY =
		"GHOST_ENTITIES_DISPLAY";
	public static final String GOOD_INHERITANCE = "GoodInheritance";
	public static final String GRAPH_LAYOUT = "GRAPH_LAYOUT";
	public static final String HELP = "HELP";
	public static final String HELP_TOPICS = "HELP_TOPICS";
	public static final String HIERARCHY_DISPLAY = "HIERARCHY_DISPLAY";
	public static final String HIERARCHY_NAMES = "HIERARCHY_NAMES";
	public static final String ICON = "_ICON";
	public static final String INHERITANCE_CLUSTERING_LAYOUT =
		"InheritanceClustering";
	public static final String INHERITANCE_DEPTH_CLUSTERING_LAYOUT =
		"InheritanceDepthClustering";
	public static final String ITEM = "ITEM_";
	public static final String JAVA_ARCHIVE_FILE = "JAVA_ARCHIVE_FILE";
	public static final String JAVA_CLASS_FILE = "JAVA_CLASS_FILE";
	public static final String JAVA_SOURCE_FILE = "JAVA_SOURCE_FILE";
	public static final String LAYOUT_TOPICS = "LAYOUT_TOPICS";
	public static final String LINK = "_LINK";
	public static final String LIST_ENTITIES = "LIST_ENTITIES";
	public static final String LIST_SIMILAR_MICRO_ARCHITECTURES =
		"LIST_SIMILAR_MICRO_ARCHITECTURES";
	public static final String LOAD_DUAL_HIERARCHICAL_PROJECT =
		"LOAD_DUAL_HIERARCHICAL_PROJECT";
	public static final String LOAD_EXTRINSIC = "LOAD_EXTRINSIC";
	public static final String LOAD_GRAPHICAL_PROJECT =
		"LOAD_GRAPHICAL_PROJECT";
	public static final String LOAD_HIERARCHICAL_PROJECT =
		"LOAD_HIERARCHICAL_PROJECT";
	public static final String LOAD_SIMILAR_MICRO_ARCHITECTURES =
		"LOAD_SIMILAR_MICRO_ARCHITECTURES";
	public static final String LOOKANDFEELS = "LOOKANDFEELS";
	public static final String MAIN = "MAIN";
	public static final String MEDIATOR = "Mediator";
	public static final String MEMENTO = "Memento";
	public static final String MENU = "MENU_";
	public static final String METHOD_NAMES = "METHOD_NAMES";
	public static final String METRICAL_PTIDEJ_SOLVER_4_FIND_SIMILAR_MICRO_ARCHITECTURE =
		"METRICAL_PTIDEJ_SOLVER_4_FIND_SIMILAR_MICRO_ARCHITECTURE";
	public static final String METRICAL_PTIDEJ_SOLVER_4_SIMILAR_MICRO_ARCHITECTURE =
		"METRICAL_PTIDEJ_SOLVER_4_SIMILAR_MICRO_ARCHITECTURE";
	public static final String METRICAL_PTIDEJ_SOLVER_4_SIMILAR_MICRO_ARCHITECTURE_HELP =
		"METRICAL_PTIDEJ_SOLVER_4_SIMILAR_MICRO_ARCHITECTURE_HELP";
	public static final String MICRO_PATTERNS = "MICRO_PATTERNS";
	public static final String MINIMIZE_ALL = "MINIMIZE_ALL";
	public static final String MNEMONIC = "_MNEMONIC";
	public static final String MODEL_DIFFERENCE_HIGHLIGHTER =
		"MODEL_DIFFERENCE_HIGHLIGHTER";
	public static final String MODEL_DIFFERENCE_HIGHLIGHTER_FROM_CLASSES =
		"MODEL_DIFFERENCE_HIGHLIGHTER_FROM_CLASSES";
	public static final String MODEL_DIFFERENCE_HIGHLIGHTER_FROM_METHODS =
		"MODEL_DIFFERENCE_HIGHLIGHTER_FROM_METHODS";
	public static final String MODELS = "MODELS";
	public static final String MODIFY_PROGRAM_MODEL = "MODIFY_PROGRAM_MODEL";
	public static final String MSE_FILE = "MSE_FILE";
	public static final Object NEW_DUAL_HIERARCHICAL_PROJECT =
		"NEW_DUAL_HIERARCHICAL_PROJECT";
	public static final String NEW_GRAPHICAL_PROJECT = "NEW_GRAPHICAL_PROJECT";
	public static final String NEW_HIERARCHICAL_PROJECT =
		"NEW_HIERARCHICAL_PROJECT";
	public static final String NEW_RULECARD = "NEW_RULECARD";
	public static final String NULL_SYM = "${NULL}";
	public static final int NUMBER_OF_ENTITIES_FOR_WARNING = 10;
	public static final String OBSERVER = "Observer";
	public static final int OPTIONS_LAYER = Integer.MIN_VALUE + 1;
	public static final String OTHER_TOOLS = "OTHER_TOOLS";
	public static final String PNL = "PNL_";
	public static final String POM_BASED_METRICS = "POM_BASED_METRICS";
	public static final String PRINT = "PRINT";
	public static final String PROBLEM = "PROBLEM";
	public static final String PROBLEM_AC4 = "AC4_PROBLEM";
	public static final String PROBLEM_CUSTOM = "CUSTOM_PROBLEM";
	public static final int PROJECTS_LAYER = Integer.MIN_VALUE;
	public static final String PROTOTYPE = "Prototype";
	public static final String PROXY = "Proxy";
	public static final String PTIDEJ = "PTIDEJ";
	public static final String PTIDEJ_EPI = "EPI";
	public static final String PTIDEJ_FILE = "PTIDEJ_FILE";
	public static final String PTIDEJ_LOGO = "PTIDEJ_LOGO";
	public static final String PTIDEJ_LOGO_FOOTER = "PTIDEJ_LOGO_FOOTER";
	public static final String PTIDEJ_METRICAL_SOLVER_4 =
		"METRICAL_PTIDEJ_SOLVER_4";
	public static final String PTIDEJ_SOLVER_3 = "PTIDEJ_SOLVER_3";
	public static final String PTIDEJ_SOLVER_3_FIND_SIMILAR_MICRO_ARCHITECTURE =
		"PTIDEJ_SOLVER_3_FIND_SIMILAR_MICRO_ARCHITECTURE";
	public static final String PTIDEJ_SOLVER_3_SIMILAR_MICRO_ARCHITECTURE =
		"PTIDEJ_SOLVER_3_SIMILAR_MICRO_ARCHITECTURE";
	public static final String PTIDEJ_SOLVER_3_SIMILAR_MICRO_ARCHITECTURE_HELP =
		"PTIDEJ_SOLVER_3_SIMILAR_MICRO_ARCHITECTURE_HELP";
	public static final String PTIDEJ_SOLVER_4 = "PTIDEJ_SOLVER_4";
	public static final String PTIDEJ_SOLVER_4_FIND_SIMILAR_MICRO_ARCHITECTURE =
		"PTIDEJ_SOLVER_4_FIND_SIMILAR_MICRO_ARCHITECTURE";
	public static final String PTIDEJ_SOLVER_4_SIMILAR_MICRO_ARCHITECTURE =
		"PTIDEJ_SOLVER_4_SIMILAR_MICRO_ARCHITECTURE";
	public static final String PTIDEJ_SOLVER_4_SIMILAR_MICRO_ARCHITECTURE_HELP =
		"PTIDEJ_SOLVER_4_SIMILAR_MICRO_ARCHITECTURE_HELP";
	public static final String PTIDEJ_SOLVERS = "PTIDEJ_SOLVERS";
	public static final String RADIO = "RADIO_";
	public static final String REMOVE_ALL_SIMILAR_MICRO_ARCHITECTURES =
		"REMOVE_ALL_SIMILAR_MICRO_ARCHITECTURES";
	public static final String RICH_TXT = "RICH_TXT_";
	public static final String ROLLOVER = "_ROLLOVER";
	public static final String RULECARD = "RULECARD";
	public static final String SAVE_ACTIVE = "SAVE_ACTIVE";
	public static final String SAVE_ALL = "SAVE_ALL";
	public static final String SELECT_ALL = "SELECT_ALL";
	public static final String SELECTED = "_SELECTED";
	public static final String SETTINGS = "SETTINGS";
	public static final String SHOW_STATISTICS = "SHOW_STATISTICS";
	public static final String SIMPLE_LAYOUT = "Simple";
	public static final String SINGLETON = "Singleton";
	public static final String SMALL_ICON = "_SMALL_ICON";
	public static final String SOLVER = "SOLVER";
	public static final String SOLVER_COMBINATORIAL_AUTOMATIC =
		"COMBINATORIAL_AUTOMATIC_SOLVER";
	public static final String SOLVER_PROBLEM_AUTOMATIC = "AUTOMATIC_SOLVER";
	public static final String SOLVER_SIMPLE_AUTOMATIC =
		"SIMPLE_AUTOMATIC_SOLVER";
	public static final String STATE = "State";
	public static final String SUGIBIB = "SUGIBIB";
	public static final String SUGIBIB_HELP = "SUGIBIB_HELP";
	public static final String SUGIYAMA_LAYOUT = "SugiyamaAlgorithm";
	public static final String SYSTEMATIC_UML = "SYSTEMATIC_UML";
	public static final String SYSTEMATIC_UML_HELP = "SYSTEMATIC_UML_HELP";
	public static final String TAB = "TAB_";
	public static final String TEMPLATE_METHOD = "TemplateMethod";
	public static final String TILE_HORIZONTALLY = "TILE_HORIZONTALLY";
	public static final String TILE_VERTICALLY = "TILE_VERTICALLY";
	public static final String TITLE = "_TITLE";
	public static final String TOGGLE = "TOGGLE_";
	public static final String TOOLBAR = "TOOLBAR_";
	public static final String TOOLBARS = "TOOLBARS";
	public static final String TOOLS = "TOOLS";
	public static final String TOOLS_SUITE = "TOOLS_SUITE";
	public static final String UML = "UML";
	public static final String UNDOCK = "UNDOCK";
	public static final String UNSELECT_ALL = "UNSELECT_ALL";
	public static final String USAGE = "USAGE";
	public static final String USE_DISPLAY = "USE_DISPLAY";
	public static final String USE_NAMES = "USE_NAMES";
	public static final String VIEW = "VIEW";
	public static final String VIEWER_OPTIONS = "VIEWER_OPTIONS";
	public static final String VISITOR = "Visitor";
	public static final String VISITORS = "VISITORS";
	public static final String WALKERS = "WALKERS";
	public static final String WINDOW = "WINDOW";

	public static String getButtonIconPath(final String aKey, final Class aClass) {
		return getButtonText(aKey.concat(ICON), aClass);
	}
	public static String getButtonText(final String aKey, final Class aClass) {
		return MultilingualManager.getString(CMD.concat(aKey), aClass);
	}
	public static String getCheckboxText(final String aKey, final Class aClass) {
		return MultilingualManager.getString(CHK.concat(aKey), aClass);
	}
	public static String getFrameTitle(final String aKey, final Class aClass) {
		return MultilingualManager.getString(
			FRM.concat(aKey).concat(TITLE),
			aClass);
	}
	public static String getIconPath(final String aKey, final Class aClass) {
		return MultilingualManager.getString(aKey.concat(ICON), aClass);
	}
	public static String getItemIconPath(final String aKey, final Class aClass) {
		return getItemText(aKey.concat(SMALL_ICON), aClass);
	}
	public static String getItemMnemonic(final String aKey, final Class aClass) {
		return getItemText(aKey.concat(MNEMONIC), aClass);
	}
	public static String getItemText(final String aKey, final Class aClass) {
		return MultilingualManager.getString(ITEM.concat(aKey), aClass);
	}
	public static String getLink(final String aKey) {
		return MultilingualManager.getString(aKey.concat(LINK));
	}
	public static String getMenuIconPath(final String aKey, final Class aClass) {
		return getMenuText(aKey.concat(SMALL_ICON), aClass);
	}
	public static String getMenuMnemonic(final String aKey, final Class aClass) {
		return getMenuText(aKey.concat(MNEMONIC), aClass);
	}
	public static String getMenuText(final String aKey, final Class aClass) {
		return MultilingualManager.getString(MENU.concat(aKey), aClass);
	}
	public static String getPanelTitle(final String aKey, final Class aClass) {
		return MultilingualManager.getString(
			PNL.concat(aKey).concat(TITLE),
			aClass);
	}
	public static String getRadioIconPath(final String aKey, final Class aClass) {
		return getRadioText(aKey.concat(SMALL_ICON), aClass);
	}
	public static String getRadioMnemonic(final String aKey, final Class aClass) {
		return getRadioText(aKey.concat(MNEMONIC), aClass);
	}
	public static String getRadioText(final String aKey, final Class aClass) {
		return MultilingualManager.getString(RADIO.concat(aKey), aClass);
	}
	public static String getRichText(final String aKey, final Class aClass) {
		return MultilingualManager.getString(RICH_TXT.concat(aKey), aClass);
	}
	public static String getRolloverButtonIconPath(
		final String aKey,
		final Class aClass) {

		return getButtonText(aKey.concat(ROLLOVER).concat(ICON), aClass);
	}
	public static String getRolloverButtonText(
		final String aKey,
		final Class aClass) {

		return getButtonText(aKey.concat(ROLLOVER), aClass);
	}
	public static String getRolloverToggleButtonIconPath(
		final String aKey,
		final Class aClass) {

		return getToggleButtonText(aKey.concat(ROLLOVER).concat(ICON), aClass);
	}
	public static String getSelectedRolloverToggleButtonIconPath(
		final String aKey,
		final Class aClass) {

		return getToggleButtonText(aKey
			.concat(SELECTED)
			.concat(ROLLOVER)
			.concat(ICON), aClass);
	}
	public static String getSelectedToggleButtonIconPath(
		final String aKey,
		final Class aClass) {

		return getToggleButtonText(aKey.concat(SELECTED).concat(ICON), aClass);
	}
	public static String getSelectedToggleButtonText(
		final String aKey,
		final Class aClass) {

		return MultilingualManager.getString(
			TOGGLE.concat(aKey).concat(SELECTED),
			aClass);
	}
	public static String getTabTitle(final String aKey, final Class aClass) {
		return MultilingualManager.getString(
			TAB.concat(aKey).concat(TITLE),
			aClass);
	}
	public static String getToggleButtonIconPath(
		final String aKey,
		final Class aClass) {

		return getToggleButtonText(aKey.concat(ICON), aClass);
	}
	public static String getToggleButtonText(
		final String aKey,
		final Class aClass) {

		return MultilingualManager.getString(TOGGLE.concat(aKey), aClass);
	}
	public static String getToolbarTitle(final String aKey, final Class aClass) {
		return MultilingualManager.getString(
			TOOLBAR.concat(aKey).concat(TITLE),
			aClass);
	}

	private Resources() {
	}
}
