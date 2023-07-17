package gr.uom.java.bytecode;

import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;

public class MethodInvocationObject {

    private String originClassName;
    private String methodName;
    private String returnType;
    private List<String> parameterList;

    public MethodInvocationObject(String originClassName, String methodName, String returnType) {
        this.originClassName = originClassName;
        this.methodName = methodName;
        this.returnType = returnType;
        this.parameterList = new ArrayList<String>();
    }

    public boolean addParameter(String parameterType) {
        return parameterList.add(parameterType);
    }

    public ListIterator<String> getParameterListIterator() {
        return parameterList.listIterator();
    }

    public String getReturnType() {
		return returnType;
	}

    public String getOriginClassName() {
        return this.originClassName;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public boolean hasParameterType(String className) {
        for (String parameterType : parameterList) {
            if (parameterType.equals(className))
                return true;
        }
        return false;
    }
    
    public boolean hasParameterType(ClassObject classObject) {
    	List<String> compatibleTypes = new ArrayList<String>();
    	compatibleTypes.add(classObject.getName());
    	ListIterator<String> superclassIterator = classObject.getSuperclassIterator();
    	while(superclassIterator.hasNext()) {
    		String superclassType = superclassIterator.next();
    		if(!superclassType.equals("java.lang.Object"))
    			compatibleTypes.add(superclassType);
    	}
    	for (String parameterType : parameterList) {
    		if(compatibleTypes.contains(parameterType))
    			return true;
    	}
    	return false;
    }

    public SignatureObject getSignature() {
        return new SignatureObject(originClassName,methodName,returnType,parameterList);
    }

    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }

        if (o instanceof MethodInvocationObject) {
            MethodInvocationObject methodInvocationObject = (MethodInvocationObject)o;

            return originClassName.equals(methodInvocationObject.originClassName) &&
                methodName.equals(methodInvocationObject.methodName) &&
                returnType.equals(methodInvocationObject.returnType) &&
                parameterList.equals(methodInvocationObject.parameterList);
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(originClassName).append("::");
        sb.append(methodName);
        sb.append("(");
        if(!parameterList.isEmpty()) {
            for(int i=0; i<parameterList.size()-1; i++)
                sb.append(parameterList.get(i)).append(", ");
            sb.append(parameterList.get(parameterList.size()-1));
        }
        sb.append(")");
        sb.append(":").append(returnType);
        return sb.toString();
    }
}