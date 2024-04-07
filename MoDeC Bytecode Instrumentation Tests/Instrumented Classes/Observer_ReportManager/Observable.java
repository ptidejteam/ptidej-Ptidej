//package src.OBSERVER;
// SUBJECT
public interface Observable {
  public void notifyObservers();
  public void register(Observer obs);
  public void unRegister(Observer obs);
}
