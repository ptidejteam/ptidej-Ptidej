package gr.uom.java.pattern;

import static org.math.array.DoubleArray.*;
import static org.math.array.LinearAlgebra.*;

import java.util.Map;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.text.NumberFormat;

public class SimilarityAlgorithm {
	
	private static final double TOLERANCE = 0.001;

    public static double[][] getTotalScore(MatrixContainer system, PatternDescriptor pattern) {
        double[][] X = new double[pattern.getClassNameList().size()][system.getClassNameList().size()];
        
        if(pattern.getAbstractMethodInvocationMatrix() != null) {
            if(allElementsEqualToZero(system.getAbstractMethodInvocationMatrix())) {
                return null;
            }
            else {
                X = plus(X,getSimilarityScore(system.getAbstractMethodInvocationMatrix(),pattern.getAbstractMethodInvocationMatrix()));
            }
        }
        if(pattern.getIterativeNonSimilarAbstractMethodInvocationMatrix() != null) {
            if(allElementsEqualToZero(system.getIterativeNonSimilarAbstractMethodInvocationMatrix())) {
                return null;
            }
            else {
                X = plus(X,getSimilarityScore(system.getIterativeNonSimilarAbstractMethodInvocationMatrix(),
                		pattern.getIterativeNonSimilarAbstractMethodInvocationMatrix()));
            }
        }
        if(pattern.getIterativeSimilarAbstractMethodInvocationMatrix() != null) {
            if(allElementsEqualToZero(system.getIterativeSimilarAbstractMethodInvocationMatrix())) {
                return null;
            }
            else {
                X = plus(X,getSimilarityScore(system.getIterativeSimilarAbstractMethodInvocationMatrix(),
                		pattern.getIterativeSimilarAbstractMethodInvocationMatrix()));
            }
        }
        if(pattern.getDoubleDispatchMatrix() != null) {
            if(allElementsEqualToZero(system.getDoubleDispatchMatrix())) {
                return null;
            }
            else {
                X = plus(X,getSimilarityScore(system.getDoubleDispatchMatrix(),pattern.getDoubleDispatchMatrix()));
            }
        }
        if(pattern.getInvokedMethodInInheritedMethodMatrix() != null) {
            if(allElementsEqualToZero(system.getInvokedMethodInInheritedMethodMatrix())) {
                return null;
            }
            else {
                X = plus(X,getSimilarityScore(system.getInvokedMethodInInheritedMethodMatrix(),pattern.getInvokedMethodInInheritedMethodMatrix()));
            }
        }
        if(pattern.getSimilarAbstractMethodInvocationMatrix() != null) {
            if(allElementsEqualToZero(system.getSimilarAbstractMethodInvocationMatrix())) {
                return null;
            }
            else {
                X = plus(X,getSimilarityScore(system.getSimilarAbstractMethodInvocationMatrix(),
                		pattern.getSimilarAbstractMethodInvocationMatrix()));
            }
        }
        if(pattern.getSimilarMethodInvocationFromSiblingSubclassMatrix() != null) {
            if(allElementsEqualToZero(system.getSimilarMethodInvocationFromSiblingSubclassMatrix())) {
                return null;
            }
            else {
                X = plus(X,getSimilarityScore(system.getSimilarMethodInvocationFromSiblingSubclassMatrix(),
                		pattern.getSimilarMethodInvocationFromSiblingSubclassMatrix()));
            }
        }
        if(pattern.getFieldOfSuperClassTypeIsInitializedWithSiblingClassTypeMatrix() != null) {
            if(allElementsEqualToZero(system.getFieldOfSuperClassTypeIsInitializedWithSiblingClassTypeMatrix())) {
                return null;
            }
            else {
                X = plus(X,getSimilarityScore(system.getFieldOfSuperClassTypeIsInitializedWithSiblingClassTypeMatrix(),
                		pattern.getFieldOfSuperClassTypeIsInitializedWithSiblingClassTypeMatrix()));
            }
        }
        if(pattern.getCloneInvocationMatrix() != null) {
            if(allElementsEqualToZero(system.getCloneInvocationMatrix())) {
                return null;
            }
            else {
                X = plus(X,getSimilarityScore(system.getCloneInvocationMatrix(),pattern.getCloneInvocationMatrix()));
            }
        }
        if(pattern.getAssociationWithInheritanceMatrix() != null) {
        	if(allElementsEqualToZero(system.getAssociationWithInheritanceMatrix())) {
                return null;
            }
        	else {
        		X = plus(X,getSimilarityScore(system.getAssociationWithInheritanceMatrix(),pattern.getAssociationWithInheritanceMatrix()));
        	}
        }
        if(pattern.getAbstractMatrix() != null) {
            if(allElementsEqualToZero(system.getAbstractMatrix())) {
                return null;
            }
            else {
                double[][] s = system.getAbstractMatrix();
                double[][] p = pattern.getAbstractMatrix();
                for(int i=0; i<s.length; i++) {
                    for(int j=0; j<p.length; j++) {
                        if(s[i][i] == 1.0 && p[j][j] == 1.0)
                            X[j][i] = X[j][i] + 1.0;
                    }
                }
                //X = X.plus(getSimilarityScore(system.getAbstractMatrix(),pattern.getAbstractMatrix()));
            }
        }
        if(pattern.getGeneralizationMatrix() != null) {
            if(allElementsEqualToZero(system.getGeneralizationMatrix())) {
                return null;
            }
            else {
                X = plus(X,getSimilarityScore(system.getGeneralizationMatrix(),pattern.getGeneralizationMatrix()));
            }
        }
        if(pattern.getAssociationMatrix() != null) {
            if(allElementsEqualToZero(system.getAssociationMatrix())) {
                return null;
            }
            else {
                int associationStartsFromRole = -1;
                int associationEndsToRole = -1;
                double[][] p = pattern.getAssociationMatrix();
                for(int i=0; i<p.length; i++) {
                    for(int j=0; j<p[i].length; j++) {
                        if(p[i][j] == 1.0) {
                            associationStartsFromRole = i;
                            associationEndsToRole = j;
                        }
                    }
                }

                double[][] temp = getSimilarityScore(system.getAssociationMatrix(),pattern.getAssociationMatrix());

                Map<Integer, LinkedHashMap<String, ArrayList<Integer>> > roleMaps = new LinkedHashMap<Integer, LinkedHashMap<String, ArrayList<Integer>> >();
                roleMaps.put(0, new LinkedHashMap<String, ArrayList<Integer>>());
                roleMaps.put(1, new LinkedHashMap<String, ArrayList<Integer>>());
                NumberFormat nf = NumberFormat.getInstance();
                nf.setMaximumFractionDigits(15);

                for(int i=0; i<temp.length; i++) {
                    for(int j=0; j<temp[i].length; j++) {
                        if(temp[i][j] > 0 && temp[i][j] < 0.01) {
                            //System.out.println(temp[i][j] + "\t" + i + "\t" + system.classNameList.get(j));
                            Map<String, ArrayList<Integer>> roleMap = roleMaps.get(i);
                            String score = nf.format(temp[i][j]);
                            if(!roleMap.containsKey(score)) {
                                ArrayList<Integer> list = new ArrayList<Integer>();
                                list.add(j);
                                roleMap.put(score,list);
                            }
                            else {
                                ArrayList<Integer> list = roleMap.get(score);
                                list.add(j);
                            }
                        }
                    }
                }

                Map<String, ArrayList<Integer>> associationStartsFromRoleMap = roleMaps.get(associationStartsFromRole);
                Map<String, ArrayList<Integer>> associationEndsToRoleMap = roleMaps.get(associationEndsToRole);
                for(String score : associationStartsFromRoleMap.keySet()) {
                    ArrayList<Integer> associationStartsFromClasses = associationStartsFromRoleMap.get(score);
                    ArrayList<Integer> associationEndsToClasses = associationEndsToRoleMap.get(score);
                    double[][] systemAssociationMatrix = system.getAssociationMatrix();
                    if(associationStartsFromClasses != null && associationEndsToClasses != null) {
                        for(Integer i : associationStartsFromClasses) {
                            for(Integer j : associationEndsToClasses) {
                                if(systemAssociationMatrix[i][j] == 1.0) {
                                    if(temp[associationStartsFromRole][i] != 1.0)
                                        temp[associationStartsFromRole][i] = 1.0;
                                    if(temp[associationEndsToRole][j] != 1.0)
                                        temp[associationEndsToRole][j] = 1.0;
                                }
                            }
                        }
                    }
                }
                X = plus(X,temp);
            }
        }

        for(int i=0; i<X.length; i++) {
            for(int j=0; j<X[i].length; j++) {
                X[i][j] = X[i][j]/(double)pattern.getDivisor(i);
            }
        }

        return X;

    }

