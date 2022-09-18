package demo.codeanalyzer.common.model;

import java.util.Collection;

/**
 * Stores basic attributes of a java class
 * @author Deepa Sobhana, Seema Richard
 */
public interface ClassFile extends BaseJavaClassModel {

    Collection<Field> getFields();

    Field getField(String fieldName);

    /**
     * @return all the methods that are present in this class. This includes
     *         methods that are added by compiler as well, e.g. clinit and init
     *         methods.
     */
    Collection<? extends Method> getMethods();

    /**
     * @param methodRef is the reference of the method that is being looked for
     * @return return the method object that matches the guven criteria. null,
     *         otherwise.
     */
    // Method getMethod(MethodRef methodRef);
    /**
     * @return external name of super class
     */
    String getNameOfSuperClass();

    /**
     * @return external names of any interfaces implemented by this class.
     */
    Collection<String> getNameOfInterfaces();

    /**
     * @return true if this is an interface, else false
     */
    boolean isInterface();

    boolean isSerializable();
    
    public boolean isTopLevelClass();

    Collection<Method> getConstructors();

    Collection<String> getClassTypes();

    JavaSourceTreeInfo getSourceTreeInfo();

    public void addClassType(String classType);

}
