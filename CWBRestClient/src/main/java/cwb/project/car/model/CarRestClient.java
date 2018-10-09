package cwb.project.car.model;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component("carRestClient")
public class CarRestClient {
	
	@Autowired
	@Qualifier("carClientUrl")
	private String carClientUrl;
	
	
	public Car requestCar() throws JsonParseException, JsonMappingException, IOException {
		ClientConfig config = new ClientConfig();
		
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(carClientUrl);
		String response = target.request().accept(MediaType.APPLICATION_JSON).get(String.class);
				
		ObjectMapper objectMapper = new ObjectMapper();
		
		String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
		Car car = objectMapper.readValue(json, Car.class);
		
		return car;
		
	}
	

	
}
