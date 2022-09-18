/*******************************************************************************
 * Copyright (c) 2008 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Fabio Petrillo for the first draft.
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package wsmapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/*
 * Use https://dreampuf.github.io/GraphvizOnline/ to draw the generated graph!
 */

public class ManifestAnalyser {
	private static final String MANIFEST_FILE = "MANIFEST.MF";
	private static Map<String, Project> Projects =
		new HashMap<String, Project>();

	private static final String WORKSPACE_PATH = "../";

	private static void addGraphNode(
		final Project aProject,
		final StringBuffer aGraph) {
		//graph.append(project.name + "\n");
		for (final Project dep : aProject.getDependencies()) {
			aGraph.append(
				"<" + aProject.getName() + "> -> <" + dep.getName() + ">\n");
		}
	}
	private static Project createProject(final Path aPath) {
		final String projectName = getProjectName(aPath);
		final Project project = new Project(projectName);
		ManifestAnalyser.Projects.put(projectName, project);
		return project;
	}
	private static void generateGraph() {
		final StringBuffer graph =
			new StringBuffer("digraph G { ranksep = 3; nodesep = 1; ");
		ManifestAnalyser.Projects.forEach(
			(key, project) -> ManifestAnalyser.addGraphNode(project, graph));
		graph.append("}");
		System.out.println(graph);
	}
	private static String getProjectName(final Path aPath) {
		try {
			// Yann 2018/03/05: Symbolic name
			// I don't forget that dependencies are in terms of symbolic names,
			// not file-system names or just "name" in the MANIFEST.MF files.
			final Manifest manifest =
				new Manifest(new FileInputStream(aPath.toFile()));
			final Attributes att = manifest.getMainAttributes();

			String projectName;
			// Somehow, this does not work!?
			//	if (att.containsValue("Bundle-SymbolicName")) {
			if (att.getValue("Bundle-SymbolicName") != null) {
				projectName = att.getValue("Bundle-SymbolicName");
				final int indexOfSemicolon = projectName.indexOf(';');
				if (indexOfSemicolon > 0) {
					projectName = projectName.substring(0, indexOfSemicolon);
				}
			}
			else {
				final String[] workspacePathParts =
					ManifestAnalyser.WORKSPACE_PATH.split("(/)|(\\\\)");
				final String[] projectNameParts =
					aPath.toString().split("(/)|(\\\\)");
				projectName = projectNameParts[workspacePathParts.length];
			}
			return projectName;
		}
		catch (final Exception e) {
			e.printStackTrace();
			return "UNKNOWN";
		}
	}
	public static void main(final String[] args) {
		try {
			Files
				.walk(Paths.get(ManifestAnalyser.WORKSPACE_PATH))
				.filter(Files::isRegularFile)
				.filter(
					p -> p.toString().contains(ManifestAnalyser.MANIFEST_FILE))
				.forEach((p) -> ManifestAnalyser.createProject(p));

			Files
				.walk(Paths.get(ManifestAnalyser.WORKSPACE_PATH))
				.filter(Files::isRegularFile)
				.filter(
					p -> p.toString().contains(ManifestAnalyser.MANIFEST_FILE))
				.forEach((p) -> ManifestAnalyser.processManifest(p));

			ManifestAnalyser.generateGraph();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}
	private static void processManifest(final Path aPath) {
		try {
			final String projectName = getProjectName(aPath);
			final Project project = ManifestAnalyser.Projects.get(projectName);

			final Manifest manifest =
				new Manifest(new FileInputStream(aPath.toFile()));
			final Attributes att = manifest.getMainAttributes();

			if (att.getValue("Require-Bundle") != null) {
				final String[] depProjects =
					att.getValue("Require-Bundle").split(",");

				for (String depProjectName : depProjects) {
					depProjectName = depProjectName.split(";")[0];
					if (ManifestAnalyser.Projects.containsKey(depProjectName)) {
						final Project depProject =
							ManifestAnalyser.Projects.get(depProjectName);
						project.addDependency(depProject);
					}
				}
			}
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}
}