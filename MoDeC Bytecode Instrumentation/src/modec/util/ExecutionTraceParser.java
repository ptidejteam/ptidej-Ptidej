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
package modec.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

import metamodel.scenariodiagram.Argument;
import metamodel.scenariodiagram.Class;
import metamodel.scenariodiagram.Component;
import metamodel.scenariodiagram.Create;
import metamodel.scenariodiagram.Instance;
import metamodel.scenariodiagram.Loop;
import metamodel.scenariodiagram.Message;
import metamodel.scenariodiagram.Operation;
import metamodel.scenariodiagram.ScenarioDiagram;
import util.io.ProxyDisk;

/**
 * @author ngjanice
 */
public class ExecutionTraceParser {
	private static final String METHOD_ENTRY = "METHOD entry";
	private static final String METHOD_EXIT = "METHOD exit";
	private static final String LOOP_ENTRY = "LOOP entry";
	private static final String LOOP_EXIT = "LOOP exit";
	private static final String CONSTRUCTOR_ENTRY = "CONSTRUCTOR entry";
	private static final String CONSTRUCTOR_EXIT = "CONSTRUCTOR exit";
	private static final String ALL_LOOPS_EXIT = "ALL LOOPs exit";

	private ScenarioDiagram sd;
	private Stack pileOfComponents;
	private Component currentComponent;
	private int level = 0;

	public ScenarioDiagram getScenarioDiagram() {
		return this.sd;
	}

	public ExecutionTraceParser(String filename) {
		this.sd = new ScenarioDiagram();
		this.currentComponent = null;
		this.pileOfComponents = new Stack();
		this.createScenarioDiagram(filename);
		//		System.out.println(
		//			"Is stack empty ?? : "
		//				+ pileOfComponents.isEmpty()
		//				+ " size "
		//				+ pileOfComponents.size()
		//				+ "\t"
		//				+ pileOfComponents);
	}

