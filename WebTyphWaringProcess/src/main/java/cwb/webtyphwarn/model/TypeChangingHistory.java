package cwb.webtyphwarn.model;


import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"cwb_ty_no",
	"report_no",
	"type",
	"cwb_typhoon_name",
	"issue",
	"typhoon_name"
})
public class TypeChangingHistory {

	@JsonProperty("cwb_ty_no")
	private Integer cwbTyNo;
	@JsonProperty("report_no")
	private String reportNo;
	@JsonProperty("type")
	private String type;
	@JsonProperty("cwb_typhoon_name")
	private String cwbTyphoonName;
	@JsonProperty("issue")
	private String issue;
	@JsonProperty("typhoon_name")
	private String typhoonName;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	
	@JsonProperty("cwb_ty_no")
	public Integer getCwbTyNo() {
	return cwbTyNo;
	}
	
	@JsonProperty("cwb_ty_no")
	public void setCwbTyNo(Integer cwbTyNo) {
	this.cwbTyNo = cwbTyNo;
	}
	
	@JsonProperty("report_no")
	public String getReportNo() {
	return reportNo;
	}
	
	@JsonProperty("report_no")
	public void setReportNo(String reportNo) {
	this.reportNo = reportNo;
	}
	
	@JsonProperty("type")
	public String getType() {
	return type;
	}
	
	@JsonProperty("type")
	public void setType(String type) {
	this.type = type;
	}
	
	@JsonProperty("cwb_typhoon_name")
	public String getCwbTyphoonName() {
	return cwbTyphoonName;
	}
	
	@JsonProperty("cwb_typhoon_name")
	public void setCwbTyphoonName(String cwbTyphoonName) {
	this.cwbTyphoonName = cwbTyphoonName;
	}
	
	@JsonProperty("issue")
	public String getIssue() {
	return issue;
	}
	
	@JsonProperty("issue")
	public void setIssue(String issue) {
	this.issue = issue;
	}
	
	@JsonProperty("typhoon_name")
	public String getTyphoonName() {
	return typhoonName;
	}
	
	@JsonProperty("typhoon_name")
	public void setTyphoonName(String typhoonName) {
	this.typhoonName = typhoonName;
	}
	
	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
	return this.additionalProperties;
	}
	
	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
	this.additionalProperties.put(name, value);
	}

}