package vn.onepay.web.models;

import java.io.Serializable;

public class LoginForm implements Serializable {
	private String userName;
	private String password;

	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	
}
