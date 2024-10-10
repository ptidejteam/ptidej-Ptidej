package javainuse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class ConcreteSubjects {
    private List<ConcreteSubject> concreteSubject = new ArrayList<ConcreteSubject>();
    public List<ConcreteSubject> getConcreteSubject() {
        return concreteSubject;
    }
    public void setConcreteSubject(List<ConcreteSubject> concreteSubject) {
        this.concreteSubject = concreteSubject;
    }
}