	public ScenarioDiagram createScenarioDiagram(String filename) {
		BufferedReader file = null;
		String aLine = "";
		Component componentToAdd = null;
		int index = -1;
		//int count = 1;

		try {
			file = new BufferedReader(new FileReader(filename));
			aLine = file.readLine();

			while (aLine != null) {
				aLine = aLine.trim();
				//System.out.println(aLine);
				if (aLine.startsWith(METHOD_ENTRY)
						|| aLine.startsWith(LOOP_ENTRY)
						|| aLine.startsWith(CONSTRUCTOR_ENTRY)) {

					int indexOpen = aLine.indexOf("("), indexClose =
						aLine.indexOf(")");
					List arguments = new ArrayList();
					String theArguments = "";
					String signature = "";

					if (indexOpen > -1 && indexClose > -1) {
						theArguments =
							aLine.substring(indexOpen + 1, indexClose);
						StringTokenizer st =
							new StringTokenizer(theArguments, ",");
						while (st.hasMoreTokens()) {
							String type_value = (String) st.nextToken().trim();
							int index_space = type_value.indexOf(" ");

							if (index_space == -1) {
								String value =
									type_value
										.substring(type_value.lastIndexOf("."))
										.toLowerCase();
								arguments.add(new Argument(type_value, value));
							}
							else
								arguments.add(new Argument(type_value
									.substring(0, index_space), type_value
									.substring(index_space + 1)));

						}
						int indexName = aLine.lastIndexOf("entry");
						signature = aLine.substring(indexName + 5, indexOpen);
					}

					if (aLine.startsWith(METHOD_ENTRY)) {
						index++;
						//	startIndex =
						//		aLine.indexOf(METHOD_ENTRY)
						//			+ METHOD_ENTRY.length()
						//			+ 1;
						StringTokenizer st = new StringTokenizer(aLine, " ");
						while (st.hasMoreTokens()
								&& !st.nextToken().equals("CALLEE"))
							continue;

						String ofClass = st.nextToken();
						int objectID = Integer.parseInt(st.nextToken());
						Instance callee = new Instance(objectID);
						callee.addOfClass(new Class(ofClass));

						componentToAdd =
							new Operation(
								signature,
								arguments,
								new Class(""),
								callee);
						componentToAdd.setIndex(index);
					}
					else if (aLine.startsWith(LOOP_ENTRY)) {
						this.level++;
						index++;
						//	startIndex =
						//		aLine.indexOf(LOOP_ENTRY)
						//			+ LOOP_ENTRY.length()
						//			+ 1;
						componentToAdd =
							new Loop(Integer.parseInt(aLine.substring(
								aLine.indexOf("#") + 1).trim()));
						componentToAdd.setIndex(index);
						index = -1;
					}
					else if (aLine.startsWith(CONSTRUCTOR_ENTRY)) {
						index++;
						//	startIndex =
						//		aLine.indexOf(CONSTRUCTOR_ENTRY)
						//			+ CONSTRUCTOR_ENTRY.length()
						//			+ 1;
						StringTokenizer st = new StringTokenizer(aLine, " ");
						while (st.hasMoreTokens()
								&& !st.nextToken().equals("CALLEE")) {
							continue;
						}

						String ofClass = st.nextToken();
						int objectID = Integer.parseInt(st.nextToken());
						Instance callee = new Instance(objectID);
						callee.addOfClass(new Class(ofClass));

						componentToAdd =
							new Create(
								signature,
								arguments,
								new Class(""),
								callee);
						componentToAdd.setIndex(index);
					}

					this.pileOfComponents.push(componentToAdd);
					if (componentToAdd instanceof Loop)
						updateCurrentComponent();
				}
				else if (aLine.startsWith(METHOD_EXIT)
						|| aLine.startsWith(LOOP_EXIT)
						|| aLine.startsWith(CONSTRUCTOR_EXIT)) {
					int startIndex;
					int indexOpen = aLine.indexOf("(")
					//,indexClose = aLine.lastIndexOf(")")
					;

					if (aLine.startsWith(METHOD_EXIT))
						startIndex =
							aLine.indexOf(METHOD_EXIT) + METHOD_EXIT.length()
									+ 1;
					else if (aLine.startsWith(LOOP_EXIT)) {
						startIndex =
							aLine.indexOf(LOOP_EXIT) + LOOP_EXIT.length() + 1;
						this.level--;
						if (this.getLastPushedLoop() != null)
							index = this.getLastPushedLoop().getIndex();
					}
					else if (aLine.startsWith(CONSTRUCTOR_EXIT))
						startIndex =
							aLine.indexOf(CONSTRUCTOR_EXIT)
									+ CONSTRUCTOR_EXIT.length() + 1;
					else
						startIndex = -1;

					int indexOfCallee = aLine.indexOf("CALLEE");
					String ofClass = "";
					int objectID = -1;
					if (indexOfCallee > -1) {
						//System.out.println(aLine);
						StringTokenizer st = new StringTokenizer(aLine, " ");
						while (st.hasMoreTokens()
								&& !st.nextToken().equals("CALLEE")) {
							continue;
						}
						ofClass = st.nextToken();
						objectID = Integer.parseInt(st.nextToken().trim());
					}
					if (startIndex == -1
							|| indexOpen == -1
							|| (!this.pileOfComponents.isEmpty()
									&& this.pileOfComponents.peek() instanceof Message
									&& aLine
										.substring(startIndex, indexOpen)
										.equals(
											((Message) this.pileOfComponents
												.peek()).getSignature().trim())
									&& indexOfCallee > -1
									&& ((Message) this.pileOfComponents.peek())
										.getDestinationClassifier() instanceof Instance
									&& ((Instance) ((Message) this.pileOfComponents
										.peek()).getDestinationClassifier())
										.getObjectID() == objectID && ((Instance) ((Message) this.pileOfComponents
								.peek()).getDestinationClassifier())
								.containsOfClass(new Class(ofClass)))
							|| (!this.pileOfComponents.isEmpty()
									&& this.pileOfComponents.peek() instanceof Loop && aLine
								.substring(aLine.indexOf("#") + 1)
								.trim()
								.equals(
									new String(""
											+ ((Loop) this.pileOfComponents
												.peek()).getID())))) {

						Component c = (Component) this.pileOfComponents.pop();
						c.setLevel(this.level);

						Message lastPushedMessage = this.getLastPushedMessage();
						if (lastPushedMessage != null && c instanceof Message) {
							((Message) c).setSourceMessage(lastPushedMessage);
							((Message) c).setSourceClassifier(lastPushedMessage
								.getDestinationClassifier());
						}

						if (c instanceof Loop)
							updateCurrentComponent();
						if (this.currentComponent == null
								|| !(this.currentComponent instanceof Loop)) {

							if (this.sd.componentSize() > c.getIndex())
								this.sd.addComponent(c.getIndex(), c);
							else
								this.sd.addComponent(c);
						}
						else {
							if (((Loop) this.currentComponent)
								.getOperand()
								.componentsSize() > c.getIndex())
								((Loop) this.currentComponent)
									.addComponentToOperand(c.getIndex(), c);
							else
								((Loop) this.currentComponent)
									.addComponentToOperand(c);
						}
					}
				}
				else if (aLine.startsWith(ALL_LOOPS_EXIT)) {
					this.level--;
					while (!this.pileOfComponents.isEmpty()
							&& this.pileOfComponents.peek() instanceof Loop) {
						if (this.getLastPushedLoop() != null)
							index = this.getLastPushedLoop().getIndex();
						Component c = (Component) this.pileOfComponents.pop();
						c.setLevel(this.level);

						Message lastPushedMessage = this.getLastPushedMessage();
						if (lastPushedMessage != null && c instanceof Message) {
							((Message) c).setSourceMessage(lastPushedMessage);
							((Message) c).setSourceClassifier(lastPushedMessage
								.getDestinationClassifier());
						}

						if (c instanceof Loop)
							updateCurrentComponent();
						/*if(currentComponent == null || !(currentComponent instanceof Loop))
							sd.addComponent(c.getIndex(), c);												
						else 
							((Loop) currentComponent).addComponentToOperand(c.getIndex(), c);	*/
						if (this.currentComponent == null
								|| !(this.currentComponent instanceof Loop)) {
							if (this.sd.componentSize() > c.getIndex())
								this.sd.addComponent(c.getIndex(), c);
							else
								this.sd.addComponent(c);
						}
						else {
							if (((Loop) this.currentComponent)
								.getOperand()
								.componentsSize() > c.getIndex())
								((Loop) this.currentComponent)
									.addComponentToOperand(c.getIndex(), c);
							else
								((Loop) this.currentComponent)
									.addComponentToOperand(c);
						}
					}

				}
				aLine = file.readLine();
				//System.out.println(count++);
				//System.out.println(aLine);
			}

			file.close();
			return this.sd;
		}
		catch (FileNotFoundException e) {
			System.out.println("The file [" + filename
					+ "] could not be found. Please try again.");
		}
		catch (IOException e) {
			System.out.println("Error while opening file " + filename);
		}
		return null;
	}

