package gr.uom.java.pattern.clustering;

import static org.math.array.LinearAlgebra.*;
import org.math.array.linearalgebra.*;
import javax.swing.tree.*;
import java.util.*;
import gr.uom.java.pattern.SystemGenerator;

public class PartitionAlgorithm {

    private DefaultMutableTreeNode rootNode;
    private int minimumClusterSize;

    public PartitionAlgorithm(SystemGenerator systemGenerator, int minimumClusterSize) {
        this.minimumClusterSize = minimumClusterSize;
        Graph rootGraph = new Graph();
        rootGraph.setClassNameList(systemGenerator.getMatrixContainer().getClassNameList());
        rootGraph.setAdjacencyMatrix(systemGenerator.getMatrixContainer().getMethodInvocationsMatrix());
        rootNode = new DefaultMutableTreeNode(rootGraph);
        fiedlerPartitioning(rootNode);
    }

    public PartitionAlgorithm(Graph rootGraph, int minimumClusterSize) {
        this.minimumClusterSize = minimumClusterSize;
        rootNode = new DefaultMutableTreeNode(rootGraph);
        fiedlerPartitioning(rootNode);
    }

    //returns rootNode after the partitioning
    public DefaultMutableTreeNode getRootNode() {
        return rootNode;
    }


    //returns a List containing all the LEAF Graphs after the partitioning of the rootGraph
    public List<Graph> getLeafSubGraphs() {
        List<Graph> leafList = new ArrayList<Graph>();
        Enumeration e = rootNode.breadthFirstEnumeration();
        while(e.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)e.nextElement();
            if(node.isLeaf())
                leafList.add((Graph)node.getUserObject());
        }

        return leafList;
    }

    //returns the eigenvector associated with the second smallest eigenvalue
    private double[] getFiedlerVector(double[][] adjacencyMatrix) {
        //A - adjacency matrix
        double[][] A = adjacencyMatrix;
        //D - degree matrix
        double[][] D = new double[A.length][A[0].length];
        //L - Laplacian matrix
        double[][] L = new double[A.length][A[0].length];

        //sumRow is row matrix, each column i in sumRow contains
        //the sum value of column i in the adjacency matrix
        double[] sumRow = sum(A);
        //fill the degree matrix with the degrees of each node.
        for(int i = 0; i < D[0].length;i++) {
            D[i][i] = sumRow[i];
        }
        //L = D - A
        L = minus(D,A);

        EigenvalueDecomposition LEigenDec = eigen(L);

        //diagonal eigenvalue matrix
        double[][] eigenValues = LEigenDec.getD();
        //eigenvector matrix
        double[][] eigenVectors = LEigenDec.getV();

        //keys are the Eigen values sorted in ascending order 
        //values are the corresponding positions in eigenValues matrix
        TreeMap<Double,Integer> sortedEigenValues = new TreeMap<Double,Integer>();

        for(int i = 0; i <eigenValues[0].length; i++) {
            //αλλάζουμε πρόσημο στις αρνητικές τιμές
            double value;
            if(eigenValues[i][i] < 0.0)
                value = -eigenValues[i][i];
            else
                value = eigenValues[i][i];

            sortedEigenValues.put(value,i);
        }

        Set<Double> keySet = sortedEigenValues.keySet();
        Iterator<Double> keyIt = keySet.iterator();
        int minIndex = 0;
        double threshold = 0.00001;
        while(keyIt.hasNext()) {
            Double key = keyIt.next();
            if(key> threshold) {
                minIndex = sortedEigenValues.get(key);
                break;
            }
        }

        return getColumnCopy(eigenVectors,minIndex);
    }

    private void fiedlerPartitioning(DefaultMutableTreeNode parentNode) {
        Graph parentGraph = (Graph)parentNode.getUserObject();

        double[] fiedlerVector = getFiedlerVector(parentGraph.getAdjacencyMatrix());

        List<String> positiveSubGraphNameList = new ArrayList<String>();
        List<String> negativeSubGraphNameList = new ArrayList<String>();

        for(int i=0; i<fiedlerVector.length; i++) {
            if(fiedlerVector[i] > 0.0)
                positiveSubGraphNameList.add(parentGraph.getClassName(i));
            else if(fiedlerVector[i] < 0.0)
                negativeSubGraphNameList.add(parentGraph.getClassName(i));
            else
                System.out.println("Zero value in Fiedler vector");
        }

        Graph positiveSubGraph = new Graph();
        positiveSubGraph.setClassNameList(positiveSubGraphNameList);

        Graph negativeSubGraph = new Graph();
        negativeSubGraph.setClassNameList(negativeSubGraphNameList);

        double[][] positiveAdjacencyMatrix =
            new double[positiveSubGraphNameList.size()][positiveSubGraphNameList.size()];

        for(int i=0; i<positiveAdjacencyMatrix.length; i++) {
            for(int j=0; j<positiveAdjacencyMatrix[0].length; j++) {
                String rowClassName = positiveSubGraph.getClassName(i);
                String columnClassName = positiveSubGraph.getClassName(j);
                positiveAdjacencyMatrix[i][j] = parentGraph.getValueInAdjacencyMatrix(rowClassName, columnClassName);
            }
        }
        positiveSubGraph.setAdjacencyMatrix(positiveAdjacencyMatrix);

        double[][] negativeAdjacencyMatrix =
            new double[negativeSubGraphNameList.size()][negativeSubGraphNameList.size()];

        for(int i=0; i<negativeAdjacencyMatrix.length; i++) {
            for(int j=0; j<negativeAdjacencyMatrix[0].length; j++) {
                String rowClassName = negativeSubGraph.getClassName(i);
                String columnClassName = negativeSubGraph.getClassName(j);

                negativeAdjacencyMatrix[i][j] = parentGraph.getValueInAdjacencyMatrix(rowClassName, columnClassName);
            }
        }
        negativeSubGraph.setAdjacencyMatrix(negativeAdjacencyMatrix);

        DefaultMutableTreeNode positiveSubNode = new DefaultMutableTreeNode(positiveSubGraph);
        DefaultMutableTreeNode negativeSubNode = new DefaultMutableTreeNode(negativeSubGraph);

        parentNode.add(positiveSubNode);
        parentNode.add(negativeSubNode);

        if(positiveSubGraph.getClassNameListSize() > minimumClusterSize) {
            fiedlerPartitioning(positiveSubNode);
        }

        if(negativeSubGraph.getClassNameListSize() > minimumClusterSize) {
            fiedlerPartitioning(negativeSubNode);
        }
    }
}