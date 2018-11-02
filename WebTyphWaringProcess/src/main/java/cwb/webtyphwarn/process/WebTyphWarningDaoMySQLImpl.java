package cwb.webtyphwarn.process;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cwb.webtyphwarn.model.WebTyphWarning;

@Repository("webTyphWarningDaoMySQLImpl")
public class WebTyphWarningDaoMySQLImpl implements WebTyphWarningDao{

	
	private JdbcTemplate jdbcTemplate;
	
	private static final String INSERT_WEB_TYPH_WARNING_STATEMENT = 
			"INSERT INTO WebTyphWarning (`TyphName`, `AlarmDate`, `State`, `ModifyDate`) VALUES (?,?,?,?)";
	
	@Override
	public void insertWebTyphWarnings(Set<WebTyphWarning> webTyphWarnings) {
		// TODO Auto-generated method stub
		
		for(WebTyphWarning webTyphWarning:webTyphWarnings) {
			jdbcTemplate.update(INSERT_WEB_TYPH_WARNING_STATEMENT,
					webTyphWarning.getTyphName(),webTyphWarning.getAlarmDate(), 
					webTyphWarning.getState(),webTyphWarning.getModifyDate());
		}
	}
	
	
	@Autowired
	@Qualifier("jdbcTemplateMySQL")
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	

}