	private void updateCurrentComponent() {
		if (!this.pileOfComponents.isEmpty()) {
			Iterator it = this.pileOfComponents.iterator();
			boolean found = false;
			while (it.hasNext()) {
				Component c = (Component) it.next();
				if (c instanceof Loop) {
					this.currentComponent = c;
					found = true;
				}
			}
			if (!found)
				this.currentComponent = null;
		}
		else
			this.currentComponent = null;
	}

	private Loop getLastPushedLoop() {
		if (!this.pileOfComponents.isEmpty()) {
			//for (int i = pileOfComponents.size() - 1; i >= 0; i++) {
			for (int i = this.pileOfComponents.size() - 1; i >= 0; i--) {
				Component c = (Component) this.pileOfComponents.get(i);
				if (c instanceof Loop)
					return (Loop) c;
			}
		}
		return null;
	}

	private Message getLastPushedMessage() {
		if (!this.pileOfComponents.isEmpty()) {
			for (int i = this.pileOfComponents.size() - 1; i >= 0; i--) {
				Component c = (Component) this.pileOfComponents.get(i);
				if (c instanceof Message)
					return (Message) c;
			}
		}
		return null;
	}

	public void printScenarioDiagramToFile(String filename) {
		try {
			final Writer fstream =
				ProxyDisk.getInstance().fileAbsoluteOutput(filename, true);
			final BufferedWriter out = new BufferedWriter(fstream);
			out.write(this.sd.toString());
			out.close();
		}
		catch (final Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	public static void main(final String[] args) {
		final ExecutionTraceParser etp = new ExecutionTraceParser(args[0]);
		//System.out.println(etp.getScenarioDiagram());
		final int index = args[0].indexOf(".trace");
		final String filename = args[0].substring(0, index) + ".sd";
		etp.printScenarioDiagramToFile(filename);
	}

}
