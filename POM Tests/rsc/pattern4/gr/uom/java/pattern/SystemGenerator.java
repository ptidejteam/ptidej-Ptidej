package gr.uom.java.pattern;

import gr.uom.java.bytecode.*;
import gr.uom.java.pattern.inheritance.Enumeratable;
import gr.uom.java.pattern.inheritance.HierarchyDetection;
import gr.uom.java.pattern.inheritance.InheritanceHierarchy;

import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;

public class SystemGenerator {
    private MatrixContainer matrixContainer;
    private SystemObject systemObject;
    private List<Enumeratable> hierarchyList;
    private ClusterSet clusterSet;

    public SystemGenerator(SystemObject systemObject) {
        this.systemObject = systemObject;
        this.matrixContainer = new MatrixContainer();
        HierarchyDetection hd = new HierarchyDetection(this.systemObject);
        this.hierarchyList = hd.getHierarchyList();

        this.matrixContainer.setClassNameList(this.systemObject.getClassNames());
        this.matrixContainer.setGeneralizationMatrix(getGeneralizationMatrix());
        this.matrixContainer.setAssociationMatrix(getAssociationMatrix());
        associationWithInheritanceMatrix();
        this.matrixContainer.setAbstractMatrix(getAbstractMatrix());
        this.matrixContainer.setAbstractMethodInvocationMatrix(getAbstractMethodInvocationMatrix());
        doubleDispatchMatrix();
        similarAbstractMethodInvocationMatrix();
        similarMethodInvocationFromSiblingSubclassMatrix();
        fieldOfSuperClassTypeIsInitializedWithSiblingClassTypeMatrix();
        invokedMethodInInheritedMethodMatrix();
        this.matrixContainer.setMethodInvocationsMatrix(getMethodInvocationsMatrix());
        iterativeNonSimilarAbstractMethodInvocationMatrix();
        iterativeSimilarAbstractMethodInvocationMatrix();
        cloneInvocationMatrix();
        singletonMatrix();
        templateMethodMatrix();
        factoryMethodMatrix();

        this.clusterSet = generateClusterSet();
    }

    public List<Enumeratable> getHierarchyList() {
        return hierarchyList;
    }

    public MatrixContainer getMatrixContainer() {
        return this.matrixContainer;
    }

    public ClusterSet getClusterSet() {
        return this.clusterSet;
    }

    private boolean belongInSameHierarchy(String className1, String className2) {
        for(Enumeratable ih : hierarchyList) {
        	if(ih instanceof InheritanceHierarchy) {
        		if(ih.getNode(className1) != null && ih.getNode(className2) != null)
        			return true;
        	}
        }
        return false;
    }

    private boolean childParentRelationship(String childClass, String parentClass) {
    	for(Enumeratable ih : hierarchyList) {
    		if(ih instanceof InheritanceHierarchy) {
	    		DefaultMutableTreeNode childNode = ih.getNode(childClass);
	    		DefaultMutableTreeNode parentNode = ih.getNode(parentClass);
	    		if(childNode != null && parentNode != null) {
	    			DefaultMutableTreeNode currentParent = (DefaultMutableTreeNode)childNode.getParent();
	    			while(currentParent != null) {
	    				if(currentParent.getUserObject().equals(parentNode.getUserObject()))
	    					return true;
	    				currentParent = (DefaultMutableTreeNode)currentParent.getParent();
	    			}
	    		}
    		}
    	}
    	return false;
    }

    private double[][] getGeneralizationMatrix() {
        ListIterator<ClassObject> it = systemObject.getClassListIterator();
        double[][] m = new double[systemObject.getClassNumber()][systemObject.getClassNumber()];
        int counter = 0;

        while(it.hasNext()) {
            ClassObject co = it.next();

            ListIterator<String> superclassIt = co.getSuperclassIterator();
            while(superclassIt.hasNext()) {
                String superclass = superclassIt.next();
                int pos = systemObject.getPositionInClassList(superclass);
                if(pos != -1) {
                    m[counter][pos] = 1;
                }
            }
            counter++;
        }
        return m;
    }

    private double[][] getAssociationMatrix() {
        ListIterator<ClassObject> it = systemObject.getClassListIterator();
        double[][] m = new double[systemObject.getClassNumber()][systemObject.getClassNumber()];
        int counter = 0;

        while(it.hasNext()) {
            ClassObject co = it.next();

            ListIterator<FieldObject> fieldIt = co.getFieldIterator();
            while(fieldIt.hasNext()) {
                FieldObject fo = fieldIt.next();
                TypeObject type = fo.getType();
                String classType;
                if(type.getClassType().contains("[]")) {
                	//Type[] is an array of associations
                	classType = type.getClassType().substring(0, type.getClassType().length()-2);
                }
                else {
                	classType = type.getClassType();
                }
                int pos = systemObject.getPositionInClassList(classType);
                if(pos != -1 && counter != pos) {
                    m[counter][pos] = 1;
                }
            }
            counter++;
        }
        return m;
    }

    private void associationWithInheritanceMatrix() {
    	ListIterator<ClassObject> it = systemObject.getClassListIterator();
        double[][] m = new double[systemObject.getClassNumber()][systemObject.getClassNumber()];
        BehavioralData behavioralData = new BehavioralData();
        int counter = 0;

        while(it.hasNext()) {
            ClassObject co = it.next();

            ListIterator<FieldObject> fieldIt = co.getFieldIterator();
            while(fieldIt.hasNext()) {
                FieldObject fo = fieldIt.next();
                if(!fo.isStatic()) {
	                TypeObject type = fo.getType();
	                String classType = type.getClassType();
	                int pos = systemObject.getPositionInClassList(classType);
	                if(pos != -1 && counter != pos && childParentRelationship(co.getName(), classType)) {
	                	m[counter][pos] = 1;
	                	behavioralData.addField(counter, pos, fo);
	                }
                }
            }
            counter++;
        }
        matrixContainer.setAssociationWithInheritanceMatrix(m);
        matrixContainer.setAssociationWithInheritanceBehavioralData(behavioralData);
    }

    private double[][] getAbstractMatrix() {
        ListIterator<ClassObject> it = systemObject.getClassListIterator();
        double[][] m = new double[systemObject.getClassNumber()][systemObject.getClassNumber()];
        int counter = 0;

        while(it.hasNext()) {
            ClassObject co = it.next();
            if(co.isAbstract() || co.isInterface()) {
                m[counter][counter] = 1;
            }
            counter++;
        }
        return m;
    }

