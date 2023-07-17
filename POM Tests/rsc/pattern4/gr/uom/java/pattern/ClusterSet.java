package gr.uom.java.pattern;

import gr.uom.java.pattern.inheritance.Enumeratable;

import java.util.*;

public class ClusterSet {
    private TreeSet<ClusterSet.Entry> entrySet;

    public ClusterSet() {
        entrySet = new TreeSet<ClusterSet.Entry>();
    }

    public void addClusterEntry(Entry e) {
        entrySet.add(e);
    }

    public SortedSet<ClusterSet.Entry> getInvokingClusterSet() {
        for(Entry e : entrySet) {
            if (e.getNumberOfMethodInvocations() == 0)
                return entrySet.headSet(e);
        }
        return entrySet;
    }

    public class Entry implements Comparable {
        private volatile int hashCode = 0;

        private int numberOfMethodInvocations;
        private List<Enumeratable> hierarchyList;

        public Entry() {
            hierarchyList = new ArrayList<Enumeratable>();
        }

        public void addHierarchy(Enumeratable ih) {
            hierarchyList.add(ih);
        }

        public void setNumberOfMethodInvocations(int n) {
            this.numberOfMethodInvocations = n;
        }

        public int getNumberOfMethodInvocations() {
            return numberOfMethodInvocations;
        }

        public List<Enumeratable> getHierarchyList() {
            return hierarchyList;
        }

        public boolean equals(Object o) {
            if(this == o) {
                return true;
            }

            if (o instanceof Entry) {
                Entry entry = (Entry)o;

                return numberOfMethodInvocations == entry.numberOfMethodInvocations &&
                    hierarchyList.equals(entry.hierarchyList);
            }
            return false;
        }

        public int compareTo(Object o) {
            Entry entry = (Entry)o;

            if(numberOfMethodInvocations != entry.numberOfMethodInvocations) {
                return -(numberOfMethodInvocations-entry.numberOfMethodInvocations);
            }

            for(int i=0; i<hierarchyList.size(); i++) {
                Enumeratable thisIh = hierarchyList.get(i);
                Enumeratable otherIh = entry.hierarchyList.get(i);
                if(!thisIh.equals(otherIh))
                    return thisIh.toString().compareTo(otherIh.toString());
            }
            //equal
            return 0;
        }

        public int hashCode() {
            if (hashCode == 0) {
                int result = 17;
                result = 37*result + numberOfMethodInvocations;
                for(int i=0; i<hierarchyList.size(); i++) {
                    Enumeratable thisIh = hierarchyList.get(i);
                    result = 37*result + thisIh.toString().hashCode();
                }
                hashCode = result;
            }
            return hashCode;
        }

        public String toString() {
            return numberOfMethodInvocations + " " + hierarchyList.toString();
        }
    }
}
