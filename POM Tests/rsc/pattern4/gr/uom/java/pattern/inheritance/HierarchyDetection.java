package gr.uom.java.pattern.inheritance;

import gr.uom.java.bytecode.SystemObject;
import gr.uom.java.bytecode.ClassObject;

import java.util.*;
import javax.swing.tree.*;

public class HierarchyDetection {
	
	private List<Enumeratable> finalHierarchyList;
	
	public HierarchyDetection(SystemObject systemObject) {

        List<Enumeratable> superclassHierarchyList = getSuperclassHierarchyList(systemObject);
        this.finalHierarchyList = getInterfaceHierarchyList(systemObject, superclassHierarchyList);
        getNonInheritingClasses(systemObject, finalHierarchyList);
    }

    private void getNonInheritingClasses(SystemObject systemObject, List<Enumeratable> hierarchyList) {
        NonInheritingClassVector v = new NonInheritingClassVector();
        for(int i=0; i<systemObject.getClassNumber(); i++) {
			ClassObject classObject = systemObject.getClassObject(i);
            if(getHierarchy(hierarchyList,classObject.getName()) == null)
                v.add(new DefaultMutableTreeNode(classObject.getName()));
        }
        hierarchyList.add(v);
    }

    private List<Enumeratable> getSuperclassHierarchyList(SystemObject systemObject) {
        List<Enumeratable> superclassHierarchyList = new ArrayList<Enumeratable>();

        for(int i=0; i<systemObject.getClassNumber(); i++) {
			ClassObject classObject = systemObject.getClassObject(i);
			if(systemObject.getPositionInClassList(classObject.getSuperclass()) != -1) {
                InheritanceHierarchy childHierarchy = getHierarchy(superclassHierarchyList, classObject.getName());
				InheritanceHierarchy parentHierarchy = getHierarchy(superclassHierarchyList, classObject.getSuperclass());
				if(childHierarchy == null && parentHierarchy == null) {
					InheritanceHierarchy ih = new InheritanceHierarchy();
					ih.addChildToParent(classObject.getName(), classObject.getSuperclass());
					superclassHierarchyList.add(ih);
				}
                else if(childHierarchy == null)
                    parentHierarchy.addChildToParent(classObject.getName(), classObject.getSuperclass());
                else if(parentHierarchy == null)
                    childHierarchy.addChildToParent(classObject.getName(), classObject.getSuperclass());
				else if( !childHierarchy.equals(parentHierarchy) ) {
                    parentHierarchy.addChildRootNodeToParent(childHierarchy.getRootNode(),classObject.getSuperclass());
					superclassHierarchyList.remove(childHierarchy);
				}
			}
		}
        return superclassHierarchyList;
    }
    
    private List<Enumeratable> getInterfaceHierarchyList(SystemObject systemObject, List<Enumeratable> superclassHierarchyList) {
        List<Enumeratable> interfaceHierarchyList = new ArrayList<Enumeratable>(superclassHierarchyList);
        
        //for the classes included in superclassHierarchyList
        //create hierarchies with their implemented interfaces
        //if the class has no parent then the tree is MOVED under the implemented interface
        //if the class has parent then the tree is COPIED under the implemented interface
        for(Enumeratable enumeratable : superclassHierarchyList) {
            InheritanceHierarchy ih = (InheritanceHierarchy)enumeratable;
            Enumeration e = ih.getEnumeration();
            while(e.hasMoreElements()) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)e.nextElement();

                ClassObject co = systemObject.getClassObject((String)node.getUserObject());
                ListIterator<String> interfaceIt = co.getInterfaceIterator();
                while(interfaceIt.hasNext()) {
                    String inter = interfaceIt.next();
                    if(systemObject.getPositionInClassList(inter) != -1) {
                        List<Enumeratable> parentHierarchies = getHierarchyList(interfaceHierarchyList, inter);
                        if(parentHierarchies.size() == 0) {
                            InheritanceHierarchy tempIh = new InheritanceHierarchy();
                            if(node.getParent() == null) {
                                tempIh.addChildRootNodeToParent(node,inter);
                                interfaceHierarchyList.add(tempIh);
                                interfaceHierarchyList.remove(ih);
                            }
                            else {
                                tempIh.addChildRootNodeToParent(ih.deepClone(node),inter);
                                interfaceHierarchyList.add(tempIh);
                            }
                        }
                        else {
                            for(Enumeratable parentEnumeratable : parentHierarchies) {
                                InheritanceHierarchy parentHierarchy = (InheritanceHierarchy)parentEnumeratable;
                                if(node.getParent() == null) {
                                    parentHierarchy.addChildRootNodeToParent(node,inter);
                                    interfaceHierarchyList.remove(ih);
                                }
                                else {
                                    parentHierarchy.addChildRootNodeToParent(ih.deepClone(node),inter);
                                }
                            }
                        }
                    }
                }
            }
        }

        //for the classes not included in superclassHierarchyList
        //create hierarchies with their implemented interfaces
        for(int i=0; i<systemObject.getClassNumber(); i++) {
			ClassObject classObject = systemObject.getClassObject(i);
			InheritanceHierarchy childHierarchy = getHierarchy(interfaceHierarchyList,classObject.getName());
            if(childHierarchy == null) {
                ListIterator<String> interfaceIt = classObject.getInterfaceIterator();
                while(interfaceIt.hasNext()) {
                    String inter = interfaceIt.next();
                    if(systemObject.getPositionInClassList(inter) != -1) {
                        InheritanceHierarchy parentHierarchy = getHierarchy(interfaceHierarchyList, inter);
                        if(parentHierarchy == null) {
                            InheritanceHierarchy tempIh = new InheritanceHierarchy();
                            tempIh.addChildToParent(classObject.getName(),inter);
                            interfaceHierarchyList.add(tempIh);
                        }
                        else {
                            parentHierarchy.addChildToParent(classObject.getName(),inter);
                        }
                    }
                }
            }
            /*if(classObject.isInterface() && childHierarchy != null) {
            	ListIterator<String> interfaceIt = classObject.getInterfaceIterator();
                while(interfaceIt.hasNext()) {
                    String inter = interfaceIt.next();
                    if(systemObject.getPositionInClassList(inter) != -1) {
                        InheritanceHierarchy parentHierarchy = getHierarchy(interfaceHierarchyList, inter);
                        if(parentHierarchy != null && !childHierarchy.equals(parentHierarchy)) {
                        	parentHierarchy.addChildRootNodeToParent(childHierarchy.getRootNode(),inter);
        					interfaceHierarchyList.remove(childHierarchy);
                        }
                    }
                }
            }*/
        }
        return interfaceHierarchyList;
    }

    public List<Enumeratable> getHierarchyList() {
		return finalHierarchyList;
	}

	//returns the first hierarchy that contains the node with name nodeName
	private InheritanceHierarchy getHierarchy(List<Enumeratable> hierarchyList, String nodeName) {
		for(Enumeratable enumeratable : hierarchyList) {
			InheritanceHierarchy ih = (InheritanceHierarchy)enumeratable;
			if(ih.getNode(nodeName) != null)
				return ih;
		}
		return null;
	}

    //returns all the hierarchies that contain the node with name nodeName
	private List<Enumeratable> getHierarchyList(List<Enumeratable> hierarchyList, String nodeName) {
		List<Enumeratable> outputList = new ArrayList<Enumeratable>();
        for(Enumeratable enumeratable : hierarchyList) {
			InheritanceHierarchy ih = (InheritanceHierarchy)enumeratable;
			if(ih.getNode(nodeName) != null)
				outputList.add(ih);
		}
		return outputList;
	}
}