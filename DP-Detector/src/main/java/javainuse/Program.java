package javainuse;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Program {
    private String name;
    private List<DesignPattern> designPattern = new ArrayList<DesignPattern>();

    @JsonProperty
    private String type;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<DesignPattern> getDesignPattern() {
        return designPattern;
    }
    public void setDesignPattern(List<DesignPattern> designPattern) {
        this.designPattern = designPattern;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}