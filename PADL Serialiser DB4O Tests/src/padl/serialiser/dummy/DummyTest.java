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
package padl.serialiser.dummy;

import java.io.File;
import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class DummyTest {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		new File(Util.DB4OFILENAME).delete();
		accessDb4o();
		new File(Util.DB4OFILENAME).delete();
		ObjectContainer db = Db4o.openFile(Util.DB4OFILENAME);
		try {
			storeFirstPilot(db);
			storeSecondPilot(db);
			retrieveAllPilots(db);
			retrievePilotByName(db);
			retrievePilotByExactPoints(db);
			updatePilot(db);
			deleteFirstPilotByName(db);
			deleteSecondPilotByName(db);
		}
		finally {
			db.close();
		}
	}

	@SuppressWarnings("deprecation")
	public static void accessDb4o() {
		ObjectContainer db = Db4o.openFile(Util.DB4OFILENAME);
		try {
			// do something with db4o
		}
		finally {
			db.close();
		}
	}

	@SuppressWarnings("deprecation")
	public static void storeFirstPilot(ObjectContainer db) {
		Pilot pilot1 = new Pilot("Michael Schumacher", 100);
		db.set(pilot1);
		System.out.println("Stored " + pilot1);
	}

	@SuppressWarnings("deprecation")
	public static void storeSecondPilot(ObjectContainer db) {
		Pilot pilot2 = new Pilot("Rubens Barrichello", 99);
		db.set(pilot2);
		System.out.println("Stored " + pilot2);
	}

	@SuppressWarnings("deprecation")
	public static void retrieveAllPilotQBE(ObjectContainer db) {
		Pilot proto = new Pilot(null, 0);
		ObjectSet result = db.get(proto);
		Util.listResult(result);
	}

	@SuppressWarnings("deprecation")
	public static void retrieveAllPilots(ObjectContainer db) {
		ObjectSet result = db.get(Pilot.class);
		Util.listResult(result);
	}

	@SuppressWarnings("deprecation")
	public static void retrievePilotByName(ObjectContainer db) {
		Pilot proto = new Pilot("Michael Schumacher", 0);
		ObjectSet result = db.get(proto);
		Util.listResult(result);
	}

	@SuppressWarnings("deprecation")
	public static void retrievePilotByExactPoints(ObjectContainer db) {
		Pilot proto = new Pilot(null, 100);
		ObjectSet result = db.get(proto);
		Util.listResult(result);
	}

	@SuppressWarnings("deprecation")
	public static void updatePilot(ObjectContainer db) {
		ObjectSet result = db.get(new Pilot("Michael Schumacher", 0));
		Pilot found = (Pilot) result.next();
		found.addPoints(11);
		db.set(found);
		System.out.println("Added 11 points for " + found);
		retrieveAllPilots(db);
	}

	@SuppressWarnings("deprecation")
	public static void deleteFirstPilotByName(ObjectContainer db) {
		ObjectSet result = db.get(new Pilot("Michael Schumacher", 0));
		Pilot found = (Pilot) result.next();
		db.delete(found);
		System.out.println("Deleted " + found);
		retrieveAllPilots(db);
	}

	@SuppressWarnings("deprecation")
	public static void deleteSecondPilotByName(ObjectContainer db) {
		ObjectSet result = db.get(new Pilot("Rubens Barrichello", 0));
		Pilot found = (Pilot) result.next();
		db.delete(found);
		System.out.println("Deleted " + found);
		retrieveAllPilots(db);
	}
}
