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
