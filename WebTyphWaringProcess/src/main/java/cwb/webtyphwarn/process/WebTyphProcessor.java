package cwb.webtyphwarn.process;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import cwb.webtyphwarn.model.Tc;
import cwb.webtyphwarn.model.TypeChangingHistory;
import cwb.webtyphwarn.model.Warning;
import cwb.webtyphwarn.model.WebTyphName2No;
import cwb.webtyphwarn.model.WebTyphWarning;

@Component("webTyphProcessor")
public class WebTyphProcessor{

private static final Logger processorLogger = LogManager.getLogger(WebTyphProcessor.class);
	

	@Autowired
	@Qualifier("typeChangingHistoryPatterns")
	private List<List<String>> typeChangingHistoryPatterns;

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
			// initial java object to ORM
			Map<String, WebTyphName2No> webTyphName2NoMap = new HashMap<>();
			Set<WebTyphWarning> webTyphWarningList = new HashSet<>();
						
			// get changing history values
			TypeChangingHistory[] typeChangingHistories = webTyphRestfulApiClient.requestTypeChangingHistory();
			
			// process history values
			Map<List<TypeChangingHistory>, List<String>> processedTypeChangingHistoryListMap = processTypeChangingHistories(typeChangingHistories);
			
			Set<List<TypeChangingHistory>> processedTypeChangingHistoryList = processedTypeChangingHistoryListMap.keySet();
			