    private void singletonMatrix() {
        ListIterator<ClassObject> it = systemObject.getClassListIterator();
        double[][] m = new double[systemObject.getClassNumber()][systemObject.getClassNumber()];
        BehavioralData behavioralData = new BehavioralData();
        int counter = 0;

        while(it.hasNext()) {
            ClassObject co = it.next();

            ListIterator<FieldObject> fieldIt = co.getFieldIterator();
            while(fieldIt.hasNext()) {
                FieldObject fo = fieldIt.next();
                TypeObject type = fo.getType();
                if(type.getClassType().equals(co.getName()) && fo.isStatic()) {
                    m[counter][counter] = 1;
                    behavioralData.addField(counter, counter, fo);
                }
            }
            counter++;
        }
        matrixContainer.setSingletonMatrix(m);
        matrixContainer.setSingletonBehavioralData(behavioralData);
    }

    private void templateMethodMatrix() {
        ListIterator<ClassObject> it = systemObject.getClassListIterator();
        double[][] m = new double[systemObject.getClassNumber()][systemObject.getClassNumber()];
        BehavioralData behavioralData = new BehavioralData();
        int counter = 0;

        while(it.hasNext()) {
            ClassObject co = it.next();

            ListIterator<MethodObject> methodIt = co.getMethodIterator();
            while(methodIt.hasNext()) {
                MethodObject mo = methodIt.next();

                ListIterator<MethodInvocationObject> mii = mo.getMethodInvocationIterator();
                while(mii.hasNext()) {
                    MethodInvocationObject mio = mii.next();

                    if(mio.getOriginClassName().equals(co.getName())) {
                        ClassObject originco = systemObject.getClassObject(mio.getOriginClassName());
                        if(originco != null) {
                            MethodObject temp = originco.getMethod(mio.getSignature());
                            if(temp != null && temp.isAbstract()) {
                                m[counter][counter] = 1;
                                behavioralData.addMethod(counter, counter, mo);
                            }
                        }
                    }
                }
            }
            counter++;
        }
        matrixContainer.setTemplateMethodMatrix(m);
        matrixContainer.setTemplateMethodBehavioralData(behavioralData);
    }

