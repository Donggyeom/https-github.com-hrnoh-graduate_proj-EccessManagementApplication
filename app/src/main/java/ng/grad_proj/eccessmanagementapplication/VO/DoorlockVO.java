package ng.grad_proj.eccessmanagementapplication.VO;

public class DoorlockVO {
//	WebSocketSession session;
	private int dno;
	private String location;
	private String mac;
	private int level;
	private int isOnline;
	
	public DoorlockVO() { isOnline = 0; }
	
	public int getDno() {
		return dno;
	}
	public void setDno(int dno) {
		this.dno = dno;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(int isOnline) {
		this.isOnline = isOnline;
	}
	@Override
	public String toString() {
		return "DoorlockVO [session=" + ", dno=" + dno + ", location=" + location + ", mac=" + mac
				+ ", level=" + level + ", isOnline=" + isOnline + "]";
	}
}
