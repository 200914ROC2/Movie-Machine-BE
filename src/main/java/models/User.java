package models;

import utilities.Utility;

public class User {
	private Integer id;
	private String username;
	private String password;
	private String passwordHash; 
	private String firstname;
	private String lastname;

	public User() {
		setId(null);
		setUsername("");
		setPassword("");
		setFirstname("");
		setLastname(""); 
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
	
	public String getPasswordHash() {		
		return passwordHash;
	}

	public Boolean checkPassword(String passwordToHash) {
		Utility util = new Utility();
		return util.verifyPassword(passwordToHash, getPasswordHash());
	}

	public void setPassword_hash(String password_hash) {  
		this.passwordHash = password_hash;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstName) {
		this.firstname = firstName;
	}

	public String getLastname() {
		return lastname;
	}



	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
}
