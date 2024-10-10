package javainuse;
import java.util.HashMap;
import java.util.Map;
public class Observers {
    private Observer observer;
    public Observer getObserver() {
        return observer;
    }
    public void setObserver(Observer observer) {
        this.observer = observer;
    }
}