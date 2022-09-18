package gr.uom.java.bytecode.delegation;

import gr.uom.java.bytecode.SignatureObject;

import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;

public class DelegationPath {
    private List<SignatureObject> path;

    public DelegationPath() {
        path = new ArrayList<SignatureObject>();
    }

    public void addSignature(SignatureObject signature) {
        path.add(signature);
    }
    public int size() {
        return path.size();
    }

    public ListIterator<SignatureObject> getPathIterator() {
        return path.listIterator();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<path.size()-1; i++) {
            sb.append(path.get(i)).append("->");
        }
        sb.append(path.get(path.size()-1));
        return sb.toString();
    }
}