    private static boolean allElementsEqualToZero(double[][] m) {
        for(int i=0; i<m.length; i++) {
            for(int j=0; j<m[i].length; j++) {
                if(m[i][j] != 0.0)
                    return false;
            }
        }
        return true;
    }

    private static double[][] getSimilarityScore(double[][] A, double[][] B) {
		int m = A.length; // row dimension
		int n = B[0].length; //column dimension
		
		if( A == new double[A.length][A[0].length] ||
			B == new double[B.length][B[0].length] )
			return new double[n][m];

        double[][] X = fill(n, m, 1.0);
		double[][] prevX = new double[n][m];
		boolean flag = false;
		int i=0;
		
		while(!flag) {
			double[][] temp1 = times(times(B,X),transpose(A));
                    //B.times(X).times(A.transpose());
			double[][] temp2 = times(times(transpose(B),X),A);
                    //B.transpose().times(X).times(A);
			double[][] temp = plus(temp1,temp2);
			X = divide(temp,norm1(temp));
			i++;
			
			if(i%2 == 0) {
				flag = convergence(X, prevX);
				prevX = X;
			}
		}
        return X;
	}
	
	private static boolean convergence(double[][] a, double[][] b) {
		for(int i=0; i<a.length; i++) {
			for(int j=0; j<a[i].length; j++) {
				if(Math.abs(a[i][j] - b[i][j]) > TOLERANCE)
					return false;
			}
		}
		return true;
	}

    public static double norm1(double[][] m) {
	    double f = 0;
		for (int j = 0; j < m[0].length; j++) {
			double s = 0;
			for (int i = 0; i < m.length; i++) {
				s += Math.abs(m[i][j]);
			}
			f = Math.max(f, s);
		}
		return f;
	}
}