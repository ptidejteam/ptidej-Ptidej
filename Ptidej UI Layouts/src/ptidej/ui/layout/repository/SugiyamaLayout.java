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
package ptidej.ui.layout.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import ptidej.ui.IVisibility;
import ptidej.ui.kernel.AbstractInheritance;
import ptidej.ui.kernel.Aggregation;
import ptidej.ui.kernel.Association;
import ptidej.ui.kernel.Composition;
import ptidej.ui.kernel.Constituent;
import ptidej.ui.kernel.ContainerAggregation;
import ptidej.ui.kernel.ContainerComposition;
import ptidej.ui.kernel.Creation;
import ptidej.ui.kernel.Delegation;
import ptidej.ui.kernel.Element;
import ptidej.ui.kernel.Entity;
import ptidej.ui.kernel.Implementation;
import ptidej.ui.kernel.Relationship;
import ptidej.ui.kernel.Specialisation;
import ptidej.ui.kernel.Use;
import ptidej.ui.layout.IUILayout;
import ptidej.ui.layout.repository.sugiyama.DummyNodesBuilder;
import ptidej.ui.layout.repository.sugiyama.HierarchiesBuilder;
import ptidej.ui.layout.repository.sugiyama.SettingValue;
import ptidej.ui.layout.repository.sugiyama.XCoordonateSetter;
import ptidej.ui.layout.repository.sugiyama.graph.DummyNode;
import ptidej.ui.layout.repository.sugiyama.graph.INode;
import ptidej.ui.layout.repository.sugiyama.graph.Node;
import ptidej.ui.layout.repository.sugiyama.graph.builders.EdgeBuilder;
import ptidej.ui.layout.repository.sugiyama.graph.builders.GraphBuilder;
import ptidej.ui.layout.repository.sugiyama.graph.builders.NodeBuilder;
import ptidej.ui.layout.repository.sugiyama.horizentalLayout.HorizentalLayout;
import ptidej.ui.layout.repository.sugiyama.horizentalLayout.PriorityHorizentalLayout;
import ptidej.ui.layout.repository.sugiyama.linesDrawing.LineDrawer;
import ptidej.ui.layout.repository.sugiyama.minimising.BarycentricCrossingMinimizer;

//
/**
 * @Author : Mohamed Kahla 
 * @since  12/05/2006
 * @version 21082006H1519
 * Do all the layout according to the Sugiyama's algorithme
 * and dased on heuristics when minising the number of crossings
 * and when displacing the nodes to get long streht edges as long as possible!
 * So
 * 
 *  This class implements the Sugiyama's algorithme and performs
 *  the drawing of the UML class diagram as a digraph 
 *   by
 *  1- respecting the Hierarchies
 *  2- building nodes and synchronising the relations
 *  3- adding dummy nodes 
 *  4- minimizing the number of crossing ...
 *  5- setting the horizental positions
 *  6- X coordonates setting!
 *  7- minimising the distance between the nodes (as axes!)
 *  8- 2D : show 
 *   a- splitters
 *   b- dumy lists
 *   c- building elements
 *   d- drawwing lines
 *   e- computing the rotation angles
 *   f- computing the new triangle's coordonates
 *   g- drawing all 
 */
public class SugiyamaLayout extends SettingValue implements IUILayout {
	private int nbLevels = 0; // the hierarchy
	private List graphHierarchies = new ArrayList(); // a list of lists
	private Entity[] entities;
	private List graphNode = new ArrayList();
	// 21-07-2006
	private GraphBuilder graphBuilder;
	// all the elements to returns whith the rhight positions 
	private List allElements = new ArrayList();
	private INode[][] matrix = new Node[0][0];

	// Auxiliary classes
	private NodeBuilder nodeBuilder;
	private EdgeBuilder edgeBuilder;

	/**
	 * @author Mohamed Kahla
	 * @since 11/07/2006
	 * @version 21082006H1519
	 * 
	 * default constructor!
	 *
	 */
	public SugiyamaLayout() {
		super();

		this.graphHierarchies = new ArrayList();
		this.nodeBuilder = null;
		this.graphBuilder = null;
		this.edgeBuilder = null;
	}

