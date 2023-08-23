package demo.codeanalyzer.common.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.lang.model.element.NestingKind;

/**
 * Stores basic attributes of a java class
 * @author Deepa Sobhana, Seema Richard
 */
public class JavaClassInfo extends BaseJavaClassModelInfo implements ClassFile {

    private String nameOfSuperClass;
    private String packageName;
    private String nestingKind;
    private boolean isInterface;
    private boolean isSerializable;
    private JavaSourceTreeInfo sourceTreeInfo;
    private Collection<Method> methods = new ArrayList<Method>();
    private Collection<Method> constructors = new ArrayList<Method>();
    private Collection<String> nameOfInterfaces = new ArrayList<String>();
    private Collection<String> classTypes = new ArrayList<String>();
    private Map<String, Field> fields = new HashMap<String, Field>();

    public void addField(Field field) {
        fields.put(field.getName(), field);
    }

    public Field getField(String fieldName) {
        return fields.get(fieldName);
    }

    public void setFields(Map<String, Field> fields) {
        this.fields = fields;
    }

    public Collection<Field> getFields() {
        return fields.values();
    }

    public void setNameOfSuperClass(String superClass) {
        this.nameOfSuperClass = superClass;
    }

    public String getNameOfSuperClass() {
        return this.nameOfSuperClass;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getNestingKind() {
        return nestingKind;
    }

    public void setNestingKind(String nestingKind) {
        this.nestingKind = nestingKind;
    }

    public void setSourceTreeInfo(JavaSourceTreeInfo sourceTreeInfo) {
        this.sourceTreeInfo = sourceTreeInfo;
    }

    public JavaSourceTreeInfo getSourceTreeInfo() {
        return this.sourceTreeInfo;
    }

    public Collection<Method> getMethods() {
        return this.methods;
    }

    public void setMethods(Collection<Method> methods) {
        this.methods = methods;
    }

    public void addMethod(Method method) {
        methods.add(method);
    }

    public Collection<Method> getConstructors() {
        return constructors;
    }

    public void setConstructors(Collection<Method> constructors) {
        this.constructors = constructors;
    }

    public void addConstructor(Method method) {
        constructors.add(method);
    }

    public Collection<String> getNameOfInterfaces() {
        return nameOfInterfaces;
    }

    public void setNameOfInterfaces(Collection<String> interfaces) {
        this.nameOfInterfaces = interfaces;
    }

    public void addNameOfInterface(String interfaceDet) {
        this.nameOfInterfaces.add(interfaceDet);
    }

    public Collection<String> getClassTypes() {
        return classTypes;
    }

    public void setClassTypes(Collection<String> classTypes) {
        this.classTypes = classTypes;
    }

    public void addClassType(String classType) {
        this.classTypes.add(classType);
    }

    public boolean isInterface() {
        return this.isInterface;
    }

    public void setIsInterface(boolean interfaceFlag) {
        this.isInterface = interfaceFlag;
    }

    public void setSerializable(boolean isSerializable) {
        this.isSerializable = isSerializable;
    }

    public boolean isSerializable() {
        return isSerializable;
    }
    
    public boolean isTopLevelClass() {
        return nestingKind.equals(NestingKind.TOP_LEVEL.toString());
    }    
}
