package castrserver.model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
	private int id;
	private String login;
	private String password;
	private String name;
	private Date birthday;
	
	public User(int id, String login, String password, String name, Date birthday) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.name = name;
		this.birthday = birthday;
	}
	
	public User(ResultSet result) throws SQLException {
		this.id = result.getInt("id");
		this.login = result.getString("login"); 
		this.password = result.getString("password"); 
		this.name = result.getString("name");
		this.birthday = result.getDate("birthday");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
}
