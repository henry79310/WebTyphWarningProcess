package cwb.webtyphwarn.process;

import java.io.IOException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cwb.webtyphwarn.config.JerseyClientFactory;
import cwb.webtyphwarn.model.TypeChangingHistory;
import cwb.webtyphwarn.model.Warning;


@Component("webTyphRestfulApiClient")
public class WebTyphRestfulApiClient {
	
	@Autowired
	@Qualifier("typeChangingHistoryUrl")
	private String typeChangingHistoryUrl;
	
	@Autowired
	@Qualifier("warningUrl")
	private String warningUrl;
	
	
	@Autowired
	@Qualifier("typeChangingHistoryYear")
	private int typeChangingHistoryYear;
	
	
	public TypeChangingHistory[] requestTypeChangingHistory() throws JsonParseException, JsonMappingException, IOException {
		
		Client client = JerseyClientFactory.create();
		
		WebTarget target = client.target(typeChangingHistoryUrl).path("/");
		
		
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
		
		Form payload = new Form();
		payload.param("year", String.valueOf(typeChangingHistoryYear));
		
		Response response = invocationBuilder
				.post(Entity.entity(payload,  MediaType.APPLICATION_FORM_URLENCODED));
		
		String values = response.readEntity(String.class);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		TypeChangingHistory[] typeChangingHistory = objectMapper.readValue(values, TypeChangingHistory[].class);
		
		return typeChangingHistory;
		
	}
	
	
	public Warning[] requestWarning(TypeChangingHistory payload) throws JsonParseException, JsonMappingException, IOException  {
		Client client = JerseyClientFactory.create();
		
		WebTarget target = client.target(warningUrl).path("/");
		
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
		
		Response response = invocationBuilder
				.post(Entity.entity(payload,  MediaType.APPLICATION_JSON));
		String values = response.readEntity(String.class);
		ObjectMapper objectMapper = new ObjectMapper();
		
		Warning[] warning = objectMapper.readValue(values, Warning[].class);
		return warning;
	}

	
	
	
}
