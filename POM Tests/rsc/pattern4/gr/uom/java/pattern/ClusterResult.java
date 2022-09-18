package gr.uom.java.pattern;

import gr.uom.java.bytecode.FieldObject;
import gr.uom.java.bytecode.MethodObject;

import java.util.*;

public class ClusterResult {
    private TreeSet<ClusterResult.Entry> entrySet;
    private PatternDescriptor patternDescriptor;
    private MatrixContainer systemContainer;

    public ClusterResult(double[][] results, PatternDescriptor patternDescriptor, MatrixContainer systemContainer) {
        entrySet = new TreeSet<ClusterResult.Entry>();
        this.patternDescriptor = patternDescriptor;
        this.systemContainer = systemContainer;

        List<String> rowNameList = patternDescriptor.getClassNameList();
        List<String> columnNameList = systemContainer.getClassNameList();
        for(int i=0; i<results.length; i++) {
            for(int j=0; j<results[i].length; j++) {
                Entry entry = new Entry(results[i][j], rowNameList.get(i), columnNameList.get(j), j);
                entrySet.add(entry);
            }
        }
    }

    public List<PatternInstance> getPatternInstanceList() {
        Map<String, LinkedHashSet<ClusterResult.Entry>> roleMap =
        	new LinkedHashMap<String, LinkedHashSet<ClusterResult.Entry>>();

        Iterator<ClusterResult.Entry> it = entrySet.iterator();
        while(it.hasNext()) {
            Entry entry = it.next();
            if(entry.getScore() > 0.5) {
                if(roleMap.containsKey(entry.getRole())) {
                	LinkedHashSet<ClusterResult.Entry> entrySet = roleMap.get(entry.getRole());
                	entrySet.add(entry);
                }
                else {
                	LinkedHashSet<ClusterResult.Entry> entrySet = new LinkedHashSet<ClusterResult.Entry>();
                	entrySet.add(entry);
                	roleMap.put(entry.getRole(), entrySet);
                }
            }
        }
        
        ArrayList<String> roles = new ArrayList<String>(roleMap.keySet());
        TreeSet<ClusterResult.EntryTuple> entryTupleSet = new TreeSet<ClusterResult.EntryTuple>();
        if(roles.size() > 0) {
	        for(ClusterResult.Entry firstRoleEntry : roleMap.get(roles.get(0))) {
	        	if(roles.size() > 1) {
		        	for(ClusterResult.Entry secondRoleEntry : roleMap.get(roles.get(1))) {
		        		if(roles.size() == 2) {
		        			EntryTuple tuple = new EntryTuple();
		        			int relationshipScore = relationshipScore(firstRoleEntry, secondRoleEntry);
		        			tuple.addPair(relationshipScore, firstRoleEntry, secondRoleEntry);
		        			entryTupleSet.add(tuple);
		        		}
		        		else if(roles.size() > 2) {
		        			for(ClusterResult.Entry thirdRoleEntry : roleMap.get(roles.get(2))) {
		        				EntryTuple tuple = new EntryTuple();
		        				int firstSecondRelationshipScore = relationshipScore(firstRoleEntry, secondRoleEntry);
		        				tuple.addPair(firstSecondRelationshipScore, firstRoleEntry, secondRoleEntry);
		        				int firstThirdRelationshipScore = relationshipScore(firstRoleEntry, thirdRoleEntry);
		        				tuple.addPair(firstThirdRelationshipScore, firstRoleEntry, thirdRoleEntry);
		        				int secondThirdRelationshipScore = relationshipScore(secondRoleEntry, thirdRoleEntry);
		        				tuple.addPair(secondThirdRelationshipScore, secondRoleEntry, thirdRoleEntry);
		        				entryTupleSet.add(tuple);
		        			}
		        		}
		        	}
	        	}
	        }
        }
        
        List<PatternInstance> patternInstanceList = new ArrayList<PatternInstance>();
        
        if(!entryTupleSet.isEmpty()) {
	        int maxRelationshipScore = entryTupleSet.first().getRelationshipScore();
	        for(ClusterResult.EntryTuple tuple : entryTupleSet) {
	        	if(tuple.getRelationshipScore() == maxRelationshipScore) {
	        		List<Entry> roleEntries = tuple.getRoleEntries();
	        		PatternInstance instance = new PatternInstance();
	        		for(Entry roleEntry : roleEntries) {
	        			instance.addEntry(instance.new Entry(roleEntry.getRole(), roleEntry.getClassName(), roleEntry.getPosition()));
	        		}
	        		for(int i=0; i<roleEntries.size(); i++) {
	        			Entry roleEntry1 = roleEntries.get(i);
	        			for(int j=i+1; j<roleEntries.size(); j++) {
	        				Entry roleEntry2 = roleEntries.get(j);
	        				Object[] mergeOutput = mergeBehavioralData(roleEntry1, roleEntry2);
	        				if(patternDescriptor.getFieldRoleName() != null) {
	        					Set<FieldObject> fields = (Set<FieldObject>)mergeOutput[0];
	        					for(FieldObject field : fields) {
	        						instance.addEntry(instance.new Entry(patternDescriptor.getFieldRoleName(), field.toString(), -1));
	        					}
	        				}
	        				if(patternDescriptor.getMethodRoleName() != null) {
	        					Set<MethodObject> methods = (Set<MethodObject>)mergeOutput[1];
	        					for(MethodObject method : methods) {
	        						instance.addEntry(instance.new Entry(patternDescriptor.getMethodRoleName(), method.getSignature().toString(), -1));
	        					}
	        				}
	        			}
	        		}
	        		patternInstanceList.add(instance);
	        	}
	        }
        }

        return patternInstanceList;
    }

