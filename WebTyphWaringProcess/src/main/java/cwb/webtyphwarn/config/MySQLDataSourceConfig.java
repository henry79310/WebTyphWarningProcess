package cwb.webtyphwarn.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
//@PropertySource("classpath:application.properties")
public class MySQLDataSourceConfig {
	
	
	private HikariDataSource dataSource;
	
	@Value("${database.mysql.jdbcUrl}")
	private String jdbcUrl;
	@Value("${database.mysql.username}")
	private String username;
	@Value("${database.mysql.password}")
	private String password;

	
	@PostConstruct
	public void initialHikariCP() throws Exception{
		
		HikariConfig config = new HikariConfig();
		config.setDataSourceClassName("com.mysql.jdbc.Driver");
		config.setJdbcUrl(jdbcUrl);
		config.setUsername(username);
		config.setPassword(password);
//		config.setMaximumPoolSize(maximumPoolSize);
//		config.setMinimumIdle(minIdle);
//		config.setAutoCommit(autoCommit);
		// In case we're using MySQL, these settings are recommended by HikariCP:
//		config.addDataSourceProperty("useServerPrepStmts", useServerPrepStmts);
//		config.addDataSourceProperty("cachePrepStmts", cachePrepStmts);
//		config.addDataSourceProperty("prepStmtCacheSize", prepStmtCacheSize);
//		config.addDataSourceProperty("prepStmtCacheSqlLimit", prepStmtCacheSqlLimit);
		dataSource = new HikariDataSource(config);
	}
	
	
	@PreDestroy
	public void cleanup(){
		dataSource.close();
	}
	
	@Bean(name = "jdbcTemplateMySQL")
	public JdbcTemplate getJdbcTemplateMySQL(){
		return new JdbcTemplate(dataSource);
	}

}
