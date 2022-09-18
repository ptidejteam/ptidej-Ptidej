package gr.uom.java.bytecode;

import java.util.ListIterator;
import java.util.List;
import java.util.ArrayList;

public class MethodObject {

    private TypeObject returnType;
    private boolean _abstract;
    private boolean _static;
    private String className;
    private ConstructorObject constructorObject;

    public MethodObject(ConstructorObject co) {
        this.constructorObject = co;
        this._abstract = false;
        this._static = false;
    }

    public void setReturnType(TypeObject returnType) {
        this.returnType = returnType;
    }

    public TypeObject getReturnType() {
        return returnType;
    }

    public void setAbstract(boolean abstr) {
        this._abstract = abstr;
    }

    public boolean isAbstract() {
        return this._abstract;
    }

    public boolean isStatic() {
        return _static;
    }

    public void setStatic(boolean s) {
        _static = s;
    }

    public void setName(String name) {
        this.constructorObject.name = name;
    }

    public String getName() {
        return constructorObject.getName();
    }

    public Access getAccess() {
        return constructorObject.getAccess();
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassName() {
        return this.className;
    }

    public ListIterator<TypeObject> getParameterListIterator() {
        return constructorObject.getParameterListIterator();
    }

    public ListIterator<MethodInvocationObject> getMethodInvocationIterator() {
        return constructorObject.getMethodInvocationIterator();
    }

    public ListIterator<String> getObjectInstantiationIterator() {
        return constructorObject.getObjectInstantiationIterator();
    }

    public ListIterator<LoopObject> getLoopIterator() {
        return constructorObject.getLoopIterator();
    }

    public ListIterator<FieldInstructionObject> getFieldInstructionIterator() {
        return constructorObject.getFieldInstructionIterator();
    }

    public boolean containsFieldInstruction(FieldObject field) {
    	return constructorObject.containsFieldInstruction(field);
    }

    public boolean hasParameterType(String className) {
        return constructorObject.hasParameterType(className);
    }

    public List<MethodInvocationObject> iterativeMethodInvocations(String origin) {
    	List<MethodInvocationObject> methodInvocationsFromOrigin = new ArrayList<MethodInvocationObject>();
        for(LoopObject loop : constructorObject.loopList) {
        	methodInvocationsFromOrigin.addAll(loop.iterativeMethodInvocations(origin));
        }
        return methodInvocationsFromOrigin;
    }

    public SignatureObject getSignature() {
        List<String> parameterList = new ArrayList<String>();
        for(TypeObject type : constructorObject.parameterList) {
            parameterList.add(type.getClassType());
        }
        return new SignatureObject(className,constructorObject.getName(),returnType.getClassType(),parameterList);
    }

    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }

        if (o instanceof MethodObject) {
            MethodObject methodObject = (MethodObject)o;

            return this.returnType.equals(methodObject.returnType) &&
                this.constructorObject.equals(methodObject.constructorObject);
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(!constructorObject.access.equals(Access.NONE))
            sb.append(constructorObject.access.toString()).append(" ");
        if(_abstract)
            sb.append("abstract").append(" ");
        if(_static)
            sb.append("static").append(" ");
        sb.append(returnType.toString()).append(" ");
        sb.append(constructorObject.name);
        sb.append("(");
        if(!constructorObject.parameterList.isEmpty()) {
            for(int i=0; i<constructorObject.parameterList.size()-1; i++)
                sb.append(constructorObject.parameterList.get(i).toString()).append(", ");
            sb.append(constructorObject.parameterList.get(constructorObject.parameterList.size()-1).toString());
        }
        sb.append(")");
        for(MethodInvocationObject mio : constructorObject.methodInvocationList) {
            sb.append("\n\t").append(mio.toString());
        }
        return sb.toString();
    }
}