package javainuse;
import java.util.HashMap;
import java.util.Map;
public class Singletons {
    private Singleton singleton;
    public Singleton getSingleton() {
        return singleton;
    }
    public void setSingleton(Singleton singleton) {
        this.singleton = singleton;
    }
}