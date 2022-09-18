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

public class MementoHandler {
  public static final String ID_FILE = "ID.txt";

  private DataConverter.Memento objMemento = null;

  public DataConverter.Memento getMemento() {
    ObjectInputStream objStream = null;
    FileUtil util = new FileUtil();

    if (util.isFileExists(ID_FILE)) {
      //read the object from the file
      try {
        objStream = new ObjectInputStream(
                      new FileInputStream(new File(ID_FILE)));

        this.objMemento = (DataConverter.Memento)
                     objStream.readObject();
        objStream.close();

      } catch (Exception e) {
        System.out.println("Error Reading Memento");
        System.exit(1);
      }
      //delete the old memento
      util.deleteFile(ID_FILE);
    }
    return this.objMemento;
  }

  public void setMemento(DataConverter.Memento memento) {
    ObjectOutputStream objStream = null;

    //write the object to the file
    try {
      objStream = new ObjectOutputStream(
                    new FileOutputStream(new File(ID_FILE)));

      objStream.writeObject(memento);
      objStream.close();

    } catch (Exception e) {
      System.out.println("Error Writing Memento");
      System.exit(1);
    }
  }

}// end of class

