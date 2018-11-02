package cwb.webtyphwarn.model;

import java.sql.Timestamp;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
	
	
	@Override
    public int hashCode() {
	  return new HashCodeBuilder(17, 31) // two randomly chosen prime numbers
			  // if deriving: appendSuper(super.hashCode()).
	            .append(this.getTyphName())
	            .append(this.getAlarmDate())
	            .append(this.getState())
	            .append(this.getModifyDate())
	            .toHashCode();
	}
 
	@Override
    public boolean equals(Object obj) {
       if (!(obj instanceof WebTyphWarning))
            return false;
        if (obj == this)
            return true;

        WebTyphWarning rhs = (WebTyphWarning) obj;
        return new EqualsBuilder()
            // if deriving: appendSuper(super.equals(obj)).
            .append(this.getTyphName(), rhs.getTyphName())
            .append(this.getAlarmDate(), rhs.getAlarmDate())
            .append(this.getState(), rhs.getState())
            .append(this.getModifyDate(), rhs.getModifyDate())
            .isEquals();
    }
	
}
