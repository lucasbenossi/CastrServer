package castrserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
		String query = "DELETE FROM MEMBER WHERE group_id = ? AND user_id = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			stmt.setInt(1, groupId);
			stmt.setInt(2, userId);
			if(stmt.executeUpdate() < 1) {
				throw new SQLException("Usuário ou Grupo não encontrado.");
			}
		}
	}
	
}
