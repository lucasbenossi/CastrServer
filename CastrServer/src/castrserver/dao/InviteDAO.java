package castrserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import castrserver.model.Game;
import castrserver.model.Invite;
import castrserver.model.User;

public class InviteDAO extends DAO {

	public InviteDAO(Connection connection) {
		super(connection);
	}
	
	public void invitePlayer(int gameId, int userId) throws SQLException {
		String query = "INSERT INTO INVITATIONS(game_id, user_id) VALUES (?,?);";
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			stmt.setInt(1, gameId);
			stmt.setInt(2, userId);
			System.out.println(stmt.toString());
			stmt.execute();
		}
	}
	
	public void acceptInvite(int gameId, int userId) throws SQLException {
		String query = "UPDATE INVITATIONS SET joined = true WHERE game_id = ? AND user_id = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			stmt.setInt(1, gameId);
			stmt.setInt(2, userId);
			System.out.println(stmt.toString());
			if(stmt.executeUpdate() < 1) {
				throw new SQLException("Convite não existe");
			}
		}
	}
	
	public void declineInvite(int gameId, int userId) throws SQLException {
		String query = "DELETE FROM INVITATIONS WHERE game_id = ? AND user_id = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			stmt.setInt(1, gameId);
			stmt.setInt(2, userId);
			System.out.println(stmt.toString());
			if(stmt.executeUpdate() < 1) {
				throw new SQLException("Convite não existe");
			}
		}
	}
	
	public LinkedList<Invite> listInvitesFromUser(int userId) throws SQLException {
		LinkedList<Invite> invites = new LinkedList<>();
		String query = "SELECT g.id, g.name, g.location, g.game_date, g.group_id, i.joined "
				+ "FROM INVITATIONS AS i JOIN GAME AS g "
				+ "ON i.game_id = g.id "
				+ "WHERE i.user_id = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			stmt.setInt(1, userId);
			System.out.println(stmt.toString());
			try (ResultSet result = stmt.executeQuery();) {
				while(result.next()) {
					Game game = new Game(result);
					Invite invite = new Invite(null, game, result.getBoolean("joined"));
					invites.add(invite);
				}
			}
		}
		return invites;
	}
	
	public LinkedList<Invite> listInvitesFromGame(int gameId) throws SQLException {
		LinkedList<Invite> invites = new LinkedList<>();
		String query = "SELECT u.id, u.login, u.password, u.name, u.birthday, i.joined "
				+ "FROM INVITATIONS AS i JOIN USER_TABLE AS u "
				+ "ON i.user_id = u.id "
				+ "WHERE i.game_id = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query);) {
			stmt.setInt(1, gameId);
			System.out.println(stmt.toString());
			try (ResultSet result = stmt.executeQuery();) {
				while(result.next()) {
					User user = new User(result);
					Invite invite = new Invite(user, null, result.getBoolean("joined"));
					invites.add(invite);
				}
			}
		}
		return invites;
	}
	
	public static void main(String[] args) throws Exception {
		try (DAOFactory daoFact = new DAOFactory();) {
			InviteDAO dao = daoFact.createInviteDAO();
			for(Invite invite : dao.listInvitesFromUser(20)) {
				System.out.println(invite.getGame().getName() + " " + invite.isJoined());
			}
		}
	}
	
}
