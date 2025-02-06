
package ffeatureextractor.commands;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.IPath;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import ca.uqam.latece.aspects.extractor.input.impl.ReverseInheritanceRelationBuilder;
import ca.uqam.latece.aspects.extractor.lattice.LatticeBuilder;
import ca.uqam.latece.aspects.extractor.lattice.graph.model.Node;
import ca.uqam.latece.aspects.extractor.lattice.impl.LatticeBuilderImpl;
import ca.uqam.latece.aspects.extractor.lattice.model.Lattice;
import ca.uqam.latece.aspects.extractor.lattice.model.Relation;
import ca.uqam.latece.aspects.extractor.lattice.visitors.Visitor;
import ca.uqam.latece.aspects.extractor.lattice.visitors.impl.ComplexPurgeExtentsVisitor;
import ca.uqam.latece.aspects.extractor.lattice.visitors.impl.FeatureDetectorVisitor;
import ca.uqam.latece.aspects.extractor.lattice.visitors.impl.LatticePrettyPrinter;
import ca.uqam.latece.aspects.extractor.lattice.visitors.impl.PrintCandidatesVisitor;
import ca.uqam.latece.aspects.extractor.lattice.visitors.impl.SimplePurgeExtentsVisitor;
import ffeatureextractor.viewpart.LatticeGraph;

	public class FeatureExtractionCommand extends AbstractHandler {
		private IWorkbenchWindow window;
		private IProject project;
		private String projectName;
		
	    @SuppressWarnings("unchecked")
	    @Override
	    public Object execute(ExecutionEvent event) throws ExecutionException {
	        ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event)
	                .getActivePage().getSelection();
	        if (selection != null & selection instanceof IStructuredSelection) {
	            IStructuredSelection strucSelection = (IStructuredSelection) selection;
	            for (Iterator<Object> iterator = strucSelection.iterator(); iterator
	                    .hasNext();) {
	                Object element = iterator.next();
	                projectName = element.toString().substring(2);
	            }
	        }
	     // retourne le nom de tous les projects dans le workspace
			IWorkspace workspace = ResourcesPlugin.getWorkspace();

			// 1. prompt user for project name
			//InputDialog projectNameDialog = new InputDialog(window.getShell(), "Project Name", "Enter project name",
			//		"<PROJECT NAME>", null);
			//projectNameDialog.open();
			//projectName = projectNameDialog.getValue();

			// 2. get project with provided name
			project = workspace.getRoot().getProject(projectName);

			if (project == null) {
				MessageDialog.openInformation(window.getShell(), "Feature Extractor",
						"No project with that name. Please try again");
				return null;
			}

			// 3. build relation from project, and print it
			ReverseInheritanceRelationBuilder relationBuilder = new ReverseInheritanceRelationBuilder();
			Relation relation = relationBuilder.buildRelationFrom(project);
			System.out.println("Printing the relation");
			System.out.println(relation.printString());

			// 4. build lattice from relation, and print it
			LatticeBuilder latticeBuilder = new LatticeBuilderImpl();

			Lattice aLattice = latticeBuilder.buildLattice(relation, null);

			System.out.println("Done building lattice!");

			// 5 display lattice (imen)
			// 5.1 pretty print lattice (hafedh)
			LatticePrettyPrinter latticePrinter = LatticePrettyPrinter.javaElementsLatticePrettyPrinter();
			//aLattice.acceptTopVisitor(latticePrinter);

			System.out.println("Done printing lattice!");

			// LatticeGraphGenerator latticeGraphGenerator = new LatticeGraphGenerator();
			// aLattice.acceptTopVisitor(latticeGraphGenerator);

			// emd (imen)

			// 6. now identify candidate features
			// 6.1 First, purge extents of non-minimas, and print new lattice
			boolean basicPurge = false;
			// ask what type of purge
			//InputDialog purgeType = new InputDialog(window.getShell(), "Type of purge", "Select purge type (erase one)",
			//		"SIMPLE COMPLEX", null);
			//purgeType.open();

			//String purge = purgeType.getValue();

			//if (purge.trim().equalsIgnoreCase("COMPLEX"))
			//	basicPurge = false;

			Visitor purgeVisitor = null;
			if (basicPurge) {
				purgeVisitor = new SimplePurgeExtentsVisitor();

			} else {
				System.out.println("Using complex purge");
				purgeVisitor = new ComplexPurgeExtentsVisitor(relationBuilder);
			}
			;
			aLattice.acceptTopVisitor(purgeVisitor);
			//latticePrinter.reset();
			System.out.println("Printing lattice after purging extents");
			aLattice.acceptTopVisitor(latticePrinter);
			System.out.println("Done printing lattice!");

			// 6.2 Second, extract candidate features
			FeatureDetectorVisitor featureDetector = new FeatureDetectorVisitor(relationBuilder);
			aLattice.acceptTopVisitor(featureDetector);

			// 6.3 Third, print candidate feature nodes
			PrintCandidatesVisitor printCandidatesVisitor = new PrintCandidatesVisitor(
					featureDetector.getCandidateFeatureNodes());
			System.out.println("Printing candidate nodes");
			//aLattice.acceptTopVisitor(printCandidatesVisitor);
			System.out.println("Done printing candidate nodes!");

			// donne vrai
			System.out.println(Platform.isRunning());
			// MessageDialog.openInformation(window.getShell(),
			// "Ffeature-extractor", "Hello, Eclipse world");

			// 5,1 building the view plugin and display lattice nodes (imen)
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			IViewPart viewPart = page.findView("ffeatureExtractor.viewpart.LatticeGraph");
			LatticeGraph graphView = (LatticeGraph) viewPart;
			graphView.updateLabel(RemoveNonCandidateNodes(printCandidatesVisitor.getNodes()));

			// graphView.updateLabel(printCandidatesVisitor.getNodes());
	        return null;
	    }
	    
	    private List<Node> RemoveNonCandidateNodes(List<Node> nodes) {
			
			//there are nodes in the lattice that have two copies in the lattice : a copy as candidate and a copy as no candidate
			//this error is found when I displayed the lattice
			//this loop transform no candidates to candidates and assign to them the type of the candidate copy 
			for (int i = nodes.size()-1; i>0; i--) {
				List<Node> children = nodes.get(i).getChildren();
				for (Node child : children) {
					int indx = nodes.indexOf(child);
					
					if (indx>0) {
						child.setTypes(nodes.get(indx).getTypes()); 
					}
				}
			}		
			System.out.println("display nodes");
			for (Node node : nodes) {
				System.out.println("display node: " + node.getName() + "-- " +node.getTypes());	
				List<Node> children = node.getChildren();
				for (Node child : children) {
					System.out.println("	children: " + child.getName() + "-- " +child.getTypes());			
				}
			}		
			//for each mode if one child is non candidate assign his children to his parents 		
			List<Node> nodesToRemove = new ArrayList<Node>();
			for (int i = nodes.size()-1; i>=0; i--) {
				List<Node> children = nodes.get(i).getChildren();
				for (Node child : children) {
					if (child.getTypes().isEmpty() ) {
						List<Node> childrenOfChild = child.getChildren();
						children.addAll(childrenOfChild);			
					}
				}
			}
			
			System.out.println("new display nodes");
			for (Node node : nodes) {
				System.out.println("new display node: " + node.getName() + "-- " +node.getTypes());	
				List<Node> children = node.getChildren();
				for (Node child : children) {
					System.out.println("	new children: " + child.getName() + "-- " +child.getTypes());			
				}
			}	
			//remove no candidate nodes from the list 
			for (Node node : nodes) {
				//System.out.println(" node" + node.getName() + "--" + node.getType());
				if (node.getTypes().isEmpty()  && node.getName() != null) {
					//System.out.pr0intln("removed node" + node.getName() + "--" + node.getType());
					nodesToRemove.add(node);
				}
			}		
			nodes.removeAll(nodesToRemove);
			// remove children of the non candidate nodes 		
			for (Node node : nodes) {
				List<Node> children = node.getChildren();
				List<Node> removedChildren = new ArrayList<Node>();
				for (Node child : children) {
					//System.out.println(" child: " + child.getName() + "-- " +child.getType()+ "parent" + node.getName());			
					if (child.getTypes().isEmpty() ) {
						System.out.println("remove child: " + child.getName() + "-- " +child.getTypes()+ "parent" + node.getName());
						removedChildren.add(child);
					}
				}children.removeAll(removedChildren);
			}				
			
			FileOutputStream fos;
			ObjectOutputStream oos;
			try {
				fos = new FileOutputStream(project.getLocation().toString()+"/"+project.getName()+"_"+"lattice.ltc");
				oos = new ObjectOutputStream(fos);
				oos.writeObject(nodes);
				oos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}
			
			
			
			
			return nodes;
		}

	}
