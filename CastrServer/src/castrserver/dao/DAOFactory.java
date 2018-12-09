package castrserver.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import castrserver.jdbc.ConnectionFactory;

public class DAOFactory implements AutoCloseable {
	private Connection connection;
	
	public DAOFactory() throws ClassNotFoundException, IOException, SQLException {
		this.connection = ConnectionFactory.getInstance().getConnection();
	}
	
	public void begin() throws SQLException {
		connection.setAutoCommit(false);
	}
	
	public void commit() throws SQLException {
		connection.commit();
	}
	
	public void rollback() throws SQLException {
		connection.rollback();
	}
	
	public void end() throws SQLException {
		connection.setAutoCommit(true);
	}

	@Override
	public void close() throws SQLException {
		connection.close();		
	}
	
	public UserDAO createUserDAO() {
		return new UserDAO(connection);
	}
	
	public GroupDAO createGroupDAO() {
		return new GroupDAO(connection);
	}
	public GameDAO createGameDAO() {
		return new GameDAO(connection);
	}
	public UserGroupMembershipDAO createUserGroupMembershipDAO() {
		return new UserGroupMembershipDAO(connection);
	}
	public FriendshipDAO createFriendshipDAO() {
		return new FriendshipDAO(connection);
	}
	public ItemDAO createItemsDAO() {
		return new ItemDAO(connection);
	}
}
