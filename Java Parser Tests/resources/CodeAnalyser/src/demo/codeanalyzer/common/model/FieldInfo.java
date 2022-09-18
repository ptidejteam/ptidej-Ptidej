package demo.codeanalyzer.common.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Stores details of fields in the java code
 * @author Deepa Sobhana, Seema Richard
 */
public class FieldInfo extends BaseJavaClassModelInfo implements Field {

    private ClassFile owningClass;
    private Collection<String> fieldTypes = new ArrayList<String>();

    public void addFieldType(String fieldType) {
       fieldTypes.add(fieldType);
    }

    public Collection<String> getFieldTypes() {
        return fieldTypes;
    }

    /**
     * @return the {@link ClassFile} this method belongs to.
     */
    public ClassFile getOwningClass() {
        return owningClass;
    }

    public void setOwningClass(ClassFile owningClass) {
        this.owningClass = owningClass;
    }
}
