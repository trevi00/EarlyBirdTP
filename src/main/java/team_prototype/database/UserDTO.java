package team_prototype.database;

public class UserDTO {
	private String userid;
	private String name;
	private String password;
	private String displayname;
	
	//getter setter toString
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getDisplayname() {
		return displayname;
	}
	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}
	
	@Override
	public String toString() {
		return "UserDTO [userid=" + userid + ", name=" + name + ", password=" + password  + ", displayName="
				+ displayname + "]";
	}
	


	
}
