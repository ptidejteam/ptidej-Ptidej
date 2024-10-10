package javainuse;
import java.util.HashMap;
import java.util.Map;
public class ConcreteObservers {
    private ConcreteObserver concreteObserver;
    public ConcreteObserver getConcreteObserver() {
        return concreteObserver;
    }
    public void setConcreteObserver(ConcreteObserver concreteObserver) {
        this.concreteObserver = concreteObserver;
    }
}