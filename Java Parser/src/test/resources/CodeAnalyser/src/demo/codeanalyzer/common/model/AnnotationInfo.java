package demo.codeanalyzer.common.model;

/**
 * Code analyzer model for storing details of annotation
 * @author Deepa Sobhana, Seema Richard
 */
public class AnnotationInfo implements Annotation {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Annotation Name: " + name);
        buffer.append("\n");
        return buffer.toString();
    }
}
