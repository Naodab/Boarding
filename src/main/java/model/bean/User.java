package model.bean;

import java.sql.Date;

public class User {
	private String username;
	private String password;
	private String position;
	private boolean avtive;
	private Date lastLogin;

	public User(String username, String password, String position, boolean avtive, Date lastLogin) {
		super();
		this.username = username;
		this.password = password;
		this.position = position;
		this.avtive = avtive;
		this.lastLogin = lastLogin;
	}

	public User() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public boolean isAvtive() {
		return avtive;
	}

	public void setAvtive(boolean avtive) {
		this.avtive = avtive;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
}
