package castrserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import castrserver.model.User;

public class FriendshipDAO extends DAO {

	public FriendshipDAO(Connection connection) {
		super(connection);
	}
	
	public void addFriend(int userId, int friendId) throws SQLException {
		String query = "INSERT INTO FRIEND(user_id, friend_id) VALUES (?,?), (?,?);";
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			stmt.setInt(1, userId);
			stmt.setInt(2, friendId);
			stmt.setInt(3, friendId);
			stmt.setInt(4, userId);
			stmt.execute();
		}
	}
	
	public void removeFriend(int userId, int friendId) throws SQLException {
		String query = "DELETE FROM FRIEND WHERE user_id = ? AND friend_id = ? OR user_id = ? AND friend_id = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			stmt.setInt(1, userId);
			stmt.setInt(2, friendId);
			stmt.setInt(3, friendId);
			stmt.setInt(4, userId);
			if(stmt.executeUpdate() < 1) {
				throw new SQLException("Amizade não existe");
			}
		}
	}
	
	public LinkedList<User> getFriends(int userId) throws SQLException {
		LinkedList<User> users = new LinkedList<>();
		String query = "SELECT u.id, u.login, u.password, u.name, u.birthday "
				+ "FROM FRIEND AS f JOIN USER_TABLE AS u "
				+ "ON f.friend_id = u.id "
				+ "WHERE f.user_id = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			stmt.setInt(1, userId);
			try (ResultSet result = stmt.executeQuery();) {
				while(result.next()) {
					users.add(new User(result));
				}
			}
		}
		return users;
	}
	
	public boolean checkFriendship(int userId, int friendId) throws SQLException {
		String query = "SELECT * FROM FRIEND WHERE user_id = ? AND friend_id = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			stmt.setInt(1, userId);
			stmt.setInt(2, friendId);
			try (ResultSet result = stmt.executeQuery();) {
				return result.next();
			}
		}
	}
}
