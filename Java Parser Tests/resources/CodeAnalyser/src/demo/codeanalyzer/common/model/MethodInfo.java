package demo.codeanalyzer.common.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Stores method information of java class
 * @author Deepa Sobhana, Seema Richard
 */
public class MethodInfo extends BaseJavaClassModelInfo implements Method {

    private ClassFile owningClass;
    private Collection<String> parameters = new ArrayList<String>();
    private String returnType;
    Collection<String> exceptions = new ArrayList<String>();

    public ClassFile getOwningClass() {
        return owningClass;
    }

    public void setOwningClass(ClassFile owningClass) {
        this.owningClass = owningClass;
    }

    public Collection<String> getParameters() {
        return parameters;
    }

    public void setParameters(Collection<String> parameters) {
        this.parameters = parameters;
    }

    public void addParameters(String parameter) {
        this.parameters.add(parameter);
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public Collection<String> getExceptions() {
        return exceptions;
    }

    public void setExceptions(Collection<String> exceptions) {
        this.exceptions = exceptions;
    }

    public void addException(String exception) {
        this.exceptions.add(exception);
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Method Name: " + getName());
        buffer.append("\n");
        if (parameters.size() <= 0) {
            buffer.append("No parameters defined ");
            buffer.append("\n");
        }
        for (Object param : parameters) {
            buffer.append(param.toString());
            buffer.append(", ");
        }
        return buffer.toString();
    }
}
