/*
NeoDatis ODB : Native Object Database (odb.info@neodatis.org)
Copyright (C) 2007 NeoDatis Inc. http://www.neodatis.org

This file is part of the db4o open source object database.

NeoDatis ODB is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

NeoDatis ODB is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
*/
package org.neodatis.odb.tutorial;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String name;
    private List players;
    public Team(String name) {
        this.name = name;
        players = new ArrayList();
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the players
     */
    public List getPlayers() {
        return players;
    }
    /**
     * @param players the players to set
     */
    public void setPlayers(List players) {
        this.players = players;
    }
    
    public void addPlayer(Player player){
        players.add(player);
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Team ").append(name).append(" ").append(players);
        return buffer.toString();
    }

}
