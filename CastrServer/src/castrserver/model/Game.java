package castrserver.model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	
	public Game(ResultSet result) throws SQLException {
		this.id = result.getInt("id");
		this.name = result.getString("name");
		this.location = result.getString("location");
		this.gameDate = result.getDate("game_date");
		this.groupId = result.getInt("group_id");
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

	public Date getGameDate() {
		return this.gameDate;
	}
	
	public int getGroupId() {
		return this.groupId;
	}
}