    private int relationshipScore(ClusterResult.Entry e1, ClusterResult.Entry e2) {
        int score = 0;
        if(patternDescriptor.getAbstractMethodInvocationMatrix() != null) {
            double[][] matrix = systemContainer.getAbstractMethodInvocationMatrix();
            if(matrix[e1.getPosition()][e2.getPosition()] == 1.0 || matrix[e2.getPosition()][e1.getPosition()] == 1.0)
                score++;
        }
        if(patternDescriptor.getIterativeNonSimilarAbstractMethodInvocationMatrix() != null) {
            double[][] matrix = systemContainer.getIterativeNonSimilarAbstractMethodInvocationMatrix();
            if(matrix[e1.getPosition()][e2.getPosition()] == 1.0 || matrix[e2.getPosition()][e1.getPosition()] == 1.0)
                score++;
        }
        if(patternDescriptor.getIterativeSimilarAbstractMethodInvocationMatrix() != null) {
            double[][] matrix = systemContainer.getIterativeSimilarAbstractMethodInvocationMatrix();
            if(matrix[e1.getPosition()][e2.getPosition()] == 1.0 || matrix[e2.getPosition()][e1.getPosition()] == 1.0)
                score++;
        }
        if(patternDescriptor.getDoubleDispatchMatrix() != null) {
            double[][] matrix = systemContainer.getDoubleDispatchMatrix();
            if(matrix[e1.getPosition()][e2.getPosition()] == 1.0 || matrix[e2.getPosition()][e1.getPosition()] == 1.0)
                score++;
        }
        if(patternDescriptor.getInvokedMethodInInheritedMethodMatrix() != null) {
            double[][] matrix = systemContainer.getInvokedMethodInInheritedMethodMatrix();
            if(matrix[e1.getPosition()][e2.getPosition()] == 1.0 || matrix[e2.getPosition()][e1.getPosition()] == 1.0)
                score++;
        }
        if(patternDescriptor.getSimilarAbstractMethodInvocationMatrix() != null) {
            double[][] matrix = systemContainer.getSimilarAbstractMethodInvocationMatrix();
            if(matrix[e1.getPosition()][e2.getPosition()] == 1.0 || matrix[e2.getPosition()][e1.getPosition()] == 1.0)
                score++;
        }
        if(patternDescriptor.getSimilarMethodInvocationFromSiblingSubclassMatrix() != null) {
            double[][] matrix = systemContainer.getSimilarMethodInvocationFromSiblingSubclassMatrix();
            if(matrix[e1.getPosition()][e2.getPosition()] == 1.0 || matrix[e2.getPosition()][e1.getPosition()] == 1.0)
                score++;
        }
        if(patternDescriptor.getFieldOfSuperClassTypeIsInitializedWithSiblingClassTypeMatrix() != null) {
            double[][] matrix = systemContainer.getFieldOfSuperClassTypeIsInitializedWithSiblingClassTypeMatrix();
            if(matrix[e1.getPosition()][e2.getPosition()] == 1.0 || matrix[e2.getPosition()][e1.getPosition()] == 1.0)
                score++;
        }
        if(patternDescriptor.getCloneInvocationMatrix() != null) {
            double[][] matrix = systemContainer.getCloneInvocationMatrix();
            if(matrix[e1.getPosition()][e2.getPosition()] == 1.0 || matrix[e2.getPosition()][e1.getPosition()] == 1.0)
                score++;
        }
        if(patternDescriptor.getAssociationWithInheritanceMatrix() != null) {
            double[][] matrix = systemContainer.getAssociationWithInheritanceMatrix();
            if(matrix[e1.getPosition()][e2.getPosition()] == 1.0 || matrix[e2.getPosition()][e1.getPosition()] == 1.0)
                score++;
        }
        if(patternDescriptor.getGeneralizationMatrix() != null) {
            double[][] matrix = systemContainer.getGeneralizationMatrix();
            if(matrix[e1.getPosition()][e2.getPosition()] == 1.0 || matrix[e2.getPosition()][e1.getPosition()] == 1.0)
                score++;
        }
        if(patternDescriptor.getAssociationMatrix() != null) {
            double[][] matrix = systemContainer.getAssociationMatrix();
            if(matrix[e1.getPosition()][e2.getPosition()] == 1.0 || matrix[e2.getPosition()][e1.getPosition()] == 1.0)
                score++;
        }
        return score;
    }

