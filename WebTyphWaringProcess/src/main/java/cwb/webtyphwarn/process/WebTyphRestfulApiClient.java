package cwb.webtyphwarn.process;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cwb.webtyphwarn.model.TypeChangingHistory;
import cwb.webtyphwarn.model.Warning;


@Component("webTyphRestfulApiClient")
public class WebTyphRestfulApiClient {
	
	@Autowired
	@Qualifier("typeChangingHistoryUrl")
	private String typeChangingHistoryUrl;
	
	
	public TypeChangingHistory[] requestTypeChangingHistory() throws JsonParseException, JsonMappingException, IOException {
		
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);

		WebTarget target = client.target(typeChangingHistoryUrl);
		String response = target.request().accept(MediaType.APPLICATION_JSON).get(String.class);
		System.out.println(response);
		ObjectMapper objectMapper = new ObjectMapper();
		
		File file = Paths.get("src/test/resources/type-changing-history_2018.json").toFile();
		TypeChangingHistory[] typeChangingHistory = objectMapper.readValue(file, TypeChangingHistory[].class);
		return typeChangingHistory;
		
	}
	
	
	public Warning[] requestWarning() throws JsonParseException, JsonMappingException, IOException  {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);

		WebTarget target = client.target(typeChangingHistoryUrl);
		String response = target.request().accept(MediaType.APPLICATION_JSON).get(String.class);
		ObjectMapper objectMapper = new ObjectMapper();
		
		File file = Paths.get("src/test/resources/warning_2018.json").toFile();
		Warning[] warning = objectMapper.readValue(file, Warning[].class);
		return warning;
	}

	
	
	
}
