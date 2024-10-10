package javainuse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class ConcreteCommands {
    private List<ConcreteCommand> concreteCommand = new ArrayList<ConcreteCommand>();
    public List<ConcreteCommand> getConcreteCommand() {
        return concreteCommand;
    }
    public void setConcreteCommand(List<ConcreteCommand> concreteCommand) {
        this.concreteCommand = concreteCommand;
    }
}