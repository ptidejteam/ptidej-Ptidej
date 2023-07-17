package gr.uom.java.bytecode;

import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;

public class LoopObject {
	private String label;
    private List<MethodInvocationObject> methodInvocationList;
    private List<FieldInstructionObject> fieldInstructionList;

    public LoopObject(String label) {
    	this.label = label;
        this.methodInvocationList = new ArrayList<MethodInvocationObject>();
        this.fieldInstructionList = new ArrayList<FieldInstructionObject>();
    }

    public String getLabel() {
		return label;
	}

	public boolean addMethodInvocation(MethodInvocationObject mio) {
        return methodInvocationList.add(mio);
    }

    public boolean addFieldInstruction(FieldInstructionObject field) {
        return fieldInstructionList.add(field);
    }

    public ListIterator<MethodInvocationObject> getMethodInvocationIterator() {
        return methodInvocationList.listIterator();
    }

    public ListIterator<FieldInstructionObject> getFieldInstructionIterator() {
        return fieldInstructionList.listIterator();
    }

    public List<MethodInvocationObject> iterativeMethodInvocations(String origin) {
        List<MethodInvocationObject> methodInvocationsFromOrigin = new ArrayList<MethodInvocationObject>();
        //boolean collectionflag = false;
        for(MethodInvocationObject mio : methodInvocationList) {
            if(mio.getOriginClassName().equals(origin))
                methodInvocationsFromOrigin.add(mio);
            /*if(mio.getOriginClassName().equals("java.util.Vector") || mio.getOriginClassName().equals("java.util.ArrayList") ||
                    mio.getOriginClassName().equals("java.util.List"))
                if(mio.getMethodName().equals("get") || mio.getMethodName().equals("elementAt"))
                    collectionflag = true;
            if(mio.getOriginClassName().equals("java.util.Enumeration"))
                if(mio.getMethodName().equals("nextElement"))
                    collectionflag = true;
            if(mio.getOriginClassName().equals("java.util.Iterator") || mio.getOriginClassName().equals("java.util.ListIterator"))
                if(mio.getMethodName().equals("next"))
                    collectionflag = true;
            if(mio.getOriginClassName().endsWith("Enumeration"))
                if(mio.getMethodName().startsWith("next"))
                    collectionflag = true;*/
        }
        //return originflag && collectionflag;
        return methodInvocationsFromOrigin;
    }
    
    public boolean equals(Object o) {
    	if(this == o)
    		return true;
    	
    	if(o instanceof LoopObject) {
    		LoopObject loop = (LoopObject)o;
    		return this.label.equals(loop.label);
    	}
    	return false;
    }
}
