package castrserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import castrserver.model.User;

public class UserDAO extends DAO {

	public UserDAO(Connection connection) {
		super(connection);
	}

	public void create(User user) throws SQLException {
		String query = "INSERT INTO USER_TABLE(login,password,name,birthday) VALUES (?,?,?,?);";
		try (PreparedStatement stmt = connection.prepareStatement(query);){
			stmt.setString(1, user.getLogin());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getName());
			stmt.setDate(4, user.getBirthday());
			System.out.println(stmt.toString());
			stmt.execute();
		}
	}
	
	public User read(int id) throws SQLException {
		String query = "SELECT * FROM USER_TABLE WHERE id = ?;";
		User user = null;
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			stmt.setInt(1, id);
			System.out.println(stmt.toString());
			try (ResultSet result = stmt.executeQuery();) {
				if(result.next()) {
					user = new User(result);
				}
			}
		}
		return user;
	}
	
	public void update(User user) throws SQLException {
		String query = "UPDATE USER_TABLE SET login = ?, password = ?, name = ?, birthday = ? WHERE id = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			stmt.setString(1, user.getLogin());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getName());
			stmt.setDate(4, user.getBirthday());
			stmt.setInt(5, user.getId());
			System.out.println(stmt.toString());
			if(stmt.executeUpdate() < 1) {
				throw new SQLException("User não encontrado.");
			}
		}
	}
	
	public void delete(int id) throws SQLException {
		String query = "DELETE FROM USER_TABLE WHERE id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			stmt.setInt(1, id);
			System.out.println(stmt.toString());
			if(stmt.executeUpdate() < 1) {
				throw new SQLException("User não encontrado");
			}
		}
	}
	
	public LinkedList<User> all() throws SQLException {
		LinkedList<User> users = new LinkedList<>();
		String query = "SELECT * FROM USER_TABLE;";
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			System.out.println(stmt.toString());
			try (ResultSet result = stmt.executeQuery();) {
				while(result.next()) {
					users.add(new User(result));
				}
			}
		}
		return users;
	}
	
	public User authenticate(String login, String password) throws SQLException {
		String query = "SELECT * FROM USER_TABLE WHERE login = ? AND password = ?;";
		User user = null;
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			stmt.setString(1, login);
			stmt.setString(2, password);
			System.out.println(stmt.toString());
			try (ResultSet result = stmt.executeQuery();) {
				if(result.next()) {
					user = new User(result);
					user.setLogin(null);
					user.setPassword(null);
				}
			}
		}
		return user;
	}

}
