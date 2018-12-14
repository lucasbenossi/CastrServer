package castrserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class ItemDAO extends DAO {

	public ItemDAO(Connection connection) {
		super(connection);
	}
	
	public void insertItem(int gameId, String item) throws SQLException {
		String query = "INSERT INTO ITEMS(game_id, item) VALUES (?,?);";
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			stmt.setInt(1, gameId);
			stmt.setString(2, item);
			System.out.println(stmt.toString());
			stmt.execute();
		}
	}
	
	public void removeItem(int gameId, String item) throws SQLException {
		String query = "DELETE FROM ITEMS WHERE game_id = ? AND item = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			stmt.setInt(1, gameId);
			stmt.setString(2, item);
			System.out.println(stmt.toString());
			if(stmt.executeUpdate() < 1) {
				throw new SQLException("Item não existe no jogo especificado");
			}
		}
	}
	
	public LinkedList<String> allItemsFromGame(int gameId) throws SQLException {
		LinkedList<String> items = new LinkedList<>();
		String query = "SELECT item FROM ITEMS WHERE game_id = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			stmt.setInt(1, gameId);
			System.out.println(stmt.toString());
			try (ResultSet result = stmt.executeQuery();) {
				while(result.next()) {
					items.add(result.getString(1));
				}
			}
		}
		return items;
	}

	public static void main(String[] args) throws Exception {
		try (DAOFactory daoFact = new DAOFactory(); ){
			ItemDAO dao = daoFact.createItemsDAO();
			for(String item : dao.allItemsFromGame(7)) {
				System.out.println(item);
			}
		}
	}
}

