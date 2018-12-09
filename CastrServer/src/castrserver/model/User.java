package castrserver.model;

import java.sql.Date;

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

	public int getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}
	
	public String getPassword() {
		return password;
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

}
