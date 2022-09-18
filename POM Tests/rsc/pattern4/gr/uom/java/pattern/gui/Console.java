package gr.uom.java.pattern.gui;

import gr.uom.java.bytecode.BytecodeReader;
import gr.uom.java.bytecode.FieldObject;
import gr.uom.java.bytecode.MethodObject;
import gr.uom.java.bytecode.SystemObject;
import gr.uom.java.pattern.*;
import gr.uom.java.pattern.inheritance.Enumeratable;

import java.io.File;
import java.util.*;

public class Console {
    public Console(File inputDir, File outputXML) {
        BytecodeReader br = new BytecodeReader(inputDir);
        SystemObject so = br.getSystemObject();
        SystemGenerator sg = new SystemGenerator(so);
        SortedSet<ClusterSet.Entry> clusterSet = sg.getClusterSet().getInvokingClusterSet();
        List<Enumeratable> hierarchyList = sg.getHierarchyList();
        LinkedHashMap<String, Vector<PatternInstance>> map = new LinkedHashMap<String, Vector<PatternInstance>>();

        PatternEnum[] patternEnum = PatternEnum.values();
        for(int i=0; i<patternEnum.length; i++) {
            String patternName = patternEnum[i].toString();
            PatternDescriptor patternDescriptor = PatternGenerator.getPattern(patternName);
            if(patternDescriptor.getNumberOfHierarchies() == 0) {
                MatrixContainer systemContainer = sg.getMatrixContainer();
                double[][] systemMatrix = null;
                BehavioralData behavioralData = null;
                if(patternName.equals(PatternEnum.SINGLETON.toString())) {
                    systemMatrix = systemContainer.getSingletonMatrix();
                    behavioralData = systemContainer.getSingletonBehavioralData();
                }
                else if(patternName.equals(PatternEnum.TEMPLATE_METHOD.toString())) {
                    systemMatrix = systemContainer.getTemplateMethodMatrix();
                    behavioralData = systemContainer.getTemplateMethodBehavioralData();
                }
                else if(patternName.equals(PatternEnum.FACTORY_METHOD.toString())) {
                    systemMatrix = systemContainer.getFactoryMethodMatrix();
                    behavioralData = systemContainer.getFactoryMethodBehavioralData();
                }

                Vector<PatternInstance> patternInstanceVector = new Vector<PatternInstance>();
                for(int j=0; j<systemMatrix.length; j++) {
                    if(systemMatrix[j][j] == 1.0) {
                        PatternInstance patternInstance = new PatternInstance();
                        patternInstance.addEntry(patternInstance.new Entry(patternDescriptor.getClassNameList().get(0),systemContainer.getClassNameList().get(j),j));
                        if(behavioralData != null) {
                        	if(patternDescriptor.getFieldRoleName() != null) {
                        		Set<FieldObject> fields = behavioralData.getFields(j, j);
                        		if(fields != null) {
                        			for(FieldObject field : fields) {
                        				patternInstance.addEntry(patternInstance.new Entry(patternDescriptor.getFieldRoleName(), field.toString(), -1));
                        			}
                        		}
                        	}
                        	if(patternDescriptor.getMethodRoleName() != null) {
                        		Set<MethodObject> methods = behavioralData.getMethods(j, j);
                        		if(methods != null) {
                        			for(MethodObject method : methods) {
                        				patternInstance.addEntry(patternInstance.new Entry(patternDescriptor.getMethodRoleName(), method.getSignature().toString(), -1));
                        			}
                        		}
                        	}
                        }
                        patternInstanceVector.add(patternInstance);
                    }
                }
                map.put(patternName,patternInstanceVector);
            }
            else if(patternDescriptor.getNumberOfHierarchies() == 1) {
                Vector<PatternInstance> patternInstanceVector = new Vector<PatternInstance>();
                for(Enumeratable ih : hierarchyList) {
                    List<Enumeratable> tempList = new ArrayList<Enumeratable>();
                    tempList.add(ih);
                    MatrixContainer hierarchyMatrixContainer = sg.getHierarchiesMatrixContainer(tempList);
                    generateResults(hierarchyMatrixContainer, patternDescriptor, patternInstanceVector);
                }
                map.put(patternName,patternInstanceVector);
            }
            else if(patternDescriptor.getNumberOfHierarchies() == 2) {
                Iterator<ClusterSet.Entry> it = clusterSet.iterator();
                Vector<PatternInstance> patternInstanceVector = new Vector<PatternInstance>();
                while(it.hasNext()) {
                    ClusterSet.Entry entry = it.next();
                    MatrixContainer hierarchiesMatrixContainer = sg.getHierarchiesMatrixContainer(entry.getHierarchyList());
                    generateResults(hierarchiesMatrixContainer, patternDescriptor, patternInstanceVector);
                }
                map.put(patternName,patternInstanceVector);
            }
        }

        new XMLExporter(map,outputXML);
    }

    private void generateResults(MatrixContainer systemContainer ,PatternDescriptor patternDescriptor, Vector<PatternInstance> patternInstanceVector) {
        double[][] results = SimilarityAlgorithm.getTotalScore(systemContainer,patternDescriptor);
        if(results != null) {
            ClusterResult clusterResult = new ClusterResult(results, patternDescriptor, systemContainer);
            List<PatternInstance> list = clusterResult.getPatternInstanceList();
            for (PatternInstance pi : list) {
                if (!patternInstanceVector.contains(pi))
                    patternInstanceVector.add(pi);
            }
        }
    }
}
