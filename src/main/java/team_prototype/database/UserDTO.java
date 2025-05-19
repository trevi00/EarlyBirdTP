package team_prototype.database;

public class UserDTO {
	private String ID;
	private String USERNAME;
	private String password;
	private String DISPLAY_NAME;
	private int point;

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	//getter setter toString
	public String getID() {
		return ID;
	}
	public void setID(String ID) {
		this.ID = ID;
	}

	public String getUSERNAME() {
		return USERNAME;
	}
	public void setUSERNAME(String USERNAME) {
		this.USERNAME = USERNAME;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getDISPLAY_NAME() {
		return DISPLAY_NAME;
	}
	public void setDISPLAY_NAME(String DISPLAY_NAME) {
		this.DISPLAY_NAME = DISPLAY_NAME;
	}
	
	@Override
	public String toString() {
		return "UserDTO [userid=" + ID + ", name=" + USERNAME + ", password=" + password  + ", displayName="
				+ DISPLAY_NAME + "]";
	}
	


	
}