    private void factoryMethodMatrix() {
        ListIterator<ClassObject> it = systemObject.getClassListIterator();
        double[][] m = new double[systemObject.getClassNumber()][systemObject.getClassNumber()];
        BehavioralData behavioralData = new BehavioralData();
        //int counter = 0;

        while(it.hasNext()) {
            ClassObject co = it.next();

            ListIterator<MethodObject> methodIt = co.getMethodIterator();
            while(methodIt.hasNext()) {
                MethodObject mo = methodIt.next();

                ListIterator<String> oii = mo.getObjectInstantiationIterator();
                while(oii.hasNext()) {
                    String instantiation = oii.next();
                    if(systemObject.getPositionInClassList(instantiation) != -1 && belongInSameHierarchy(mo.getReturnType().getClassType(),instantiation)) {
                        ListIterator<String> superIt = co.getSuperclassIterator();
                        while(superIt.hasNext()) {
                            String superName = superIt.next();
                            int superpos = systemObject.getPositionInClassList(superName);
                            if(superpos != -1) {
                                ListIterator<MethodObject> superMethodIt = systemObject.getClassObject(superpos).getMethodIterator();
                                while(superMethodIt.hasNext()) {
                                    MethodObject supermo = superMethodIt.next();
                                    if(supermo.isAbstract() && supermo.equals(mo)) {
                                        m[superpos][superpos] = 1;
                                        behavioralData.addMethod(superpos, superpos, supermo);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //counter++;
        }
        matrixContainer.setFactoryMethodMatrix(m);
        matrixContainer.setFactoryMethodBehavioralData(behavioralData);
    }

    private double[][] getAbstractMethodInvocationMatrix() {
        ListIterator<ClassObject> it = systemObject.getClassListIterator();
        double[][] m = new double[systemObject.getClassNumber()][systemObject.getClassNumber()];
        int counter = 0;

        while(it.hasNext()) {
            ClassObject co = it.next();

            ListIterator<MethodObject> methodIt = co.getMethodIterator();
            while(methodIt.hasNext()) {
                MethodObject mo = methodIt.next();

                ListIterator<MethodInvocationObject> mii = mo.getMethodInvocationIterator();
                while(mii.hasNext()) {
                    MethodInvocationObject mio = mii.next();
                    int pos = systemObject.getPositionInClassList(mio.getOriginClassName());
                    if(pos != -1 && !belongInSameHierarchy(co.getName(),mio.getOriginClassName())) {
                        MethodObject temp = systemObject.getClassObject(pos).getMethod(mio.getSignature());
                        if(temp != null && temp.isAbstract()) {
                            m[counter][pos] = 1;
                            //break;
                        }
                    }
                }
            }
            counter++;
        }
        return m;
    }

    private void doubleDispatchMatrix() {
        ListIterator<ClassObject> it = systemObject.getClassListIterator();
        double[][] m = new double[systemObject.getClassNumber()][systemObject.getClassNumber()];
        BehavioralData behavioralData = new BehavioralData();
        int counter = 0;

        while(it.hasNext()) {
            ClassObject co = it.next();
            ListIterator<MethodObject> methodIt = co.getMethodIterator();
            while(methodIt.hasNext()) {
                MethodObject mo = methodIt.next();
                ListIterator<TypeObject> pi = mo.getParameterListIterator();
                while(pi.hasNext()) {
                    TypeObject p = pi.next();
                    String parameterType = p.getClassType();
                    int pos = systemObject.getPositionInClassList(parameterType);
                    if(pos != -1) {
                        ListIterator<MethodInvocationObject> mii = mo.getMethodInvocationIterator();
                        while(mii.hasNext()) {
                            MethodInvocationObject mio = mii.next();
                            if(mio.getOriginClassName().equals(parameterType) && mio.hasParameterType(co)) {
                                MethodObject temp = systemObject.getClassObject(pos).getMethod(mio.getSignature());
                                if(temp != null && temp.isAbstract() && temp.getName().startsWith("visit")) {
                                    m[pos][counter] = 1;
                                    behavioralData.addMethod(pos, counter, mo);
                                }
                            }
                        }
                    }
                }
            }
            counter++;
        }
        matrixContainer.setDoubleDispatchMatrix(m);
        matrixContainer.setDoubleDispatchBehavioralData(behavioralData);
    }

    private void similarAbstractMethodInvocationMatrix() {
        ListIterator<ClassObject> it = systemObject.getClassListIterator();
        double[][] m = new double[systemObject.getClassNumber()][systemObject.getClassNumber()];
        BehavioralData behavioralData = new BehavioralData();
        int counter = 0;

        while(it.hasNext()) {
            ClassObject co = it.next();

            ListIterator<MethodObject> methodIt = co.getMethodIterator();
            while(methodIt.hasNext()) {
                MethodObject mo = methodIt.next();

                ListIterator<MethodInvocationObject> mii = mo.getMethodInvocationIterator();
                while(mii.hasNext()) {
                    MethodInvocationObject mio = mii.next();

                    if( mio.getMethodName().equals(mo.getName()) && !mio.getOriginClassName().equals(co.getName()) ) {
                        int pos = systemObject.getPositionInClassList(mio.getOriginClassName());
                        if(pos != -1) {
                            MethodObject temp = systemObject.getClassObject(pos).getMethod(mio.getSignature());
                            if(temp != null && temp.equals(mo) && temp.isAbstract() && childParentRelationship(co.getName(), mio.getOriginClassName())) {
                                m[counter][pos] = 1;
                                behavioralData.addMethod(counter, pos, mo);
                            }
                        }
                    }
                }
            }
            counter++;
        }
        matrixContainer.setSimilarAbstractMethodInvocationMatrix(m);
        matrixContainer.setSimilarAbstractMethodInvocationBehavioralData(behavioralData);
    }

    private void similarMethodInvocationFromSiblingSubclassMatrix() {
    	ListIterator<ClassObject> it = systemObject.getClassListIterator();
    	double[][] m = new double[systemObject.getClassNumber()][systemObject.getClassNumber()];
    	BehavioralData behavioralData = new BehavioralData();
    	int counter = 0;
    	
    	while(it.hasNext()) {
    		ClassObject co = it.next();
    		
    		ListIterator<MethodObject> methodIt = co.getMethodIterator();
    		while(methodIt.hasNext()) {
    			MethodObject mo = methodIt.next();
    			
    			ListIterator<MethodInvocationObject> mii = mo.getMethodInvocationIterator();
    			while(mii.hasNext()) {
    				MethodInvocationObject mio = mii.next();
    				
    				if( mio.getMethodName().equals(mo.getName()) &&
    						mio.getSignature().getReturnType().equals(mo.getSignature().getReturnType()) &&
    						mio.getSignature().getParameterList().equals(mo.getSignature().getParameterList()) &&
    						!mio.getOriginClassName().equals(co.getName()) ) {
    					int pos = systemObject.getPositionInClassList(mio.getOriginClassName());
    					if(pos != -1) {
    						MethodObject temp = systemObject.getClassObject(pos).getMethod(mio.getSignature());
    						if(temp != null && temp.equals(mo) &&
    								!childParentRelationship(co.getName(), mio.getOriginClassName()) &&
    								commonSuperclassDeclaringAbstractMethod(co.getName(), mio.getOriginClassName(), mio)) {
    							m[counter][pos] = 1;
    							behavioralData.addMethod(counter, pos, mo);
    						}
    					}
    				}
    			}
    		}
    		counter++;
    	}
    	matrixContainer.setSimilarMethodInvocationFromSiblingSubclassMatrix(m);
    	matrixContainer.setSimilarMethodInvocationFromSiblingSubclassBehavioralData(behavioralData);
    }
    
    private boolean commonSuperclassDeclaringAbstractMethod(String className1, String className2, MethodInvocationObject mio) {
    	for(Enumeratable ih : hierarchyList) {
        	if(ih instanceof InheritanceHierarchy) {
        		DefaultMutableTreeNode node1 = ih.getNode(className1);
        		DefaultMutableTreeNode node2 = ih.getNode(className2);
        		if(node1 != null && node2 != null) {
        			int level1 = node1.getLevel();
        			int level2 = node2.getLevel();
        			DefaultMutableTreeNode minLevelNode = null;
        			if(level1 <= level2)
        				minLevelNode = node1;
        			else
        				minLevelNode = node2;
        			DefaultMutableTreeNode currentParent = (DefaultMutableTreeNode)minLevelNode.getParent();
	    			while(currentParent != null) {
	    				String currentParentClassName = (String)currentParent.getUserObject();
	    				ClassObject parentClassObject = systemObject.getClassObject(currentParentClassName);
	    				ListIterator<MethodObject> methodIt = parentClassObject.getMethodIterator();
	    	    		while(methodIt.hasNext()) {
	    	    			MethodObject mo = methodIt.next();
	    	    			if( mo.isAbstract() && mio.getMethodName().equals(mo.getName()) &&
	        						mio.getSignature().getReturnType().equals(mo.getSignature().getReturnType()) &&
	        						mio.getSignature().getParameterList().equals(mo.getSignature().getParameterList()) )
	    	    				return true;
	    	    		}
	    	    		if(parentClassObject.isInterface()) {
	    	    			ListIterator<String> interfaceIt = parentClassObject.getInterfaceIterator();
	    	                while(interfaceIt.hasNext()) {
	    	                    String inter = interfaceIt.next();
	    	                    ClassObject interfaceClassObject = systemObject.getClassObject(inter);
	    	                    if(interfaceClassObject != null) {
	    	                    	ListIterator<MethodObject> interfaceMethodIt = interfaceClassObject.getMethodIterator();
	    		    	    		while(interfaceMethodIt.hasNext()) {
	    		    	    			MethodObject mo = interfaceMethodIt.next();
	    		    	    			if( mo.isAbstract() && mio.getMethodName().equals(mo.getName()) &&
	    		        						mio.getSignature().getReturnType().equals(mo.getSignature().getReturnType()) &&
	    		        						mio.getSignature().getParameterList().equals(mo.getSignature().getParameterList()) )
	    		    	    				return true;
	    		    	    		}
	    	                    }
	    	                }
	    	    		}
	    				currentParent = (DefaultMutableTreeNode)currentParent.getParent();
	    			}
        		}
        	}
    	}
    	return false;
    }

    private void fieldOfSuperClassTypeIsInitializedWithSiblingClassTypeMatrix() {
    	ListIterator<ClassObject> it = systemObject.getClassListIterator();
        double[][] m = new double[systemObject.getClassNumber()][systemObject.getClassNumber()];
        BehavioralData behavioralData = new BehavioralData();
        int counter = 0;

        while(it.hasNext()) {
            ClassObject co = it.next();
            ListIterator<FieldObject> fieldIterator = co.getFieldIterator();
            while(fieldIterator.hasNext()) {
            	FieldObject fo = fieldIterator.next();
            	TypeObject type = fo.getType();
            	if(co.extendsClassType(type.getClassType())) {
            		ListIterator<ConstructorObject> constructorIterator = co.getConstructorIterator();
            		while(constructorIterator.hasNext()) {
            			ConstructorObject constructor = constructorIterator.next();
            			if(constructor.containsFieldInstruction(fo)) {
            				ListIterator<String> objectInstantiationIterator = constructor.getObjectInstantiationIterator();
            				while(objectInstantiationIterator.hasNext()) {
            					String objectInstantiation = objectInstantiationIterator.next();
            					int pos = systemObject.getPositionInClassList(objectInstantiation);
            					if(pos != -1 && belongInSameHierarchy(objectInstantiation, type.getClassType()) &&
            							childParentRelationship(objectInstantiation, type.getClassType())) {
            						m[counter][pos] = 1;
            						behavioralData.addField(counter, pos, fo);
            					}
            				}
            			}
            			ListIterator<MethodInvocationObject> methodInvocationIterator = constructor.getMethodInvocationIterator();
            			while(methodInvocationIterator.hasNext()) {
            				MethodInvocationObject methodInvocation = methodInvocationIterator.next();
            				if(methodInvocation.getOriginClassName().equals(co.getName())) {
            					MethodObject invokedMethod = co.getMethod(methodInvocation.getSignature());
            					if(invokedMethod!= null && invokedMethod.containsFieldInstruction(fo)) {
            						ListIterator<String> objectInstantiationIterator = invokedMethod.getObjectInstantiationIterator();
            						while(objectInstantiationIterator.hasNext()) {
                    					String objectInstantiation = objectInstantiationIterator.next();
                    					int pos = systemObject.getPositionInClassList(objectInstantiation);
                    					if(pos != -1 && belongInSameHierarchy(objectInstantiation, type.getClassType()) &&
                    							childParentRelationship(objectInstantiation, type.getClassType())) {
                    						m[counter][pos] = 1;
                    						behavioralData.addField(counter, pos, fo);
                    					}
                    				}
            					}
            				}
            			}
            		}
            	}
            }
            
            counter++;
        }
        matrixContainer.setFieldOfSuperClassTypeIsInitializedWithSiblingClassTypeMatrix(m);
        matrixContainer.setFieldOfSuperClassTypeIsInitializedWithSiblingClassTypeBehavioralData(behavioralData);
    }

    private void invokedMethodInInheritedMethodMatrix() {
        ListIterator<ClassObject> it = systemObject.getClassListIterator();
        double[][] m = new double[systemObject.getClassNumber()][systemObject.getClassNumber()];
        BehavioralData behavioralData = new BehavioralData();
        int counter = 0;

        while(it.hasNext()) {
            ClassObject co = it.next();

            ListIterator<String> superclassIt = co.getSuperclassIterator();
            while(superclassIt.hasNext()) {
                String superclass = superclassIt.next();
                int pos = systemObject.getPositionInClassList(superclass);
                if(pos != -1) {
                    ListIterator<MethodObject> methodIt = co.getMethodIterator();
                    while(methodIt.hasNext()) {
                        MethodObject mo = methodIt.next();

                        ListIterator<MethodObject> interfaceMethods = systemObject.getClassObject(pos).getMethodIterator();
                        while(interfaceMethods.hasNext()) {
                            MethodObject interfaceMethod = interfaceMethods.next();
                            if(mo.equals(interfaceMethod) && interfaceMethod.isAbstract()) {
                                ListIterator<MethodInvocationObject> mii = mo.getMethodInvocationIterator();
                                while(mii.hasNext()) {
                                    MethodInvocationObject mio = mii.next();
                                    int pos2 = systemObject.getPositionInClassList(mio.getOriginClassName());
                                    if(pos2 != -1 && !co.getName().equals(mio.getOriginClassName()) &&
                                        co.hasFieldType(mio.getOriginClassName()) &&
                                        !superclass.equals(mio.getOriginClassName()) &&
                                        !belongInSameHierarchy(co.getName(),mio.getOriginClassName())) {
                                            m[counter][pos2] = 1;
                                            behavioralData.addMethod(counter, pos2, mo);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            counter++;
        }
        matrixContainer.setInvokedMethodInInheritedMethodMatrix(m);
        matrixContainer.setInvokedMethodInInheritedMethodBehavioralData(behavioralData);
    }

    private double[][] getMethodInvocationsMatrix() {
        ListIterator<ClassObject> it = systemObject.getClassListIterator();
        double[][] m = new double[systemObject.getClassNumber()][systemObject.getClassNumber()];
        int counter = 0;

        while(it.hasNext()) {
            ClassObject co = it.next();
            List<MethodInvocationObject> discreteInvocations = new ArrayList<MethodInvocationObject>();

            ListIterator<MethodObject> methodIt = co.getMethodIterator();
            while(methodIt.hasNext()) {
                MethodObject mo = methodIt.next();

                ListIterator<MethodInvocationObject> mii = mo.getMethodInvocationIterator();
                while(mii.hasNext()) {
                    MethodInvocationObject mio = mii.next();
                    if(!discreteInvocations.contains(mio)) {
                        discreteInvocations.add(mio);
                        int pos = systemObject.getPositionInClassList(mio.getOriginClassName());
                        if(pos != -1 && pos!=counter) {
                            //(i,j) increment
                            m[counter][pos] = m[counter][pos] + 1.0;

                            //(j,i) increment
                            m[pos][counter] = m[pos][counter] + 1.0;
                        }
                    }
                }
            }

            ListIterator<ConstructorObject> constructorIt = co.getConstructorIterator();
            while(constructorIt.hasNext()) {
                ConstructorObject coo = constructorIt.next();

                ListIterator<MethodInvocationObject> mii = coo.getMethodInvocationIterator();
                while(mii.hasNext()) {
                    MethodInvocationObject mio = mii.next();
                    if(!discreteInvocations.contains(mio)) {
                        discreteInvocations.add(mio);
                        int pos = systemObject.getPositionInClassList(mio.getOriginClassName());
                        if(pos != -1 && pos!=counter) {
                            //(i,j) increment
                            m[counter][pos] = m[counter][pos] + 1.0;

                            //(j,i) increment
                            m[pos][counter] = m[pos][counter] + 1.0;
                        }
                    }
                }
            }
            counter++;
        }
        return m;
    }

    private void iterativeNonSimilarAbstractMethodInvocationMatrix() {
        ListIterator<ClassObject> it = systemObject.getClassListIterator();
        double[][] m = new double[systemObject.getClassNumber()][systemObject.getClassNumber()];
        BehavioralData behavioralData = new BehavioralData();
        int counter = 0;

        while(it.hasNext()) {
            ClassObject co = it.next();

            ListIterator<MethodObject> methodIt = co.getMethodIterator();
            while(methodIt.hasNext()) {
                MethodObject mo = methodIt.next();
                if(!mo.getAccess().equals(Access.PRIVATE)) {
	                ListIterator<FieldInstructionObject> fii = mo.getFieldInstructionIterator();
	                while(fii.hasNext()) {
	                    FieldInstructionObject fio = fii.next();
	                    if(fio.getClassType().equals("java.util.Vector") || fio.getClassType().equals("java.util.ArrayList") || fio.getClassType().equals("java.util.List")) {
	                        ListIterator<MethodInvocationObject> mii = mo.getMethodInvocationIterator();
	                        while(mii.hasNext()) {
	                            MethodInvocationObject mio = mii.next();
	                            if(mio.getOriginClassName().equals("java.util.Vector") || mio.getOriginClassName().equals("java.util.ArrayList") || mio.getOriginClassName().equals("java.util.List")) {
	                                if(mio.getMethodName().equals("addElement") || mio.getMethodName().equals("add")) {
	                                    ListIterator<TypeObject> paramIt = mo.getParameterListIterator();
	                                    while(paramIt.hasNext()) {
	                                        TypeObject param = paramIt.next();
	                                        int pos = systemObject.getPositionInClassList(param.getClassType());
	                                        if(pos != -1) {
	                                        	Map<MethodObject, List<MethodInvocationObject>> iterativeMethodInvocationMap = 
	                                        		co.iterativeMethodInvocations(param.getClassType());
	                                        	for(MethodObject method : iterativeMethodInvocationMap.keySet()) {
	                                        		if(!mo.equals(method)) {
	                                        			List<MethodInvocationObject> iterativeMethodInvocations = iterativeMethodInvocationMap.get(method);
	                                        			for(MethodInvocationObject iterativeMethodInvocation : iterativeMethodInvocations) {
	                                        				MethodObject temp = systemObject.getClassObject(pos).getMethod(iterativeMethodInvocation.getSignature());
	                                        				if(temp != null && temp.isAbstract()) {
	                                        					if(!childParentRelationship(co.getName(), iterativeMethodInvocation.getOriginClassName()) &&
	                                        							containsDetachMethod(co, mo, method, fio, param)) {
	                                        						m[counter][pos] = 1;
	                                        						behavioralData.addMethod(counter, pos, method);
	                                        					}
	                                        				}
	                                        			}
	                                        		}
	                                        	}
	                                        }
	                                    }
	                                }
	                            }
	                        }
	                    }
	                }
                }
            }
            ListIterator<FieldObject> fieldIt = co.getFieldIterator();
            while(fieldIt.hasNext()) {
            	FieldObject fo = fieldIt.next();
            	TypeObject type = fo.getType();
            	if(type.getClassType().contains("[]")) {
            		String classType = type.getClassType().substring(0, type.getClassType().length()-2);
            		ListIterator<MethodObject> methodIt2 = co.getMethodIterator();
                    while(methodIt2.hasNext()) {
                        MethodObject mo = methodIt2.next();
                        if(!mo.getAccess().equals(Access.PRIVATE)) {
        	                ListIterator<FieldInstructionObject> fii = mo.getFieldInstructionIterator();
        	                while(fii.hasNext()) {
        	                    FieldInstructionObject fio = fii.next();
        	                    if(fo.equals(fio)) {
        	                    	ListIterator<TypeObject> paramIt = mo.getParameterListIterator();
                                    while(paramIt.hasNext()) {
                                        TypeObject param = paramIt.next();
                                        if(param.getClassType().equals(classType)) {
                                        	int pos = systemObject.getPositionInClassList(classType);
                                    		if(pos != -1) {
                                    			Map<MethodObject, List<MethodInvocationObject>> iterativeMethodInvocationMap = 
                                            		co.iterativeMethodInvocations(classType);
                                            	for(MethodObject method : iterativeMethodInvocationMap.keySet()) {
                                            		if(!mo.equals(method)) {
                                            			if(containsFieldInstruction(method, fio)) {
                                            				List<MethodInvocationObject> iterativeMethodInvocations = iterativeMethodInvocationMap.get(method);
                                            				for(MethodInvocationObject iterativeMethodInvocation : iterativeMethodInvocations) {
                                            					MethodObject temp = systemObject.getClassObject(pos).getMethod(iterativeMethodInvocation.getSignature());
                                            					if(temp != null && temp.isAbstract()) {
                                            						if(!childParentRelationship(co.getName(), iterativeMethodInvocation.getOriginClassName())) {
                                            							m[counter][pos] = 1;
                                            							behavioralData.addMethod(counter, pos, method);
                                            						}
                                            					}
                                            				}
                                            			}
                                            		}
                                            	}
                                    		}
                                        }
                                    }
        	                    }
        	                }
                        }
                    }
            	}	
            }
            counter++;
        }
        matrixContainer.setIterativeNonSimilarAbstractMethodInvocationMatrix(m);
        matrixContainer.setIterativeNonSimilarAbstractMethodInvocationBehavioralData(behavioralData);
    }

    private boolean containsDetachMethod(ClassObject co, MethodObject attachMethod, MethodObject notifyMethod, FieldInstructionObject container, TypeObject parameterElement) {
    	ListIterator<MethodObject> methodIt = co.getMethodIterator();
        while(methodIt.hasNext()) {
            MethodObject mo = methodIt.next();
            if(!mo.getAccess().equals(Access.PRIVATE) && !mo.equals(attachMethod) && !mo.equals(notifyMethod)) {
            	if(containsFieldInstruction(mo, container)) {
            		ListIterator<MethodInvocationObject> mii = mo.getMethodInvocationIterator();
            		while(mii.hasNext()) {
            			MethodInvocationObject mio = mii.next();
            			if(mio.getOriginClassName().equals("java.util.Vector") || mio.getOriginClassName().equals("java.util.ArrayList") || mio.getOriginClassName().equals("java.util.List")) {
            				if(mio.getMethodName().equals("remove") || mio.getMethodName().equals("removeElement")) {
            					ListIterator<TypeObject> paramIt = mo.getParameterListIterator();
            					while(paramIt.hasNext()) {
            						TypeObject param = paramIt.next();
            						if(param.getClassType().equals(parameterElement.getClassType()))
            							return true;
            					}
            				}
            				else if(mio.getMethodName().equals("clear") || mio.getMethodName().equals("removeAllElements")) {
            					return true;
            				}
            			}
            		}
            	}
            }
        }
        return false;
    }

    private boolean containsFieldInstruction(MethodObject method, FieldInstructionObject fieldInstruction) {
    	ListIterator<FieldInstructionObject> fii = method.getFieldInstructionIterator();
        while(fii.hasNext()) {
            FieldInstructionObject fio = fii.next();
            if(fio.equals(fieldInstruction))
            	return true;
        }
        return false;
    }

    private void iterativeSimilarAbstractMethodInvocationMatrix() {
        ListIterator<ClassObject> it = systemObject.getClassListIterator();
        double[][] m = new double[systemObject.getClassNumber()][systemObject.getClassNumber()];
        BehavioralData behavioralData = new BehavioralData();
        int counter = 0;

        while(it.hasNext()) {
            ClassObject co = it.next();

            ListIterator<MethodObject> methodIt = co.getMethodIterator();
            while(methodIt.hasNext()) {
                MethodObject mo = methodIt.next();
                if(!mo.getAccess().equals(Access.PRIVATE)) {
	                ListIterator<FieldInstructionObject> fii = mo.getFieldInstructionIterator();
	                while(fii.hasNext()) {
	                    FieldInstructionObject fio = fii.next();
	                    if(fio.getClassType().equals("java.util.Vector") || fio.getClassType().equals("java.util.ArrayList") || fio.getClassType().equals("java.util.List")) {
	                        ListIterator<MethodInvocationObject> mii = mo.getMethodInvocationIterator();
	                        while(mii.hasNext()) {
	                            MethodInvocationObject mio = mii.next();
	                            if(mio.getOriginClassName().equals("java.util.Vector") || mio.getOriginClassName().equals("java.util.ArrayList") || mio.getOriginClassName().equals("java.util.List")) {
	                                if(mio.getMethodName().equals("addElement") || mio.getMethodName().equals("add")) {
	                                    ListIterator<TypeObject> paramIt = mo.getParameterListIterator();
	                                    while(paramIt.hasNext()) {
	                                        TypeObject param = paramIt.next();
	                                        int pos = systemObject.getPositionInClassList(param.getClassType());
	                                        if(pos != -1) {
	                                        	Map<MethodObject, List<MethodInvocationObject>> iterativeMethodInvocationMap = 
	                                        		co.iterativeMethodInvocations(param.getClassType());
	                                        	for(MethodObject method : iterativeMethodInvocationMap.keySet()) {
	                                        		if(!mo.equals(method)) {
		                                        		List<MethodInvocationObject> iterativeMethodInvocations = iterativeMethodInvocationMap.get(method);
		                                        		for(MethodInvocationObject iterativeMethodInvocation : iterativeMethodInvocations) {
		                                        			MethodObject temp = systemObject.getClassObject(pos).getMethod(iterativeMethodInvocation.getSignature());
		                                        			if(temp != null && temp.equals(method) && temp.isAbstract() &&
		                                        					childParentRelationship(co.getName(), iterativeMethodInvocation.getOriginClassName())) {
		                                        				m[counter][pos] = 1;
		                                        				behavioralData.addMethod(counter, pos, method);
		                                        			}
		                                        		}
	                                        		}
	                                        	}
	                                        }
	                                    }
	                                }
	                            }
	                        }
	                    }
	                }
                }
            }
            ListIterator<FieldObject> fieldIt = co.getFieldIterator();
            while(fieldIt.hasNext()) {
            	FieldObject fo = fieldIt.next();
            	TypeObject type = fo.getType();
            	if(type.getClassType().contains("[]")) {
            		String classType = type.getClassType().substring(0, type.getClassType().length()-2);
            		ListIterator<MethodObject> methodIt2 = co.getMethodIterator();
                    while(methodIt2.hasNext()) {
                        MethodObject mo = methodIt2.next();
                        if(!mo.getAccess().equals(Access.PRIVATE)) {
        	                ListIterator<FieldInstructionObject> fii = mo.getFieldInstructionIterator();
        	                while(fii.hasNext()) {
        	                    FieldInstructionObject fio = fii.next();
        	                    if(fo.equals(fio)) {
        	                    	ListIterator<TypeObject> paramIt = mo.getParameterListIterator();
                                    while(paramIt.hasNext()) {
                                        TypeObject param = paramIt.next();
                                        if(param.getClassType().equals(classType)) {
                                        	int pos = systemObject.getPositionInClassList(classType);
                                    		if(pos != -1) {
                                    			Map<MethodObject, List<MethodInvocationObject>> iterativeMethodInvocationMap = 
                                            		co.iterativeMethodInvocations(classType);
                                            	for(MethodObject method : iterativeMethodInvocationMap.keySet()) {
                                            		if(!mo.equals(method)) {
	                                                	List<MethodInvocationObject> iterativeMethodInvocations = iterativeMethodInvocationMap.get(method);
	                                                	for(MethodInvocationObject iterativeMethodInvocation : iterativeMethodInvocations) {
	                                                		MethodObject temp = systemObject.getClassObject(pos).getMethod(iterativeMethodInvocation.getSignature());
	                                                		if(temp != null && temp.equals(method) && temp.isAbstract() &&
	                                                				childParentRelationship(co.getName(), iterativeMethodInvocation.getOriginClassName())) {
	                                                			m[counter][pos] = 1;
	                                                			behavioralData.addMethod(counter, pos, method);
	                                                		}
	                                                	}
                                            		}
                                            	}
                                    		}
                                        }
                                    }
        	                    }
        	                }
                        }
                    }
            	}
            }
            counter++;
        }
        matrixContainer.setIterativeSimilarAbstractMethodInvocationMatrix(m);
        matrixContainer.setIterativeSimilarAbstractMethodInvocationBehavioralData(behavioralData);
    }

    private void cloneInvocationMatrix() {
        ListIterator<ClassObject> it = systemObject.getClassListIterator();
        double[][] m = new double[systemObject.getClassNumber()][systemObject.getClassNumber()];
        BehavioralData behavioralData = new BehavioralData();
        int counter = 0;

        while(it.hasNext()) {
            ClassObject co = it.next();

            ListIterator<MethodObject> methodIt = co.getMethodIterator();
            while(methodIt.hasNext()) {
                MethodObject mo = methodIt.next();

                ListIterator<MethodInvocationObject> mii = mo.getMethodInvocationIterator();
                while(mii.hasNext()) {
                    MethodInvocationObject mio = mii.next();
                    if(mio.getMethodName().equals("clone")) {
                        int pos = systemObject.getPositionInClassList(mio.getOriginClassName());
                        if(pos != -1) {
                            m[counter][pos] = 1;
                            behavioralData.addMethod(counter, pos, mo);
                        }
                    }
                }
            }

            ListIterator<ConstructorObject> constructorIt = co.getConstructorIterator();
            while(constructorIt.hasNext()) {
                ConstructorObject coo = constructorIt.next();

                ListIterator<MethodInvocationObject> mii = coo.getMethodInvocationIterator();
                while(mii.hasNext()) {
                    MethodInvocationObject mio = mii.next();
                    if(mio.getMethodName().equals("clone")) {
                        int pos = systemObject.getPositionInClassList(mio.getOriginClassName());
                        if(pos != -1)
                            m[counter][pos] = 1;
                    }
                }
            }
            counter++;
        }
        matrixContainer.setCloneInvocationMatrix(m);
        matrixContainer.setCloneInvocationBehavioralData(behavioralData);
    }

    //returns a MatrixContainer object for the Hierarchies that contains the hierarchyList
    public MatrixContainer getHierarchiesMatrixContainer(List<Enumeratable> hierarchyList) {
        //list that contains the classnames of all the hierarchies in the hierarchyList
        List<String> hierarchiesClassNameList = new ArrayList<String>();
        MatrixContainer hierarchiesMatrixContainer = new MatrixContainer();

        for(Enumeratable ih : hierarchyList) {
            Enumeration e = ih.getEnumeration();
            while(e.hasMoreElements()) {
                String s = (String)((DefaultMutableTreeNode)e.nextElement()).getUserObject();
                //does not permit duplicate class names
                if(!hierarchiesClassNameList.contains(s))
                    hierarchiesClassNameList.add(s);
            }
        }

        hierarchiesMatrixContainer.setClassNameList(hierarchiesClassNameList);
        Object[] generalizationOutput = generateHierarchiesMatrix(hierarchiesClassNameList, getMatrixContainer().getGeneralizationMatrix(), null);
        hierarchiesMatrixContainer.setGeneralizationMatrix((double[][])generalizationOutput[0]);

        Object[] associationOutput = generateHierarchiesMatrix(hierarchiesClassNameList, getMatrixContainer().getAssociationMatrix(), null);
        hierarchiesMatrixContainer.setAssociationMatrix((double[][])associationOutput[0]);

        Object[] associationWithInheritanceOutput = generateHierarchiesMatrix(hierarchiesClassNameList,
        		getMatrixContainer().getAssociationWithInheritanceMatrix(),
        		getMatrixContainer().getAssociationWithInheritanceBehavioralData());
        hierarchiesMatrixContainer.setAssociationWithInheritanceMatrix((double[][])associationWithInheritanceOutput[0]);
        hierarchiesMatrixContainer.setAssociationWithInheritanceBehavioralData((BehavioralData)associationWithInheritanceOutput[1]);
        
        Object[] abstractOutput = generateHierarchiesMatrix(hierarchiesClassNameList, getMatrixContainer().getAbstractMatrix(), null);
        hierarchiesMatrixContainer.setAbstractMatrix((double[][])abstractOutput[0]);

        Object[] abstractMethodInvocationOutput = generateHierarchiesMatrix(hierarchiesClassNameList,
        		getMatrixContainer().getAbstractMethodInvocationMatrix(),
        		null);
        hierarchiesMatrixContainer.setAbstractMethodInvocationMatrix((double[][])abstractMethodInvocationOutput[0]);

        Object[] similarAbstractMethodInvocationOutput = generateHierarchiesMatrix(hierarchiesClassNameList,
        		getMatrixContainer().getSimilarAbstractMethodInvocationMatrix(),
        		getMatrixContainer().getSimilarAbstractMethodInvocationBehavioralData());
        hierarchiesMatrixContainer.setSimilarAbstractMethodInvocationMatrix((double[][])similarAbstractMethodInvocationOutput[0]);
        hierarchiesMatrixContainer.setSimilarAbstractMethodInvocationBehavioralData((BehavioralData)similarAbstractMethodInvocationOutput[1]);

        Object[] similarMethodInvocationFromSiblingSubclassOutput = generateHierarchiesMatrix(hierarchiesClassNameList,
        		getMatrixContainer().getSimilarMethodInvocationFromSiblingSubclassMatrix(),
        		getMatrixContainer().getSimilarMethodInvocationFromSiblingSubclassBehavioralData());
        hierarchiesMatrixContainer.setSimilarMethodInvocationFromSiblingSubclassMatrix((double[][])similarMethodInvocationFromSiblingSubclassOutput[0]);
        hierarchiesMatrixContainer.setSimilarMethodInvocationFromSiblingSubclassBehavioralData((BehavioralData)similarMethodInvocationFromSiblingSubclassOutput[1]);
        
        Object[] fieldOfSuperClassTypeIsInitializedWithSiblingClassTypeOutput = generateHierarchiesMatrix(hierarchiesClassNameList,
        		getMatrixContainer().getFieldOfSuperClassTypeIsInitializedWithSiblingClassTypeMatrix(),
        		getMatrixContainer().getFieldOfSuperClassTypeIsInitializedWithSiblingClassTypeBehavioralData());
        hierarchiesMatrixContainer.setFieldOfSuperClassTypeIsInitializedWithSiblingClassTypeMatrix((double[][])fieldOfSuperClassTypeIsInitializedWithSiblingClassTypeOutput[0]);
        hierarchiesMatrixContainer.setFieldOfSuperClassTypeIsInitializedWithSiblingClassTypeBehavioralData((BehavioralData)fieldOfSuperClassTypeIsInitializedWithSiblingClassTypeOutput[1]);
        
        Object[] doubleDispatchOutput = generateHierarchiesMatrix(hierarchiesClassNameList,
        		getMatrixContainer().getDoubleDispatchMatrix(),
        		getMatrixContainer().getDoubleDispatchBehavioralData());
        hierarchiesMatrixContainer.setDoubleDispatchMatrix((double[][])doubleDispatchOutput[0]);
        hierarchiesMatrixContainer.setDoubleDispatchBehavioralData((BehavioralData)doubleDispatchOutput[1]);

        Object[] invokedMethodInInheritedMethodOutput = generateHierarchiesMatrix(hierarchiesClassNameList,
        		getMatrixContainer().getInvokedMethodInInheritedMethodMatrix(),
        		getMatrixContainer().getInvokedMethodInInheritedMethodBehavioralData());
        hierarchiesMatrixContainer.setInvokedMethodInInheritedMethodMatrix((double[][])invokedMethodInInheritedMethodOutput[0]);
        hierarchiesMatrixContainer.setInvokedMethodInInheritedMethodBehavioralData((BehavioralData)invokedMethodInInheritedMethodOutput[1]);
        
        Object[] iterativeNonSimilarAbstractMethodInvocationOutput = generateHierarchiesMatrix(hierarchiesClassNameList,
        		getMatrixContainer().getIterativeNonSimilarAbstractMethodInvocationMatrix(),
        		getMatrixContainer().getIterativeNonSimilarAbstractMethodInvocationBehavioralData());
        hierarchiesMatrixContainer.setIterativeNonSimilarAbstractMethodInvocationMatrix((double[][])iterativeNonSimilarAbstractMethodInvocationOutput[0]);
        hierarchiesMatrixContainer.setIterativeNonSimilarAbstractMethodInvocationBehavioralData((BehavioralData)iterativeNonSimilarAbstractMethodInvocationOutput[1]);
        
        Object[] iterativeSimilarAbstractMethodInvocationOutput = generateHierarchiesMatrix(hierarchiesClassNameList,
        		getMatrixContainer().getIterativeSimilarAbstractMethodInvocationMatrix(),
        		getMatrixContainer().getIterativeSimilarAbstractMethodInvocationBehavioralData());
        hierarchiesMatrixContainer.setIterativeSimilarAbstractMethodInvocationMatrix((double[][])iterativeSimilarAbstractMethodInvocationOutput[0]);
        hierarchiesMatrixContainer.setIterativeSimilarAbstractMethodInvocationBehavioralData((BehavioralData)iterativeSimilarAbstractMethodInvocationOutput[1]);

        Object[] cloneInvocationOutput = generateHierarchiesMatrix(hierarchiesClassNameList,
        		getMatrixContainer().getCloneInvocationMatrix(),
        		getMatrixContainer().getCloneInvocationBehavioralData());
        hierarchiesMatrixContainer.setCloneInvocationMatrix((double[][])cloneInvocationOutput[0]);
        hierarchiesMatrixContainer.setCloneInvocationBehavioralData((BehavioralData)cloneInvocationOutput[1]);

        return hierarchiesMatrixContainer;
    }

    private Object[] generateHierarchiesMatrix(List<String> hierarchiesClassNameList, double[][] systemMatrix, BehavioralData systemBehavioralData) {

        double[][] hierarchiesMatrix = new double[hierarchiesClassNameList.size()][hierarchiesClassNameList.size()];
        BehavioralData hierarchiesBehavioralData = new BehavioralData();

        for(int i=0; i<hierarchiesClassNameList.size(); i++) {
            String className1 = hierarchiesClassNameList.get(i);
            int systemI = systemObject.getPositionInClassList(className1);
            for(int j=0; j<hierarchiesClassNameList.size(); j++) {
                String className2 = hierarchiesClassNameList.get(j);
                int systemJ = systemObject.getPositionInClassList(className2);
                hierarchiesMatrix[i][j] = systemMatrix[systemI][systemJ];
                if(systemBehavioralData != null) {
                	Set<FieldObject> fields = systemBehavioralData.getFields(systemI, systemJ);
                	if(fields != null)
                		hierarchiesBehavioralData.addFields(i, j, fields);
                	Set<MethodObject> methods = systemBehavioralData.getMethods(systemI, systemJ);
                	if(methods != null)
                		hierarchiesBehavioralData.addMethods(i, j, methods);
                }
            }
        }

        return new Object[] {hierarchiesMatrix, hierarchiesBehavioralData};
    }

    private ClusterSet generateClusterSet() {
        ClusterSet clusterSet = new ClusterSet();
        double[][] systemAdjacencyMatrix = getMatrixContainer().getMethodInvocationsMatrix();

        for(int i=0; i<hierarchyList.size(); i++) {
            Enumeratable ih1 = hierarchyList.get(i);
            Enumeratable ih2 = null;
            for(int j=i+1; j<hierarchyList.size(); j++) {
                double sum = 0;
                Enumeration e1 = ih1.getEnumeration();
                List<String> ih1NodesChecked = new ArrayList<String>();
                while(e1.hasMoreElements()) {
                    String className1 = (String)((DefaultMutableTreeNode)e1.nextElement()).getUserObject();
                    if(!ih1NodesChecked.contains(className1)) {
                        ih1NodesChecked.add(className1);
                        ih2 = hierarchyList.get(j);
                        Enumeration e2 = ih2.getEnumeration();
                        List<String> ih2NodesChecked = new ArrayList<String>();
                        while(e2.hasMoreElements()) {
                            String className2 = (String)((DefaultMutableTreeNode)e2.nextElement()).getUserObject();
                            if(!ih2NodesChecked.contains(className2)) {
                                ih2NodesChecked.add(className2);
                                if(!className1.equals(className2))
                                    sum = sum + systemAdjacencyMatrix[systemObject.getPositionInClassList(className1)][systemObject.getPositionInClassList(className2)];
                            }
                        }
                    }
                }
                ClusterSet.Entry entry = clusterSet.new Entry();
                entry.addHierarchy(ih1);
                entry.addHierarchy(ih2);
                entry.setNumberOfMethodInvocations((int)sum);
                clusterSet.addClusterEntry(entry);
            }
        }

        return clusterSet;
    }
}
