package cwb.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

@Configuration
@ComponentScan(basePackages = "cwb.project.car.model, cwb.project.main, cwb.project.config")
//@PropertySource("classpath:application.properties")
public class ResourceConfig {
	
	public static String resourceConfigPath;
	
	
	@Value("${car.client.url}")
	private String carClientUrl;
	@Bean("carClientUrl")
	public String getCarClientUrl() {
		return carClientUrl;
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
		PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
		propertySourcesPlaceholderConfigurer.setLocation(new FileSystemResource(resourceConfigPath));
		return propertySourcesPlaceholderConfigurer;
	}
}
