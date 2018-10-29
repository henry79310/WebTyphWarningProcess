package cwb.webtyphwarn.model;

import java.sql.Timestamp;

public class WebTyphWarning {
	private String typhName;
	private Timestamp alarmDate;
	private int state;
	private Timestamp modifyDate;
	
	public String getTyphName() {
		return typhName;
	}
	public void setTyphName(String typhName) {
		this.typhName = typhName;
	}
	public Timestamp getAlarmDate() {
		return alarmDate;
	}
	public void setAlarmDate(Timestamp alarmDate) {
		this.alarmDate = alarmDate;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Timestamp getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Timestamp modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	
}
