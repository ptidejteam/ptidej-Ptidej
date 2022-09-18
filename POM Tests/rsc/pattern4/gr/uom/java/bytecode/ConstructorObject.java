package gr.uom.java.bytecode;

import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;

public class ConstructorObject {

    protected String name;
	protected List<TypeObject> parameterList;
	protected List<MethodInvocationObject> methodInvocationList;
	protected List<String> objectInstantiationList;
    protected List<LoopObject> loopList;
    protected List<FieldInstructionObject> fieldInstructionList;
    protected Access access;

    public ConstructorObject() {
		this.parameterList = new ArrayList<TypeObject>();
		this.methodInvocationList = new ArrayList<MethodInvocationObject>();
		this.objectInstantiationList = new ArrayList<String>();
        this.loopList = new ArrayList<LoopObject>();
        this.fieldInstructionList = new ArrayList<FieldInstructionObject>();
        this.access = Access.NONE;
    }

    public void setAccess(Access access) {
        this.access = access;
    }

    public Access getAccess() {
        return access;
    }

    public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean addParameter(TypeObject parameterType) {
		return parameterList.add(parameterType);
	}
	
	public boolean addMethodInvocation(MethodInvocationObject mio) {
		return methodInvocationList.add(mio);
	}
	
	public boolean addObjectInstantiation(String oi) {
		return objectInstantiationList.add(oi);
	}

    public boolean addLoop(LoopObject loop) {
        return loopList.add(loop);
    }

    public boolean addFieldInstruction(FieldInstructionObject field) {
        return fieldInstructionList.add(field);
    }

    public ListIterator<TypeObject> getParameterListIterator() {
		return parameterList.listIterator();
	}
	
	public ListIterator<MethodInvocationObject> getMethodInvocationIterator() {
		return methodInvocationList.listIterator();
	}
	
	public ListIterator<String> getObjectInstantiationIterator() {
		return objectInstantiationList.listIterator();
	}

    public ListIterator<LoopObject> getLoopIterator() {
        return loopList.listIterator();
    }

    public ListIterator<FieldInstructionObject> getFieldInstructionIterator() {
        return fieldInstructionList.listIterator();
    }

    public boolean containsFieldInstruction(FieldObject field) {
    	for(FieldInstructionObject fieldInstruction : fieldInstructionList) {
    		if(field.equals(fieldInstruction))
    			return true;
    	}
    	return false;
    }

    public boolean hasParameterType(String className) {
        for (TypeObject parameterType : parameterList) {
            if (parameterType.getClassType().equals(className))
                return true;
        }
        return false;
    }

    public SignatureObject getSignature() {
        List<String> parameterList = new ArrayList<String>();
        for(TypeObject type : this.parameterList) {
            parameterList.add(type.getClassType());
        }
        return new SignatureObject(name,name,null,parameterList);
    }

    public boolean equals(Object o) {
        if(this == o) {
			return true;
		}

		if (o instanceof ConstructorObject) {
			ConstructorObject constructorObject = (ConstructorObject)o;

			return this.name.equals(constructorObject.name) &&
				this.parameterList.equals(constructorObject.parameterList);
		}
		return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(!access.equals(Access.NONE))
            sb.append(access.toString()).append(" ");
        sb.append(name);
        sb.append("(");
        if(!parameterList.isEmpty()) {
            for(int i=0; i<parameterList.size()-1; i++)
                sb.append(parameterList.get(i).toString()).append(", ");
            sb.append(parameterList.get(parameterList.size()-1).toString());
        }
        sb.append(")");
        for(MethodInvocationObject mio : methodInvocationList) {
            sb.append("\n\t").append(mio.toString());
        }
        return sb.toString();
    }
}