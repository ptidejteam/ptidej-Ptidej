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

import java.util.Date;

public class Game {
    private Date when;
    private Sport sport;
    private Team team1;
    private Team team2;
    private String result;
    
    
    public Game(Date when, Sport sport, Team team1, Team team2) {
        this.when = when;
    	this.sport = sport;
        this.team1 = team1;
        this.team2 = team2;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public Sport getSport() {
        return sport;
    }
    public void setSport(Sport sport) {
        this.sport = sport;
    }
    public Team getTeam1() {
        return team1;
    }
    public void setTeam1(Team team1) {
        this.team1 = team1;
    }
    public Team getTeam2() {
        return team2;
    }
    public void setTeam2(Team team2) {
        this.team2 = team2;
    }
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(when).append(" : Game of ").append(sport).append(" between ").append(team1.getName()).append(" and ").append(team2.getName());
        return buffer.toString();
    }
}