	/**
	 * @author Mohamed Kahla
	 * @since 26/07/2006
	 * @version 21082006H1519
	 * 
	 * paremeter for Sugiyama's layout!
	 * All this parameters must be positifs or 0
	 * other wise thes default options are set!
	 * 
	 * @param aDebugPrintingLevel from 0 to 10
	 * @param aMaxNumberOfRounds 
	 * @param aMaxNbRoundsWhithoutProgress
	 * @param aMaxNbIterationPhase2
	 * @param aMaxNbIterationPhase1
	 * @param aMaxIterationsHorizentalLayout
	 * @param aHorizentalDistanceEntities
	 * @param aVerticalDistanceEntities
	 * @param aDefaultSplitter from 0 to aVerticalDistanceEntities
	 * @param aDistanceBetweenChannels
	 * @param anArrowForm from .. to ..
	 * @param aZoom 1 or greater!
	 * @param aMaxViewingLevelSelected
	 */
	public SugiyamaLayout(
		int aDebugPrintingLevel,
		int aMaxNumberOfRounds,
		int aMaxNbRoundsWhithoutProgress,
		int aMaxNbIterationPhase2,
		int aMaxNbIterationPhase1,
		int aMaxIterationsHorizentalLayout,
		int aHorizentalDistanceEntities,
		int aVerticalDistanceEntities,
		int aDefaultSplitter,
		int aDistanceBetweenChannels,
		int anArrowForm,
		int aZoom,
		int aMaxViewingLevelSelected) {

		super(
			aDebugPrintingLevel,
			aMaxNumberOfRounds,
			aMaxNbRoundsWhithoutProgress,
			aMaxNbIterationPhase2,
			aMaxNbIterationPhase1,
			aMaxIterationsHorizentalLayout,
			aHorizentalDistanceEntities,
			aVerticalDistanceEntities,
			aDefaultSplitter,
			aDistanceBetweenChannels,
			anArrowForm,
			aZoom,
			aMaxViewingLevelSelected);

		this.graphHierarchies = new ArrayList();
		this.nodeBuilder = null;
		this.graphBuilder = null;
		this.edgeBuilder = null;
	}

	private boolean debugging = false;

	/**
	 * 
	 */
	public boolean getDebugStatus() {
		return this.debugging;

	}

	/**
	 * 
	 * 
	 */
	public void activateDebuging() {
		this.debugging = true;
	}

	/**
	 * 
	 * 
	 */
	public void deActivateDebuging() {
		this.debugging = false;
	}

	private void printDebugMessage(final String message) {
		if (this.getDebugStatus()) {
			System.out.println(message);
		}

	}

