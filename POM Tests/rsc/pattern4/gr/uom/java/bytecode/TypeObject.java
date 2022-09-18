package gr.uom.java.bytecode;

public class TypeObject {
    private String classType;
    private String genericType;

    public TypeObject(String type) {
        this.classType = type;
    }

    public String getClassType() {
        return classType;
    }

    public String getGenericType() {
        return genericType;
    }

    public void setGeneric(String g) {
        this.genericType = g;
    }

    public boolean containsGeneticType(String s) {
        if(genericType != null)
            return genericType.contains(s);
        else
            return false;
    }

    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }

        if (o instanceof TypeObject) {
            TypeObject typeObject = (TypeObject)o;

            if(this.classType.equals(typeObject.classType)) {
                if(this.genericType == null && typeObject.genericType == null)
                    return true;
                else if(this.genericType != null && typeObject.genericType != null)
                    return this.genericType.equals(typeObject.genericType);
            }
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(classType);
        if(genericType != null)
            sb.append("<").append(genericType).append(">");
        return sb.toString();
    }
}