			for(List<TypeChangingHistory> processedTypeChangingHistories:processedTypeChangingHistoryList) {
				
				List<String> pattern = processedTypeChangingHistoryListMap.get(processedTypeChangingHistories);
				
				for(int index = 0; index < processedTypeChangingHistories.size(); index++) {
					
					// check if typhoon name exists or not & create webType
					if(!webTyphName2NoMap.containsKey(processedTypeChangingHistories.get(index).getTyphoonName())) {
						WebTyphName2No webTypName2No = new WebTyphName2No();
						String cwbTyNo = processedTypeChangingHistories.get(index).getCwbTyNo().toString();
						cwbTyNo = cwbTyNo.length()<2?"0"+cwbTyNo:cwbTyNo;
						String typeNo = new StringBuilder()
								.append(processedTypeChangingHistories.get(index).getIssue().substring(0,4))
								.append(cwbTyNo).toString();
						webTypName2No.setTyphNo(typeNo);
						webTypName2No.setTyphNameCht(processedTypeChangingHistories.get(index).getCwbTyphoonName());
						webTypName2No.setTyphNameEng(processedTypeChangingHistories.get(index).getTyphoonName());
						webTyphName2NoMap.put(processedTypeChangingHistories.get(index).getTyphoonName(), webTypName2No);
					}
					
					Warning[] warnings = webTyphRestfulApiClient.requestWarning(processedTypeChangingHistories.get(index));
					
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
						if(issue.equals(processedTypeChangingHistories.get(index).getIssue()) 
							&& typhoonName.equals(processedTypeChangingHistories.get(index).getTyphoonName())
							&& type.equals(processedTypeChangingHistories.get(index).getType())) {
							
							String states[] = pattern.get(index).split("&");
							for(int stateIndex = 1; stateIndex < states.length; stateIndex ++) {
								WebTyphWarning webTyphWarning = new WebTyphWarning();
								
								String typhName = new StringBuilder()
										.append(year)
										.append(typhoonName)
										.toString();
								webTyphWarning.setTyphName(typhName);
								
								webTyphWarning.setAlarmDate(new Timestamp(format.parse(fixTime).getTime()));
								webTyphWarning.setModifyDate(new Timestamp(format.parse(issue).getTime()));
								webTyphWarning.setState(Integer.parseInt(states[stateIndex]));
								webTyphWarningList.add(webTyphWarning);
							}
						}else {
							continue;
						}
					}
					
				}
				
			}
			
			insertWebTyph(webTyphName2NoMap, webTyphWarningList);
			
		} catch (Exception e) {
			processorLogger.error("", e);
			e.printStackTrace();
		} 
		
	}
	
	private Map<List<TypeChangingHistory>, List<String>> processTypeChangingHistories(TypeChangingHistory[] typeChangingHistories){
		
		Map<List<TypeChangingHistory>, List<String>> prcoessedResults = new HashMap<>();
		
		// Allocating each typhoon by CwbTyNo.
		Map<Integer, List<TypeChangingHistory>> typeChangingHistoryMap = new HashMap<>();
		for(TypeChangingHistory typeChangingHistory:typeChangingHistories) {
			
			if(typeChangingHistoryMap.containsKey(typeChangingHistory.getCwbTyNo())) {
				List<TypeChangingHistory> typeChangingList = typeChangingHistoryMap.get(typeChangingHistory.getCwbTyNo());
				typeChangingList.add(typeChangingHistory);
				typeChangingHistoryMap.put(typeChangingHistory.getCwbTyNo(), typeChangingList);
			}else {
				List<TypeChangingHistory> list = new ArrayList<>();
				list.add(typeChangingHistory);
				typeChangingHistoryMap.put(typeChangingHistory.getCwbTyNo(), list);
			}
		}
		
		// foreach all of typhoons and sorting it by issue element.
		Set<Integer> typeChangingHistoryKey = typeChangingHistoryMap.keySet();
		for(Integer key:typeChangingHistoryKey) {
			List<TypeChangingHistory> typeChangingHistoryList = typeChangingHistoryMap.get(key);
			
			// Sorting by issue element.
			Collections.sort(typeChangingHistoryList, new Comparator<TypeChangingHistory>() {
				@Override
				public int compare(TypeChangingHistory o1, TypeChangingHistory o2) {
					// TODO Auto-generated method stub
					try {
						Timestamp issue1 = new Timestamp(format.parse(o1.getIssue()).getTime());
						Timestamp issue2 = new Timestamp(format.parse(o2.getIssue()).getTime());
						
						return issue1.after(issue2)==true?0:-1;
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						processorLogger.error("", e);
					}
					return 0;
				}
			});
			
			// Check typeChangingHistoryList is fitted in with typeChangingHistoryPatterns or not.
			
			for(int patternIndex = 0;  patternIndex < typeChangingHistoryPatterns.size(); patternIndex++) {
				List<String> typeChangingHistoryPattern = typeChangingHistoryPatterns.get(patternIndex);
				
				if(typeChangingHistoryPattern.size() == typeChangingHistoryList.size()) {
					for(int patternEleIndex = 0; patternEleIndex < typeChangingHistoryPattern.size(); patternEleIndex++) {
						// get type pattern
						String typePattern = typeChangingHistoryPattern.get(patternEleIndex).split("&")[0];
						if(!typeChangingHistoryList.get(patternEleIndex).getType().equals(typePattern)) {
							break;
						}
						
						// Fitted in with one of the patterns, if for loop runs the last element.
						if(patternEleIndex == typeChangingHistoryPattern.size()-1) {
							prcoessedResults.put(typeChangingHistoryList, typeChangingHistoryPatterns.get(patternIndex));
						}
					}
				}
			}
		}
		
		return prcoessedResults;
	}
	
	private void insertWebTyph(Map<String, WebTyphName2No> webTyphName2NoMap, Set<WebTyphWarning> webTyphWarningList) {
		
		Set<WebTyphName2No> webTyphName2NoList = new HashSet<>();
		for(String key:webTyphName2NoMap.keySet()) {
			webTyphName2NoList.add(webTyphName2NoMap.get(key));
		}
		
		webTyphName2NoDaoMySQLImpl.insertWebTyphName2Nos(webTyphName2NoList);
		webTyphWarningDaoMySQLImpl.insertWebTyphWarnings(webTyphWarningList);
	}
	
}