	/**
	 * @author Mohamed Kahla
	 * @since  12/05/2006
	 * @version 21082006H1535
	 * 
	 * This method compute a new position and dimention
	 * for the Entities and return them
	 */
	public Constituent[] doLayout(final Entity[] someEntities) {
		this.printDebugMessage("1");

		if (someEntities.length == 0) {
			// if no entity send!
			return someEntities;
		}

		this.entities = someEntities;
		// I have to do this to solve the problem when a new layout is needed 
		// so a new graph model is ceated!

		// 05-06-2006
		// more methods added to be more clear
		this.printDebugMessage("2");
		this.printRelations();

		// 11-07-2006
		// if the ghost visibility have changed we 
		// have to rebuild the hierarchy
		// Yann 2014/04/11: Why?
		// Why not doing that all the time?
		// Yann 2014/04/27: Ah, silly!
		// But fixing other bugs in the graphic framework, I realised
		// that it is *not* the job of the layouter to decide what is
		// it be computed or not, else there would be discrepancies,
		// in particular between the entities positions and the lines
		// (inheritance, relationships...).
		//	if (!this.graphHierarchyBuilded
		//			|| (this.lastGhostVisibility != this.ghostVisibility)) {
		//
		//		// TODO
		//		// added 07/08/2006
		//		this.graphHierarchyBuilded = true;
		//		this.lastGhostVisibility = this.ghostVisibility;

		// 18-05-2006
		final HierarchiesBuilder hierarchiesBuilder =
			HierarchiesBuilder.getInstance();
		hierarchiesBuilder.buildHierarchies(this.entities);
		this.graphHierarchies = hierarchiesBuilder.getGraphHierarchies();
		this.nbLevels = hierarchiesBuilder.getNbLevels();
		//	}

		this.printDebugMessage("3, graphHierarchy.size : "
				+ this.graphHierarchies.size());

		// edges building
		// 25-07-2006
		this.edgeBuilder = new EdgeBuilder();

		// node building
		this.nodeBuilder =
			new NodeBuilder(
				this.entities,
				this.graphHierarchies,
				this.nbLevels,
				this.edgeBuilder);
		this.nodeBuilder.buildNodes();
		this.graphNode = this.nodeBuilder.getNodeCreated();

		this.printDebugMessage("4 , GraphNodes.size() :"
				+ this.graphNode.size());
		this.printNodes();

		// 21-07-2006
		// building graph
		this.graphBuilder = new GraphBuilder(this.graphNode, this.nbLevels);
		this.graphBuilder.buildGraph();
		this.graphBuilder.getGraph();

		this.printDebugMessage("5 : Graph biuld");

		// Dummy nodes Building

		//			EdgeSet edgeSet = this.edgeBuilder.getEdgeSet();
		//			this.dummyBuilder = new DummyBuilder(this.graph, edgeSet);
		//			this.dummyBuilder.buildDummy();

		/////////////////////////////////////////////////////////////////

		final DummyNodesBuilder dummyBuilder =
			new DummyNodesBuilder(this.graphNode);
		dummyBuilder.addDummy(); // dummy nodes are added to graphNode
		this.graphNode = dummyBuilder.getGraphModel();
		////////////////////////////////////////////////////////////////////

		//			this.graphNode = this.graph.getAllNodes();

		this.printDebugMessage("6 , GraphNodes.size() : "
				+ this.graphNode.size());
		this.printNodes();

		// 11-07-2006
		// this is the most time consumming one!
		// BUT if we rebuild the Nodes or the dummy nodes or
		// both of them we must perform again the Crossing minimizing step 

		final BarycentricCrossingMinimizer crossingMin =
			new BarycentricCrossingMinimizer(this.graphNode, this.nbLevels);

		this.printDebugMessage("6.1");

		crossingMin.reduceCrossing();

		this.printDebugMessage("6.2");

		this.matrix = crossingMin.getMatrix();

		this.printDebugMessage("7");
		this.printMatrix();

		// HorizentalLayout hLayout = new QuadraticHorizentalLayout(this.matrix);
		final HorizentalLayout hLayout =
			new PriorityHorizentalLayout(this.matrix);
		hLayout.doHorizentalLayout();

		this.printDebugMessage("8");
		this.printDebugMessage("Matrix.length: " + this.matrix.length);

		// now we have to set the X coordonates according to the relatif positions
		final double[][] relatifPosition = hLayout.getVertexGlobalOrdring();
		final XCoordonateSetter xSetter =
			new XCoordonateSetter(this.matrix, relatifPosition);
		xSetter.setXCoordonate2();

		this.printDebugMessage("9");
		this.printDebugMessage("Matrix.length: " + this.matrix.length);

		final LineDrawer lineDrawer =
			new LineDrawer(
				this.entities,
				this.graphNode,
				this.nbLevels,
				this.matrix);
		lineDrawer.draw();
		this.allElements = lineDrawer.getAllElements();

		this.printDebugMessage("10");

		// All the pattern's constituents (Entities and Elements).
		final Constituent[] temp =
			new Constituent[this.entities.length + this.allElements.size()];
		this.allElements.toArray(temp);
		System.arraycopy(
			this.entities,
			0,
			temp,
			this.allElements.size(),
			this.entities.length);

		// Yann 2013/06/04: Explanations
		// We have a mixed bag of entities and elements,
		// all to be displayed or not... but all with
		// the proper coordinates... or they should!
		return temp;
	}
	/**
	 * @author Mohamed Kahla
	 * @since 14-07-2006
	 * priint the nodes and there relations!
	 *
	 */
	private void printNodes() {

		// if we could show debug messages
		if (getDebugStatus()) {

			System.out.println("Nodes Hierachy !");

			ListIterator itr = this.graphNode.listIterator(0);
			while (itr.hasNext()) {
				Node next = (Node) itr.next();

				System.out.println("Level : " + next.getLevel());

				if (next instanceof DummyNode)
					System.out.println("Dummy");

				else
					System.out.println("Entity Name : "
							+ next.getEntity().getName());

				System.out.println("NbChildre : "
						+ next.getChildren().getNbChildren());
				final INode[] children = next.getTabChildren();
				//			Iterator childItr = children.iterator();
				//			while (childItr.hasNext()) {
				for (int i = 0; i < children.length; i++) {
					final INode child = children[i]; //(Node) childItr.next();
					System.out.println("Level Chlid : " + child.getLevel());
					if (!(child instanceof DummyNode))
						System.out.println("Child Name : "
								+ child.getEntity().getName());
					else
						System.out.println("Dummi Name : ?");
				}

				System.out.println();
			}
		}
	}

