package gr.uom.java.bytecode.delegation;

import gr.uom.java.bytecode.SystemObject;
import gr.uom.java.bytecode.ClassObject;
import gr.uom.java.bytecode.MethodObject;

import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;

public class DelegationUtility {
    private List<DelegationPath> delegationPathList;

    public DelegationUtility(SystemObject systemObject) {
        delegationPathList = new ArrayList<DelegationPath>();
        ListIterator<ClassObject> classIt = systemObject.getClassListIterator();
        while(classIt.hasNext()) {
            ClassObject classObject = classIt.next();
            ListIterator<MethodObject> methodIt = classObject.getMethodIterator();
            while(methodIt.hasNext()) {
                MethodObject methodObject = methodIt.next();
                DelegationTree tree = new DelegationTree(systemObject,methodObject);
                if(tree.getDepth() >= 1) {
                    delegationPathList.addAll(tree.getDelegationPathList());
                }
            }
        }
    }

    public List<DelegationPath> getAllDelegationPaths() {
        return this.delegationPathList;
    }

    //size must be >= 2
    public List<DelegationPath> getDelegationPathsOfSize(int size) {
        List<DelegationPath> depthDelegationPathList = new ArrayList<DelegationPath>();
        for(DelegationPath path : delegationPathList) {
            if(path.size() == size)
                depthDelegationPathList.add(path);
        }
        return depthDelegationPathList;
    }
}
