package javainuse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class ConcreteBuilders {
    private List<ConcreteBuilder> concreteBuilder = new ArrayList<ConcreteBuilder>();
    public List<ConcreteBuilder> getConcreteBuilder() {
        return concreteBuilder;
    }
    public void setConcreteBuilder(List<ConcreteBuilder> concreteBuilder) {
        this.concreteBuilder = concreteBuilder;
    }
}