	/**
	 * @author Mohamed Kahla
	 * @since 14-07-2006
	 * print the nodes and there relations 
	 * according to the Matrix relation 
	 *
	 */
	private void printMatrix() {

		// if we could show debug messages
		if (getDebugStatus()) {

			System.out.println("Matrix !");

			for (int i = 0; i < this.matrix.length; i++) {
				System.out.println("Level : " + i);
				for (int j = 0; j < this.matrix[i].length; j++) {

					if (this.matrix[i][j] instanceof DummyNode) {
						System.out.println("j : " + j + ", Dummy");
					}
					else
						System.out.println("j : " + j + ", Node : "
								+ this.matrix[i][j].getEntity().getName());

				}
			}

			System.out.println();
		}
	}

	/**
	 * @author Mohamed Kahla
	 * printRelations for the Entities!
	 *
	 */
	private void printRelations() {
		if (this.getDebugStatus()) {
			for (int i = 0; i < this.entities.length; i++) {
				// for the Generalisatin elements
				final AbstractInheritance[] dHierarchies =
					this.entities[i].getHierarchies();

				// for the other elements
				final Element[] dElements = this.entities[i].getElements();

				// the same tab for all the elements!
				final Element[] allElements =
					new Element[dElements.length + dHierarchies.length];

				System
					.arraycopy(dElements, 0, allElements, 0, dElements.length);
				System.arraycopy(
					dHierarchies,
					0,
					allElements,
					dElements.length,
					dHierarchies.length);

				// all the links
				for (int j = 0; j < allElements.length; j++) {
					Element elem = allElements[j];

					// Relationship
					if (elem instanceof Relationship) {
						final Entity origin =
							((Relationship) elem).getOriginEntity();
						final Entity target =
							((Relationship) elem).getTargetEntity();

						// AbstractGeneralisation
						if ((elem instanceof AbstractInheritance)
								&& checkVisibility(
									IVisibility.HIERARCHY_DISPLAY_ELEMENTS,
									origin)) {

							printDebugMessage("AbstractGeneralisation");

							// Implementation
							if (elem instanceof Implementation) {
								printDebugMessage("Implementation");
							}
							// Specialisation
							else if (elem instanceof Specialisation) {
								printDebugMessage("Specialisation");
							}

							print(origin, target);
						}
						// Association
						else if (elem instanceof Association) {
							// Aggregation
							if (elem instanceof Aggregation
									&& checkVisibility(
										IVisibility.AGGREGATION_DISPLAY_ELEMENTS,
										origin)) {
								printDebugMessage("Aggregation");
								print(origin, target);
							}
							else if (elem instanceof ContainerAggregation
									&& checkVisibility(
										IVisibility.CONTAINER_AGGREGATION_DISPLAY_ELEMENTS,
										origin)) {
								printDebugMessage("ContainerAggregation");
								print(origin, target);
							}
							else if (elem instanceof Composition
									&& checkVisibility(
										IVisibility.COMPOSITION_DISPLAY_ELEMENTS,
										origin)) {
								printDebugMessage("Composition");
								print(origin, target);
							}
							else if (elem instanceof ContainerComposition
									&& checkVisibility(
										IVisibility.CONTAINER_COMPOSITION_DISPLAY_ELEMENTS,
										origin)) {
								printDebugMessage("ContainerComposition");
								print(origin, target);
							}
							else if (checkVisibility(
								IVisibility.ASSOCIATION_DISPLAY_ELEMENTS,
								origin)) {
								printDebugMessage("Association");
								print(origin, target);
							}
						}
						// Delegation
						else if (elem instanceof Delegation
								&& checkVisibility(
									IVisibility.DELEGATION_DISPLAY_ELEMENTS,
									origin)) {
							printDebugMessage("Delegation");
							print(origin, target);
						}
						// Use
						else if (elem instanceof Use) {
							// Creation
							if (elem instanceof Creation
									&& checkVisibility(
										IVisibility.CREATION_DISPLAY_ELEMENTS,
										origin)) {
								printDebugMessage("Creation");
								print(origin, target);
							}
							else if (checkVisibility(
								IVisibility.USE_DISPLAY_ELEMENTS,
								origin))
								print(origin, target);
						}
					}
				}
			}
		}
	}

