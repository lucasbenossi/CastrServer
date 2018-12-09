package castrserver.model;

import java.sql.Date;

public class Game {
	private int id;
	private String name;
	private String location;
	private Date gameDate;
	private int groupId;
	
	public Game(int id, String name, String location, Date gameDate, int groupId) {
		this.id = id;
		this.name = name;
		this.location = location;
		this.gameDate = gameDate;
		this.groupId = groupId;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public Date getDate() {
		return this.gameDate;
	}
	
	public int getGroupId() {
		return this.groupId;
	}
	
}