    private Object[] mergeBehavioralData(ClusterResult.Entry e1, ClusterResult.Entry e2) {
    	Set<FieldObject> fields = new LinkedHashSet<FieldObject>();
    	Set<MethodObject> methods = new LinkedHashSet<MethodObject>();
    	if(patternDescriptor.getIterativeSimilarAbstractMethodInvocationMatrix() != null) {
    		BehavioralData iterativeSimilarAbstractMethodInvocationBehavioralData = systemContainer.getIterativeSimilarAbstractMethodInvocationBehavioralData();
    		processBehavioralData(iterativeSimilarAbstractMethodInvocationBehavioralData, e1, e2, fields, methods);
    	}
    	if(patternDescriptor.getIterativeNonSimilarAbstractMethodInvocationMatrix() != null) {
    		BehavioralData iterativeNonSimilarAbstractMethodInvocationBehavioralData = systemContainer.getIterativeNonSimilarAbstractMethodInvocationBehavioralData();
    		processBehavioralData(iterativeNonSimilarAbstractMethodInvocationBehavioralData, e1, e2, fields, methods);
    	}
    	if(patternDescriptor.getDoubleDispatchMatrix() != null) {
    		BehavioralData doubleDispatchBehavioralData = systemContainer.getDoubleDispatchBehavioralData();
    		processBehavioralData(doubleDispatchBehavioralData, e1, e2, fields, methods);
    	}
    	if(patternDescriptor.getSimilarAbstractMethodInvocationMatrix() != null) {
    		BehavioralData similarAbstractMethodInvocationBehavioralData = systemContainer.getSimilarAbstractMethodInvocationBehavioralData();
    		processBehavioralData(similarAbstractMethodInvocationBehavioralData, e1, e2, fields, methods);
    	}
    	if(patternDescriptor.getSimilarMethodInvocationFromSiblingSubclassMatrix() != null) {
    		BehavioralData similarMethodInvocationFromSiblingSubclassBehavioralData = systemContainer.getSimilarMethodInvocationFromSiblingSubclassBehavioralData();
    		processBehavioralData(similarMethodInvocationFromSiblingSubclassBehavioralData, e1, e2, fields, methods);
    	}
    	if(patternDescriptor.getInvokedMethodInInheritedMethodMatrix() != null) {
    		BehavioralData invokedMethodInInheritedMethodBehavioralData = systemContainer.getInvokedMethodInInheritedMethodBehavioralData();
    		processBehavioralData(invokedMethodInInheritedMethodBehavioralData, e1, e2, fields, methods);
    	}
    	if(patternDescriptor.getCloneInvocationMatrix() != null) {
    		BehavioralData cloneInvocationBehavioralData = systemContainer.getCloneInvocationBehavioralData();
    		processBehavioralData(cloneInvocationBehavioralData, e1, e2, fields, methods);
    	}
    	if(patternDescriptor.getFieldOfSuperClassTypeIsInitializedWithSiblingClassTypeMatrix() != null) {
    		BehavioralData fieldOfSuperClassTypeIsInitializedWithSiblingClassTypeBehavioralData = systemContainer.getFieldOfSuperClassTypeIsInitializedWithSiblingClassTypeBehavioralData();
    		processBehavioralData(fieldOfSuperClassTypeIsInitializedWithSiblingClassTypeBehavioralData, e1, e2, fields, methods);
    	}
    	if(patternDescriptor.getAssociationWithInheritanceMatrix() != null) {
    		BehavioralData associationWithInheritanceBehavioralData = systemContainer.getAssociationWithInheritanceBehavioralData();
    		processBehavioralData(associationWithInheritanceBehavioralData, e1, e2, fields, methods);
    	}
    	return new Object[] {fields, methods};
    }

