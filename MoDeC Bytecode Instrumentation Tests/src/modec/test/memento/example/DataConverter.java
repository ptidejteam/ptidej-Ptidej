/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package modec.test.memento.example;

//package src.MEMENTO;
import java.io.*;
import java.util.*;

public class DataConverter {
  public static final String DATA_FILE = "Data/Data.txt";
  public static final String OUTPUT_FILE = "Data/SQL.txt";

  private long ID = 0;

  public Memento createMemento() {
	  System.out.println("In createMemento!");
    return (new Memento(this.ID));
  }
  public void setMemento(Memento memento) {
    if (memento != null)
      this.ID = memento.getID();
  }

  public long getLastProcessedID() {
    return this.ID;
  }
  public void setLastProcessedID(long lastID) {
    this.ID = lastID;
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
          this.ID = Long.valueOf(strID).longValue();
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

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long lastProcessedID;

    private Memento(long ID) {
    System.out.println("Dans le constructeur de Memento !!!!!!!!!!!!");
      this.lastProcessedID = ID;
    }
    private long getID() {
      return this.lastProcessedID;
    }
  }// end of class


}// end of class

