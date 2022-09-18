package gr.uom.java.pattern.clustering;

import java.util.*;

public class Graph {
	
	private List<String> classNameList;
	private double[][] adjacencyMatrix;
	
	public int getClassNameListSize() {
		return classNameList.size();
	}

    public boolean containsClassName(String className) {
        return classNameList.contains(className);
    }

    public ListIterator getClassNameListIterator() {
        return classNameList.listIterator();
    }
    
	public String getClassName(int pos) {
		return classNameList.get(pos);
	}
	
	public int getPositionInClassNameList(String className) {
		for(int i=0; i<classNameList.size(); i++) {
			if( className.equals(classNameList.get(i)) )
				return i;
		}
		return -1;
	}
	
	public double getValueInAdjacencyMatrix(String rowClassName, String columnClassName) {
		int row = getPositionInClassNameList(rowClassName);
		int column = getPositionInClassNameList(columnClassName);
		
		if(row != -1 && column != -1)
			return adjacencyMatrix[row][column];
		else {
			System.out.println("Element not found in parent adjacency matrix");
			return -1.0;
		}
	}

    public int getMethodInvocations() {
        int sum = 0;
        for(int i=0; i<adjacencyMatrix.length; i++) {
            for(int j=0; j<=i; j++) {
                sum = sum + (int)adjacencyMatrix[i][j];
            }
        }
        return sum;
    }

	public void setAdjacencyMatrix(double[][] m) {
		this.adjacencyMatrix = m;
	}
	
	public double[][] getAdjacencyMatrix() {
		return this.adjacencyMatrix;
	}
	
	public void setClassNameList(List<String> l) {
		this.classNameList = l;
	}
	
	public String toString() {
		return classNameList.toString();
	}
}