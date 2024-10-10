package javainuse;
import java.util.HashMap;
import java.util.Map;
public class DesignPattern {
    private String name;
    private MicroArchitectures microArchitectures;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public MicroArchitectures getMicroArchitectures() {
        return microArchitectures;
    }
    public void setMicroArchitectures(MicroArchitectures microArchitectures) {
        this.microArchitectures = microArchitectures;
    }
}