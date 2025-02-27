package glass.lattice.visitor.impl;

import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

import glass.ast.IMethod;
import glass.ast.IType;
import glass.lattice.model.ILatticeNode;
import glass.lattice.visitor.AbstractVisitor;
import glass.lattice.visitor.IVisitor;

public class LatticePrettyPrinter extends AbstractVisitor implements IVisitor {
	/**
	 * an interface that indicates how we print the elements of the extent and the elements of the intent
	 * Instantiations of the LatticePrettyPrinter or its subclasses can use different implementations
	 * of this interface
	 * @author Hafedh
	 *
	 */
	public static interface ElementPrinter {
		
		public String printIntentElement(Object intentElement);
		
		public String printExtentElement(Object extentElement);
	}
	
	/**
	 * a default element printer that will print classes and methods nicely
	 */
	public static ElementPrinter DEFAULT_ELEMENT_PRINTER = new ElementPrinter() {
		public String printIntentElement(Object intentElement) {
			return intentElement.toString();
		}
		
		public String printExtentElement(Object extentElement) {
			return extentElement.toString();
		}
	};
	
	public static ElementPrinter JAVA_ELEMENT_PRINTER = new ElementPrinter() {
		public String printIntentElement(Object intentElement) {
			IMethod method = (IMethod)intentElement;
			return method.getSignature();
		}
		
		public String printExtentElement(Object extentElement) {
				return ((IType) extentElement).getFullyQualifiedParameterizedName();
		}
	};
	
	protected ElementPrinter printer;
	
	protected LatticePrettyPrinter() {
		nodeIndents = new HashMap<ILatticeNode,String>();
		ids = new HashMap<ILatticeNode,String>();
	}
	
	public LatticePrettyPrinter(ElementPrinter printer) {
		this();
		this.printer = printer;
	}
	
	protected int globalCounter = 0;
	
	public static LatticePrettyPrinter defaultPrettyPrinter() {
		return new LatticePrettyPrinter(DEFAULT_ELEMENT_PRINTER );
	}
	
	public static LatticePrettyPrinter javaElementsLatticePrettyPrinter() {
		return new LatticePrettyPrinter(JAVA_ELEMENT_PRINTER );
		
	}
	
	/**
	 * used to store the indents to be used for printing the lattice
	 */
	private HashMap<ILatticeNode,String> nodeIndents = null;
	
	/**
	 * stores node ids so that 
	 */
	private HashMap<ILatticeNode,String> ids = null;

	
	public HashMap<ILatticeNode,String> getNodeIndents() {
		return nodeIndents;
	}

	public void setNodeIndents(HashMap<ILatticeNode,String> nodeIndents) {
		this.nodeIndents = nodeIndents;
	}

	public HashMap<ILatticeNode,String> getIds() {
		return ids;
	}

	public void setIds(HashMap<ILatticeNode,String> ids) {
		this.ids = ids;
	}

	/**
	 * the preprocessing of the children of the node simply computes the indent for printing the children
	 */
	public void preprocessChildren(ILatticeNode node){
		String nodeIndent = getNodeIndents().get(node);
		System.out.println(nodeIndent+"ITS CHILDREN:=================");
		String childrenIndent = nodeIndent + "\t->";
			
		for (ILatticeNode child: node.getChildren()){
			getNodeIndents().put(child,childrenIndent);
			
		}
	}
	
	@Override
	public void processNode(ILatticeNode node) {
		// check if the node has an ID
		String nodeId = getIds().get(node);
		if (nodeId == null) {
			/* hiding the word node to display only the id in the graph node*/
			nodeId =/* "NODE_"+ */ ""+globalCounter++;
			//nodeId  = String.valueOf(node.hashCode()); 
			///System.out.println("#############################################      node.hashCode()");
			getIds().put(node, nodeId);
		}
		
		// get node indent
		String nodeIndent = getNodeIndents().get(node);
		if (nodeIndent == null){
			// this means that this node has no parents, since it is in the processing of parents
			// that we compute the children's indents. This means that the current node is the top
			// Thus, we use an empty string as an indent
			nodeIndent = "";
			getNodeIndents().put(node, nodeIndent);
		}
		// print the node
		System.out.println(nodeIndent+nodeId + "["+ printExtent(node)+"," + printIntent(node) +"]");
		
		
		
	}
	
	@Override
	public void processVisitedNode(ILatticeNode node) {
		// since it is visited, it means it has an id
		// just print its ID number
		String nodeId = getIds().get(node);

		// get node indent
		String nodeIndent = getNodeIndents().get(node);
		// print the node
		System.out.println(nodeIndent+nodeId + "[...,...]");
	}
	
	
	/**
	 * returns a string representing an ordered set of string representations of elements of the intent 
	 * @param node
	 * @return
	 */
	protected String printIntent(ILatticeNode node) {
		SortedSet<String> printStrings = new TreeSet<String>();
		for (Object feature: node.getIntent()){
			printStrings.add(printer.printIntentElement(feature));
		}
		return printStrings.toString();
	}
	
	/**
	 * returns a string representing an ordered set of string representations of elements of the extent 
	 * @param node
	 * @return
	 */
	protected String printExtent(ILatticeNode node) {
		SortedSet<String> printStrings = new TreeSet<String>();
		for (Object object: node.getExtent()){
			printStrings.add(printer.printExtentElement(object));
		}
		return printStrings.toString();
	}
	
	
	/**
	 * this method readies the visitor for another pretty print. Typically, another lattice.
	 * If we want to print a lattice before and after an insertion, we should NOT call reset
	 * because we want to keep the same node IDs to follow the results of insertion
	 */
	public void reset() {
		super.reset();
		globalCounter = 0;
		setNodeIndents(new HashMap<ILatticeNode,String>());
		setIds(new HashMap<ILatticeNode,String>());
	}

}
