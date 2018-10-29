package cwb.webtyphwarn.main;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import cwb.webtyphwarn.config.ResourceConfig;
import cwb.webtyphwarn.process.WebTyphProcessor;


// Program Arguments : src/main/resources/application.properties
// VM Arguments: -Dlog4j.configurationFile=src/main/resources/log4j2-dev.xml
public class Main {

	public static void main(String[] args) {
		
		// When timezone need to +8:00
		//System.setProperty("user.timezone", "UTC");
        //TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		System.setProperty("Log4jContextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
		
		// Set Log file path
		if(args[0] != null && Files.exists(Paths.get(args[0]))) {
			ResourceConfig.resourceConfigPath = args[0];
		}else {
			System.exit(0);
		}
		
		@SuppressWarnings("resource")
		AbstractApplicationContext appContext = new AnnotationConfigApplicationContext(ResourceConfig.class);
		
		//TyphoonWarningProcessor be executed.
		WebTyphProcessor typhoonWarningProcessor = (WebTyphProcessor)appContext.getBean("webTyphProcessor");
		typhoonWarningProcessor.process();
		
	}
}
