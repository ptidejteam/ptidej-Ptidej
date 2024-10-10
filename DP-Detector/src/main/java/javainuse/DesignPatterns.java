package javainuse;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.HashMap;
import java.util.Map;
public class DesignPatterns {

    private Program program;
    public Program getProgram() {
        return program;
    }
    public void setProgram(Program program) {
        this.program = program;
    }
}