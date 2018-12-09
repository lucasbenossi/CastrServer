package castrserver.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Group {
	private int id;
	private String name;
	private int creatorId;
	
	public Group(int id, String name, int creatorId) {
		this.id = id;
		this.name = name;
		this.creatorId = creatorId;
	}
	
	public Group(ResultSet result) throws SQLException {
		this.id = result.getInt("id");
		this.name = result.getString("name"); 
		this.creatorId = result.getInt("creator_id");
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
	
	public int getCreatorId() {
		return this.creatorId;
	}
}
