package cwb.webtyphwarn.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class WebTyphName2No {
	private String typhNo;
	private String typhNameEng;
	private String typhNameCht;
	
	public String getTyphNo() {
		return typhNo;
	}
	public void setTyphNo(String typhNo) {
		this.typhNo = typhNo;
	}
	public String getTyphNameEng() {
		return typhNameEng;
	}
	public void setTyphNameEng(String typhNameEng) {
		this.typhNameEng = typhNameEng;
	}
	public String getTyphNameCht() {
		return typhNameCht;
	}
	public void setTyphNameCht(String typhNameCht) {
		this.typhNameCht = typhNameCht;
	}
	
	@Override
    public int hashCode() {
	  return new HashCodeBuilder(17, 31) // two randomly chosen prime numbers
			  // if deriving: appendSuper(super.hashCode()).
	            .append(this.getTyphNo())
	            .append(this.getTyphNameEng())
	            .append(this.getTyphNameCht())
	            .toHashCode();
	}
 
	@Override
    public boolean equals(Object obj) {
       if (!(obj instanceof WebTyphName2No))
            return false;
        if (obj == this)
            return true;

        WebTyphName2No rhs = (WebTyphName2No) obj;
        return new EqualsBuilder()
            // if deriving: appendSuper(super.equals(obj)).
            .append(this.getTyphNo(), rhs.getTyphNo())
            .append(this.getTyphNameEng(), rhs.getTyphNameEng())
            .append(this.getTyphNameCht(), rhs.getTyphNameCht())
            .isEquals();
    }
	
	
}