	/**
	 * * @author Mohamed Kahla
	 * find all the visible relation from this entity!
	 * 
	 */
	private boolean checkVisibility(final int aVisibility, final Entity e) {

		boolean visibility = false;

		switch (aVisibility) {
			case IVisibility.HIERARCHY_DISPLAY_ELEMENTS :
				{
					// Abstract Generalisation 
					// Implementation & Specialisation
					int abstractGenerilisationVisibility =
						e.getVisibleElements()
								& IVisibility.HIERARCHY_DISPLAY_ELEMENTS;
					// if not visible!
					if (abstractGenerilisationVisibility == IVisibility.HIERARCHY_DISPLAY_ELEMENTS)
						visibility = true;

					break;
				}
			case IVisibility.AGGREGATION_DISPLAY_ELEMENTS :
				{
					// Aggregation
					int aggregationVisibility =
						e.getVisibleElements()
								& IVisibility.AGGREGATION_DISPLAY_ELEMENTS;
					if (aggregationVisibility == IVisibility.AGGREGATION_DISPLAY_ELEMENTS)
						visibility = true;

					break;
				}
			case IVisibility.ASSOCIATION_DISPLAY_ELEMENTS :
				{
					// Association
					int associationVisibility =
						e.getVisibleElements()
								& IVisibility.ASSOCIATION_DISPLAY_ELEMENTS;
					if (associationVisibility == IVisibility.ASSOCIATION_DISPLAY_ELEMENTS)
						visibility = true;

					break;
				}
			case IVisibility.COMPOSITION_DISPLAY_ELEMENTS :
				{
					// Composition
					int compositionVisibility =
						e.getVisibleElements()
								& IVisibility.COMPOSITION_DISPLAY_ELEMENTS;
					if (compositionVisibility == IVisibility.COMPOSITION_DISPLAY_ELEMENTS)
						visibility = true;

					break;
				}
			case IVisibility.CONTAINER_AGGREGATION_DISPLAY_ELEMENTS :
				{
					// Container Aggregation 
					int containerAggregationVisibility =
						e.getVisibleElements()
								& IVisibility.CONTAINER_AGGREGATION_DISPLAY_ELEMENTS;
					if (containerAggregationVisibility == IVisibility.CONTAINER_AGGREGATION_DISPLAY_ELEMENTS)
						visibility = true;

					break;
				}
			case IVisibility.CONTAINER_COMPOSITION_DISPLAY_ELEMENTS :
				{
					// Container Composition
					int containerCompositionVisibility =
						e.getVisibleElements()
								& IVisibility.CONTAINER_COMPOSITION_DISPLAY_ELEMENTS;
					if (containerCompositionVisibility == IVisibility.CONTAINER_COMPOSITION_DISPLAY_ELEMENTS)
						visibility = true;

					break;
				}
			case IVisibility.CREATION_DISPLAY_ELEMENTS :
				{
					// Creation
					int creationVisibility =
						e.getVisibleElements()
								& IVisibility.CREATION_DISPLAY_ELEMENTS;
					if (creationVisibility == IVisibility.CREATION_DISPLAY_ELEMENTS)
						visibility = true;

					break;
				}
			case IVisibility.DELEGATION_DISPLAY_ELEMENTS :
				{
					// Delegation 
					// TODO 
					// This field don't exist in the swing implementation!visible
					int delegationVisibility =
						e.getVisibleElements()
								& IVisibility.DELEGATION_DISPLAY_ELEMENTS;
					if (delegationVisibility == IVisibility.DELEGATION_DISPLAY_ELEMENTS)
						visibility = true;

					break;
				}
			case IVisibility.USE_DISPLAY_ELEMENTS :
				{
					// Use
					int useVisibility =
						e.getVisibleElements()
								& IVisibility.USE_DISPLAY_ELEMENTS;
					if (useVisibility == IVisibility.USE_DISPLAY_ELEMENTS)
						visibility = true;

					break;
				}

			default :
				break;
		}

		//		System.out.println("visibility: " + visibility);
		return visibility;
	}

	/**
	 *  @author Mohamed Kahla
	 * Prin the origin and target names if the debug messages are
	 * showable!
	 * 
	 */
	private void print(Entity anOrigin, Entity aTarget) {
		// print
		printDebugMessage("origin: " + anOrigin.getName());
		printDebugMessage("target: " + aTarget.getName());
		printDebugMessage("");

	}

	public String getName() {
		return "SugiyamaAlgorithm";
	}
}
