package ww.security.common;

public class LoginInfo {
	
	public String userName=null;
	public String password=null;
	public Object user=null;
	
	public LoginInfo() {
		
	}
	
	public LoginInfo(String userName,String password) {
		this.userName=userName;
		this.password=password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Object getUser() {
		return user;
	}

	public void setUser(Object user) {
		this.user = user;
	}	

}
