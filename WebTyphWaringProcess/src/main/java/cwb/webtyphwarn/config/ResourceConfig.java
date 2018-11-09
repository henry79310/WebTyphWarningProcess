package cwb.webtyphwarn.config;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

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
	
	
	@Value("${type.changing.history.patterns}")
	private String typeChangingHistoryPatterns;
	@Bean("typeChangingHistoryPatterns")
	public List<List<String>> getTypeChangingPatterns() {
		
		List<List<String>> patternsList = new LinkedList<>(); 
		String patternArray[] = typeChangingHistoryPatterns.split("\\|");
		for(String pattern:patternArray) {
			String values[] = pattern.split(",");
			List<String> patternList = new LinkedList<>();
			for(String value:values) {
				patternList.add(value);
			}
			patternsList.add(patternList);
		}
		
		return patternsList;
	}
	
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
		PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
		propertySourcesPlaceholderConfigurer.setLocation(new FileSystemResource(resourceConfigPath));
		return propertySourcesPlaceholderConfigurer;
	}
}