	private void processBehavioralData(BehavioralData behavioralData, ClusterResult.Entry e1, ClusterResult.Entry e2, Set<FieldObject> fields, Set<MethodObject> methods) {
		Set<FieldObject> fields1 = behavioralData.getFields(e1.getPosition(), e2.getPosition());
		if(fields1 != null) {
			fields.addAll(fields1);
		}
		if(e1.getPosition() != e2.getPosition()) {
			Set<FieldObject> fields2 = behavioralData.getFields(e2.getPosition(), e1.getPosition());
			if(fields2 != null) {
				fields.addAll(fields2);
			}
		}
		Set<MethodObject> methods1 = behavioralData.getMethods(e1.getPosition(), e2.getPosition());
		if(methods1 != null) {
			methods.addAll(methods1);
		}
		if(e1.getPosition() != e2.getPosition()) {
			Set<MethodObject> methods2 = behavioralData.getMethods(e2.getPosition(), e1.getPosition());
			if(methods2 != null) {
				methods.addAll(methods2);
			}
		}
	}

    private class Entry implements Comparable {
        private volatile int hashCode = 0;

        private Double score;
        private String role;
        private String className;
        private int position;

        public Entry(Double score, String role, String className, int position) {
            this.score = score;
            this.role = role;
            this.className = className;
            this.position = position;
        }

        public Double getScore() {
            return score;
        }

        public String getRole() {
            return role;
        }

        public String getClassName() {
            return className;
        }

        public int getPosition() {
            return position;
        }

        public boolean equals(Object o) {
            if(this == o) {
                return true;
            }

            if (o instanceof Entry) {
                Entry entry = (Entry)o;

                return score.equals(entry.score) && role.equals(entry.role) &&
                    className.equals(entry.className);
            }
            return false;
        }

        public int compareTo(Object o) {
            Entry entry = (Entry)o;

            if(!(score.equals(entry.score))) {
                return -score.compareTo(entry.score);
            }

            if(!(role.equals(entry.role))) {
                return role.compareTo(entry.role);
            }

            if(!(className.equals(entry.className))) {
                return className.compareTo(entry.className);
            }
            //equal
            return 0;
        }

        public int hashCode() {
            if (hashCode == 0) {
                int result = 17;
                result = 37*result + score.hashCode();
                result = 37*result + role.hashCode();
                result = 37*result + className.hashCode();
                hashCode = result;
            }
            return hashCode;
        }

        public String toString() {
            return score + " (" + role + "," + className + ")";
        }
    }
    
    private class EntryTuple implements Comparable {
    	private int relationshipScore;
    	private List<Entry> roleEntries;
    	private volatile int hashCode = 0;
    	
    	public EntryTuple() {
    		this.relationshipScore = 0;
    		this.roleEntries = new ArrayList<Entry>();
    	}
    	
    	public int getRelationshipScore() {
			return relationshipScore;
		}
    	
		public List<Entry> getRoleEntries() {
			return roleEntries;
		}
		
		public void addPair(int relationshipScore, ClusterResult.Entry e1, ClusterResult.Entry e2) {
			if(!roleEntries.contains(e1))
				roleEntries.add(e1);
			if(!roleEntries.contains(e2))
				roleEntries.add(e2);
			this.relationshipScore += relationshipScore;
		}
    	
		public boolean equals(Object o) {
            if(this == o) {
                return true;
            }

            if (o instanceof EntryTuple) {
                EntryTuple tuple = (EntryTuple)o;
                
                if(relationshipScore != tuple.relationshipScore)
                	return false;
                
                for(Entry e : tuple.roleEntries) {
                	if(!roleEntries.contains(e))
                		return false;
                }
                return true;
            }
            return false;
        }
    	
    	public int hashCode() {
            if (hashCode == 0) {
                int result = 17;
                result = 37*result + relationshipScore;
                for(Entry e : roleEntries) {
                	result = 37*result + e.hashCode();
                }
                hashCode = result;
            }
            return hashCode;
        }
    	
    	public String toString() {
            return relationshipScore + " " + roleEntries.toString();
        }
    	
		public int compareTo(Object o) {
			EntryTuple tuple = (EntryTuple)o;
			
			if(relationshipScore != tuple.relationshipScore) {
                return -Integer.valueOf(relationshipScore).compareTo(Integer.valueOf(tuple.relationshipScore));
            }
			
			if(roleEntries.size() == tuple.roleEntries.size()) {
				for(int i=0; i<roleEntries.size(); i++) {
					if(!roleEntries.get(i).equals(tuple.roleEntries.get(i)))
						return roleEntries.get(i).compareTo(tuple.roleEntries.get(i));
				}
			}
			else
				return Integer.valueOf(roleEntries.size()).compareTo(Integer.valueOf(tuple.roleEntries.size()));
            
			return 0;
		}
    }
}
