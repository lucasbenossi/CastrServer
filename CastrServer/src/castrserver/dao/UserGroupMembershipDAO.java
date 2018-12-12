package castrserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import castrserver.model.Group;
import castrserver.model.User;

public class UserGroupMembershipDAO extends DAO {

	public UserGroupMembershipDAO(Connection connection) {
		super(connection);
	}

	public void insertUserGroup(int groupId, int userId) throws SQLException {
		String query = "INSERT INTO MEMBER(group_id,user_id) VALUES (?,?);";
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			stmt.setInt(1, groupId);
			stmt.setInt(2, userId);
			stmt.execute();
		}
	}
	
	public void deleteUserGroup(int groupId, int userId) throws SQLException {
		String query 
				= "WITH owner AS ( "
					+ "SELECT creator_id "
					+ "FROM GROUP_TABLE "
					+ "WHERE id = ? "
					+ ") "
				+ "DELETE FROM MEMBER "
				+ "USING owner "
				+ "WHERE owner.creator_id != ? AND group_id = ? AND user_id = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			stmt.setInt(1, groupId);
			stmt.setInt(2, userId);
			stmt.setInt(3, groupId);
			stmt.setInt(4, userId);
			if(stmt.executeUpdate() < 1) {
				throw new SQLException("Usuário ou Grupo não encontrado ou usuário é dono do grupo.");
			}
		}
	}
	
	public LinkedList<Group> groupsUserIsIn(int userId) throws SQLException {
		LinkedList<Group> groups = new LinkedList<>();
		String query = "SELECT g.id, g.name, g.creator_id "
				+ "FROM GROUP_TABLE AS g JOIN MEMBER AS m "
				+ "ON g.id = m.group_id "
				+ "WHERE m.user_id = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			stmt.setInt(1, userId);
			try (ResultSet result = stmt.executeQuery();) {
				while(result.next()) {
					groups.add(new Group(result));
				}
			}
		}
		return groups;
	}
	
	public LinkedList<User> usersFromGroup(int groupId) throws SQLException {
		LinkedList<User> users = new LinkedList<>();
		String query = "SELECT u.id, u.login, u.password, u.name, u.birthday "
				+ "FROM USER_TABLE AS u JOIN MEMBER AS m "
				+ "ON u.id = m.user_id "
				+ "WHERE m.group_id = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			stmt.setInt(1, groupId);
			try (ResultSet result = stmt.executeQuery();) {
				while (result.next()) {
					users.add(new User(result));
				}
			}
		}
		return users;
	}
	
}
