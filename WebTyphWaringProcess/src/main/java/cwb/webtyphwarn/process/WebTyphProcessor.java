package cwb.webtyphwarn.process;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import cwb.webtyphwarn.config.ResourceEnum;
import cwb.webtyphwarn.model.Tc;
import cwb.webtyphwarn.model.TypeChangingHistory;
import cwb.webtyphwarn.model.Warning;
import cwb.webtyphwarn.model.WebTyphName2No;
import cwb.webtyphwarn.model.WebTyphWarning;

@Component("webTyphProcessor")
public class WebTyphProcessor{

private static final Logger processorLogger = LogManager.getLogger(WebTyphProcessor.class);
	
	@Autowired
	@Qualifier("webTyphRestfulApiClient")
	private WebTyphRestfulApiClient webTyphRestfulApiClient;
	
	@Autowired
	@Qualifier("webTyphName2NoDaoMySQLImpl")
	private WebTyphName2NoDaoMySQLImpl webTyphName2NoDaoMySQLImpl;

	@Autowired
	@Qualifier("webTyphWarningDaoMySQLImpl")
	private WebTyphWarningDaoMySQLImpl webTyphWarningDaoMySQLImpl;

	
	private DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

	
	public void process() {
		try {
			
			// get changing history values
			TypeChangingHistory[] typeChangingHistories = webTyphRestfulApiClient.requestTypeChangingHistory();
			// get warning values
			Warning[] warnings = webTyphRestfulApiClient.requestWarning();
			
			// initial java object to ORM
			Map<String, WebTyphName2No> webTyphName2NoMap = new HashMap<>();
			List<WebTyphWarning> webTyphWarningList = new ArrayList<>();
			
			// Mapping jackson2 object to java object ...
			for(TypeChangingHistory typeChangingHistory:typeChangingHistories) {
				// check if typhoon name exists or not & create webType
				if(!webTyphName2NoMap.containsKey(typeChangingHistory.getTyphoonName())) {
					WebTyphName2No webTypName2No = new WebTyphName2No();
					String typeNo = new StringBuilder()
							.append(typeChangingHistory.getIssue().substring(0,4))
							.append(typeChangingHistory.getCwbTyNo()).toString();
					webTypName2No.setTyphNo(typeNo);
					System.out.println(typeChangingHistory.getCwbTyphoonName());
					webTypName2No.setTyphNameCht(typeChangingHistory.getCwbTyphoonName());
					webTypName2No.setTyphNameEng(typeChangingHistory.getTyphoonName());
					webTyphName2NoMap.put(typeChangingHistory.getTyphoonName(), webTypName2No);
				}
				
				// find item in warnings & add to the list.
				for(Warning warning:warnings) {
					// assume Tcs size is always 1
					if(warning.getTcs().size() != 1) {
						continue;
					}
					
					
					Tc tc = warning.getTcs().get(0);
					String issue = warning.getIssue();
					String typhoonName = tc.getTyphoonName();
					String type = tc.getType();
					String fixTime = tc.getFixTime();
					String year = tc.getYear().toString();

					// check issue & typhoonName & type are the same.
					if(issue.equals(typeChangingHistory.getIssue()) 
						&& typhoonName.equals(typeChangingHistory.getTyphoonName())
						&& type.equals(typeChangingHistory.getType())) {
						
						WebTyphWarning webTyphWarning = new WebTyphWarning();
						
						String typhName = new StringBuilder()
								.append(year).
								append(typhoonName).toString();
						webTyphWarning.setTyphName(typhName);
						
						webTyphWarning.setAlarmDate(new Timestamp(format.parse(fixTime).getTime()));
						webTyphWarning.setModifyDate(new Timestamp(format.parse(issue).getTime()));
						switch (type) {
						case "SEA":
							webTyphWarning.setState(Integer.parseInt(ResourceEnum.SEA.value()));						
							break; 
						case "LAND":
							webTyphWarning.setState(Integer.parseInt(ResourceEnum.LAND.value()));						
							break; 
						case "END":
							webTyphWarning.setState(Integer.parseInt(ResourceEnum.END.value()));						
							break; 
						default:
							break;
						}
						
						webTyphWarningList.add(webTyphWarning);
					
					
					}else {
						continue;
					}
					
				}
				
			}
			
			// 
			insertWebTyph(webTyphName2NoMap, webTyphWarningList);
			
		} catch (Exception e) {
			processorLogger.error("", e);
			e.printStackTrace();
		} 
		
		
		
		
	}
	
	
	private void insertWebTyph(Map<String, WebTyphName2No> webTyphName2NoMap, List<WebTyphWarning> webTyphWarningList) {
		
		List<WebTyphName2No> webTyphName2NoList = new ArrayList<>();
		for(String key:webTyphName2NoMap.keySet()) {
			webTyphName2NoList.add(webTyphName2NoMap.get(key));
		}
		
		webTyphName2NoDaoMySQLImpl.insertWebTyphName2Nos(webTyphName2NoList);
		webTyphWarningDaoMySQLImpl.insertWebTyphWarnings(webTyphWarningList);
	}
	
	

}
