package castrserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import castrserver.model.Group;

public class GroupDAO extends DAO {

	public GroupDAO(Connection connection) {
		super(connection);
	}

	public void create(Group group) throws SQLException {
		String query = "INSERT INTO GROUP_TABLE(name,creator_id) VALUES (?,?);";
		try (PreparedStatement stmt = connection.prepareStatement(query);){
			stmt.setString(1, group.getName());
			stmt.setInt(2, group.getCreatorId());
			stmt.execute();
		}
	}
	
	public Group read(int id) throws SQLException {
		String query = "SELECT * FROM GROUP_TABLE WHERE id = ?;";
		Group group = null;
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			stmt.setInt(1, id);
			try (ResultSet result = stmt.executeQuery();) {
				if(result.next()) {
					group = new Group(result.getInt("id"),
							result.getString("name"), 
							result.getInt("creator_id"));
				}
			}
		}
		return group;
	}
	
	public void update(Group group) throws SQLException {
		String query = "UPDATE GROUP_TABLE SET name = ?, creator_id = ? WHERE id = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			stmt.setString(1, group.getName());
			stmt.setInt(2, group.getCreatorId());
			stmt.setInt(3, group.getId());
			if(stmt.executeUpdate() < 1) {
				throw new SQLException("Grupo não encontrado.");
			}
		}
	}
	
	public void delete(int id) throws SQLException {
		String query = "DELETE FROM GROUP_TABLE WHERE id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			stmt.setInt(1, id);
			if(stmt.executeUpdate() < 1) {
				throw new SQLException("Grupo não encontrado");
			}
		}
	}
	
	public LinkedList<Group> all() throws SQLException {
		LinkedList<Group> groups = new LinkedList<>();
		String query = "SELECT * FROM GROUP_TABLE;";
		try (PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet result = stmt.executeQuery();) {
			while(result.next()) {
				groups.add(new Group(result.getInt("id"),
						result.getString("name"), 
						result.getInt("creator_id")));
			}
		}
		return groups;
	}
	
}
