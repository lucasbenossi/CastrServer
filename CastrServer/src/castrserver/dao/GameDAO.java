package castrserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import castrserver.model.Game;
import castrserver.model.Group;

public class GameDAO extends DAO {

	public GameDAO(Connection connection) {
		super(connection);
	}
	
	public void create(Game game) throws SQLException {
		String query = "INSERT INTO GAME(name,location,game_date,group_id) VALUES (?,?,?,?);";
		try (PreparedStatement stmt = connection.prepareStatement(query);){
			stmt.setString(1, game.getName());
			stmt.setString(2, game.getLocation());
			stmt.setDate(3, game.getDate());
			stmt.setInt(4, game.getGroupId());
			stmt.execute();
		}
	}
	
	public Game read(int id) throws SQLException {
		String query = "SELECT * FROM GAME WHERE id = ?;";
		Game game = null;
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			stmt.setInt(1, id);
			try (ResultSet result = stmt.executeQuery();) {
				if(result.next()) {
					game = new Game(result);
				}
			}
		}
		return game;
	}
	
	public void update(Game game) throws SQLException {
		String query = "UPDATE GAME SET name = ?, location = ?, game_date = ?, group_id = ? WHERE id = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			stmt.setString(1, game.getName());
			stmt.setString(2, game.getLocation());
			stmt.setDate(3, game.getDate());
			stmt.setInt(4, game.getGroupId());
			stmt.setInt(5, game.getId());
			if(stmt.executeUpdate() < 1) {
				throw new SQLException("Grupo não encontrado.");
			}
		}
	}
	
	public void delete(int id) throws SQLException {
		String query = "DELETE FROM GAME WHERE id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			stmt.setInt(1, id);
			if(stmt.executeUpdate() < 1) {
				throw new SQLException("Game não encontrado");
			}
		}
	}
	
	public LinkedList<Game> all() throws SQLException {
		LinkedList<Game> games = new LinkedList<>();
		String query = "SELECT * FROM GAME;";
		try (PreparedStatement stmt = connection.prepareStatement(query);
				ResultSet result = stmt.executeQuery();) {
			while(result.next()) {
				games.add(new Game(result));
			}
		}
		return games;
	}
	
	public Group getOwner(int gameId) throws SQLException {
		Group group = null;
		String query = "SELECT g.id, g.name, g.creator_id "
				+ "FROM GROUP_TABLE AS g JOIN GAME as j "
				+ "ON g.id = j.group_id "
				+ "WHERE j.id = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			stmt.setInt(1, gameId);
			try (ResultSet result = stmt.executeQuery();) {
				if(result.next()) {
					group = new Group(result);
				}
			}
		}
		return group;
	}
	
	public LinkedList<Game> getGamesFromOwner(int groupId) throws SQLException {
		LinkedList<Game> games = new LinkedList<>();
		String query = "SELECT * FROM GAME WHERE group_id = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			stmt.setInt(1, groupId);
			try (ResultSet result = stmt.executeQuery();) {
				while(result.next()) {
					games.add(new Game(result));
				}
			}
		}
		return games;
	}
}
