package gr.uom.java.bytecode;

import java.util.List;

public class SignatureObject {
    private String className;
    private String methodName;
    private List<String> parameterList;
    private String returnType;

    public SignatureObject(String className, String methodName, String returnType, List<String> parameterList) {
        this.className = className;
        this.methodName = methodName;
        this.returnType = returnType;
        this.parameterList = parameterList;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public List<String> getParameterList() {
        return parameterList;
    }

    public String getReturnType() {
        return returnType;
    }

    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }

        if (o instanceof SignatureObject) {
            SignatureObject signatureObject = (SignatureObject)o;

            return className.equals(signatureObject.className) &&
                methodName.equals(signatureObject.methodName) &&
                returnType.equals(signatureObject.returnType) &&
                parameterList.equals(signatureObject.parameterList);
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(!className.equals(methodName))
            sb.append(className).append("::");
        sb.append(methodName);
        sb.append("(");
        if(!parameterList.isEmpty()) {
            for(int i=0; i<parameterList.size()-1; i++)
                sb.append(parameterList.get(i)).append(", ");
            sb.append(parameterList.get(parameterList.size()-1));
        }
        sb.append(")");
        if(returnType != null)
            sb.append(":").append(returnType);
        return sb.toString();
    }
}
