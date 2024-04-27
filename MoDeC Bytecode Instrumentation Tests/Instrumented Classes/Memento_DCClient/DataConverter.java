//package src.MEMENTO;
import java.io.*;
import java.util.*;

public class DataConverter {
  public static final String DATA_FILE = "Data/Data.txt";
  public static final String OUTPUT_FILE = "Data/SQL.txt";

  private long ID = 0;

  public Memento createMemento() {
	  System.out.println("In createMemento!");
    return (new Memento(ID));
  }
  public void setMemento(Memento memento) {
    if (memento != null)
      ID = memento.getID();
  }

  public long getLastProcessedID() {
    return ID;
  }
  public void setLastProcessedID(long lastID) {
    ID = lastID;
  }
  public boolean process() {
    boolean success = true;
    String inputLine = "";
    long currID = 0;

    try {
      File inFile = new File(DATA_FILE);
      BufferedReader br = new BufferedReader(
                            new InputStreamReader(
                              new FileInputStream(inFile)));

      long lastID = getLastProcessedID();

      while ((inputLine = br.readLine()) != null) {
        StringTokenizer st =
          new StringTokenizer(inputLine, ",");
        String strID = st.nextToken();
        currID = Long.valueOf(strID).longValue();

        if (lastID < currID) {
          Customer c =
            new Customer(strID, st.nextToken(),
                         st.nextToken(), st.nextToken());

          if (!(c.isValid())) {
            success = false;
            break;
          }
          ID = Long.valueOf(strID).longValue();
          FileUtil util = new FileUtil();
          util.writeToFile(OUTPUT_FILE, c.getSQL(),
                           true, true);

        }
      }
      br.close();
    } // Try
    catch (Exception ex) {
      System.out.println(" An error has occurred " +
                         ex.getMessage());
      System.exit(1);
    }
    if (success == false) {
      System.out.println("An error has occurred at ID=" +
                         currID);
      System.out.println("Data Record=" + inputLine);
      return false;
    }
    return true;
  }

  class Memento implements java.io.Serializable {

    private long lastProcessedID;

    private Memento(long ID) {
    System.out.println("Dans le constructeur de Memento !!!!!!!!!!!!");
      lastProcessedID = ID;
    }
    private long getID() {
      return lastProcessedID;
    }
  }// end of class


}// end of class

