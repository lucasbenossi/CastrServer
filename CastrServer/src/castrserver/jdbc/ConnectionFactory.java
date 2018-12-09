package castrserver.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
	private static ConnectionFactory instance = null;

	private String dbHost;
	private String dbPort;
	private String dbName;
	private String dbUser;
	private String dbPassword;

	private ConnectionFactory() {
	}

	// Singleton
	public static ConnectionFactory getInstance() {
		if (instance == null) {
			instance = new ConnectionFactory();
		}
		return instance;
	}

	public Connection getConnection() throws ClassNotFoundException, IOException, SQLException {
		Class.forName("org.postgresql.Driver");
		readProperties();
		String url = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;
		return DriverManager.getConnection(url, dbUser, dbPassword);
	}

	private void readProperties() throws IOException {
		Properties properties = new Properties();
		String path = "castrserver/jdbc/datasource.properties";
		InputStream input = this.getClass().getClassLoader().getResourceAsStream(path);
		properties.load(input);
		dbHost = properties.getProperty("host");
		dbPort = properties.getProperty("port");
		dbName = properties.getProperty("name");
		dbUser = properties.getProperty("user");
		dbPassword = properties.getProperty("password");
	}

}
