package gr.uom.java.pattern.gui;

import gr.uom.java.bytecode.FieldObject;
import gr.uom.java.bytecode.MethodObject;
import gr.uom.java.pattern.*;
import gr.uom.java.pattern.gui.progress.ProgressObserver;
import gr.uom.java.pattern.gui.progress.DetectionFinishedEvent;
import gr.uom.java.pattern.gui.progress.PatternDetectionSource;
import gr.uom.java.pattern.inheritance.Enumeratable;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.*;

public class LongTask {
    private int lengthOfTask;
    private int current = 0;
    private boolean done = false;

    private SystemGenerator sg;
    private PatternDescriptor patternDescriptor;
    private ProgressObserver progressObserver;
    private String patternName;
    private SortedSet<ClusterSet.Entry> clusterSet;
    private List<Enumeratable> hierarchyList;
    private PatternEnum[] patternEnum;

    public LongTask(SystemGenerator sg, PatternDescriptor patternDescriptor,
                    ProgressObserver progressObserver, String patternName) {
        this.sg = sg;
        this.patternDescriptor = patternDescriptor;
        this.progressObserver = progressObserver;
        this.patternName = patternName;
        this.clusterSet = sg.getClusterSet().getInvokingClusterSet();
        this.hierarchyList = sg.getHierarchyList();

        if(patternDescriptor.getNumberOfHierarchies() == 1)
            lengthOfTask = hierarchyList.size();
        else if(patternDescriptor.getNumberOfHierarchies() == 2)
            lengthOfTask = clusterSet.size();
    }

    public LongTask(SystemGenerator sg, ProgressObserver progressObserver) {
        this.sg = sg;
        this.progressObserver = progressObserver;
        this.patternEnum = PatternEnum.values();
        lengthOfTask = patternEnum.length;
        this.clusterSet = sg.getClusterSet().getInvokingClusterSet();
        this.hierarchyList = sg.getHierarchyList();
    }

    public void go() {
        final SwingWorker worker = new SwingWorker() {
            public Object construct() {
                current = 0;
                done = false;
                if(patternName == null && patternDescriptor == null)
                    return new LongActualTask();
                else
                    return new ShortActualTask();
            }
        };
        worker.start();
    }

    public int getLengthOfTask() {
        return lengthOfTask;
    }

    public int getCurrent() {
        return current;
    }

    public boolean isDone() {
        return done;
    }

    public class ShortActualTask {
        public ShortActualTask() {
        	ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        	long start = bean.getCurrentThreadCpuTime();
            if(patternDescriptor.getNumberOfHierarchies() == 1) {
                Vector<PatternInstance> patternInstanceVector = new Vector<PatternInstance>();
                for(Enumeratable ih : hierarchyList) {
                    List<Enumeratable> tempList = new ArrayList<Enumeratable>();
                    tempList.add(ih);
                    MatrixContainer hierarchyMatrixContainer = sg.getHierarchiesMatrixContainer(tempList);
                    generateResults(hierarchyMatrixContainer, patternDescriptor, patternInstanceVector);
                    current++;
                }
                PatternDetectionSource source = new PatternDetectionSource(patternName, patternInstanceVector);
                DetectionFinishedEvent event = new DetectionFinishedEvent(source);
                progressObserver.fireDetectionFinishedEvent(event);
                done = true;
            }
            else if(patternDescriptor.getNumberOfHierarchies() == 2) {
                Iterator<ClusterSet.Entry> it = clusterSet.iterator();
                Vector<PatternInstance> patternInstanceVector = new Vector<PatternInstance>();
                while(it.hasNext()) {
                    ClusterSet.Entry entry = it.next();
                    MatrixContainer hierarchiesMatrixContainer = sg.getHierarchiesMatrixContainer(entry.getHierarchyList());
                    generateResults(hierarchiesMatrixContainer, patternDescriptor, patternInstanceVector);
                    current++;
                }
                PatternDetectionSource source = new PatternDetectionSource(patternName, patternInstanceVector);
                DetectionFinishedEvent event = new DetectionFinishedEvent(source);
                progressObserver.fireDetectionFinishedEvent(event);
                done = true;
            }
            long end = bean.getCurrentThreadCpuTime();
            long time = end - start;
            System.out.println("Detection time for " + patternName + ": " + time/1000000.0 + " ms");
        }
    }

    public class LongActualTask {
        public LongActualTask() {
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
                    PatternDetectionSource source = new PatternDetectionSource(patternName, patternInstanceVector);
                    DetectionFinishedEvent event = new DetectionFinishedEvent(source);
                    progressObserver.fireDetectionFinishedEvent(event);
                    current++;
                }
                else if(patternDescriptor.getNumberOfHierarchies() == 1) {
                    Vector<PatternInstance> patternInstanceVector = new Vector<PatternInstance>();
                    for(Enumeratable ih : hierarchyList) {
                        List<Enumeratable> tempList = new ArrayList<Enumeratable>();
                        tempList.add(ih);
                        MatrixContainer hierarchyMatrixContainer = sg.getHierarchiesMatrixContainer(tempList);
                        generateResults(hierarchyMatrixContainer, patternDescriptor, patternInstanceVector);
                    }
                    PatternDetectionSource source = new PatternDetectionSource(patternName, patternInstanceVector);
                    DetectionFinishedEvent event = new DetectionFinishedEvent(source);
                    progressObserver.fireDetectionFinishedEvent(event);
                    current++;
                }
                else if(patternDescriptor.getNumberOfHierarchies() == 2) {
                    Iterator<ClusterSet.Entry> it = clusterSet.iterator();
                    Vector<PatternInstance> patternInstanceVector = new Vector<PatternInstance>();
                    while(it.hasNext()) {
                        ClusterSet.Entry entry = it.next();
                        MatrixContainer hierarchiesMatrixContainer = sg.getHierarchiesMatrixContainer(entry.getHierarchyList());
                        generateResults(hierarchiesMatrixContainer, patternDescriptor, patternInstanceVector);
                    }
                    PatternDetectionSource source = new PatternDetectionSource(patternName, patternInstanceVector);
                    DetectionFinishedEvent event = new DetectionFinishedEvent(source);
                    progressObserver.fireDetectionFinishedEvent(event);
                    current++;
                }
            }
            done = true;
        }
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
