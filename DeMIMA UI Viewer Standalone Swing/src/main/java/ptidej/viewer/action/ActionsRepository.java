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
package ptidej.viewer.action;

import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractAction;
import ptidej.viewer.utils.Resources;
import util.io.ProxyConsole;

// TODO To remove, too many indirections.
public class ActionsRepository {
	// TODO: Use static initialisers!
	private static String[] actionsGroups = { Resources.FILE,
			Resources.DESIGN_MOTIFS, Resources.PTIDEJ_SOLVER_3,
			Resources.PTIDEJ_SOLVER_4, Resources.PTIDEJ_METRICAL_SOLVER_4,
			Resources.PTIDEJ_SOLVERS, Resources.WINDOW,
			Resources.VIEWER_OPTIONS, Resources.HELP, Resources.DESIGN_SMELLS,
			Resources.EPI, Resources.GRAPH_LAYOUT };
	private static ActionsRepository UniqueInstance;
	private static Map fill(final String strGroup) {
		final Map actionsGroup = new HashMap();

		if (strGroup.equals(actionsGroups[0])) { // FILE
			actionsGroup.put(
				Resources.NEW_DUAL_HIERARCHICAL_PROJECT,
				FileAction.getInstance());
			actionsGroup.put(
				Resources.NEW_GRAPHICAL_PROJECT,
				FileAction.getInstance());
			actionsGroup.put(
				Resources.NEW_HIERARCHICAL_PROJECT,
				FileAction.getInstance());
			actionsGroup.put(
				Resources.LOAD_DUAL_HIERARCHICAL_PROJECT,
				FileAction.getInstance());
			actionsGroup.put(
				Resources.LOAD_GRAPHICAL_PROJECT,
				FileAction.getInstance());
			actionsGroup.put(
				Resources.LOAD_HIERARCHICAL_PROJECT,
				FileAction.getInstance());
			actionsGroup
				.put(Resources.LOAD_EXTRINSIC, FileAction.getInstance());
			actionsGroup.put(Resources.AOL_CODE_FILE, FileAction.getInstance());
			actionsGroup
				.put(Resources.AOL_IDIOM_FILE, FileAction.getInstance());
			actionsGroup.put(Resources.ASPECTJ_FILE, FileAction.getInstance());
			actionsGroup.put(Resources.MSE_FILE, FileAction.getInstance());
			actionsGroup.put(
				Resources.ECLIPSE_JDT_PROJECT,
				FileAction.getInstance());
			actionsGroup.put(Resources.CPP_FILE, FileAction.getInstance());
			actionsGroup.put(
				Resources.JAVA_CLASS_FILE,
				FileAction.getInstance());
			actionsGroup.put(
				Resources.JAVA_ARCHIVE_FILE,
				FileAction.getInstance());
			actionsGroup.put(
				Resources.JAVA_SOURCE_FILE,
				FileAction.getInstance());
			actionsGroup.put(Resources.PTIDEJ_FILE, FileAction.getInstance());
			actionsGroup.put(Resources.EXPORT_SVG, FileAction.getInstance());
			actionsGroup.put(Resources.CLOSE_ALL, FileAction.getInstance());
			actionsGroup.put(Resources.CLOSE_ACTIVE, FileAction.getInstance());
			actionsGroup.put(
				Resources.CLOSE_ALL_EXPECT_ACTIVE,
				FileAction.getInstance());
			actionsGroup.put(Resources.SAVE_ALL, FileAction.getInstance());
			actionsGroup.put(Resources.SAVE_ACTIVE, FileAction.getInstance());
			actionsGroup.put(Resources.PRINT, FileAction.getInstance());
			actionsGroup.put(Resources.EXIT, FileAction.getInstance());
		}
		else if (strGroup.equals(actionsGroups[1])) { // PATTERN
			actionsGroup.put(
				Resources.ABSTRACT_FACTORY,
				PatternChoiceAction.getInstance());
			actionsGroup.put(
				Resources.ADAPTER,
				PatternChoiceAction.getInstance());
			actionsGroup.put(
				Resources.BUILDER,
				PatternChoiceAction.getInstance());
			actionsGroup.put(
				Resources.CHAIN_OF_RESPONSIBILITY,
				PatternChoiceAction.getInstance());
			actionsGroup.put(
				Resources.COMMAND,
				PatternChoiceAction.getInstance());
			actionsGroup.put(
				Resources.COMPOSITE,
				PatternChoiceAction.getInstance());
			actionsGroup.put(
				Resources.CREATOR,
				PatternChoiceAction.getInstance());
			actionsGroup.put(
				Resources.DECORATOR,
				PatternChoiceAction.getInstance());
			actionsGroup.put(
				Resources.FACADE,
				PatternChoiceAction.getInstance());
			actionsGroup.put(
				Resources.FACTORY_METHOD,
				PatternChoiceAction.getInstance());
			actionsGroup.put(
				Resources.FLY_WEIGHT,
				PatternChoiceAction.getInstance());
			actionsGroup.put(
				Resources.GOOD_INHERITANCE,
				PatternChoiceAction.getInstance());
			actionsGroup.put(
				Resources.MEDIATOR,
				PatternChoiceAction.getInstance());
			actionsGroup.put(
				Resources.MEMENTO,
				PatternChoiceAction.getInstance());
			actionsGroup.put(
				Resources.OBSERVER,
				PatternChoiceAction.getInstance());
			actionsGroup.put(
				Resources.PROTOTYPE,
				PatternChoiceAction.getInstance());
			actionsGroup
				.put(Resources.PROXY, PatternChoiceAction.getInstance());
			actionsGroup.put(
				Resources.SINGLETON,
				PatternChoiceAction.getInstance());
			actionsGroup
				.put(Resources.STATE, PatternChoiceAction.getInstance());
			actionsGroup.put(
				Resources.TEMPLATE_METHOD,
				PatternChoiceAction.getInstance());
			actionsGroup.put(
				Resources.VISITOR,
				PatternChoiceAction.getInstance());
		}
		else if (strGroup.equals(actionsGroups[2])) { // PTIDEJ_SOLVER_3
			actionsGroup.put(
				Resources.PROBLEM_AC4,
				Solver3Action.getUniqueInstance());
			actionsGroup.put(
				Resources.PROBLEM_CUSTOM,
				Solver3Action.getUniqueInstance());
			actionsGroup.put(
				Resources.GENERATE_PROGRAM_MODEL,
				Solver3Action.getUniqueInstance());
			actionsGroup.put(
				Resources.GENERATE_SOLVER_EXECUTION_DATA,
				Solver3Action.getUniqueInstance());
			actionsGroup.put(
				Resources.PTIDEJ_SOLVER_3_FIND_SIMILAR_MICRO_ARCHITECTURE,
				Solver3Action.getUniqueInstance());
			actionsGroup.put(
				Resources.PTIDEJ_SOLVER_3_SIMILAR_MICRO_ARCHITECTURE_HELP,
				Solver3Action.getUniqueInstance());
		}
		else if (strGroup.equals(actionsGroups[3])) { // PTIDEJ_SOLVER_4
			actionsGroup.put(
				Resources.PTIDEJ_SOLVER_4_FIND_SIMILAR_MICRO_ARCHITECTURE,
				Solver4Action.getInstance());
			actionsGroup.put(
				Resources.PTIDEJ_SOLVER_4_SIMILAR_MICRO_ARCHITECTURE_HELP,
				Solver4Action.getInstance());
		}
		else if (strGroup.equals(actionsGroups[4])) { // METRICAL_PTIDEJ_SOLVER_4
			actionsGroup
				.put(
					Resources.METRICAL_PTIDEJ_SOLVER_4_FIND_SIMILAR_MICRO_ARCHITECTURE,
					SolverFingerprintsAction.getInstance());
			actionsGroup
				.put(
					Resources.METRICAL_PTIDEJ_SOLVER_4_SIMILAR_MICRO_ARCHITECTURE_HELP,
					SolverFingerprintsAction.getInstance());
		}
		else if (strGroup.equals(actionsGroups[5])) { // SOLVERS
			actionsGroup.put(
				Resources.SOLVER_PROBLEM_AUTOMATIC,
				SolversAction.getInstance());
			actionsGroup.put(
				Resources.SOLVER_COMBINATORIAL_AUTOMATIC,
				SolversAction.getInstance());
			actionsGroup.put(
				Resources.SOLVER_SIMPLE_AUTOMATIC,
				SolversAction.getInstance());
			actionsGroup.put(
				Resources.SHOW_STATISTICS,
				SolversAction.getInstance());
			actionsGroup.put(
				Resources.LIST_ENTITIES,
				SolversAction.getInstance());
			actionsGroup.put(
				Resources.LOAD_SIMILAR_MICRO_ARCHITECTURES,
				SolversAction.getInstance());
			actionsGroup.put(
				Resources.LIST_SIMILAR_MICRO_ARCHITECTURES,
				SolversAction.getInstance());
			actionsGroup.put(
				Resources.REMOVE_ALL_SIMILAR_MICRO_ARCHITECTURES,
				SolversAction.getInstance());
			actionsGroup.put(
				Resources.MODIFY_PROGRAM_MODEL,
				SolversAction.getInstance());
		}
		else if (strGroup.equals(actionsGroups[6])) { // WINDOW
			actionsGroup
				.put(Resources.MINIMIZE_ALL, WindowAction.getInstance());
			actionsGroup.put(Resources.CASCADE, WindowAction.getInstance());
			actionsGroup.put(
				Resources.TILE_VERTICALLY,
				WindowAction.getInstance());
			actionsGroup.put(
				Resources.TILE_HORIZONTALLY,
				WindowAction.getInstance());
		}
		else if (strGroup.equals(actionsGroups[7])) { // VIEWER_OPTIONS
			actionsGroup.put(Resources.SELECT_ALL, OptionAction.getInstance());
			actionsGroup
				.put(Resources.UNSELECT_ALL, OptionAction.getInstance());
			actionsGroup.put(
				Resources.CREATION_NAMES,
				OptionAction.getInstance());
			actionsGroup.put(
				Resources.CREATION_DISPLAY,
				OptionAction.getInstance());
			actionsGroup.put(Resources.USE_NAMES, OptionAction.getInstance());
			actionsGroup.put(Resources.USE_DISPLAY, OptionAction.getInstance());
			actionsGroup.put(
				Resources.ASSOCIATION_NAMES,
				OptionAction.getInstance());
			actionsGroup.put(
				Resources.ASSOCIATION_DISPLAY,
				OptionAction.getInstance());
			actionsGroup.put(
				Resources.COMPOSITION_NAMES,
				OptionAction.getInstance());
			actionsGroup.put(
				Resources.COMPOSITION_DISPLAY,
				OptionAction.getInstance());
			actionsGroup.put(
				Resources.AGGREGATION_NAMES,
				OptionAction.getInstance());
			actionsGroup.put(
				Resources.AGGREGATION_DISPLAY,
				OptionAction.getInstance());
			actionsGroup.put(
				Resources.CONTAINER_COMPOSITION_NAMES,
				OptionAction.getInstance());
			actionsGroup.put(
				Resources.CONTAINER_COMPOSITION_DISPLAY,
				OptionAction.getInstance());
			actionsGroup.put(
				Resources.CONTAINER_AGGREGATION_NAMES,
				OptionAction.getInstance());
			actionsGroup.put(
				Resources.CONTAINER_AGGREGATION_DISPLAY,
				OptionAction.getInstance());
			actionsGroup.put(
				Resources.HIERARCHY_NAMES,
				OptionAction.getInstance());
			actionsGroup.put(
				Resources.HIERARCHY_DISPLAY,
				OptionAction.getInstance());
			actionsGroup.put(Resources.FIELD_NAMES, OptionAction.getInstance());
			actionsGroup
				.put(Resources.METHOD_NAMES, OptionAction.getInstance());
			actionsGroup.put(
				Resources.FULLY_QUALIFIED_NAMES,
				OptionAction.getInstance());
			actionsGroup.put(
				Resources.GHOST_ENTITIES_DISPLAY,
				OptionAction.getInstance());
		}
		else if (strGroup.equals(actionsGroups[8])) { // HELP
			actionsGroup.put(Resources.USAGE, HelpAction.getInstance());
			actionsGroup.put(Resources.ABOUT, HelpAction.getInstance());
		}
		else if (strGroup.equals(actionsGroups[9])) { // DESIGN DEFECTS
			actionsGroup.put(
				Resources.NEW_RULECARD,
				AntiPatternAction.getInstance());
			actionsGroup.put(
				Resources.DELETE_RULECARD,
				AntiPatternAction.getInstance());
			actionsGroup.put(
				Resources.DETECT_DESIGN_SMELLS,
				AntiPatternAction.getInstance());
		}
		else if (strGroup.equals(actionsGroups[10])) { // EPI
			actionsGroup.put(
				Resources.EPI_FIND_SIMILAR_MICRO_ARCHITECTURE,
				EPIAction.getInstance());
			actionsGroup.put(
				Resources.EPI_SIMILAR_MICRO_ARCHITECTURE_HELP,
				EPIAction.getInstance());
		}
		else if (strGroup.equals(actionsGroups[11])) { // Graph layouts
			actionsGroup.put(
				Resources.INHERITANCE_CLUSTERING_LAYOUT,
				GraphLayoutAction.getInstance());
			actionsGroup.put(
				Resources.INHERITANCE_DEPTH_CLUSTERING_LAYOUT,
				GraphLayoutAction.getInstance());
			actionsGroup.put(
				Resources.SIMPLE_LAYOUT,
				GraphLayoutAction.getInstance());
			actionsGroup.put(
				Resources.SUGIYAMA_LAYOUT,
				GraphLayoutAction.getInstance());
		}

		return actionsGroup;
	}
	private static Map fillAll() {
		final Map root = new HashMap();
		for (int i = 0; i < actionsGroups.length; i++) {
			root.put(actionsGroups[i], fill(actionsGroups[i]));
		}
		return root;
	}
	public static ActionsRepository getInstance() {
		return (ActionsRepository.UniqueInstance == null) ? ActionsRepository.UniqueInstance =
			new ActionsRepository()
				: ActionsRepository.UniqueInstance;
	}

	private Map root;
	private ActionsRepository() {
		this.root = ActionsRepository.fillAll();
	}
	public AbstractAction getAction(
		final String strActionName,
		final String strActionGroupName) {

		final Map actionGroup = (Map) this.root.get(strActionGroupName);
		if (actionGroup == null) {
			ProxyConsole
				.getInstance()
				.errorOutput()
				.print("Unknown action group: ");
			ProxyConsole
				.getInstance()
				.errorOutput()
				.println(strActionGroupName);
			return null;
		}
		else {
			final AbstractAction action =
				(AbstractAction) (((Map) (actionGroup)).get(strActionName));
			if (action == null) {
				ProxyConsole
					.getInstance()
					.errorOutput()
					.print("Unknown action: ");
				ProxyConsole
					.getInstance()
					.errorOutput()
					.print(strActionGroupName);
				ProxyConsole.getInstance().errorOutput().print("::");
				ProxyConsole.getInstance().errorOutput().print(strActionName);
				ProxyConsole
					.getInstance()
					.errorOutput()
					.println(" (in ptidej.viewer.action.ActionsRepository)");
			}
			return action;
		}
	}
}
