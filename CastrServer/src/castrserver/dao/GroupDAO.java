package castrserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import castrserver.model.Group;
import castrserver.model.User;

public class GroupDAO extends DAO {

	public GroupDAO(Connection connection) {
		super(connection);
	}

	public void create(Group group) throws SQLException {
		String query 
				= "WITH g AS ( "
					+ "INSERT INTO GROUP_TABLE(name,creator_id) "
					+ "VALUES (?,?) "
					+ "RETURNING id as group_id, creator_id as user_id "
					+ ") "
				+ "INSERT INTO MEMBER (SELECT * FROM g);";
		try (PreparedStatement stmt = connection.prepareStatement(query);){
			stmt.setString(1, group.getName());
			stmt.setInt(2, group.getCreatorId());
			System.out.println(stmt.toString());
			stmt.execute();
		}
	}
	
	public Group read(int id) throws SQLException {
		String query = "SELECT * FROM GROUP_TABLE WHERE id = ?;";
		Group group = null;
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			stmt.setInt(1, id);
			System.out.println(stmt.toString());
			try (ResultSet result = stmt.executeQuery();) {
				if(result.next()) {
					group = new Group(result);
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
			System.out.println(stmt.toString());
			if(stmt.executeUpdate() < 1) {
				throw new SQLException("Grupo não encontrado.");
			}
		}
	}
	
	public void delete(int id) throws SQLException {
		String query = "DELETE FROM GROUP_TABLE WHERE id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			stmt.setInt(1, id);
			System.out.println(stmt.toString());
			if(stmt.executeUpdate() < 1) {
				throw new SQLException("Grupo não encontrado");
			}
		}
	}
	
	public LinkedList<Group> all() throws SQLException {
		LinkedList<Group> groups = new LinkedList<>();
		String query = "SELECT * FROM GROUP_TABLE;";
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			System.out.println(stmt.toString());
			try (ResultSet result = stmt.executeQuery();) {
				while(result.next()) {
					groups.add(new Group(result));
				}
			}
		}
		return groups;
	}
	
	public User getOwnerFromGroup(int id) throws SQLException {
		String query = "SELECT u.id, u.login, u.password, u.name, u.birthday "
				+ "FROM USER_TABLE AS u JOIN GROUP_TABLE AS g "
				+ "ON u.id = g.creator_id "
				+ "WHERE g.id = ?;";
		User owner = null;
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			stmt.setInt(1, id);
			System.out.println(stmt.toString());
			try (ResultSet result = stmt.executeQuery();) {
				if(result.next()) {
					owner = new User(result);
				}
			}
		}
		return owner;
	}
	
	public LinkedList<Group> getGroupsFromOwner(int id) throws SQLException {
		LinkedList<Group> groups = new LinkedList<>();
		String query = "SELECT * FROM GROUP_TABLE WHERE creator_id = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			stmt.setInt(1, id);
			System.out.println(stmt.toString());
			try (ResultSet result = stmt.executeQuery();) {
				while(result.next()) {
					groups.add(new Group(result));
				}
			}
		}
		return groups;
	}
	
}
