package cwb.webtyphwarn.config;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

@Configuration
@ComponentScan(basePackages = "cwb.webtyphwarn.main, cwb.webtyphwarn.config, cwb.webtyphwarn.process")
//@PropertySource("classpath:application.properties")
public class ResourceConfig {
	
	public static String resourceConfigPath;
	
	
	@Value("${type.changing.history.url}")
	private String typeChangingHistoryUrl;
	@Bean("typeChangingHistoryUrl")
	public String getTypeChangingHistoryUrl() {
		return typeChangingHistoryUrl;
	}
	
	@Value("${warning.url}")
	private String warningUrl;
	@Bean("warningUrl")
	public String getWarningUrlUrl() {
		return warningUrl;
	}
	
	@Value("${type.changing.history.year}")
	private String typeChangingHistoryYear;
	@Bean("typeChangingHistoryYear")
	public int getTypeChangingHistoryYear() {
		try {
			return Integer.parseInt(typeChangingHistoryYear);
		}catch(Exception e) {
			return Calendar.getInstance().get(Calendar.YEAR);
		}
	}
	
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
		PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
		propertySourcesPlaceholderConfigurer.setLocation(new FileSystemResource(resourceConfigPath));
		return propertySourcesPlaceholderConfigurer;
	}
}
