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
import com.db4o.query.Predicate;
import com.db4o.query.Query;

public class StructuredExample extends Util {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		new File(Util.DB4OFILENAME).delete();
		ObjectContainer db = Db4o.openFile(Util.DB4OFILENAME);
		try {
			storeFirstCar(db);
			storeSecondCar(db);
			db.close();
			db = Db4o.openFile(Util.DB4OFILENAME);
			retrieveAllCarsQBE(db);
			retrieveAllPilotsQBE(db);
			retrieveCarByPilotQBE(db);
			retrieveCarByPilotNameQuery(db);
			retrieveCarByPilotProtoQuery(db);
			retrievePilotByCarModelQuery(db);
			updateCar(db);
			updatePilotSingleSession(db);
			updatePilotSeparateSessionsPart1(db);
			db.close();
			db = Db4o.openFile(Util.DB4OFILENAME);
			updatePilotSeparateSessionsPart2(db);
			db.close();
			updatePilotSeparateSessionsImprovedPart1();
			db = Db4o.openFile(Util.DB4OFILENAME);
			updatePilotSeparateSessionsImprovedPart2(db);
			db.close();
			db = Db4o.openFile(Util.DB4OFILENAME);
			updatePilotSeparateSessionsImprovedPart3(db);
			deleteFlat(db);
			db.close();
			deleteDeepPart1();
			db = Db4o.openFile(Util.DB4OFILENAME);
			deleteDeepPart2(db);
			deleteDeepRevisited(db);
		}
		finally {
			db.close();
		}
	}

	public static void storeFirstCar(ObjectContainer db) {
		Car car1 = new Car("Ferrari");
		Pilot pilot1 = new Pilot("Michael Schumacher", 100);
		car1.setPilot(pilot1);
		db.store(car1);
	}

	public static void storeSecondCar(ObjectContainer db) {
		Pilot pilot2 = new Pilot("Rubens Barrichello", 99);
		db.store(pilot2);
		Car car2 = new Car("BMW");
		car2.setPilot(pilot2);
		db.store(car2);
	}

	public static void retrieveAllCarsQBE(ObjectContainer db) {
		Car proto = new Car(null);
		ObjectSet result = db.queryByExample(proto);
		listResult(result);
	}

	public static void retrieveAllPilotsQBE(ObjectContainer db) {
		Pilot proto = new Pilot(null, 0);
		ObjectSet result = db.queryByExample(proto);
		listResult(result);
	}

	public static void retrieveAllPilots(ObjectContainer db) {
		ObjectSet result = db.queryByExample(Pilot.class);
		listResult(result);
	}

	public static void retrieveCarByPilotQBE(ObjectContainer db) {
		Pilot pilotproto = new Pilot("Rubens Barrichello", 0);
		Car carproto = new Car(null);
		carproto.setPilot(pilotproto);
		ObjectSet result = db.queryByExample(carproto);
		listResult(result);
	}

	public static void retrieveCarByPilotNameQuery(ObjectContainer db) {
		Query query = db.query();
		query.constrain(Car.class);
		query.descend("pilot").descend("name").constrain("Rubens Barrichello");
		ObjectSet result = query.execute();
		listResult(result);
	}

	public static void retrieveCarByPilotProtoQuery(ObjectContainer db) {
		Query query = db.query();
		query.constrain(Car.class);
		Pilot proto = new Pilot("Rubens Barrichello", 0);
		query.descend("pilot").constrain(proto);
		ObjectSet result = query.execute();
		listResult(result);
	}

	public static void retrievePilotByCarModelQuery(ObjectContainer db) {
		Query carquery = db.query();
		carquery.constrain(Car.class);
		carquery.descend("model").constrain("Ferrari");
		Query pilotquery = carquery.descend("pilot");
		ObjectSet result = pilotquery.execute();
		listResult(result);
	}

	public static void retrieveAllPilotsNative(ObjectContainer db) {
		ObjectSet results = db.query(new Predicate() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unused")
			public boolean match(Pilot pilot) {
				return true;
			}
		});
		listResult(results);
	}

	public static void retrieveAllCars(ObjectContainer db) {
		ObjectSet results = db.queryByExample(Car.class);
		listResult(results);
	}

	public static void retrieveCarsByPilotNameNative(ObjectContainer db) {
		ObjectSet results = db.query(new Predicate() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unused")
			public boolean match(Car car) {
				// return car.getPilot().getName().equals(pilotName);
				return true;
			}
		});
		listResult(results);
	}
	public static void updateCar(ObjectContainer db) {
		ObjectSet result = db.query(new Predicate() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unused")
			public boolean match(Car car) {
				return car.getModel().equals("Ferrari");
			}
		});
		Car found = (Car) result.next();
		found.setPilot(new Pilot("Somebody else", 0));
		db.store(found);
		result = db.query(new Predicate() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unused")
			public boolean match(Car car) {
				return car.getModel().equals("Ferrari");
			}
		});
		listResult(result);
	}
	public static void updatePilotSingleSession(ObjectContainer db) {
		ObjectSet result = db.query(new Predicate() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unused")
			public boolean match(Car car) {
				return car.getModel().equals("Ferrari");
			}
		});
		Car found = (Car) result.next();
		// found.getPilot().addPoints(1);
		db.store(found);
		result = db.query(new Predicate() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unused")
			public boolean match(Car car) {
				return car.getModel().equals("Ferrari");
			}
		});
		listResult(result);
	}
	public static void updatePilotSeparateSessionsPart1(ObjectContainer db) {
		ObjectSet result = db.query(new Predicate() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unused")
			public boolean match(Car car) {
				return car.getModel().equals("Ferrari");
			}
		});
		Car found = (Car) result.next();
		// found.getPilot().addPoints(1);
		db.store(found);
	}
	public static void updatePilotSeparateSessionsPart2(ObjectContainer db) {
		ObjectSet result = db.query(new Predicate() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unused")
			public boolean match(Car car) {
				return car.getModel().equals("Ferrari");
			}
		});
		listResult(result);
	}
	@SuppressWarnings("deprecation")
	public static void updatePilotSeparateSessionsImprovedPart1() {
		Db4o
			.configure()
			.objectClass("com.db4o.f1.chapter2.Car")
			.cascadeOnUpdate(true);
	}

	public static void updatePilotSeparateSessionsImprovedPart2(
		ObjectContainer db) {
		ObjectSet result = db.query(new Predicate() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unused")
			public boolean match(Car car) {
				return car.getModel().equals("Ferrari");
			}
		});
		Car found = (Car) result.next();
		// found.getPilot().addPoints(1);
		db.store(found);
	}
	public static void updatePilotSeparateSessionsImprovedPart3(
		ObjectContainer db) {
		ObjectSet result = db.query(new Predicate() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unused")
			public boolean match(Car car) {
				return car.getModel().equals("Ferrari");
			}
		});
		listResult(result);
	}
	public static void deleteFlat(ObjectContainer db) {
		ObjectSet result = db.query(new Predicate() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unused")
			public boolean match(Car car) {
				return car.getModel().equals("Ferrari");
			}
		});
		Car found = (Car) result.next();
		db.delete(found);
		result = db.queryByExample(new Car(null));
		listResult(result);
	}
	@SuppressWarnings("deprecation")
	public static void deleteDeepPart1() {
		Db4o
			.configure()
			.objectClass("com.db4o.f1.chapter2.Car")
			.cascadeOnDelete(true);
	}

	public static void deleteDeepPart2(ObjectContainer db) {
		ObjectSet result = db.query(new Predicate() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unused")
			public boolean match(Car car) {
				return car.getModel().equals("BMW");
			}
		});
		Car found = (Car) result.next();
		db.delete(found);
		result = db.query(new Predicate() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unused")
			public boolean match(Car car) {
				return true;
			}
		});
		listResult(result);
	}
	public static void deleteDeepRevisited(ObjectContainer db) {
		ObjectSet result = db.query(new Predicate() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unused")
			public boolean match(Pilot pilot) {
				return pilot.getName().equals("Michael Schumacher");
			}
		});
		if (!result.hasNext()) {
			System.out.println("Pilot not found!");
			return;
		}
		Pilot pilot = (Pilot) result.next();
		Car car1 = new Car("Ferrari");
		Car car2 = new Car("BMW");
		car1.setPilot(pilot);
		car2.setPilot(pilot);
		db.store(car1);
		db.store(car2);
		db.delete(car2);
		ObjectSet cars = db.query(new Predicate() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unused")
			public boolean match(Car car) {
				return true;
			}
		});
		listResult(cars);
	}
}
