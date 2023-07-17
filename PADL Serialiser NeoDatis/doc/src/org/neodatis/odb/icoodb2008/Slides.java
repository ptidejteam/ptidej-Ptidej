package org.neodatis.odb.icoodb2008;

import java.util.Date;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;
import org.neodatis.odb.tutorial.Player;
import org.neodatis.odb.tutorial.Sport;

public class Slides {

	public void slide1() throws Exception{
		ODB odb = ODBFactory.open("base.odb");
		
		odb.close();
	}
	
	public void slide2Insert() throws Exception{
		// Open the database
		ODB odb = ODBFactory.open("base.odb");
		// Creates a tennis player
		Player player = new Player("Andr\u00E9 Agassi", new Date(), new Sport("Tennis"));
		// Actually stores the object
		odb.store(player);
		// Closes the database : implicit commit
		odb.close();
	}
	
	public void slide2Getting() throws Exception{
		// Open the database
		ODB odb = ODBFactory.open("base.odb");
		// Retrieves all players
		Objects objects = odb.getObjects(Player.class);
		// Iterates through players
		while(objects.hasNext()){
			Player player = (Player) objects.next();
			System.out.println(player);
		}
		// Closes the database : implicit commit
		odb.close();
	}

	public void slide2Update() throws Exception{
		// Open the database
		ODB odb = ODBFactory.open("base.odb");
		// Creates a tennis player
		Player player = new Player("Andr\u00E9 Agassi", new Date(), new Sport("Tennis"));
		// Actually stores the object
		odb.store(player);
		// Closes the database : implicit commit
		odb.close();
	}

	public void slideGetFromOid() throws Exception{
		// Open the database
		ODB odb = ODBFactory.open("base.odb");
		// Creates a tennis player
		Player player = (Player) odb.getObjectFromId(null);
		// Actually stores the object
		odb.store(player);
		// Closes the database : implicit commit
		odb.close();
	}

	public void slideGetCriteriaQuery() throws Exception{
		// Open the database
		ODB odb = ODBFactory.open("base.odb");

		// Creates a Criteria query to get players with name = 'Andr\u00E9 Agassi'
		IQuery query = new CriteriaQuery(Player.class,Where.equal("name", "Andr\u00E9 Agassi"));
		
		// executes the query
		Objects players = odb.getObjects(query);
		
		// Closes the database : implicit commit
		odb.close();
	}

}
