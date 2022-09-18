//package src.MEMENTO;
public class DCClient {

  public static void main(String[] args) {
    MementoHandler objMementoHandler = new MementoHandler();
    DataConverter objConverter = new DataConverter();

    objConverter.setMemento(objMementoHandler.getMemento());

    if (!(objConverter.process())) {

      System.out.println("Description: Invalid data - " +
                         "Process Stopped");
      System.out.println("Please correct the Data and " +
                         "Run the Application Again");
	objMementoHandler.setMemento(objConverter.createMemento());

    }
//    else
  //  {
	//	objMementoHandler.setMemento(
      //  objConverter.createMemento());
	//}

  }
}
