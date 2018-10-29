package cwb.webtyphwarn.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"info",
	"issue",
	"tcs",
	"area"
})
public class Warning {

	@JsonProperty("info")
	private List<Info> info = null;
	@JsonProperty("issue")
	private String issue;
	@JsonProperty("tcs")
	private List<Tc> tcs = null;
	@JsonProperty("area")
	private List<String> area = null;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	
	@JsonProperty("info")
	public List<Info> getInfo() {
	return info;
	}
	
	@JsonProperty("info")
	public void setInfo(List<Info> info) {
	this.info = info;
	}
	
	@JsonProperty("issue")
	public String getIssue() {
	return issue;
	}
	
	@JsonProperty("issue")
	public void setIssue(String issue) {
	this.issue = issue;
	}
	
	@JsonProperty("tcs")
	public List<Tc> getTcs() {
	return tcs;
	}
	
	@JsonProperty("tcs")
	public void setTcs(List<Tc> tcs) {
	this.tcs = tcs;
	}
	
	@JsonProperty("area")
	public List<String> getArea() {
	return area;
	}
	
	@JsonProperty("area")
	public void setArea(List<String> area) {
	this.area = area;
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